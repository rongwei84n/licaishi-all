<template>
  <div class="my-custom-wrapper">
    <workRoomHeader @back="back"  mytitle="我的客户" ></workRoomHeader>
    <Scroll class="scroll-conntent" :data="customs" :pullup="pullup" @scrollToEnd="scrollToEnd">
      <div>
        <div v-for="(custom,index) in customs" :key="index" @click="customOrderFun(custom.uid)">
          <split :sh="8"></split>
          <customBaseInfo :customName="custom.name" :customTel="custom.phone"
                          :customOrderCount="custom.orderCounts" :customSignDate="custom.createtime"></customBaseInfo>
        </div>
        <loading v-if="pullup"></loading>
        <to-end v-else></to-end>
      </div>
    </Scroll>
    <router-view></router-view>
  </div>
</template>

<script type="text/ecmascript-6">
  import ajax from "api/ajax";
  import Scroll from "base/scroll/scroll";
  import loading from "base/loading/loading";
  import split from "components/split/split";
  import toEnd from "base/toEnd/toEnd";
  import workRoomHeader from "components/workRoomHeader/workRoomHeader";
  import customBaseInfo from "components/myCustom/customBaseInfo";
  export default {
    data() {
      return {
        customs:[

        ],
        customId:"",
        pageNo: 1, //当前页
        pullup: true, //开启上拉加载
      }
    },
    components: {
      split,
      workRoomHeader,
      customBaseInfo,
      Scroll,
      loading,
      toEnd
    },
    created() {
      this.get_mycustom();
    },
    methods: {
      back(){
        this.$router.go(-1)
      },
      customOrderFun(userId){
        this.$router.push({path:"/rank/mycustom/customOrder",query:{customId:userId}})
      },
      scrollToEnd() {
        this.pageNo++;
        // if (this.pageNo === 4) {
        //   this.pullup = false;
        // }
        this.get_mycustom();
      },
      get_mycustom(){
        ajax({
          url: `/srv/v1/workshop/queryMyCustomers?pageNo=${this.pageNo}&&pageSize=${
            this.$store.state.pageSize
            }`,
          method: "GET"
        }).then(res => {
          if (res.status === 200) {
            this.customs = [...this.customs, ...res.data.result.list];
            if (res.data.result.pager) {
              this.pullup = res.data.result.pager.hasNaxtPage;
            } else {
              this.pullup = false;
            }
          } else if(res.status === 2) { //未登录
            this.back();
          }
        });
      }
    }
  }
</script>

<style lang="stylus" rel="stylesheet/stylus">
  .my-custom-wrapper
    position absolute
    top 0px
    width 100%
    height 100vh
    background #FFF
    z-index 100
    /* 滑动块区域 */
    .scroll-conntent
      position: absolute;
      top: 50px;
      bottom: 5px;
      overflow: hidden;
      width: 100%;
</style>
