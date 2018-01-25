<template>
    <div class="sidebar">
       <el-menu :default-active="onRoutes"
                 :default-openeds="opens"
                 class="el-menu-vertical-demo"
                 unique-opened
                 router
                 background-color="#324157"
                 text-color="#bfcbd9"
                 active-text-color="#20a0ff">
            <template v-for="item in items">
              <my-tree :item="item"></my-tree>
            </template>
        </el-menu>
    </div>
</template>

<script>
    import myTree from "./menu.vue";
    import commonHandle from "./common";

    export default {
        components: {
          myTree
        },
        data () {
            return {
              selectMenus: [],
              items: []
            };
          },
        created () {
          console.log(this.onRoutes);
          let fn = function (response, self) {
              self.items = response.data.result.sysMenus;
          };
          commonHandle.requestPost("/ota/sys/query_user_menus", this, {}, fn);
        },
        computed: {
            onRoutes () {
                return this.$route.path;
            },
            opens () {
              // 菜单自动展开
              let data = this.items;
              let url = this.$route.path;
              this.selectMenus = [];
              this.handle(data, url);
              return this.selectMenus;
            }
        },
        methods: {
          handle (data, url) {
            // 寻找当前选中的url
            for (let i in data) {
              if (data[i].url === url) {
                this.selectMenus.unshift(data[i].url);
                this.handleId(this.items, data[i].parentMenuId);
                break;
              } else if (data[i].childMenus) {
                this.handle(data[i].childMenus, url);
              }
            }
          },
          handleId (data, id) {
            // 寻找当前选中的url的所有父节点
            for (let i in data) {
              if (data[i].id === id) {
                this.selectMenus.unshift(data[i].url);
                if (data[i].parentMenuId === 0) {
                  break;
                } else {
                  this.handleId(this.items, data[i].parentMenuId);
                }
              } else if (data[i].childMenus) {
                this.handleId(data[i].childMenus, id);
              }
            }
          }
        }
    };
</script>

<style lang="less">
    .sidebar{
        display: block;
        position: absolute;
        width: 250px;
        left: 0;
        top: 70px;
        bottom:0;
        background: #f1f1f1;
        i {
          color: #bfcbd9;
        }
        .el-submenu.is-active i {
          color: #4d4d4d;
        }
        .el-menu{
          border-right: 0;
        }
        .el-submenu .el-submenu__title{
          background: #52657b !important
        }
        .el-submenu.is-active .el-submenu__title{
          color: #4d4d4d !important;
          background: #c3c8ce !important;
        }
        .selectOne.is-active .el-submenu__title{
          color: #bfcbd9 !important;
          background: #384452 !important;
        }
        .selectOne .el-submenu__title:hover{
          background: #209bf6 !important;
        }
        .selectOne .el-submenu__title:active{
          background: #2a8cef !important;
        }
        .selectOne i{
          color: #bfcbd9 !important;
        }
        .selectOne .el-submenu__icon-arrow, .selectOne .el-icon-arrow-down{
          display: none;
        }
        .selectTwo .el-submenu__icon-arrow, .selectTwo .el-icon-arrow-down{
          display: block;
        }
        .selectTwo.el-submenu .el-submenu__title{
          color: #4d4d4d !important;
          background: #c3c8ce !important;
        }
        .selectTwo.is-active .el-submenu__title{
          color: #4d4d4d !important;
          background: #c3c8ce !important;
        }
        .selectTwo .el-submenu__title:hover{
          background: #a3a8af !important;
        }
        .selectTwo .el-submenu__title:active{
          background: #8a8f96 !important;
        }
        .selectTwo i{
          color: #4d4d4d !important;
        }
        .el-submenu .el-menu-item{
          color: #4d4d4d !important;
          background: #f1f1f1 !important;
          .round{
            display: inline-block;
            border-radius: 50%;
            width: 6px;
            height: 6px;
            margin-right: 2px;
            background: #878787;
          }
        }
        .el-submenu .el-menu-item:hover{
          background: #e2e2e2 !important;
        }
        .el-submenu .el-menu-item:active{
          background: #d6d6d6 !important;
        }
        .el-submenu .el-menu-item.is-active{
          color: #4d4d4d !important;
          background: #fff !important;
          border-left: 5px solid #409eff;
          .round{
            background: #209bf6;
          }
        }
        .el-submenu [class^=icon-phihome-]{
          vertical-align: middle;
          margin-right: 5px;
          width: 24px;
          text-align: center;
          font-size: 14px;
          display: inline-block;
        }
    }
    .sidebar > ul {
        max-height:100%;
        overflow: auto;
    }
</style>
