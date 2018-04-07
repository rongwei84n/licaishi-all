/*
 * @Author: 张浩然 
 * @Date: 2018-04-07 14:49:34 
 * @Last Modified by: 张浩然
 * @Last Modified time: 2018-04-07 22:43:15
 * 
 *  找回密码--验证身份
 */

<template>
  <div id="findPwdA">
    <mt-header title="找回密码--验证身份">
      <mt-button icon="back" @click="back" slot="left"></mt-button>
    </mt-header>
    <div class="findPwdA-content">
      <div class="findPwdA-content-input">
        <mt-field label="手机号码:" placeholder="请输入手机号码" :attr="{ maxlength: 11 }" type="tel" v-model="account">
        </mt-field>
        <mt-field label="验证码:" placeholder="请输入验证码" :attr="{ maxlength: 6 }" v-model="phoneCode">
          <span v-if="phoneCodeStatus" style="color:red;" @click="getPhoneCode">发送验证码</span>
          <span v-else>{{timer}}秒后重发</span>
        </mt-field>
      </div>
      <div class="findPwdA-btn">
        <mt-button type="primary" :disable="disable" @click.native="toFindPwdB" size="normal">下一步</mt-button>
      </div>
    </div>
  </div>
</template>

<script type="es6">
import { MessageBox } from "mint-ui";
import { isPhone } from "common/js/Phone";

export default {
  data() {
    return {
      account: "17762864299", //账户
      phoneCode: "", //手机验证码

      disable: false,
      findPwdAsource: "", //注册来源
      timeOut: "", //计时器对象
      phoneCodeStatus: true,
      timer: 59 //倒计时时间
    };
  },
  methods: {
    msgFoo(key, msg) {
      if (this[key] === "") {
        MessageBox("提示", msg);
        return;
      }
    },
    toFindPwdB() {
      this.$router.push("/findPwdB");
      if (this.account === "") {
        MessageBox("提示", "手机号码不能为空");
        return;
      }
      if (this.phoneCode === "") {
        MessageBox("提示", "验证码不能为空");
        return;
      }
      // 校验短信验证码
      this.$ajax({
        url: `/cli/v1/verifyVerificationCode?authorizationcode=x&phonenumber=${
          this.account
        }&verificationcode=${this.phoneCode}`,
        method: "get"
      }).then(res => {
        if (res.data.error === this.$store.state.status) {
          this.$router.push({
            path: "/findPwdB",
            query: {
              account: this.account, //账户
              phoneCode: this.phoneCode //手机验证码
            }
          });
        } else if (res.data.error === "1") {
          MessageBox("提示", "验证码错误");
          return;
        } else if (res.data.error === "2") {
          MessageBox("提示", "验证码过期");
          return;
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
        } else if (res.data.error === "14") {
          this.getPhoneCodeTime();
          this.$ajax({
            url: `/cli/v1/verificationMsg?authorizationcode=x&phonenumber=${
              this.account
            }&verificationtype=xxx`,
            method: "GET"
          }).then(res => {
            if (res.data.error === this.$store.state.status) {
            }
          });
          return;
        } else if (res.data.error === "25") {
          MessageBox("提示", "邮箱已经注册");
          return;
        } else if (res.data.error === "34") {
          MessageBox("提示", "手机号格式错误");
          return;
        }
      });

      // if (this.account === "") {
      //   MessageBox("提示", "手机号码不能为空");
      //   return;
      // }
      // // 判断手机号码是否正确
      // if (!isPhone(this.account)) {
      //   MessageBox("提示", "手机号码格式不正确");
      //   return;
      // }
      // this.$ajax({
      //   url: `/srv/v1/verifyVerificationCode?authorizationcode=${""}&phonenumber=${
      //     this.account
      //   }&verificationcode=0`,
      //   method: "GET"
      // }).then(res => {
      //   if (res.status === this.$store.state.status) {
      //   }
      // });
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

#findPwdA {
  position: relative;
  height: 100%;
  width: 100%;
  z-index: 100;
  background-color: $color-background-d;

  .findPwdA-content {
    position: absolute;
    width: 100vw;
    top: 40px;
    bottom: 0;

    .findPwdA-content-logo {
      width: 100vw;
      height: 195px;
      border: 1px solid red;
    }
  }

  .findPwdA-content-input {
    .mint-cell-title {
      width: 65px;
    }
  }

  .findPwdA-btn {
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



