<template>
  <div class="accountWrap">
    <div class="search">
      <span class="name">邮箱</span>
      <el-input v-model="emails" class="el_type" placeholder="请输入邮箱地址"></el-input>
      <span class="name">姓名</span>
      <el-input v-model="name" class="el_type" placeholder="请输入姓名"></el-input>
      <span class="name">部门</span>
      <el-select v-model="type">
        <el-option
          v-for="item in typeData"
          :key="item.strId"
          :label="item.strValue"
          :value="item.strId">
        </el-option>
      </el-select>
      <span class="name">角色</span>
      <el-select v-model="role">
        <el-option
          v-for="item in roleAllData"
          :key="item.strId"
          :label="item.strValue"
          :value="item.strId">
        </el-option>
      </el-select>
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
        <el-table-column width="150" align="center" label="操作">
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
        <el-form :model="form" label-width="100px">
          <el-form-item label="邮箱">
            <el-input v-model="form.email" :disabled="disabled"></el-input>
          </el-form-item>
          <el-form-item label="姓名">
            <el-input v-model="form.name" :disabled="disabled"></el-input>
          </el-form-item>
          <el-form-item label="部门">
            <el-input v-model="form.type" :disabled="disabled"></el-input>
          </el-form-item>
          <el-form-item label="角色">
            <el-select v-model="form.role" placeholder="">
              <el-option
                v-for="item in roleData"
                :key="item.strId"
                :label="item.strValue"
                :value="item.strId">
              </el-option>
            </el-select>
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
        emails: "",
        role: "",
        currentPage: 1,
        size: 10,
        total: 0,
        loading: true,
        dialogFormVisible: false,
        disabled: true,
        editId: "",
        roleData: [],
        roleAllData: [],
        typeData: [],
        form: {
          email: "",
          name: "",
          type: "",
          role: ""
        },
        tableList: [
          {
            value: "email",
            name: "邮箱"
          }, {
            value: "realName",
            name: "姓名"
          }, {
            value: "department",
            name: "部门"
          }, {
            value: "userRolesName",
            name: "角色"
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
          email: this.emails,
          realName: this.name,
          department: this.type,
          roleId: this.role,
          pageSize: this.size,
          curPage: this.currentPage
        };
      }
    },
    created () {
      // 检查页面权限
      commonHandle.checkMenu("/ota/sys/check_sys_user_page_auth", this, this.$route.path);
      let fn = function (response, self) {
        self.roleData = response.data.result.sysRoles;
        self.roleAllData = response.data.result.allSysRoles;
        self.typeData = response.data.result.sysDepartments;
      };
      // 查询下拉框信息
      commonHandle.requestPost("/ota/sys/query_sys_dprole_info", this, {}, fn);
      this.queryTable();
    },
    methods: {
      searchTable () {
        // 搜索表格数据
        this.queryTable();
      },
      handleSizeChange (val) {
        this.size = val;
        this.queryTable();
      },
      handleEdit (index, row, data) {
        // 编辑详细信息
        this.dialogFormVisible = true;
        this.form = {
          email: row.email,
          name: row.userName,
          type: row.department,
          role: row.userRoleIds
        };
        this.editId = row.uid;
      },
      handleDelete (index, row, data) {
        this.$confirm("此操作将永久删除, 是否继续?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          let params = {uid: row.uid};
          let fn = function (response, self) {
            self.$message({
              type: "success",
              message: "删除成功!"
            });
            self.queryTable();
          };
          commonHandle.requestPost("/ota/sys/del_user", this, params, fn);
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
          self.tableData = response.data.result.sysUsersInfo;
          self.total = response.data.result.totalCount;
          self.loading = false;
        };
        commonHandle.requestPost("/ota/sys/query_sys_user_info", this, this.param, fnTable);
      },
      formConfirm () {
        let params = {uid: this.editId, roleIds: this.form.role};
        let fn = function (response, self) {
          self.queryTable();
        };
        commonHandle.requestPost("/ota/sys/update_user_roles", this, params, fn);
        this.dialogFormVisible = false;
      },
      formCancel () {
        this.dialogFormVisible = false;
      }
    }
  };
</script>
<style lang="less">
  .accountWrap{
    min-width: 1280px;
    max-width: 1500px;
    .search{
      margin-bottom: 50px;
      .name{
        padding: 0 15px 0 50px;
      }
      button{
        margin-left: 20px;
      }
      .el_type, .el-select{
        width: 12%;
      }
    }
    .el-input__inner{
      max-width: 300px;
    }
    .page-data{
      text-align: center;
      padding: 70px 0;
    }
  }
</style>
