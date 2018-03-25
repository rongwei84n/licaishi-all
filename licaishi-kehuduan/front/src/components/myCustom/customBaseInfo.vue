<template>
  <div class="custom-base-info-wrapper">
    <div class="custom-name">
      <span>姓名：{{customName}}</span>
    </div>
    <div>
      <span>手机号：{{customTel}}</span>
    </div>
    <div>
      <span>订单数：{{customOrderCount}}</span>
    </div>
    <div>
      <span>注册时间：{{customSignDate}}</span>
    </div>
  </div>
</template>

<script type="text/ecmascript-6">
  export default {
    props:{
      customName:"",
      customTel:"",
      customOrderCount:"",
      customSignDate:""
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
  .custom-base-info-wrapper
    //background bisque
    width 100%
    height 130px
    >div
      font-size 14px
      height 30px
      line-height 30px
      >span
        padding-left 10px
    .custom-name
      height 40px
      line-height 40px
      font-size 15px
      border solid 1px #EFF3F6

</style>
