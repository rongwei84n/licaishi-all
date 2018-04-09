<style lang="less">
@import "./unpay.less";
</style>

<template>
  <div class="unpay">
    <template>
      <Breadcrumb>
        <BreadcrumbItem to="/home">首页</BreadcrumbItem>
        <BreadcrumbItem to="/main/unpay">待打款</BreadcrumbItem>
      </Breadcrumb>
    </template>
    <div class="unpay-body">
      <div class="unpay-body-header">
        <DatePicker type="date" v-model="startDate" value-format="yyyy-MM-dd" placeholder="开始日期" transfer></DatePicker>
        <DatePicker type="date" v-model="endDate" value-format="yyyy-MM-dd" placeholder="结束日期" transfer></DatePicker>
        <div>
          <Button type="primary" icon="ios-search" @click="handleSearch">检索</Button>
        </div>
      </div>
      <div class="unpay-body-body">
        <edit-table refs="table2" v-model="orderList" @detailsBtn-click="detailsBtn" @on-handleContract="contractOrder" @on-handlePay="payOrder" @on-handleDone="doneOrder" @on-failure="failureOrder" :columns-list="editInlineColumns"></edit-table>
      </div>
    </div>
  </div>
</template>

<script>
import moment from "moment";
import EditTable from "../table/EditTable";
import { editInlineColumns } from "./tableConf";
export default {
  components: {
    EditTable
  },
  data() {
    return {
      startDate: "",
      endDate: "",

      orderList: [],
      listLoading: false,

      total: 0, // 总记录数
      pageSize: 20, // 每页数据条数,
      pageNumber: 1 // 当前第几页
    };
  },
  created() {
    this.editInlineColumns = editInlineColumns;
    this.handleSearch();
  },
  filters: {
    formatContractStatus(row, column) {
      return row.contractStatus == 1 ? "是" : "否";
    }
  },
  methods: {
    doneOrder(index){
      const currObj = this.orderList[index];
      if(currObj.contractStatus != '1'){
        this.$Message.error('该订单合同还未完成');
        return;
      }
      if(currObj.payStatus != '1'){
        this.$Message.error('该订单还未完成打款');
        return;
      }
      this.$ajax({
        url: "/order/orderdone",
        params: {
          uid: currObj.uid
        },
        headers: {
          "Content-Type": "application/x-www-form-urlencoded"
        }
      }).then(res => {
        if (res.data.status == 200) {
          this.$Message.success('修改成功');
          this.handleSearch();
        } else {
          if(res.data.message){
            this.$Message.error(res.data.message);
          }else{
            this.$Message.error('修改失败');
          }
        }
      });
    },
    contractOrder(index){
      const currObj = this.orderList[index];
      this.$ajax({
        url: "/order/ordercontract",
        params: {
          uid: currObj.uid
        },
        headers: {
          "Content-Type": "application/x-www-form-urlencoded"
        }
      }).then(res => {
        if (res.data.status == 200) {
          this.$Message.success('修改成功');
          this.handleSearch();
        } else {
          this.$Message.error('修改失败');
        }
      });
    },
    payOrder(index){
      const currObj = this.orderList[index];
      if(currObj.voucherStatus != '1'){
        this.$Message.error('该订单还未上传凭证');
        return;
      }
      this.$ajax({
        url: "/order/orderpay",
        params: {
          uid: currObj.uid
        },
        headers: {
          "Content-Type": "application/x-www-form-urlencoded"
        }
      }).then(res => {
        if (res.data.status == 200) {
          this.$Message.success('修改成功');
          this.handleSearch();
        } else {
          this.$Message.error('修改失败');
        }
      });
    },
    failureOrder(index){
      const currObj = this.orderList[index];
      this.$ajax({
        url: "/order/orderfailure",
        params: {
          uid: currObj.uid
        },
        headers: {
          "Content-Type": "application/x-www-form-urlencoded"
        }
      }).then(res => {
        if (res.data.status == 200) {
          this.$Message.success('修改成功');
          this.handleSearch();
        } else {
          this.$Message.error('修改失败');
        }
      });
    },
    // TODO:下面四个改成一个咯
    formatVoucherStatus(row, column) {
      return row.voucherStatus == 1 ? "是" : "否";
    },
    formatContractStatus(row, column) {
      return row.contractStatus == 1 ? "是" : "否";
    },
    handleSizeChange(val) {
      this.pageSize = val;
      this.handleSearch();
    },
    handleCurrentChange(val) {
      this.pageNumber = val;
      this.handleSearch();
    },
    /**
     * 请求待打款订单列表
     */
    handleSearch() {
      this.listLoading = true;
      let url;
      if (this.startDate && this.endDate) {
        url = `/order/orderlist?status=01&startDate=${this.$moment(
          this.startDate
        ).format("YYYY-MM-DD")}&endDate=${this.$moment(this.endDate).format(
          "YYYY-MM-DD"
        )}&pageNumber=${this.pageNumber}&pageSize=${this.pageSize}`;
      } else if(this.startDate) {
        url = `/order/orderlist?status=01&startDate=${this.$moment(
          this.startDate
        ).format("YYYY-MM-DD")}&endDate=&pageNumber=${
          this.pageNumber
        }&pageSize=${this.pageSize}`;
      } else if (this.endDate) {
        url = `/order/orderlist?status=01&startDate=&endDate=${this.$moment(
          this.endDate
        ).format("YYYY-MM-DD")}&pageNumber=${
          this.pageNumber
        }&pageSize=${this.pageSize}`;
      } else {
        url = `/order/orderlist?status=01&startDate=&endDate=&pageNumber=${this.pageNumber}&pageSize=${this.pageSize}`;
      }
      this.$ajax({
        url,
        method: "get"
      }).then(res => {
        this.listLoading = false;
        if (res.data.status == 200) {
          this.total = res.data.result.total;
          this.orderList = res.data.result.dataList;
        }
      });
    },
    /**
     * 了解详情
     */
    detailsBtn(index) {
      const currObj = this.orderList[index];
      this.$router.push({
        name: "orderDetail",
        query: {
          orderDetail: JSON.stringify(currObj)
        }
      });
    }
  }
};
</script>

<style lang="less">
.order {
  width: 100%;
  height: 100%;
  padding: 20px;
}
</style>

