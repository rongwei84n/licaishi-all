/*
 * @Author: 张浩然 
 * @Date: 2018-03-18 21:38:35 
 * @Last Modified by: 张浩然
 * @Last Modified time: 2018-03-18 22:42:19
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
      <el-form :model="addForm" label-width="100px" :rules="formRules" ref="addForm">
        <el-form-item label="产品简称" prop="pShortName" class="w40p">
          <el-input v-model="addForm.pShortName"></el-input>
        </el-form-item>
        <el-form-item label="产品全称" prop="pFullName" class="w60p">
          <el-input v-model="addForm.pFullName"></el-input>
        </el-form-item>
        <el-form-item label="产品类型" prop="pType">
          <el-select v-model="addForm.pType" placeholder="请选择产品类型">
            <el-option v-for="(item,index) of pTypeList" :key="index" :label="item.value" :value="item.key"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="募集规模" prop="pAllIssuingScale">
          <el-input v-model="addForm.pAllIssuingScale" placeholder="请输入募集规模">
            <template slot="append">万元</template>
          </el-input>
        </el-form-item>
        <el-form-item label="投资期限" prop="pDulTime">
          <el-input v-model="addForm.pDulTime">
            <template slot="append">月</template>
          </el-input>
        </el-form-item>
        <el-form-item label="预期年化收益" prop="expectAnnualRevenue">
          <el-input v-model="addForm.pExpectAnnualRevenue">
            <template slot="append">%</template>
          </el-input>
        </el-form-item>
        <el-form-item label="产品销售状态" prop="pSaleStatus">
          <el-select v-model="addForm.pSaleStatus" placeholder="请选择产品销售状态">
            <el-option v-for="(item,index) of pSaleStatusList" :key="index" :label="item.value" :value="item.key"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="投资领域" prop="pInvestType">
          <el-select v-model="addForm.pInvestType" placeholder="请选择投资领域">
            <el-option v-for="(item,index) of pInvestTypeList" :key="index" :label="item.value" :value="item.key"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="付息方式" prop="pPaymentInterestType">
          <el-select v-model="addForm.pPaymentInterestType" placeholder="请选择付息方式">
            <el-option v-for="(item,index) of pPaymentInterestTypeList" :key="index" :label="item.value" :value="item.key"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="发行日期" prop="pSaleStartTime">
          <el-date-picker type="date" value-format="yyyy-MM-dd" placeholder="选择日期" v-model="addForm.pSaleStartTime"></el-date-picker>
        </el-form-item>
        <el-form-item label="发行机构" prop="pInvestOwnerId">
          <el-input v-model="addForm.pInvestOwnerId"></el-input>
        </el-form-item>
        <el-form-item label="大小配比" prop="pSizeRatioType">
          <el-select v-model="addForm.pSizeRatioType" placeholder="请选择大小配比类型">
            <el-option label="小额畅打" value="01"></el-option>
            <el-option label="已配出小额" value="02"></el-option>
            <el-option label="严格配比" value="03"></el-option>
            <el-option label="全大额" value="04"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="最低投资金额" prop="pMinAmount">
          <el-input v-model="addForm.pMinAmount">
            <template slot="append">万元</template>
          </el-input>
        </el-form-item>
        <el-form-item label="最迟打款天数" prop="latestPayNum">
          <el-input v-model="addForm.latestPayNum">
            <template slot="append">天</template>
          </el-input>
        </el-form-item>
        <el-form-item label="备注" prop="pRecruitmentSummary">
          <el-input type="textarea" v-model="addForm.pRecruitmentSummary"></el-input>
        </el-form-item>
        <el-form-item label="产品优势" prop="pCpys">
          <el-input type="textarea" v-model="addForm.pCpys"></el-input>
        </el-form-item>
        <el-form-item label="募集账号" prop="pMjzh">
          <el-input type="textarea" v-model="addForm.pMjzh"></el-input>
        </el-form-item>
        <el-form-item label="风险控制" prop="pFxkz">
          <el-input type="textarea" v-model="addForm.pFxkz"></el-input>
        </el-form-item>
        <el-form-item label="还款来源" prop="pHkly">
          <el-input type="textarea" v-model="addForm.pHkly"></el-input>
        </el-form-item>
        <el-form-item label="资金用途" prop="pZjyt">
          <el-input type="textarea" v-model="addForm.pZjyt"></el-input>
        </el-form-item>
        <el-form-item label="融资方" prop="pRzf">
          <el-input type="textarea" v-model="addForm.pRzf"></el-input>
        </el-form-item>
        <el-form-item label="担保方" prop="pDbf">
          <el-input type="textarea" v-model="addForm.pDbf"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="addFormVisible = false">取消</el-button>
        <el-button type="primary" @click.native="addSubmit" :loading="addLoading">提交</el-button>
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
      }
    };
  },
  methods: {
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
                  this.addFormVisible = false;
                  this.handleSearch();
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
    // 底部的按钮框
    .dialog-footer {
      display: flex;
      justify-content: center;
      align-items: center;
      .el-button {
        width: 15%;
      }
    }
  }
}
</style>




