<template>
  <div class="product-kind">
    <scroll ref="scroll" class="product-wrapper" :data="productKinds">
      <ul class="product-wrapper-ul">
        <!--<li v-for="productKind in productKinds" class="productKind" >
          <span>{{productKind}}</span>
        </li>-->
        <li class="productKind" :class="{kindActive:kindActiveCode===1}" @click="selectProductKind(1,$event)" >
            <span>集合信托</span>
        </li>
        <li class="productKind" :class="{kindActive:kindActiveCode===2}" @click="selectProductKind(2,$event)">
            <span>集合资管</span>
        </li>
        <li class="productKind" :class="{kindActive:kindActiveCode===3}" @click="selectProductKind(3,$event)">
            <span>债券基金</span>
        </li>
        <li class="productKind" :class="{kindActive:kindActiveCode===4}" @click="selectProductKind(4,$event)">
            <span>阳光私募</span>
        </li>
        <li class="productKind" :class="{kindActive:kindActiveCode===5}" @click="selectProductKind(5,$event)">
            <span>股权基金</span>
        </li>
      </ul>
    </scroll>
    <div v-show="showFlag" class="product-select">
      <ul class="product-select-ul">
        <li class="product-select-ul-li" :class="{shaixuan:shaixuancode===1}" @click="shaixuan(1,$event)">
          全部
        </li>
        <li class="product-select-ul-li" :class="{shaixuan:shaixuancode===2}" @click="shaixuan(2,$event)">
          佣金
        </li>
        <li class="product-select-ul-li" :class="{shaixuan:shaixuancode===3}" @click="shaixuan(3,$event)">
          收益
        </li>
        <li class="product-select-ul-li" @click="showTiaojian()">
          筛选
        </li>
      </ul>
    </div>
    <transition name="tiaojian">
      <div class="tiaojian-wrapper" v-show="tiaojianshow">
        <ul>
          <li class="tiaojian-content">
            <div class="tiaojian-title">产品期限</div>
            <div class="tiaojian-info">
              <div class="tiaojian-info-one" v-for="item in items">{{item}}</div>
            </div>
          </li>
          <li class="tiaojian-content">
            <div class="tiaojian-title">产品期限</div>
            <div class="tiaojian-info">
              <div class="tiaojian-info-one" v-for="item in items">{{item}}</div>
            </div>
          </li>
        </ul>
        <div class="tiaojian-bottom">
          <div class="quxiao" @click="hideTiaojian">取 消</div>
          <div class="queding">确 定</div>
        </div>
      </div>
    </transition>
    <transition name="fade">
      <div class="list-mask" @click="hideTiaojian" v-show="tiaojianshow"></div>
    </transition>
  </div>
</template>

<script type="text/ecmascript-6">
  import Scroll from 'base/scroll/scroll'
  export default {
    data () {
      return {
        productKinds: [],
        showFlag:true,
        kindActiveCode:1,
        shaixuancode:1,
        neturl: 'http://47.97.100.240/',
        productData:[],
        items:[],
        tiaojianshow:false
      }
    },
    created:function () {
      this._initProductKins();
      this._initItems();
      //this.mockData()
      //this.$emit('showProductList',this.productData);
    },
    computed: {

    },
    methods:{
      _initProductKins(){
        this.productKinds.push('集合信托','集合资管','债券基金','阳光私募','股权基金');
      },
      _initItems(){
        this.items.push('不限','1-6个月','6-12个月','1-2年','2-3年','3年以上','5年以上');
      },
      showTiaojian(){
        this.tiaojianshow = true;
      },
      selectProductKind(num,event){
          this.kindActiveCode = num;
          this.shaixuancode=1
          this.searchProduct();
      },
      shaixuan(num,event){
        this.shaixuancode = num;
        this.searchProduct();
      },
      hideTiaojian(){
        this.tiaojianshow = false
      },
      searchProduct(){
        //this.$route.push({path:'/product/productshow',params:{productkind:this.kindActiveCode,producttype:this.shaixuancode}});

        //this.$router.go({name: 'user', params: {userId: 1}});
        let _this = this;
        window.phihome.util.netRequest("get", _this.neturl + 'srv/v1/product/list?pageNo=1&pageSize=10&type=03', '', '',
          function (response) {
            response = JSON.parse(response);
            if (response.errorCode == 0) { //网络请求成功
              let netResponse = JSON.parse(response.netResponse);
              if (netResponse.status == 200) { //获取账号成功
                _this.productData = netResponse.result.list;
                _this.$emit('showProductList',_this.productData);
              } else {
                _this.name = '未设置';
              }
            } else {
              _this.name = '未登录';
            }
          })
        //this.mockData();
        //alert(this.$emit)
        //this.$emit('showProductList',this.productData);
      },
      mockData(){
        this.productData=[];
        if(this.kindActiveCode===1) {
          this.productData.push({
              name: '九州期货-优选2号',
              yuqi: '9',
              qixian: '12',
              lingyu: '工商企业',
              bili: '5'
            },
            {
              name: '九州期货-3号',
              yuqi: '9',
              qixian: '12',
              lingyu: '工商企业',
              bili: '5'
            },
            {
              name: '九州期货-优选4号',
              yuqi: '9',
              qixian: '12',
              lingyu: '工商企业',
              bili: '5'
            },
            {
              name: '你麻痹优选2号',
              yuqi: '9',
              qixian: '12',
              lingyu: '工商企业',
              bili: '5'
            },
            {
              name: '九州期货-优选2号',
              yuqi: '9',
              qixian: '12',
              lingyu: '工商企业',
              bili: '5'
            },
            {
              name: '九州期货-优选2号',
              yuqi: '9',
              qixian: '12',
              lingyu: '工商企业',
              bili: '5'
            },
            {
              name: '九州期货-优选2号',
              yuqi: '9',
              qixian: '12',
              lingyu: '工商企业',
              bili: '5'
            },
            {
              name: '九州期货-优选2号',
              yuqi: '9',
              qixian: '12',
              lingyu: '工商企业',
              bili: '5'
            }
          )
        }else  if(this.kindActiveCode===2){
          this.productData.push({
            name: '九州期货-优选20号',
            yuqi: '9',
            qixian: '12',
            lingyu: '工商企业',
            bili: '5'
          })
        }else  if(this.kindActiveCode===3&&this.shaixuancode===2){
          this.productData.push({
            name: '九州期货-优选32号',
            yuqi: '9',
            qixian: '12',
            lingyu: '工商企业',
            bili: '5'
          })
        }else{
          this.productData.push({
            name: '九州期货-其他',
            yuqi: '9',
            qixian: '12',
            lingyu: '工商企业',
            bili: '5'
          })
        }
      }
    },
    components: {
      Scroll
    }
  }
</script>

<style lang="stylus" rel="stylesheet/stylus">
  .product-kinds
    width 100%
    height 66px
    .product-wrapper
      //overflow hidden
      width 100%
      height 40px
      background #f5f5f5
      border-top 1px solid red
      white-space:nowrap
      font-size 0
      .product-wrapper-ul
        .productKind
          display inline-block
          height 40px
          text-align center
          line-height 40px
          font-size 15px
          width 100px
          //background blue
        .kindActive
          background red
    .product-select
      width 100%
      height 26px
      background #FFF
      .product-select-ul
        display flex
        width 100%
        height 100%
        line-height 26px
        align-items:center
        .product-select-ul-li
          flex 1
          display table-cell
          text-align center
          border-right 1px solid #7e8c8d
          height 18px
          line-height 18px
          font-size 13px
          &:last-child
            border-right none

        .shaixuan
          color:red

    .tiaojian-wrapper
      position fixed
      left 0px
      bottom -65%
      background beige
      width 100%
      z-index 100
      height 65%
      transform: translate3d(0, -100%, 0)
      &.tiaojian-enter-active, &.tiaojian-leave-active
        transition: all 1s
      &.tiaojian-enter, &.tiaojian-leave-active
        transform: translate3d(0, 0, 0)
      .tiaojian-bottom
        position absolute
        bottom 0px
        height 30px
        width 100%

        display flex
        .quxiao,.queding
          flex 1
          background #7e8c8d
          line-height 30px
          text-align  center
        .queding
          background red
      .tiaojian-content
        border-bottom solid 1px blueviolet
        .tiaojian-title
          height 50px
          line-height 50px
          font-size 14px
        .tiaojian-info
          .tiaojian-info-one
            display inline-block
            border solid 1px brown
            padding 2px 10px
            border-radius: 50px
            font-size 14px
            margin 0px 8px 14px


    .list-mask
      position: fixed
      top: 0
      left: 0
      width: 100%
      height: 100%
      z-index: 99
      backdrop-filter: blur(10px)
      opacity: 1
      background: rgba(7, 17, 27, 0.6)
      &.fade-enter-active, &.fade-leave-active
        transition: all 0.5s
      &.fade-enter, &.fade-leave-active
        opacity: 0
        background: rgba(7, 17, 27, 0)
</style>
