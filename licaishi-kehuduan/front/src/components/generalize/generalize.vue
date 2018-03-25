<template>
  <div class="generalize-wrapper">
    <workRoomHeader @back="back"  mytitle="" ></workRoomHeader>
    <div class="logo">
    </div>
    <div class="generalize-avatar">
      <img width="100%" height="100%" :src="headerAvatar"  />
    </div>
    <div class="generalize-content">
      <div class="name-class">
        {{name}}
      </div>
      <div class="tel-class">
        {{tel}}
      </div>
      <div class="des-class">
        &nbsp;&nbsp;&nbsp;&nbsp;{{des}}
      </div>
      <div class="tuiguang-class">
        推广方式
      </div>
      <div class="tuiguangfangshi">
        <table>
          <tr>
            <td>
              1、向客户出示二维码，通过扫码注册
            </td>
          </tr>
          <tr>
            <td>
              2、分享链接给客户，通过链接注册
            </td>
          </tr>
        </table>
      </div>
    </div>
  </div>
</template>

<script type="text/ecmascript-6">
  import workRoomHeader from "components/workRoomHeader/workRoomHeader";
  export default {
    data() {
      return {
        headerAvatar:"",
        name:"理财师姓名",
        tel:"18682052180",
        des:"是考虑到交付时间过来看世界各类考试结果历史来看过还是考虑的健康围殴提偶我还不能",
        neturl: "http://47.97.100.240/"
      }
    },
    created:function () {
      this.queryAccountDetail();
    },
    methods: {
      back() {
        this.$router.go(-1)
      },
      queryAccountDetail() {
        let _this = this;
        window.phihome.util.netRequest(
          "get",
          _this.neturl + "srv/v1/accountDetail",
          "",
          "",
          function (response) {
            response = JSON.parse(response);
            if (response.error == 0) {
              //获取账号成功
              _this.name = response.data.nickname;
              _this.tel = response.data.phonenumber;
              //_this.des = response.data.workstudio;
              _this.headerAvatar = response.data.img;

              //_this.isLogin = true;
            } else {
              _this.name = "未设置";
              //_this.isLogin = false;
              _this.headerAvatar = '';
            }
          }
        )
      }
    },
    components: {
      workRoomHeader
    }
  }

</script>

<style lang="stylus" rel="stylesheet/stylus">
  .generalize-wrapper
    position absolute
    top 0px
    width 100%
    height 100vh
    background #FFF
    z-index 100
    background-image url("~@/common/image/lcs_tuiguang.png")
    background-size 100% 100%
    background-repeat no-repeat
    .logo
      position absolute
      width: 14%;
      top 4vh
      right 3vw
      height: 6%;
      background-image: url(/static/img/lcs-logo.3133185.png);
      background-size: 100% 100%;
      background-repeat no-repeat
    .generalize-avatar
      width 90px
      height 90px
      margin: 0px  auto 10px auto
      border-radius  50%
      background #FFF
      overflow hidden
    .generalize-content
      text-align center
      color #FFF
      div
        margin-top 14px
      .name-class
        font-size 15px
      .tel-class
        font-size 15px
      .des-class
        font-size 13px
        line-height 22px
        text-align left
        padding 0px 10px
      .tuiguangfangshi
        table
          margin auto
          tr
            td
              height 44px
              line-height 44px
              text-align left
              font-size 14px

    .mint-header
      background none
      height 45px
    .mint-header-button.is-left
      color:#FFF


</style>
