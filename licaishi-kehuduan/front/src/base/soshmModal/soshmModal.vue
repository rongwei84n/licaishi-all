<template>
  <div class="main" @click="fooModalContent">
    <div class="modal-content">
      <p>分享至</p>
      <div class="btnBlock">
        <div class="btn" @click="confirm(1)">
          <img src="./image/weibo.png" class="img">
          <span class="title">新浪微博</span>
        </div>
        <div class="btn" @click="confirm(2)" v-if="thirdTerminalType==='1'">
          <img src="./image/pengyouquan.png" class="img">
          <span class="title">朋友圈</span>
        </div>
        <div class="btn" @click="confirm(3)">
          <img src="./image/weixin.png" class="img">
          <span class="title">微信好友</span>
        </div>
        <div class="btn" @click="confirm(4)">
          <img src="./image/qq.png" class="img">
          <span class="title">QQ好友</span>
        </div>
        <div class="btn" @click="confirm(5)">
          <img src="./image/qqZone.png" class="img">
          <span class="title">QQ空间</span>
        </div>
        <div class="btn" @click="confirm(6)" v-if="thirdTerminalType==='1'">
          <img src="./image/duanxin.png" class="img">
          <span class="title">短信</span>
        </div>
        <!-- <div class="btn" @click="confirm(7)">
                    <img src="./image/youxiang.png" class="img">
                    <span class="title">邮箱</span>
                </div> -->
      </div>
    </div>
  </div>
</template>

<script type='es6'>
export default {
  props: {
    thirdTerminalType: {
      type: String,
      default: "1"
    }
  },
  data() {
    return {
      // // 获取第三方接入方的状态
      // thirdTerminalType: window.localStorage.thirdTerminalType
    };
  },
  methods: {
    // 1、新浪微博
    // 2、朋友圈
    // 3、微信好友
    // 4、qq好友
    // 确定点击事件
    confirm(index) {
      this.$emit("confirmClick", index);
    },
    // 模态层事件
    fooModalContent() {
      this.$emit("fooModalContent");
    }
  }
};
// @import "~common/less/defined.less";
</script>

<style lang="stylus" scoped>
.main {
  background-color: rgba(0, 0, 0, 0.5);
  z-index: 1000;
  position: absolute;
  top: 0;
  bottom: 0;
  right: 0;
  left: 0;

  .modal-content {
    width: 100%;
    height: 253px;
    position: fixed;
    bottom: 0;
    background-color: #ffffff;

    > p {
      margin: 30px 0;
      text-align: center;
      font-size: 12px;
      color: #999999;
    }

    .btnBlock {
      width: 100%;
      display: flex;
      align-items: center;
      flex-wrap: wrap;

      .btn {
        width: 25%;
        height: 70px;
        margin-bottom: 10px;

        .img {
          height: 50px;
          width: 50px;
          display: block;
          margin: auto;
        }

        .title {
          // margin: 0 auto;
          font-size: 10px;
          color: #19344f;
          line-height: 12px;
          text-align: center;
          display: inline-block;
          width: 100%;
        }
      }
    }
  }
}
</style>


