import Vue from 'vue';
import Router from 'vue-router';

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: '/',
      redirect: '/login'
    },
    {
      path: '/home',
      component: resolve => require(['../components/common/home.vue'], resolve),
      children: [
        {
          path: '/',
          component: resolve => require(['../components/pages/welcome.vue'], resolve)
        },
        {
          path: 'lcsmanage',
          component: resolve => require(['../components/pages/lcsmanage.vue'], resolve)
        },
        {
          path: 'product/trust',
          component: resolve => require(['../components/pages/product/Trust.vue'], resolve)
        },
        {
          path: 'product/debtfund',
          component: resolve => require(['../components/pages/product/DebtFund.vue'], resolve)
        },
        {
          path: 'product/assets',
          component: resolve => require(['../components/pages/product/AssetsManagement.vue'], resolve)
        },
        {
          path: 'ordermanage',
          component: resolve => require(['../components/pages/ordermanage.vue'], resolve)
        }
      ]
    },
    {
      path: '/login',
      component: resolve => require(['../components/pages/login.vue'], resolve)
    }
  ]
})
