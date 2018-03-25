/*
 * @Author: 张浩然 
 * @Date: 2018-03-06 22:49:44 
 * @Last Modified by: 张浩然
 * @Last Modified time: 2018-03-06 22:50:10
 *
 * loading组件--用与上拉加载的底部状态
 */

<template>
  <div class="loading">
    <mt-spinner :type="3" color="#ec1323"></mt-spinner>
  </div>
</template>
<script type="text/ecmascript-6">
export default {};
</script>
<style scoped lang="stylus" rel="stylesheet/stylus">
@import '~common/stylus/variable';

.loading {
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
