// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import VueRouter from 'vue-router'
import VueResource from 'vue-resource'
import Layout from './components/layout'

import LoginPage from './pages/login'
import IndexPage from './pages/index'
import FinancerM from './pages/financermange'
import OrderM from './pages/ordermange'

Vue.use(VueRouter)
Vue.use(VueResource)

let router = new VueRouter({
  mode: 'history',
  routes: [
    {
      path: '/',
      component: LoginPage
    },
    {
      path: '/login',
      component: LoginPage
    },
    {
      path: '/index',
      component: IndexPage,
      children: [
        {
          path: 'financermange',
          component:FinancerM
        },
        {
          path: 'ordermanage',
          component: OrderM
        }
      ]
    }
  ]
})

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { Layout },
  template: '<Layout/>'
})
