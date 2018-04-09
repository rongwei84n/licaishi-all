<template>
    <div class="moduleTitle">
        <div class="titleContent">
            <img :src="iconUrl" alt="">
            <span>{{title}}</span>
        </div>
        <div class="moreContent" v-if="right">
            <span @click.self="_moreClick()">更多
                <img src="../../common/image/more.png" alt="">
            </span>
        </div>
    </div>
</template>

<script type="es6">
export default {
  props: {
    // 图标地址
    iconUrl: "",
    // title
    title: "",
    right: {
      type: Boolean,
      defalut: true
    }
  },
  methods: {
    /**
     * “更多”按钮单机事件
     * @event
     */
    _moreClick() {
      this.$emit("moreClick");
    }
  }
};
</script>

<style scoped lang="stylus" rel="stylesheet/stylus">
@import '~common/stylus/variable';

.moduleTitle {
    width: 100%;
    height: 40px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    border-bottom: 1px solid #dce0e1;
    box-sizing: border-box;
    padding: 0 6px 0 10px;

    .titleContent {
        display: flex;
        align-items: center;

        img {
            width: 20px;
            height: 20px;
            margin-right: 6px;
        }

        >span {
            font-size: 16px;
        }
    }

    .moreContent {
        >span {
            font-size: 10px;
            color: #686868;
            display: flex;
            align-items: center;

            >img {
                width: 14px;
                height: 12px;
            }
        }
    }
}
</style>
