<template>
  <div class="phicommWrap">
    <div class="search">
      <span class="name">手机号</span>
      <el-input v-model="phone" class="el_type" placeholder="请输入手机号"></el-input>
      <span class="name">职业</span>
      <el-select v-model="job">
        <el-option
          v-for="item in jobData"
          :key="item.job"
          :label="item.job_val"
          :value="item.job">
        </el-option>
      </el-select>
      <span class="name">性别</span>
      <el-select v-model="sex">
        <el-option
          v-for="item in sexData"
          :key="item.value"
          :label="item.name"
          :value="item.value">
        </el-option>
      </el-select>
      <span class="name">生日</span>
      <el-date-picker
        v-model="birthDay"
        type="daterange"
        format="yyyy/MM/dd"
        start-placeholder="开始日期"
        end-placeholder="结束日期"
        :clearable="false"
        :editable="false">
      </el-date-picker>
      <span class="name">注册时间</span>
      <el-date-picker
        v-model="sign"
        format="yyyy/MM/dd"
        start-placeholder="开始日期"
        end-placeholder="结束日期"
        :clearable="false"
        :editable="false"
        type="daterange">
      </el-date-picker>
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
        <el-table-column label="序号" width="80px" type="index" align="center">
        </el-table-column>
        <el-table-column
          :prop="item.value"
          v-for="item in tableList"
          :key="item.value"
          align="center"
          :label="item.name">
        </el-table-column>
        <el-table-column width="150" align="center" label="绑定设备数量">
          <template scope="scope">
            <el-button type="text" @click="deviceClick(scope.row)">{{scope.row.bind_amount}}</el-button>
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
    <div class="import-data">
      <el-dialog
        :title="title"
        :visible.sync="importVisible"
        width="1200px"
        :close-on-click-modal="false">
        <el-table
          :data="deviceData"
          border
          :header-row-style="headerStyle"
          v-loading="loading"
          element-loading-text="拼命加载中"
          max-height="500"
          style="width: 100%">
          <el-table-column
            :prop="item.value"
            v-for="item in deviceList"
            :key="item.value"
            align="center"
            :label="item.name">
          </el-table-column>
          <el-table-column width="100" align="center" label="状态">
            <template scope="scope">
              <span :class="{red: 0 === scope.row.online_status}">{{state(scope.row.online_status)}}</span>
            </template>
          </el-table-column>
        </el-table>
        <span slot="footer" class="dialog-footer">
          <el-button @click="importCancel">取 消</el-button>
          <el-button type="primary" @click="importConfirm()">确 定</el-button>
        </span>
      </el-dialog>
    </div>
  </div>
</template>
<script>
  import commonHandle from "../../common/common";
  export default {
    data () {
      return {
        noAuthority: true,
        title: "",
        phone: "",
        job: "",
        sex: "",
        birthDay: "",
        sign: "",
        currentPage: 1,
        size: 10,
        total: 0,
        loading: true,
        importVisible: false,
        jobData: [],
        sexData: [
          {
            value: "-1",
            name: "全部"
          },
          {
            value: "1",
            name: "男"
          },
          {
            value: "2",
            name: "女"
          }
        ],
        tableList: [
          {
            value: "uid",
            name: "用户ID"
          }, {
            value: "phone_number",
            name: "手机号"
          }, {
            value: "nick_name",
            name: "昵称"
          }, {
            value: "birthday",
            name: "生日"
          }, {
            value: "sex",
            name: "性别"
          }, {
            value: "job",
            name: "职业"
          }, {
            value: "register_time",
            name: "注册时间"
          }],
        deviceList: [
          {
            value: "device_type",
            name: "产品名称"
          }, {
            value: "model",
            name: "型号"
          }, {
            value: "hardware_version",
            name: "硬件版本号"
          }, {
            value: "rom_version",
            name: "固件版本号"
          }, {
            value: "device_id",
            name: "Device ID"
          }, {
            value: "mac",
            name: "MAC地址"
          }, {
            value: "city_name",
            name: "地区"
          }],
        tableData: [],
        deviceData: [],
        headerStyle: {
          "background-color": "#eef1f6",
          "color": "#1f2d3d"
        }
      };
    },
    computed: {
      param () {
        return {
          phone_number: this.phone,
          sex: this.sex,
          job: this.job,
          birthday_start: commonHandle.getDateStrings(new Date(String(this.birthDay[0]))),
          birthday_end: commonHandle.getDateStrings(new Date(String(this.birthDay[1]))),
          register_start: commonHandle.getDateStrings(new Date(String(this.sign[0]))),
          register_end: commonHandle.getDateStrings(new Date(String(this.sign[1]))),
          product_type_id: 1000,
          page_size: this.size,
          cur_page: this.currentPage
        };
      }
    },
    created () {
      // 检查页面权限
      commonHandle.checkMenu("/ota/sys/check_sys_user_page_auth", this, this.$route.path);
      let fn = function (response, self) {
        self.jobData = response.data.result.jobs;
      };
      // 查询下拉框信息
      commonHandle.requestPost("/ota/usermgr/user_detail/get_query_term", this, {}, fn);
      this.queryTable();
    },
    methods: {
      searchTable () {
        this.queryTable();
      },
      deviceClick (row) {
        this.title = row.nick_name + "的设备";
        this.importVisible = true;
        this.queryDeviceTable(row.uid);
      },
      state (value) {
        if (value === 1) {
          return "在线";
        } else {
          return "离线";
        }
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
          self.tableData = response.data.result.userList;
          self.total = response.data.result.total_count;
          self.loading = false;
        };
        commonHandle.requestPost("/ota/usermgr/user_detail/query_user_info/" + this.currentPage + "/" + this.size, this, this.param, fnTable);
      },
      queryDeviceTable (id) {
        // 查询表格信息
        let params = {
          "uid": id,
          "product_type_id": 1000
        };
        let fnTable = function (response, self) {
          self.deviceData = response.data.result.devices_info;
          self.loading = false;
        };
        commonHandle.requestPost("/ota/usermgr/user_detail/query_user_bind_info/", this, params, fnTable);
      },
      importCancel () {
        this.importVisible = false;
      },
      importConfirm () {
        this.importVisible = false;
      }
    }
  };
</script>
<style lang="less">
  .phicommWrap{
    min-width: 1280px;
    max-width: 1500px;
    .search{
      margin-bottom: 50px;
      .name{
        padding: 0 10px 0 15px;
      }
      button{
        margin-left: 20px;
      }
      .el_type, .el-select{
        width: 150px;
      }
      .el-date-editor{
        width: 210px;
      }
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
