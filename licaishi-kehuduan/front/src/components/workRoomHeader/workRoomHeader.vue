<template>
  <div>
    <mt-header :title="mytitle">
      <mt-button icon="back" @click="back" slot="left"></mt-button>
      <mt-button slot="right" @click="rightFunc" v-show="rightFlag">{{content}}</mt-button>
    </mt-header>
  </div>
</template>

<script type="text/ecmascript-6">
  export default {
    props:{
      mytitle:"",
      content:"",
      rightFlag:false
    },
    methods:{
      back(){
        this.$emit('back')
      },
      rightFunc(){
        this.$emit('clickFunc')
      }
    }
  }
</script>

<style lang="stylus" rel="stylesheet/stylus">
  .mint-header
    color #000000
    background-color #FFF
    height 50px
  .mint-cell
    min-height 40px
  //border-bottom 1px solid #eff3f6
    .mint-cell-title
      width auto
      margin-left 10px
      .mint-cell-text
        font-size 13px
    .mint-cell-value
      margin-right 10px
    .mint-field-core
      font-size 13px
    .mint-cell-wrapper
      width 100%
      background-position bottom right
      padding 0px
</style>
