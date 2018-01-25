<template>
  <div>
    <template v-if="item.childMenus">
      <el-submenu :class="{selectOne: item.parentMenuId === 0, selectTwo: item.parentMenuId !== 0}" :index="item.url">
        <template v-for="(subItem,i) in item.childMenus" v-if="subItem.childMenus">
          <tree-menu :item="subItem"></tree-menu>
        </template>
        <template  slot="title"><i :class="item.icon"></i>{{ item.name }}</template>
        <el-menu-item v-if="!subItem.childMenus" v-for="(subItem,i) in item.childMenus" :key="i" :index="subItem.url">
          <span class="round"></span>
          {{subItem.name }}
        </el-menu-item>
      </el-submenu>
    </template>
    <template v-else>
        <el-menu-item :index="item.url">
          <i :class="item.icon"></i>{{ item.name }}
        </el-menu-item>
    </template>
  </div>
</template>

<script>
  export default {
    name: "treeMenu",
    props: ["item"],
    data () {
      return {
      };
    }
  };
</script>

