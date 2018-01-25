<template>
  <div class="deviceWrap">
    <div class="search">
      <span class="name">产品名称</span>
      <el-select @change="querySelectName" v-model="name">
        <el-option
          v-for="item in nameData"
          :key="item.device_type"
          :label="item.device_type_val"
          :value="item.device_type">
        </el-option>
      </el-select>
      <span class="name">型号</span>
      <el-select @change="querySelectType" v-model="type">
        <el-option
          v-for="item in typeData"
          :key="item.model"
          :label="item.model_val"
          :value="item.model">
        </el-option>
      </el-select>
      <span class="name">硬件版本号</span>
      <el-select @change="querySelectHw" v-model="hwVersion">
        <el-option
          v-for="item in hwData"
          :key="item.hardware_version"
          :label="item.hardware_version_val"
          :value="item.hardware_version">
        </el-option>
      </el-select>
      <span class="name">固件版本号</span>
      <el-select v-model="fwVersion">
        <el-option
          v-for="item in fwData"
          :key="item.rom_version"
          :label="item.rom_version_val"
          :value="item.rom_version">
        </el-option>
      </el-select>
      <span class="name">地区</span>
      <el-select v-model="citys">
        <el-option
          v-for="item in cityData"
          :key="item.city_id"
          :label="item.city_name"
          :value="item.city_id">
        </el-option>
      </el-select>
      <span class="name">手机号</span>
      <el-input v-model="phone" class="el_type" placeholder="请输入手机号"></el-input>
      <el-button type="primary" @click="searchTable">搜索</el-button>
    </div>
    <div class="text">
      <el-table
        :data="tableData"
        border
        :header-row-style="headerStyle"
        v-loading="loading"
        element-loading-text="拼命加载中"
        max-height="500"
        style="width: 100%">
        <el-table-column
          prop="device_type"
          label="产品名称"
          fixed="left"
          align="center"
          width="200">
        </el-table-column>
        <el-table-column
          :prop="item.value"
          v-for="item in tableList"
          :key="item.value"
          align="center"
          :width="item.width"
          :label="item.name">
        </el-table-column>
        <el-table-column width="100" align="center" label="状态">
          <template scope="scope">
            <span :class="{red: 0 === scope.row.online_status}">{{state(scope.row.online_status)}}</span>
          </template>
        </el-table-column>
        <el-table-column fixed="right" width="100" align="center" label="操作">
          <template scope="scope">
            <el-button v-if="scope.row.show" :disabled="noAuthority" type="text" @click="refreshData(scope.$index, scope.row)">刷新</el-button>
            <i v-if="!scope.row.show" class="columnRefresh el-icon-loading"></i>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="page-data">
      <div class="block">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="[10, 20, 30, 50]"
          :page-size="size"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
        </el-pagination>
      </div>
    </div>
  </div>
</template>
<script>
  import commonHandle from "../../common/common";
  export default {
    data () {
      return {
        noAuthority: true,
        hwVersion: "-1",
        fwVersion: "-1",
        type: -1,
        name: -1,
        citys: "",
        phone: "",
        currentPage: 1,
        size: 10,
        total: 0,
        loading: true,
        fwData: {},
        hwData: [],
        nameData: [],
        typeData: [],
        cityData: [],
        tableList: [
          {
            value: "model",
            name: "型号",
            width: "200"
          }, {
            value: "hardware_version",
            name: "硬件版本号",
            width: "200"
          }, {
            value: "rom_version",
            name: "固件版本号",
            width: "200"
          }, {
            value: "device_id",
            name: "Device ID",
            width: "250"
          }, {
            value: "mac",
            name: "MAC地址",
            width: "250"
          }, {
            value: "city_name",
            name: "地区",
            width: "200"
          }, {
            value: "uid",
            name: "绑定用户ID",
            width: "200"
          }, {
            value: "phone_number",
            name: "手机号",
            width: "200"
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
          device_type: this.name,
          model: this.type,
          hardware_version: this.hwVersion,
          rom_version: this.fwVersion,
          city_id: this.citys,
          phone_number: this.phone,
          page_size: this.size,
          cur_page: this.currentPage
        };
      },
      selectParam () {
        return {
          device_type: this.name,
          model: this.type,
          hardware_version: this.hwVersion
        };
      }
    },
    created () {
      // 检查页面权限
      commonHandle.checkMenu("/ota/sys/check_sys_user_page_auth", this, this.$route.path);
      this.querySelect();
    },
    methods: {
      searchTable () {
        this.queryTable();
      },
      handleSizeChange (val) {
        this.size = val;
        this.queryTable();
      },
      handleCurrentChange (val) {
        this.currentPage = val;
        this.queryTable();
      },
      queryTable () {
        // 查询表格信息
        let fnTable = function (response, self) {
          for (let i = 0; i < response.data.result.devices_info.length; i++) {
            response.data.result.devices_info[i].show = true;
          }
          self.tableData = response.data.result.devices_info;
          self.total = response.data.result.total_count;
          self.loading = false;
        };
        commonHandle.requestPost("/ota/devicemgr/device_details/query_devices__info/" + this.currentPage + "/" + this.size, this, this.param, fnTable);
      },
      querySelect () {
        let fn = function (response, self) {
          self.hwData = response.data.result.hardware_versions;
          self.fwData = response.data.result.rom_versions;
          self.cityData = response.data.result.areas;
          self.nameData = response.data.result.device_types;
          self.typeData = response.data.result.models;
          self.citys = self.cityData[0].city_id;
          self.queryTable();
        };
        // 查询下拉框信息
        commonHandle.requestPost("/ota/devicemgr/device_details/query_device_all_type", this, this.selectParam, fn);
      },
      querySelectName () {
        this.hwVersion = "-1";
        this.fwVersion = "-1";
        this.type = -1;
        let fn = function (response, self) {
          self.hwData = response.data.result.hardware_versions;
          self.fwData = response.data.result.rom_versions;
          self.typeData = response.data.result.models;
        };
        // 查询下拉框信息
        commonHandle.requestPost("/ota/devicemgr/device_details/query_device_all_type", this, this.selectParam, fn);
      },
      querySelectType () {
        this.hwVersion = "-1";
        this.fwVersion = "-1";
        let fn = function (response, self) {
          self.hwData = response.data.result.hardware_versions;
          self.fwData = response.data.result.rom_versions;
        };
        // 查询下拉框信息
        commonHandle.requestPost("/ota/devicemgr/device_details/query_device_all_type", this, this.selectParam, fn);
      },
      querySelectHw () {
        this.fwVersion = "-1";
        let fn = function (response, self) {
          self.fwData = response.data.result.rom_versions;
        };
        // 查询下拉框信息
        commonHandle.requestPost("/ota/devicemgr/device_details/query_device_all_type", this, this.selectParam, fn);
      },
      state (value) {
        if (value === 1) {
          return "在线";
        } else {
          return "离线";
        }
      },
      refreshData (index, row) {
        // 查询设备在线状态
        this.tableData[index].show = !this.tableData[index].show;
        let fnTable = function (response, self) {
          self.$message({
            type: "success",
            message: "操作成功!"
          });
          self.tableData[index].show = !self.tableData[index].show;
          self.tableData[index].online_status = response.data.result.online_status;
        };
        commonHandle.requestPost("/ota/devicemgr/device_details/query_devices_online_status", this, {"device_id": row.device_id}, fnTable);
      }
    }
  };
</script>
<style lang="less">
  .deviceWrap{
    min-width: 1280px;
    max-width: 1500px;
    .search{
      margin-bottom: 50px;
      .name{
        padding: 0 10px 0 10px;
      }
      button{
        margin-left: 20px;
      }
      .el_type, .el-select{
        width: 10%;
      }
    }
    .columnRefresh{
      font-size: 18px;
      padding: 10px 20px;
      color: #409EFF;
    }
    .columnRefresh:hover{
      font-weight: bold;
    }
    .red{
      color: red;
    }
    .page-data{
      text-align: center;
      padding: 70px 0;
    }
  }
</style>
