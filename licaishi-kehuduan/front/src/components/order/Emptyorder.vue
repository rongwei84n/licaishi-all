<template>
  <div class="container">
    <img class="img" src="~@/common/image/no_order.png"/>
    <div class="no_content">没有相关的订单，快去下单吧</div>
  </div>
</template>

<script>
  export default {
    name: "emptyorder"
  }
</script>

<style lang="stylus" rel="stylesheet/stylus">
  .container {
    .img {
      padding-top: 32pt;
      width: 121pt;
      height 158pt;
    }
  }
</style>
