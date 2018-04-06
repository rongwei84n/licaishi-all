/*
 * @Author: 张浩然 
 * @Date: 2018-03-25 20:01:10 
 * @Last Modified by: 张浩然
 * @Last Modified time: 2018-03-25 20:24:36
 *
 *  私募类产品详情
 */

<template>
  <div class="smProDetails">
    <Breadcrumb>
      <BreadcrumbItem to="/main">首页</BreadcrumbItem>
      <BreadcrumbItem to="/main/smPro">私募类产品</BreadcrumbItem>
      <BreadcrumbItem to="/main/smPro/smProDetails">产品详情</BreadcrumbItem>
    </Breadcrumb>
    <!-- 产品详情展示模块 -->
    <div class="smProDetails-body">

    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {};
  },
  methods: {
    // 获取产品详情
    get_proDetails() {
      if (this.$route.query.pDetils) {
        const pDetils = JSON.parse(this.$route.query.pDetils);
        console.log(pDetils);
      }
    }
  },
  created() {
    this.get_proDetails();
  }
};
</script>

<style lang="less">
@import "./smProDetails.less";
</style>

