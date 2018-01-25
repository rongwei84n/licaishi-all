<template>
  <div class="electricWrap">
    <div class="changeDate">
      <span v-for="(item,index) in itemsButton" :class="{backgroundbutton: j==index}" @click="toggleButton(index)">{{item.name}}</span>
    </div>
    <div class="search">
      <span class="name">型号</span>
      <el-select @change="querySelectType" v-model="type">
        <el-option
          v-for="item in typeData"
          :key="item.str_id"
          :label="item.str_val"
          :value="item.str_id">
        </el-option>
      </el-select>
      <span class="name">硬件版本号</span>
      <el-select @change="querySelectHw" v-model="hw">
        <el-option
          v-for="item in hwData"
          :key="item.str_id"
          :label="item.str_val"
          :value="item.str_id">
        </el-option>
      </el-select>
      <span class="name">固件版本号</span>
      <el-select v-model="fw">
        <el-option
          v-for="item in fwData"
          :key="item.str_id"
          :label="item.str_val"
          :value="item.str_id">
        </el-option>
      </el-select>
      <el-button type="primary" @click="searchTable">搜索</el-button>
    </div>
    <div class="portHead clearfix">
      <span>{{date}}电量使用分布</span>
      <span class="portDate">
        日期
        <el-date-picker
          v-model="value7"
          type="date"
          align="right"
          :clearable="false"
          :editable="false"
          @change="dateChange()"
          placeholder="选择日期"
          format="yyyy/MM/dd">
        </el-date-picker>
      </span>
    </div>
    <div class="barData">
      <div id="main"></div>
    </div>
    <div class="portHead clearfix">
      数据明细
      <span class="portDate" @click="exportData">
        <el-button type="primary">导出</el-button>
      </span>
    </div>
    <div class="text">
      <el-table
        :data="tableData"
        border
        :header-row-style="headerStyle"
        v-loading="loading"
        element-loading-text="拼命加载中"
        max-height="300"
        style="width: 100%">
        <el-table-column label="序号" width="80px" type="index" align="center">
        </el-table-column>
        <el-table-column
          :prop="item.value"
          v-for="item in tableList"
          :key="item.value"
          align="center"
          :label="item.name">
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>
<script>
  import commonHandle from "../../common/common";
  let hostName = location.host;
  let protoType = location.protocol;
  export default {
    data () {
      return {
        type: "-1",
        hw: "-1",
        fw: "-1",
        value7: new Date().setTime(new Date().getTime() - 3600 * 1000 * 24),
        j: 0,
        date: "日",
        loading: true,
        dialogFormVisible: false,
        disabled: true,
        itemsButton: [
          {
            value: 0,
            name: "日"
          }, {
            value: 1,
            name: "周"
          }, {
            value: 2,
            name: "月"
          }
        ],
        lineName: "",
        lineChartXs: [],
        lineChartYs: [],
        hwData: [],
        fwData: [],
        typeData: [],
        export: {
          type: "-1",
          hw: "-1",
          fw: "-1"
        },
        form: {
          email: "",
          name: "",
          type: "",
          role: ""
        },
        tableList: [
          {
            value: "date",
            name: "日期"
          }, {
            value: "elct_count",
            name: "电量使用数量"
          }, {
            value: "device_count",
            name: "设备数量"
          }, {
            value: "device_percent",
            name: "设备数占比"
          }],
        tableData: [],
        headerStyle: {
          "background-color": "#eef1f6",
          "color": "#1f2d3d"
        }
      };
    },
    computed: {
      param () {
        return {
          model: this.type,
          hw_ver: this.hw,
          fw_ver: this.fw,
          date: commonHandle.getDateStrings(new Date(this.value7)),
          time_veidoo: this.j + 1
        };
      },
      selectParam () {
        return {
          "model": this.type,
          "hardware_version": this.hw
        };
      }
    },
    mounted () {
      // 检查页面权限
      commonHandle.checkMenu("/ota/sys/check_sys_user_page_auth", this, this.$route.path);
      this.querySelect();
      this.drawLineChart();
    },
    methods: {
      searchTable () {
        this.export = {
          type: this.type,
          hw: this.hw,
          fw: this.fw
        };
        // 搜索表格数据
        this.queryTable();
        this.queryBar();
      },
      queryTable () {
        // 查询表格信息
        let fnTable = function (response, self) {
          self.tableData = response.data.result.list;
          self.loading = false;
        };
        commonHandle.requestPost("/ota/stat/outlet/query_elct_data_list", this, this.param, fnTable);
      },
      queryBar () {
        // 查询柱状图
        let params = {
          model: this.type,
          hw_ver: this.hw,
          fw_ver: this.fw,
          date: commonHandle.getDateStrings(new Date(this.value7)),
          time_veidoo: this.j + 1
        };
        let fns = function (response, self) {
          self.lineName = response.data.result.date_name;
          self.lineChartXs = response.data.result.line_charts_x;
          self.lineChartYs = response.data.result.line_charts_y;
          self.drawLineChart();
        };
        commonHandle.requestPost("/ota/stat/outlet/query_elct_data", this, params, fns);
      },
      querySelect () {
        let fn = function (response, self) {
          self.hwData = response.data.result.hw_vers;
          self.fwData = response.data.result.fw_vers;
          self.typeData = response.data.result.models;
          self.queryTable();
          self.queryBar();
        };
        // 查询下拉框信息
        commonHandle.requestPost("/ota/stat/outlet/query_elct_data_need_info", this, this.selectParam, fn);
      },
      querySelectType () {
        this.hw = "-1";
        this.fw = "-1";
        let fn = function (response, self) {
          self.hwData = response.data.result.hw_vers;
          self.fwData = response.data.result.fw_vers;
        };
        // 查询下拉框信息
        commonHandle.requestPost("/ota/stat/outlet/query_elct_data_need_info", this, this.selectParam, fn);
      },
      querySelectHw () {
        this.fw = "-1";
        let fn = function (response, self) {
          self.fwData = response.data.result.fw_vers;
        };
        // 查询下拉框信息
        commonHandle.requestPost("/ota/stat/outlet/query_elct_data_need_info", this, this.selectParam, fn);
      },
      exportData () {
        let start = commonHandle.getDateStrings(new Date(this.value7));
        let model = this.export.type;
        let hwVersion = this.export.hw;
        let fwVersion = this.export.fw;
        let timeVeidoo = this.j + 1;
        let str = "model=" + model + "&hw_ver=" + hwVersion + "&date=" + String(start) + "&fw_ver=" + fwVersion + "&time_veidoo=" + timeVeidoo;
        console.log(str);
        window.open(protoType + "//" + hostName + "/ota/stat/outlet/query_elct_data_list_export?" + str, "_blank");
      },
      toggleButton (index) {
        this.j = index;
        this.date = this.itemsButton[index].name;
        this.queryTable();
        this.queryBar();
      },
      drawLineChart () {
        this.lineChartOrder = this.$echarts.init(document.getElementById("main"));
        this.lineChartOrder.setOption({
          color: ["#71c3ff"],
          backgroundColor: "#fff",
          tooltip: {
            trigger: "axis",
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
              type: "line"        // 默认为直线，可选为："line" | "shadow"
            }
          },
          legend: {
            selectedMode: false,
            data: [this.lineName],
            x: "center",
            orient: "horizontal",
            y: "bottom"
          },
          xAxis: [
            {
              type: "category",
              data: this.lineChartXs
            }
          ],
          yAxis: [
            {
              type: "value",
              axisLine: {
                show: false
              }
            }
          ],
          series: [
            {
              name: this.lineName,
              type: "bar",
              data: this.lineChartYs,
              animationDelay: function (idx) {
                return idx * 10;
              }
            }
          ],
          animationEasing: "elasticOut"
        });
      },
      dateChange (val) {
        this.export = {
          type: this.type,
          hw: this.hw,
          fw: this.fw
        };
        // 搜索表格数据
        this.queryTable();
        this.queryBar();
      }
    }
  };
</script>
<style lang="less">
  .electricWrap{
    min-width: 1280px;
    max-width: 1350px;
    margin-left: 50px;
    .search{
      margin-bottom: 20px;
      .name{
        padding: 0 15px 0 0;
      }
      button{
        margin-left: 20px;
      }
      .el_type, .el-select{
        width: 12%;
      }
    }
    .changeDate{
      margin-bottom: 50px;
      color: #000;
      span{
        cursor: pointer;
        padding: 10px 20px;
        background: #f1f1f1;
      }
      span:hover{
        opacity: 0.6;
      }
      .backgroundbutton{
        color: #fff;
        background-color: #2ba4fe;
      }
    }
    .el-select{
      margin-right: 12%;
    }
    .el-input__inner{
      max-width: 210px;
    }
    #main{
      width: 100%;
      height: 350px;
    }
    .portHead{
      font-weight: bold;
      padding: 10px 0;
      .portDate{
        float: right;
      }
    }
  }
</style>
