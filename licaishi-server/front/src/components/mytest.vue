<template>
  <div class="order-container">
    <p>单选框:</p>
    <input type="checkbox" id="checkbox" v-model="checked"/>
    <label >{{checked}}</label> <br>

    <p>多选框：</p>

    <input type="checkbox" id="roob" value="Roob" v-model="checkedNames"/>

    <input type="checkbox" id="goo" value="Google" v-model="checkedNames"/>

    <span>选择的值 {{checkedNames}}</span>

    <select v-model="selected">
      <option value="未选择">选择一个网址</option>
      <option value="www.baidu.com">百度</option>
      <option value="www.google.com">谷歌</option>
    </select>
    <p>选择的值是 {{selected}}</p>

    <input v-model.trim="age" />

    <p>年龄是 {{age}}</p>

    <img class="card-img" :src="imgDataUrl" style="height: 16rem;">
    <div class="card-footer text-muted">
      <input type="file" id="image" name="image" @change="preview($event)">
    </div>

  </div>
</template>
<script type="text/javascript">
  import { changeOrder, changeStatus} from '../vuex/action'
  export default{
    data(){
      let checked = true
      let checkedNames = []
      let selected = '未选择'
      let age
      let imgDataUrl = ''

      return {checked, checkedNames, selected, age, imgDataUrl}
    },
    vuex:{
      getters: {
        goods: state => state.goods,
        goods_unstorage: state => state.goods_unstorage
      },
      actions: {
        changeOrder,
        changeStatus
      }
    },
    methods:{
      changeDisplay: function(){
        if(this.goods_unstorage.length == 0){
          this.changeOrder('goods')
        }
        this.display = !this.display
      },

      say: function(message) {
        alert(message)
      },
      preview: function(event){
        this.imgDataUrl = event.target.files;
      }
    }
  }
</script>
<style type="text/css">
  .btn{
    position: absolute;
    width: 20px;
    bottom: 20px;
    right: 10px;
    background-color: #BFF8B7;
    color: #090808;
    border: 1px solid #bff8b7;
    border-radius: 3px;
    box-shadow: 2px 2px 2px #000000;
    font-weight: bold;
  }
</style>
