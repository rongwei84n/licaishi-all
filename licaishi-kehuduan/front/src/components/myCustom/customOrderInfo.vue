<template>
  <div>
    <div class="top-content">
      <span class="product-name">{{productName}}</span>
      <span class="product-time">{{productTime}}</span>
    </div>
    <div class="bottom-content">
      <div>
        <span class="span-red">{{loanAmount}}</span>
        <span>订单金额</span>
      </div>
      <div>
        <span>{{auEndDate}}</span>
        <span>订单日期</span>
      </div>
      <div>
        <span>{{commission}}</span>
        <span>佣金</span>
      </div>
    </div>
  </div>

</template>

<script type="text/ecmascript-6">
  export default {
    name: "order-list-item",
    props: {
      productName: "", //产品名称
      productTime: "", //产品名称
      loanAmount: "", //出借金额
      auEndDate: "", //授权到期日
      commission: "" //获得佣金
    }
  }
</script>

<style lang="stylus" rel="stylesheet/stylus">
  .top-content
    height: 36px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    .product-name
      margin-left: 4px;
      font-size: 14px;
      color:#000
    .product-time
      width: 70px;
      font-size: 12px;
      color:#686868
  .bottom-content
    display: flex;
    height: calc(100px - 40px);
    justify-content: space-around;
    align-items: center;
    .span-red
      color red
    div
      height: 90%;
      flex: 1;
      display: flex;
      flex-direction: column;
      justify-content: space-around;
      align-items: center;
      >span
        font-size: 13px;
      &:last-child

        >span:last-child
          color: #686868;
        >span:first-child
          color: red;
</style>
