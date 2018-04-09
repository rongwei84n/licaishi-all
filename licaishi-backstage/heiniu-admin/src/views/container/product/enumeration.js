/*
 * @Author: 张浩然 
 * @Date: 2018-03-24 16:12:59 
 * @Last Modified by: 张浩然
 * @Last Modified time: 2018-03-25 20:57:09
 * 
 * 产品属性相关的枚举字段
 */

/**
 * 产品类型枚举
 */
export const pTypeList = [{
    key: '00',
    value: '不限'
}, {
    key: '01',
    value: '集合信托'
}, {
    key: '02',
    value: '集合资管'
}, {
    key: '03',
    value: '债权基金'
}, {
    key: '04',
    value: '私募股权'
}]

/**
 * 产品销售状态
 */
export const pSaleStatusList = [{
    key: '01',
    value: '预热中'
}, {
    key: '02',
    value: '募集中'
}, {
    key: '03',
    value: '募集结束'
}]

/**
 * 投资领域
 */
export const pInvestTypeList = [
{
        key: '00',
        value: '其他'
},
{
    key: '01',
    value: '房地产类'
}, {
    key: '02',
    value: '金融市场'
}, {
    key: '03',
    value: '基础设施'
}, {
    key: '04',
    value: '资金池'
}, {
    key: '05',
    value: '工商企业'
}]

/**
 * 付息方式
 */
export const pPaymentInterestTypeList = [{
    key: '01',
    value: '按月付息'
}, {
    key: '02',
    value: '按季付息'
}, {
    key: '03',
    value: '按半年付息'
}, {
    key: '04',
    value: '按年付息'
}, {
    key: '05',
    value: '到期付本息'
}]

/**
 * 大小配比类型
 */
export const pSizeRatioType = [{
    key: '01',
    value: '小额畅打'
}, {
    key: '02',
    value: '已配出小额'
}, {
    key: '03',
    value: '严格配比'
}, {
    key: '04',
    value: '全大额'
}]