<template>
    <div class="header clearfix">
        <div class="user-info">
            <span class="el-dropdown-link">
                &nbsp{{username}}&nbsp&nbsp&nbsp
                <span class="logout" @click="logout">
                    <span class="banner icon-phihome-logout" @click="logout"></span>
                  注销
                </span>
            </span>
        </div>
        <div class="logo">
          <img width="186px" height="19px" src="../../assets/img/logo.png">
          <span class="title">深研后台管理系统</span>
        </div>
    </div>
</template>
<script>
    import commonHandle from "./common";
    export default {
        data () {
            return {
              name: ""
            };
        },
        computed: {
            username () {
                this.name = localStorage.getItem("real_name");
                return this.name;
            }
        },
        created () {
        },
        methods: {
            logout () {
                let params = {};
                params.userName = localStorage.getItem("userName");
                params.token = localStorage.getItem("token");
                let fn = function (response, self) {
                    localStorage.clear();
                    self.$router.push("/login");
                };
                commonHandle.requestPost("/ota/user_logout", this, params, fn);
            }
        }
    };
</script>
<style lang="less" scoped>
    .header {
        min-width: 620px;
        position: relative;
        box-sizing: border-box;
        height: 70px;
        font-size: 14px;
        line-height: 70px;
        color: #fff;
    }
    .header .logo{
        float: left;
        padding-left: 44px;
        text-align: center;
        img{
          vertical-align: text-bottom;
        }
        .title{
          font-size: 20px;
          padding-left: 50px;
          letter-spacing: 6.4px;
        }
    }
    .user-info {
        float: right;
        padding-right: 22px;
        color: #fff;
    }
    .user-info .el-dropdown-link{
        position: relative;
        display: inline-block;
        padding-left: 50px;
        color: #f5f7fa;
        cursor: pointer;
        vertical-align: middle;
    }
    .banner{
      vertical-align: middle;
    }
    .el-dropdown-link .logout:hover{
        color: #f7ba2a;
    }
</style>
