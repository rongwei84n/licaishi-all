<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: "app"
};
</script>

<style lang="less">
	  @import "./assets/css/main.css";
    @import "./assets/css/color-dark.css";     /*深色主题*/
    @import "./assets/fonts/style.css";  // 菜单栏字体图标
    .el-select{
      .el-input__inner:hover{
        border-color: #409EFE !important;
      }
    }
    .el-select-dropdown__item.hover, .el-select-dropdown__item:hover{
      background-color: #409EFE !important;
      color: #fff !important;
    }
    .el-select-dropdown__item.selected:hover{
      color: #fff !important;
    }
</style>
