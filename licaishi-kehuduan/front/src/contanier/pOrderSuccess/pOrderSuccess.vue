/*
 * @Author: 张浩然 
 * @Date: 2018-03-07 19:23:27 
 * @Last Modified by: 张浩然
 * @Last Modified time: 2018-03-18 11:03:09
 *
 * 产品预约成功
 */

 <template>
  <div id="pOrderSuccess">
    <mt-header title="产品预约成功">
      <mt-button icon="back" @click="back" slot="left"></mt-button>
    </mt-header>
    <Scroll id="scroll">
      <div class="body">
        <img src="../../common/image/666_09.png" alt="">
        <p>恭喜您！预约成功，请等待审核</p>
        <div class="btn-content">
          <span class="checkOrder" @click="checkOrder">查看订单</span>
          <span class="goOrder" @click="goOrder">继续预约</span>
        </div>
        <whiteSpace></whiteSpace>
      </div>
    </Scroll>
  </div>
</template>

 <script type="es6">
import Scroll from "base/scroll/scroll";
import whiteSpace from "base/whiteSpace/whiteSpace";

export default {
  methods: {
    // 跳转到我的订单
    checkOrder() {
      this.$router.replace({ name: "Orderlist", params: { tab_id: "1" } });
    },

    // 跳转到产品列表
    goOrder() {
      this.$router.replace({ name: "product", params: { activeIndex: 0 } });
      // 此处跳转到产品列表不能添加历史记录
    },
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

#pOrderSuccess {
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
      width: 100%;
      display: flex;
      flex-direction: column;
      align-items: center;

      >img {
        width: 64px;
        height: 64px;
        margin: 50px 0 20px 0;
      }

      >p {
        font-size: $font-size-medium-x;
        color: $color-highlight-background;
      }

      .btn-content {
        display: flex;
        justify-content: space-around;
        height: 34px;
        width: 80%;
        margin-top: 40px;

        >span {
          width: 90px;
          height: 34px;
          display: inline-block;
          line-height: 34px;
          text-align: center;
          color: $color-background;
        }

        .checkOrder {
          background-color: #FE8921;
        }

        .goOrder {
          background-color: #E10101;
        }
      }
    }
  }
}
</style>

 
 
