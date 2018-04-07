package com.auts.lcscli.bean.litebean;

/**
 * Created by huangrongwei on 2018/3/17.
 */

public class OrderItemDetailBean {

    private int status;

    private String message;

    private Result result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public static class Result {
        private String orderNO; //订单ID

        private String amount; //订单金额

        private String productShortName; //产品名称

        private String area; //投资领域

        private String comRatio; //返佣比例

        private String commission; //返佣金额

        private String proRatio;//预期收益率

        private String profit; //客户收益

        private String contractStatus; //合同状态

        private String productStatus; //产品发行状态

        private String voucher_status; //支付状态 支付凭证上传状态 0否 1是

        private String voucher_path; //打款凭证

        private String customerName; //客户姓名

        private String customerPhone; //客户手机号码

        private String tip; //备注

        private String status; //订单状态 01-待打款 02-待结佣  03-已结佣 99-已失效

        public String getOrderNO() {
            return orderNO;
        }

        public void setOrderNO(String orderNO) {
            this.orderNO = orderNO;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getProductShortName() {
            return productShortName;
        }

        public void setProductShortName(String productShortName) {
            this.productShortName = productShortName;
        }

        public String getProfit() {
            return profit;
        }

        public void setProfit(String profit) {
            this.profit = profit;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getComRatio() {
            return comRatio;
        }

        public void setComRatio(String comRatio) {
            this.comRatio = comRatio;
        }

        public String getCommission() {
            return commission;
        }

        public void setCommission(String commission) {
            this.commission = commission;
        }

        public String getProRatio() {
            return proRatio;
        }

        public void setProRatio(String proRatio) {
            this.proRatio = proRatio;
        }

        public String getContractStatus() {
            return contractStatus;
        }

        public void setContractStatus(String contractStatus) {
            this.contractStatus = contractStatus;
        }

        public String getProductStatus() {
            return productStatus;
        }

        public void setProductStatus(String productStatus) {
            this.productStatus = productStatus;
        }

        public String getVoucher_status() {
            return voucher_status;
        }

        public void setVoucher_status(String voucher_status) {
            this.voucher_status = voucher_status;
        }

        public String getVoucher_path() {
            return voucher_path;
        }

        public void setVoucher_path(String voucher_path) {
            this.voucher_path = voucher_path;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getCustomerPhone() {
            return customerPhone;
        }

        public void setCustomerPhone(String customerPhone) {
            this.customerPhone = customerPhone;
        }

        public String getTip() {
            return tip;
        }

        public void setTip(String tip) {
            this.tip = tip;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }


}
