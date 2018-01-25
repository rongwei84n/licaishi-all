<template>
    <div class="login-wrap">
        <div class="ms-login">
        <div class="ms-title">PhiHome管理后台</div>
            <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="0px" class="demo-ruleForm">
                <el-form-item prop="username">
                    <el-input v-model="ruleForm.username" placeholder="用户名" @keyup.native.enter="submitForm('ruleForm')">
                      <i slot="prefix" class="el-input__icon el-icon-user"></i>
                      <i slot="suffix" v-show="showUser" class="el-input__icon el-icon-del" @click="delUser"></i>
                    </el-input>
                </el-form-item>
                <el-form-item prop="password">
                  <el-popover
                    ref="popover2"
                    placement="right"
                    :value="disabled"
                    width="200"
                    trigger="manual"
                    content="大小写锁定已打开">
                  </el-popover>
                  <el-input
                    type="password"
                    placeholder="密码"
                    v-model="ruleForm.password"
                    v-popover:popover2
                    @blur="passBlur"
                    @keypress.native="passChange($event)"
                    @keyup.native.enter="submitForm('ruleForm')">
                    <i slot="prefix" class="el-input__icon el-icon-lock"></i>
                    <i slot="suffix" v-show="showPass" class="el-input__icon el-icon-del" @click="delPass"></i>
                  </el-input>
                </el-form-item>
                <div class="login-btn">
                    <el-button type="primary" @click="submitForm('ruleForm')">登录</el-button>
                </div>
                <p style="font-size:12px;line-height:30px;color:#f00;">{{tips}}</p>
            </el-form>
        </div>
    </div>
</template>

<script>
    import commonHandle from "../common/common";
    let checkUser = (rule, value, callback) => {
        let patrnSupport = "";
        for (let codePoint of value) {
          if (codePoint.charCodeAt(0) < 32 || codePoint.charCodeAt(0) > 126) {
            patrnSupport = "ok";
            break;
          }
        }
        if (!value) {
            return callback(new Error("请输入用户名"));
        } else if (value.length < 1 || value.length > 25) {
            return callback(new Error("用户名长度在1到25个字符"));
        } else if (patrnSupport) {
            return callback(new Error("用户名存在特殊字符"));
        } else {
            return callback();
        }
    };
    let checkPass = (rule, value, callback) => {
        if (!value) {
            return callback(new Error("请输入密码"));
        } else {
            return callback();
        }
    };

    export default {
        data () {
            return {
                tip: "",
                disabled: false,
                ruleForm: {
                    username: "",
                    password: ""
                },
                rules: {
                    username: [
                        { validator: checkUser, trigger: "blur" }
                    ],
                    password: [
                        { validator: checkPass, trigger: "blur" }
                    ]
                }
            };
        },
        computed: {
            tips () {
              this.tip = localStorage.getItem("errorMsg");
              return this.tip;
            },
            showUser () {
              let state = this.ruleForm.username.length > 0;
              return state;
            },
            showPass () {
              let state = this.ruleForm.password.length > 0;
              return state;
            }
        },
        methods: {
            delUser () {
              this.ruleForm.username = "";
            },
            delPass () {
              this.ruleForm.password = "";
              this.disabled = false;
            },
            passBlur () {
              this.disabled = false;
            },
            passChange (event) {
              let valueCapsLock = event.keyCode ? event.keyCode : event.which; // 按键
              var valueShift = event.shiftKey ? event.shiftKey : valueCapsLock === 16; // shift键是否按住
              if ((valueCapsLock >= 65 && valueCapsLock <= 90) && !valueShift) {
                // 输入了大写字母，并且shift键没有按住，说明Caps Lock打开
                this.disabled = true;
              } else {
                this.disabled = false;
              }
            },
            submitForm (formName) {
                const self = this;
                self.$refs[formName].validate((valid) => {
                    if (valid) {
                        let params = {};
                        params.account = self.ruleForm.username;
                        params.passwd = self.ruleForm.password;
                        let fn = function (response, self) {
                          localStorage.setItem("real_name", response.data.result.real_name);
                          localStorage.setItem("userName", response.data.result.userName);
                          localStorage.setItem("uid", response.data.result.uid);
                          localStorage.setItem("token", response.data.result.token);
                          localStorage.setItem("errorMsg", "");
                          self.$router.push(response.data.result.defaultPageUrl);
                        };
                        commonHandle.requestPost("/ota/user_login", self, params, fn);
                    } else {
                        console.log("error submit!!");
                        return false;
                    }
                });
            }
        }
    };
</script>

<style>
  .login-wrap .el-icon-user:before{
    display: inline-block;
    content:"";
    width: 18px;
    height: 18px;
    background-image: url("../../assets/img/user.png");
    background-size: 18px 18px;
    background-repeat: no-repeat;
  }
  .login-wrap .el-icon-user-outline:before{
    display: inline-block;
    content:"";
    width: 18px;
    height: 18px;
    background-image: url("../../assets/img/user.png");
    background-size: 18px 18px;
    background-repeat: no-repeat;
  }
  .login-wrap .el-icon-del:before{
    display: inline-block;
    content:"";
    width: 18px;
    height: 18px;
    background-image: url("../../assets/img/del.png");
    background-size: 18px 18px;
    background-repeat: no-repeat;
  }
  .login-wrap .el-icon-del:hover{
    cursor: pointer;
  }
  .login-wrap .el-icon-del-outline:before{
    display: inline-block;
    content:"";
    width: 18px;
    height: 18px;
    background-image: url("../../assets/img/del.png");
    background-size: 18px 18px;
    background-repeat: no-repeat;
  }
  .login-wrap .el-icon-lock:before{
    display: inline-block;
    content:"";
    width: 18px;
    height: 18px;
    background-image: url("../../assets/img/lock.png");
    background-size: 18px 18px;
    background-repeat: no-repeat;
  }
  .login-wrap .el-icon-lock-outline:before{
    display: inline-block;
    content:"";
    width: 18px;
    height: 18px;
    background-image: url("../../assets/img/lock.png");
    background-size: 18px 18px;
    background-repeat: no-repeat;
  }
  .login-wrap .el-form-item.is-success .el-input__inner,
  .login-wrap .el-form-item.is-success .el-input__inner:focus,
  .login-wrap .el-form-item.is-success .el-textarea__inner,
  .login-wrap .el-form-item.is-success .el-textarea__inner:focus {
    border-color: #75c4ff;
  }
  .login-wrap .el-input__icon{
    line-height: 46px;
  }
  .login-wrap{
    position: relative;
    width:100%;
    height:100%;
    background-color: #fff;
    background-image: url("../../assets/img/login.jpg");
    background-size: 100% 100%;
    background-repeat: no-repeat;
  }
  .ms-title{
    width: 100%;
    text-align: center;
    margin-bottom: 40px;
    color: #63b9f9;
    font-family: "Microsoft YaHei";
    font-size: 28px;
    font-weight: bold;
  }
  .ms-login{
    position: absolute;
    left: 50%;
    top:50%;
    width:280px;
    height:240px;
    margin: -230px 0 0 -180px;
    padding:40px;
    border-radius: 6px;
    box-shadow: 1px 1px 1px 2px  rgba(61, 60, 61, 0.35);
    background: #fff;
  }
  .ms-login .el-input__inner{
    padding:3px 36px !important;
  }
  .login-btn{
    text-align: center;
  }
  .login-btn button{
    width:100%;
    height:36px;
  }
</style>
