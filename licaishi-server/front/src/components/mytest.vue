<template>
  <div class="order-container">
    <input type="button" value="点击获取" @click="get()"/>

    <!--<li v-for="item in arrs" v-on:click="item.completed = ! item.completed">-->
      <!--&lt;!&ndash; {{item}} &ndash;&gt;-->

      <!--<span class="title">{{item.title}}</span>-->

    <!--</li>-->

    <button v-on:click="invokeNative">调用本地方法</button>
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
      let arrs

      return {checked, checkedNames, selected, age, imgDataUrl, arrs}
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
      },
      invokeNative: function () {
        alert('调用本地方法')
        window.phihome.util.netRequest('get','http://114.141.173.53/v1/accountDetail','', '',function (response) {

        })


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
