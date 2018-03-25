/*
 * @Author: 张浩然 
 * @Date: 2018-03-05 10:11:06 
 * @Last Modified by: 张浩然
 * @Last Modified time: 2018-03-17 15:57:36
 * 产品--股权基金--子组件
 */

<template>
  <div class="gatherTrust">
    <conditionTab @onClick="get_tabsIndex"></conditionTab>
    <Scroll class="scroll-conntent" :data="productList" :pullup="pullup" @scrollToEnd="scrollToEnd">
      <div>
        <product-item v-for="(item,index) in productList" :key="index" :productItem="item"></product-item>
        <loading v-if="pullup"></loading>
        <to-end v-else></to-end>
      </div>
    </Scroll>
    <mt-popup v-model="popupVisible" position="bottom">
      <prodQueryModal :status="popupVisible" @reset="pQueryReset" @confirm="confirm"></prodQueryModal>
    </mt-popup>
  </div>
</template>

<script type="es6">
import ajax from "api/ajax";
import Scroll from "base/scroll/scroll";
import loading from "base/loading/loading";
import toEnd from "base/toEnd/toEnd";
import productItem from "components/pro_product/productItem";
import prodQueryModal from "components/ProdQueryModal/prodQueryModal";
import conditionTab from "components/conditionTab/conditionTab";

export default {
  data() {
    return {
      /*  产品列表*/
      ptype: "04",
      productList: [],
      loading: false,
      /**
       * 条件选择框
       */
      popupVisible: false, //筛选条件
      initList: [0, 0, 0, 0, 0, 0, 0], //初始化选择项--item根据枚举使用
      /**
       * 条件查询用
       */
      saleStatus: "", //募集状态 01：预热中 02：募集中 03：募集结束 04：产品成立
      dueTime: "", //产品期限： 01；02；03；04；05
      annualRevenue: "", //预期收益：01；02；03；04；05；06
      pPaymentInterestType: "", //付息方式：01：按月付息 02：按季付息 03：按半年付息 04：按年付息 05 到期付本息
      pInvestType: "", //投资领域：01：房地产类 02：金融市场 03：基础设施 04：资金池 05：工商企业 99：其他
      minimumAmount: "", //起购金额
      pSizeRatioType: "", //大小配比：01：小额畅打 02：已配出小额 03：严格配比 04：全大额
      pRabateProfitParameter: false, //按照佣金比例从高到底 true
      pAnnualRevenueExpectParameter: false, //按照预期收益率从高到底排序 true
      /**
       * 翻页数据
       */
      pullup: true, //开启上拉加载
      pageNo: 1 //当前页
    };
  },
  created() {
    this.get_proList();
  },
  methods: {
    // 上拉加载
    scrollToEnd() {
      if (this.pullup) {
        this.pageNo++;
        this.get_proList();
      }
    },
    /**
     * @param index 条件筛选栏的索引
     */
    get_tabsIndex(index) {
      let url;
      switch (index) {
        case 0:
          this.pageNo = 1;
          this.pullup = true;
          this.initList = [0, 0, 0, 0, 0, 0, 0];
          this.productList = [];
          this.get_proList();
          break;
        case 1:
          this.pageNo = 1;
          this.pullup = true;
          this.productList = [];
          url = `/srv/v1/product/list?pageNo=${this.pageNo}&pageSize=${
            this.$store.state.pageSize
          }&type=${this.ptype}&saleStatus=0${this.initList[0]}&dueTime=0${
            this.initList[1]
          }&annualRevenue=0${this.initList[2]}&pPaymentInterestType=0${
            this.initList[3]
          }&pInvestType=0${this.initList[4]}&minimumAmount=0${
            this.initList[5]
          }&pSizeRatioType=0${this.initList[6]}&pRabateProfitParameter=true`;
          this.get_proList(url);
          break;
        case 2:
          this.pageNo = 1;
          this.pullup = true;
          this.productList = [];
          url = `/srv/v1/product/list?pageNo=${this.pageNo}&pageSize=${
            this.$store.state.pageSize
          }&type=${this.ptype}&saleStatus=0${this.initList[0]}&dueTime=0${
            this.initList[1]
          }&annualRevenue=0${this.initList[2]}&pPaymentInterestType=0${
            this.initList[3]
          }&pInvestType=0${this.initList[4]}&minimumAmount=0${
            this.initList[5]
          }&pSizeRatioType=0${
            this.initList[6]
          }&pAnnualRevenueExpectParameter=true`;
          this.get_proList(url);
          break;
        // 打开条件筛选框
        case 3:
          this.popupVisible = true;
          break;
        default:
          break;
      }
    },
    /**
     *  获取产品列表
     */
    get_proList(
      url = `/srv/v1/product/list?pageNo=${this.pageNo}&pageSize=${
        this.$store.state.pageSize
      }&type=${this.ptype}&saleStatus=0${this.initList[0]}&dueTime=0${
        this.initList[1]
      }&annualRevenue=0${this.initList[2]}&pPaymentInterestType=0${
        this.initList[3]
      }&pInvestType=0${this.initList[4]}&minimumAmount=0${
        this.initList[5]
      }&pSizeRatioType=0${this.initList[6]}`
    ) {
      ajax({
        url,
        method: "GET"
      }).then(res => {
        if (res.status === 200) {
          if (res.data.result.pager) {
            this.pullup = res.data.result.pager.hasNaxtPage;
            this.productList = [...this.productList, ...res.data.result.list];
          } else {
            this.pullup = false;
          }
        }
      });
    },
    /**
     * @event 条件选择框确定事件
     */
    confirm(parmas) {
      this.initList = parmas;
      this.popupVisible = false;
      this.pageNo = 1;
      this.productList = [];
      this.get_proList();
    },
    /**
     * @event 条件选择框重置
     */
    pQueryReset() {
      this.initList = [0, 0, 0, 0, 0, 0, 0];
      // this.popupVisible = false;
    }
  },
  components: {
    Scroll,
    productItem,
    conditionTab,
    loading,
    toEnd,
    prodQueryModal
  }
};
</script>

<style lang="stylus">
.gatherTrust {
  position: relative;
  width: 100%;
  height: calc(100% - 90px);

  .tabs-content {
  }

  /* 滑动块区域 */
  >.scroll-conntent {
    position: absolute;
    top: 34px;
    bottom: 0;
    overflow: hidden;
    width: 100%;
  }
}
</style>


