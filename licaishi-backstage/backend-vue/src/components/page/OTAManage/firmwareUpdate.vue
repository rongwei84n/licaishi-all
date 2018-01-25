<template>
  <div class="alldata">
    <div class="search">
      <span>产品名称</span>
      <el-input
        placeholder="请输入产品名称"
        v-model="inputProuduct">
      </el-input>
      <span>型号</span>
      <el-input
        placeholder="请输入型号"
        v-model="version">
      </el-input>
      <span>新版本号</span>
      <el-input
        placeholder="请输入新版本号"
        v-model="newVersion">
      </el-input>
      <span>升级方式</span>
      <el-select v-model="upgrad">
        <el-option
          v-for="item in updateType"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>
      <el-button type="primary" @click="searchTable">搜索</el-button>
    </div>
    <div class="add-data">
      <el-button type="primary" @click="addData" :disabled="noAuthority">+添加固件升级</el-button>
    </div>
    <el-table
      :data="tableData"
      border
      :header-row-style="headerStyle"
      v-loading="loading"
      element-loading-text="拼命加载中"
      max-height="500"
      style="width: 100%">
      <el-table-column
        prop="product_area_name"
        label="产品名称"
        fixed="left"
        align="center"
        width="150">
      </el-table-column>
      <el-table-column
        v-for="item in items"
        :key="item.id"
        :prop="item.value"
        :label="item.name"
        align="center"
        :width="item.width">
      </el-table-column>
      <el-table-column
        label="MAC地址"
        align="center"
        width="150">
        <template scope="scope">
          <el-tooltip placement="top">
            <div slot="content">{{scope.row.mac_addr_list}}</div>
            <div style="max-height: 90px">{{scope.row.mac_addr_list}}</div>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column
        v-for="item in itemLast"
        :key="item.id"
        :prop="item.value"
        :label="item.name"
        align="center"
        :width="item.width">
      </el-table-column>
      <el-table-column
        fixed="right"
        label="操作"
        align="center"
        width="70">
        <template scope="scope">
          <el-button @click="handleEdit(scope.row)" type="text" size="small" :disabled="noAuthority">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="page-data">
      <div class="block">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage4"
          :page-sizes="[10, 20, 30, 50]"
          :page-size="size"
          layout="total, sizes, prev, pager, next, jumper"
          :total="totalDatas">
        </el-pagination>
      </div>
    </div>
    <div class="dia-data">
      <el-dialog width="570px" :close-on-click-modal="false" :visible.sync="dialogFormVisible">
        <el-form ref="form" :model="form">
          <el-form-item label="产品名称" :label-width="formLabelWidth">
            <el-select :disabled="addDisabled" v-model="form.product_area_id" placeholder="">
              <el-option
                v-for="item in areas"
                :key="item.product_area_id"
                :label="item.product_area_name"
                :value="item.product_area_id">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="型号" :label-width="formLabelWidth">
            <el-select :disabled="addDisabled" v-model="form.product_line_id" placeholder="">
              <el-option
                v-for="item in lines"
                :key="item.product_line_id"
                :label="item.product_line_name"
                :value="item.product_line_id">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="硬件版本" :label-width="formLabelWidth">
            <el-select :disabled="addDisabled" v-model="form.hw_ver" placeholder="">
              <el-option
                v-for="item in hardWareVers"
                :key="item.str_value"
                :label="item.str_value"
                :value="item.str_value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="新版本号" :label-width="formLabelWidth">
            <el-select :disabled="addDisabled" v-model="form.fw_ver" placeholder="">
              <el-option
                v-for="item in newVers"
                :key="item.str_value"
                :label="item.str_value"
                :value="item.str_value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="升级方式" :label-width="formLabelWidth">
            <el-select v-model="form.update_type">
              <el-option
                v-for="item in updateType"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="支持版本" :label-width="formLabelWidth">
            <el-select v-model="form.support_ver" @change="supporverChange" multiple placeholder="可多选">
              <el-option
                v-for="item in supVers"
                :key="item.str_id"
                :label="item.str_value"
                :value="item.str_id"
                :disabled="item.disabled">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="地区" :label-width="formLabelWidth">
            <el-select v-model="form.citys" @change="cityChange" multiple placeholder="可多选">
              <el-option
                v-for="item in citys"
                :key="item.city_id"
                :label="item.city_name"
                :value="item.city_id"
                :disabled="item.disabled">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="设备数量" :label-width="formLabelWidth">
            <el-select v-model="form.gray_target" placeholder="">
              <el-option
                v-for="item in devNumber"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="状态" :label-width="formLabelWidth">
            <el-select v-model="form.active_status" placeholder="">
              <el-option
                v-for="item in devStatus"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="用户ID" :label-width="formLabelWidth">
            <el-input
              placeholder="根据手机号获取用户ID"
              v-model="form.white_list"
              :disabled="true">
            </el-input>
            <el-button type="primary" icon="el-icon-edit" :disabled="disableId" @click="openUserId"></el-button>
            <el-checkbox  v-model="checked" @change="openUserAllId">全部用户</el-checkbox>
          </el-form-item>
          <el-form-item label="MAC地址" :label-width="formLabelWidth">
            <el-select @change="macChange" v-model="form.mac" placeholder="">
              <el-option
                v-for="item in macData"
                :key="item.str_id"
                :label="item.str_value"
                :value="item.str_id">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item v-show="macShow" label="" :label-width="formLabelWidth">
            <el-input
              placeholder="请输入MAC地址"
              v-model="form.macSingle">
            </el-input>
            <span style="display:block;color:red">注：MAC地址使用英文逗号分开，例如：DC:A9:04:85:57:45,DC:A9:04:85:57:49</span>
          </el-form-item>
          <el-form-item v-show="macsShow" label="" :label-width="formLabelWidth">
            <div class="macAll">
              <el-input
                placeholder="请输入MAC起始地址"
                v-model="form.macOne">
              </el-input>
              <span>-</span>
              <el-input
                placeholder="请输入MAC结束地址"
                v-model="form.macTwo">
              </el-input>
            </div>
          </el-form-item>
          <el-form-item v-show="uploadShow" label="" :label-width="formLabelWidth">
            <el-upload
              class="upload-demo"
              ref="upload"
              drag
              :data="dataParam"
              :action= "actionUpload()"
              :file-list="fileList"
              :on-change="handleChange"
              :on-success="successdata"
              :on-error="errorData"
              :auto-upload="false">
              <i class="el-icon-upload"></i>
              <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
            </el-upload>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="formCancel()">取 消</el-button>
          <el-button type="primary" @click="formConfirm()">确 定</el-button>
        </div>
      </el-dialog>
      <el-dialog width="570px" title="添加用户ID" :close-on-click-modal="false" :visible.sync="dialogFormVisibleId">
        <el-form :model="formId">
          <el-form-item label="用户手机号" :label-width="formLabelWidth">
            <el-input v-model="formId.phoneNumber" auto-complete="off"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="cancelId">取 消</el-button>
          <el-button type="primary" @click="confirmId">确 定</el-button>
        </div>
      </el-dialog>

    </div>
  </div>
</template>

<script>
  import commonHandle from "../../common/common";
  let hostName = location.host;
  let protoType = location.protocol;

  export default {
    data () {
      return {
        noAuthority: true,
        inputProuduct: "",
        version: "",
        newVersion: "",
        upgrad: "",
        totalDatas: 0,
        currentPage4: 1,
        size: 10,
        loading: true,
        editType: "add",
        addDisabled: false,
        dialogFormVisible: false,
        dialogFormVisibleId: false,
        disableId: false,
        checked: false,
        macShow: false,
        macsShow: false,
        uploadShow: false,
        formLabelWidth: "120px",
        headerStyle: {
          "background-color": "#eef1f6",
          "color": "#1f2d3d"
        },
        // 表格表头
        items: [
          {"name": "型号", "width": "150", "value": "product_line_name"},
          {"name": "硬件版本号", "width": "150", "value": "hw_ver"},
          {"name": "新版本号", "width": "150", "value": "fw_ver"},
          {"name": "升级方式", "width": "150", "value": "update_type_name"},
          {"name": "支持版本", "width": "150", "value": "show_support_ver"},
          {"name": "地区", "width": "150", "value": "citysZh"},
          {"name": "设备数量", "width": "150", "value": "grayName"},
          {"name": "用户ID", "width": "150", "value": "show_white_list"}
        ],
        itemLast: [
          {"name": "状态", "width": "150", "value": "show_active_status"},
          {"name": "操作时间", "width": "200", "value": "op_time_str"},
          {"name": "操作人", "width": "150", "value": "operator"}
        ],
        // 表格显示数据
        form: {
          "product_area_id": "",
          "product_line_id": "",
          "hw_ver": "",
          "fw_ver": "",
          "update_type": "",
          "support_ver": [],
          "citys": [],
          "gray_target": "",
          "active_status": "",
          "white_list": "",
          "mac": "",
          "macSingle": "",
          "macOne": "",
          "macTwo": ""
        },
        // 用户id
        formId: {
          phoneNumber: ""
        },
        tableData: [],
        fileList: [],
        dataParam: {},
        areas: [],
        lines: [],
        macData: [],
        hardWareVers: [],
        newVers: [],
        supVers: [],
        citys: [],
        // 升级方式
        updateType: [
          /* {
            value: "2",
            label: "APP初始化升级-半强制"
          },
          {
            value: "1",
            label: "APP初始化升级-强制"
          }, */
          {
            value: "5",
            label: "APP手动升级-普通"
          },
          {
            value: "3",
            label: "APP手动升级-强制"
          },
          {
            value: "4",
            label: "APP手动升级-半强制"
          },
          {
            value: "0",
            label: "静默升级"
          }
        ],
        // 设备数量
        devNumber: [
          {
            value: -1,
            label: "全部"
          },
          {
            value: 100,
            label: "100"
          },
          {
            value: 500,
            label: "500"
          },
          {
            value: 1000,
            label: "1000"
          },
          {
            value: 5000,
            label: "5000"
          },
          {
            value: 10000,
            label: "10000"
          }
        ],
        devStatus: [
          {
            value: 0,
            label: "反激活"
          },
          {
            value: 1,
            label: "激活"
          }
        ]
      };
    },
    created () {
      // 检查页面权限
      commonHandle.checkMenu("/ota/sys/check_sys_user_page_auth", this, this.$route.path);
      let fn = function (response, self) {
        // 返回处理数据并赋值给表格绑定数据
        self.tableData = commonHandle.transformFirmwareUpdateData(response);
        self.totalDatas = response.data.result.totalCount;
        self.loading = false;
      };
      let param = {
        product_area_name: encodeURIComponent(commonHandle.trim(this.inputProuduct)),
        product_line_name: encodeURIComponent(commonHandle.trim(this.version)),
        fw_ver: encodeURIComponent(commonHandle.trim(this.newVersion)),
        update_type: this.upgrad,
        page_size: 10,
        cur_page: 1
      };
      // 查询固件升级表格
      commonHandle.requestPost("/ota/query_upgrade_policy_list", this, param, fn);
    },
    methods: {
      addData: function () {
        // 添加固件升级
        this.form = {
          "product_area_id": "",
          "product_line_id": "",
          "hw_ver": "",
          "fw_ver": "",
          "update_type": "",
          "support_ver": [],
          "citys": [],
          "gray_target": "",
          "active_status": 1,
          "white_list": "",
          "mac": "",
          "macSingle": "",
          "macOne": "",
          "macTwo": ""
        };
        this.editType = "add";
        this.addDisabled = false;
        this.checked = false;
        this.disableId = false;
        this.macShow = false;
        this.macsShow = false;
        this.uploadShow = false;
        let fn = function (response, self) {
          self.areas = response.data.result.areas;
          self.macData = response.data.result.macTypes;
          self.lines = response.data.result.lines;
          self.hardWareVers = response.data.result.hardWareVers;
          self.newVers = response.data.result.newVers;
          self.supVers = commonHandle.FirmwareUpateDataJson(response.data.result.supVers);
          self.citys = commonHandle.FirmwareUpateDataJson(response.data.result.citys);
        };
        // 添加固件升级下拉框选择数据查询
        commonHandle.requestPost("/ota/query_upgrade_firmware_info", this, {}, fn);
        this.dialogFormVisible = true;
      },
      searchTable: function () {
        commonHandle.queryFirmwareUpateData("", "", this, commonHandle);
      },
      handleEdit: function (row) {
        // 编辑表格行数据
        this.dialogFormVisible = true;
        this.addDisabled = true;
        this.editType = "edit";
        let cityAll = [];
        for (let i = 0; i < row.resCitys.length; i++) {
          cityAll.push(row.resCitys[i].city_id);
        }
        this.checked = row.white_list === "-1";
        this.disableId = row.white_list === "-1";
        // mac地址
        let macOne = "";
        let macTwo = "";
        let macSingle = "";
        console.log(row.mac_addr_list);
        if (row.mac_type === 2) {
          macOne = row.mac_addr_list.split("-")[0];
          macTwo = row.mac_addr_list.split("-")[1];
        } else if (row.mac_type === 1) {
          macSingle = row.mac_addr_list;
        }

        // 编辑是下拉框数据自动显示
        this.form = {
          "product_area_id": row.product_area_id,
          "product_line_id": row.product_line_id,
          "hw_ver": row.hw_ver,
          "fw_ver": row.fw_ver,
          "update_type": row.update_type,
          "support_ver": row.support_ver.split(","),
          "citys": cityAll,
          "gray_target": row.gray_target,
          "active_status": row.active_status,
          "white_list": row.white_list,
          "mac": row.mac_type,
          "macSingle": macSingle,
          "macOne": macOne,
          "macTwo": macTwo
        };
        this.macChange(row.mac_type);
        let fn = function (response, self) {
          self.areas = response.data.result.areas;
          self.macData = response.data.result.macTypes;
          self.lines = response.data.result.lines;
          self.hardWareVers = response.data.result.hardWareVers;
          self.newVers = response.data.result.newVers;
          self.supVers = commonHandle.FirmwareUpateDataJson(response.data.result.supVers);
          self.citys = commonHandle.FirmwareUpateDataJson(response.data.result.citys);
          self.supporverChange(self.form.support_ver);
          self.cityChange(self.form.citys);
        };
        // 添加固件升级下拉框选择数据查询
        commonHandle.requestPost("/ota/query_upgrade_firmware_info", this, {}, fn);
        console.log(row);
      },
      handleSizeChange (val) {
        commonHandle.queryFirmwareUpateData("size", val, this, commonHandle);
      },
      handleCurrentChange (val) {
        commonHandle.queryFirmwareUpateData("page", val, this, commonHandle);
      },
      formConfirm () {
        let areaId = this.form.product_area_id;
        let lineId = this.form.product_line_id;
        let hwVer = this.form.hw_ver;
        let fwVer = this.form.fw_ver;
        let updateType = this.form.update_type;
        let supportVer = this.form.support_ver;
        let citys = this.form.citys;
        let grayQuantity = this.form.gray_target;
        let whiteList = this.form.white_list;
        let macType = this.form.mac;
        let macAddrList = "";
        if (macType === 2) {
          macAddrList = this.form.macOne + "," + this.form.macTwo;
        } else if (macType === 1) {
          macAddrList = this.form.macSingle;
        }
        let activeStatus = this.form.active_status;
        // 校验下拉框数据是否填写完整，如果没有填写完整弹出提示
        if (!macType || !areaId || !lineId || !hwVer || !fwVer || updateType.length <= 0 || supportVer.length <= 0 || citys.length <= 0 || !grayQuantity || activeStatus === "" || !whiteList) {
          this.$message.error("请把内容填写完整");
          return;
        }
        let temp = /^([a-fA-F0-9]{2}:){5}[a-fA-F0-9]{2}$/;
        if (macType === 2) {
          if (!temp.test(this.form.macOne) || !temp.test(this.form.macTwo)) {
            this.$message.error("请填写正确的MAC地址");
            return;
          }
        } else if (macType === 1) {
          if (this.form.macSingle.length && this.form.macSingle.length > 0) {
            let datas = this.form.macSingle.split(",");
            for (let i = 0; i < datas.length; i++) {
              if (!temp.test(datas[i])) {
                this.$message.error("请填写正确的MAC地址");
                return;
              }
            }
          } else {
            this.$message.error("请填写正确的MAC地址");
            return;
          }
        }
        this.dataParam = {};
        this.dataParam.product_area_id = areaId;
        this.dataParam.product_line_id = lineId;
        this.dataParam.hw_ver = hwVer;
        this.dataParam.fw_ver = fwVer;
        this.dataParam.update_type = updateType;
        this.dataParam.support_ver = supportVer.join();
        this.dataParam.citys = citys.join();
        this.dataParam.gray_target = grayQuantity;
        this.dataParam.active_status = activeStatus;
        this.dataParam.white_list = whiteList;
        this.dataParam.mac_type = macType;
        this.dataParam.mac_addr_list = macAddrList;
        this.dataParam.userName = localStorage.getItem("userName");
        this.dataParam.lastOptUid = localStorage.getItem("uid");
        this.dataParam.token = localStorage.getItem("token");
        this.dataParam.real_name = localStorage.getItem("real_name");
        if (this.editType === "edit") {
          this.dataParam.is_update = 1;
        }
        let fn = function (response, self) {
          self.$message({
            message: "操作成功",
            type: "success"
          });
          commonHandle.queryFirmwareUpateData("", "", self, commonHandle);
        };
        commonHandle.requestPost("/ota/add_upgrade_policy", this, this.dataParam, fn);
        this.dialogFormVisible = false;
      },
      formCancel () {
        this.dialogFormVisible = false;
      },
      openUserId () {
        this.formId.phoneNumber = "";
        this.dialogFormVisibleId = true;
      },
      openUserAllId () {
        if (this.checked) {
          this.disableId = true;
          this.form.white_list = "-1";
        } else {
          this.disableId = false;
          this.form.white_list = "";
        }
      },
      cancelId () {
        this.dialogFormVisibleId = false;
      },
      confirmId () {
        let number = commonHandle.trim(this.formId.phoneNumber);
        let listType = this.form.white_list.slice(this.form.white_list.length - 1, this.form.white_list.length);
        let reg = /^1[0-9]{10}$/;
        let flag = reg.test(number);
        // 校验手机号的正确性
        if (!number || !flag) {
          this.$message.error("请填写正确的手机号");
          return;
        }
        let param = {"phonenumber": this.formId.phoneNumber};
        let fn = function (response, self) {
          if (listType === ",") {
            let List = self.form.white_list.substring(0, self.form.white_list.length - 1);
            let arrayList = List.split(",");
            let arraylength = arrayList.length;
            console.log(List);
            console.log(arrayList);
            console.log(arraylength);
            console.log(number);
            for (let i = 0; i < arraylength; i++) {
              if (response.data.result.extend_msg === arrayList[i]) {
                self.$message.error("该用户ID已经存在,请重新输入");
                return;
              }
            }
          }
          self.form.white_list = self.form.white_list + response.data.result.extend_msg + ",";
          self.dialogFormVisibleId = false;
        };
        commonHandle.requestGet("/ota/query_phi_account", this, param, fn);
      },
      supporverChange (val) {
        // 如果选择全部，则其他选项的置灰
        let value = val.join() || this.form.support_ver.join();
        if (value) {
          if (value.indexOf("-1") >= 0) {
            for (let i = 0; i < this.supVers.length; i++) {
              if (this.supVers[i].str_id !== "-1") {
                this.supVers[i].disabled = true;
              }
            }
          } else {
             for (let i = 0; i < this.supVers.length; i++) {
              if (this.supVers[i].str_id === "-1") {
                this.supVers[i].disabled = true;
              }
            }
          }
        } else {
          for (let i = 0; i < this.supVers.length; i++) {
            this.supVers[i].disabled = false;
          }
        }
      },
      cityChange (val) {
        // 如果选择全部，则其他选项的置灰
        let value = val.join() || this.form.citys.join();
        if (value) {
          if (value.indexOf(-1) >= 0) {
            for (let i = 0; i < this.citys.length; i++) {
              if (this.citys[i].city_id !== -1) {
                this.citys[i].disabled = true;
              }
            }
          } else {
            for (let i = 0; i < this.citys.length; i++) {
              if (this.citys[i].city_id === -1) {
                this.citys[i].disabled = true;
              }
            }
          }
        } else {
          for (let i = 0; i < this.citys.length; i++) {
            this.citys[i].disabled = false;
          }
        }
      },
      macChange (val) {
        var _this = this;
        switch (val) {
          case -1:
            _this.macShow = false;
            _this.macsShow = false;
            _this.uploadShow = false;
            break;
          case 1:
            _this.macShow = true;
            _this.macsShow = false;
            _this.uploadShow = false;
            break;
          case 2:
            _this.macShow = false;
            _this.macsShow = true;
            _this.uploadShow = false;
            break;
          default:
            _this.macShow = false;
            _this.macsShow = false;
            _this.uploadShow = true;
        };
      },
      actionUpload () {
        if (this.editType === "add") {
          return protoType + "//" + hostName + "/ota/add_ver_relation";
        } else {
          return protoType + "//" + hostName + "/ota/update_firmware_pkg";
        }
      },
      handleChange (file, fileList) {
        let length = this.$refs.upload.uploadFiles.length;
        if (length > 1) {
          this.$refs.upload.uploadFiles.shift();
        }
        console.log(this.$refs.upload.uploadFiles);
      },
      successdata (response, file, fileList) {
        let fn = function (response, self) {
          self.$message({
            message: "操作成功",
            type: "success"
          });
          commonHandle.queryFirmwarePage("", "", self, commonHandle);
        };
        if (this.$refs.upload && this.$refs.upload.uploadFiles !== null && this.$refs.upload.uploadFiles !== undefined) {
          this.$refs.upload.uploadFiles.shift();
        }
        commonHandle.requestOK(response, this, fn);
        this.fullscreenLoading = false;
        this.fullscreenapkLoading = false;
      },
      errorData (err, file, fileList) {
        if (this.$refs.upload && this.$refs.upload.uploadFiles !== null && this.$refs.upload.uploadFiles !== undefined) {
          this.$refs.upload.uploadFiles.shift();
        }
        commonHandle.requestErr(err, this);
        this.fullscreenLoading = false;
        this.fullscreenapkLoading = false;
      }
    }
  };
</script>

<style lang="less">
  @import "../../less/firmware";
</style>
