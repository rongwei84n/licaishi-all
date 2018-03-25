<template>
  <div class="my-commission-info-wrapper">
    <div class="my-commission-info-header-content">
      <div>{{myDate}}</div>
      <div>佣金：<span>￥{{myComAmount}}</span></div>
    </div>
    <div class="my-commission-info-bottom-content">

      <div >
        <span>订单号：{{orderId}}</span>
      </div>
      <div>
        <span>订单金额：{{orderAmount}}</span>
      </div>
      <div>
        <span>产品名称：{{customTep}}</span>
      </div>
      <div>
        <span>客户姓名：{{customName}}</span>
      </div>

    </div>
  </div>
</template>

<script type="text/ecmascript-6">
  export default {
    props:{
      myDate:"",
      myComAmount:"",
      orderId:"",
      orderAmount:"",
      customName:"",
      customTep:""
    },
    data() {
      return {}
    },
    components: {
    },
    methods: {
      back(){
        this.$router.go(-1)
      }
    }
  }
</script>

<style lang="stylus" rel="stylesheet/stylus">
  .my-commission-info-wrapper
  //background bisque
    width 100%
    height 160px
    .my-commission-info-header-content
      border solid 1px #EFF3F6
      height 40px
      display flex
      align-items center
      font-size 15px
      div
        flex 1
        padding-left 10px
        span
          color red
        &:last-child
          text-align right
          padding-right 10px

    .my-commission-info-bottom-content
      >div
        font-size 14px
        //background red
        height 30px
        line-height 30px
        >span
          padding-left 10px

</style>
