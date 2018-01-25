<template>
    <div class="alldata">
        <div class="search">
            <span>产品名称</span>
            <el-input
                placeholder="请输入产品名称"
                v-model="inputProuduct">
            </el-input>
            <span>型号</span>
            <el-input
                placeholder="请输入型号"
                v-model="version">
            </el-input>
            <span>新版本号</span>
            <el-input
                placeholder="请输入新版本号"
                v-model="newVersion">
            </el-input>
            <el-button type="primary" @click="searchTable">搜索</el-button>
        </div>
        <div class="add-data">
            <el-button type="primary" @click="addData" :disabled="noAuthority">+添加固件</el-button>
        </div>
        <el-table
            :data="tableData"
            border
            :header-row-style="headerStyle"
            v-loading="loading"
            element-loading-text="拼命加载中"
            max-height="500"
            style="width: 100%">
            <el-table-column
                prop="product_area_name"
                label="产品名称"
                fixed="left"
                align="center"
                width="150">
            </el-table-column>
            <el-table-column
                v-for="item in items"
                :key="item.id"
                :prop="item.value"
                :label="item.name"
                align="center"
                :width="item.width">
            </el-table-column>
            <el-table-column width="150" label="固件安装包">
              <template scope="scope">
                <el-button
                  size="small"
                  type="warning"
                  :disabled="noAuthority"
                  @click="handleUpload(scope.$index, scope.row, tableData)">上传</el-button>
                <span>{{scope.row.file_name}}</span>
              </template>
            </el-table-column>
            <el-table-column
              v-for="item in itemLast"
              :key="item.id"
              :prop="item.value"
              :label="item.name"
              align="center"
              :width="item.width">
            </el-table-column>
            <el-table-column fixed="right" width="70" align="center" label="操作">
                <template scope="scope">
                    <el-button
                        size="small"
                        type="text"
                        :disabled="noAuthority"
                        @click="handleEdit(scope.$index, scope.row, tableData)">编辑</el-button>
                </template>
            </el-table-column>
        </el-table>
        <div class="page-data">
            <div class="block">
                <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="currentPage4"
                    :page-sizes="[10, 20, 30, 50]"
                    :page-size="size"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="totalDatas">
                </el-pagination>
            </div>
        </div>
        <div class="dia-data">
            <el-dialog width="570px" :close-on-click-modal="false" :before-close="handleClose" :visible.sync="dialogFormVisible">
                <el-form :model="form">
                    <el-form-item v-if="uploadVisible" label="产品名称" :label-width="formLabelWidth">
                        <el-select :disabled="addDisabled" v-model="form.productAreaId" placeholder="">
                            <el-option v-for="area in areas" :key="area.product_area_id" :label="area.product_area_name" :value="area.product_area_id"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item v-if="uploadVisible" label="型号" :label-width="formLabelWidth">
                        <el-select :disabled="addDisabled" v-model="form.productLineId" placeholder="">
                          <el-option v-for="line in lines" :key="line.product_line_id" :label="line.product_line_name" :value="line.product_line_id"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item v-if="uploadVisible" label="硬件版本" :label-width="formLabelWidth">
                      <el-input :disabled="addDisabled" v-model="form.hwVer" auto-complete="off"></el-input>
                    </el-form-item>
                    <el-form-item v-if="uploadVisible" label="新版本号" :label-width="formLabelWidth">
                        <el-input :disabled="addDisabled" v-model="form.fwVer" auto-complete="off"></el-input>
                        <span style="display:block;color:red">注:只允许输入数字和英文小数点</span>
                    </el-form-item>
                    <el-form-item v-if="uploadVisible" label="更新时间" :label-width="formLabelWidth">
                        <el-date-picker
                          v-model="form.updateTimeStr"
                          type="datetime"
                          placeholder="选择日期时间"
                          align="right"
                          :picker-options="pickerOptions1">
                        </el-date-picker>
                    </el-form-item>
                    <el-form-item v-if="uploadVisible" label="更新内容" :label-width="formLabelWidth">
                        <el-input v-model="form.content" type="textarea" :rows="2" auto-complete="off"></el-input>
                    </el-form-item>
                    <el-form-item v-if="addType" label="固件安装包" :label-width="formLabelWidth">
                      <span style="color:red">(最大不允许超过300M)</span>
                      <el-upload
                        class="upload-demo"
                        ref="upload"
                        drag
                        :data="dataParam"
                        :action= "actionUpload()"
                        :file-list="fileList"
                        :on-change="handleChange"
                        :on-success="successdata"
                        :on-error="errorData"
                        :auto-upload="false">
                        <i class="el-icon-upload"></i>
                        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                      </el-upload>
                    </el-form-item>
                    <el-form-item v-if="addType" label="文件MD5值" :label-width="formLabelWidth">
                      <el-input v-model="form.md5" auto-complete="off"></el-input>
                      <a href="http://www.atool.org/file_hash.php" target="_blank" style="color:blue;cursor: pointer">点击</a>计算MD5值
                    </el-form-item>
                    <el-form-item v-if="uploadVisible" label="支持版本" :label-width="formLabelWidth">
                        <el-input v-model="form.supportVer" auto-complete="off"></el-input>
                        <span style="display:block;color:red">注：支持版本填写格式使用英文逗号分开，例如：0.0.0.1,0.0.0.2</span>
                        <span style="display:block;color:red">没有支持版本则填写其本身版本号</span>
                    </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="formCancel()">取 消</el-button>
                    <el-button type="primary" element-loading-text="上传文件中，请等待" v-loading.fullscreen.lock="fullscreenLoading" @click="formConfirm()">确 定</el-button>
                </div>
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
                inputProuduct: "",
                version: "",
                newVersion: "",
                upgrad: "",
                totalDatas: 0,
                currentPage4: 1,
                size: 10,
                addDisabled: false,
                uploadVisible: true,
                dialogFormVisible: false,
                dialogFormVisibleAPK: false,
                fullscreenLoading: false,
                fullscreenapkLoading: false,
                formLabelWidth: "120px",
                editType: "add",
                addType: true,
                headerStyle: {
                  "background-color": "#eef1f6",
                  "color": "#1f2d3d"
                },
                // 固件表格表头
                items: [
                    {"name": "型号", "width": "150", "value": "product_line_name"},
                    {"name": "硬件版本号", "width": "150", "value": "hw_ver"},
                    {"name": "新版本号", "width": "150", "value": "fw_ver"},
                    {"name": "更新时间", "width": "200", "value": "update_time_str"},
                    {"name": "更新内容", "width": "150", "value": "content"}
                   // {"name": "固件安装包", "width": "150", "value": "file_name"},
                ],
                itemLast: [
                  {"name": "支持版本", "width": "150", "value": "support_ver"},
                  {"name": "操作时间", "width": "200", "value": "op_time_str"},
                  {"name": "操作人", "width": "150", "value": "operator_name"}
                ],
                // 固件表格数据绑定
                form: {
                    "productAreaId": "",
                    "productLineId": "",
                    "hwVer": "",
                    "fwVer": "",
                    "updateTimeStr": "",
                    "md5": "",
                    "content": "",
                    "supportVer": ""
                },
                tableData: [],
                loading: true,
                areas: [],
                lines: [],
                action: protoType + "//" + hostName + "/ota/add_ver_relation",
                actionApk: protoType + "//" + hostName + "/ota/update_firmware_pkg",
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
                fileUrl: "",
                fileName: "",
                fileList: [],
                fileListApk: [],
                apkParams: {
                    "productAreaId": "",
                    "productLineId": "",
                    "hwVer": "",
                    "fwVer": ""
                }
            };
        },
        created () {
          // 检查页面权限
          commonHandle.checkMenu("/ota/sys/check_sys_user_page_auth", this, this.$route.path);
          let fn = function (response, self) {
              self.tableData = response.data.result.firmwareVersions;
              self.totalDatas = response.data.result.totalCount;
              self.loading = false;
            };
            let param = {
                product_area_name: encodeURIComponent(commonHandle.trim(this.inputProuduct)),
                product_line_name: encodeURIComponent(commonHandle.trim(this.version)),
                fw_ver: encodeURIComponent(commonHandle.trim(this.newVersion)),
                page_size: 10,
                cur_page: 1
            };
            commonHandle.requestPost("/ota/query_firmware_version_list", this, param, fn);
        },
        methods: {
            addData: function () {
                // 添加固件信息
                this.form = {
                    "productAreaId": "",
                    "productLineId": "",
                    "hwVer": "",
                    "fwVer": "",
                    "updateTimeStr": "",
                    "md5": "",
                    "content": "",
                    "supportVer": ""
                };
                this.editType = "add";
                this.addType = true;
                this.uploadVisible = true;
                this.addDisabled = false;
                let fn = function (response, self) {
                  self.areas = response.data.result.areas;
                  self.lines = response.data.result.lines;
                };
                // 添加固件信息下拉框查询
                commonHandle.requestPost("/ota/query_addfirmware_info", this, {}, fn);
                this.dialogFormVisible = true;
            },
            searchTable () {
                commonHandle.queryFirmwarePage("", "", this, commonHandle);
            },
            handleEdit (index, row, tableData) {
                // 添加固件编辑
                this.addDisabled = true;
                this.editType = "edit";
                this.addType = false;
                this.uploadVisible = true;
                this.form = {
                    "productAreaId": row.product_area_id,
                    "productLineId": row.product_line_id,
                    "hwVer": row.hw_ver,
                    "fwVer": row.fw_ver,
                    "updateTimeStr": new Date(row.op_time_str),
                    "content": row.content,
                    "supportVer": row.support_ver
                };
                this.dialogFormVisible = true;
                this.fileUrl = row.file_url;
                this.fileName = row.file_name;
                let fn = function (response, self) {
                  self.areas = response.data.result.areas;
                  self.lines = response.data.result.lines;
                };
                commonHandle.requestPost("/ota/query_addfirmware_info", this, {}, fn);
                console.log(row);
                // console.log(index);
            },
            handleSizeChange (val) {
              commonHandle.queryFirmwarePage("size", val, this, commonHandle);
            },
            handleCurrentChange (val) {
              commonHandle.queryFirmwarePage("page", val, this, commonHandle);
            },
            formConfirm () {
                let productAreaId = commonHandle.trim(this.form.productAreaId);
                let productLineId = commonHandle.trim(this.form.productLineId);
                let hwVer = commonHandle.trim(this.form.hwVer);
                let fwVer = commonHandle.trim(this.form.fwVer);
                let updateTimeStr = commonHandle.trim(this.form.updateTimeStr);
                let md5 = commonHandle.trim(this.form.md5);
                let content = commonHandle.trim(this.form.content);
                let supportVer = commonHandle.trim(this.form.supportVer);
                if (this.$refs.upload && this.$refs.upload.uploadFiles) {
                  // 校验固件信息添加是否完整
                  let length = this.$refs.upload.uploadFiles.length;
                  if (this.editType === "add") {
                    if (!productAreaId || !productLineId || !hwVer || !fwVer || !updateTimeStr || !content || length <= 0 || !supportVer || !md5) {
                      this.$message.error("请把内容填写完整");
                      return;
                    }
                  } else if (this.editType === "upload") {
                    if (length <= 0 || !md5) {
                      this.$message.error("请把内容填写完整");
                      return;
                    }
                  }
                } else {
                  // 校验固件信息编辑是否完整
                  if (!productAreaId || !productLineId || !hwVer || !fwVer || !updateTimeStr || !content || !supportVer) {
                    this.$message.error("请把内容填写完整");
                    return;
                  }
                }
                if (this.editType === "add" || this.editType === "edit") {
                    let patrn = /^[0-9.]+$/;
                    if (!patrn.test(fwVer)) {
                      this.$message.error("新版本号只允许输入数字和英文小数点");
                      return;
                    }
                    let patrnSupport = /^[0-9.,]+$/;
                    if (!patrnSupport.test(supportVer)) {
                      this.$message.error("支持版本号只允许输入数字、英文小数点和英文逗号");
                      return;
                    }
                }
                this.dataParam.product_area_id = productAreaId;
                this.dataParam.product_line_id = productLineId;
                this.dataParam.hw_ver = hwVer;
                this.dataParam.fw_ver = fwVer;
                this.dataParam.update_time_str = commonHandle.getHoursStrings(new Date(updateTimeStr));
                this.dataParam.file_md5 = md5;
                this.dataParam.content = content;
                this.dataParam.support_ver = supportVer;
                this.dataParam.userName = localStorage.getItem("userName");
                this.dataParam.lastOptUid = localStorage.getItem("uid");
                this.dataParam.token = localStorage.getItem("token");
                this.dataParam.real_name = localStorage.getItem("real_name");
                 if (this.editType === "edit") {
                    this.dataParam.file_url = this.fileUrl;
                    this.dataParam.file_name = this.fileName;
                    let fn = function (response, self) {
                      self.$message({
                        message: "操作成功",
                        type: "success"
                      });
                      commonHandle.queryFirmwarePage("", "", self, commonHandle);
                    };
                    commonHandle.requestPost("/ota/update_ver_relation", this, this.dataParam, fn);
                } else if (this.editType === "add") {
                   let reg = new RegExp("[\\u4E00-\\u9FFF]+", "g");
                   let val = this.$refs.upload.uploadFiles[0].name;
                   if (reg.test(val)) {
                     this.$message.error("文件名不能包含中文");
                     return;
                   }
                   this.fullscreenLoading = true;
                    this.$refs.upload.submit();
                } else {
                     let reg = new RegExp("[\\u4E00-\\u9FFF]+", "g");
                     let val = this.$refs.upload.uploadFiles[0].name;
                     if (reg.test(val)) {
                       this.$message.error("文件名不能包含中文");
                       return;
                     }
                    this.dataParam.product_area_id = this.apkParams.productAreaId;
                    this.dataParam.product_line_id = this.apkParams.productLineId;
                    this.dataParam.hw_ver = this.apkParams.hwVer;
                    this.dataParam.fw_ver = this.apkParams.fwVer;
                    this.dataParam.file_md5 = md5;
                    this.dataParam.userName = localStorage.getItem("userName");
                    this.dataParam.lastOptUid = localStorage.getItem("uid");
                    this.dataParam.token = localStorage.getItem("token");
                    this.dataParam.real_name = localStorage.getItem("real_name");
                    this.fullscreenLoading = true;
                    this.$refs.upload.submit();
                }
                this.dialogFormVisible = false;
            },
            formCancel () {
                if (this.$refs.upload && this.$refs.upload.uploadFiles !== null && this.$refs.upload.uploadFiles !== undefined) {
                  this.$refs.upload.uploadFiles.shift();
                }
                this.dialogFormVisible = false;
            },
            // 上传文件action动态变换
            actionUpload () {
              if (this.editType === "add") {
                return protoType + "//" + hostName + "/ota/add_ver_relation";
              } else {
                return protoType + "//" + hostName + "/ota/update_firmware_pkg";
              }
            },
            handleUpload (index, row, datas) {
              this.uploadVisible = false;
              this.addType = true;
              this.editType = "upload";
              this.form.md5 = "";
              this.apkParams.productAreaId = row.product_area_id;
              this.apkParams.productLineId = row.product_line_id;
              this.apkParams.hwVer = row.hw_ver;
              this.apkParams.fwVer = row.fw_ver;
              this.dialogFormVisible = true;
            },
            handleClose (done) {
                if (this.$refs.upload && this.$refs.upload.uploadFiles !== null && this.$refs.upload.uploadFiles !== undefined) {
                    this.$refs.upload.uploadFiles.shift();
                }
                done();
            },
            handleChange (file, fileList) {
                let length = this.$refs.upload.uploadFiles.length;
                if (length > 1) {
                  this.$refs.upload.uploadFiles.shift();
                }
              console.log(this.$refs.upload.uploadFiles);
            },
            successdata (response, file, fileList) {
                let fn = function (response, self) {
                  self.$message({
                    message: "操作成功",
                    type: "success"
                  });
                  commonHandle.queryFirmwarePage("", "", self, commonHandle);
                };
                if (this.$refs.upload && this.$refs.upload.uploadFiles !== null && this.$refs.upload.uploadFiles !== undefined) {
                  this.$refs.upload.uploadFiles.shift();
                }
                commonHandle.requestOK(response, this, fn);
                this.fullscreenLoading = false;
                this.fullscreenapkLoading = false;
            },
            errorData (err, file, fileList) {
                if (this.$refs.upload && this.$refs.upload.uploadFiles !== null && this.$refs.upload.uploadFiles !== undefined) {
                  this.$refs.upload.uploadFiles.shift();
                }
                commonHandle.requestErr(err, this);
                this.fullscreenLoading = false;
                this.fullscreenapkLoading = false;
            }
        }
    };
</script>

<style lang="less" scoped>
  @import "../../less/firmware";
</style>
