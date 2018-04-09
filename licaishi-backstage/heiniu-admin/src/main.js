import Vue from "vue";
import iView from "iview";
import moment from "moment";
import { router } from "./router/index";
import store from "./store";
import App from "./app.vue";
import "iview/dist/styles/iview.css";
import ajax from "@/libs/ajax";
import VueQuillEditor from "vue-quill-editor";
import "quill/dist/quill.core.css";
import "quill/dist/quill.snow.css";
import "quill/dist/quill.bubble.css";

import { imgUrl } from "@/libs/allConfig.js";

Vue.use(iView);

moment.locale("zh-CN");

Vue.prototype.$ajax = ajax;
Vue.prototype.$moment = moment;
Vue.prototype.$imgUrl = imgUrl;

{
  /* < img :src=`${this.$imgUrl+props.row.voucherPath}` style="width:80px;"/> */
}

new Vue({
  el: "#app",
  router: router,
  store: store,
  render: h => h(App),
  mounted() {
    this.$store.commit("updateMenulist");
  }
});
