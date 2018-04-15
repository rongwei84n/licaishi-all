/*
 * @Author: zhanghr 
 * @Date: 2018-04-09 15:01:40 
 * @Last Modified by: 张浩然
 * @Last Modified time: 2018-04-15 12:17:16
 *
 *  分享进来的注册页
 */


<template>
  <div id="Register">
    <mt-header title="诚邀您加入嘿牛理财"></mt-header>
    <div class="Register-content">
      <div class="Register-content-input">
        <mt-field label="手机号码" placeholder="请输入手机号码" :attr="{ maxlength: 11 }" type="tel" v-model="account">
          <!-- <i class="fa fa-user fa-fw"></i> -->
        </mt-field>
        <mt-field label="密码" placeholder="请输入登录密码" type="password" v-model="pwd"></mt-field>
        <mt-field label="重复密码" placeholder="请再次输入登录密码" type="password" v-model="pwdB"></mt-field>
        <mt-field label="验证码" placeholder="请输入验证码" :attr="{ maxlength: 6 }" v-model="phoneCode">
          <span v-if="phoneCodeStatus" style="color:red;" @click="getPhoneCode">发送验证码</span>
          <span v-else>{{timer}}秒后重发</span>
        </mt-field>
      </div>
      <div class="Register-btn">
        <mt-button type="primary" :disable="disable" @click.native="register" size="normal">立即注册</mt-button>
      </div>
      <!-- TODO:此处预留，是否阅读用户协议 -->
      <div class="more-content">
        <span>我已阅读并同意用户注册协议</span>
        <!-- <span>忘记密码</span> -->
      </div>
    </div>
  </div>
</template>

<script type="es6">
import md5 from "js-md5";
import { MessageBox } from "mint-ui";
import { isPhone } from "common/js/Phone";

export default {
  data() {
    return {
      account: "", //账户
      pwd: "", //密码
      pwdB: "", //密码
      phoneCode: "", //手机验证码
      disable: false,
      registersource: "x", //注册来源
      timeOut: "", //计时器对象
      phoneCodeStatus: true,
      timer: 59 //倒计时时间
    };
  },
  created() {
    if (this.$route.query.uid) {
      this.registersource = this.$route.query.uid;
      // MessageBox("测试", "当前理财师id为" + this.registersource);
    }
  },
  methods: {
    //检测账户状态
    checkPhonenumber() {
      this.$ajax({
        url: `/cli//v1/checkPhonenumber?authorizationcode=x&phonenumber=${
          this.phonenumber
        }`,
        method: "get"
      }).then(res => {
        if (res.status === this.$store.state.status) {
        }
      });
    },
    getPhoneCodeTime() {
      // 倒计时验证码触发
      this.phoneCodeStatus = false;
      this.timeOut = setInterval(() => {
        if (this.timer > 0 && this.timer <= 59) {
          this.timer--;
        } else {
          this.phoneCodeStatus = true;
          clearInterval(this.timeOut);
          this.timer = 59;
        }
      }, 1000);
    },
    // 获取手机验证码
    getPhoneCode() {
      if (this.account === "") {
        MessageBox("提示", "手机号码不能为空");
        return;
      }
      // 判断手机号码是否正确
      if (!isPhone(this.account)) {
        MessageBox("提示", "手机号码格式不正确");
        return;
      }
      // 先判断账户是否存在，然后请求验证码
      // 0.表示成功；11.授权码错误；14. 该账户已经存在；25.邮箱已经注册；34.手机号格式错误；
      this.$ajax({
        url: `/cli//v1/checkPhonenumber?authorizationcode=x&phonenumber=${
          this.account
        }`,
        method: "get"
      }).then(res => {
        if (res.data.error === this.$store.state.status) {
          // 请求验证码
          // TODO:此处应该添加验证码时间限制
          this.getPhoneCodeTime();
          this.$ajax({
            url: `/cli/v1/verificationMsg?authorizationcode=x&phonenumber=${
              this.account
            }&verificationtype=xxx`,
            method: "GET"
          }).then(res => {
            if (res.status === this.$store.state.status) {
            }
          });
        } else if (res.data.error === "14") {
          MessageBox("提示", "该账户已经存在");
          return;
        } else if (res.data.error === "25") {
          MessageBox("提示", "邮箱已经注册");
          return;
        } else if (res.data.error === "34") {
          MessageBox("提示", "手机号格式错误");
          return;
        }
      });
    },
    // 注册按钮
    register() {
      if (this.account === "") {
        MessageBox("提示", "手机号码不能为空");
        return;
      }
      if (this.pwd === "") {
        MessageBox("提示", "密码不能为空");
        return;
      }
      if (this.pwdB === "") {
        MessageBox("提示", "请再次输入登录密码");
        return;
      }
      if (this.pwdB !== this.pwd) {
        MessageBox("提示", "两次输入的密码不一致");
        return;
      }
      if (this.phoneCode === "") {
        MessageBox("提示", "手机验证码不能为空");
        return;
      }
      // 判断手机号码是否正确
      if (!isPhone(this.account)) {
        MessageBox("提示", "手机号码格式不正确");
        return;
      }
      this.$ajax({
        url: `/cli/v1/account?password=${md5(this.pwd)}&phonenumber=${
          this.account
        }&registersource=${this.registersource}&verificationcode=${
          this.phoneCode
        }`,
        method: "post",
        parmas: {
          password: md5(this.pwd),
          phonenumber: this.account,
          registersource: this.registersource,
          verificationcode: this.phoneCode
        }
      }).then(res => {
        if (res.data.error === this.$store.state.status) {
          MessageBox.confirm("您已注册成功!点击确定按钮下载app").then(
            action => {
              // TODO:此处后面得根据安卓或者ios
              window.location.href = "https://fir.im/terf";
            }
          );
        }
      });
    },
    /**
     * 返回按钮单机事件
     */
    back() {
      this.$router.go(-1);
    }
  }
};
</script>

<style lang="stylus">
@import '~common/stylus/variable';

#Register {
  position: relative;
  height: 100%;
  width: 100%;
  z-index: 100;
  background-color: $color-background-d;

  .Register-content {
    position: absolute;
    width: 100vw;
    top: 40px;
    bottom: 0;

    .Register-content-logo {
      width: 100vw;
      height: 195px;
      border: 1px solid red;
    }
  }

  .Register-content-input {
    .mint-cell-title {
      width: 65px;
    }
  }

  .Register-btn {
    width: 100vw;
    padding: 20px;

    .mint-button--normal {
      width: 100%;
    }
  }

  .more-content {
    padding: 0 20px;
    display: flex;
    justify-content: space-between;

    >span {
      font-size: 14px;
      color: $color-dialog-background;
    }
  }
}
</style>




