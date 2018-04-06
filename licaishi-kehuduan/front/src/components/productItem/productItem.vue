/*
 * @Author: 张浩然 
 * @Date: 2018-03-13 22:45:10 
 * @Last Modified by: 张浩然
 * @Last Modified time: 2018-03-27 23:28:30
 * 首页--产品组件
 */

<template>
    <div class="pro-content" @click="to_pDetails">
        <!-- <div class="pro-content-yqnh">
            <span class="span-key">预期年化</span>
            <span class="span-value annual-rate">{{pExpectAnnualRevenue}}</span>
        </div> -->
        <div class="pro-header-content">
            <div class="title-content">
                <span class="type">集合资管</span>
            </div>
            <div class="status-content">
                <span class="name">{{pShortName}}</span>
                <!-- TODO:此处根据状态替换icon -->
                <img :src="get_pSaleStatus" alt="状态">
            </div>
        </div>
        <div class="pro-main">
            <div class="main-left-content">
                <span class="span-key">预期年化</span>
                <span class="span-value annual-rate">{{pExpectAnnualRevenue}}</span>
            </div>
            <div class="main-right-content">
                <div>
                    <span class="span-key">产品期限</span>
                    <span class="span-value">{{pDulTime}}个月</span>
                </div>
                <div>
                    <span class="span-key">投资领域</span>
                    <span class="span-value">{{get_pInvestType}}</span>
                </div>
                <div>
                    <span class="span-key">返佣比例</span>
                    <span class="span-value">{{pCommission}}</span>
                </div>
            </div>
        </div>
    </div>
</template>

<script type="es6">
export default {
  props: {
    pCode: {
      type: String,
      default: "",
      required: true
    },
    pShortName: "", //产品简称
    pSaleStatus: "", //产品销售状态 01：预热中 02：募集中 03：募集结束 04：产品成立
    pExpectAnnualRevenue: "", //年化率
    pDulTime: "", //投资期限
    pInvestType: "", //投资领域
    pCommission: "" //返佣比例
  },
  methods: {
    to_pDetails() {
      this.$router.push({
        name: "pDetails",
        query: { pCode: this.pCode }
      });
    }
  },
  computed: {
    get_pSaleStatus() {
      switch (this.pSaleStatus) {
        case "01":
          return "../../../static/image/product_status/preparing.png";
        case "02":
          return "../../../static/image/product_status/funding.png";
        case "03":
          return "../../../static/image/product_status/finish.png";
        /* 这期不做 */
        // case "04":
        //   return "../../../static/image/product_status/complete.png";
      }
    },
    get_pInvestType() {
      switch (this.pInvestType) {
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
  }
};
</script>

<style lang="stylus" scoped>
.pro-content {
    height: 140px;
    width: 100vw;

    .pro-header-content {
        height: 40px;
        border-bottom: 1px solid #fa5c5f;
        display: flex;
        justify-content: space-between;
        align-items: center;

        .title-content {
            font-size: 14px;
            width: 90px;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 0 4px;
            border-right: 1px solid #cfd3d4;
            color: red;
        }

        .status-content {
            flex: 1;
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding-left:8px;

            .name {
                font-size: 14px;
                margin-left: 4px;
            }

            >img {
                height:36px;
                width:36px;
                // height: 40px;
                // width: 40px;
            }
        }
    }

    .pro-main {
        display: flex;
        height: calc(120px - 24px);
        border-bottom: 4px solid #dce0e3;

        >div {
            flex: 1;
            display: flex;
            flex-direction: column;
            margin: 10px 0;
            padding: 5px 10px;
        }

        .main-left-content {
            border-right: 1px solid #dbe0e4;
            // justify-content: space-around;
        }

        .main-right-content {
            >div {
                flex: 1;
            }
        }
    }

    .span-key, .span-value {
        font-size: 14px;
        padding:2px;
    }

    // 年化率
    .annual-rate {
        flex: 1;
        font-size: 24px;
        display: inline-block;
        height: 60px;
        line-height: 60px;
    }

    .span-key {
        color: #686868;
    }
    .span-value {
        color: #fc5756;
    }
}
</style>


