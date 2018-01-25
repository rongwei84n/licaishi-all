<template>
  <div class="roleWrap">
    <div class="search">
      <el-button type="primary" @click="searchTable" :disabled="noAuthority">+添加角色</el-button>
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
        <el-table-column width="200" align="center" label="操作">
          <template scope="scope">
            <el-button
              size="small"
              type="text"
              :disabled="scope.row.editable === 1"
              @click="handleEdit(scope.$index, scope.row, tableData)">编辑</el-button>
            <el-button
              size="small"
              type="text"
              :disabled="scope.row.editable === 1"
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
    <div class="dia-data">
      <el-dialog width="570px" :close-on-click-modal="false" :visible.sync="dialogFormVisible">
        <el-form :model="form">
          <el-form-item label="角色" :label-width="formLabelWidth">
            <el-input v-model="form.role" auto-complete="off" :disabled="disabled"></el-input>
          </el-form-item>
          <el-form-item label="权限" :label-width="formLabelWidth">
            <div class="roleTree">
              <el-tree
                :data="data2"
                show-checkbox
                node-key="id"
                ref="tree"
                highlight-current
                :props="defaultProps">
              </el-tree>
            </div>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="formCancel()">取 消</el-button>
          <el-button type="primary" @click="formConfirm()">确 定</el-button>
        </div>
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
        type: "",
        name: "",
        email: "",
        role: "",
        currentPage: 1,
        size: 10,
        total: 0,
        id: "",
        loading: true,
        disabled: false,
        editType: "",
        formLabelWidth: "150px",
        data2: [
          {
          id: 1,
          label: "一级 1",
          children: [{
            id: 4,
            label: "二级 1-1",
            children: [{
              id: 9,
              label: "三级 1-1-1",
              children: [{
                id: 9,
                label: "三级 1-1-1",
                children: [{
                  id: 9,
                  label: "三级 1-1-1"
                }, {
                  id: 10,
                  label: "三级 1-1-2"
                }]
              }, {
                id: 10,
                label: "三级 1-1-2"
              }]
            }, {
              id: 10,
              label: "三级 1-1-2"
            }]
          }]
        }, {
          id: 2,
          label: "一级 2",
          children: [{
            id: 5,
            label: "二级 2-1"
          }, {
            id: 6,
            label: "二级 2-2"
          }]
        }, {
          id: 3,
          label: "一级 3",
          children: [{
            id: 7,
            label: "二级 3-1"
          }, {
            id: 8,
            label: "二级 3-2"
          }]
        }],
        defaultProps: {
          children: "children",
          label: "label"
        },
        dialogFormVisible: false,
        form: {
          role: ""
        },
        roleData: [],
        typeData: [],
        tableList: [
          {
            value: "name",
            name: "角色"
          }, {
            value: "roleAuthNames",
            name: "权限"
          }, {
            value: "optTimeStr",
            name: "操作时间"
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
          pageSize: this.size,
          curPage: this.currentPage
        };
      }
    },
    created () {
      // 检查页面权限
      commonHandle.checkMenu("/ota/sys/check_sys_user_page_auth", this, this.$route.path);
      this.queryTable();
    },
    methods: {
      searchTable () {
        // 添加角色
        this.dialogFormVisible = true;
        this.disabled = false;
        this.editType = "add";
        this.form.role = "";
        this.id = "";
        let fn = function (response, self) {
          self.data2 = response.data.result.authTreeInfos;
          self.$refs.tree.setCheckedKeys([]);
        };
        commonHandle.requestPost("/ota/sys/query_role_need_auth_info", this, {}, fn);
      },
      handleSizeChange (val) {
        this.size = val;
        this.queryTable();
      },
      handleEdit (index, row, data) {
        // 编辑角色
        this.dialogFormVisible = true;
        this.disabled = true;
        this.editType = "edit";
        this.id = row.id;
        this.form = {
          role: row.name
        };
        let fn = function (response, self) {
          self.data2 = response.data.result.authTreeInfos;
          self.$refs.tree.setCheckedKeys(row.roleAuthIds.split(","));
        };
        commonHandle.requestPost("/ota/sys/query_role_need_auth_info", this, {}, fn);
      },
      handleDelete (index, row, data) {
        this.$confirm("此操作将永久删除, 是否继续?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          let params = {id: row.id};
          let fn = function (response, self) {
            self.$message({
              type: "success",
              message: "删除成功!"
            });
            self.queryTable();
          };
          commonHandle.requestPost("/ota/sys/del_role", this, params, fn);
        }).catch(() => {
        });
      },
      handleCurrentChange (val) {
        this.currentPage = val;
        this.queryTable();
      },
      queryTable () {
        // 查询表格信息
        let fnTable = function (response, self) {
          self.tableData = response.data.result.sysRoles;
          self.total = response.data.result.totalCount;
          self.loading = false;
        };
        commonHandle.requestPost("/ota/sys/query_sys_role_info_list", this, this.param, fnTable);
      },
      formConfirm () {
        // 确认添加或编辑角色
        let ids = "";
        let nodeArray = this.$refs.tree.getCheckedNodes();
        for (let i = 0; i < nodeArray.length; i++) {
          if (!nodeArray[i].children) {
            ids += nodeArray[i].id + ",";
          }
        }
        ids = ids.substring(0, ids.length - 1);
        if (!this.form.role || !ids) {
          this.$message.error("请把内容填写完整");
          return;
        }
        let params = {
          name: this.form.role,
          authIds: ids,
          id: this.id
        };
        let fn = function (response, self) {
          self.queryTable();
        };
        let url = "";
        if (this.editType === "add") {
          url = "/ota/sys/add_role";
        } else {
          url = "/ota/sys/edit_role";
        }
        commonHandle.requestPost(url, this, params, fn);
        this.dialogFormVisible = false;
        this.$refs.tree.setCheckedKeys([]);
      },
      formCancel () {
        this.dialogFormVisible = false;
      }
    }
  };
</script>
<style lang="less">
  .roleWrap{
    min-width: 1280px;
    max-width: 1500px;
    .search{
      margin-bottom: 30px;
    }
    .el-input{
      max-width: 300px;
    }
    .el-input__inner{
      max-width: 300px;
    }
    .el-tree-node>.el-tree-node__children{
      overflow: visible;
    }
    .el-form-item__content{
      overflow: auto;
    }
    .el-checkbox-group{
      margin-left: 40px;
    }
    i{
      margin-right: 10px;
    }
    .roleTree{
      margin-top: 10px;
      min-height: 200px;
      max-height: 400px;
    }
    .page-data{
      text-align: center;
      padding: 70px 0;
    }
  }
</style>
