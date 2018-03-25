<template>
  <div class="container">
    <mt-header fixed :title = "selected" class="header" v-if="$route.path !== '/login'">
        <mt-button slot="left">
            <i class="iconfont" v-if="$route.path == '/index'" @click="sideVisible = !sideVisible">&#xe677;</i>
        </mt-button>
        <mt-button slot="right">
            <i class="iconfont" v-if="$route.path == '/order'">&#xe639;</i>
        </mt-button>
    </mt-header>
    <mt-popup
      :visible.sync="sideVisible"
      position="left" class="sidebar">
      <div class="userinfo">
          <img src="./assets/logo.png" style="width:250px; height:50px;">
          <h4></h4>
          <p></p>
      </div>
      <ul class="sideinfo">
          <li><i class="iconfont"></i></li>
          <li><i class="iconfont"></i></li>
          <li><i class="iconfont"></i></li>
          <li><i class="iconfont"></i></li>
      </ul>
    </mt-popup>
    <mt-tabbar :selected.sync="selected" class="bottom_tabs" fixed v-if="$route.path !== '/login'">
        <mt-tab-item id="首页" v-link="'/index'">
            <i class="iconfont tabs-icon" slot="icon">&#xe622;</i>
            首页
        </mt-tab-item>
        <mt-tab-item id="产品" v-link="'/order'">
            <i class="iconfont tabs-icon" slot="icon">&#xe6e1;</i>
            产品
        </mt-tab-item>
        <mt-tab-item id="工具" v-link="'/goods'">
            <i class="iconfont tabs-icon" slot="icon">&#xe624;</i>
            工具
        </mt-tab-item>
        <mt-tab-item id="我的" v-link="'/personinfo'">
            <i class="iconfont tabs-icon" slot="icon">&#xe677;</i>
            我的
        </mt-tab-item>
        <mt-tab-item id="测试界面" v-link="'/mytest'">
          <i class="iconfont tabs-icon" slot="icon">&#xe677;</i>
          我的测试界面
        </mt-tab-item>
    </mt-tabbar>
    <router-view></router-view>
  </div>
</template>
<script type="text/javascript">
    require('./assets/js/phihome-1.0.0.js?n=1');

    import store from './vuex/store'
    import { changeSelected } from './vuex/action'

    export default{
        store,
        data(){
            let selected = '首页'
            let sideVisible = false
            return { selected, sideVisible }
        },
        vuex:{
            getters:{
                select: state => state.selected
            },
            actions:{
                changeSelected
            }
        },
        watch:{
            'select': function (val, oldVal) {
                this.selected = val
            }
        }
    }
</script>
<style type="text/css">
    html{
        overflow: hidden;
    }
    html,body{
        height: 100%;
    }
    .container{
        height: 100%;
    }
    .bottom_tabs .fa{
        font-size: 22px;
    }
    .search_bar{
        margin-top: 35px;
        border-radius: 2px;
        border: 1px solid #efefef;
        outline: none;
        width: 100%;
    }
    .tabs-icon{
        color: #999898;
    }
    .is-selected .tabs-icon{
        color: #26a2ff;
    }
    .container .sidebar{
        height: 100%;
        width: 70%;
    }
</style>
