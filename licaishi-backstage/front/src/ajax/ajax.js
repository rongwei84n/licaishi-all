/**
 * Created by hhly-pc on 2017/9/20.
 */
import qs from "qs"
import axios from "axios"

const IP_PORT = {
  // 接口调试
  houtai: "http://47.97.100.240/backstage/v1",
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
    axios({
      withCredentials: true,
      // TOOD:路径前缀
      url: IP_PORT.houtai + url,
      data: qsStatus ? qs.stringify(Object.assign({}, params)) : params,
      headers,
      method,
      timeout: 10000,
    }).then(
      (res) => {
        // 请求成功返回
        if (res.status === 200) {
          resolve(res)
        }
      }
      ,
      (err) => reject(err)
    )
  })
}

export default ajax
