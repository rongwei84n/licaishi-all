<template>
  <div>
    <div class="app-head">
      <div class="app-head-inner">
        <router-link :to="{path: '/'}">
          <img src="../assets/logo.png">
        </router-link>
        <div class="head-nav">
          <ul class="nav-list">
            <li>登录</li>
            <li class="nav-pile">|</li>
            <li>注册</li>
            <li class="nav-pile">|</li>
            <li>关于</li>
          </ul>
        </div>
      </div>
    </div>
    <div class="index-wrap">
      <div class="index-left">
        <div class="index-left-block">
          <h2>基础数据管理</h2>
          <ul>
            <router-link v-for="item in basicList" :key="item.name" :to="{ path: item.path }" tag="li" active-class="active">
              {{ item.name }}
            </router-link>
          </ul>
        </div>
        <div class="index-left-block lastest-news">
          <h2>统计分析</h2>
        </div>
      </div>
      <div class="index-right">
        <div class="index-board-list">
          <keep-alive>
            <router-view></router-view>
          </keep-alive>
        </div>
      </div>
    </div>
    <div class="app-foot">
      <p>© 2018 理财师管理后台</p>
    </div>
  </div>
</template>

<script>
  export default {
    name: "index",
    data() {
      return {
        basicList: [
          {
            name: '理财师管理',
            path: '/index/financermange'
          },
          {
            name: '订单管理',
            path: '/index/ordermanage'
          }
        ]
      }
    }
  }
</script>

<style>
  .app-head {
    background: #363636;
    color: #b2b2b2;
    height: 90px;
    line-height: 90px;
    width: 100%;
  }
  .app-head-inner {
    width: 1200px;
    margin: 0 auto;
  }
  .app-head-inner img {
    width: 100px;
    margin-top: 10px;
    float: left;
  }
  .head-nav {
    float: right;
  }
  .head-nav ul {
    overflow: hidden;
  }
  .head-nav li {
    cursor: pointer;
    float: left;
  }
  .nav-pile {
    padding: 0 10px;
  }
  .app-foot {
    text-align: center;
    height: 80px;
    width: 100%;
    line-height: 80px;
    background: #e3e4e8;
    clear: both;
    margin-top: 10px;
  }

  .index-wrap {
    width: 1200px;
    margin: 0 auto;
    overflow: hidden;
  }
  .index-left {
    float: left;
    width: 250px;
    text-align: left;
  }
  .index-right {
    float: left;
    width: 950px;
  }
  .index-left-block {
    margin-top: 15px;
    background: #fff;
    box-shadow: 0 0 1px #ddd;
  }
  .index-left-block h2 {
    background: #390804;
    color: #fff;
    padding: 10px 15px;
  }
  .index-left-block li {
    padding: 12px;
    cursor: pointer;
  }
  .index-left-block li:hover {
    background-color: #cccccc;
  }
  .active {
    background-color: #cccccc;
  }
  .index-board-list {
    overflow: hidden;
  }
  .lastest-news {
    min-height: 112px;
  }
  .mange-container {
    background: #fff;
    width:100%;
    min-height:550px;
    margin:15px;
    padding:15px;
  }
  .data-table {
    width:90%;
  }
  .data-table th {
    background: #999;
    color: #fff;
    padding:15px;
    font-weight: bold;
    border: 1px solid #f0f2f5;
  }
  .data-table td {
    border: 1px solid #f0f2f5;
    padding: 15px;
  }
</style>
