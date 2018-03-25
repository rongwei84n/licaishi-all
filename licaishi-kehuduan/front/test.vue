/*
 * @Author: 张浩然 
 * @Date: 2018-03-07 19:23:27 
 * @Last Modified by: 张浩然
 * @Last Modified time: 2018-03-09 00:04:04
 *
 * 基础布局组件
 * 带头部与底部布局
 */

 <template>
  <div id="order">
    <mt-header title="预约">
      <mt-button icon="back" @click="back" slot="left"></mt-button>
    </mt-header>
    <Scroll id="scroll">
      <div class="body">
        <whiteSpace></whiteSpace>
      </div>
    </Scroll>
    <!-- 推荐 -->
    <footer class="footer">
      <span class="submit">提交</span>
    </footer>
  </div>
</template>

 <script type="es6">
import ajax from "api/ajax";
import Scroll from "base/scroll/scroll";
import whiteSpace from "base/whiteSpace/whiteSpace";

export default {
  props: {},
  data() {
    return {};
  },
  mounted() {},
  methods: {
    /**
     * 返回按钮单机事件
     */
    back() {
      this.$router.go(-1);
    }
  },
  components: {
    Scroll,
    whiteSpace
  }
};
</script>

<style lang="stylus">
@import '~common/stylus/variable';

#order {
  position: relative;
  height: 100%;
  width: 100%;
  z-index: 100;
  background-color: $color-background;

  #scroll {
    position: absolute;
    top: 40px;
    bottom: 50px;
    left: 0;
    right: 0;

    .body {
      margin-top: 6px;
      background-color: $color-background-d;
    }
  }

  .footer {
    height: 40px;
    position: absolute;
    bottom: 0;
    width: 100%;
    z-index: -1;
  }
}
</style>

 
 
