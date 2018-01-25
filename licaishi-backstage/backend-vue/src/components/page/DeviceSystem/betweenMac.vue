<template>
  <div class="betweenWrap">
    <div class="title">MAC段地址录入</div>
    <div class="search">
      <span class="name">公司名称</span>
      <el-select v-model="company">
        <el-option
          v-for="item in companyData"
          :key="item.product_family_id"
          :label="item.product_family_name"
          :value="item.product_family_id">
        </el-option>
      </el-select>
      <span class="name">产品名称</span>
      <el-select v-model="name">
        <el-option
          v-for="item in nameData"
          :key="item.product_area_id"
          :label="item.product_area_name"
          :value="item.product_area_id">
        </el-option>
      </el-select>
      <span class="name">型号</span>
      <el-select v-model="type">
        <el-option
          v-for="item in typeData"
          :key="item.product_line_id"
          :label="item.product_line_name"
          :value="item.product_line_id">
        </el-option>
      </el-select>
    </div>
    <div class="singleText">
      <div class="tips">（MAC样例：DC:A9:04:85:57:45，连续MAC区间不要超过1万，如果超过请分段录入）</div>
      <span class="macTitle">MAC区间</span>
      <el-input
        size="medium"
        placeholder="请输入起始MAC地址"
        v-model="startAddress">
      </el-input>
      <span class="macSplit">------</span>
      <el-input
        size="medium"
        placeholder="请输入结尾MAC地址"
        v-model="endAddress">
      </el-input>
      <i v-show="successShow" class="el-icon-success right"></i>
      <div class="tips" v-show="errorShow">注：格式不正确，请输入正确的MAC地址</div>
      <div class="button">
        <el-button type="primary" @click="searchTable" :disabled="noAuthority">录入</el-button>
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
        company: "",
        type: "",
        name: "",
        startAddress: "",
        endAddress: "",
        companyData: [],
        nameData: [],
        typeData: []
      };
    },
    computed: {
      successShow () {
        // 校验Mac地址6位，例如00:00:00:00:00:00
        let temp = /^([a-fA-F0-9]{2}:){5}[a-fA-F0-9]{2}$/;
        if ((this.startAddress && !temp.test(this.startAddress)) || (this.endAddress && !temp.test(this.endAddress))) {
          return false;
        } else if (this.startAddress && this.endAddress) {
          return true;
        }
      },
      errorShow () {
        let temp = /^([a-fA-F0-9]{2}:){5}[a-fA-F0-9]{2}$/;
        if ((this.startAddress && !temp.test(this.startAddress)) || (this.endAddress && !temp.test(this.endAddress))) {
          return true;
        } else {
          return false;
        }
      }
    },
    created () {
      // 检查页面权限
      commonHandle.checkMenu("/ota/sys/check_sys_user_page_auth", this, this.$route.path);
      let fn = function (response, self) {
        self.companyData = response.data.result.families;
        self.nameData = response.data.result.areas;
        self.typeData = response.data.result.lines;
      };
      // 查询下拉框信息
      commonHandle.requestPost("/ota/query_addfirmware_info", this, {}, fn);
    },
    methods: {
      searchTable () {
        if (this.company === "" || !this.name || !this.type || !this.startAddress || !this.endAddress) {
          this.$message.error("请把内容填写完整");
          return;
        }
        if (this.errorShow) {
          this.$message.error("请填写正确的MAC地址");
          return;
        }
        let fn = function (response, self) {
          self.company = "";
          self.type = "";
          self.name = "";
          self.$message({
            message: "录入成功",
            type: "success"
          });
        };
        let familyName = "";
        let areaName = "";
        let lineName = "";
        for (let i = 0; i < this.companyData.length; i++) {
          if (this.companyData[i].product_family_id === this.company) {
            familyName = this.companyData[i].product_family_name;
          }
        }
        for (let i = 0; i < this.nameData.length; i++) {
          if (this.nameData[i].product_area_id === this.name) {
            areaName = this.nameData[i].product_area_name;
          }
        }
        for (let i = 0; i < this.typeData.length; i++) {
          if (this.typeData[i].product_line_id === this.type) {
            lineName = this.typeData[i].product_line_name;
          }
        }
        let param = {
          product_family_id: this.company,
          product_family_name: familyName,
          product_area_id: this.name,
          product_area_name: areaName,
          product_line_id: this.type,
          product_line_name: lineName,
          start_mac: this.startAddress.toUpperCase(),
          end_mac: this.endAddress.toUpperCase()
        };
        // 录入信息
        commonHandle.requestPost("/deviceidmgr/add_serial_mac", this, param, fn);
      }
    }
  };
</script>
<style lang="less">
  .betweenWrap{
    width: 70%;
    min-width: 1040px;
    border: 1px solid rgb(84, 92, 100);
    .title{
      font-size: 20px;
      height: 45px;
      line-height: 45px;
      margin-bottom: 60px;
      background-color: rgb(84, 92, 100);
      color: rgb(255, 208, 75);
      padding-left: 10px;
      border-bottom: 1px solid rgb(84, 92, 100);
    }
    .search{
      .name{
        padding: 0 15px 0 40px;
      }
      button{
        margin-left: 50px;
      }
    }
    .singleText{
      margin-top: 50px;
      .macTitle{
        padding: 0 10px 0 40px;
      }
      .macSplit{
        padding: 0 20px;
      }
      .el-input{
        width: 35%;
      }
      .button{
        width: 100%;
        margin: 60px 0 50px 0;
        text-align: center;
      }
    }
    .tips{
      padding: 10px 0  0 130px;
      font-size: 14px;
      color: #fa5555;
    }
    .right{
      color: #67c23a;
      padding-left: 10px;
      font-size: 20px;
    }
  }
</style>
