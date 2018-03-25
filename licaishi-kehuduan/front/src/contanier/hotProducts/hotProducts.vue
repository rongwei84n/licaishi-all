/*
 * @Author: 张浩然 
 * @Date: 2018-03-07 19:23:27 
 * @Last Modified by: 张浩然
 * @Last Modified time: 2018-03-18 11:35:24
 *
 * 热销产品||最新推荐
 */

 <template>
  <div id="hotProducts">
    <mt-header :title="title">
      <mt-button icon="back" @click="back" slot="left"></mt-button>
    </mt-header>
    <Scroll id="scroll" :data="recommendProductsList" :pullup="pullup">
      <div class="body">
        <product-item v-for="(item,index) in recommendProductsList" :key="index" :pCode="item.pCode" :pShortName="item.pShortName" :pExpectAnnualRevenue="item.pExpectAnnualRevenue" :pSaleStatus="item.pSaleStatus" :pDulTime="item.pDulTime" :pInvestType="item.pInvestType" :pCommission="item.pCommission"></product-item>
        <!-- <loading v-if="pullup"></loading>
        <to-end v-else></to-end> -->
      </div>
    </Scroll>
    <!-- 推荐 -->
    <!-- <footer class="footer">
      <span class="submit">提交</span>
    </footer> -->
  </div>
</template>

 <script type="es6">
import axios from "axios";``
import Scroll from "base/scroll/scroll";
import loading from "base/loading/loading";
import toEnd from "base/toEnd/toEnd";
import whiteSpace from "base/whiteSpace/whiteSpace";
import productItem from "components/productItem/productItem";

export default {
  props: {},
  data() {
    return {
      pullup: false, //开启上拉加载
      recommendType: "",
      recommendProductsList: [] //产品列表
    };
  },
  created() {
    this.recommendType = parseInt(this.$route.query.recommendType);
    this.recommendProducts(this.recommendType);
  },
  computed: {
    title() {
      switch (this.recommendType) {
        case 1:
          return "热销产品";
          break;
        case 2:
          return "最新推荐";
          break;
        default:
          break;
      }
    }
  },
  methods: {
    /**
     * 返回按钮单机事件
     */
    back() {
      this.$router.go(-1);
    },
    // 请求推荐热销产品列表
    recommendProducts(recommendType) {
      axios({
        url: `http://47.97.100.240/srv/v1/product/recommendProducts?recommendType=${recommendType}`,
        method: "get",
        timeout: 10000
      }).then(res => {
        if (res.status === 200) {
          this.recommendProductsList = res.data.result;
        }
      });
    }
  },
  components: {
    Scroll,
    whiteSpace,
    loading,
    toEnd,
    productItem
  }
};
</script>

<style lang="stylus">
@import '~common/stylus/variable';

#hotProducts {
  position: relative;
  height: 100%;
  width: 100%;
  z-index: 100;
  background-color: $color-background;

  #scroll {
    position: absolute;
    top: 50px;
    bottom: 50px;
    left: 0;
    right: 0;

    .body {
      margin-top: 6px;
      background-color: $color-background-d;
    }
  }

  .footer {
    height: 40px;
    position: absolute;
    bottom: 0;
    width: 100%;
    z-index: -1;
  }
}
</style>

 
 
