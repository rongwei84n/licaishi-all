<template>
  <div id="recommend">
    <scroll ref="scroll" class="recommend-content" :data="newRecommendProductsList">
      <div>
        <div v-if="recommends.length" class="slider-wrapper" ref="sliderWrapper">
          <mt-swipe :auto="4000">
            <mt-swipe-item v-for="(item,index) in recommends" :key="index">
              <a :href="item.link_url">
                <img class="needsclick" @load="loadImage" :src="item.url">
              </a>
            </mt-swipe-item>
          </mt-swipe>
        </div>
        <!-- 首页功能导航菜单 -->
        <div class="nav-func">
          <ul class="ofh">
            <li class="fl" @click="to_NanProducts(1)">
              <img src="~@/common/image/icon_17.jpg" alt=""><br>
              <span>热销产品</span>
            </li>
            <li class="fl" @click="to_NanProducts(2)">
              <img src="~@/common/image/icon_19.jpg" alt=""><br/>
              <span>最新推荐</span>
            </li>
            <li @click="toFuncPage('product',0)" class="fl">
              <img src="~@/common/image/icon_21.jpg" alt=""><br/>
              <span>集合信托</span>
            </li>
            <!--  -->
            <li @click="toFuncPage('product',1)" class="fl">
              <img src="~@/common/image/icon_23.jpg" alt=""><br/>
              <span>集合资管</span>
            </li>
            <li @click="toFuncPage('product',2)" class="fl">
              <img src="~@/common/image/icon_29.jpg" alt=""><br>
              <span>债券基金</span>
            </li>
            <li @click="toFuncPage('product',3)" class="fl">
              <img src="~@/common/image/icon_30.jpg" alt=""><br/>
              <span>股权私募</span>
            </li>
            <!-- <li @click="toFuncPage('product',4)" class="fl">
              <img src="~@/common/image/icon_31.jpg" alt=""><br/>
              <span>股权基金</span>
            </li> -->
            <!--
            <li @click="toFuncPage" class="fl">
              <img src="~@/common/image/icon_32.jpg" alt=""><br/>
              <span>视频路演</span>
            </li> -->
          </ul>
        </div>
        <!-- 热销产品区域 -->
        <module-title title="热销产品" iconUrl="../../../static/image/icon_17.jpg" right @moreClick="to_NanProducts(1)"></module-title>
        <product-item v-for="(item,index) in recommendProductsList" :key="index" :pCode="item.pCode" :pShortName="item.pShortName" :pExpectAnnualRevenue="item.pExpectAnnualRevenue" :pSaleStatus="item.pSaleStatus" :pDulTime="item.pDulTime" :pInvestType="item.pInvestType" :pCommission="item.pCommission"></product-item>
        <!-- 广告区域 -->
        <img src="../../common/image/body.png" alt="" class="advertising">
        <!-- 最新推荐产品区域 -->
        <div class="newRecommendProducts-content">
          <module-title title="最新推荐" iconUrl="../../../static/image/icon_17.jpg" right @moreClick="to_NanProducts(2)"></module-title>
          <product-item v-for="(item,index) in newRecommendProductsList" :key="index" :pCode="item.pCode" :pShortName="item.pShortName" :pExpectAnnualRevenue="item.pExpectAnnualRevenue" :pSaleStatus="item.pSaleStatus" :pDulTime="item.pDulTime" :pInvestType="item.pInvestType" :pCommission="item.pCommission"></product-item>
        </div>
      </div>
    </scroll>
    <router-view></router-view>
  </div>
</template>

<script type="text/ecmascript-6">
import ajax from "api/ajax";
import axios from "axios";
import Loading from "base/loading/loading";
import Scroll from "base/scroll/scroll";
import { getRecommend } from "api/recommend";
import { ERR_OK } from "api/config";
import moduleTitle from "components/moduleTitle/moduleTitle";
import productItem from "components/productItem/productItem";

export default {
  data() {
    return {
      recommends: [], //广告轮播图
      recommendProductsList: [], //热销产品列表
      newRecommendProductsList: [] //最新推荐产品列表
    };
  },
  created() {
    // this._getRecommend();
    this.get_banners();
    // this._getDiscList();
    this.recommendProducts();
    this.newRecommendProducts();
  },
  methods: {
    /**
     * 导航栏点击事件
     */
    toFuncPage(url, params) {
      if (url) {
        this.$router.push({
          path: `/${url}`,
          query: { activeIndex: params }
        });
      }
    },
    //
    to_NanProducts(recommendType) {
      this.$router.push({
        path: "/hotProducts",
        query: { recommendType: recommendType }
      });
    },
    loadImage() {
      if (!this.checkloaded) {
        this.checkloaded = true;
        this.$refs.scroll.refresh();
      }
    },
    /**
     * 获取首页banner
     */
    get_banners() {
      axios({
        withCredentials: true,
        // TOOD:路径前缀
        url: "http://47.97.100.240//srv/v1/get_banners",
        method: "get",
        timeout: 10000
      }).then(res => {
        if (res.status === 200) {
          this.recommends = res.data.result.list;
        }
      });
    },
    // 请求推荐热销产品列表
    recommendProducts() {
      axios({
        withCredentials: true,
        // TOOD:路径前缀
        url:
          "http://47.97.100.240/srv/v1/product/recommendProducts?recommendType=2",
        method: "get",
        timeout: 10000
      }).then(res => {
        if (res.status === 200) {
          this.recommendProductsList = res.data.result.slice(0, 4);
        }
      });
    },
    // 获取最新推荐产品列表
    newRecommendProducts() {
      axios({
        withCredentials: true,
        // TOOD:路径前缀
        url:
          "http://47.97.100.240/srv/v1/product/recommendProducts?recommendType=1",
        method: "get",
        timeout: 10000
      }).then(res => {
        if (res.status === 200) {
          this.newRecommendProductsList = res.data.result.slice(0, 4);
        }
      });
    }
  },
  components: {
    // Slider,
    Loading,
    Scroll,
    moduleTitle,
    productItem
  }
};
</script>

<style lang="stylus" rel="stylesheet/stylus">
@import '~common/stylus/variable';

#recommend {
  position: relative;
  width: 100%;
  height: 100%;

  .recommend-content {
    position: absolute;
    // top: 40px;
    top: 0;
    bottom: 46px;
    background: #fff;
    overflow: hidden;
    width: 100%;

    .nav-func {
      padding-top: 18px;
      background-color: #eff3f6;

      .ofh {
        display: flex;
        flex-wrap: wrap;
        width: 100%;

        li {
          width: 33%;
          // width: 25%;
          text-align: center;
          margin-bottom: 19px;
          font-size: 14px;

          img {
            width: 48px;
            padding-bottom: 6px;
          }
        }
      }
    }

    // 广告区域
    .advertising {
      position: relative;
      width: 100vw;
      height: 86px;
      border-top: 8px solid #eff3f6;
      border-bottom: 8px solid #eff3f6;
      top: -4px;
    }

    // 最新推荐产品区域
    .newRecommendProducts-content {
      position: relative;
      top: -4px;
    }

    /*
     * 轮播图组件
     */
    .slider-wrapper {
      height: 180px;
      width: 100%;
      overflow: hidden;

      img {
        height: 190px;
        width: 100%;
      }
    }

    .recommend-list {
      .list-title {
        height: 65px;
        line-height: 65px;
        text-align: center;
        font-size: $font-size-medium;
        color: $color-theme;
      }

      .item {
        display: flex;
        box-sizing: border-box;
        align-items: center;
        padding: 0 20px 20px 20px;

        .icon {
          flex: 0 0 60px;
          width: 60px;
          padding-right: 20px;
        }

        .text {
          display: flex;
          flex-direction: column;
          justify-content: center;
          flex: 1;
          line-height: 20px;
          overflow: hidden;
          font-size: $font-size-medium;

          .name {
            margin-bottom: 10px;
          }
        }
      }
    }

    .loading-container {
      position: absolute;
      width: 100%;
      top: 50%;
      transform: translateY(-50%);
    }
  }
}
</style>
