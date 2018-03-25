/*
 * @Author: 张浩然 
 * @Date: 2018-03-05 19:34:23 
 * @Last Modified by: 张浩然
 * @Last Modified time: 2018-03-07 22:55:00
 * 条件查询tabs
 */

<template>
  <div class="conditionTab-content" @click="onClick">
    <div class="tabs-content">
      <span v-for="(item,index) in tabList" :key="index" :data-index="index" :class="{active:activeIndex===index}">{{item}}</span>
    </div>
  </div>
</template>
<script type="es6">
export default {
  props: {
    tabList: {
      type: Array,
      default: function() {
        return ["综合", "佣金高到低", "收益高到低", "筛选"];
      }
    }
  },
  data() {
    return {
      /*  选择项的索引 */
      activeIndex: 0
    };
  },
  methods: {
    onClick(e) {
      const curIndex = parseInt(e.target.getAttribute("data-index"));
      // 此组件最后一项不需要选中样式
      if (this.tabList.length - 1 !== curIndex) {
        this.activeIndex = curIndex;
      }
      this.$emit("onClick", curIndex);
    }
  }
};
</script>

<style lang="stylus" scoped>
@import '~common/stylus/mixin.styl';

.conditionTab-content {
  height: 34px;
  width: 100vw;
  background-color: #eff3f6;

  .tabs-content {
    height: 26px;
    display: flex;
    justify-content: space-around;
    align-items: center;
    background-color: #FFFFFF;

    >span {
      flex: 1;
      font-size: 14px;
      line-height: 14px;
      color: #212121;
      display: inline-block;
      height: 14px;
      text-align: center;
      extend-click();
    }

    >span:not(:last-child) {
      border-right: 1px solid #212121;
    }

    .active {
      color: #E10101;
    }
  }
}
</style>


