/*
 * @Author: 张浩然 
 * @Date: 2018-04-07 14:49:34 
 * @Last Modified by: 张浩然
 * @Last Modified time: 2018-04-07 21:09:11
 * 
 *  登录页面
 */


<template>
  <div id="login">
    <mt-header fixed title="登录">
    </mt-header>
    <div class="login-content">
      <div class="login-content-logo">

      </div>
      <div class="login-content-input">
        <mt-field label="账户" placeholder="请输入手机号码" v-model="account">
        </mt-field>
        <mt-field label="密码" placeholder="请输入登录密码" type="password" v-model="pwd">

        </mt-field>
      </div>
      <div class="login-btn">
        <mt-button type="primary" size="normal" @click.native="login">登录</mt-button>
      </div>
      <div class="more-content">
        <span @click="toRegister">注册</span>
        <span @click="toFindPwdA">忘记密码</span>
      </div>
    </div>
  </div>
</template>

<script type="es6">
import md5 from "js-md5";
export default {
  data() {
    return {
      account: "", //账户
      pwd: "", //密码
      phoneCode: "" //验证码
    };
  },
  methods: {
    /**
     * 跳转注册页面
     */
    toRegister() {
      this.$router.push("/register");
    },
    toFindPwdA() {
      this.$router.push("/findPwdA");
    },
    login() {}
  }
};
</script>

<style lang="stylus">
@import '~common/stylus/variable';

#login {
  position: relative;
  height: 100%;
  width: 100%;
  z-index: 100;
  background-color: $color-background-d;

  .login-content {
    position: absolute;
    width: 100vw;
    top: 40px;
    bottom: 0;

    .login-content-logo {
      width: 100vw;
      height: 195px;
      border: 1px solid red;
    }
  }

  .login-content-input {
    .mint-cell-title {
      width: 45px;
    }
  }

  .login-btn {
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
      font-size: 16px;
      color: $color-dialog-background;
    }
  }
}
</style>



