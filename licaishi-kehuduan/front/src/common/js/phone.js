/*
 * @Author: 张浩然 
 * @Date: 2018-04-07 16:03:57 
 * @Last Modified by: 张浩然
 * @Last Modified time: 2018-04-07 16:17:29
 * 
 * 验证手机验证码
 */


function isPhone(phone) {
  const regStr = /^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/
  if (regStr.test(phone) === false) {
    return false;
  } else {
    return true;
  }
}
export {
  isPhone
}