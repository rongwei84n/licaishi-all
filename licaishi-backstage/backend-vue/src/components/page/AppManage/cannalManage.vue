<template>
  <div class="channel-data">
    <div class="import">
      <el-button type="primary" @click="importData" :disabled="noAuthority">+添加渠道</el-button>
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
          <el-form-item label="渠道" :label-width="formLabelWidth">
            <el-input v-model="form.channelName" type="text" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="渠道号" :label-width="formLabelWidth">
            <el-input v-model="form.channel" type="text" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="下载地址" :label-width="formLabelWidth">
            <el-input v-model="form.address" type="text" auto-complete="off"></el-input>
          </el-form-item>
        </el-form>
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
        fullscreenLoading: false,
        id: "",
        editType: "",
        currentPage: 1,
        size: 10,
        total: 0,
        loading: true,
        importVisible: false,
        dataParamApk: {},
        fileListApk: [],
        tableList: [
          {
            value: "channel_name",
            name: "渠道"
          }, {
            value: "channel_number",
            name: "渠道号"
          }, {
            value: "file_url",
            name: "下载地址"
          }, {
            value: "real_name",
            name: "操作人"
          }],
        tableData: [],
        form: {
          channel: "",
          channelName: "",
          address: ""
        },
        headerStyle: {
          "background-color": "#eef1f6",
          "color": "#1f2d3d"
        }
      };
    },
    computed: {
      param () {
        return {
          page_size: this.size,
          cur_page: this.currentPage
        };
      }
    },
    created () {
      // 检查页面权限
      commonHandle.checkMenu("/ota/sys/check_sys_user_page_auth", this, this.$route.path);
      this.queryTable();
    },
    methods: {
      importData () {
        this.id = "";
        this.editType = "add";
        this.form = {
          channel: "",
          channelName: "",
          address: ""
        };
        this.importVisible = true;
      },
      handleEdit (index, row, tableData) {
        // 添加固件编辑
        this.editType = "edit";
        this.id = row.id;
        this.form = {
          "channel": row.channel_number,
          "channelName": row.channel_name,
          "address": row.download_url
        };
        this.importVisible = true;
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
          commonHandle.requestPost("/ota/appmgr/channel/delete_channel", this, params, fn);
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
      queryTable () {
        // 查询表格信息
        let fnTable = function (response, self) {
          self.tableData = response.data.result.channels;
          self.total = response.data.result.total_count;
          self.loading = false;
        };
        commonHandle.requestPost("/ota/appmgr/channel/query_channel_info/" + this.currentPage + "/" + this.size, this, this.param, fnTable);
      },
      importCancel () {
        this.importVisible = false;
      },
      importConfirm () {
        let channel = this.form.channel;
        let channelName = this.form.channelName;
        let address = this.form.address;
        if (!channel || !channelName) {
          this.$message.error("请把内容填写完整");
          return;
        }
        let params = {
          "id": this.id,
          "channel_name": channelName,
          "channel_number": channel,
          "file_url": address
        };
        let fnTable = function (response, self) {
          self.$message({
            type: "success",
            message: "操作成功!"
          });
          self.queryTable();
        };
        let url = "";
        if (this.editType === "add") {
          url = "/ota/appmgr/channel/add_channel";
        } else if (this.editType === "edit") {
          url = "/ota/appmgr/channel/edit_channel";
        }
        commonHandle.requestPost(url, this, params, fnTable);
        this.importVisible = false;
      }
    }
  };
</script>
<style lang="less">
  .channel-data{
    min-width: 1280px;
    max-width: 1500px;
    .import{
      margin-bottom: 10px;
    }
    .page-data{
      text-align: center;
      padding: 70px 0;
    }
    .import-data{
      .el-input, .el-input__inner{
        width: 300px;
      }
    }
  }
</style>
