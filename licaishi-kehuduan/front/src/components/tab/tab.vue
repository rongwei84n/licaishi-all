<template>
  <div class="tab">
    <router-link tag="div" class="tab-item" to="/recommend" replace>
      <img @click.self="linkActive" title="0" src="~@/common/image/homeActive.png" alt="homeActive.png"><br/>
      <span class="tab-link">首页</span>
    </router-link>
    <router-link tag="div" class="tab-item" to="/product?activeIndex=0" replace>
      <img @click.self="linkActive" title="1" src="~@/common/image/product.png" alt="productActive.png"><br/>
      <span class="tab-link">产品</span>
    </router-link>
    <router-link tag="div" class="tab-item" to="/rank" replace>
      <img @click.self="linkActive" title="2" src="~@/common/image/workRoom.png" alt="workRoomActive.png"><br/>
      <span class="tab-link">工作室</span>
    </router-link>
    <router-link tag="div" class="tab-item" to="/search" replace>
      <img @click.self="linkActive" title="3" src="~@/common/image/userCenter.png" alt="userCenterActive.png"><br/>
      <span class="tab-link">我的</span>
    </router-link>
    <!-- <mt-tabbar v-model="selected">
      <mt-tab-item id="recommend">
        <router-link tag="div" to="/recommend" replace>
          <img slot="icon" src="~@/common/image/home.png"> 首页
        </router-link>
      </mt-tab-item>
      <mt-tab-item id="product">
        <router-link tag="div" to="/product" replace>
          <img slot="icon" src="~@/common/image/product.png"> 产品
        </router-link>
      </mt-tab-item>
      <mt-tab-item id="rank">
        <router-link tag="div" to="/rank" replace>
          <img slot="icon" src="~@/common/image/workRoom.png"> 工作室
        </router-link>
      </mt-tab-item>
      <mt-tab-item id="search">
        <img slot="icon" src="~@/common/image/userCenter.png"> 我的
      </mt-tab-item>
    </mt-tabbar> -->
  </div>
</template>

<script type="text/ecmascript-6">
import home from "common/image/home.png";
import product from "common/image/product.png";
import workRoom from "common/image/workRoom.png";
import userCenter from "common/image/userCenter.png";
import homeActive from "common/image/homeActive.png";
import productActive from "common/image/productAcitve.png";
import workRoomActive from "common/image/workRoomActive.png";
import userCenterActive from "common/image/userCenterActive.png";
export default {
  data() {
    return {
      tabIcons: [home, product, workRoom, userCenter],
      tabIconsA: [homeActive, productActive, workRoomActive, userCenterActive],
      recommends: [],
      discList: [],
      selected: "recommend"
    };
  },
  methods: {
    /**
     * tab路由点击事件
     * @param 事件对象s
     */
    linkActive(event) {
      let target = event.target;
      let tabImgs = document.querySelectorAll(".tab img");
      let liTags = document.querySelectorAll(".tab .tab-item");
      let index = target.getAttribute("title");
      for (let i = 0; i < liTags.length; i++) {
        liTags[i].className = "tab-item";
      }
      liTags[index].className = "tab-item active";
      for (let i = 0; i < tabImgs.length; i++) {
        tabImgs[i].src = this.tabIcons[i];
      }
      target.src = this.tabIconsA[index];
    }
  },
  watch: {
    // selected() {
    //   this.$router.push(`/${this.selected}`);
    //   console.log(this.$route.path);
    // }
  }
};
</script>

<style scoped lang="stylus" rel="stylesheet/stylus">
@import '~common/stylus/variable';

.tab {
  position: fixed;
  bottom: 0;
  z-index: 99;
  width: 100%;
  background: #fff;
  color: #303030;
  display: flex;
  align-items: center;
  height: 46px;
  /* line-height: 44px */
  font-size: $font-size-medium;

  .tab-item {
    flex: 1;
    text-align: center;

    img {
      height: 20px;
      width: 20px;
    }

    .tab-link {
      padding-bottom: 5px;
    }

    &.router-link-active {
      .tab-link {
        color: red;
      }
    }
  }
}
</style>
