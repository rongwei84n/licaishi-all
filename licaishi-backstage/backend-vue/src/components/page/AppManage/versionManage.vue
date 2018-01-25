<template>
  <div class="version-manage">
    <div class="changeDate">
      <span v-for="(item,index) in itemsButton" :class="{backgroundbutton: j==index}" @click="toggleButton(index)">{{item.name}}</span>
    </div>
      <div v-show="j==0" class="update">
        <div class="search">
          <span class="name">平台</span>
          <el-select v-model="os">
            <el-option
              v-for="item in osData"
              :key="item.ostype"
              :label="item.ostype_val"
              :value="item.ostype">
            </el-option>
          </el-select>
          <span class="name">新版本号</span>
          <el-select v-model="version">
            <el-option
              v-for="item in versionData"
              :key="item.version"
              :label="item.version_val"
              :value="item.version">
            </el-option>
          </el-select>
          <span class="name">升级方式</span>
          <el-select v-model="type">
            <el-option
              v-for="item in updateData"
              :key="item.upgrade_type_id"
              :label="item.upgrade_type_name"
              :value="item.upgrade_type_id">
            </el-option>
          </el-select>
          <el-button type="primary" @click="searchTable">搜索</el-button>
        </div>
        <div class="add">
          <el-button type="primary" @click="addData" :disabled="noAuthority" v-popover:popover1>+添加升级</el-button>
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
            <el-table-column width="100" align="center" label="操作">
              <template scope="scope">
                <el-button
                  size="small"
                  type="text"
                  :disabled="noAuthority"
                  @click="handleEdit(scope.$index, scope.row, tableData)">编辑</el-button>
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
            title=""
            :visible.sync="importVisible"
            width="570px"
            :close-on-click-modal="false">
            <el-form :model="form" label-width="100px">
              <el-form-item label="平台">
                <el-select v-model="form.os" placeholder="必填项">
                  <el-option
                    v-for="item in osData"
                    :key="item.ostype"
                    :label="item.ostype_val"
                    :value="item.ostype">
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="新版本号">
                <el-select v-model="form.version" placeholder="必填项">
                  <el-option
                    v-for="item in versionData"
                    :key="item.version"
                    :label="item.version_val"
                    :value="item.version">
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="升级方式">
                <el-select v-model="form.type" placeholder="必填项">
                  <el-option
                    v-for="item in updateData"
                    :key="item.upgrade_type_id"
                    :label="item.upgrade_type_name"
                    :value="item.upgrade_type_id">
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="支持版本">
                <el-select multiple v-model="form.support" placeholder="必填项">
                  <el-option
                    v-for="item in supportData"
                    :key="item.version"
                    :label="item.version_val"
                    :value="item.version">
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="备注">
                <el-input
                  type="textarea"
                  :rows="3"
                  placeholder="请输入内容"
                  v-model="form.textarea">
                </el-input>
              </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
            <el-button @click="formCancel">取 消</el-button>
            <el-button type="primary" @click="formConfirm">确 定</el-button>
          </span>
          </el-dialog>
        </div>
      </div>
      <div v-show="j==1" class="apk">
        <div class="search">
          <span class="name">渠道号</span>
          <el-select v-model="channel">
            <el-option
              v-for="item in channelData"
              :key="item.channel_number"
              :label="item.channel_number_val"
              :value="item.channel_number">
            </el-option>
          </el-select>
          <span class="name">渠道名称</span>
          <el-select v-model="channelName">
            <el-option
              v-for="item in channelNameData"
              :key="item.channel_name"
              :label="item.channel_name_val"
              :value="item.channel_name">
            </el-option>
          </el-select>
          <span class="name">版本号</span>
          <el-select v-model="apkVersion">
            <el-option
              v-for="item in apkVersionData"
              :key="item.version"
              :label="item.version_val"
              :value="item.version">
            </el-option>
          </el-select>
          <span class="name">更新时间</span>
          <el-date-picker
            v-model="apkTime"
            :editable="false"
            :clearable="false"
            align="left"
            type="datetimerange"
            placeholder="选择时间范围">
          </el-date-picker>
          <el-button type="primary" @click="apkSearchTable">搜索</el-button>
        </div>
        <div class="add">
          <el-button type="primary" @click="apkAddData" :disabled="noAuthority">+添加安装包</el-button>
        </div>
        <el-table
          :data="apkTableData"
          border
          :header-row-style="headerStyle"
          v-loading="apkLoading"
          element-loading-text="拼命加载中"
          max-height="500"
          style="width: 100%">
          <el-table-column label="序号" width="80px" type="index" align="center">
          </el-table-column>
          <el-table-column
            v-for="item in items"
            :key="item.id"
            :prop="item.value"
            :label="item.name"
            align="center">
          </el-table-column>
          <el-table-column align="center" width="150" label="操作">
            <template scope="scope">
              <el-button
                size="small"
                type="text"
                :disabled="noAuthority"
                @click="apkHandleEdit(scope.$index, scope.row, tableData)">编辑</el-button>
              <el-button
                size="small"
                type="text"
                :disabled="noAuthority"
                @click="downLoadApk(scope.row)">下载安装包</el-button>
            </template>
          </el-table-column>
        </el-table>
        <div class="page-data">
          <div class="block">
            <el-pagination
              @size-change="apkHandleSizeChange"
              @current-change="apkHandleCurrentChange"
              :current-page="apkCurrentPage"
              :page-sizes="[10, 20, 30, 50]"
              :page-size="apkSize"
              layout="total, sizes, prev, pager, next, jumper"
              :total="totalDatas">
            </el-pagination>
          </div>
        </div>
        <div class="dia-data">
          <el-dialog width="570px" :close-on-click-modal="false" :before-close="apkHandleClose" :visible.sync="dialogFormVisible">
            <el-form :model="apkForm">
              <el-form-item label="渠道名称" :label-width="formLabelWidth">
                <el-select v-model="apkForm.channel" placeholder="">
                  <el-option v-for="item in formChannelData" :key="item.channel_number" :label="item.channel_name" :value="item.channel_number"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="版本号" :label-width="formLabelWidth">
                <el-input v-model="apkForm.version" auto-complete="off"></el-input>
              </el-form-item>
              <el-form-item label="更新时间" :label-width="formLabelWidth">
                <el-date-picker
                  v-model="apkForm.updateTimeStr"
                  type="datetime"
                  :editable="false"
                  :clearable="false"
                  placeholder="选择日期时间"
                  align="right"
                  :picker-options="pickerOptions1">
                </el-date-picker>
              </el-form-item>
              <el-form-item label="更新内容(内部)" :label-width="formLabelWidth">
                <el-input v-model="apkForm.inside" type="textarea" :rows="2" auto-complete="off"></el-input>
              </el-form-item>
              <el-form-item label="更新内容(外部)" :label-width="formLabelWidth">
                <el-input v-model="apkForm.outside" type="textarea" :rows="2" auto-complete="off"></el-input>
              </el-form-item>
              <el-form-item label="APP安装包" :label-width="formLabelWidth">
                <el-upload
                  class="upload-demo"
                  ref="upload"
                  drag
                  :data="dataParam"
                  :action= "apkActionUpload()"
                  :file-list="fileList"
                  :on-change="apkHandleChange"
                  :on-success="apkSuccessdata"
                  :on-error="apkErrorData"
                  :auto-upload="false">
                  <i class="el-icon-upload"></i>
                  <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                </el-upload>
              </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
              <el-button @click="apkFormCancel()">取 消</el-button>
              <el-button type="primary" element-loading-text="上传文件中，请等待" v-loading.fullscreen.lock="fullscreenLoading" @click="apkFormConfirm()">确 定</el-button>
            </div>
          </el-dialog>
        </div>
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
        // 升级管理数据
        activeName: "update",
        type: "",
        id: "",
        os: "",
        j: 0,
        version: "",
        support: "",
        editType: "",
        hardwareVersion: "",
        currentPage: 1,
        size: 10,
        total: 0,
        loading: true,
        importVisible: false,
        supportData: [],
        updateData: [],
        osData: [],
        versionData: [],
        itemsButton: [
          {
            value: 0,
            name: "升级管理"
          }, {
            value: 1,
            name: "渠道安装包"
          }
        ],
        tableList: [
          {
            value: "ostype_name",
            name: "平台",
            width: "200"
          }, {
            value: "version",
            name: "新版本号",
            width: "200"
          }, {
            value: "upgrade_type_name",
            name: "升级方式",
            width: "200"
          }, {
            value: "old_version",
            name: "支持版本",
            width: "290"
          }, {
            value: "note",
            name: "备注",
            width: "290"
          }, {
            value: "update_time",
            name: "操作时间",
            width: "290"
          }],
        tableData: [],
        form: {
          os: "",
          version: "",
          type: "",
          support: [],
          textarea: ""
        },
        headerStyle: {
          "background-color": "#eef1f6",
          "color": "#1f2d3d"
        },
        // 渠道安装包数据
        apkId: "",
        channel: "",
        apkVersion: "",
        channelName: "",
        apkTime: "",
        totalDatas: 0,
        apkCurrentPage: 1,
        apkSize: 10,
        dialogFormVisible: false,
        fullscreenLoading: false,
        formLabelWidth: "120px",
        apkEditType: "add",
        // 渠道安装包表格表头
        items: [
          {"name": "渠道号", "value": "channel_number"},
          {"name": "渠道名称", "value": "channel_name"},
          {"name": "版本号", "value": "version"},
          {"name": "更新时间", "value": "update_time"},
          {"name": "更新内容（内部）", "value": "inter_note"},
          {"name": "更新内容（外部）", "value": "exter_note"},
          {"name": "操作时间", "value": "create_time"}
        ],
        // 渠道安装包表格数据绑定
        apkForm: {
          "channel": "",
          "version": "",
          "updateTimeStr": "",
          "inside": "",
          "outside": ""
        },
        formChannelData: [],
        apkTableData: [],
        apkLoading: true,
        channelData: [],
        channelNameData: [],
        apkVersionData: [],
        dataParam: {},
        dataParamApk: {},
        pickerOptions1: {
          shortcuts: [{
            text: "今天",
            onClick (picker) {
              picker.$emit("pick", new Date());
            }
          }]
        },
        fileList: []
      };
    },
    computed: {
      param () {
        return {
          ostype: this.os,
          version: this.version,
          upgrade_type_id: this.type,
          product_type_id: 1001,
          page_size: this.size,
          cur_page: this.currentPage
        };
      },
      apkParam () {
        return {
          channel_number: this.channel,
          channel_name: this.channelName,
          version: this.apkVersion,
          start_time: commonHandle.getHoursString(this.apkTime[0]),
          end_time: commonHandle.getHoursString(this.apkTime[1]),
          product_type_id: 1001,
          page_size: this.apkSize,
          cur_page: this.apkCurrentPage
        };
      }
    },
    created () {
      // 检查页面权限
      commonHandle.checkMenu("/ota/sys/check_sys_user_page_auth", this, this.$route.path);
      let fn = function (response, self) {
        self.osData = response.data.result.ostypes;
        self.updateData = response.data.result.upgrade_types;
        self.versionData = response.data.result.versions;
        self.supportData = response.data.result.versions;
        self.os = self.osData[0].ostype;
        self.version = self.versionData[0].version;
        self.type = self.updateData[0].upgrade_type_id;
        self.queryTable();
      };
      // 查询下拉框信息
      commonHandle.requestPost("/ota/appmgr/version/query_all_type", this, {oper_type: 1}, fn);
      let fns = function (response, self) {
        self.channelData = response.data.result.channel_numbers;
        self.channelNameData = response.data.result.channel_names;
        self.apkVersionData = response.data.result.versions;
        self.channel = self.channelData[0].channel_number;
        self.apkVersion = self.apkVersionData[0].version;
        self.channelName = self.channelNameData[0].channel_name;
        self.apkQueryTable();
      };
      // 查询下拉框信息
      commonHandle.requestPost("/ota/appmgr/channel_package/query_all_type", this, {}, fns);
    },
    methods: {
      // 升级管理方法
      toggleButton (index) {
        this.j = index;
      },
      searchTable () {
        this.queryTable();
      },
      addData () {
        this.editType = "add";
        this.id = "";
        let fn = function (response, self) {
          self.osData = response.data.result.ostypes;
          self.updateData = response.data.result.upgrade_types;
          self.versionData = response.data.result.versions;
          self.supportData = response.data.result.versions;
        };
        // 查询下拉框信息
        commonHandle.requestPost("/ota/appmgr/version/query_all_type", this, {oper_type: 2}, fn);
        this.form = {
          os: "",
          version: "",
          type: "",
          support: [],
          textarea: ""
        };
        this.importVisible = true;
      },
      handleEdit (index, row, tableData) {
        // 添加固件编辑
        this.editType = "edit";
        this.id = row.id;
        let fn = function (response, self) {
          self.osData = response.data.result.ostypes;
          self.updateData = response.data.result.upgrade_types;
          self.versionData = response.data.result.versions;
          self.supportData = response.data.result.versions;
        };
        // 查询下拉框信息
        commonHandle.requestPost("/ota/appmgr/version/query_all_type", this, {oper_type: 2}, fn);
        this.form = {
          os: row.ostype,
          version: row.version,
          type: row.upgrade_type_id,
          support: row.old_version.split(","),
          textarea: row.note
        };
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
      queryTable () {
        // 查询表格信息
        let fnTable = function (response, self) {
          self.tableData = response.data.result.upgrade_policys;
          self.total = response.data.result.total_count;
          self.loading = false;
        };
        commonHandle.requestPost("/ota/appmgr/version/query_upgrade_info/" + this.currentPage + "/" + this.size, this, this.param, fnTable);
      },
      formCancel () {
        this.importVisible = false;
      },
      formConfirm () {
        let os = this.form.os;
        let version = this.form.version;
        let support = this.form.support;
        let type = this.form.type;
        let textarea = this.form.textarea;
        if (!os || !type || !version || !support) {
          this.$message.error("请把内容填写完整");
          return;
        }
        let params = {
          "id": this.id,
          "ostype": os,
          "new_version": version,
          "upgrade_type_id": type,
          "product_type_id": 1001,
          "old_version": support.join(","),
          "note": textarea
        };
        let fnTable = function (response, self) {
          self.queryTable();
        };
        let url = "";
        if (this.editType === "add") {
          url = "/ota/appmgr/version/add_upgrade_info";
        } else if (this.editType === "edit") {
          url = "/ota/appmgr/version/edit_upgrade_info";
        }
        commonHandle.requestPost(url, this, params, fnTable);
        this.importVisible = false;
      },
      // 渠道安装包方法
      apkAddData () {
        // 添加固件信息
        this.apkForm = {
          "channel": "",
          "version": "",
          "updateTimeStr": "",
          "inside": "",
          "outside": ""
        };
        this.apkEditType = "add";
        this.apkId = "";
        let fn = function (response, self) {
          self.formChannelData = response.data.result.channels;
        };
        // 添加固件信息下拉框查询
        commonHandle.requestPost("/ota/appmgr/channel/query_all_channel", this, {}, fn);
        this.dialogFormVisible = true;
      },
      apkSearchTable () {
        this.apkQueryTable();
      },
      apkHandleEdit (index, row, tableData) {
        // 添加安装包编辑
        this.apkEditType = "edit";
        this.apkId = row.id;
        this.apkForm = {
          "channel": row.channel_id,
          "version": row.version,
          "updateTimeStr": row.update_time,
          "inside": row.inter_note,
          "outside": row.exter_note
        };
        this.dialogFormVisible = true;
        this.fileList = [{
          name: row.file_name,
          url: row.file_url,
          status: "finished"
        }];
        let fn = function (response, self) {
          self.formChannelData = response.data.result.channels;
        };
        commonHandle.requestPost("/ota/appmgr/channel/query_all_channel", this, {}, fn);
      },
      downLoadApk (row) {
       window.open(row.file_url, "_blank");
      },
      apkHandleSizeChange (val) {
        this.apkSize = val;
        this.apkQueryTable();
      },
      apkHandleCurrentChange (val) {
        this.apkCurrentPage = val;
        this.apkQueryTable();
      },
      apkFormConfirm () {
        let channel = commonHandle.trim(this.apkForm.channel);
        let version = commonHandle.trim(this.apkForm.version);
        let inside = commonHandle.trim(this.apkForm.inside);
        let outside = commonHandle.trim(this.apkForm.outside);
        let updateTimeStr = commonHandle.trim(this.apkForm.updateTimeStr);
          // 校验安装包信息添加是否完整
        if (this.$refs.upload && this.$refs.upload.uploadFiles) {
          let file = this.$refs.upload.uploadFiles;
          if (!channel || !version || !inside || !outside || !updateTimeStr || file.length <= 0) {
            this.$message.error("请把内容填写完整");
            return;
          }
          let reg = /[\u4E00-\u9FA5\uF900-\uFA2D]/;
          if (reg.test(file[0].name)) {
            this.$message.error("文件名不能包含中文");
            return;
          }
          // 校验安装包信息编辑是否完整
          this.dataParam.channel_id = channel;
          this.dataParam.id = this.apkId;
          this.dataParam.version = version;
          this.dataParam.exter_note = outside;
          this.dataParam.inter_note = inside;
          this.dataParam.product_type_id = 1001;
          this.dataParam.update_time = commonHandle.getHoursString(new Date(updateTimeStr));
          this.dataParam.userName = localStorage.getItem("userName");
          this.dataParam.lastOptUid = localStorage.getItem("uid");
          this.dataParam.token = localStorage.getItem("token");
          this.dataParam.real_name = localStorage.getItem("real_name");
          if (this.apkEditType === "edit") {
            if (file[0].status === "ready") {
              this.fullscreenLoading = true;
              this.$refs.upload.submit();
            } else {
              let fn = function (response, self) {
                self.$message({
                  message: "操作成功",
                  type: "success"
                });
                self.apkQueryTable();
              };
              commonHandle.requestPost("/ota/appmgr/channel_package/edit_channel_no_package", this, this.dataParam, fn);
            }
          } else if (this.apkEditType === "add") {
            this.fullscreenLoading = true;
            this.$refs.upload.submit();
          }
        }
        this.dialogFormVisible = false;
      },
      apkFormCancel () {
        if (this.$refs.upload && this.$refs.upload.uploadFiles !== null && this.$refs.upload.uploadFiles !== undefined) {
          this.$refs.upload.uploadFiles.shift();
        }
        this.dialogFormVisible = false;
      },
      apkActionUpload () {
        // 上传文件action动态变换
        if (this.apkEditType === "add") {
          return protoType + "//" + hostName + "/ota/appmgr/channel_package/add_channel_package";
        } else {
          return protoType + "//" + hostName + "/ota/appmgr/channel_package/edit_channel_package";
        }
      },
      apkQueryTable () {
        // 查询表格信息
        let fnTable = function (response, self) {
          self.apkTableData = response.data.result.channel_packages;
          self.totalDatas = response.data.result.total_count;
          self.apkLoading = false;
        };
        commonHandle.requestPost("/ota/appmgr/channel_package/query_channel_package_info/" + this.apkCurrentPage + "/" + this.apkSize, this, this.apkParam, fnTable);
      },
      apkHandleClose (done) {
        if (this.$refs.upload && this.$refs.upload.uploadFiles !== null && this.$refs.upload.uploadFiles !== undefined) {
          this.$refs.upload.uploadFiles.shift();
        }
        done();
      },
      apkHandleChange (file, fileList) {
        let length = this.$refs.upload.uploadFiles.length;
        if (length > 1) {
          this.$refs.upload.uploadFiles.shift();
        }
      },
      apkSuccessdata (response, file, fileList) {
        let fn = function (response, self) {
          self.$message({
            message: "操作成功",
            type: "success"
          });
          self.apkQueryTable();
        };
        if (this.$refs.upload && this.$refs.upload.uploadFiles !== null && this.$refs.upload.uploadFiles !== undefined) {
          this.$refs.upload.uploadFiles.shift();
        }
        commonHandle.requestOK(response, this, fn);
        this.fullscreenLoading = false;
      },
      apkErrorData (err, file, fileList) {
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
  .version-manage{
    min-width: 1280px;
    max-width: 1500px;
    .search{
      margin-bottom: 30px;
      margin-top: 20px;
      .name{
        padding: 0 10px 0 15px;
      }
      button{
        margin-left: 20px;
      }
      .el-select, .el-input{
        width: 200px;
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
    .update{
      .el-select{
        margin-right: 8%;
      }
    }
    .apk{
      .el-select{
        margin-right: 10px;
      }
    }
    .add{
      margin-bottom: 20px;
    }
    .page-data{
      text-align: center;
      padding: 70px 0 30px 0;
    }
    .dia-data{
      .el-input .el-input__inner{
        max-width: 300px;
      }
    }
  }
</style>
