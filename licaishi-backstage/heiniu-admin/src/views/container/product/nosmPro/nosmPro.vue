/*
 * @Author: 张浩然 
 * @Date: 2018-03-24 15:47:29 
 * @Last Modified by: zhanghr
 * @Last Modified time: 2018-03-29 15:06:32
 *
 *  请求接口后在table配置文件内改掉对应的key即可
 *
 *  模态框内的表单属性需要你来根据字段修改
 */

<style lang="less">
@import "./nosmPro.less";
</style>

<template>
  <div class="nosmPro">
    <template>
      <Breadcrumb>
        <BreadcrumbItem to="/main">首页</BreadcrumbItem>
        <BreadcrumbItem to="/nosmPro">产品管理</BreadcrumbItem>
      </Breadcrumb>
    </template>
    <div class="nosmPro-body">
      <div class="nosmPro-body-header">
        <div class="nosmPro-body-header-ptype">
          <span>产品类型：</span>
          <div>
            <Select transfer v-model="currPType" placeholder="请选择产品类型">
              <Option v-for="(item,index) of pTypeList" :key="index" :value="item.key">{{item.value}}</Option>
            </Select>
          </div>
        </div>
        <div class="nosmPro-body-header-proName">
          <!-- <span>产品名称：</span>
          <Input v-model="proNameSearch" placeholder="请输入搜索产品简称" style="width: 200px;marginRight:20px;"></Input> -->
          <Button type="primary" icon="ios-search" @click="handleSearch">检索</Button>
        </div>
        <div>
          <Button type="primary" icon="plus" @click="handleAdd">新增</Button>
        </div>
      </div>
      <div class="nosmPro-body-body">
        <edit-table refs="table2" v-model="dataList" @on-delete="handleDelete" @on-change="handleChange" @detailsBtn-click="handleDetails" :columns-list="editInlineColumns"></edit-table>
      </div>
    </div>
    <!-- 理财师模态框 -->
    <Modal v-model="proModalVisible" :title="titleModal" @on-ok="handleSubmit('proModalValidate')" :loading="loading">
      <Form ref="proModalValidate" :model="proModalValidate" :rule="ruleValidate" :label-width="100">
        <FormItem label="产品类型" prop="pType">
          <Select transfer v-model="proModalValidate.pType" placeholder="请选择产品类型">
            <Option v-for="(item,index) of pTypeList" v-if="index!==0" :key="index" :value="item.key">{{item.value}}</Option>
          </Select>
        </FormItem>
        <FormItem label="产品简称" prop="pShortName">
          <Input v-model="proModalValidate.pShortName" placeholder="请输入产品简称"></Input>
        </FormItem>
        <FormItem label="产品销售状态" prop="pSaleStatus" :label-width="100">
          <Select transfer v-model="proModalValidate.pSaleStatus" placeholder="请选择产品销售状态">
            <Option v-for="(item,index) of pSaleStatusList" :key="index" :value="item.key">{{item.value}}</Option>
          </Select>
        </FormItem>
        <FormItem label="投资领域" prop="pInvestType">
          <Select transfer v-model="proModalValidate.pInvestType" placeholder="请选择投资领域">
            <Option v-for="(item,index) of pInvestTypeList" :key="index" :value="item.key">{{item.value}}</Option>
          </Select>
        </FormItem>
        <FormItem label="付息方式" prop="pPaymentInterestType">
          <Select transfer v-model="proModalValidate.pPaymentInterestType" placeholder="请选择付息方式">
            <Option v-for="(item,index) of pPaymentInterestTypeList" :key="index" :value="item.key">{{item.value}}</Option>
          </Select>
        </FormItem>
        <FormItem label="大小配比" prop="pSizeRatioType">
          <Select transfer v-model="proModalValidate.pSizeRatioType" placeholder="请选择大小配比">
            <Option v-for="(item,index) of pSizeRatioTypeList" :key="index" :value="item.key">{{item.value}}</Option>
          </Select>
        </FormItem>
        <FormItem label="投资区间" position="top">
          <div class="investConf-content">
            <!-- <p style="color:red">投资金额区间必须从100万开始且必须连贯</p> -->
            <table cellspacing="10px">
              <thead>
                <tr style="fontSizt:14px;">
                  <th>投资金额(万)</th>
                  <th style="width: 94px;">预期年化收益(%)</th>
                  <th>佣金比例(%)</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(item,index) of profitRebates" :key="index">
                  <td class="money-content">
                    <Input type="text" v-model="item.prStartAmount" @on-change="lastChange(index)" :disabled="index===0&&profitRebates.length!==1" />
                    <span>-</span>
                    <Input type="text" v-model="item.prEndAmount" :disabled="(index+1) !== profitRebates.length&&profitRebates.length!==1" />
                  </td>
                  <td>
                    <Input v-model="item.prExpectAnnualRevenue" type="text" />
                  </td>
                  <td>
                    <Input type="text" v-model="item.prCommission" />
                  </td>
                  <td>
                    <Button type="dashed" icon="minus-round" @click.native="delete_profitRebates(index)">删除</Button>
                  </td>
                </tr>
              </tbody>
            </table>
            <div class="dialog-footer">
              <Button type="ghost" icon="plus-round" @click.native="add_profitRebates">增加</Button>
            </div>
          </div>
        </FormItem>
        <!-- 产品基础信息模块 -->
        <FormItem label="产品全称" prop="pFullName">
          <Input v-model="proModalValidate.pFullName" placeholder="请输入产品全称"></Input>
        </FormItem>
        <FormItem label="募集规模" prop="pAllIssuingScale">
          <Input v-model="proModalValidate.pAllIssuingScale" placeholder="请输入募集规模"></Input>
        </FormItem>

        <FormItem label="发行机构" prop="pInvestOwnerId">
          <Input v-model="proModalValidate.pInvestOwnerId" placeholder="请输入发行机构"></Input>
        </FormItem>
        <FormItem label="管理机构" prop="pTgjg">
          <Input v-model="proModalValidate.pTgjg" placeholder="请输入管理机构"></Input>
        </FormItem>
        <FormItem label="投资期限" prop="pDulTime">
          <Input v-model="proModalValidate.pDulTime" placeholder="请输入投资期限"></Input>
        </FormItem>
        <FormItem label="成立日期" prop="pSaleStartTime">
          <DatePicker type="date" @on-change="handleDatePicker" transfer confirm placeholder="请选择成立日期" format="yyyy-MM-dd" :value="proModalValidate.pSaleStartTime"></DatePicker>
        </FormItem>
        <!-- <FormItem label="最低投资金额" prop="pMinAmount" :label-width="100">
          <Input v-model="proModalValidate.pMinAmount" placeholder="请输入最低投资金额"></Input>
        </FormItem> -->
        <FormItem label="最迟打款天数" prop="pLatestPayNum" :label-width="100">
          <Input v-model="proModalValidate.pLatestPayNum" placeholder="请输入最迟打款天数"></Input>
        </FormItem>
        <FormItem label="Summary" prop="pRecruitmentSummary">
          <Input v-model="proModalValidate.pRecruitmentSummary" type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="Summary"></Input>
        </FormItem>
        <FormItem label="注意:" prop="name" :label-width="40">
          <p style="color:red;">以下输入框全部需要填写格式化文本，请点击
            <router-link :to="{ name: 'Udit'}" tag="a" target="_blank">
              【富文本编辑】
            </router-link>
            <!-- <span style="color:blue;cursor:pointer;" @click="openNewUdit">【富文本编辑】</span>
            内完成格式化文本编辑后复制至此即可 -->
            内完成格式化文本编辑后复制至此即可
          </p>
        </FormItem>
        <!-- 此模块内容都是富文本 -->
        <FormItem label="产品优势" prop="pCpys">
          <Input v-model="proModalValidate.pCpys" type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="产品优势"></Input>
        </FormItem>
        <FormItem label="募集账号" prop="pMjzh">
          <Input v-model="proModalValidate.pMjzh" type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="募集账号"></Input>
        </FormItem>
        <FormItem label="风险控制" prop="pFxkz">
          <Input v-model="proModalValidate.pFxkz" type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="风险控制"></Input>
        </FormItem>
        <FormItem label="还款来源" prop="pHkly">
          <Input v-model="proModalValidate.pHkly" type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="还款来源"></Input>
        </FormItem>
        <FormItem label="资金用途" prop="pZjyt">
          <Input v-model="proModalValidate.pZjyt" type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="资金用途"></Input>
        </FormItem>
        <!-- TODO:在c端中未见到担保方字段 -->
        <FormItem label="融资方" prop="pRzf">
          <Input v-model="proModalValidate.pRzf" type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="融资方"></Input>
        </FormItem>
        <FormItem label="担保方" prop="pDbf">
          <Input v-model="proModalValidate.pDbf" type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="担保方"></Input>
        </FormItem>
        <FormItem label="认购须知" prop="pRgxz">
          <Input v-model="proModalValidate.pRgxz" type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="认购须知"></Input>
        </FormItem>
        <FormItem label="备注" prop="pRemark">
          <Input v-model="proModalValidate.pRemark" type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="产品的备注信息"></Input>
        </FormItem>
        <!-- 上传相关pdf资料模块 -->
        <FormItem label="上传pdf资料" prop="PDF">
          <Upload multiple type="drag" action="//jsonplaceholder.typicode.com/posts/">
            <div style="padding: 20px 0">
              <Icon type="ios-cloud-upload" size="52" style="color: #3399ff"></Icon>
              <p>可以点击或者拖曳上传</p>
            </div>
          </Upload>
        </FormItem>
      </Form>
    </Modal>
  </div>
</template>

<script>
import moment from "moment";
import EditTable from "./table/EditTable";
import { editInlineColumns } from "./tableConf";
import {
  pTypeList,
  pSaleStatusList,
  pInvestTypeList,
  pPaymentInterestTypeList,
  pSizeRatioType
} from "../enumeration.js";
export default {
  components: {
    EditTable
  },
  data() {
    return {
      dataList: [], //产品列表
      editInlineColumns: [], //table组件的配置文件
      /**
       * 条件查询
       */
      proNameSearch: "", //产品名称
      currPType: "01", //当前产品类型
      pTypeList: [...pTypeList],
      /**
       * 翻页数据
       */
      total: 0, //总记录数
      pageSize: 20, //每页数据条数,
      pageNumber: 1, //当前第几页,
      /**
       * 模态框
       */
      loading: true, //模态框按钮是否loading状态
      ModalStaus: [true, false], //模态框状态枚举，0：新增，1：编辑
      titleModal: "", //模态框标题
      proModalVisible: false, //模态框状态
      pSaleStatusList: [...pSaleStatusList], //产品销售状态
      pInvestTypeList: [...pInvestTypeList], //投资领域
      pPaymentInterestTypeList: [...pPaymentInterestTypeList], //付息方式
      pSizeRatioTypeList: [...pSizeRatioType], //大小配比类型
      //模态框数据对象
      // TODO:未知字段未完善，
      proModalValidate: {
        id: "",
        pCode: "",
        pType: "", //当前产品类型
        pShortName: "",
        pFullName: "",
        pAllIssuingScale: "", //募集规模
        pSaleStatus: "", //当前产品销售状态
        pInvestType: "", //当前产品投资领域
        pPaymentInterestType: "", //当前产品付息方式
        pSizeRatioType: "", //当前产品大小配比
        pInvestOwnerId: "", //发行机构
        pDulTime: "",
        // pMinAmount: "",
        pLatestPayNum: "",
        pCpys: "",
        pMjzh: "",
        pFxkz: "",
        pHkly: "",
        pZjyt: "",
        pRzf: "",
        pDbf: "",
        pRemark: "", //备注
        pTgjg: "", // 管理机构
        pRgxz: "", // 认购须知
        pRecruitmentSummary: "",
        pSaleStartTime: "",
        profitRebates: this.profitRebates
      },
      /**
       *  投资区间对象
       */
      profitRebates: [
        {
          prStartAmount: "100", //默认100万起
          prEndAmount: "",
          prExpectAnnualRevenue: "", //预期年化收益
          prCommission: "" //返佣比例
        }
      ],
      // 表单验证规则对象
      ruleValidate: {
        pShortName: [
          {
            required: true,
            message: "请输入产品简称",
            trigger: "blur"
          }
        ],
        pType: [
          {
            required: true,
            message: "请输入产品类型",
            trigger: "change"
          }
        ],
        pAllIssuingScale: [
          {
            required: true,
            message: "请输入募集规模",
            trigger: "blur"
          }
        ],
        pDulTime: [
          {
            required: true,
            message: "请输入投资期限",
            trigger: "change"
          }
        ]
      }
    };
  },
  created() {
    this.editInlineColumns = editInlineColumns;
    this.handleSearch();
  },
  methods: {
    /**
     * 日期选择器--change事件
     */
    handleDatePicker(date) {
      console.log(date);
      this.proModalValidate.pSaleStartTime = date;
      // this.value3 = date;
    },
    // 打开新页面
    openNewUdit() {
      // 获取url的前缀
      // const host = window.location.host;
      this.$router.resolve({ name: "Udit" });
      // window.open(`${host}/#/main/Udit`);
    },
    /**
     * 投资区间列表：增加单机事件
     */
    add_profitRebates() {
      this.profitRebates.push({
        prStartAmount: "",
        prEndAmount: "",
        prExpectAnnualRevenue: "", //预期年化收益
        prCommission: "" //返佣比例
      });
    },
    /**
     * 投资区间列表：移除当前选中行
     */
    delete_profitRebates(pIndex) {
      if (pIndex === 0) {
        return;
      }
      const temObj = this.profitRebates[pIndex];
      const temObjInterval =
        parseFloat(temObj.prEndAmount) - parseFloat(temObj.prStartAmount);
      this.profitRebates.forEach((item, index) => {
        if (index > pIndex) {
          item.prStartAmount = parseFloat(item.prStartAmount) - temObjInterval;
          item.prEndAmount = parseFloat(item.prEndAmount) - temObjInterval;
        }
      });
      this.profitRebates.splice(pIndex, 1);
    },
    /**
     * 更新投资区间
     */
    lastChange(index) {
      // debugger;
      //获取当前投资区间的长度
      const arrLength = this.profitRebates.length;
      //获取当前对象
      const temObj = this.profitRebates[index];
      // 判断当前行是否是最后一行
      if (index < arrLength - 1) {
        // 获取下一行的对象
        const temNextObj = this.profitRebates[index + 1];
        // 将这一行的右区间设置为上一行的左区间
        temNextObj.prStartAmount = temObj.prEndAmount;
      }
      if (index !== 0 && arrLength > 1) {
        // 获取上一行的对象
        const temLineObj = this.profitRebates[index - 1];
        // 将这一行的左区间设置为上一行的右区间
        temLineObj.prEndAmount = temObj.prStartAmount;
      }
    },
    //   获取理财师列表
    handleSearch() {
      this.$ajax({
        url: `/product/list?pType=${this.currPType}&pageNumber=${
          this.pageNumber
        }&pageSize=${this.pageSize}`,
        method: "get"
      }).then(res => {
        if (res.data.status == 200) {
          this.total = res.data.result.total;
          this.pageSize = res.data.result.pageSize;
          this.pageNumber = res.data.result.pageNumber;
          this.dataList = res.data.result.dataList;
        }
      });
    },
    /**
     *  了解详情
     */
    handleDetails(index) {
      const currObj = this.dataList[index];
      // TODO:此处是做模态框弹出展示，还是进入子级页面展示不清楚
      this.$router.push({
        name: "nosmProDetails",
        query: {
          pDetils: JSON.stringify(currObj)
        }
      });
    },
    /**
     * 删除当前产品
     */
    handleDelete(index) {},
    /**
     * 显示编辑页面
     */
    handleChange(index) {
      this.ModalStaus = Array.from({ length: 2 }, () => false);
      // TODO:可以根据此字段做条件判断模态框内的可编辑项
      this.ModalStaus[1] = true;

      const currObj = this.dataList[index];
      this.titleModal = "编辑产品";
      this.proModalVisible = true;
      this.profitRebates = currObj.profitRebates;
      // 初始化表单数据，让编辑模态框展示有内容
      this.proModalValidate = {
        id: currObj.id,
        pCode: currObj.pCode,
        pType: currObj.pType, // 当前产品类型
        pShortName: currObj.pShortName,
        pFullName: currObj.pFullName,
        pAllIssuingScale: currObj.pAllIssuingScale, // 募集规模
        pSaleStatus: currObj.pSaleStatus, // 当前产品销售状态
        pInvestType: currObj.pInvestType, // 当前产品投资领域
        pPaymentInterestType: currObj.pPaymentInterestType, // 当前产品付息方式
        pSizeRatioType: currObj.pSizeRatioType, // 当前产品大小配比
        pInvestOwnerId: currObj.pInvestOwnerId, // 发行机构
        pDulTime: currObj.pDulTime,
        // pMinAmount: "",
        pLatestPayNum: currObj.pLatestPayNum,
        pCpys: currObj.pCpys,
        pMjzh: currObj.pMjzh,
        pFxkz: currObj.pFxkz,
        pHkly: currObj.pHkly,
        pZjyt: currObj.pZjyt,
        pRzf: currObj.pRzf,
        pDbf: currObj.pDbf,
        pRemark: currObj.pRemark,
        pTgjg: currObj.pTgjg,
        pRgxz: currObj.pRgxz,
        pRecruitmentSummary: currObj.pRecruitmentSummary,
        pSaleStartTime: currObj.pSaleStartTime,
        profitRebates: this.profitRebates
      };
      this.proModalVisible = true;
    },
    //显示新增界面
    handleAdd() {
      this.ModalStaus = Array.from({ length: 2 }, () => false);
      this.ModalStaus[0] = true;
      this.titleModal = "新增产品";
      this.proModalVisible = true;
      this.proModalValidate = {
        pType: "", //当前产品类型
        pShortName: "",
        pFullName: "",
        pAllIssuingScale: "", //募集规模
        pSaleStatus: "", //当前产品销售状态
        pInvestType: "", //当前产品投资领域
        pPaymentInterestType: "", //当前产品付息方式
        pSizeRatioType: "", //当前产品大小配比
        pInvestOwnerId: "", //发行机构
        pDulTime: "",
        // pMinAmount: "",
        pLatestPayNum: "2",
        pCpys: "",
        pMjzh: "",
        pFxkz: "",
        pHkly: "",
        pZjyt: "",
        pRzf: "",
        pDbf: "",
        pRemark: "",
        pTgjg: "", // 管理机构
        pRgxz: "", // 认购须知
        pRecruitmentSummary: "",
        pSaleStartTime: "",
        profitRebates: this.profitRebates
      };
      this.profitRebates = [
        {
          prStartAmount: "100", //默认100万起
          prEndAmount: "",
          prExpectAnnualRevenue: "", //预期年化收益
          prCommission: "" //返佣比例
        }
      ];
    },
    /**
     * 延迟
     */
    loading_false() {
      this.loading = false;
      setTimeout(() => {
        this.loading = true;
      }, 100);
    },
    /**模态框确定按钮 */
    handleSubmit(name) {
      // 对投资区间做过滤
      if (this.profitRebates.length > 0) {
        for (let i = 0; i < this.profitRebates.length; i++) {
          const ele = this.profitRebates[i];
          if (!ele.prCommission) {
            this.$Message.error("投资区间内【返佣比例】不能为空");
            this.loading_false();
            return;
          }
          if (!ele.prExpectAnnualRevenue) {
            this.loading_false();
            this.$Message.error("投资区间内【预期年化收益】不能为空");
            return;
          }
          if (
            i !== this.profitRebates.length - 1 &&
            ele.prStartAmount > ele.prEndAmount
          ) {
            this.loading_false();
            this.$Message.error("区间内单行投资金额左边必须小于右边");
            return;
          }
          if (
            i === this.profitRebates.length - 1 &&
            ele.prEndAmount != 0 &&
            ele.prStartAmount > ele.prEndAmount
          ) {
            this.loading_false();
            this.$Message.error("区间内单行投资金额左边必须小于右边");
            return;
          }
        }
      } else {
        this.loading_false();
        this.$Message.error("投资区间必须填入信息");
        return;
      }
      this.$refs[name].validate(valid => {
        this.proModalValidate.profitRebates = this.profitRebates;
        if (valid) {
          if (this.ModalStaus[0]) {
            this.addProduct();
          } else if (this.ModalStaus[1]) {
            this.editProduct();
          }
        } else {
          this.$Message.error("参数有误");
        }
      });
    },

    // 编辑产品
    editProduct() {
      // TODO:在此处编写ajax请求即可
      this.$ajax({
        url: "/product/editProduct",
        params: JSON.stringify(this.proModalValidate),
        qsStatus: false
      }).then(res => {
        console.log(res);
        if (res.data.status === 200) {
          this.$Message.success("修改成功!");
          this.loading = true;
          this.proModalVisible = false;
          this.handleSearch();
        } else {
          this.$Message.error("修改失败!");
          this.loading = true;
          this.proModalVisible = false;
        }
      });
    },

    // 新增产品
    addProduct() {
      // TODO:在此处编写ajax请求即可
      this.$ajax({
        url: "/product/addProduct",
        params: JSON.stringify(this.proModalValidate),
        qsStatus: false
      }).then(res => {
        console.log(res);
        if (res.data.status === 200) {
          this.loading = true;
          this.proModalVisible = false;
          this.$Message.success("新增成功!");
          this.handleSearch();
        } else {
          this.loading = true;
          this.proModalVisible = false;
          this.$Message.error("新增失败!");
        }
      });
    }
  }
};
</script>

<style>

</style>

