<style lang="less">
@import "./unsettle.less";
</style>

<template>
  <div class="unsettle">
    <template>
      <Breadcrumb>
        <BreadcrumbItem to="/home">首页</BreadcrumbItem>
        <BreadcrumbItem to="/main/unsettle">待结佣</BreadcrumbItem>
      </Breadcrumb>
    </template>
    <div class="unsettle-body">
      <div class="unsettle-body-header">
        <DatePicker type="date" v-model="startDate" value-format="yyyy-MM-dd" placeholder="开始日期" transfer></DatePicker>
        <DatePicker type="date" v-model="endDate" value-format="yyyy-MM-dd" placeholder="结束日期" transfer></DatePicker>
        <div>
          <Button type="primary" icon="ios-search" @click="handleSearch">检索</Button>
        </div>
      </div>
      <div class="unsettle-body-body">
        <edit-table refs="table2" v-model="orderList" @detailsBtn-click="detailsBtn" @on-handleSettled="settledOrder" :columns-list="editInlineColumns"></edit-table>
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
    settledOrder(index){
      const currObj = this.orderList[index];
      this.$ajax({
        url: "/order/ordersettled",
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
        url = `/order/orderlist?status=02&startDate=${this.$moment(
          this.startDate
        ).format("YYYY-MM-DD")}&endDate=${this.$moment(this.endDate).format(
          "YYYY-MM-DD"
        )}&pageNumber=${this.pageNumber}&pageSize=${this.pageSize}`;
      } else if(this.startDate) {
        url = `/order/orderlist?status=02&startDate=${this.$moment(
          this.startDate
        ).format("YYYY-MM-DD")}&endDate=&pageNumber=${
          this.pageNumber
        }&pageSize=${this.pageSize}`;
      } else if (this.endDate) {
        url = `/order/orderlist?status=02&startDate=&endDate=${this.$moment(
          this.endDate
        ).format("YYYY-MM-DD")}&pageNumber=${
          this.pageNumber
        }&pageSize=${this.pageSize}`;
      } else {
        url = `/order/orderlist?status=02&startDate=&endDate=&pageNumber=${this.pageNumber}&pageSize=${this.pageSize}`;
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
    handleContract(index, row) {
      this.$confirm("确认该订单已完成合同?", "提示", {
        type: "warning"
      }).then(() => {
        this.$axios
          .post("/order/ordercontract", { uid: row.uid })
          .then(res => {
            if (res.data.status == 200) {
              this.$message({
                type: "success",
                message: "操作成功!"
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
            this.$message({
              showClose: true,
              message: "访问服务器异常",
              type: "warning"
            });
          });
      });
    },
    handleSettled(index, row) {
      this.$confirm("确认该订单结佣?", "提示", {
        type: "warning"
      }).then(() => {
        this.$axios
          .post("/order/ordersettled", { uid: row.uid })
          .then(res => {
            if (res.data.status == 200) {
              this.$message({
                type: "success",
                message: "操作成功!"
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
            this.$message({
              showClose: true,
              message: "访问服务器异常",
              type: "warning"
            });
          });
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

