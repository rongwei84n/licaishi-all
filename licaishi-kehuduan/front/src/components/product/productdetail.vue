<template>
  <div class="product-detail" v-show="showFlag">
    <div class="product-detail-header">
      <div @click="hide" class="tuihui"><-</div>
      <div>产品详情</div>
    </div>
    <div class="fengge">

    </div>
    <div class="product-detail-context">
      <ul>
        <li>
          {{productRecord.name}}
        </li>
        <li>
          预期年化率：{{productRecord.yuqi}}%
        </li>
        <li>
          期限：{{productRecord.qixian}}
        </li>
        <li>
          领域：{{productRecord.lingyu}}
        </li>
        <li>
          比例：{{productRecord.bili}}
        </li>
      </ul>
    </div>
  </div>
</template>

<script type="text/ecmascript-6">
  export default {
    props: {
      productRecord: {
        type: Object
      }
    },
    data() {
      return {
        showFlag: false
      }
    },
    methods: {
      show() {
        this.showFlag = true;
      },
      hide() {
        this.showFlag = false;
      }
    }
  }

</script>

<style lang="stylus" rel="stylesheet/stylus">
  .product-detail
    position fixed
    top 0px
    left 0px
    width 100%
    bottom 0px
    z-index 100
    background-color bisque
    .product-detail-header
      .tuihui
        font-size 26px
        font-weight 800
        color:red
</style>
