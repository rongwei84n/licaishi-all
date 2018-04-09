/*
 * @Author: 张浩然 
 * @Date: 2018-03-25 20:01:10 
 * @Last Modified by: 张浩然
 * @Last Modified time: 2018-03-25 22:23:56
 *
 *  非私募类产品详情
 */

<template>
  <div class="nosmProDetails">
    <Breadcrumb>
      <BreadcrumbItem to="/main">首页</BreadcrumbItem>
      <BreadcrumbItem to="/main/nosmPro">非私募类产品</BreadcrumbItem>
      <BreadcrumbItem to="/main/nosmPro/nosmProDetails">产品详情</BreadcrumbItem>
    </Breadcrumb>
    <!-- 产品详情展示模块 -->
    <div class="nosmProDetails-body">
      <div class="nosmProDetails-body-content">
        <h1>产品信息</h1>
        <div class="nosmProDetails-body-content-pItem">
          <span>产品编号:</span>
          {{pDetils.pCode}}
        </div>
        <div class="nosmProDetails-body-content-pItem">
          <span>产品名称:</span>
          {{pDetils.pShortName}}
        </div>
        <!-- 后面通过if判断当前这个模块是否显示就行 -->
        <div class="nosmProDetails-body-content-pItem" v-if="pDetils.pSaleStatus">
          <span>产品状态:</span>
          {{pSaleStatusList[parseInt(pDetils.pSaleStatus)-1].value}}
        </div>
        <div class="nosmProDetails-body-content-pItem" v-if="pDetils.pType">
          <span>产品类型:</span>
          {{pTypeList[parseInt(pDetils.pType)].value}}
        </div>
        <div class="nosmProDetails-body-content-pItem">
          <span>产品全称:</span>
          {{pDetils.pFullName}}
        </div>
        <div class="nosmProDetails-body-content-pItem">
          <span>募集规模:</span>
          {{pDetils.pAllIssuingScale}}元
        </div>
        <div class="nosmProDetails-body-content-pItem" v-if="pDetils.pInvestType">
          <span>投资领域:</span>
          {{pInvestTypeList[parseInt(pDetils.pInvestType)].value}}
        </div>
        <div class="nosmProDetails-body-content-pItem" v-if="pDetils.pPaymentInterestType">
          <span>付息方式:</span>
          {{pPaymentInterestTypeList[parseInt(pDetils.pPaymentInterestType)-1].value}}
        </div>
        <div class="nosmProDetails-body-content-pItem" v-if="pDetils.pSizeRatioType">
          <span>大小配比:</span>
          {{pSizeRatioType[parseInt(pDetils.pSizeRatioType)-1].value}}
        </div>
        <div class="nosmProDetails-body-content-pItem">
          <span>发行机构:</span>
          {{pDetils.pInvestOwnerId}}
        </div>
        <div class="nosmProDetails-body-content-pItem">
          <span>管理机构:</span>
          {{pDetils.pTgjg}}
        </div>
        <div class="nosmProDetails-body-content-pItem">
          <span>投资期限:</span>
          {{pDetils.pDulTime}}个月
        </div>
        <div class="nosmProDetails-body-content-pItem">
          <span>成立日期:</span>
          {{pDetils.pSaleStartTime}}
        </div>
        <div class="nosmProDetails-body-content-pItem">
          <span>Summary:</span>
          <p v-html="pDetils.pRecruitmentSummary"></p>
        </div>
        <div class="nosmProDetails-body-content-pItem">
          <span>产品优势:</span>
          <p v-html="pDetils.pCpys"></p>
        </div>
        <div class="nosmProDetails-body-content-pItem">
          <span>募集账号:</span>
          <p v-html="pDetils.pMjzh"></p>
        </div>
        <div class="nosmProDetails-body-content-pItem">
          <span>风险控制:</span>
          <p v-html="pDetils.pFxkz"></p>
        </div>
        <div class="nosmProDetails-body-content-pItem">
          <span>还款来源:</span>
          <p v-html="pDetils.pHkly"></p>
        </div>
        <div class="nosmProDetails-body-content-pItem">
          <span>资金用途:</span>
          <p v-html="pDetils.pZjyt"></p>
        </div>
        <div class="nosmProDetails-body-content-pItem">
          <span>融资方:</span>
          <p v-html="pDetils.pRzf"></p>
        </div>
        <div class="nosmProDetails-body-content-pItem">
          <span>担保方:</span>
          <p v-html="pDetils.pDbf"></p>
        </div>
        <div class="nosmProDetails-body-content-pItem">
          <span>预览资料:</span>
          <a v-for="(item,index) of pDetils.productAttachments" :key='index' :href="item.paFilePath">{{item.paFileName}}</a>
          <!-- <a href="https://mozilla.github.io/pdf.js/web/compressed.tracemonkey-pldi-09.pdf">测试pdf</a> -->
        </div>
        <div class="nosmProDetails-body-content-pItem">
          <span>备注:</span>
          <p v-html="pDetils.pRemark"></p>
        </div>
        <div class="nosmProDetails-body-content-pItem">
          <span>认购须知:</span>
          <p v-html="pDetils.pRgxz"></p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {
  pTypeList,
  pInvestTypeList,
  pSaleStatusList,
  pPaymentInterestTypeList,
  pSizeRatioType
} from "../enumeration";
import Util from "@/libs/util";
export default {
  data() {
    return {
      pInvestTypeList,
      pSaleStatusList,
      pPaymentInterestTypeList,
      pSizeRatioType,
      pTypeList,
      pDetils: "" //产品对象
    };
  },
  methods: {
    // 获取产品详情
    get_proDetails() {
      if (this.$route.query.pDetils) {
        this.pDetils = JSON.parse(this.$route.query.pDetils);
        this.pDetils.pAllIssuingScale = Util.toThousands(
          this.pDetils.pAllIssuingScale
        );
      }
    }
  },
  created() {
    this.get_proDetails();
  }
};
</script>

<style lang="less">
@import "./nosmProDetails.less";
</style>

