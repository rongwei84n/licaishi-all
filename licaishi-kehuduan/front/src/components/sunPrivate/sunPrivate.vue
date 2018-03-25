/*
 * @Author: 张浩然 
 * @Date: 2018-03-05 10:11:06 
 * @Last Modified by: 张浩然
 * @Last Modified time: 2018-03-17 20:03:22
 * 产品--阳光私募--子组件
 */

<template>
  <div id="sunPrivate">
    <!-- <conditionTab @onClick="get_tabsIndex"></conditionTab> -->
    <Scroll class="scroll-conntent" :data="productList" :pullup="pullup" @scrollToEnd="scrollToEnd">
      <div>
        <product-item v-for="(item,index) in productList" :key="index" :productItem="item"></product-item>
        <loading v-if="pullup"></loading>
        <to-end v-else></to-end>
      </div>
    </Scroll>
    <mt-popup v-model="popupVisible" position="bottom">
      <sunProdQueryModal :status="popupVisible" @reset="pQueryReset" @confirm="confirm"></sunProdQueryModal>
    </mt-popup>
  </div>
</template>

<script type="es6">
import ajax from "api/ajax";
import Scroll from "base/scroll/scroll";
import loading from "base/loading/loading";
import toEnd from "base/toEnd/toEnd";
import productItem from "components/pro_product/productItem";
import sunProdQueryModal from "./sunProdQueryModal";
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
      /* 按顺序内容分别为筛选弹出框从上至下的选择项,每项的枚举为当前item的"0+索引" */
      initList: [0, 0, 0], //初始化选择项--item根据枚举使用
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
          this.initList = [0, 0, 0];
          this.productList = [];
          this.get_proList();
          break;
        case 1:
          this.pageNo = 1;
          this.pullup = true;
          this.productList = [];
          url = `/srv/v1/product/list?pageNo=${this.pageNo}&pageSize=${
            this.$store.state.pageSize
          }&type=${this.ptype}&saleStatus=0${this.initList[0]}&minimumAmount=0${
            this.initList[1]
          }&pCommission=0${this.initList[2]}&pRabateProfitParameter=true`;
          this.get_proList(url);
          break;
        case 2:
          this.pageNo = 1;
          this.pullup = true;
          this.productList = [];
          url = `/srv/v1/product/list?pageNo=${this.pageNo}&pageSize=${
            this.$store.state.pageSize
          }&type=${this.ptype}&saleStatus=0${this.initList[0]}&minimumAmount=0${
            this.initList[1]
          }&pCommission=0${
            this.initList[2]
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
      }&type=${this.ptype}&saleStatus=0${this.initList[0]}&minimumAmount=0${
        this.initList[1]
      }&pCommission=0${this.initList[2]}`
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
      this.initList = [0, 0, 0];
      // this.popupVisible = false;
    }
  },
  components: {
    Scroll,
    productItem,
    conditionTab,
    loading,
    toEnd,
    sunProdQueryModal
  }
};
</script>

<style lang="stylus">
#sunPrivate {
  position: relative;
  width: 100%;
  height: calc(100% - 90px);

  /* 滑动块区域 */
  .scroll-conntent {
    position: absolute;
    top: 0;
    bottom: 0;
    overflow: hidden;
    width: 100%;
  }
}
</style>


