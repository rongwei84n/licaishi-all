<template>
  <div class="rank">
    <div class="rank-context">
      <div class="logo">
      </div>
      <img src="~@/common/image/xuanchuan.png" class="xuanchuan"><br/>
      <div class="rank-tubiaoqu">
        <div class="tubiao-flex">
          <div class="tubiao" @click="rankClick" id="1">
            <img src="~@/common/image/workroom-info.png" alt="工作室信息"><br>
            <span>工作室信息</span>
          </div>
        </div>
        <div class="tubiao-flex">
          <div class="tubiao" @click="rankClick" id="2">
            <img src="~@/common/image/my-custom.png" alt="我的客户"><br/>
            <span>我的客户</span>
          </div>
        </div>
        <div class="tubiao-flex">
          <div class="tubiao" @click="rankClick" id="3">
            <img src="~@/common/image/my-fee.png" alt="我的佣金"><br/>
            <span>我的佣金</span>
          </div>
        </div>
        <div class="tubiao-flex">
          <div class="tubiao" @click="rankClick" id="4">
            <img src="~@/common/image/generalize.png" alt="我要推广"><br/>
            <span>我要推广</span>
          </div>
        </div>
      </div>
    </div>
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  data() {
    return {};
  },
  methods: {
    rankClick(e) {
      let flag = parseInt(e.currentTarget.id);

      if (flag === 1) {
        this.$router.push("/rank/workroominfo");
      } else if (flag === 2) {
        this.$router.push("/rank/mycustom");
      } else if (flag === 3) {
        this.$router.push("/rank/mycommission");
      } else if (flag === 4) {
        this.$router.push("/rank/generalize");
      }
    }
  }
};
</script>

<style lang="stylus" rel="stylesheet/stylus">
.rank {
  position: relative;
  width: 100%;
  height: 100vh;

  .rank-context {
    position: absolute;
    bottom: 46px;
    width: 100%;
    top: 0px;
    // height 100%
    overflow: hidden;
    // background-color: #eff3f6;
    z-index: 2;
    background-image: url('~@/common/image/workroom-backimg.png');
    background-size: 100% 100%;

    .logo {
      width: 14%;
      height: 6%;
      background-image: url('~@/common/image/lcs-logo.png');
      background-size: 100% 100%;
      margin: 36px 20px 0px auto;
      // float right
    }

    .xuanchuan {
      display: block;
      width: 90%;
      height: 26%;
      margin: 0px auto 10px auto;
    }

    .rank-tubiaoqu {
      display: flex;
      width: 80%;
      margin: auto;
      // margin-top 10%
      flex-wrap: wrap;
      justify-content: space-around;
      align-items: center;

      .tubiao-flex {
        flex: 0 0 50%;

        .tubiao {
          width: 85px;
          height: 85px;
          background: #FFF;
          border-radius: 16px;
          text-align: center;
          margin: 0px auto 30%;
          padding-top: 12px;

          img {
            width: 48px;
          }

          span {
            display: block;
            font-size: 13px;
            margin-top: 5px;
          }
        }
      }
    }
  }
}
</style>
