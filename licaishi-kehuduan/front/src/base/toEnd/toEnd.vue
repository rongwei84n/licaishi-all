/*
 * @Author: 张浩然
 * @Date: 2018-03-06 22:47:10
 * @Last Modified by: 张浩然
 * @Last Modified time: 2018-03-06 22:53:11
 * 底部状态组件--用于翻页到最底部使用
 */

 <template>
    <div class="end-content">
        <hr color="#EDEEEF" />
        <span>{{msg}}</span>
        <hr color="#EDEEEF" />
    </div>
</template>

<script>
export default {
  props: {
    msg: {
      type: String,
      default: "没有更多了"
    }
  }
};
</script>

<style lang="stylus">
.end-content {
    width: 100vw;
    height: 30px;
    display: flex;
    justify-content: space-between;
    align-items: center;

    >hr {
        flex: 1;
    }

    >span {
        font-size: 12px;
        margin: 0 6px;
        color: #666;
    }
}
</style>





