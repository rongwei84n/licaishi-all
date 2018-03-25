<template>
  <div class="settings-all-0">
    <!-- 头部功能模块 -->
    <div class="setting-header">
      <div class="content-wrapper" v-on:click="gotoLogin">
        <div class="avatar">
          <img width="64" height="64" v-if="headerAvatar.length <= 0" src="~@/common/image/head_portrait.png" />
          <img width="64" height="64" v-else :src="headerAvatar" class="header_avatar" />
        </div>
        <div class="content">
          <div class="av-name">{{name}}</div>
          <div v-if="this.name!=='未登录'" class="money-all">累计佣金: {{sumCommission}}</div>
        </div>
      </div>
      <div class="right-icon">
        <div class="ri-setting" v-on:click="gotoPersonInfo">设置</div>
        <div class="ri-message">
          <img width="16px" height="18px" src="../../common/image/my_settings_ring.png" />
        </div>
      </div>
    </div>

    <!-- 我的订单模块 -->
    <div class="setting-order">
      <div class="view-all-orders" @click="handleOrderClick('1')">查看全部订单></div>
      <img slot="icon" src="../../common/image/my_settings_order.png" width="24" height="24" class="icon">
      <div class="my-order">
        <hr color="#EDEEEF" />
        <span>我的订单</span>
        <hr color="#EDEEEF" />
      </div>
      <div class="order-items">
        <div class="order-items-tab" @click="handleOrderClick('2')">
          <img slot="icon" src="../../common/image/my_settings_waitpay.png" width="24" height="27">
          <span>
            待打款
          </span>
        </div>
        <div class="order-items-tab" @click="handleOrderClick('3')">
          <img slot="icon" src="../../common/image/my_settings_wait_commission.png" width="24" height="27">
          <span>待结佣</span>
        </div>
        <div class="order-items-tab" @click="handleOrderClick('4')">
          <img slot="icon" src="../../common/image/my_settings_already_commission.png" width="24" height="27">
          <span>已结佣</span>
        </div>
        <div class="order-items-tab" @click="handleOrderClick('5')">
          <img slot="icon" src="../../common/image/my_settings_failed.png" width="24" height="27">
          <span>已失败</span>
        </div>
      </div>
    </div>

    <div class="my-owns">
      <mt-cell title="我的工作室" to="/rank" is-link>
        <img slot="icon" src="../../common/image/my_settings_mystudio.png" width="18" height="14">
      </mt-cell>
      <mt-cell title="我的客户" to="/rank/mycustom" is-link>
        <img slot="icon" src="../../common/image/my_settings_my_customer.png" width="18" height="14">
      </mt-cell>
      <mt-cell title="我要推广" is-link>
        <img slot="icon" src="../../common/image/my_settings_spread.png" width="18" height="14">
      </mt-cell>
      <mt-cell title="客服热线：400-0852-6325">
        <img slot="icon" src="../../common/image/my_settings_hotline.png" width="18" height="14">
      </mt-cell>
    </div>
    <router-view></router-view>

    <div>
      <img width="100%" src="../../common/image/my_settings_bottom.png" />
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      sumCommission: "获取失败",
      isLogin: false,
      headerAvatar: "",
      name: "未登录",
      neturl: "http://47.97.100.240/"
    };
  },
  created: function() {
    this.queryAccountDetail();
    this.queryCommission();
  },

  methods: {
    queryCommission() {
      let _this = this;
      window.phihome.util.netRequest(
        "get",
        _this.neturl + "srv/v1/workshop/queryCommission",
        "",
        "",
        function(response) {
          response = JSON.parse(response);
          if (response.status == 200) {
            if (response.result.sumCommission === null) {
              _this.sumCommission = "0元";
            } else {
              _this.sumCommission = response.result.sumCommission + "元";
            }
          } else {
            _this.sumCommission = "获取失败";
          }
        }
      );
    },
    queryAccountDetail() {
      let _this = this;
      window.phihome.util.netRequest(
        "get",
        _this.neturl + "srv/v1/accountDetail",
        "",
        "",
        function(response) {
          response = JSON.parse(response);
          if (response.error == 0) {
            //获取账号成功
            _this.name = response.data.nickname;
            _this.headerAvatar = response.data.img;
            _this.isLogin = true;
          } else {
            _this.name = "未登录";
            _this.isLogin = false;
            _this.headerAvatar = "";
          }
          if (_this.name == null || _this.name == "") {
            _this.name = "未设置";
          }
        }
      );
    },
    // 跳转到原生页面
    gotoLogin() {
      let _this = this;
      if (_this.isLogin) {
        window.phihome.app.openPage("lcs.account.personinfo", null, function(
          response
        ) {
          _this.queryAccountDetail();
        });
      } else {
        window.phihome.app.openPage("lcs.account.login", null, function(
          response
        ) {
          _this.queryAccountDetail();
        });
      }
    },
    gotoPersonInfo() {
      window.phihome.app.openPage("lcs.account.personinfo", null, function(
        response
      ) {});
    },

    gotoAllOrderList() {
      this.handleOrderClick("1");
    },
    handleWaitpayOnclick() {
      this.handleOrderClick("2");
    },
    handleWaitCommissionOnclick() {
      this.handleOrderClick("3");
    },
    handleAlreadyCommissionOnclick() {
      this.handleOrderClick("4");
    },
    handleFailedOnclick() {
      this.handleOrderClick("5");
    },

    handleOrderClick(item) {
      if (this.isLogin) {
        this.$router.push({ name: "Orderlist", params: { tab_id: item } });
      } else {
        window.phihome.app.openPage("lcs.account.login", null, function(
          response
        ) {
          _this.queryAccountDetail();
        });
      }
    }
  },
  components: {}
};
</script>

<style lang="stylus" rel="stylesheet/stylus">
// @import '~common/stylus/variable';
.settings-all-0 {
  position: relative;
  width: 100%;
  background: #EDF1F5;
  z-index: 2;

  /* 头部功能模块 */
  .setting-header {
    height: calc(143px + 14px);
    width: 100%;
    padding-left: 18px;
    position: relative;
    background: url('../../common/image/my_settings_header_background.png') center center;
    background-color: red;
    background-size: cover;
    border-bottom: solid 1px gainsboro;

    .content-wrapper {
      padding: 24px 12px 18px 18px;
      font-size: 0;

      .avatar {
        vertical-align: top;
        display: inline-block;

        .header_avatar {
          border-radius: 40px;
        }
      }

      .content {
        align-content: center;
        display: inline-block;
        font-size: 14px;
        margin-left: 16px;
        color: #ffffff;

        .av-name {
          margin-top: 10px;
          font-size: 14px;
          margin-bottom: 10px;
        }

        .money-all {
          padding: 2px 4px;
          font-size: 12px;
          border: 1px solid #FFDE00;
          border-radius: 12px;
        }
      }
    }
  }

  .right-icon {
    position: absolute;
    right: 18px;
    top: 24px;
    // font-size: 20px;
    display: flex;
    justify-content: center;
    align-items: center;

    .ri-setting {
      // visibility: hidden;
      margin-right: 16px;
      font-size: 16px;
      color: #ffffff;
      display: inline-block;
    }

    .ri-message {
      display: inline-block;
    }
  }

  /* 我的订单模块 */
  .setting-order {
    position: relative;
    height: 128px;
    width: 100vw;
    background: #FFFFFF;

    .view-all-orders {
      position: absolute;
      font-size: 10px;
      color: #666666;
      top: 6px;
      right: 4px;
    }

    .icon {
      position: absolute;
      top: -13px;
      left: calc(50% - 12px);
      width: 24px;
      height: 27px;
    }

    /* 我的订单 */
    .my-order {
      width: 100vw;
      height: calc(128px - 84px);
      display: flex;
      justify-content: space-between;
      align-items: center;

      > hr {
        flex: 1;
        color: #EDEEEF;
        // border-bottom: 1px solid #EDEEEF;
      }

      // >img {
      // height: 22px;
      // width: 22px;
      // position: absolute;
      // }
      > span {
        margin: 0 6px;
        font-size: 14px;
        color: #666666;
      }
    }

    /* 订单导航模块 */
    .order-items {
      display: flex;
      justify-content: space-around;
      width: 100%;
      height: 84px;

      .order-items-tab {
        height: 80%;
        flex: 1;
        display: flex;
        flex-direction: column;
        justify-content: space-around;
        align-items: center;

        > span {
          font-size: 10px;
          color: #303030;
        }
      }
    }
  }

  /* 功能导航模块 */
  .my-owns {
    margin: 12px 0;
    background: #FFFFFF;
    height: 172px;
    border-bottom: solid 1px gainsboro;

    > div {
      border-bottom: 1px solid #EDEEEF;
    }

    /* 改变cell组件内的文字大小 */
    .mint-cell-text {
      font-size: 14px;
    }
  }
}
</style>
