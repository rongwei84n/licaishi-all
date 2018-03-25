/*
 * @Author: 张浩然 
 * @Date: 2018-03-07 19:23:27 
 * @Last Modified by: 张浩然
 * @Last Modified time: 2018-03-19 00:49:49
 *
 * 产品详情组件
 */

 <template>
  <div id="pDetails">
    <mt-header title="产品详情">
      <mt-button icon="back" @click="back" slot="left"></mt-button>
      <i class="fa fa-share-alt extend-click" slot="right"></i>
    </mt-header>
    <Scroll class="scroll-conntent" :data="pDetailsObj">
      <div>
        <!-- 产品描述 -->
        <div class="p-presentation">
          <div class="header-content">
            <span class="header-title">{{pDetailsObj.pShortName}}</span>
            <img :src="pStatus" alt="">
          </div>
          <div class="body-content">
            <div class="data-content" v-if="pDetailsObj.profitRebates">
              <div>
                <span class="title">投资金额</span>
                <span v-for="(item,index) of pDetailsObj.profitRebates" :key="index">{{item.prAmountDisplay}}</span>
                <!-- <span>100-300万元</span>
                <span>100-300万元</span> -->
              </div>
              <div>
                <span class="title">预期年化收益</span>
                <span v-for="(item,index) of pDetailsObj.profitRebates" :key="index">{{item.prExpectAnnualRevenue}}</span>
              </div>
              <div>
                <span class="title">返佣比例</span>
                <span v-for="(item,index) of pDetailsObj.profitRebates" :key="index">{{item.prCommission}}</span>
              </div>
            </div>
            <p class="annotation" v-if="pDetailsObj.pRecruitmentSummary">
              {{pDetailsObj.pRecruitmentSummary}}
            </p>
          </div>
        </div>
        <!-- 基础信息 -->
        <div class="p-basic-information">
          <div class="pro-header-content">
            <span>基础信息</span>
            <span class="copy" data-clipboard-target="#information" data-clipboard-action="copy" id="information_btn" @click="copy('information_btn','#information')">
              <i></i>复制
            </span>
          </div>
          <div class="body-content" id="information">
            <div v-if="pDetailsObj.pFullName">
              <span class="title">产品全称</span>
              <span>{{pDetailsObj.pFullName}}</span>
            </div>
            <div v-if="pDetailsObj.pAllIssuingScale">
              <span class="title">募集规模</span>
              <span>{{pDetailsObj.pAllIssuingScale}}元</span>
            </div>
            <div v-if="pInvestType_str">
              <span class="title">投资领域</span>
              <span>{{pInvestType_str}}</span>
            </div>
            <div v-if="pPaymentInterestType_str">
              <span class="title">付息方式</span>
              <span>{{pPaymentInterestType_str}}</span>
            </div>
            <div v-if="pSizeRatioType_str">
              <span class="title">大小配比</span>
              <span>{{pSizeRatioType_str}}</span>
            </div>
            <div v-if="pDetailsObj.pInvestName">
              <span class="title">发行机构</span>
              <span>{{pDetailsObj.pInvestName}}</span>
            </div>
            <div v-if="pDetailsObj.pDulTime">
              <span class="title">投资期限</span>
              <span>{{pDetailsObj.pDulTime}}个月</span>
            </div>
            <div v-if="pDetailsObj.pSaleStartTime">
              <span class="title">发行时间</span>
              <span>{{pDetailsObj.pSaleStartTime}}</span>
            </div>
            <!-- <div v-if="pDetailsObj.pLatestPayNum">
              <span class="title">最迟打款日期</span>
              <span>{{pDetailsObj.pLatestPayNum}}</span>
            </div> -->
          </div>
        </div>
        <!-- 产品优势 -->
        <div class="p-superiority" v-if="pDetailsObj.pCpys">
          <div class="pro-header-content">
            <span>产品优势</span>
            <span class="copy">
              <i></i>复制
            </span>
          </div>
          <div class="body-content">
            <p>
              {{pDetailsObj.pCpys}}
            </p>
          </div>
        </div>
        <!-- 募集账号 -->
        <div class="p-collect-account" v-if="pDetailsObj.pMjzh">
          <div class="pro-header-content">
            <span>募集账号</span>
            <span class="copy">
              <i></i>复制
            </span>
          </div>
          <div class="body-content">
            <p v-html="pDetailsObj.pMjzh">
              <!-- {{pDetailsObj.pMjzh}} -->
            </p>
          </div>
        </div>
        <!-- 风险控制 -->
        <div class="risk-control" v-if="pDetailsObj.pFxkz">
          <div class="pro-header-content">
            <span>风险控制</span>
          </div>
          <div class="body-content">
            <p v-html="pDetailsObj.pFxkz">
            </p>
          </div>
        </div>
        <!-- 还款来源 -->
        <div class="source-repayment" v-if="pDetailsObj.pHkly">
          <div class="pro-header-content">
            <span>还款来源</span>
          </div>
          <div class="body-content">
            <p v-html="pDetailsObj.pHkly">
            </p>
          </div>
        </div>
        <!-- 资金用途 -->
        <div class="purpose" v-if="pDetailsObj.pZjyt">
          <div class="pro-header-content">
            <span>资金用途</span>
          </div>
          <div class="body-content">
            <p v-html="pDetailsObj.pZjyt">
            </p>
          </div>
        </div>
        <!-- 融资方 -->
        <div class="financing" v-if="pDetailsObj.pRrzf">
          <div class="pro-header-content">
            <span>融资方</span>
          </div>
          <div class="body-content">
            <p v-html="pDetailsObj.pRrzf">
            </p>
          </div>
        </div>
        <!-- 预览资料 -->
        <div class="preview-material" v-if="pDetailsObj.productAttachments&&pDetailsObj.productAttachments.length>0">
          <div class="pro-header-content">
            <span>预览资料</span>
          </div>
          <!-- TODO:预览资料模块 -->
          <!-- pDetailsObj.productAttachments -->
        </div>
        <!-- 备注 -->
        <!-- TODO:缺少字段 -->
        <div class="back">
          <div class="pro-header-content">
            <span>备注</span>
          </div>
        </div>
        <!-- 推荐 -->
        <!-- <module-title iconUrl="../../common/image/zan.png" title="推荐"></module-title>
        <template v-if="pDetailsObj.productAttachments&&pDetailsObj.productAttachments.length>0">
          <product-item v-for=""></product-item>
        </template> -->
        <white-space></white-space>
      </div>
    </Scroll>
    <div class="recommend-content">
      <a href="tel:0147-88469258">
        <i class="fa-phone-square fa"></i>
        咨询
      </a>
      <a class="subscribe" @click="subscribe">立即预约</a>
    </div>
  </div>
</template>

 <script type="es6">
import {
  pInvestType,
  pPaymentInterestType,
  pSizeRatioType
} from "common/js/pEnumerate";
import Clipboard from "clipboard";
import moduleTitle from "components/moduleTitle/moduleTitle";
import Scroll from "base/scroll/scroll";
import productItem from "components/productItem/productItem";
import whiteSpace from "base/whiteSpace/whiteSpace";

export default {
  data() {
    return {
      pCode: "", //产品编号
      pStatusCode: "", //产品状态编码
      pDetailsObj: {}, //产品详情
      /**
       * 枚举返回字符串
       */
      pInvestType_str: "",
      pPaymentInterestType_str: "",
      pSizeRatioType_str: ""
    };
  },
  created() {
    this.get_pDetails();
  },
  methods: {
    /**
     * @param btnId 按钮id
     * @param targetId 目标块id
     */
    copy(btnId, targetId) {
      var targetText = document.querySelector(targetId).innerHTML;
      var clipboard = new Clipboard(btnId);
      clipboard.on("success", function(e) {
        alert("复制成功");
        // alert(e);
        e.clearSelection();
      });
      clipboard.on("error", function(e) {
        console.error("Action:", e.action);
        console.error("Trigger:", e.trigger);
      });
    },
    /**
     * 获取产品详情
     */
    get_pDetails() {
      if (this.$route.query) {
        this.pCode = this.$route.query.pCode;
      }
      this.$ajax({
        url: `/srv/v1/product/productDetail?pCode=${this.pCode}`,
        method: "GET"
      }).then(res => {
        if (res.status === this.$store.state.status) {
          this.pDetailsObj = res.data.result;
          this.pDetailsObj.pLatestPayNum = this.$moment()
            .add(this.pDetailsObj.pLatestPayNum, "d")
            .format("YYYY-MM-DD");
          this.pInvestType_str = pInvestType[this.pDetailsObj.pInvestType];
          this.pPaymentInterestType_str =
            pPaymentInterestType[this.pDetailsObj.pPaymentInterestType];
          this.pSizeRatioType_str =
            pSizeRatioType[this.pDetailsObj.pSizeRatioType];
        }
      });
    },
    /**
     * 立即预约
     */
    subscribe() {
      // 判断当前是否登录
      // this.$ajax({
      //   url: `/srv/v1/login_status`,
      //   method: "GET"
      // }).then(res => {
      //   if (res.data.status === this.$store.state.status) {
      //     this.$router.push({
      //       name: "pOrder",
      //       query: {
      //         pId: this.pDetailsObj.pId,
      //         pShortName: this.pDetailsObj.pShortName,
      //         profitRebates: JSON.stringify(this.pDetailsObj.profitRebates),
      //         pLatestPayNum: this.pDetailsObj.pLatestPayNum
      //       }
      //     });
      //   }
      // });
      this.$router.push({
        name: "pOrder",
        query: {
          pId: this.pDetailsObj.pId,
          pShortName: this.pDetailsObj.pShortName,
          profitRebates: JSON.stringify(this.pDetailsObj.profitRebates),
          pLatestPayNum: this.pDetailsObj.pLatestPayNum
        }
      });
    },
    back() {
      this.$router.go(-1);
    }
  },
  computed: {
    pStatus() {
      switch ("01") {
        // 预热中
        case "01":
          return require("../../common/image/p-warm-up.png");
          break;
        // 募集中
        case "02":
          return require("../../common/image/p-funding.png");
          break;
        // 募集结束
        case "03":
          return require("../../common/image/p-finish.png");
          break;
        // 产品成立
        case "04":
          return require("../../common/image/p-complete.png");
          break;
        default:
          break;
      }
      // // 04代表是私募产品，以下是除去私募产品以外的产品状态
      // if (this.pDetailsObj.pType !== "04") {
      // } else {
      // }
    }
  },
  components: {
    Scroll,
    whiteSpace,
    moduleTitle,
    productItem
  }
};
</script>

<style lang="stylus">
@import '~common/stylus/variable';
@import '~common/stylus/mixin';

#pDetails {
  position: relative;
  height: 100%;
  width: 100%;
  z-index: 100;
  background-color: $color-background;

  .scroll-conntent {
    position: absolute;
    bottom: 40px;
    top: 40px;

    >div {
      >div {
        margin: 6px 0;
        background-color: $color-background-d;

        >div:first-of-type {
          border-bottom: 1px solid $color-title-border;
        }
      }

      .header-title {
        font-size: $font-size-medium-x;
        color: $color-text;
        margin: 14px 0 8px 0;
      }

      p, span, a {
        font-size: $font-size-medium;
        color: $color-text;
      }

      .title {
        color: $color-title;
        margin: 8px 0;
      }

      .red-span {
        color: $color-sub-theme;
      }

      // 带复制功能的title
      .pro-header-content {
        height: 36px;
        position: relative;
        display: flex;
        justify-content: center;
        align-items: center;

        .copy {
          position: absolute;
          right: 14px;
        }
      }

      // 内容块
      .body-content {
        padding: 10px 6px;
      }

      // 产品描述
      .p-presentation {
        .header-content {
          display: flex;
          flex-direction: column;
          // justify-content: space-around;
          // justify-content: space-evenly;
          align-items: center;
          height: 60px;

          >img {
            width: 44px;
            height: 14px;
          }
        }

        .body-content {
          .data-content {
            display: flex;
            justify-content: space-between;
            margin-bottom: 6px;

            >div {
              flex: 1;
              display: flex;
              flex-direction: column;
              // justify-content: center;
              align-items: center;

              >span {
                margin: 7px 0;
              }
            }
          }

          .annotation {
            color: $color-sub-theme;
            line-height: 20px;
          }
        }
      }

      // 基础信息
      .p-basic-information {
        .body-content {
          >div {
            display: flex;
            align-items: center;
            justify-content: space-between;

            >span {
            }
          }
        }
      }

      // 产品优势
      .p-superiority, .p-collect-account, .risk-control, .source-repayment {
        .body-content {
          >p {
            line-height: 22px;
            margin: 6px 0;
          }
        }
      }
    }
  }

  // 咨询
  .recommend-content {
    height: 40px;
    position: absolute;
    bottom: 0;
    display: flex;
    width: 100vw;
    background-color: $color-background-d;
    z-index: 101;

    // extend-click();
    >a {
      flex: 1;
      line-height: 40px;
      text-align: center;
      color: #464545;
    }

    .subscribe {
      color: $color-background-d;
      background-color: $color-sub-theme;
    }
  }
}
</style>

 
 
