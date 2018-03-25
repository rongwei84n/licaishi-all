<template>
  <div id="app">
    <!-- <m-header></m-header> -->
    <Tab></Tab>
    <router-view></router-view>
  </div>
</template>

<script type="text/ecmascript-6">
require("./common/js/phihome-1.0.0.js?n=1");
import MHeader from "components/m-header/m-header";
import Tab from "components/tab/tab";
export default {
  // data() {
  //   screenHeight: document.body.clientHeight; // 这里是给到了一个默认值 （这个很重要），
  //   originHeight: document.body.clientHeight; //默认高度在watch里拿来做比较
  // },
  created () {
    // if (condition) {
      
    // }
  },
  mounted() {
    // this.$router.replace("/recommend");
    // window.onresize = () => {
    //   return (() => {
    //     window.screenHeight = document.body.screenHeight;
    //     this.screenHeight = window.screenHeight;
    //   })();
    // };
  },
  // watch: {
  //   screenHeight(val) {
  //     this.screenHeight = val;
  //     if (this.originHeight != val) {
  //       this.isOriginHei = false;
  //     } else {
  //       this.isOriginHei = true;
  //     }
  //   }
  // },
  components: {
    MHeader,
    Tab
  }
};
</script>

<style lang="stylus" rel="stylesheet/stylus">
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  position: relative;
  height: 100%;
  width: 100%;
  overflow: hidden;
}
</style>
