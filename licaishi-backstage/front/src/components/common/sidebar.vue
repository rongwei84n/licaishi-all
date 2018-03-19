<template>
  <div class="sidebar">
    <el-menu class="el-menu-vertical-demo" theme="dark" unique-opened router>
      <template v-for="item in items">
        <template v-if="item.subs">
          <el-submenu :index="item.index">
            <template slot="title">
              <i :class="item.icon"></i>{{ item.title }}</template>
            <el-menu-item v-for="(subItem,i) in item.subs" :key="i" :index="subItem.index">{{ subItem.title }}
            </el-menu-item>
          </el-submenu>
        </template>
        <template v-else>
          <el-menu-item :index="item.index">
            <i :class="item.icon"></i>{{ item.title }}
          </el-menu-item>
        </template>
      </template>
    </el-menu>
  </div>
</template>

<script>
export default {
  data() {
    return {
      items: [
        {
          icon: "el-icon-service",
          index: "/home/lcsmanage",
          title: "理财师管理"
        },
        {
          icon: "el-icon-location-outline",
          index: "/home/khmanage",
          title: "客户管理"
        },
        {
          icon: "el-icon-goods",
          index: "5",
          title: "产品管理",
          subs: [
            {
              index: "/home/product/trust",
              title: "集合信托"
            },
            {
              index: "/home/product/assets",
              title: "集合资管"
            },
            {
              index: "/home/product/debtfund",
              title: "债权基金"
            },
            {
              index: "/home/product/addProduct",
              title: "新增产品"
            }
          ]
        },
        {
          icon: "el-icon-news",
          index: "3",
          title: "订单管理",
          subs: [
            {
              index: "/home/order/unpay",
              title: "待打款"
            },
            {
              index: "/home/order/unsettle",
              title: "待结佣"
            },
            {
              index: "/home/order/settled",
              title: "已结佣"
            },
            {
              index: "/home/order/failure",
              title: "已失败"
            }
          ]
        },
        {
          icon: "el-icon-location-outline",
          index: "/home/Udit",
          title: "可视化编辑"
        }
      ]
    };
  }
};
</script>

<style scoped>
.sidebar {
  display: block;
  position: absolute;
  width: 250px;
  left: 0;
  top: 70px;
  bottom: 0;
  background: #2e363f;
}
.sidebar > ul {
  height: 100%;
}
</style>
