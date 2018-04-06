<style lang="less">
@import "./settled.less";
</style>

<template>
  <div class="settled">
    <template>
      <Breadcrumb>
        <BreadcrumbItem to="/home">首页</BreadcrumbItem>
        <BreadcrumbItem to="/main/settled">已结佣</BreadcrumbItem>
      </Breadcrumb>
    </template>
    <div class="settled-body">
      <div class="settled-body-header">
        <DatePicker type="date" v-model="startDate" value-format="yyyy-MM-dd" placeholder="开始日期" transfer></DatePicker>
        <DatePicker type="date" v-model="endDate" value-format="yyyy-MM-dd" placeholder="结束日期" transfer></DatePicker>
        <div>
          <Button type="primary" icon="ios-search" @click="handleSearch">检索</Button>
        </div>
      </div>
      <div class="settled-body-body">
        <edit-table refs="table2" v-model="orderList" @detailsBtn-click="detailsBtn" :columns-list="editInlineColumns"></edit-table>
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
        url = `/order/orderlist?status=03&startDate=${this.$moment(
          this.startDate
        ).format("YYYY-MM-DD")}&endDate=${this.$moment(this.endDate).format(
          "YYYY-MM-DD"
        )}&pageNumber=${this.pageNumber}&pageSize=${this.pageSize}`;
      } else if(this.startDate) {
        url = `/order/orderlist?status=03&startDate=${this.$moment(
          this.startDate
        ).format("YYYY-MM-DD")}&endDate=&pageNumber=${
          this.pageNumber
        }&pageSize=${this.pageSize}`;
      } else if (this.endDate) {
        url = `/order/orderlist?status=03&startDate=&endDate=${this.$moment(
          this.endDate
        ).format("YYYY-MM-DD")}&pageNumber=${
          this.pageNumber
        }&pageSize=${this.pageSize}`;
      } else {
        url = `/order/orderlist?status=03&startDate=&endDate=&pageNumber=${this.pageNumber}&pageSize=${this.pageSize}`;
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

