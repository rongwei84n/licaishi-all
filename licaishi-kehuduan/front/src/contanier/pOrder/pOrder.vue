/*
 * @Author: 张浩然 
 * @Date: 2018-03-07 19:23:27 
 * @Last Modified by: 张浩然
 * @Last Modified time: 2018-03-18 10:46:17
 *
 * 基础布局组件
 * 带头部与底部布局
 */

 <template>
  <div id="order">
    <mt-header title="预约">
      <mt-button icon="back" @click="back" slot="left"></mt-button>
    </mt-header>
    <Scroll id="scroll">
      <div class="scroll-content">
        <mt-field label="产品名称" disabled v-model="pName"></mt-field>
        <!-- TODO:此处客户另外会提供接口获取 -->
        <mt-field label="客户姓名" disabled placeholder="请输入客户姓名" v-model="customerName" @click.native="updatePickerStatus('CustomersModal')">
          <i class="fa fa-address-card"></i>
        </mt-field>
        <mt-field label="身份证号" type="number" placeholder="请输入身份证号" v-model="cardId"></mt-field>
        <!-- <mt-field label="手机号" placeholder="请输入手机号" v-model="phone"></mt-field> -->
        <mt-field label="银行卡号" type="number" placeholder="请输入银行卡号" v-model="bankCardNo"></mt-field>
        <mt-field label="打卡行" disabled placeholder="点击右侧图标选择银行" v-model="bankName" @click.native="updatePickerStatus('bankCardPickStatus')">
          <i class="fa fa-credit-card"></i>
        </mt-field>
        <mt-field label="预约金额" type="number" placeholder="请输入预约金额" v-model="amount">
          <span>万元</span>
        </mt-field>
        <mt-field label="最迟打款日期" placeholder="最迟打款日期" disabled v-model="pLatestPayNum">
          <!-- <mt-field label="最迟打款日期" placeholder="最迟打款日期" disabled v-model="pLatestPayNum" @click.native="openDatePicker"> -->
          <!-- <i class="fa fa-calendar"></i> -->
        </mt-field>
        <mt-field label="备注" placeholder="备注" type="textarea" rows="4" v-model="note"></mt-field>
      </div>
    </Scroll>
    <!-- 推荐 -->
    <footer class="footer">
      <span class="submit" @click="submit">提交</span>
    </footer>
    <mt-datetime-picker type="date" ref="picker" v-model="pickerValue" year-format="{value} 年" month-format="{value} 月" date-format="{value} 日" @confirm="handleConfirm">
    </mt-datetime-picker>
    <!-- <mt-popup v-model="CustomersModal" position="bottom">
      <mt-picker ref='pickerObj' @change="onValuesChange" :slots="Customers" valueKey="name"></mt-picker>
    </mt-popup> -->
    <!-- 下拉选择菜单 -->
    <pulldown-select :values="Customers" valueKey="name" @confirm="onValuesChange" @cancel="updatePickerStatus('CustomersModal')" v-if="CustomersModal"></pulldown-select>
    <!-- 银行卡选择器 -->
    <pulldown-select :values="bankcard_picker_data" @confirm="bankConfirm" @cancel="updatePickerStatus('bankCardPickStatus')" v-if="bankCardPickStatus"></pulldown-select>
  </div>
</template>

 <script type="es6">
import formatDateTime from "common/js/date";
import ajax from "api/ajax";
import Scroll from "base/scroll/scroll";
import whiteSpace from "base/whiteSpace/whiteSpace";
import PulldownSelect from "base/pulldownSelect/PulldownSelect";
import { MessageBox } from "mint-ui";
import qs from "qs";
import { isCardNo } from "common/js/isCardNo";

export default {
  data() {
    return {
      //产品编号
      pId: "",
      //产品名称
      pName: "",
      /**
       * 银行卡弹窗选择事件
       */
      bankCardPickStatus: false,
      bankcard_picker_data: [
        {
          values: [
            "招商银行",
            "中国银行",
            "工商银行",
            "建设银行",
            "交通银行",
            "农业银行",
            "中国邮政",
            "光大银行",
            "广发银行",
            "华夏银行",
            "民生银行",
            "平安银行",
            "浦发银行",
            "中信银行"
          ],
          className: "slot",
          textAlign: "center",
          flex: 1
        }
      ],
      /**
       *  客户列表选择用
       */
      Customers: [{ values: [] }], //客户列表
      CustomersModal: false,
      /**
       *
       */
      profitRebates: [], //产品佣金收益间隔
      customerId: "", //客户UID
      customerName: "", //客户姓名
      cardId: "", //身份证号
      bankCardNo: "", //银行卡号
      bankName: "", //打卡行
      amount: "", //预约金额
      pLatestPayNum: "", //最迟打款日期
      pickerValue: "",
      note: "", //备注
      proRatio: "", // 预期收益率
      comRatio: "" // 佣金比例
    };
  },
  created() {
    this.get_Customers();
    this.pLatestPayNum = this.$route.query.pLatestPayNum;
    // 此处router获取失败
    this.pId = this.$route.query.pId;
    this.pName = this.$route.query.pShortName;
    this.profitRebates = JSON.parse(this.$route.query.profitRebates);
    console.log(this.profitRebates);
  },
  methods: {
    /* 银行选择组件确定事件 */
    bankConfirm(param) {
      if (param === undefined) {
        param = "工商银行";
      }
      this.bankName = param;
      this.updatePickerStatus("bankCardPickStatus");
    },
    // 打开银行卡选择栏
    updatePickerStatus(params) {
      this[params] = !this[params];
    },
    // 选择客户姓名
    onValuesChange(parmas) {
      this.customerName = parmas.name;
      this.customerId = parmas.uid;
      this.updatePickerStatus("CustomersModal");
    },
    /**
     * 获取客户列表
     */
    get_Customers() {
      ajax({
        url: "/srv/v1/order/queryCustomersForOrder",
        method: "GET"
      }).then(res => {
        if (res.status === 200) {
          this.Customers[0].values = res.data.result.list;
        }
      });
    },
    /* 校验身份证是否规范 */
    check_reg(param, msg) {
      if (this[param]) {
        return true;
      } else {
        MessageBox("提示", msg);
        return false;
      }
    },
    // 提交
    submit() {
      /**
       * 1。判断当前是否全部输入
       */
      if (
        this.check_reg("customerName", "客户姓名未选择") &&
        this.check_reg("cardId", "身份证号未填写") &&
        this.check_reg("bankCardNo", "银行卡号未填写") &&
        this.check_reg("bankName", "打卡行未选择") &&
        this.check_reg("amount", "预约金额未填写") &&
        this.check_reg("pLatestPayNum", "最迟打款日期未选择")
      ) {
        const promise = new Promise(function(resolve, reject) {
          /**
           * 2。首先得判断当前登录状态
           */
          ajax({
            url: `/srv/v1/login_status`,
            method: "GET"
          }).then(res => {
            if (res.status == 200) {
              resolve(res);
            } else {
              reject(err);
            }
          });
        });
        promise.then(
          res => {
            window.phihome.util.netRequest(
              "post",
              `http://47.97.100.240/srv/v1/order/createOrder`,
              "",
              {
                productId: this.pId,
                customerId: this.customerId,
                customerName: this.customerName,
                cardId: this.cardId,
                amount: this.amount,
                lastPayDate: this.pLatestPayNum,
                comRatio: this.comRatio,
                proRatio: this.proRatio,
                issuingBank: this.bankName,
                bankCardNo: this.bankCardNo,
                note: this.note
              },
              res => {
                res = JSON.parse(res);
                // 原生对象包装一层模拟axios返回的对象结构
                if (res.status == 200) {
                  this.$router.replace("/pOrderSuccess");
                }
              }
            );
            // ajax({
            //   url: "/srv/v1/order/createOrder",
            //   params: {
            //     productId: this.pId,
            //     customerId: this.customerId,
            //     customerName: this.customerName,
            //     cardId: this.cardId,
            //     amount: this.amount,
            //     lastPayDate: this.pLatestPayNum,
            //     comRatio: this.comRatio,
            //     proRatio: this.proRatio,
            //     issuingBank: this.bankName,
            //     bankCardNo: this.bankCardNo,
            //     note: this.note
            //   },
            //   method: "post"
            // }).then(res => {
            //   if (res.status == 200) {
            //     console.log("预约订单返回成功");
            //     this.$router.replace("/pOrderSuccess");
            //   }
            // });
          },
          // 此处应该跳转登录页
          err => {}
        );
      }
    },
    /**
     * 返回按钮单机事件
     */
    back() {
      this.$router.go(-1);
    },
    // 获取当前时间
    handleConfirm(date) {
      this.pLatestPayNum = this.pickerValue = formatDateTime({
        time: date,
        ymd: true
      });
    },
    openDatePicker() {
      this.$refs.picker.open();
    }
  },
  computed: {
    // // 佣金比例
    // comRatio() {
    //   this.profitRebates.forEach(item => {
    //     if (
    //       parseInt(this.amount) > parseInt(item.prStartAmount) &&
    //       parseInt(this.amount) < parseInt(item.prEndAmount)
    //     ) {
    //       return item.prCommission;
    //     }
    //   });
    // },
    // // 预期收益率
    // proRatio() {
    //   this.profitRebates.forEach(item => {
    //     if (
    //       parseInt(this.amount) > parseInt(item.prStartAmount) &&
    //       parseInt(this.amount) < parseInt(item.prEndAmount)
    //     ) {
    //       return item.prExpectAnnualRevenue;
    //     }
    //   });
    // }
  },
  watch: {
    amount() {
      const unit = 10000;
      this.profitRebates.forEach(item => {
        if (
          parseInt(this.amount) > parseInt(item.prStartAmount) / unit &&
          parseInt(this.amount) < parseInt(item.prEndAmount) / unit
        ) {
          this.comRatio = item.prCommission;
          this.proRatio = item.prExpectAnnualRevenue;
        }
      });
    }
  },
  components: {
    Scroll,
    whiteSpace,
    PulldownSelect
  }
};
</script>

<style lang="stylus">
@import '~common/stylus/variable';

#order {
  position: relative;
  height: 100%;
  width: 100%;
  z-index: 100;
  background-color: $color-background;

  #scroll {
    position: absolute;
    top: 40px;
    bottom: 50px;
    left: 0;
    right: 0;

    .scroll-content {
      margin-top: 6px;
      background-color: $color-background-d;
    }
  }

  // 客户姓名选择弹出框
  .mint-popup-bottom:not(.mint-datetime) {
    width: 100%;
    height: auto;
  }

  .footer {
    height: 40px;
    position: absolute;
    bottom: 0;
    width: 100%;
    z-index: -1;

    .submit {
      width: 100%;
      height: 100%;
      display: block;
      font-size: 14px;
      line-height: 40px;
      text-align: center;
      background-color: $color-sub-theme;
      color: $color-background-d;
    }
  }
}
</style>

 
 
