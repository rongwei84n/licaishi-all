<template>
  <div class="macWrap">
    <div class="search">
      <span class="name">产品名称</span>
      <el-select @change="querySelectName" v-model="name">
        <el-option
          v-for="item in nameData"
          :key="item.product_area_id"
          :label="item.product_area_name"
          :value="item.product_area_id">
        </el-option>
      </el-select>
      <span class="name">产品型号</span>
      <el-select @change="querySelectType" v-model="type">
        <el-option
          v-for="item in typeData"
          :key="item.product_line_id"
          :label="item.product_line_name"
          :value="item.product_line_id">
        </el-option>
      </el-select>
      <span class="name">产品料号</span>
      <el-select v-model="company">
        <el-option
          v-for="item in companyData"
          :key="item.part_number_id"
          :label="item.part_number_value"
          :value="item.part_number_id">
        </el-option>
      </el-select>
      <span class="name">MAC地址</span>
      <el-input v-model="mac" class="el_type" placeholder="请输入MAC地址"></el-input>
      <el-button type="primary" @click="searchTable">搜索</el-button>
    </div>
    <div class="import">
      <el-button type="primary" @click="importData" :disabled="noAuthority">添加DeviceID</el-button>
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
          :prop="item.value"
          v-for="item in tableList"
          :key="item.value"
          align="center"
          :width="item.width"
          :label="item.name">
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
        title=""
        :visible.sync="importVisible"
        width="570px"
        :close-on-click-modal="false"
        :before-close="handleClose">
        <el-form ref="form" :model="form" label-width="100px">
          <el-form-item label="产品名称" label-width="100px">
            <el-select  @change="queryFormName" placeholder="必填项" v-model="form.name">
              <el-option
                v-for="item in formNameData"
                :key="item.product_area_id"
                :label="item.product_area_name"
                :value="item.product_area_id">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="产品型号" label-width="100px">
            <el-select placeholder="必填项" v-model="form.type">
              <el-option
                v-for="item in formTypeData"
                :key="item.product_line_id"
                :label="item.product_line_name"
                :value="item.product_line_id">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="MAC地址">
            <el-select v-model="form.company" @change="macChange">
              <el-option
                v-for="item in macData"
                :key="item.value"
                :label="item.name"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item v-show="macShow" label="" :label-width="formLabelWidth">
            <el-input
              placeholder="请输入MAC地址"
              type="textarea"
              :rows="2"
              v-model="form.macSingle">
            </el-input>
          </el-form-item>
          <el-form-item v-show="macsShow" label="" :label-width="formLabelWidth">
            <div class="macAll">
              <el-input
                placeholder="请输入MAC起始地址"
                v-model="form.macOne">
              </el-input>
              <span>-</span>
              <el-input
                placeholder="请输入MAC结束地址"
                v-model="form.macTwo">
              </el-input>
            </div>
          </el-form-item>
          <el-form-item v-show="uploadShow" label="" label-width="100px">
            <el-popover
              ref="popover1"
              placement="right-start"
              title="Excel模板"
              trigger="hover">
              <el-row>
                <el-col>
                  <el-card :body-style="{ padding: '0px', color: '#f00'}">
                    <div style="padding: 14px;">
                      <span>注：文件内容不要超过1000个MAC地址</span>
                    </div>
                    <img src="../../../assets/img/macAddress.png" class="image">
                  </el-card>
                </el-col>
              </el-row>
            </el-popover>
            <span class="template" v-popover:popover1>Excel模板</span>
            <el-upload
              class="upload-demo"
              ref="upload"
              drag
              :data="dataParamApk"
              :action= "actionUpload()"
              :file-list="fileListApk"
              :on-change="importChange"
              :on-success="successdata"
              :on-error="errorData"
              :auto-upload="false">
              <i class="el-icon-upload"></i>
              <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
            </el-upload>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="importCancel">取 消</el-button>
          <el-button type="primary"
             element-loading-text="上传文件中，请等待"
             v-loading.fullscreen.lock="fullscreenLoading"
             element-loading-spinner="el-icon-loading"
             element-loading-background="rgba(0, 0, 0, 0.8)"
             @click="importConfirm()">确 定</el-button>
        </span>
      </el-dialog>
    </div>
    <div>
      <el-dialog
      title="提示"
      :visible.sync="messageVisible"
      width="30%">
      <span class="message-data">{{message}}</span>
    </el-dialog>
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
        noAuthority: true,
        fullscreenLoading: false,
        company: -1,
        type: -1,
        name: -1,
        mac: "",
        address: "",
        message: "",
        currentPage: 1,
        size: 10,
        total: 0,
        macShow: false,
        macsShow: false,
        uploadShow: false,
        loading: true,
        importVisible: false,
        messageVisible: false,
        dataParamApk: {},
        companyData: [],
        nameData: [],
        typeData: [],
        formNameData: [],
        formTypeData: [],
        macData: [
          {
            value: 1,
            name: "输入MAC地址"
          },
          {
            value: 2,
            name: "MAC地址段"
          },
          {
            value: 3,
            name: "导入Excel表格"
          }
        ],
        fileListApk: [],
        tableList: [
          {
            value: "device_id",
            name: "Device ID",
            width: "300"
          },
          {
            value: "part_number",
            name: "产品料号",
            width: "298"
          }, {
            value: "product_area_name",
            name: "产品名称",
            width: "200"
          }, {
            value: "product_line_name",
            name: "产品型号",
            width: "200"
          }, {
            value: "hw_ver",
            name: "硬件版本号",
            width: "200"
          }, {
            value: "mac",
            name: "MAC地址",
            width: "300"
          }],
        tableData: [],
        form: {
          company: 1,
          name: "",
          type: "",
          macSingle: "",
          macOne: "",
          macTwo: ""
        },
        headerStyle: {
          "background-color": "#eef1f6",
          "color": "#1f2d3d"
        }
      };
    },
    computed: {
      param () {
        let familyName = "";
        let areaName = "";
        let lineName = "";
        for (let i = 0; i < this.companyData.length; i++) {
          if (this.companyData[i].part_number_id === this.company) {
            familyName = this.companyData[i].part_number_value;
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
        return {
          part_number_id: this.company,
          part_number_value: familyName,
          product_area_id: this.name,
          product_area_name: areaName,
          product_line_id: this.type,
          product_line_name: lineName,
          mac: this.mac,
          page_size: this.size,
          cur_page: this.currentPage
        };
      },
      selectParam () {
        return {
          "product_area_id": this.name,
          "product_line_id": this.type,
          "hw_ver": "",
          "part_number": this.company
        };
      }
    },
    created () {
      // 检查页面权限
      commonHandle.checkMenu("/ota/sys/check_sys_user_page_auth", this, this.$route.path);
      this.querySelect();
      this.queryTable();
    },
    methods: {
      searchTable () {
        this.queryTable();
      },
      importData () {
        this.form = {
          company: 1,
          name: "",
          type: "",
          macSingle: "",
          macOne: "",
          macTwo: ""
        };
        this.macShow = true;
        this.macsShow = false;
        this.uploadShow = false;
        if (this.$refs.upload && this.$refs.upload.uploadFiles !== null && this.$refs.upload.uploadFiles !== undefined) {
          this.$refs.upload.uploadFiles.shift();
        }
        let param = {
          "product_area_id": -1,
          "product_line_id": -1,
          "hw_ver": "",
          "part_number": -1
        };
        let fn = function (response, self) {
          self.formNameData = response.data.result.areas;
          self.formTypeData = response.data.result.lines;
        };
        // 查询下拉框信息
        commonHandle.requestPost("/ota/query_product_info", this, param, fn);
        this.importVisible = true;
      },
      handleSizeChange (val) {
        this.size = val;
        this.queryTable();
      },
      handleCurrentChange (val) {
        this.currentPage = val;
        this.queryTable();
      },
      macChange (val) {
        var _this = this;
        switch (val) {
          case 1:
            _this.macShow = true;
            _this.macsShow = false;
            _this.uploadShow = false;
            break;
          case 2:
            _this.macShow = false;
            _this.macsShow = true;
            _this.uploadShow = false;
            break;
          default:
            _this.macShow = false;
            _this.macsShow = false;
            _this.uploadShow = true;
        };
      },
      queryTable () {
        // 查询表格信息
        let fnTable = function (response, self) {
          for (let i = 0; i < response.data.result.page_info.list.length; i++) {
            // 处理返回时间格式
            response.data.result.page_info.list[i].op_time = commonHandle.getHoursStrings(new Date(response.data.result.page_info.list[i].op_time * 1000));
          }
          self.tableData = response.data.result.page_info.list;
          self.total = response.data.result.page_info.total;
          self.loading = false;
        };
        commonHandle.requestPost("/deviceidmgr/get_all_mac/" + this.currentPage + "/" + this.size, this, this.param, fnTable);
      },
      querySelect () {
        let fn = function (response, self) {
          self.companyData = response.data.result.part_number;
          self.nameData = response.data.result.areas;
          self.typeData = response.data.result.lines;
          self.formNameData = response.data.result.areas;
          self.formTypeData = response.data.result.lines;
          self.companyData.unshift({part_number_id: -1, part_number_value: "全部"});
          self.nameData.unshift({product_area_id: -1, product_area_name: "全部"});
          self.typeData.unshift({product_line_id: -1, product_line_name: "全部"});
        };
        // 查询下拉框信息
        commonHandle.requestPost("/ota/query_product_info", this, this.selectParam, fn);
      },
      querySelectName (val) {
        this.type = -1;
        this.company = -1;
        let fn = function (response, self) {
          self.typeData = response.data.result.lines;
          self.companyData = response.data.result.part_number;
          self.typeData.unshift({product_line_id: -1, product_line_name: "全部"});
          self.companyData.unshift({part_number_id: -1, part_number_value: "全部"});
        };
        // 产品名称联动
        commonHandle.requestPost("/ota/query_product_info", this, this.selectParam, fn);
      },
      querySelectType () {
        this.company = -1;
        let fn = function (response, self) {
          self.companyData = response.data.result.part_number;
          self.companyData.unshift({part_number_id: -1, part_number_value: "全部"});
        };
        // 产品型号联动
        commonHandle.requestPost("/ota/query_product_info", this, this.selectParam, fn);
      },
      queryFormName () {
        this.form.type = "";
        let param = {
          "product_area_id": this.form.name,
          "product_line_id": this.form.type,
          "hw_ver": "",
          "part_number": ""
        };
        let fn = function (response, self) {
          self.formTypeData = response.data.result.lines;
        };
        // 弹出框产品联动
        commonHandle.requestPost("/ota/query_product_info", this, param, fn);
      },
      actionUpload () {
        return protoType + "//" + hostName + "/deviceidmgr/add_excel_mac";
      },
      handleClose (done) {
        if (this.$refs.upload && this.$refs.upload.uploadFiles !== null && this.$refs.upload.uploadFiles !== undefined) {
          this.$refs.upload.uploadFiles.shift();
        }
        done();
      },
      importCancel () {
        this.importVisible = false;
        if (this.$refs.upload && this.$refs.upload.uploadFiles !== null && this.$refs.upload.uploadFiles !== undefined) {
          this.$refs.upload.uploadFiles.shift();
        }
      },
      importConfirm () {
        let company = this.form.company;
        let name = this.form.name;
        let type = this.form.type;
        let macSingle = this.form.macSingle;
        let macOne = this.form.macOne;
        let macTwo = this.form.macTwo;
        let length = this.$refs.upload.uploadFiles.length;
        let areaName = "";
        let lineName = "";
        for (let i = 0; i < this.nameData.length; i++) {
          if (this.nameData[i].product_area_id === name) {
            areaName = this.nameData[i].product_area_name;
          }
        }
        for (let i = 0; i < this.typeData.length; i++) {
          if (this.typeData[i].product_line_id === type) {
            lineName = this.typeData[i].product_line_name;
          }
        }
        this.dataParamApk.product_area_id = name;
        this.dataParamApk.product_area_name = areaName;
        this.dataParamApk.product_line_id = type;
        this.dataParamApk.product_line_name = lineName;
        this.dataParamApk.userName = localStorage.getItem("userName");
        this.dataParamApk.lastOptUid = localStorage.getItem("uid");
        this.dataParamApk.token = localStorage.getItem("token");
        this.dataParamApk.real_name = localStorage.getItem("real_name");
        let temp = /^([a-fA-F0-9]{2}:){5}[a-fA-F0-9]{2}$/;
        if (company === 1) {
          if (!company || !name || !type || !macSingle) {
            this.$message.error("请把内容填写完整");
            return;
          }
          if (macSingle && !temp.test(macSingle)) {
            this.$message.error("请填写正确的MAC地址");
            return;
          }
          let param = {};
          let params = {mac: macSingle.toUpperCase()};
          Object.assign(param, this.dataParamApk, params);
          let fn = function (response, self) {
            self.$message({
              message: "操作成功",
              type: "success"
            });
          };
          // 单个录入
          commonHandle.requestPost("/deviceidmgr/add_single_mac", this, param, fn);
        } else if (company === 2) {
          if (!company || !name || !type || !macOne || !macTwo) {
            this.$message.error("请把内容填写完整");
            return;
          }
          if ((macOne && !temp.test(macOne)) || (macTwo && !temp.test(macTwo))) {
            this.$message.error("请填写正确的MAC地址");
            return;
          }
          let param = {};
          let params = {start_mac: macOne.toUpperCase(), end_mac: macTwo.toUpperCase()};
          Object.assign(param, this.dataParamApk, params);
          let fn = function (response, self) {
            self.$message({
              message: "操作成功",
              type: "success"
            });
          };
          // 地址段的录入
          commonHandle.requestPost("/deviceidmgr/add_serial_mac", this, param, fn);
        } else {
          if (!company || !name || !type || length <= 0) {
            this.$message.error("请把内容填写完整");
            return;
          }
          // 文件提交
          this.$refs.upload.submit();
          this.fullscreenLoading = true;
        }
        this.importVisible = false;
      },
      importChange (file, fileList) {
        let fileArray = this.$refs.upload.uploadFiles;
        if (fileArray.length > 1) {
          fileArray.shift();
        }
        if (fileArray.length !== 0 && fileArray[0].name.slice(-4) !== ".xls" && fileArray[0].name.slice(-5) !== ".xlsx") {
          this.$message({
            message: "该文件不是Excel文件，请重新选择",
            type: "warning"
          });
          fileArray.shift();
        }
        console.log(fileArray);
      },
      successdata (response, file, fileList) {
        let fn = function (response, self) {
          if (response.result.invalid_macs && response.result.invalid_macs.length > 0) {
            self.messageVisible = true;
            self.message = "导入部分成功，不成功的MAC地址有" + response.result.invalid_macs.length + "个，分别为：" + response.result.invalid_macs.join();
          } else {
            self.$message({
              message: "导入成功",
              type: "success"
            });
          }
          self.queryTable();
        };
        if (this.$refs.upload && this.$refs.upload.uploadFiles !== null && this.$refs.upload.uploadFiles !== undefined) {
          this.$refs.upload.uploadFiles.shift();
        }
        commonHandle.requestOK(response, this, fn);
        this.fullscreenLoading = false;
      },
      errorData (err, file, fileList) {
        if (this.$refs.upload && this.$refs.upload.uploadFiles !== null && this.$refs.upload.uploadFiles !== undefined) {
          this.$refs.upload.uploadFiles.shift();
        }
        commonHandle.requestErr(err, this);
        this.fullscreenLoading = false;
      }
    }
  };
</script>
<style lang="less">
  .macWrap{
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
        width: 12%;
      }
    }
    .import{
      margin-bottom: 10px;
    }
    .import-data{
      .el-input{
        width: 200px;
      }
    }
    .template{
      color: blue;
      cursor: pointer;
    }
    .message-data{
      width: 500px;
      max-height: 500px;
      display: inline-block;
      overflow: auto;
      word-wrap: break-word;
    }
    .page-data{
      text-align: center;
      padding: 70px 0;
    }
  }
</style>
