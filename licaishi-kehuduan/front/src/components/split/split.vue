<template>
  <div class="split" :style="{height:sh+'px'}"></div>
</template>

<script type="text/ecmascript-6">
  export default {
    props: {
      sh: {
        type: Number
      }
    }
  };
</script>

<style lang="stylus" rel="stylesheet/stylus">
  .split
    width: 100%
    height: 16px
    //border-top: 1px solid rgba(7, 17, 27, 0.1)
    //border-bottom: 1px solid rgba(7, 17, 27, 0.1)
    background: #EFF3F6
</style>
