<template>
  <div class="custom-order-detail">
    <workRoomHeader @back="back"  mytitle="客户订单详情" ></workRoomHeader>
    <split :sh="8"></split>
    <mt-cell title="产品名称" >{{orderDetail.productShortName}}</mt-cell>
    <mt-cell title="客户姓名" >{{orderDetail.customerName}}</mt-cell>
    <mt-cell title="订单金额" >{{orderDetail.amount}}</mt-cell>
    <!--<mt-cell title="期待年回报率" >{{orderDetail.proRatio}}</mt-cell>
    <mt-cell title="期待回报" >{{orderDetail.proRatio}}</mt-cell>-->
    <mt-cell title="佣金" >{{orderDetail.commission}}</mt-cell>
    <mt-cell title="订单日期" >{{orderDetail.orderDate}}</mt-cell>
    <mt-cell title="订单号" >{{orderDetail.orderNo}}</mt-cell>
    <mt-cell title="交易状态" >{{cDisplayStatus}}</mt-cell>

  </div>
</template>

<script type="text/ecmascript-6">
  import workRoomHeader from "components/workRoomHeader/workRoomHeader";
  import split from "components/split/split";
  export default {
    data() {
      return {
        orderDetail:""
      }
    },
    //props:{
      //orderDetail:""
    //},
    methods: {
      back() {
        this.$router.go(-1)
        //this.$emit('closeDetail')
      }
    },
    created(){
      this.orderDetail = this.$route.query
    },
    computed:{
      cDisplayStatus(){ //订单状态
        if(this.orderDetail.status === "01") {
          return "待打款";
        }else if(this.orderDetail.status === "02") {
          return "待结佣";
        }else if(this.orderDetail.status === "03") {
          return "已结佣";
        }else if(this.orderDetail.status === "99") {
          return "已失效";
        }
        return this.orderDetail.status;
      }
    },

    components: {
      workRoomHeader,
      split
    }
  }
</script>

<style lang="stylus" rel="stylesheet/stylus">
  .custom-order-detail
    position absolute
    top 0px
    width 100%
    height 100vh
    background #FFF
    z-index 1
    .mint-cell-text
      font-size:14px
    .mint-cell-value
      font-size:14px
</style>
