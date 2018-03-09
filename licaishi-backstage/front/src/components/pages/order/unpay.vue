<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/home' }"><i class="el-icon-menu"></i> 首页</el-breadcrumb-item>
        <el-breadcrumb-item>待打款</el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <div class="handle-box">
      <el-date-picker v-model="startDate" type="date" placeholder="开始日期"></el-date-picker>
      <el-date-picker v-model="endDate" type="date" placeholder="结束日期"></el-date-picker>
      <el-button type="primary" icon="el-icon-search" @click="handleSearch">检索</el-button>
    </div>

    <el-table :data="orderList" v-loading="listLoading" style="width: 100%" border>
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" inline class="detail-in-table">
            <el-form-item label="订单编号">
              <span>{{ props.row.orderNo }}</span>
            </el-form-item>
            <el-form-item label="订单金额">
              <span>{{ props.row.amount }}</span>
            </el-form-item>
            <el-form-item label="订单日期">
              <span>{{ props.row.orderDate}}</span>
            </el-form-item>
            <el-form-item label="最迟打款日期">
              <span>{{ props.row.latestPayDate }}</span>
            </el-form-item>
            <el-form-item label="理财师">
              <span>{{ props.row.financer }}</span>
            </el-form-item>
            <el-form-item label="电话">
              <span>{{ props.row.financerTel }}</span>
            </el-form-item>
            <el-form-item label="客户">
              <span>{{ props.row.customer }}</span>
            </el-form-item>
            <el-form-item label="电话">
              <span>{{ props.row.customerTel }}</span>
            </el-form-item>
            <el-form-item label="产品">
              <span>{{ props.row.product }}</span>
            </el-form-item>
            <el-form-item label="发行机构">
              <span>{{ props.row.inst }}</span>
            </el-form-item>
            <el-form-item label="佣金比率">
              <span>{{ props.row.comRatio }}</span>
            </el-form-item>
            <el-form-item label="佣金">
              <span>{{ props.row.commission }}</span>
            </el-form-item>
            <el-form-item label="收益比率">
              <span>{{ props.row.proRatio }}</span>
            </el-form-item>
            <el-form-item label="收益">
              <span>{{ props.row.profit }}</span>
            </el-form-item>
            <el-form-item label="合同">
              <span>{{ props.row.contractStatus }}</span>
            </el-form-item>
            <el-form-item label="凭证">
              <span v-if="voucherStatus === '1' ">
                <img src=""/>
              </span>
              <span v-else>
                未上传
              </span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column prop="orderNo" label="编号" width="80"></el-table-column>
      <el-table-column prop="amount" label="金额" width="60"></el-table-column>
      <el-table-column prop="orderDate" label="订单日期" width="100"></el-table-column>
      <el-table-column prop="latestPayDate" label="最迟打款日" width="100"></el-table-column>
      <el-table-column prop="proShortNam" label="产品" width="120"></el-table-column>
      <el-table-column prop="financer" label="理财师" width="80"></el-table-column>
      <el-table-column prop="customer" label="客户" width="80"></el-table-column>
      <el-table-column prop="commission" label="佣金" width="60"></el-table-column>
      <el-table-column prop="profit" label="收益" width="60"></el-table-column>
      <el-table-column prop="voucherStatus" label="凭证" width="60"></el-table-column>
      <el-table-column prop="contractStatus" label="合同" width="60"></el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button size="small" @click="handleSettle(scope.$index, scope.row)">完成打款</el-button>
          <el-button size="small" type="danger" @click="handleFailure(scope.$index, scope.row)">失败</el-button>
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
  </div>
</template>

<script>
    export default {
      data() {
        return {
          startDate: '',
          endDate: '',

          orderList: [],
          listLoading: false,

          total:0,//总记录数
          pageSize:20,//每页数据条数,
          pageNumber:1//当前第几页
        }
      },
      mounted: function () {
        this.handleSearch();
      },
      methods: {
        handleSizeChange(val) {
          this.pageSize = val;
          this.handleSearch();
        },
        handleCurrentChange(val) {
          this.pageNumber = val;
          this.handleSearch();
        },
        handleSearch() {
          this.$axios.get('/order/orderlist', {params: {status: '110', startDate: this.startDate, endDate: this.endDate, pageNumber: this.pageNumber, pageSize: this.pageSize}}).then((res) => {
            if (res.data.status == 200) {
              this.total = res.data.result.total;
              this.orderList = res.data.result.dataList;
            }
          }).catch((res) => {
            console.log(res);
          });
        },
        handleSettle(index, row) {
        },
        handleFailure(index, row) {
        }
      }
    }
</script>

<style scoped>

</style>
