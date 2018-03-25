/*
 * @Author: 张浩然 
 * @Date: 2018-03-11 21:21:45 
 * @Last Modified by: 张浩然
 * @Last Modified time: 2018-03-11 21:33:05
 * 
 * 枚举文件，主要用于存储产品状态
 */

/* 付息方式 */
const pPaymentInterestType = {
    "01": "按月付息",
    "02": "按季付息",
    "03": "按半年付息",
    "04": "按年付息",
    "05": "到期付本息",
}

/* 大小配比 */
const pSizeRatioType = {
    "01": "小额畅打",
    "02": "已配出小额",
    "03": "严格配比",
    "04": "全大额"
}

/* 产品类型 */
const pType = {
    "01": "集合信托",
    "02": "集合资管",
    "03": "债权基金",
    "04": "股权基金",
    "05": "阳光私募",
}

/* 投资领域 */
const pInvestType = {
    "01": "房地产类",
    "02": "金融市场",
    "03": "基础设施",
    "04": "资金池",
    "05": "工商企业",
    "99": "其他",
}

export { pPaymentInterestType, pSizeRatioType, pType, pInvestType }