<template>
  <div class="commission-wrapper">
    <workRoomHeader @back="back"  mytitle="我的佣金" ></workRoomHeader>
    <div class="commission_wrapper">
      <div>
        ￥{{sumCommission}}
      </div>
      <div>
        <span>
          已结佣：￥{{ocCommission}}
        </span>
        <span>
          待结佣：￥{{wcCommission}}
        </span>
      </div>
    </div>
    <!--<img src="~@/common/image/my-co.png"/>-->
    <Scroll class="scroll-conntent" :data="comList">
      <div>
        <div v-for="(commission,index) in comList" :key="index" @click="displayOrderDetail(commission)">
          <split :sh="8"></split>
          <myCommissionInfo :myDate="commission.orderDate" :myComAmount="commission.commission"
              :orderId="commission.orderNo" :orderAmount="commission.amount"
              :customName="commission.customerName" :customTep="commission.productShortName"></myCommissionInfo>
        </div>
      </div>
    </Scroll>
  </div>
</template>

<script type="text/ecmascript-6">
  import ajax from "api/ajax";
  import Scroll from "base/scroll/scroll";
  import split from "components/split/split";
  import workRoomHeader from "components/workRoomHeader/workRoomHeader";
  import myCommissionInfo from "components/mycommission/myCommissionInfo";
  export default {
    data() {
      return {
        comList: [],/**[
          {
            myDate:"2014.2.26 12:22:22",
            myComAmount:"50",
            orderId:"555555555",
            orderAmount:"26",
            customName:"劳务",
            customTep:"1366666666"
          },
          {
            myDate:"2014.2.26 12:22:22",
            myComAmount:"50",
            orderId:"555555555",
            orderAmount:"26",
            customName:"劳务",
            customTep:"1366666666"
          }
        ],*/
        wcCommission:"0",//待结佣
        ocCommission:"0",//已结佣
        sumCommission:"0",//总结佣
        pageNo: 1, //当前页
        pullup: true, //开启上拉加载

      }
    },
    components: {
      split,
      workRoomHeader,
      Scroll,
      myCommissionInfo
    },
    created() {
      this.get_mycommission_info();
      this.get_mycommission();
    },
    methods: {
      back() {
        this.$router.go(-1)
      },
      displayOrderDetail(orderDetail) {
        this.$router.push({
          path: "/rank/mycustom/customOrderDetail",
          query: orderDetail
        });

      },
      get_mycommission_info() {
        ajax({
          url: `/srv/v1/workshop/queryOrdersByfinancerId?pageNo=${this.pageNo}&pageSize=${
            this.$store.state.pageSize
            }`,
          method: "GET"
        }).then(res => {

          if (res.status === 200) {
            this.comList = [...this.comList, ...res.data.result.list];
            if (res.data.result.pager) {
              this.pullup = res.data.result.pager.hasNaxtPage;
            } else {
              this.pullup = false;
            }
          }
        });
      },
      get_mycommission() {
        ajax({
          url: `/srv/v1/workshop/queryCommission`,
          method: "GET"
        }).then(res => {
          if (res.status === 200) {
            this.sumCommission = res.data.result.sumCommission==null?"0":res.data.result.sumCommission;
            this.ocCommission = res.data.result.ocCommission==null?"0":res.data.result.ocCommission;
            this.wcCommission = res.data.result.wcCommission==null?"0":res.data.result.wcCommission;
          }
        });
      }
    }
  };
</script>

<style lang="stylus" rel="stylesheet/stylus">
  .commission-wrapper
    position absolute
    top 0px
    width 100%
    height 100vh
    background #FFF
    z-index 100
    .scroll-conntent
      position: absolute;
      top: 130px;
      bottom: 0px;
      overflow: hidden;
      width: 100%;
    .commission_wrapper
      width 100%
      height:80px
      background-image url("~@/common/image/my-co.png")
      background-size 100% 100%
      background-repeat no-repeat
      padding-top 20px
      //border 1px solid darkred
      div
        width 100%
        height 30px
        padding 0px 10px
        text-align center
        font-size 18px
        color #FFF
        font-weight 700
        span
          padding 0px 10px
          font-size 15px
</style>
