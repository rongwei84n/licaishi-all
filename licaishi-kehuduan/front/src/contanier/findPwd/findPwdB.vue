/*
 * @Author: 张浩然 
 * @Date: 2018-04-07 14:49:34 
 * @Last Modified by: 张浩然
 * @Last Modified time: 2018-04-07 21:32:52
 * 
 *  找回密码--设置密码
 */


<template>
  <div id="findPwdB">
    <mt-header title="找回密码--重置密码">
      <mt-button icon="back" @click="back" slot="left"></mt-button>
    </mt-header>
    <div class="findPwdB-content">
      <div class="findPwdB-content-input">
        <mt-field label="密码" placeholder="请输入新密码" type="password" v-model="passwordA">
        </mt-field>
        <mt-field label="确认密码" placeholder="再次输入新密码" type="password" v-model="passwordB">
        </mt-field>
      </div>
      <div class="findPwdB-btn">
        <mt-button type="primary" @click.native="forgetpassword" size="normal">确认</mt-button>
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
      passwordA: "",
      passwordB: "",
      account: "", //账户
      phoneCode: "" //手机验证码
    };
  },
  mounted() {},
  watch: {
    // 监测路由变化,只要变化了就调用获取路由参数方法将数据存储本组件即可
    '$route': "getParams"
  },
  methods: {
    getParams() {
      console.log(111);
      this.account = this.$route.query.account;
      this.phoneCode = this.$route.query.phoneCode;
      console.log(this.$route.query);
    },
    forgetpassword() {
      if (this.passwordA === "") {
        MessageBox("提示", "新密码不能为空");
        return;
      }
      // 判断手机号码是否正确
      if (this.passwordA !== this.passwordB) {
        MessageBox("提示", "两次输入密码不一致");
        return;
      }
      this.$ajax({
        url: `/cli/v1/forgetpassword?authorizationcode=x&newpassword=${
          this.passwordA
        }&phonenumber=${this.account}&verificationcode=${this.phoneCode}`,
        method: "post",
        params: {}
      }).then(res => {
        if (res.data.error === this.$store.state.status) {
          MessageBox.confirm("重置密码成功,点击确定跳转登录").then(action => {
            this.$router.push("/login");
          });
        } else if (res.data.error === "1") {
          MessageBox("提示", "验证码错误");
        } else if (res.data.error === "2") {
          MessageBox("提示", "验证码过期");
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

#findPwdB {
  position: relative;
  height: 100%;
  width: 100%;
  z-index: 100;
  background-color: $color-background-d;

  .findPwdB-content {
    position: absolute;
    width: 100vw;
    top: 40px;
    bottom: 0;

    .findPwdB-content-logo {
      width: 100vw;
      height: 195px;
      border: 1px solid red;
    }
  }

  .findPwdB-content-input {
    .mint-cell-title {
      width: 65px;
    }
  }

  .findPwdB-btn {
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



