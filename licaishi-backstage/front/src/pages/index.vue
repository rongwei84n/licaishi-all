<template>
  <div class="index-wrap">
    <div class="index-left">
      <div class="index-left-block">
        <h2>基础数据管理</h2>
          <ul>
            <li v-for="item in basicList">
              <a :href="item.url">{{ item.name }}</a>
            </li>
          </ul>
      </div>
      <div class="index-left-block lastest-news">
        <h2>统计分析</h2>
      </div>
    </div>
    <div class="index-right">
      <div class="index-board-list">
      </div>
    </div>
  </div>
</template>

<script>
    export default {
      created: function(){
        this.$http.get('getList').then(function(data){
          console.log(data);
        },function(err){
          console.log(err);
        });
      },
      data() {
        return {
          basicList: [
            {
              name: '理财师管理',
              url: ''
            },
            {
              name: '订单管理',
              url: ''
            }
          ]
        }
      }
    }
</script>

<style scoped>
  .index-wrap {
    width: 1200px;
    margin: 0 auto;
    overflow: hidden;
  }
  .index-left {
    float: left;
    width: 300px;
    text-align: left;
  }
  .index-right {
    float: left;
    width: 900px;
  }
  .index-left-block {
    margin-top: 15px;
    background: #fff;
    box-shadow: 0 0 1px #ddd;
  }
  .index-left-block .hr {
    margin-bottom: 20px;
  }
  .index-left-block h2 {
    background: #390804;
    color: #fff;
    padding: 10px 15px;
  }
  .index-left-block h3 {
    padding: 0 15px 5px 15px;
    font-weight: bold;
    color: #222;
  }
  .index-left-block ul {
    padding: 10px 15px;
  }
  .index-left-block li {
    padding: 5px;
  }
  .index-board-list {
    overflow: hidden;
  }
  .index-board-item {
    float: left;
    width: 400px;
    background: #fff;
    box-shadow: 0 0 1px #ddd;
    padding: 20px;
    margin-right: 20px;
    margin-bottom: 20px;
  }
  .index-board-item-inner {
    min-height: 125px;
    padding-left: 120px;
  }
  .index-board-car .index-board-item-inner{
    background: url(../assets/images/1.png) no-repeat;
  }
  .index-board-loud .index-board-item-inner{
    background: url(../assets/images/2.png) no-repeat;
  }
  .index-board-earth .index-board-item-inner{
    background: url(../assets/images/3.png) no-repeat;
  }
  .index-board-hill .index-board-item-inner{
    background: url(../assets/images/4.png) no-repeat;
  }
  .index-board-item h2 {
    font-size: 18px;
    font-weight: bold;
    color: #000;
    margin-bottom: 15px;
  }
  .line-last {
    margin-right: 0;
  }
  .index-board-button {
    margin-top: 20px;
  }
  .lastest-news {
    min-height: 512px;
  }
  .hot-tag {
    background: red;
    color: #fff;
  }
  .new-item {
    display: inline-block;
    width: 230px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
</style>
