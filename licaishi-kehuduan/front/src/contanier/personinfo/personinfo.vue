/*
 * @Author: 张浩然 
 * @Date: 2018-04-07 14:49:34 
 * @Last Modified by: zhanghr
 * @Last Modified time: 2018-04-09 16:55:21
 * 
 *  注册页面
 */


<template>
  <div id="personinfo">
    <mt-header title="账号设置">
      <mt-button icon="back" @click="back" slot="left"></mt-button>
    </mt-header>
    <div class="personinfo-content">
      <div class="personinfo-content-input">
        <div class="personinfo-content-input-head">
          <img src="~common/image/my_settings_order.png" alt="" @click="updataHead">
        </div>
        <div class="personinfo-content-input-cell">
          <mt-cell :title="account" is-link>
            <span slot="icon">姓名:</span>
          </mt-cell>
        </div>
        <div class="personinfo-content-input-cell">
          <mt-cell :title="pwd">
            <span slot="icon">登录名：</span>
          </mt-cell>
        </div>
        <div class="personinfo-content-input-cell">
          <mt-cell :title="phoneCode">
            <span slot="icon">手机号：</span>
          </mt-cell>
        </div>
        <div class="personinfo-content-input-cell">
          <mt-cell :title="phoneCode" is-link>
            <span slot="icon">工作室名称：</span>
          </mt-cell>
        </div>
        <div class="personinfo-content-input-cell">
          <mt-cell title="" is-link>
            <span slot="icon">修改登录密码</span>
          </mt-cell>
        </div>
        <div class="personinfo-content-input-cell">
          <mt-cell title="" is-link>
            <span slot="icon">关于嘿牛理财师</span>
          </mt-cell>
        </div>
      </div>
      <div class="personinfo-btn">
        <mt-button type="danger" :disable="disable" @click.native="logout" size="normal">退出当前账号</mt-button>
      </div>
    </div>
    <mt-actionsheet :actions="actions" v-model="sheetVisible">
    </mt-actionsheet>
  </div>
</template>

<script type="es6">
import md5 from "js-md5";
import { MessageBox } from "mint-ui";
import { isPhone } from "common/js/Phone";

export default {
  data() {
    return {
      account: "17762864299", //账户
      pwd: "ran303030", //密码
      phoneCode: "", //手机验证码
      disable: false,
      personinfosource: "x", //注册来源
      timeOut: "", //计时器对象
      phoneCodeStatus: true,
      timer: 59, //倒计时时间,
      actions: [
        {
          name: "拍照",
          method: this.photograph
        },
        {
          name: "从相册中选择",
          method: "111"
        }
      ],
      sheetVisible: false //选择头像弹窗
    };
  },
  methods: {
    // 退出登录
    updataHead() {
      this.sheetVisible = true;
    },
    // 拍照函数
    photograph() {
      console.log(111);
    },
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
    personinfo() {
      if (this.account === "") {
        MessageBox("提示", "手机号码不能为空");
        return;
      }
      if (this.pwd === "") {
        MessageBox("提示", "密码不能为空");
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
        }&personinfosource=${this.personinfosource}&verificationcode=${
          this.phoneCode
        }`,
        method: "post",
        parmas: {
          password: md5(this.pwd),
          phonenumber: this.account,
          personinfosource: this.personinfosource,
          verificationcode: this.phoneCode
        }
      }).then(res => {
        console.log(res);
        if (res.data.error === this.$store.state.status) {
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

#personinfo {
  position: relative;
  height: 100%;
  width: 100%;
  z-index: 100;

  .personinfo-content {
    position: absolute;
    width: 100vw;
    top: 40px;
    bottom: 0;
    background-color: $color-background;

    .personinfo-content-logo {
      width: 100vw;
      height: 195px;
      border: 1px solid red;
    }
  }

  /* 信息块 */
  .personinfo-content-input {
    background-color: $color-background-d;

    .mint-cell-title {
      width: auto;
    }

    /* 账户头像 */
    .personinfo-content-input-head {
      display: flex;
      justify-content: center;
      align-items: center;
      height: 100px;

      >img {
        width: 66px;
        height: 66px;
      }
    }

    /* 修改了cell组件内的text样式 */
    .personinfo-content-input-cell {
      border-top: 1px solid #c8c8cd;

      .mint-cell-title {
        >span {
          color: #7e8c8d;
        }

        .mint-cell-text {
          font-size: 16px;
          color: $color-dialog-background;
        }
      }
    }
  }

  .personinfo-btn {
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



