import Vue from 'vue';
import App from './App';
import router from './router';
import axios from 'axios';
import qs from 'qs';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';    // 默认主题
// import '../static/css/theme-green/index.css';       // 浅绿色主题
import "babel-polyfill";

Vue.use(ElementUI);

//axios.defaults.baseURL = 'http://47.97.100.240:7074/v1';
axios.defaults.baseURL = 'http://127.0.0.1:7074/v1';
axios.defaults.transformRequest = [function(data) {
  return qs.stringify(data)
}];
Vue.prototype.$axios = axios;

new Vue({
  router,
  render: h => h(App)
}).$mount('#app');
