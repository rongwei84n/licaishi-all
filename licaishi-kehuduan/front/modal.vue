/*
 * @Author: 张浩然 
 * @Date: 2018-03-04 22:28:31 
 * @Last Modified by: 张浩然
 * @Last Modified time: 2018-03-17 13:19:09
 * 
 * 此demo基础布局
 */

<template>
    <div class="main">
        <scroll class="scroll-content">
        </scroll>
    </div>
</template>

<script type="es6">
export default {};
</script>

<style lang="stylus" scoped>
.main {
    position: relative;
    width: 100%;
    height: 100vh;

    .scroll-content {
        position: absolute;
        top: 60px;
        bottom: 46px;
        background: #fff;
        overflow: hidden;
        width: 100%;
    }
}
</style>


