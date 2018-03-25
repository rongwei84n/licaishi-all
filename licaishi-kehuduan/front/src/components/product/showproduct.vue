<template>
  <div>
    <scroll ref="scroll" class="product-record-list" :data="productRecordList">
      <div>
        <div v-for="productRecord in productRecordList" class="product-record"
             @click="showProductDetail(productRecord,$event)">
          <div class="fenge"></div>
          <div class="product-name">
            {{productRecord.pShortName}}
          </div>
          <div class="product-info">
            <div class="product-info-flex">
              <div class="yuqi">
                {{productRecord.pExpectAnnualRevenue}}%
              </div>
              <div class="yuqi-dec">
                预期年化
              </div>
            </div>
            <div class="product-info-flex">
              <div class="qixian">
                {{productRecord.pDulTime}}个月
              </div>
              <div class="qixian-dec">
                产品期限
              </div>
            </div>
            <div class="product-info-flex">
              <div class="lingyu">
                {{productRecord.pInvestType}}
              </div>
              <div class="lingyu-dec">
                投资领域
              </div>
            </div>
            <div class="product-info-flex">
              <div class="bili">
                {{productRecord.pExpectAnnualRevenue}}%
              </div>
              <div class="bili-dec">
                返佣比例
              </div>
            </div>
          </div>
        </div>
      </div>
    </scroll>
  </div>
</template>

<script type="text/ecmascript-6">
  import Scroll from 'base/scroll/scroll'
  export default {
    props: {
      productRecordList: {
        type: Array
      }
    },
    components: {
      Scroll
    },
    methods:{
      showProductDetail(productRecord, event) {
        this.$emit('showProductDetail', productRecord);
      }
    }
  }
</script>

<style lang="stylus" rel="stylesheet/stylus">
  .product-record-list
    width 100%
    height 100%
    background #FFF
    overflow hidden
    .product-record
      height 100px
      background #fff
      .fenge
        height 6px
        background rgb(245,245,245)
      .product-name
        height 38px
        line-height 38px
        text-align left
        padding-left 1%
        font-size 13px
        font-weight 550
        border-bottom 1px solid rgb(220,220,220)

      .product-info
        display flex
        height 56px
        .product-info-flex
          flex:1
          .yuqi
            color:red
            font-size 13px
            height:22px
            line-height:22px
            margin-top 7px
          .yuqi-dec
            font-size 12px
            color :rgb(150,150,150)
            height:22px
            line-height:22px
          .qixian
            font-size 13px
            height:22px
            line-height:22px
            margin-top 7px
          .qixian-dec
            font-size 12px
            color :rgb(150,150,150)
            height:22px
            line-height:22px
          .lingyu
            font-size 13px
            height:22px
            line-height:22px
            margin-top 7px
          .lingyu-dec
            font-size 12px
            color :rgb(150,150,150)
            height:22px
            line-height:22px
          .bili
            color:red
            font-size 13px
            height:22px
            line-height:22px
            margin-top 7px
          .bili-dec
            font-size 12px
            color :rgb(150,150,150)
            height:22px
            line-height:22px
</style>
