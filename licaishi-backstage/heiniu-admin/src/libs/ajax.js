import qs from "qs";
import axios from "axios";

const IP_PORT = {
  // 接口调试
  houtai: "http://47.97.100.240/backstage/v1"
  // 'houtai': 'http://127.0.0.1:7074/v1',
};

const IMG_IP_PORT = {
  houtai: "http://47.97.100.240/backstage/v1"
};
/**
 * 请求图片服务器例子
 */
/**
 * this.$ajax({
 *  url,
 *  params,
 *  imgServer:true,
 * }).then(res=>{
 *  回调函数
 * })
 *
 */

/**
 * ajax初步封装
 *
 * @param {String} url 请求地址
 * @param {Object} params 请求参数
 * @param {String} method 请求方法
 * @param {String} qsStatus 配合headers参数使用，如果是json模式则都不需要传，如果是表单提交传false
 * @param {String} headers 请求方法
 */
function ajax({
  url,
  params,
  method = "post",
  qsStatus = true,
  headers = {
    "Content-Type": "application/json; charset=utf-8"
    // 'Content-Type': 'application/x-www-form-urlencoded'
  },
  imgServer = false
} = {}) {
  return new Promise((resolve, reject) => {
    axios({
      withCredentials: true,
      // TOOD:路径前缀
      url: imgServer ? IMG_IP_PORT.houtai_url + url : IP_PORT.houtai + url,
      data: qsStatus ? qs.stringify(Object.assign({}, params)) : params,
      headers,
      method,
      timeout: 10000
    }).then(
      res => {
        resolve(res);
        // 请求成功返回
        // if (res.data.status === 200) {

        // } else {

        // }
        // TODO:其他状态枚举外面过滤
      },
      err => reject(err)
    );
  });
}

export default ajax;
