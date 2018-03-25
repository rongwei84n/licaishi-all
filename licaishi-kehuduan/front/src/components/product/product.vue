<template>
  <div class="product" ref="product">
    <!--
    <scroll ref="scroll" class="product-content" :data="productTypeList">
      <div class="product-list">
        <div v-for="productType in productTypeList" class="productType" >
          <span @click="setProductType($event)">{{productType}}</span>
        </div>
      </div>
    </scroll>-->
    <productKinds @showProductList="showProductList" class="product-kinds"
    ></productKinds>
    <!--<router-view></router-view>
    <div class="product-select">
      <div v-for="selectType in productSelectList" class="product-select-div">
        {{selectType}}
      </div>
    </div>-->
    <ShowProduct :productRecordList="productRecordList" class="product-record-list-v"
    @showProductDetail="showProductDetail"></ShowProduct>
    <!--<scroll ref="scroll" class="product-record-list" :data="productRecordList">
    <div>
      <div v-for="productRecord in productRecordList" class="product-record"
           @click="showProductDetail(productRecord,$event)">
        <div class="fenge"></div>
        <div class="product-name">
          {{productRecord.name}}
        </div>
        <div class="product-info">
          <div class="product-info-flex">
            <div class="yuqi">
              {{productRecord.yuqi}}%
            </div>
            <div class="yuqi-dec">
              预期年化
            </div>
          </div>
          <div class="product-info-flex">
            <div class="qixian">
              {{productRecord.qixian}}个月
            </div>
            <div class="qixian-dec">
              产品期限
            </div>
          </div>
          <div class="product-info-flex">
            <div class="lingyu">
              {{productRecord.lingyu}}
            </div>
            <div class="lingyu-dec">
              投资领域
            </div>
          </div>
          <div class="product-info-flex">
            <div class="bili">
              {{productRecord.bili}}%
            </div>
            <div class="bili-dec">
              返佣比例
            </div>
          </div>
        </div>
      </div>
    </div>
    </scroll>-->
    <ProductDetail ref="productDetail" :productRecord="selectProduct"></ProductDetail>
  </div>
</template>

<script type="text/ecmascript-6">
  import Scroll from 'base/scroll/scroll'
  import productKinds from 'components/product/productKinds'
  import ProductDetail from 'components/product/productDetail'
  import ShowProduct from 'components/product/showProduct'
  export default {
    data () {
      return {
        productTypeList: [],
        productSelectList: ['全部','佣金','收益','筛选'],
        productRecordList:[],
        selectProduct:{}
      }
    },
    created:function () {
      //this.weiwei()
    },
    methods:{
      showProductDetail(productRecord) {
        this.selectProduct = productRecord;
        this.$refs.productDetail.show();
        //this.selectedFood = food;

        //this.$refs.food.show();
      },
      showProductList(data){
        this.productRecordList = data;
      }
    },
    components: {
      Scroll,
      productKinds,
      ProductDetail,
      ShowProduct
    }
  }
</script>

<style lang="stylus" rel="stylesheet/stylus">
  .product
    position absolute
    width 100%
    top 88px
    bottom 71px
    overflow hidden
    display flex
    flex-direction:column
    .product-kinds
      flex 0 0 auto
      //white-space:nowrap
      //position relative
      overflow hidden
      //height:40px

    .product-record-list-v
      flex 1
      position: relative
      bottom 0px
      overflow hidden
</style>
