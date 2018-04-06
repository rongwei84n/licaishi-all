<template>
  <div class="all-content">
    <div v-on:click="gotoDetail">
      <mt-cell :title="cDisplayOrderId">
        <div class="order_status_waitpay" v-if="status==='01'">{{cDisplayStatus}}</div>
        <div class="order_status_waitrebate" v-if="status==='02'">{{cDisplayStatus}}</div>
        <div class="order_status_already_rebate" v-if="status==='03'">{{cDisplayStatus}}</div>
        <div class="order_status_already_failed" v-if="status==='99'">{{cDisplayStatus}}</div>
      </mt-cell>
      <mt-cell :title="cDisplayProdName"></mt-cell>
      <mt-cell>
        <div slot="title" class="title_prodname">{{cDisplayOrderAmount}}</div>
      </mt-cell>
      <mt-cell>
        <div slot="title" class="title_prodname">{{cDisplayRebatePresent}}</div>
      </mt-cell>
      <mt-cell>
        <div slot="title" class="title_prodname">{{cDisplayRebateAmount}}</div>
      </mt-cell>
      <mt-cell :title="cDisplayVoucherStatus"></mt-cell>
      <mt-cell :title="cDisplayCustomerName"></mt-cell>
    </div>
    <mt-cell v-if="status==='01'">
      <div class="item_cancel_order" @click="cancelOrder">取消订单</div>
    </mt-cell>
  </div>
</template>

<script>
import ajax from "api/ajax";
import { MessageBox } from "mint-ui";

export default {
  name: "order-list-item",
  props: {
    index: "", //传入的索引
    orderId: "", //订单
    prodName: "", //产品名称
    orderAmount: "", //订单金额
    rebatePresent: "", //返佣比例
    rebateAmount: "", //返佣金额
    status: "", //订单状态(待打款可以取消订单)
    voucharStatus: "",
    customerName: "", //客户姓名
    selectedOrder: "" //选择的菜单
  },
  computed: {
    cDisplayOrderId() {
      return "订单号: " + this.orderId;
    },
    cDisplayProdName() {
      return "产品名称: " + this.prodName;
    },
    cDisplayOrderAmount() {
      return "订单金额: " + this.orderAmount + "元";
    },
    cDisplayRebatePresent() {
      return "返佣比例: " + this.rebatePresent;
    },
    cDisplayRebateAmount() {
      return "返佣金额: " + this.rebateAmount + "元";
    },
    cDisplayVoucherStatus() {
      //支付状态
      if (this.voucharStatus === "1") {
        return "支付状态: 已支付";
      } else {
        return "支付状态: 未支付";
      }
    },
    cDisplayStatus() {
      //订单状态
      if (this.status === "01") {
        return "待打款";
      } else if (this.status === "02") {
        return "待结佣";
      } else if (this.status === "03") {
        return "已结佣";
      } else if (this.status === "99") {
        return "已失效";
      }
      return this.status;
    },
    cDisplayCustomerName() {
      return "客户姓名: " + this.customerName;
    }
  },

  methods: {
    cancelOrder() {
      MessageBox.confirm("是否取消订单?").then(action => {
        ajax({
          url: `/srv/v1/order/cancelOrder?orderNo=${this.orderId}`,
          method: "post"
        }).then(res => {
          if (res.status === 200) {
            // 抛出事件
            this.$emit("order-cancel", this.index);
          }
        });
      });
    },
    gotoDetail() {
      window.phihome.app.openPage(
        "lcs.order.orderdetail",
        this.orderId,
        function(response) {}
      );
    }
  }
};
</script>

<style lang="stylus" rel="stylesheet/stylus">
.all-content {
  .title_prodname {
    font-size: 14px;
    color: #e10101;
  }

  .order_status_waitpay {
    font-size: 14px;
    color: #e10101;
  }

  .order_status_waitrebate {
    color: #00C5E9;
    font-size: 14px;
  }

  .order_status_already_rebate {
    color: #08B200;
    font-size: 14px;
  }

  .order_status_already_failed {
    color: #666666;
    font-size: 14px;
  }

  .item_cancel_order {
    color: #212121;
    font-size: 14px;
  }
}
</style>
