package com.auts.lcs.model.request;

public class CreateOrderRequestModel {
    private String productId;

    private String customerId;

    private String customerName;

    private String cardId;

    private String amount;

    private String lastPayDate;

    private String comRatio;
    private String proRatio;
    private String issuingBank;
    private String bankCardNo;
    private String note;
    public String getProductId() {
        return productId;
    }
    public void setProductId(String productId) {
        this.productId = productId;
    }
    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public String getCardId() {
        return cardId;
    }
    public void setCardId(String cardId) {
        this.cardId = cardId;
    }
    public String getAmount() {
        return amount;
    }
    public void setAmount(String amount) {
        this.amount = amount;
    }
    public String getLastPayDate() {
        return lastPayDate;
    }
    public void setLastPayDate(String lastPayDate) {
        this.lastPayDate = lastPayDate;
    }
    public String getComRatio() {
        return comRatio;
    }
    public void setComRatio(String comRatio) {
        this.comRatio = comRatio;
    }
    public String getProRatio() {
        return proRatio;
    }
    public void setProRatio(String proRatio) {
        this.proRatio = proRatio;
    }
    public String getIssuingBank() {
        return issuingBank;
    }
    public void setIssuingBank(String issuingBank) {
        this.issuingBank = issuingBank;
    }
    public String getBankCardNo() {
        return bankCardNo;
    }
    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }
    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }
}
