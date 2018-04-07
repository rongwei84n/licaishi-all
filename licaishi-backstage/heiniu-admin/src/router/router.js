import Main from "@/views/Main.vue";

// 不作为Main组件的子页面展示的页面单独写，如下
export const loginRouter = {
  path: "/login",
  name: "login",
  meta: {
    title: "Login - 登录"
  },
  component: resolve => {
    require(["@/views/container/login/login.vue"], resolve);
  }
};

export const page404 = {
  path: "/*",
  name: "error-404",
  meta: {
    title: "404-页面不存在"
  },
  component: resolve => {
    require(["@/views/container/error-page/404.vue"], resolve);
  }
};

export const page403 = {
  path: "/403",
  meta: {
    title: "403-权限不足"
  },
  name: "error-403",
  component: resolve => {
    require(["@//views/container/error-page/403.vue"], resolve);
  }
};

export const page500 = {
  path: "/500",
  meta: {
    title: "500-服务端错误"
  },
  name: "error-500",
  component: resolve => {
    require(["@/views/container/error-page/500.vue"], resolve);
  }
};

/**
 * 作为Main组件的子页面展示但是不在左侧菜单显示的路由写在otherRouter里
 */
export const otherRouter = {
  path: "/",
  name: "otherRouter", // 必填，在面包屑处理中需要用到，值固定为otherRouter（或者可以在./src/libs/util.js中修改）
  redirect: "/home", // 选填，这里如果不填在浏览器地址栏输入域名后自动跳转到首页
  component: Main, // 必填，主组件，用于加载侧边栏和右侧面包屑、标签栏、工具条、子页面路由视图等
  children: [
    {
      // 在Main右侧视图显示的页面都要作为otherRouter的children来添加
      path: "home",
      title: {
        i18n: "home"
      },
      name: "home_index",
      component: resolve => {
        require(["@/views/container/home/home.vue"], resolve);
      }
    },
    {
      // 非私募类产品详情
      path: "/main/nosmPro/nosmProDetails",
      name: "nosmProDetails",
      component: resolve => {
        require([
          "@/views/container/product/nosmProDetails/nosmProDetails.vue"
        ], resolve);
      }
    },
    {
      // 私募类产品详情
      path: "/main/nosmPro/smProDetails",
      name: "smProDetails",
      component: resolve => {
        require([
          "@/views/container/product/smProDetails/smProDetails.vue"
        ], resolve);
      }
    },
    {
      // 订单详情
      path: "/main/order/orderDetail",
      name: "orderDetail",
      component: resolve => {
        require([
          "@/views/container/order/detail/orderDetail.vue"
        ], resolve);
      }
    }
  ]
};

/**
 * 作为Main组件的子页面展示并且在左侧菜单显示的路由写在appRouter里
 */
export const appRouter = [
  /*  第一种情况：只有一级菜单 */
  {
    path: "/main", // 必填
    icon: "person", // 必填，此icon将显示在左侧菜单栏
    title: "理财师管理", // 必填，此title值将显示在左侧菜单栏
    component: Main, // 必填，且固定，用于加载Main组件
    children: [
      {
        path: "lcsmanage",
        name: "lcsmanage",
        component: resolve => {
          require(["@/views/container/lcsmanage/lcsmanage.vue"], resolve);
        }
      }
    ]
  },
  {
    path: "/main", // 必填
    icon: "person-stalker", // 必填，此icon将显示在左侧菜单栏
    title: "客户管理", // 必填，此title值将显示在左侧菜单栏
    component: Main, // 必填，且固定，用于加载Main组件
    children: [
      {
        path: "khmanage",
        name: "khmanage",
        component: resolve => {
          require(["@/views/container/khmanage/khmanage.vue"], resolve);
        }
      }
    ]
  },
  /* 第二种情况：有二级菜单 */
  {
    path: "/main",
    icon: "ios-folder",
    name: "group",
    title: "产品管理",
    component: Main,
    children: [
      /* 若要展示二级菜单，title字段必给 */
      {
        path: "nosmPro",
        icon: "ios-calendar",
        name: "nosmPro",
        title: "非私募类产品", // 必填，将显示在左侧菜单栏二级菜单
        component: resolve => {
          require(["@/views/container/product/nosmPro/nosmPro.vue"], resolve);
        }
      }
      // ,
      // {
      //   path: "smPro",
      //   icon: "ios-calendar-outline",
      //   name: "smPro",
      //   title: "私募类产品",
      //   component: resolve => {
      //     require(["@/views/container/product/smPro/smPro.vue"], resolve);
      //   }
      // }
    ]
  },
  {
    path: "/main",
    icon: "ios-browsers-outline",
    name: "order",
    title: "订单管理",
    component: Main,
    children: [
      /* 若要展示二级菜单，title字段必给 */
      {
        path: "unpay",
        icon: "clock",
        name: "unpay",
        title: "待打款", // 必填，将显示在左侧菜单栏二级菜单
        component: resolve => {
          require(["@/views/container/order/unpay/unpay.vue"], resolve);
        }
      },
      {
        path: "unsettle",
        icon: "compass",
        name: "unsettle",
        title: "待结佣",
        component: resolve => {
          require(["@/views/container/order/unsettle/unsettle.vue"], resolve);
        }
      },
      {
        path: "settled",
        icon: "help-buoy",
        name: "settled",
        title: "已结佣",
        component: resolve => {
          require(["@/views/container/order/settled/settled.vue"], resolve);
        }
      },
      {
        path: "failure",
        icon: "bug",
        name: "failure",
        title: "已失败",
        component: resolve => {
          require(["@/views/container/order/failure/failure.vue"], resolve);
        }
      }
    ]
  },
  {
    path: "/main", // 必填
    icon: "edit", // 必填，此icon将显示在左侧菜单栏
    title: "富文本编辑", // 必填，此title值将显示在左侧菜单栏
    component: Main, // 必填，且固定，用于加载Main组件
    children: [
      {
        path: "Udit",
        name: "Udit",
        title: "Udit",
        component: resolve => {
          require(["@/views/container/Udit/Udit.vue"], resolve);
        }
      }
    ]
  }
];

// 所有上面定义的路由都要写在下面的routers里
export const routers = [
  loginRouter,
  otherRouter,
  ...appRouter,
  page500,
  page403,
  page404
];
