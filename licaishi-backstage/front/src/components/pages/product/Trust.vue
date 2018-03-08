<template>
    <div>
      <div class="crumbs">
        <el-breadcrumb separator="/">
          <el-breadcrumb-item :to="{ path: '/home' }"><i class="el-icon-menu"></i> 首页</el-breadcrumb-item>
          <el-breadcrumb-item>产品管理/集合信托</el-breadcrumb-item>
        </el-breadcrumb>
      </div>

      <div class="handle-box">
        <el-input v-model="nameSearch" placeholder="产品简称" class="handle-input"></el-input>
        <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
        <el-button type="primary" icon="el-icon-plus" @click="handleAdd">新增</el-button>
        <el-button type="danger" icon="el-icon-close" :disabled="this.multipleSelection.length===0" class="handle-del"
                   @click="batchRemove">批量删除
        </el-button>
      </div>

      <el-table :data="productList" v-loading="listLoading" style="width: 100%" border ref="multipleTable"
                @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="40"></el-table-column>
        <el-table-column prop="uid" label="uid" width="70"></el-table-column>
        <el-table-column prop="pCode" label="产品编号" width="70"></el-table-column>
        <el-table-column prop="pShortName" label="产品简称" width="150"></el-table-column>
        <el-table-column prop="pFullName" label="产品全称" width="250"></el-table-column>
        <el-table-column prop="pType" label="类型" width="70" ></el-table-column>
        <el-table-column prop="pExpectAnnualRevenue" label="预期年化收益" width="70"></el-table-column>
        <el-table-column prop="pAllIssuingScale" label="募集规模" width="70"></el-table-column>
        <el-table-column prop="pDulTime" label="投资期限" width="70"></el-table-column>
        <el-table-column prop="pSaleStatus" label="产品状态" width="70"></el-table-column>
        <el-table-column prop="pSaleStartTime" label="发行日期" width="70"></el-table-column>
        <el-table-column prop="pPaymentInterestType" label="付息方式" width="70"></el-table-column>
        <el-table-column prop="pInvestType" label="投资领域" width="70"></el-table-column>
        <el-table-column prop="pSizeRatioType" label="大小配比" width="70"></el-table-column>
        <el-table-column prop="saleStatus5" label="产品状态" width="70"></el-table-column>
        <el-table-column prop="saleStatus6" label="产品状态" width="70"></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button size="small" @click="handleEdit(scope.$index, scope.row)">配置收益</el-button>
            <el-button size="small" @click="handleEdit(scope.$index, scope.row)">配置附带信息</el-button>
            <el-button size="small" @click="handleEdit(scope.$index, scope.row)">修改</el-button>
            <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!--分页-->
      <el-pagination
        style="float:right;margin-top:8px;"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNumber"
        :page-sizes="[20, 100, 300, 500]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>

      <!--新增界面-->
      <el-dialog title="新增" :visible.sync="addFormVisible" :close-on-click-modal="false">
        <el-form :model="addForm" label-width="100px" :rules="formRules" ref="addForm">
          <el-form-item label="产品简称" prop="pShortName">
            <el-input v-model="addForm.pShortName"></el-input>
          </el-form-item>
          <el-form-item label="产品全称" prop="pFullName">
            <el-input v-model="addForm.pFullName"></el-input>
          </el-form-item>
          <el-form-item label="产品类型" prop="pType">
            <el-select v-model="addForm.pType" placeholder="请选择产品类型">
              <el-option label="集合信托" value="01"></el-option>
              <el-option label="集合资管" value="02"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="募集规模" prop="pAllIssuingScale">
            <el-input v-model="addForm.pAllIssuingScale"></el-input>
          </el-form-item>
          <el-form-item label="投资期限" prop="pDulTime">
            <el-input v-model="addForm.pDulTime"></el-input>
          </el-form-item>
          <el-form-item label="预期年化收益" prop="expectAnnualRevenue">
            <el-input v-model="addForm.pExpectAnnualRevenue"></el-input>
          </el-form-item>
          <el-form-item label="投资领域" prop="pInvestType">
            <el-select v-model="addForm.pInvestType" placeholder="请选择投资领域">
              <el-option label="房地产类" value="01"></el-option>
              <el-option label="金融市场" value="02"></el-option>
              <el-option label="基础设施" value="03"></el-option>
              <el-option label="资金池" value="04"></el-option>
              <el-option label="工商企业" value="05"></el-option>
              <el-option label="其他" value="00"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="付息方式" prop="pPaymentInterestType">
            <el-select v-model="addForm.pPaymentInterestType" placeholder="请选择付息方式">
              <el-option label="按月付息" value="01"></el-option>
              <el-option label="按季付息" value="02"></el-option>
              <el-option label="按半年付息" value="03"></el-option>
              <el-option label="按年付息" value="04"></el-option>
              <el-option label="到期付本息" value="05"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="发行日期" prop="pSaleStartTime">
            <el-date-picker type="date" value-format="yyyy-MM-dd" placeholder="选择日期"
                            v-model="addForm.pSaleStartTime"></el-date-picker>
          </el-form-item>
          <el-form-item label="大小配比" prop="pSizeRatioType">
            <el-select v-model="addForm.pSizeRatioType" placeholder="请选择大小配比类型">
              <el-option label="小额畅打" value="01"></el-option>
              <el-option label="已配出小额" value="02"></el-option>
              <el-option label="严格配比" value="03"></el-option>
              <el-option label="全大额" value="04"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="最低投资金额" prop="minAmount">
            <el-input v-model="addForm.minAmount"></el-input>
          </el-form-item>
          <el-form-item label="发行金额" prop="issuingScale">
            <el-input v-model="addForm.expectSaleAmount"></el-input>
          </el-form-item>
          <el-form-item label="期望销售金额" prop="expectSaleAmount">
            <el-input v-model="addForm.expectSaleAmount"></el-input>
          </el-form-item>
          <el-form-item label="产品状态" prop="pSaleStatus">
            <el-input v-model="addForm.pSaleStatus"></el-input>
          </el-form-item>
          <el-form-item label="备注" prop="summary">
            <el-input type="textarea" v-model="addForm.summary"></el-input>
          </el-form-item>
          <el-form-item label="备注" prop="summary">
            <el-input type="textarea" v-model="addForm.summary"></el-input>
          </el-form-item>
          <el-form-item label="产品优势" prop="summary">
            <el-input type="textarea" v-model="addForm.summary"></el-input>
          </el-form-item>
          <el-form-item label="募集账号" prop="summary">
            <el-input type="textarea" v-model="addForm.summary"></el-input>
          </el-form-item>
          <el-form-item label="风险控制" prop="summary">
            <el-input type="textarea" v-model="addForm.summary"></el-input>
          </el-form-item>
          <el-form-item label="还款来源" prop="summary">
            <el-input type="textarea" v-model="addForm.summary"></el-input>
          </el-form-item>
          <el-form-item label="资金用途" prop="summary">
            <el-input type="textarea" v-model="addForm.summary"></el-input>
          </el-form-item>
          <el-form-item label="融资方" prop="summary">
            <el-input type="textarea" v-model="addForm.summary"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click.native="addFormVisible = false">取消</el-button>
          <el-button type="primary" @click.native="addSubmit" :loading="addLoading">提交</el-button>
        </div>
      </el-dialog>
    </div>
</template>

<script>
    export default {
      data() {
        return {
          nameSearch: '',
          productList: [],
          multipleSelection: [],//列表选中列
          listLoading: false,

          total:0,//总记录数
          pageSize:20,//每页数据条数,
          pageNumber:1,//当前第几页

          addFormVisible: false,//新增界面是否显示
          addLoading: false,
          //新增界面数据
          addForm: {
            pShortName: '',
            pFullName: '',
            pType: '',
            pExpectAnnualRevenue: '',
            pAllIssuingScale: '',
            pDulTime: '',
            pPaymentInterestType: '',
            pInvestType: '',
            pSizeRatioType: '',
            pIssuingScale:'',
            pMinAmount:'',
            pRecruitmentSummary: '',
            pCpys: '',
            pMjzh: '',
            pFxkz: '',
            pHkly: '',
            pRrzf: '',
            pDbf: '',
            pZjyt: ''
          },

        }
      },
      mounted: function () {
        this.handleSearch();
      },

      methods: {
        handleSelectionChange(val) {
          this.multipleSelection = val;
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
          this.$axios.get('/financer/financerlist', {
            params: {
              nameSearch: this.nameSearch,
              pageNumber: this.pageNumber,
              pageSize: this.pageSize
            }
          }).then((res) => {
            if (res.data.status == 200) {
              this.total = res.data.result.total;
              this.productList = res.data.result.dataList;
            } else {
              self.errorText = res.data.message;
            }
          }).catch((res) => {
            self.errorText = '访问服务器异常';
            console.log(res);
          });
        },

        //显示新增界面
        handleAdd() {
          this.addFormVisible = true;
          this.addForm = {
            name: '',
            phone: '',
            sex: -1,
            age: 0,
            birthday: '',
            address: ''
          }
        },

        addSubmit() {
          this.$refs.addForm.validate((valid) => {
            if (valid) {
              this.$confirm('确认提交吗？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
              }).then(() => {
                this.addLoading = true;
                this.$axios.post('/financer/addfinancer', this.addForm).then((res) => {
                  this.addLoading = false;
                  if (res.data.status == 200) {
                    this.$message({
                      type: 'success',
                      message: '新增成功!'
                    });
                    this.$refs['addForm'].resetFields();
                    this.addFormVisible = false;
                    this.handleSearch();
                  } else {
                    this.$message({
                      showClose: true,
                      message: res.data.message,
                      type: 'error'
                    });
                  }
                }).catch((res) => {
                  this.addLoading = false;
                  this.$message({
                    showClose: true,
                    message: '访问服务器异常',
                    type: 'warning'
                  });
                });
              });
            }
          });
        },

      }
    }
</script>

<style scoped>
  .handle-box {
    margin-bottom: 20px;
  }

  .handle-select {
    width: 120px;
  }

  .handle-input {
    width: 300px;
    display: inline-block;
  }

  .detail-in-table {
    font-size: 0;
  }

  .detail-in-table label {
    width: 90px;
    color: #99a9bf;
  }

  .detail-in-table .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 50%;
  }

</style>
