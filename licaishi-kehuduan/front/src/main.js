import 'babel-polyfill'
import Vue from 'vue'
import App from './App'
import router from './router'
import FastClick from 'FastClick'
import VueLazyload from 'vue-lazyload'
import store from './store'
import moment from "moment";
import {
  Cell,
  Field,
  Spinner,
  Popup,
  Header,
  Button,
  Navbar,
  Tabbar,
  TabItem,
  MessageBox,
  Swipe, SwipeItem,
  Picker,
  TabContainer,
  TabContainerItem,
  DatetimePicker
} from "mint-ui"

import 'common/stylus/index.styl'
import 'common/stylus/cover_mint.styl' // mint-ui覆盖文件


import ajax from "api/ajax"
import axios from 'axios'

/* eslint-disable no-unused-vars */
// import vConsole from 'vconsole'

Vue.component(Cell.name, Cell)
Vue.component(Field.name, Field)
Vue.component(Spinner.name, Spinner)
Vue.component(Popup.name, Popup)
Vue.component(Header.name, Header)
Vue.component(Button.name, Button)
Vue.component(Navbar.name, Navbar)
Vue.component(Picker.name, Picker)
Vue.component(Swipe.name, Swipe);
Vue.component(SwipeItem.name, SwipeItem);
Vue.component(Tabbar.name, Tabbar);
Vue.component(TabItem.name, TabItem);
Vue.component(MessageBox.name, MessageBox)
Vue.component(DatetimePicker.name, DatetimePicker)
Vue.component(TabContainer.name, TabContainer)
Vue.component(TabContainerItem.name, TabContainerItem)

FastClick.attach(document.body)

Vue.use(VueLazyload, {
  // loading: require('common/image/wangzai.jpg')
})
// Vue.use(ajax)
moment.locale("zh-CN");

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})

Vue.prototype.$ajax = ajax
Vue.prototype.$axios = axios
Vue.prototype.$moment = moment