/*
 * @Author: 张浩然 
 * @Date: 2018-03-04 23:04:51 
 * @Last Modified by: 张浩然
 * @Last Modified time: 2018-03-18 11:46:11
 * 产品页-产品组件
 */

<template>
  <div class="main" @click="to_pDetails">
    <div class="top-content">
      <span class="product-name">{{productItem.pShortName}}</span>
      <!-- TODO:此处根据状态替换icon -->
      <img :src="get_pSaleStatus" alt="状态">
    </div>
    <div class="bottom-content">
      <div>
        <span class="span-red">{{productItem.pExpectAnnualRevenue}}</span>
        <span>预期年化</span>
      </div>
      <div>
        <span>{{productItem.pDulTime}}个月</span>
        <span>产品期限</span>
      </div>
      <div>
        <span>{{get_pInvestType}}</span>
        <span>投资领域</span>
      </div>
      <div>
        <span class="span-red">{{productItem.pCommission}}</span>
        <span>返佣比例</span>
      </div>
    </div>
  </div>
</template>

<script type="es6">
export default {
  props: {
    /* 产品组件所需要的对象 */
    productItem: {
      type: Object,
      default: function() {
        return {
          pShortName: "", //产品简称
          pSaleStatus: "", //产品销售状态 01：预热中 02：募集中 03：募集结束 04：产品成立
          pExpectAnnualRevenue: "", //年化率
          pDulTime: "", //投资期限
          pInvestType: "" //投资领域}
        };
      },
      required: true
    }
  },
  computed: {
    get_pSaleStatus() {
      // 非私募产品
      switch (this.productItem.pSaleStatus) {
        case "01":
          return "../../../static/image/product_status/preparing.png";
        case "02":
          return "../../../static/image/product_status/funding.png";
        case "03":
          return "../../../static/image/product_status/finish.png";
        // case "04":
        //   return "../../../static/image/product_status/complete.png";
      }
    },
    get_pInvestType() {
      switch (this.productItem.pInvestType) {
        case "01":
          return "房地产类";
        case "02":
          return "金融市场";
        case "03":
          return "基础设施";
        case "04":
          return "资金池";
        case "05":
          return "工商企业";
        case "00":
          return "其他";
        default:
          break;
      }
    }
  },
  methods: {
    /**
     *
     * @event 跳转到产品详情
     */
    to_pDetails() {
      // 如果是私募产品则必须登录
      if (this.productItem.pType === "04") {
        this.$ajax({
          url: `/srv/v1/login_status`,
          method: "GET"
        }).then(res => {
          if (res.data.status === this.$store.state.status) {
            this.$router.push({
              name: "pDetails",
              query: { pCode: this.productItem.pCode }
            });
          }
        });
      } else {
        this.$router.push({
          name: "pDetails",
          query: { pCode: this.productItem.pCode }
        });
      }
      // // 本地测试用
      // this.$router.push({
      //   name: "pDetails",
      //   query: { pCode: this.productItem.pCode }
      // });
    }
  }
};
</script>

<style lang="stylus" scoped>
.main {
  height: 120px;
  width: 100vw;

  span {
    font-size: 14px;
    color: #000000;
  }

  span:first-child {
    font-size: 16px;
  }

  .span-red {
    color: red;
  }

  .top-content {
    height: 40px;
    border-bottom: 1px solid #dce0e3;
    display: flex;
    justify-content: space-between;
    align-items: center;

    .product-name {
      margin-left: 4px;
      font-size: 16px;
    }

    >img {
      height: 22px;
      width: 22px;
    }
  }

  .bottom-content {
    display: flex;
    height: calc(120px - 40px);
    border-bottom: 4px solid #eff3f6;
    justify-content: space-around;
    align-items: center;

    >div {
      height: 70%;
      flex: 1;
      display: flex;
      flex-direction: column;
      justify-content: space-around;
      align-items: center;

      >span:last-child {
        color: #686868;
      }
    }
  }
}
</style>


