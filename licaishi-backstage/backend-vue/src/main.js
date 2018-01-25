// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from "vue";
import axios from "axios";
import ElementUI from "element-ui";
import "element-ui/lib/theme-chalk/index.css";
import router from "./router";
import "babel-polyfill";
import echarts from "echarts";
import App from "./App";
import commonHandle from "./components/common/common";


Vue.config.productionTip = false;

/* eslint-disable no-new */
Vue.use(ElementUI);
Vue.prototype.$axios = axios;
Vue.prototype.$echarts = echarts;
router.beforeEach((to, from, next) => {
  if (to.path !== "/login") {
    commonHandle.checkLogout("/ota/check_user_log_status", router.app);
  }
  next();
});
new Vue({
    router,
    render: h => h(App)
}).$mount("#app");
