<template>
  <div class="custom-order">
    <workRoomHeader @back="back" mytitle="客户订单"></workRoomHeader>
    <split :sh="4"></split>
    <mt-navbar class="page-part" v-model="selected">
      <mt-tab-item id="1">全部</mt-tab-item>
      <mt-tab-item id="2">待打款</mt-tab-item>
      <mt-tab-item id="3">待结佣</mt-tab-item>
      <mt-tab-item id="4">已结佣</mt-tab-item>
      <mt-tab-item id="5">已失败</mt-tab-item>
    </mt-navbar>
    <split :sh="4"></split>
    <Scroll class="scroll-conntent" :data="customOrderList" :pullup="pullup" @scrollToEnd="scrollToEnd">
      <div>
        <div v-show="customOrderList">
          <div v-for="(product,index) in customOrderList" @click="displayOrderDetail(product)">
            <customOrderInfo :productName="product.productShortName" :loanAmount="product.amount" :auEndDate="product.orderDate" :commission="product.commission" :productTime="product.ee"></customOrderInfo>
            <split :sh="8"></split>
          </div>
        </div>
        <mt-tab-container v-model="selected">
          <mt-tab-container-item id="1">
            <Emptyorder v-if="customOrderList.length<=0" class="no-order"></Emptyorder>
          </mt-tab-container-item>
          <mt-tab-container-item id="2">
            <Emptyorder v-if="customOrderList.length<=0" class="no-order"></Emptyorder>
          </mt-tab-container-item>
          <mt-tab-container-item id="3">
            <Emptyorder v-if="customOrderList.length<=0" class="no-order"></Emptyorder>
          </mt-tab-container-item>
          <mt-tab-container-item id="4">
            <Emptyorder v-if="customOrderList.length<=0" class="no-order"></Emptyorder>
          </mt-tab-container-item>
          <mt-tab-container-item id="5">
            <Emptyorder v-if="customOrderList.length<=0" class="no-order"></Emptyorder>
          </mt-tab-container-item>
        </mt-tab-container>
        <loading v-if="pullup"></loading>
        <to-end v-else></to-end>
      </div>

    </Scroll>
    <!--<div class="order-detail" v-show="orderDetailFlag" >
      <customOrderDetail @closeDetail="closeDetail" :orderDetail="orderDetail"></customOrderDetail>
    </div>-->
  </div>
</template>

<script type="text/ecmascript-6">
import ajax from "api/ajax";
import Scroll from "base/scroll/scroll";
import loading from "base/loading/loading";
import toEnd from "base/toEnd/toEnd";
import split from "components/split/split";
import workRoomHeader from "components/workRoomHeader/workRoomHeader";
import customOrderInfo from "components/myCustom/customOrderInfo";
import customOrderDetail from "components/myCustom/customOrderDetail";
import Emptyorder from "components/order/Emptyorder";
export default {
  data() {
    return {
      selected: "1",
      customOrderList: [],
      pageNo: 1, //当前页
      pullup: true, //开启上拉加载
      customId: "",
      orderDetailFlag: false,
      customOrderList1:[],
      customOrderList2:[],
      customOrderList3:[],
      customOrderList4:[],
      customOrderList5:[],
      pullup1: true,
      pullup2: true,
      pullup3: true,
      pullup4: true,
      pullup5: true,
      //orderDetail:""
    };
  },
  created() {
    this.customId = this.$route.query.customId;
    this.get_custom_order();
  },
  watch: {
    selected: function (val, oldVal) {
      // 这里就可以通过 val 的值变更来确定
      this.get_custom_order();
    }
  },
  methods: {
    back() {
      this.$router.go(-1);
    },
    displayOrderDetail(orderDetail) {
      this.$router.push({
        path: "/rank/mycustom/customOrderDetail",
        query: orderDetail
      });
      //this.orderDetailFlag = true
      //this.orderDetail = orderDetail
    },
    /**
      closeDetail(){
        this.orderDetailFlag = false
      },*/
    scrollToEnd() {
      this.pageNo++;
      // if (this.pageNo === 4) {
      //   this.pullup = false;
      // }
      this.get_custom_order();
    },
    /**get_custom_order() {
      ajax({
        url: `/srv/v1/workshop/queryOrdersByCustomerId?pageNo=${
          this.pageNo
        }&pageSize=${this.$store.state.pageSize}&customerId=${this.customId}&status=${status}`,
        method: "GET"
      }).then(res => {
        if (res.status === 200) {
          this.customOrderList = [...this.customOrderList,...res.data.result.list];
          if (res.data.result.pager) {
            this.pullup = res.data.result.pager.hasNaxtPage;
          } else {
            this.pullup = false;
          }
        }
      });
    },*/
    get_custom_order() {
      let canPull = false;
      if(this.selected == 1) {
        canPull = this.pullup1;
      }else if(this.selected == 2) {
        canPull = this.pullup2;
      }else if(this.selected == 3) {
        canPull = this.pullup3;
      }else if(this.selected == 4) {
        canPull = this.pullup4;
      }else if(this.selected == 5) {
        canPull = this.pullup5;
      }

      if(canPull) {
        let status = "00";
        if(this.selected == 1) {
          status = "00";
        } else if(this.selected == 2) {
          status = "01";
        } else if(this.selected == 3) {
          status = "02";
        } else if(this.selected == 4) {
          status = "03";
        } else if(this.selected == 5) {
          status = "99";
        }
        //alert(canPull)
        ajax({
          url: `/srv/v1/workshop/queryOrdersByCustomerId?pageNo=${
            this.pageNo
            }&pageSize=${this.$store.state.pageSize}&customerId=${this.customId}&status=${status}`,
          method: "GET"
        }).then(res => {
          if (res.status === 200) {
            if(this.selected == 1) {
              this.customOrderList1 = [...this.customOrderList1,...res.data.result.list];
            }else if(this.selected == 2) {
              this.customOrderList2 = [...this.customOrderList2, ...res.data.result.list];
            }else if(this.selected == 3) {
              this.customOrderList3 = [...this.customOrderList3, ...res.data.result.list];
            }else if(this.selected == 4) {
              this.customOrderList4 = [...this.customOrderList4, ...res.data.result.list];
            }else if(this.selected == 5) {
              this.customOrderList5 = [...this.customOrderList5, ...res.data.result.list];
            }
            if (res.data.result.pager) {
              if(this.selected == 1) {
                this.pullup1 = res.data.result.pager.hasNaxtPage;
              }else if(this.selected == 2) {
                this.pullup2 = res.data.result.pager.hasNaxtPage;
              }else if(this.selected == 3) {
                this.pullup3 = res.data.result.pager.hasNaxtPage;
              }else if(this.selected == 4) {
                this.pullup4 = res.data.result.pager.hasNaxtPage;
              }else if(this.selected == 5) {
                this.pullup5 = res.data.result.pager.hasNaxtPage;
              }
            } else {
              if(this.selected == 1) {
                this.pullup1 = false;
              }else if(this.selected == 2) {
                this.pullup2 = false;
              }else if(this.selected == 3) {
                this.pullup3 = false;
              }else if(this.selected == 4) {
                this.pullup4 = false;
              }else if(this.selected == 5) {
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
      if(this.selected == 1) {
        this.customOrderList = this.customOrderList1;
      }else if(this.selected == 2) {
        this.customOrderList = this.customOrderList2;
      }else if(this.selected == 3) {
        this.customOrderList = this.customOrderList3;
      }else if(this.selected == 4) {
        this.customOrderList = this.customOrderList4;
      }else if(this.selected == 5) {
        this.customOrderList = this.customOrderList5;
      }
      if(this.selected == 1) {
        this.pullup = this.pullup1;
      }else if(this.selected == 2) {
        this.pullup = this.pullup2;
      }else if(this.selected == 3) {
        this.pullup = this.pullup3;
      }else if(this.selected == 4) {
        this.pullup = this.pullup4;
      }else if(this.selected == 5) {
        this.pullup = this.pullup5;
      }
    }
  },
  components: {
    workRoomHeader,
    customOrderInfo,
    split,
    Scroll,
    loading,
    toEnd,
    customOrderDetail,
    Emptyorder
  }
};
</script>

<style lang="stylus" rel="stylesheet/stylus">
.custom-order {
  position: absolute;
  top: 0px;
  width: 100%;
  height: 100vh;
  background: #FFF;
  z-index: 1;

  .mint-navbar .mint-tab-item {
    padding: 8px;
  }

  .mint-tab-item-label {
    font-size: 13px;
  }

  .scroll-conntent {
    position: absolute;
    top: 88px;
    bottom: 5px;
    overflow: hidden;
    width: 100%;
  }

  .no-order {
    margin auto;
    text-align center;
  }
  .order-detail {
    position: absolute;
    width: 100%;
    height: 100vh;
    top: 0px;
    background: #FFF;
    z-index: 1;
  }
}
</style>
