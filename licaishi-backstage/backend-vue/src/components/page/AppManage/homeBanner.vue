<template>
  <div class="banner-data">
    <div class="add-data">
      <el-button type="primary" @click="addData" :disabled="noAuthority">+添加图片</el-button>
    </div>
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
        v-for="item in items"
        :key="item.id"
        :prop="item.value"
        :label="item.name"
        align="center">
      </el-table-column>
      <el-table-column width="300" align="center" label="图片">
        <template scope="scope">
          <img width="200px" height="100px" alt="加载失败" :src="scope.row.img_url"/>
        </template>
      </el-table-column>
      <el-table-column
        v-for="item in itemLast"
        :key="item.id"
        :prop="item.value"
        :label="item.name"
        align="center">
      </el-table-column>
      <el-table-column width="150" align="center" label="操作">
        <template scope="scope">
          <el-button
            size="small"
            type="text"
            :disabled="noAuthority"
            @click="handleEdit(scope.$index, scope.row, tableData)">编辑</el-button>
          <el-button
            size="small"
            type="text"
            :disabled="noAuthority"
            @click="handleDelete(scope.$index, scope.row, tableData)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
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
    <div class="dia-data">
      <el-dialog width="600px" :close-on-click-modal="false" :before-close="handleClose" :visible.sync="dialogFormVisible">
        <el-form :model="form">
          <el-form-item label="开始时间" :label-width="formLabelWidth">
            <el-date-picker
              v-model="form.start"
              type="date"
              placeholder="选择日期"
              align="right"
              :editable="false"
              :clearable="false"
              :picker-options="pickerOptions1">
            </el-date-picker>
            <el-time-select
              v-model="form.startHour"
              :picker-options="startData"
              :editable="false"
              :clearable="false"
              placeholder="选择时间">
            </el-time-select>
          </el-form-item>
          <el-form-item label="结束时间" :label-width="formLabelWidth">
            <el-date-picker
              v-model="form.end"
              type="date"
              placeholder="选择日期"
              align="right"
              :editable="false"
              :clearable="false"
              :picker-options="pickerOptions1">
            </el-date-picker>
            <el-time-select
              v-model="form.endHour"
              :picker-options="startData"
              :editable="false"
              :clearable="false"
              placeholder="选择时间">
            </el-time-select>
          </el-form-item>
        </el-form>
        <div v-if="addType" class="imgUpload">
          <el-upload
            class="upload-demo"
            ref="upload"
            :data="dataParam"
            list-type="picture"
            :action= "actionUpload()"
            :file-list="fileList"
            :on-change="handleChange"
            :on-success="successdata"
            :on-error="errorData"
            :auto-upload="false">
            <el-button size="small" type="primary">选择图片</el-button>
            <div slot="tip" class="el-upload__tip">支持PNG、JPG格式图片，大小不超过200K，尺寸1080*608，分辨率72P</div>
          </el-upload>
        </div>
        <div class="tips">{{tips}}</div>
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
        total: 0,
        currentPage: 1,
        size: 10,
        id: "",
        tips: "",
        dialogFormVisible: false,
        fullscreenLoading: false,
        formLabelWidth: "120px",
        editType: "add",
        addType: true,
        headerStyle: {
          "background-color": "#eef1f6",
          "color": "#1f2d3d"
        },
        // 固件表格表头
        items: [
          {"name": "开始时间", "width": "150", "value": "start_time"},
          {"name": "结束时间", "width": "150", "value": "end_time"}
        ],
        itemLast: [
          {"name": "操作人", "width": "200", "value": "last_opt_user_name"},
          {"name": "操作时间", "width": "150", "value": "oper_time"}
        ],
        // 固件表格数据绑定
        form: {
          "start": "",
          "end": "",
          "startHour": "",
          "endHour": ""
        },
        tableData: [],
        startData: {
          start: "00:00",
          step: "01:00",
          end: "23:00"
        },
        loading: true,
        dataParam: {},
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
    created () {
      // 检查页面权限
      commonHandle.checkMenu("/ota/sys/check_sys_user_page_auth", this, this.$route.path);
      this.queryTable();
    },
    methods: {
      addData: function () {
        // 添加固件信息
        this.form = {
          "start": "",
          "end": "",
          "startHour": "",
          "endHour": ""
        };
        this.editType = "add";
        this.id = "";
        this.addType = true;
        this.dialogFormVisible = true;
      },
      searchTable () {
        this.queryTable();
      },
      handleEdit (index, row, tableData) {
        // 添加固件编辑
        this.editType = "edit";
        this.id = row.id;
        this.addType = false;
        this.form = {
          "start": new Date(row.start_time.split(" ")[0]),
          "end": new Date(row.end_time.split(" ")[0]),
          "startHour": row.start_time.split(" ")[1],
          "endHour": row.end_time.split(" ")[1]
        };
        this.dialogFormVisible = true;
        console.log(row);
      },
      handleDelete (index, row, tableData) {
        this.$confirm("此操作将永久删除, 是否继续?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          let params = {
            id: row.id
          };
          let fn = function (response, self) {
            self.$message({
              type: "success",
              message: "删除成功!"
            });
            self.queryTable();
          };
          commonHandle.requestPost("/ota/appmgr/feixun_ai/banner/delete_picture", this, params, fn);
        }).catch(() => {
        });
      },
      handleSizeChange (val) {
        this.size = val;
        this.queryTable();
      },
      handleCurrentChange (val) {
        this.currentPage = val;
        this.queryTable();
      },
      formConfirm () {
        let start = commonHandle.trim(this.form.start);
        let end = commonHandle.trim(this.form.end);
        let startHour = commonHandle.trim(this.form.startHour);
        let endHour = commonHandle.trim(this.form.endHour);
        this.tips = "";
        // 校验固件信息添加是否完整
        if (this.editType === "add") {
          if (this.$refs.upload && this.$refs.upload.uploadFiles) {
            let fileArray = this.$refs.upload.uploadFiles;
            if (!start || !end || !endHour || !startHour || fileArray.length <= 0) {
              this.$message.error("请把内容填写完整");
              return;
            }
            if (fileArray.length > 0) {
              let size = this.$refs.upload.uploadFiles[0].size / 1024;
              let img = this.$refs.upload.$el.getElementsByTagName("img")[0];
              let widthImg = commonHandle.checkImg(img)[0];
              let heightImg = commonHandle.checkImg(img)[1];
              if (size > 200) {
                this.tips = "图片大小超出限制，仅支持大小不超过200k的图片";
                return;
              }
              if (widthImg !== 1080 && heightImg !== 608) {
                this.tips = "图片尺寸错误，仅支持1080 * 608尺寸的图片";
                return;
              }
              if (fileArray.length !== 0 && fileArray[0].name.toLocaleLowerCase().slice(-4) !== ".png" && fileArray[0].name.toLocaleLowerCase().slice(-4) !== ".jpg") {
                this.tips = "图片格式错误，仅支持PNG、JPG格式的图片";
                return;
              }
            }
          } else {
            this.$message.error("请把内容填写完整");
            return;
          }
        } else {
          if (!start || !end || !endHour || !startHour) {
            this.$message.error("请把内容填写完整");
            return;
          }
        }
        if (new Date(commonHandle.getDateStrings(new Date(start)) + " " + startHour).getTime() >= new Date(commonHandle.getDateStrings(new Date(end)) + " " + endHour).getTime()) {
          this.tips = "请选择正确的日期和时间";
          return;
        }
        this.dataParam.id = this.id;
        this.dataParam.start_time = commonHandle.getDateStrings(new Date(start)) + " " + startHour;
        this.dataParam.end_time = commonHandle.getDateStrings(new Date(end)) + " " + endHour;
        this.dataParam.userName = localStorage.getItem("userName");
        this.dataParam.real_name = localStorage.getItem("real_name");
        this.dataParam.lastOptUid = localStorage.getItem("uid");
        this.dataParam.token = localStorage.getItem("token");
        if (this.editType === "edit") {
          let fn = function (response, self) {
            self.$message({
              message: "操作成功",
              type: "success"
            });
           self.queryTable();
          };
          commonHandle.requestPost("/ota/appmgr/feixun_ai/banner/edit_picture", this, this.dataParam, fn);
        } else if (this.editType === "add") {
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
      queryTable () {
        // 查询表格信息
        let fnTable = function (response, self) {
          self.tableData = response.data.result.pictures;
          self.total = response.data.result.total_count;
          self.loading = false;
        };
        commonHandle.requestPost("/ota/appmgr/feixun_ai/banner/query_all_picture/" + this.currentPage + "/" + this.size, this, {}, fnTable);
      },
      // 上传文件action动态变换
      actionUpload () {
        return protoType + "//" + hostName + "/ota/appmgr/feixun_ai/banner/upload_picture";
      },
      handleClose (done) {
        if (this.$refs.upload && this.$refs.upload.uploadFiles !== null && this.$refs.upload.uploadFiles !== undefined) {
          this.$refs.upload.uploadFiles.shift();
        }
        done();
      },
      handleChange (file, fileList) {
        this.tips = "";
        let fileArray = this.$refs.upload.uploadFiles;
        if (fileArray.length > 1) {
          this.$refs.upload.uploadFiles.shift();
        }
        if (fileArray.length !== 0 && fileArray[0].name.toLocaleLowerCase().slice(-4) !== ".png" && fileArray[0].name.toLocaleLowerCase().slice(-4) !== ".jpg") {
          this.tips = "图片格式错误，仅支持PNG、JPG格式的图片";
        }
      },
      successdata (response, file, fileList) {
        let fn = function (response, self) {
          self.$message({
            message: "操作成功",
            type: "success"
          });
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

<style lang="less" scoped>
  .banner-data{
    min-width: 1280px;
    max-width: 1500px;
    .add-data{
      margin-bottom: 10px;
    }
    .page-data{
      text-align: center;
      margin: 70px 0;
    }
    .dia-data{
      .el-date-editor.el-input, .el-date-editor.el-input__inner{
        width: 150px;
        margin-right: 20px;
      }
    }
    .imgUpload{
      padding-left: 50px;
    }
    .tips{
      color: red;
      text-align: center;
    }
  }
</style>
