<template>
  <div class="order-container">

    <input type="button" value="热门产品" v-on:click="getHotProducts"></input>
    <p>{{xxx}}</p>

    <ul id="example-1">
      <li v-for="hot_p in hotProducts">
        {{ hot_p.name }}
        {{hot_p.url}}
      </li>
    </ul>

  </div>
</template>
<script type="text/javascript">
  import { changeOrder, changeStatus} from '../vuex/action'

  let prods = []; // 热门产品
  export default {
    data() {
      return {
        xxx:'xx',
        roomJsonArr:[],
        hotProducts:[prods]
      }
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
      getHotProducts: function () {
        let _this = this;
        window.phihome.util.netRequest('get','http://192.168.1.109:8094/server/hot_products','', '',function (response) {
          let errorMessage;
          response = JSON.parse(response);
          if (response.errorCode == 0) {
            let netResponse = JSON.parse(response.netResponse);

            let responseResult = netResponse.result;
            _this.roomJsonArr = [];
            for (let i = 0; i < responseResult.length; i++) {
              let hotProI = responseResult[i];
              _this.roomJsonArr.push({'name': hotProI.name, 'url': hotProI.url});
            }
            _this.$data.hotProducts = _this.roomJsonArr;


            // if (netResponse.status == 200) {
            //   _this.hotProducts = netResponse.message;
            //   window.phihome.app.hideLoading('', function (response) {
            //   });
            // } else {
            //   if (netResponse.status == 11001) { // 账号下没有该设备，返回设备首页
            //     _this.device_name = '智能排插TC1';
            //     _this.toast_show = true;
            //   } else {
            //     window.phihome.app.toast(netResponse.message, function (response) {
            //     });
            //     window.phihome.app.hideLoading('', function (response) {
            //     });
            //   }
            // }
          } else {
            errorMessage = response.errorMsg;
            window.phihome.app.toast(errorMessage, function (response) {
            });
            window.phihome.app.hideLoading('', function (response) {
            });
          }
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
