/*
 * @Author: 张浩然 
 * @Date: 2018-03-12 22:08:09 
 * @Last Modified by: 张浩然
 * @Last Modified time: 2018-03-12 22:09:13
 * 
 * 身份证验证函数
 */


/**
 * 身份证验证函数
 * 
 * @param {Number} card 
 */
function isCardNo(card) {
  // 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X 
  const reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
  if (reg.test(card) === false) {
    return false;
  } else {
    return true;
  }
}

export { isCardNo }