<script>
  let hostName = location.host;
  let protoType = location.protocol;
  export default {
    // axios post方法
    requestPost (url, self, params, fn) {
      params.userName = localStorage.getItem("userName");
      params.real_name = localStorage.getItem("real_name");
      params.lastOptUid = localStorage.getItem("uid");
      params.token = localStorage.getItem("token");
      self.$axios({
        method: "post",
        url: protoType + "//" + hostName + url,
        data: params
      }).then(function (response) {
        if (response.data.err_code !== 200) {
          self.$message.error(response.data.err_msg);
        } else if (response.data.result.ret_code !== 200) {
          self.$message.error(response.data.result.ret_msg);
        } else {
          // 如果有function，运行function
          if (fn) {
            fn(response, self);
          }
        }
      }).catch(function (err) {
        if (err) {
          console.log(err);
        }
        self.$message.error("操作失败");
      });
    },
    // axios get方法，和post方法不一样，get参数为params,post参数为data
    requestGet (url, self, params, fn) {
      params.userName = localStorage.getItem("userName");
      params.real_name = localStorage.getItem("real_name");
      params.lastOptUid = localStorage.getItem("uid");
      params.token = localStorage.getItem("token");
      self.$axios({
        method: "get",
        url: protoType + "//" + hostName + url,
        params: params
      }).then(function (response) {
        if (response.data.err_code !== 200) {
          self.$message.error(response.data.err_msg);
        } else if (response.data.result.ret_code !== 200) {
          self.$message.error(response.data.result.ret_msg);
        } else {
          // 如果有function，运行function
          if (fn) {
            fn(response, self);
          }
          console.log(response);
        }
      }).catch(function (err) {
        if (err) {
          console.log(err);
        }
        self.$message.error("操作失败");
      });
    },
    // 请求成功数据处理
    requestOK (response, self, fn) {
      if (response.err_code !== 200) {
        self.$message.error(response.err_msg);
      } else if (response.result.ret_code !== 200) {
        self.$message.error(response.result.ret_msg);
      } else {
          if (fn) {
              fn(response, self);
          }
        console.log(response);
      }
    },
    // 请求失败数据处理
    requestErr (err, self) {
      if (err) {
        console.log(err);
      }
      self.$message.error("数据返回异常");
    },
    // 格式化日期 例如2017-09-15 12:30:30
    getHoursStrings (date) {
      if (date) {
        let y = date.getFullYear();
        let m = date.getMonth() + 1;
        m = m < 10 ? ("0" + m) : m;
        let d = date.getDate();
        d = d < 10 ? ("0" + d) : d;
        let h = date.getHours();
        h = h < 10 ? ("0" + h) : h;
        let minute = date.getMinutes();
        minute = minute < 10 ? ("0" + minute) : minute;
        let seconds = date.getSeconds();
        seconds = seconds < 10 ? ("0" + seconds) : seconds;

        return y + "-" + m + "-" + d + " " + h + ":" + minute + ":" + seconds;
      } else {
        return "";
      }
    },
    // 格式化日期 例如2017/09/15 12:30:30
    getHoursString (date) {
      if (date) {
        let y = date.getFullYear();
        let m = date.getMonth() + 1;
        m = m < 10 ? ("0" + m) : m;
        let d = date.getDate();
        d = d < 10 ? ("0" + d) : d;
        let h = date.getHours();
        h = h < 10 ? ("0" + h) : h;
        let minute = date.getMinutes();
        minute = minute < 10 ? ("0" + minute) : minute;
        let seconds = date.getSeconds();
        seconds = seconds < 10 ? ("0" + seconds) : seconds;

        return y + "/" + m + "/" + d + " " + h + ":" + minute + ":" + seconds;
      } else {
        return "";
      }
    },
    getDateStrings (date) {
      if (date) {
        let y = date.getFullYear();
        let m = date.getMonth() + 1;
        m = m < 10 ? ("0" + m) : m;
        let d = date.getDate();
        d = d < 10 ? ("0" + d) : d;

        return y + "/" + m + "/" + d;
      } else {
        return "";
      }
    },
    // 去掉字符串首尾空格
    trim (s) {
      return String(s).replace(/(^\s*)|(\s*$)/g, "");
    },
    checkLogout (url, self) {
      // 校验用户身份是否过期
      self.$axios({
        method: "post",
        url: protoType + "//" + hostName + url,
        data: {
          userName: localStorage.getItem("userName"),
          real_name: localStorage.getItem("real_name"),
          token: localStorage.getItem("token"),
          lastOptUid: localStorage.getItem("uid")
        }
      }).then(function (response) {
        if (response.data.err_code !== 200) {
          localStorage.clear();
          localStorage.setItem("errorMsg", response.data.err_msg);
          self.$router.push("/login");
        } else if (response.data.result.ret_code !== 200) {
          localStorage.clear();
          localStorage.setItem("errorMsg", response.data.result.ret_msg);
          self.$router.push("/login");
        }
      }).catch(function (err) {
        if (err) {
          console.log(err);
        }
        localStorage.clear();
        self.$router.push("/login");
      });
    },
    checkMenu (url, self, path) {
      // 校验页面编辑权限
      self.$axios({
        method: "post",
        url: protoType + "//" + hostName + url,
        data: {
          userName: localStorage.getItem("userName"),
          real_name: localStorage.getItem("real_name"),
          token: localStorage.getItem("token"),
          lastOptUid: localStorage.getItem("uid"),
          pageUrl: path
        }
      }).then(function (response) {
        if (response.data.err_code !== 200) {
          localStorage.clear();
          self.$message.error(response.data.err_msg);
          self.$router.push("/login");
        } else if (response.data.result.ret_code !== 200) {
          localStorage.clear();
          self.$message.error(response.data.result.ret_msg);
          self.$router.push("/login");
        } else if (response.data.result.pageAuth === 1) {
          // 1为不可编辑可以查看
          self.noAuthority = true;
        } else if (response.data.result.pageAuth === 2) {
          // 2为可编辑
          self.noAuthority = false;
        } else {
          localStorage.clear();
          localStorage.setItem("errorMsg", "无权限查看，请重新登录！");
          self.$router.push("/login");
        }
      }).catch(function (err) {
        if (err) {
          console.log(err);
        }
        localStorage.clear();
        self.$router.push("/login");
      });
    },
    // 校验图片元素宽度和高度
    checkImg (img) {
      if (img) {
        let nWidth = img.naturalWidth;
        let nHeight = img.naturalHeight;
        return [nWidth, nHeight];
      }
    },
    // 固件升级查询表格数据处理
    transformFirmwareUpdateData (response) {
      let upgradeLength = response.data.result.upgradePolicys.length;
      let citysZh = "";
      let rescitysLength = 0;
      let updatetype = "";
      let updatetypeLength = [];
      let grayName = "";
      for (let i = 0; i < upgradeLength; i++) {
        rescitysLength = response.data.result.upgradePolicys[i].resCitys.length;
        citysZh = "";
        updatetypeLength = response.data.result.upgradePolicys[i].update_type.split(",");
        updatetype = "";
        // 格式化设备数量
        if (response.data.result.upgradePolicys[i].gray_target === -1) {
          grayName = "全部";
        } else {
          grayName = response.data.result.upgradePolicys[i].gray_target;
        }
        // 格式化mac地址
        if (response.data.result.upgradePolicys[i].mac_type === -1) {
          response.data.result.upgradePolicys[i].mac_addr_list = "全部";
        } else if (response.data.result.upgradePolicys[i].mac_type === 2) {
          response.data.result.upgradePolicys[i].mac_addr_list = response.data.result.upgradePolicys[i].mac_addr_list.split(",").join("-");
        }
        // 格式化城市
        for (let j = 0; j < rescitysLength; j++) {
          citysZh += response.data.result.upgradePolicys[i].resCitys[j].city_name + ",";
        }
        // 格式化升级方式
        for (let j = 0; j < updatetypeLength.length; j++) {
          if (updatetypeLength[j] === "0") {
            updatetype += "静默升级" + ",";
          } else if (updatetypeLength[j] === "1") {
            updatetype += "APP初始化升级-强制" + ",";
          } else if (updatetypeLength[j] === "2") {
            updatetype += "APP初始化升级-半强制" + ",";
          } else if (updatetypeLength[j] === "3") {
            updatetype += "APP手动升级-强制" + ",";
          } else if (updatetypeLength[j] === "4") {
            updatetype += "APP手动升级-半强制" + ",";
          } else if (updatetypeLength[j] === "5") {
            updatetype += "APP手动升级-普通" + ",";
          }
        }
        response.data.result.upgradePolicys[i].grayName = grayName;
        response.data.result.upgradePolicys[i].citysZh = citysZh.slice(0, citysZh.length - 1);
        response.data.result.upgradePolicys[i].update_type_name = updatetype.slice(0, updatetype.length - 1);
      }
      return response.data.result.upgradePolicys;
    },
    queryFirmwarePage (type, val, _this, commonHandleData) {
      _this.loading = true;
      let size = _this.size;
      let page = _this.currentPage4;
      if (type === "size" && val) {
          size = val;
          _this.size = val;
      } else if (type === "page" && val) {
          page = val;
           _this.currentPage4 = val;
      }
      console.log(_this.currentPage4);
      let fn = function (response, self) {
        self.tableData = response.data.result.firmwareVersions;
        self.totalDatas = response.data.result.totalCount;
        self.loading = false;
      };
      let param = {
        product_area_name: encodeURIComponent(commonHandleData.trim(_this.inputProuduct)),
        product_line_name: encodeURIComponent(commonHandleData.trim(_this.version)),
        fw_ver: encodeURIComponent(commonHandleData.trim(_this.newVersion)),
        page_size: size,
        cur_page: page
      };
      commonHandleData.requestPost("/ota/query_firmware_version_list", _this, param, fn);
    },
    queryFirmwareUpateData (type, val, _this, commonHandleData) {
      _this.loading = true;
      let size = _this.size;
      let page = _this.currentPage4;
      if (type === "size" && val) {
        size = val;
        _this.size = val;
      } else if (type === "page" && val) {
        page = val;
        _this.currentPage4 = val;
      }
      let fn = function (response, self) {
        // 返回处理数据并赋值给表格绑定数据
        self.tableData = commonHandleData.transformFirmwareUpdateData(response);
        self.totalDatas = response.data.result.totalCount;
        self.loading = false;
      };
      let param = {
        product_area_name: encodeURIComponent(commonHandleData.trim(_this.inputProuduct)),
        product_line_name: encodeURIComponent(commonHandleData.trim(_this.version)),
        fw_ver: encodeURIComponent(commonHandleData.trim(_this.newVersion)),
        update_type: _this.upgrad,
        page_size: size,
        cur_page: page
      };
      // 查询固件升级表格
      commonHandleData.requestPost("/ota/query_upgrade_policy_list", _this, param, fn);
  },
    FirmwareUpateDataJson (data) {
      let datas = data;
      for (let i = 0; i < datas.length; i++) {
        datas[i].disabled = false;
      }
      return datas;
    }
  };
</script>
