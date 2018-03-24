<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/home' }">
          <i class="el-icon-menu"></i> 首页</el-breadcrumb-item>
        <el-breadcrumb-item>理财师管理</el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <div class="handle-box">
      <el-input v-model="nameSearch" placeholder="理财师姓名" class="handle-input"></el-input>
      <el-button type="primary" icon="el-icon-search" @click="handleSearch">检索</el-button>
      <el-button type="primary" icon="el-icon-plus" @click="handleAdd">新增</el-button>
      <el-button type="danger" icon="el-icon-close" :disabled="this.multipleSelection.length===0" class="handle-del" @click="batchRemove">批量删除
      </el-button>
    </div>

    <el-table :data="lcsList" v-loading="listLoading" style="width: 100%" border ref="multipleTable" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="40"></el-table-column>
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" inline class="detail-in-table">
            <el-form-item label="姓名">
              <span>{{ props.row.name }}</span>
            </el-form-item>
            <el-form-item label="电话">
              <span>{{ props.row.phone }}</span>
            </el-form-item>
            <el-form-item label="性别">
              <span>{{ props.row.sex | formatDetailSex}}</span>
            </el-form-item>
            <el-form-item label="出生日期">
              <span>{{ props.row.birthday }}</span>
            </el-form-item>
            <el-form-item label="电子邮箱">
              <span>{{ props.row.email }}</span>
            </el-form-item>
            <el-form-item label="创建日期">
              <span>{{ props.row.createtime }}</span>
            </el-form-item>
            <el-form-item label="地址">
              <span>{{ props.row.address }}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column prop="uid" label="uid" width="70"></el-table-column>
      <el-table-column prop="name" label="姓名" width="150"></el-table-column>
      <el-table-column prop="phone" label="电话" width="150"></el-table-column>
      <el-table-column prop="sex" label="性别" width="80" :formatter="formatSex"></el-table-column>
      <el-table-column prop="email" label="电子邮箱" width="100"></el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
          <el-button size="small" type="danger" :disabled="scope.row.status===-1" @click="handleCancel(scope.$index, scope.row)">注销</el-button>
          <el-button size="small" type="primary" :disabled="scope.row.status===0" @click="handleNormal(scope.$index, scope.row)">启用</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--分页-->
    <el-pagination style="float:right;margin-top:8px;" @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pageNumber" :page-sizes="[20, 100, 300, 500]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
    </el-pagination>

    <!--新增界面-->
    <el-dialog title="新增" :visible.sync="addFormVisible" :close-on-click-modal="false">
      <el-form :model="addForm" label-width="80px" :rules="formRules" ref="addForm">
        <el-form-item label="姓名" prop="name">
          <el-input v-model="addForm.name"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="addForm.phone"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio-group v-model="addForm.sex">
            <el-radio class="radio" :label="1">男</el-radio>
            <el-radio class="radio" :label="0">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="生日">
          <el-date-picker type="date" value-format="yyyy-MM-dd" placeholder="选择日期" v-model="addForm.birthday"></el-date-picker>
        </el-form-item>
        <el-form-item label="电子邮箱" prop="email" label-width="160px">
          <el-input v-model="addForm.email"></el-input>
        </el-form-item>
        <el-form-item label="地址">
          <el-input type="textarea" v-model="addForm.address"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="addFormVisible = false">取消</el-button>
        <el-button type="primary" @click.native="addSubmit" :loading="addLoading">提交</el-button>
      </div>
    </el-dialog>

    <!--修改界面-->
    <el-dialog title="修改" :visible.sync="editFormVisible" :close-on-click-modal="false">
      <el-form :model="editForm" label-width="80px" :rules="formRules" ref="editForm">
        <el-form-item label="姓名" prop="name">
          <el-input v-model="editForm.name"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="editForm.phone"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio-group v-model="editForm.sex">
            <el-radio class="radio" :label="1">男</el-radio>
            <el-radio class="radio" :label="0">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="生日">
          <el-date-picker type="date" value-format="yyyy-MM-dd" placeholder="选择日期" v-model="editForm.birthday"></el-date-picker>
        </el-form-item>
        <el-form-item label="电子邮箱" prop="email">
          <el-input v-model="editForm.email"></el-input>
        </el-form-item>
        <el-form-item label="地址">
          <el-input type="textarea" v-model="editForm.address"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="editFormVisible = false">取消</el-button>
        <el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      nameSearch: "",
      lcsList: [],
      multipleSelection: [], //列表选中列
      listLoading: false,

      total: 0, //总记录数
      pageSize: 20, //每页数据条数,
      pageNumber: 1, //当前第几页

      formRules: {
        name: [{ required: true, message: "请输入姓名", trigger: "blur" }],
        phone: [{ required: true, message: "请输入电话", trigger: "blur" }],
        email: [
          { type: "email", message: "请输入正确格式的邮箱", trigger: "blur" }
        ]
      },

      addFormVisible: false, //新增界面是否显示
      addLoading: false,
      //新增界面数据
      addForm: {
        name: "",
        phone: "",
        sex: -1,
        birthday: "",
        email: "",
        address: ""
      },

      editFormVisible: false, //新增界面是否显示
      editLoading: false,
      //编辑界面数据
      editForm: {
        name: "",
        phone: "",
        sex: -1,
        birthday: "",
        email: "",
        address: ""
      }
    };
  },
  mounted: function() {
    this.handleSearch();
  },
  filters: {
    formatDetailSex: function(val) {
      return val == 1 ? "男" : val == 0 ? "女" : "未知";
    }
  },
  methods: {
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    //性别显示转换
    formatSex: function(row, column) {
      return row.sex == 1 ? "男" : row.sex == 0 ? "女" : "未知";
    },

    handleSizeChange(val) {
      this.pageSize = val;
      this.handleSearch();
    },
    handleCurrentChange(val) {
      this.pageNumber = val;
      this.handleSearch();
    },
    handleSearch() {
      this.$axios
        .get("/financer/financerlist", {
          params: {
            nameSearch: this.nameSearch,
            pageNumber: this.pageNumber,
            pageSize: this.pageSize
          }
        })
        .then(res => {
          if (res.data.status == 200) {
            this.total = res.data.result.total;
            this.lcsList = res.data.result.dataList;
          }
        })
        .catch(res => {
          console.log(res);
        });
    },

    //显示新增界面
    handleAdd() {
      this.addFormVisible = true;
      this.addForm = {
        name: "",
        phone: "",
        sex: -1,
        age: 0,
        birthday: "",
        address: ""
      };
    },
    //显示修改界面
    handleEdit(index, row) {
      this.editFormVisible = true;
      this.editForm = Object.assign({}, row);
    },
    addSubmit() {
      this.$refs.addForm.validate(valid => {
        if (valid) {
          this.$confirm("确认提交吗？", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }).then(() => {
            this.addLoading = true;
            this.$axios
              .post("/financer/addfinancer", this.addForm)
              .then(res => {
                this.addLoading = false;
                if (res.data.status == 200) {
                  this.$message({
                    type: "success",
                    message: "新增成功!"
                  });
                  this.$refs["addForm"].resetFields();
                  this.addFormVisible = false;
                  this.handleSearch();
                } else {
                  this.$message({
                    showClose: true,
                    message: res.data.message,
                    type: "error"
                  });
                }
              })
              .catch(res => {
                this.addLoading = false;
                this.$message({
                  showClose: true,
                  message: "访问服务器异常",
                  type: "warning"
                });
              });
          });
        }
      });
    },
    editSubmit() {
      this.$refs.editForm.validate(valid => {
        if (valid) {
          this.$confirm("确认提交吗？", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }).then(() => {
            this.editLoading = true;
            this.$axios
              .post("/financer/editfinancer", this.editForm)
              .then(res => {
                console.log(res);
                this.editLoading = false;
                if (res.data.status == 200) {
                  this.$message({
                    type: "success",
                    message: "修改成功!"
                  });
                  this.$refs["editForm"].resetFields();
                  this.editFormVisible = false;
                  this.handleSearch();
                } else {
                  this.$message({
                    showClose: true,
                    message: res.data.message,
                    type: "error"
                  });
                }
              })
              .catch(res => {
                this.editLoading = false;
                this.$message({
                  showClose: true,
                  message: "访问服务器异常",
                  type: "warning"
                });
              });
          });
        }
      });
    },

    batchRemove() {
      var ids = this.multipleSelection.map(item => item.uid).toString();
      this.$confirm("确认删除选中记录吗？", "提示", {
        type: "warning"
      }).then(() => {
        this.listLoading = true;
        this.$axios
          .post("/financer/btrvfinancer", { uids: ids })
          .then(res => {
            this.listLoading = false;
            if (res.data.status == 200) {
              this.$message({
                type: "success",
                message: "删除成功!"
              });
              this.handleSearch();
            } else {
              this.$message({
                showClose: true,
                message: res.data.message,
                type: "error"
              });
            }
          })
          .catch(res => {
            this.listLoading = false;
            this.$message({
              showClose: true,
              message: "访问服务器异常",
              type: "warning"
            });
          });
      });
    },
    handleDelete(index, row) {
      this.$confirm("确认删除该记录吗?", "提示", {
        type: "warning"
      }).then(() => {
        this.listLoading = true;
        this.$axios
          .post("/financer/delfinancer", { uid: row.uid })
          .then(res => {
            this.listLoading = false;
            if (res.data.status == 200) {
              this.$message({
                type: "success",
                message: "删除成功!"
              });
              this.handleSearch();
            } else {
              this.$message({
                showClose: true,
                message: res.data.message,
                type: "error"
              });
            }
          })
          .catch(res => {
            this.listLoading = false;
            this.$message({
              showClose: true,
              message: "访问服务器异常",
              type: "warning"
            });
          });
      });
    },
    handleCancel(index, row) {
      this.$confirm("确认注销该理财师账号吗?", "提示", {
        type: "warning"
      }).then(() => {
        this.listLoading = true;
        this.$axios
          .post("/financer/handlecancel", { uid: row.uid })
          .then(res => {
            this.listLoading = false;
            if (res.data.status == 200) {
              this.$message({
                type: "success",
                message: "注销成功!"
              });
              this.handleSearch();
            } else {
              this.$message({
                showClose: true,
                message: res.data.message,
                type: "error"
              });
            }
          })
          .catch(res => {
            this.listLoading = false;
            this.$message({
              showClose: true,
              message: "访问服务器异常",
              type: "warning"
            });
          });
      });
    },
    handleNormal(index, row) {
      this.$confirm("确认启用该理财师账户吗?", "提示", {
        type: "warning"
      }).then(() => {
        this.listLoading = true;
        this.$axios
          .post("/financer/handlenormal", { uid: row.uid })
          .then(res => {
            this.listLoading = false;
            if (res.data.status == 200) {
              this.$message({
                type: "success",
                message: "启用成功!"
              });
              this.handleSearch();
            } else {
              this.$message({
                showClose: true,
                message: res.data.message,
                type: "error"
              });
            }
          })
          .catch(res => {
            this.listLoading = false;
            this.$message({
              showClose: true,
              message: "访问服务器异常",
              type: "warning"
            });
          });
      });
    }
  }
};
</script>

<style scoped>
.handle-input {
  width: 220px;
  display: inline-block;
}
</style>
