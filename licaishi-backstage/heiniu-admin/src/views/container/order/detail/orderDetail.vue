<template>
  <div class="orderDetails">
    <Breadcrumb>
      <BreadcrumbItem to="/main">首页</BreadcrumbItem>
      <BreadcrumbItem to="/main/order/orderDetail">订单详情</BreadcrumbItem>
    </Breadcrumb>
    <!-- 订单详情展示模块 -->
    <div class="orderDetails-body">
      <div class="orderDetails-body-content">
        <h1>订单信息</h1>
        <div class="orderDetails-body-content-pItem">
          <span>订单编号:</span>
          {{oDetail.orderNo}}
        </div>
        <div class="orderDetails-body-content-pItem">
          <span>订单金额:</span>
          {{oDetail.amount}}
        </div>
        <div class="orderDetails-body-content-pItem">
          <span>订单日期:</span>
          {{oDetail.orderDate}}
        </div>
        <div class="orderDetails-body-content-pItem">
          <span>最迟打款日:</span>
          {{oDetail.latestPayDate}}
        </div>
        <div class="orderDetails-body-content-pItem">
          <span>理财师:</span>
          {{oDetail.financer}}
        </div>
        <div class="orderDetails-body-content-pItem">
          <span>电话:</span>
          {{oDetail.financerTel}}
        </div>
        <div class="orderDetails-body-content-pItem">
          <span>客户:</span>
          {{oDetail.customer}}
        </div>
        <div class="orderDetails-body-content-pItem">
          <span>客户身份证号:</span>
          {{oDetail.customerCardNo}}
        </div>
        <div class="orderDetails-body-content-pItem">
          <span>电话:</span>
          {{oDetail.customerTel}}
        </div>
        <div class="orderDetails-body-content-pItem">
          <span>产品:</span>
          {{oDetail.product}}
        </div>
        <div class="orderDetails-body-content-pItem">
          <span>发行机构:</span>
          {{oDetail.inst}}
        </div>
        <div class="orderDetails-body-content-pItem">
          <span>佣金比率:</span>
          {{oDetail.comRatio}}
        </div>
        <div class="orderDetails-body-content-pItem">
          <span>佣金:</span>
          {{oDetail.commission}}
        </div>
        <div class="orderDetails-body-content-pItem">
          <span>收益比率:</span>
          {{oDetail.proRatio}}
        </div>
        <div class="orderDetails-body-content-pItem">
          <span>收益:</span>
          {{oDetail.profit}}
        </div>
        <div class="orderDetails-body-content-pItem">
          <span>发卡银行:</span>
          {{oDetail.issuingBank}}
        </div>
        <div class="orderDetails-body-content-pItem">
          <span>银行卡号:</span>
          {{oDetail.cardNo}}
        </div>
        <div class="orderDetails-body-content-pItem">
          <span>合同:</span>
          {{oDetail.contractStatus == 0 ? "否" : "是"}}
        </div>
        <div class="orderDetails-body-content-pItem">
          <span>凭证:</span>
          <span v-if="oDetail.voucherStatus == 1">
            <a :href="$imgUrl + 'voucher/' + oDetail.voucherPath" target="_blank">
              <img :src="$imgUrl + 'voucher/' + oDetail.voucherPath" style="width:80px;"/>
            </a>
          </span>
          <span v-else>
            未上传
          </span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Util from "@/libs/util";
export default {
  data() {
    return {
      oDetail: "" //订单对象
    };
  },
  methods: {
    // 获取产品详情
    getOrderDetail() {
      if (this.$route.query.orderDetail) {
        this.oDetail = JSON.parse(this.$route.query.orderDetail);
      }
    }
  },
  created() {
    this.getOrderDetail();
  }
};
</script>

<style lang="less">
@import "./orderDetail.less";
</style>

