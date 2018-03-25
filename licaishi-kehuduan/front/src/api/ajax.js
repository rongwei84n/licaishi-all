/**
 * Created by hhly-pc on 2017/9/20.
 */
import qs from "qs"
import axios from "axios"

const IP_PORT = {
  // 接口调试
  houtai: "http://47.97.100.240",
}

/**
 * ajax初步封装
 *
 * @param {String} url 请求地址
 * @param {Object} params 请求参数
 * @param {String} method 请求方法
 */
function ajax({
  url,
  params,
  method = "POST",
  qsStatus = true,
  headers = {
    "Content-Type": "application/json; charset=utf-8",
  },
} = {}) {
  return new Promise((resolve, reject) => {
    /**
     * 真机调试
     */
    let methodL = method.toLocaleLowerCase();
    console.log("进入了全局方法");
    window.phihome.util.netRequest(
      methodL,
      IP_PORT.houtai + url,
      headers,
      params = qs.stringify(Object.assign({}, params)),
      function (res) {
        console.log("进入了全局回调");
        // 原生对象包装一层模拟axios返回的对象结构
        const temObj = {
          data: JSON.parse(res),
          status: JSON.parse(res).status
        }
        /* 判断当前是否登录 */
        if (JSON.parse(res).status === 2) {
          window.phihome.app.openPage("lcs.account.login", null, function (
            response
          ) {
            // _this.queryAccountDetail();
          });
        } else if (JSON.parse(res).status === 200) {
          resolve(temObj)
        }
      }
    );
    /**
     * 本地调试
     */
    // axios({
    //   withCredentials: true,
    //   // TOOD:路径前缀
    //   url: IP_PORT.houtai + url,
    //   data: qsStatus ? qs.stringify(Object.assign({}, params)) : params,
    //   headers,
    //   method,
    //   timeout: 10000,
    // }).then(
    //   (res) => {
    //     // 请求成功返回
    //     if (res.status === 200) {
    //       resolve(res)
    //     }
    //   }
    //   ,
    //   (err) => reject(err)
    // )
  })
}

export default ajax
