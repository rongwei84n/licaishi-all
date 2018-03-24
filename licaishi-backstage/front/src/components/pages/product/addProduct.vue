/*
 * @Author: 张浩然 
 * @Date: 2018-03-18 21:38:35 
 * @Last Modified by: 张浩然
 * @Last Modified time: 2018-03-20 18:37:31
 * 
 * 新增产品页面，因为添加东西太多
 */


<template>
  <div id="addProduct">
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/home' }">
        <i class="el-icon-menu"></i> 首页</el-breadcrumb-item>
      <el-breadcrumb-item>产品管理</el-breadcrumb-item>
      <el-breadcrumb-item>新增产品</el-breadcrumb-item>
    </el-breadcrumb>
    <div class="addProduct-content">
      <el-form :model="addForm" label-width="100px" ref="addForm ">
        <el-form-item label="产品类型" prop="pType">
          <el-select v-model="addForm.pType " placeholder="请选择产品类型 ">
            <el-option v-for="(item,index) of pTypeList " :key="index " :label="item.value " :value="item.key "></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="产品简称: " prop="pShortName " class="w40p ">
          <el-input v-model="addForm.pShortName "></el-input>
        </el-form-item>
        <el-form-item label="产品销售状态: " prop="pSaleStatus ">
          <el-select v-model="addForm.pSaleStatus " placeholder="请选择产品销售状态 ">
            <el-option v-for="(item,index) of pSaleStatusList " :key="index " :label="item.value " :value="item.key "></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="投资信息配置:" prop="pSaleStatus">
          <div class="investConf-content">
            <p style="color:red">投资金额区间必须从100万开始且必须连贯</p>
            <table cellspacing="10px">
              <thead>
                <tr>
                  <th>投资金额(万)</th>
                  <th>预期年化收益(%)</th>
                  <th>返佣比例(%)</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(item,index) of investConfList" :key="index">
                  <td class="money-content">
                    <el-input type="text" v-model="item.prStartAmount" @change="lastChange(index)" :disabled="index===0&&investConfList.length!==1" />
                    <span>-</span>
                    <el-input type="text" v-model="item.prEndAmount" :disabled="(index+1) !== investConfList.length&&investConfList.length!==1" />
                  </td>
                  <td>
                    <el-input v-model="item.prExpectAnnualRevenue" type="text" />
                  </td>
                  <td>
                    <el-input type="text" v-model="item.prCommission" />
                  </td>
                  <td @click="delete_investConfList(index)">
                    -
                  </td>
                </tr>
              </tbody>
            </table>
            <div class="dialog-footer">
              <el-button type="primary " @click.native="add_investConfList">增加</el-button>
            </div>
          </div>
        </el-form-item>
        <el-form-item label="产品全称:" prop="pFullName" class="w60p">
          <el-input v-model="addForm.pFullName "></el-input>
        </el-form-item>
        <el-form-item label="募集规模" prop="pAllIssuingScale ">
          <el-input v-model="addForm.pAllIssuingScale " placeholder="请输入募集规模 ">
            <template slot="append ">万元</template>
          </el-input>
        </el-form-item>
        <el-form-item label="投资领域 " prop="pInvestType ">
          <el-select v-model="addForm.pInvestType " placeholder="请选择投资领域 ">
            <el-option v-for="(item,index) of pInvestTypeList " :key="index " :label="item.value " :value="item.key "></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="付息方式 " prop="pPaymentInterestType ">
          <el-select v-model="addForm.pPaymentInterestType " placeholder="请选择付息方式 ">
            <el-option v-for="(item,index) of pPaymentInterestTypeList " :key="index " :label="item.value " :value="item.key "></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="大小配比 " prop="pSizeRatioType ">
          <el-select v-model="addForm.pSizeRatioType " placeholder="请选择大小配比类型 ">
            <el-option label="小额畅打 " value="01 "></el-option>
            <el-option label="已配出小额 " value="02 "></el-option>
            <el-option label="严格配比 " value="03 "></el-option>
            <el-option label="全大额 " value="04 "></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="发行机构 " prop="pInvestOwnerId ">
          <el-input v-model="addForm.pInvestOwnerId "></el-input>
        </el-form-item>
        <el-form-item label="投资期限 " prop="pDulTime ">
          <el-input v-model="addForm.pDulTime ">
            <template slot="append ">月</template>
          </el-input>
        </el-form-item>
        <el-form-item label="发行时间 " prop="pSaleStartTime ">
          <el-date-picker type="date " value-format="yyyy-MM-dd " placeholder="选择日期 " v-model="addForm.pSaleStartTime "></el-date-picker>
        </el-form-item>
        <el-form-item label="预期年化收益 " prop="expectAnnualRevenue ">
          <el-input v-model="addForm.pExpectAnnualRevenue ">
            <template slot="append ">%</template>
          </el-input>
        </el-form-item>

        <el-form-item label="最低投资金额 " prop="pMinAmount ">
          <el-input v-model="addForm.pMinAmount ">
            <template slot="append ">万元</template>
          </el-input>
        </el-form-item>
        <el-form-item label="最迟打款天数 " prop="latestPayNum ">
          <el-input v-model="addForm.latestPayNum ">
            <template slot="append ">天</template>
          </el-input>
        </el-form-item>
      </el-form>
    </div>
    <div class="addProduct-content ">
      <h4>富文本编辑区域</h4>
      <h6>此区域文本请到可视化编辑模块进行完善之后直接拷贝至此</h6>
      <el-form :model="addForm" label-width="100px" ref="addForm">
        <el-form-item label="备注 " prop="pRecruitmentSummary">
          <el-input type="textarea " v-model="addForm.pRecruitmentSummary "></el-input>
        </el-form-item>
        <el-form-item label="产品优势 " prop="pCpys ">
          <el-input type="textarea " v-model="addForm.pCpys "></el-input>
        </el-form-item>
        <el-form-item label="募集账号 " prop="pMjzh ">
          <el-input type="textarea " v-model="addForm.pMjzh "></el-input>
        </el-form-item>
        <el-form-item label="风险控制 " prop="pFxkz ">
          <el-input type="textarea " v-model="addForm.pFxkz "></el-input>
        </el-form-item>
        <el-form-item label="还款来源 " prop="pHkly ">
          <el-input type="textarea " v-model="addForm.pHkly "></el-input>
        </el-form-item>
        <el-form-item label="资金用途 " prop="pZjyt ">
          <el-input type="textarea " v-model="addForm.pZjyt"></el-input>
        </el-form-item>
        <el-form-item label="融资方 " prop="pRzf ">
          <el-input type="textarea " v-model="addForm.pRzf "></el-input>
        </el-form-item>
        <el-form-item label="担保方 " prop="pDbf ">
          <el-input type="textarea " v-model="addForm.pDbf "></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer " class="dialog-footer ">
        <el-button type="primary " @click.native="test " :loading="addLoading ">提交</el-button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      /**
       * TODO:所有的枚举都应该专门使用一个配置文件来进行操作
       * 枚举块
       */
      pTypeList: [
        {
          key: "01",
          value: "集合信托"
        },
        {
          key: "02",
          value: "集合资管"
        },
        {
          key: "03",
          value: "债权基金"
        }
      ],
      pSaleStatusList: [
        {
          key: "01",
          value: "预热中"
        },
        {
          key: "02",
          value: "募集中"
        },
        {
          key: "03",
          value: "募集结束"
        }
      ],
      pInvestTypeList: [
        {
          key: "01",
          value: "房地产类"
        },
        {
          key: "02",
          value: "金融市场"
        },
        {
          key: "03",
          value: "基础设施"
        },
        {
          key: "04",
          value: "资金池"
        },
        {
          key: "05",
          value: "工商企业"
        },
        {
          key: "00",
          value: "其他"
        }
      ],
      pPaymentInterestTypeList: [
        {
          key: "01",
          value: "按月付息"
        },
        {
          key: "02",
          value: "按季付息"
        },
        {
          key: "03",
          value: "按半年付息"
        },
        {
          key: "04",
          value: "按年付息"
        },
        {
          key: "06",
          value: "到期付本息"
        }
      ],
      addLoading: false, //提交按钮的过渡状态
      /**
       * 表单对象
       */
      addForm: {
        pShortName: "", //产品简称
        pFullName: "", //产品全称
        pType: "", //产品类型
        pAllIssuingScale: "", //募集规模
        pDulTime: "", //投资期限
        pExpectAnnualRevenue: "", //预期年化收益
        pInvestType: "", //投资领域
        pSaleStatus: "", //产品销售状态
        pPaymentInterestType: "", //付息方式
        pMinAmount: "",
        pSaleStartTime: "",
        pSizeRatioType: "",
        pInvestOwnerId: "",
        pAllSubscriptionAmount: "",
        pRecruitmentSummary: "",
        pCpys: "",
        pMjzh: "",
        pFxkz: "",
        pHkly: "",
        pRzf: "",
        pDbf: "",
        pZjyt: ""
      },
      // 投资区间对象
      investConfList: [
        {
          prStartAmount: "100", //默认100万起
          prEndAmount: "",
          prExpectAnnualRevenue: "", //预期年化收益
          prCommission: "" //返佣比例
        }
      ],
      // 表单验证规则
      rules: {
        pShortName: [
          { required: true, message: "请输入产品简称", trigger: "blur" },
          { min: 4, max: 12, message: "长度在 4 到 12 个字符", trigger: "blur" }
        ],
        pFullName: [
          { required: true, message: "请输入产品全称", trigger: "blur" },
          { min: 4, max: 16, message: "长度在 4 到 16 个字符", trigger: "blur" }
        ],
        pType: [
          { required: true, message: "请选择产品类型", trigger: "change" }
        ],
        pAllIssuingScale: [
          {
            type: "number",
            required: true,
            message: "请输入募集规模",
            trigger: "blur"
          }
        ],
        pDulTime: [
          {
            type: "number",
            required: true,
            message: "请输入投资期限",
            trigger: "blur"
          }
        ],
        resource: [
          { required: true, message: "请选择活动资源", trigger: "change" }
        ],
        desc: [{ required: true, message: "请填写活动形式", trigger: "blur" }]
      },
      // 测试数据结构
      pDetailsObj: {
        pAllIssuingScale: "100000000",
        pAllSubscriptionAmount: null,
        pCode: "283430ba-0247-11e8-b8ea-ecf4bbce3884",
        pCommission: "1.2%",
        pCpys:
          "地级市政信、AA级融资主体、AA+国企连带责任担保、7.9亿应收账款质押登记",
        pDbf: "遵义市播州区城市建设投资经营有限公司",
        pDulTime: "24",
        pExpectAnnualRevenue: "8.8%",
        pFullName: "国通信托-大通阳明全称",
        pFxkz:
          "<p>1、遵义市播州区城市建设投资经营有限公司为融资方履行还本付息义务提供连带责任保证担保。 </p><p>2、应收账款转让登记：本计划资金用于受让融资方因代建标的应收账款项目而享有对播州区人民政府【79,383.48】万元应收账</p><p>款债权，并办理应收账款转让登记。 </p>",
        pHkly: "遵义国投营业收入、营业外收入及筹资性现金流",
        pId: "1",
        pInvestName: "上海信托",
        pInvestOwnerId: "1",
        pInvestType: "03",
        pLatestPayNum: "2018-03-22",
        pMinAmount: "1000000",
        pMjzh:
          "大通资产-阳明221号集合资产管理计划 <br/>【账户名】<br/>【账 号】<br/>【开户行】<br/>【备 注】XX认购大通阳明221号XX万",
        pPaymentInterestType: "03",
        pRecruitmentSummary: "本期第1期，本期规模2000万，开放进款中，有下期。",
        pRexiao: "Y",
        pRgxz: null,
        pRrzf: null,
        pSaleStartTime: 1519192702000,
        pSaleStatus: "02",
        pShortName: "国通信托-大通阳明 221 号",
        pSizeRatioType: "01",
        pTuijian: "Y",
        pType: "01",
        pZjyt:
          "<p>直接用于受让遵义国投享有对播州区人民政府79383.48万元应收账款债权，最终用于贵州省内经济较发达地区遵义市播州区基础设施建设</p>",
        productAttachments: [],
        profitRebates: [
          {
            prAmountDisplay: "100-200万",
            prCommission: "0.1%",
            prEndAmount: 2000000,
            prExpectAnnualRevenue: "8.8%",
            prProductCode: "283430ba-0247-11e8-b8ea-ecf4bbce3884",
            prStartAmount: 1000000
          }
        ]
      }
    };
  },
  methods: {
    /**
     * 更新投资区间
     */
    lastChange(index) {
      //获取当前投资区间的长度
      const arrLength = this.investConfList.length;
      //获取当前对象
      const temObj = this.investConfList[index];
      // 判断当前行是否是最后一行
      if (index < arrLength - 1) {
        // 获取下一行的对象
        const temNextObj = this.investConfList[index + 1];
        // 将这一行的右区间设置为上一行的左区间
        temNextObj.prStartAmount = temObj.prEndAmount;
      }
      if (index !== 0 && arrLength > 1) {
        // 获取上一行的对象
        const temLineObj = this.investConfList[index - 1];
        // 将这一行的左区间设置为上一行的右区间
        temLineObj.prEndAmount = temObj.prStartAmount;
      }
    },
    test() {
      this.$ajax({
        url: "/product/addProduct",
        params: this.pDetailsObj,
        qsStatus: false
      }).then(res => {
        console.log(res);
      });
    },
    // 增加投资区间item
    add_investConfList() {
      this.investConfList.push({
        prStartAmount: "",
        prEndAmount: "",
        prExpectAnnualRevenue: "", //预期年化收益
        prCommission: "" //返佣比例
      });
    },
    // 移除当前选中行
    delete_investConfList(pIndex) {
      if (pIndex === 0) {
        return;
      }
      const temObj = this.investConfList[pIndex];
      const temObjInterval =
        parseFloat(temObj.prEndAmount) - parseFloat(temObj.prStartAmount);
      this.investConfList.forEach((item, index) => {
        if (index > pIndex) {
          item.prStartAmount = parseFloat(item.prStartAmount) - temObjInterval;
          item.prEndAmount = parseFloat(item.prEndAmount) - temObjInterval;
        }
      });
      this.investConfList.splice(pIndex, 1);
    },
    /**
     * 新增模态框--提交按钮
     */
    addSubmit() {
      this.$refs.addForm.validate(valid => {
        if (valid) {
          this.$confirm("确认提交吗？", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }).then(() => {
            this.addLoading = true;
            this.$axios
              .post("/product/addProduct", this.addForm)
              .then(res => {
                this.addLoading = false;
                if (res.data.status == 200) {
                  this.$message({
                    type: "success",
                    message: "新增成功!"
                  });
                  this.$refs["addForm"].resetFields();
                } else {
                  this.$message({
                    showClose: true,
                    message: res.data.message,
                    type: "error"
                  });
                }
              })
              .catch(res => {
                this.addLoading = false;
                this.$message({
                  showClose: true,
                  message: "访问服务器异常",
                  type: "warning"
                });
              });
          });
        }
      });
    }
  }
};
</script>

<style lang="scss">
#addProduct {
  .addProduct-content {
    margin: 40px 0;
    padding: 20px;
    padding-right: 60px;
    background-color: #f9f7f7;
    border-radius: 20px;
    // 公共样式模块
    .w20p {
      width: 20%;
    }
    .w40p {
      width: 40%;
    }
    .w60p {
      width: 60%;
    }
    .w80p {
      width: 80%;
    }
    > div {
      margin-bottom: 20px;
    }
  }
}

// 投资信息块
.investConf-content {
  border: 1px solid #e6e6e6;
  padding: 20px;
  .money-content {
    display: flex;
    justify-content: space-around;
    align-items: center;
    // .el-input {
    //   display: inline-block;
    // }
  }
}

// 底部的按钮框
.dialog-footer {
  display: flex;
  justify-content: center;
  align-items: center;
  .el-button {
    width: 15%;
  }
}
</style>




