<template>
  <div class="all-content">
    <div class="header">
      <mt-header title="我的订单">
        <mt-button icon="back" @click="back" slot="left"></mt-button>
        <mt-button icon="search" slot="right" @click="handleSearch"></mt-button>
      </mt-header>
    </div>

    <div class="page-navbar">
      <!-- navbar -->
      <mt-navbar class="page-part" v-model="selected">
        <mt-tab-item id="1">全部</mt-tab-item>
        <mt-tab-item id="2">待打款</mt-tab-item>
        <mt-tab-item id="3">待结佣</mt-tab-item>
        <mt-tab-item id="4">已结佣</mt-tab-item>
        <mt-tab-item id="5">已失败</mt-tab-item>
      </mt-navbar>

      <!-- tabcontainer -->
      <scroll class="scroll-content" :data="arrOrderList" :pullup="pullup" @scrollToEnd="scrollToEnd">
        <div>
          <!-- 此处建议先按状态分成5个子组件。再5个子组件内去分别处理逻辑，类似于产品模块 -->
          <!-- TODO:测试组件--开始 -->
          <div v-show="arrOrderList.length>0">
            <div v-for="(item,index) of arrOrderList" :key="index">
              <OrderListItem :index="index" :selectedOrder=selected :orderId="item.orderNo" :voucharStatus="item.voucherStatus" :prodName="item.productShortName" :orderAmount="item.amount" :rebatePresent="item.comRatio" :rebateAmount="item.commission" :status="item.status" :customerName="item.customerName" @order-cancel="orderCancel" />
              <split :sh="8"></split>
            </div>
          </div>
          <loading v-if="pullup"></loading>
          <to-end v-else-if="arrOrderList.length > 0"></to-end>
          <!-- TODO:测试组件--结束 -->

          <!-- <div v-for="(n,index) of 100" :key="index" class="textDiv">{{n}}</div> -->
          <mt-tab-container v-model="selected" class="mt-tab-contailer">
            <mt-tab-container-item id="1" v-if="arrOrderList.length<=0">
              <Emptyorder></Emptyorder>
            </mt-tab-container-item>
            <mt-tab-container-item id="2" v-if="arrOrderList.length<=0">
              <Emptyorder></Emptyorder>
            </mt-tab-container-item>
            <mt-tab-container-item id="3" v-if="arrOrderList.length<=0">
              <Emptyorder></Emptyorder>
            </mt-tab-container-item>
            <mt-tab-container-item id="4" v-if="arrOrderList.length<=0">
              <Emptyorder></Emptyorder>
            </mt-tab-container-item>
            <mt-tab-container-item id="5" v-if="arrOrderList.length<=0">
              <Emptyorder></Emptyorder>
            </mt-tab-container-item>
          </mt-tab-container>
        </div>
      </scroll>
    </div>
  </div>
</template>

<script>
import Emptyorder from "components/order/Emptyorder";
import split from "components/split/split";
import ajax from "api/ajax";
import Scroll from "base/scroll/scroll";
import loading from "base/loading/loading";
import toEnd from "base/toEnd/toEnd";
import OrderListItem from "components/order/OrderListItem";

export default {
  name: "page-order-list",
  data() {
    return {
      selected: "1",
      pageNo: 1, //当前页
      pageNoAttr: [1, 1, 1, 1, 1, 1],
      pullup: true, //开启上拉加载
      pullup1: true,
      pullup2: true,
      pullup3: true,
      pullup4: true,
      pullup5: true,

      /**
       * 测试用
       * 订单总对象
       */
      arrOrderList: {
        /* 待打款 */
        "01": [],
        /* 待结佣 */
        "02": [],
        /* 已结佣 */
        "03": [],
        /* 已失败 */
        "99": []
      },
      arrOrderList1: {},
      arrOrderList2: {},
      arrOrderList3: {},
      arrOrderList4: {},
      arrOrderList5: {}
    };
  },
  watch: {
    selected(val, oldVal) {
      // 这里就可以通过 val 的值变更来确定
      this.get_orderList();
    }
  },

  created() {
    let _this = this;
    _this.selected = this.$route.params.tab_id;
    //查询订单
    if (_this.selected == 1) {
      _this.get_orderList();
    }
  },
  methods: {
    // 删除成功后手动删除当前行
    orderCancel(index) {
      // TODO:遍历当前数组，删除此item
      // console.log(index);
      this.arrOrderList2.splice(index,1)
      this.arrOrderList=this.arrOrderList2
    },
    handleSearch(){},
    back() {
      this.$router.go(-1);
    },
    scrollToEnd() {
      this.pageNo++;
      this.pageNoAttr[this.selected]++;
      this.get_orderList();
    },
    /**
     * 测试用
     * 请求全部订单
     * 用于测试滑动组件
     */
    get_orderList() {
      let canPull = false;
      if (this.selected == 1) {
        canPull = this.pullup1;
      } else if (this.selected == 2) {
        canPull = this.pullup2;
      } else if (this.selected == 3) {
        canPull = this.pullup3;
      } else if (this.selected == 4) {
        canPull = this.pullup4;
      } else if (this.selected == 5) {
        canPull = this.pullup5;
      }
      if (canPull) {
        let status = "00";
        if (this.selected == 1) {
          status = "00";
        } else if (this.selected == 2) {
          status = "01";
        } else if (this.selected == 3) {
          status = "02";
        } else if (this.selected == 4) {
          status = "03";
        } else if (this.selected == 5) {
          status = "99";
        }
        ajax({
          url: `/srv/v1/order/list?pageNo=${
            this.pageNoAttr[this.selected]
          }&pageSize=${this.$store.state.pageSize}&status=${status}`,
          method: "GET"
        }).then(res => {
          if (res.status === 200 && res.data.result) {
            if (this.selected == 1) {
              this.arrOrderList1 = [
                ...this.arrOrderList1,
                ...res.data.result.list
              ];
            } else if (this.selected == 2) {
              this.arrOrderList2 = [
                ...this.arrOrderList2,
                ...res.data.result.list
              ];
            } else if (this.selected == 3) {
              this.arrOrderList3 = [
                ...this.arrOrderList3,
                ...res.data.result.list
              ];
            } else if (this.selected == 4) {
              this.arrOrderList4 = [
                ...this.arrOrderList4,
                ...res.data.result.list
              ];
            } else if (this.selected == 5) {
              this.arrOrderList5 = [
                ...this.arrOrderList5,
                ...res.data.result.list
              ];
            }
            if (res.data.result.pager) {
              if (this.selected == 1) {
                this.pullup1 = res.data.result.pager.hasNaxtPage;
              } else if (this.selected == 2) {
                this.pullup2 = res.data.result.pager.hasNaxtPage;
              } else if (this.selected == 3) {
                this.pullup3 = res.data.result.pager.hasNaxtPage;
              } else if (this.selected == 4) {
                this.pullup4 = res.data.result.pager.hasNaxtPage;
              } else if (this.selected == 5) {
                this.pullup5 = res.data.result.pager.hasNaxtPage;
              }
            } else {
              if (this.selected == 1) {
                this.pullup1 = false;
              } else if (this.selected == 2) {
                this.pullup2 = false;
              } else if (this.selected == 3) {
                this.pullup3 = false;
              } else if (this.selected == 4) {
                this.pullup4 = false;
              } else if (this.selected == 5) {
                this.pullup5 = false;
              }
            }
          }
          this.copyValue();
        });
      } else {
        this.copyValue();
      }
    }, //End get_orderList

    copyValue() {
      // switch (parseInt(this.selected)) {
      //   case 1:
      //     this.arrOrderList = this.arrOrderList1;
      //     this.pullup = this.pullup1;
      //     break;
      //   case 2:
      //     this.arrOrderList = this.arrOrderList2;
      //     this.pullup = this.pullup2;
      //     break;
      //   case 3:
      //     this.arrOrderList = this.arrOrderList3;
      //     this.pullup = this.pullup3;
      //     break;
      //   case 4:
      //     this.arrOrderList = this.arrOrderList4;
      //     this.pullup = this.pullup4;
      //     break;
      //   case 5:
      //     this.arrOrderList = this.arrOrderList5;
      //     this.pullup = this.pullup5;
      //     break;
      //   default:
      //     console.log("tab的id有问题");
      //     break;
      // }
      if (this.selected == 1) {
        this.arrOrderList = this.arrOrderList1;
        this.pullup = this.pullup1;
      } else if (this.selected == 2) {
        this.arrOrderList = this.arrOrderList2;
        this.pullup = this.pullup2;
      } else if (this.selected == 3) {
        this.arrOrderList = this.arrOrderList3;
        this.pullup = this.pullup3;
      } else if (this.selected == 4) {
        this.arrOrderList = this.arrOrderList4;
        this.pullup = this.pullup4;
      } else if (this.selected == 5) {
        this.arrOrderList = this.arrOrderList5;
        this.pullup = this.pullup5;
      }
      if (this.selected == 1) {
        this.pullup = this.pullup1;
      } else if (this.selected == 2) {
        this.pullup = this.pullup2;
      } else if (this.selected == 3) {
        this.pullup = this.pullup3;
      } else if (this.selected == 4) {
        this.pullup = this.pullup4;
      } else if (this.selected == 5) {
        this.pullup = this.pullup5;
      }
    }
  },

  components: {
    Emptyorder,
    split,
    Scroll,
    loading,
    toEnd,
    OrderListItem
  }
};
</script>

<style lang="stylus" rel="stylesheet/stylus">
.all-content {
  position: relative;
  width: 100%;
  height: 100%;
  background: #EDF1F5;
  z-index: 100;

  .header {
    border-bottom: solid 5px #EFF3F6;
  }

  .page-navbar {
    height: calc(100% - 55px);
    position: relative;

    /* 滑动块区域 */
    .scroll-content {
      position: absolute;
      top: 56px;
      bottom: 0;
      overflow: hidden;
      width: 100%;

      .textDiv {
        height: 100px;
      }

      .mt-tab-contailer {
        left: 50%;
        margin-left: -100px;
      }
    }
  }
}
</style>
