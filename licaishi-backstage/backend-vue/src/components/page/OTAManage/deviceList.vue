<template>
  <div class="listWrap">
    <div class="search">
      <span class="name">产品名称</span>
      <el-select @change="querySelectName" v-model="name">
        <el-option
          v-for="item in nameData"
          :key="item.product_area_id"
          :label="item.product_area_name"
          :value="item.product_area_id">
        </el-option>
      </el-select>
      <span class="name">型号</span>
      <el-select @change="querySelectType" v-model="type">
        <el-option
          v-for="item in typeData"
          :key="item.product_line_id"
          :label="item.product_line_name"
          :value="item.product_line_id">
        </el-option>
      </el-select>
      <span class="name">硬件版本号</span>
      <el-select @change="querySelectHw" v-model="hardwareVersion">
        <el-option
          v-for="item in hardWareData"
          :key="item.hwver_id"
          :label="item.hwver_val"
          :value="item.hwver_id">
        </el-option>
      </el-select>
      <span class="name">产品料号</span>
      <el-select v-model="number">
        <el-option
          v-for="item in numberData"
          :key="item.part_number"
          :label="item.part_number_val"
          :value="item.part_number">
        </el-option>
      </el-select>
      <el-button type="primary" @click="searchTable">搜索</el-button>
    </div>
    <div class="add">
      <el-button type="primary" @click="addData" :disabled="noAuthority" v-popover:popover1>+添加设备</el-button>
    </div>
    <div class="text">
      <el-table
        :data="tableData"
        border
        :header-row-style="headerStyle"
        v-loading="loading"
        element-loading-text="拼命加载中"
        max-height="500"
        style="width: 100%">
        <el-table-column label="序号" width="80px" type="index" align="center">
        </el-table-column>
        <el-table-column
          :prop="item.value"
          v-for="item in tableList"
          :key="item.value"
          align="center"
          :label="item.name">
        </el-table-column>
        <el-table-column width="100" align="center" label="操作">
          <template scope="scope">
            <el-button
              size="small"
              type="text"
              :disabled="noAuthority"
              @click="handleEdit(scope.$index, scope.row, tableData)">编辑</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="page-data">
      <div class="block">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="[10, 20, 30, 50]"
          :page-size="size"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
        </el-pagination>
      </div>
    </div>
    <div class="import-data">
      <el-dialog
        title=""
        :visible.sync="importVisible"
        width="570px"
        :close-on-click-modal="false">
        <el-form ref="form" :model="form" label-width="100px">
          <el-form-item label="产品料号">
            <el-input :disabled="editDisabled" v-model="form.number"></el-input>
          </el-form-item>
          <el-form-item label="产品名称">
            <el-input :disabled="editDisabled" v-model="form.name"></el-input>
          </el-form-item>
          <el-form-item label="型号">
            <el-input :disabled="editDisabled" v-model="form.type"></el-input>
          </el-form-item>
            <el-form-item label="硬件版本号">
              <el-input :disabled="editDisabled" v-model="form.hardwareVersion"></el-input>
            </el-form-item>
            <el-form-item label="备注">
              <el-input
                type="textarea"
                :rows="3"
                placeholder="请输入内容"
                v-model="form.textarea">
              </el-input>
            </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="formCancel">取 消</el-button>
          <el-button type="primary" @click="formConfirm">确 定</el-button>
        </span>
      </el-dialog>
    </div>
  </div>
</template>
<script>
  import commonHandle from "../../common/common";

  export default {
    data () {
      return {
        noAuthority: true,
        type: -1,
        id: "",
        number: "-1",
        name: -1,
        editType: "",
        hardwareVersion: "-1",
        currentPage: 1,
        size: 10,
        total: 0,
        editDisabled: false,
        loading: true,
        importVisible: false,
        hardWareData: [],
        numberData: [],
        nameData: [],
        typeData: [],
        tableList: [
          {
            value: "part_number",
            name: "产品料号",
            width: "200"
          }, {
            value: "product_area_name",
            name: "产品名称",
            width: "200"
          }, {
            value: "product_line_name",
            name: "型号",
            width: "200"
          }, {
            value: "hwver",
            name: "硬件版本号",
            width: "290"
          }, {
            value: "fwver",
            name: "支持固件版本号",
            width: "290"
          }, {
            value: "remarks",
            name: "备注",
            width: "290"
          }],
        tableData: [],
        form: {
          number: "",
          name: "",
          type: "",
          hardwareVersion: "",
          textarea: ""
        },
        headerStyle: {
          "background-color": "#eef1f6",
          "color": "#1f2d3d"
        }
      };
    },
    computed: {
      param () {
        return {
          hwver: this.hardwareVersion,
          part_number: this.number,
          product_area_id: this.name,
          product_line_id: this.type,
          page_size: this.size,
          cur_page: this.currentPage
        };
      },
      selectParam () {
        return {
          product_area_id: this.name,
          product_line_id: this.type,
          hwver_id: this.hardwareVersion
        };
      }
    },
    created () {
      // 检查页面权限
      commonHandle.checkMenu("/ota/sys/check_sys_user_page_auth", this, this.$route.path);
      this.querySelect();
    },
    methods: {
      searchTable () {
        this.queryTable();
      },
      addData () {
        this.editType = "add";
        this.editDisabled = false;
        this.id = "";
        this.form = {
          number: "",
          name: "",
          type: "",
          hardwareVersion: "",
          textarea: ""
        };
        this.importVisible = true;
      },
      handleEdit (index, row, tableData) {
        // 添加固件编辑
        this.editType = "edit";
        this.editDisabled = true;
        this.id = row.id;
        this.form = {
          number: row.part_number,
          name: row.product_area_name,
          type: row.product_line_name,
          hardwareVersion: row.hwver,
          textarea: row.remarks
        };
        this.importVisible = true;
      },
      handleDelete (index, row, tableData) {
        this.$confirm("此操作将永久删除, 是否继续?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          let params = {
            id: row.id
          };
          let fn = function (response, self) {
            self.$message({
              type: "success",
              message: "删除成功!"
            });
            self.queryTable();
          };
          commonHandle.requestPost("/ota/devicemgr/device_list/delete_device", this, params, fn);
        }).catch(() => {
        });
      },
      handleSizeChange (val) {
        this.size = val;
        this.queryTable();
      },
      handleCurrentChange (val) {
        this.currentPage = val;
        this.queryTable();
      },
      queryTable () {
        // 查询表格信息
        let fnTable = function (response, self) {
          self.tableData = response.data.result.products;
          self.total = response.data.result.total_count;
          self.loading = false;
        };
        commonHandle.requestPost("/ota/devicemgr/device_list/query_device_list/" + this.currentPage + "/" + this.size, this, this.param, fnTable);
      },
      querySelect () {
        let fn = function (response, self) {
          self.hardWareData = response.data.result.hwvers;
          self.numberData = response.data.result.part_numbers;
          self.nameData = response.data.result.areas;
          self.typeData = response.data.result.lines;
          self.queryTable();
        };
        // 查询下拉框信息
        commonHandle.requestPost("/ota/devicemgr/device_list/query_device_all_type", this, this.selectParam, fn);
      },
      querySelectName () {
        this.type = -1;
        this.number = "-1";
        this.hardwareVersion = "-1";
        let fn = function (response, self) {
          self.hardWareData = response.data.result.hwvers;
          self.numberData = response.data.result.part_numbers;
          self.typeData = response.data.result.lines;
        };
        // 查询下拉框信息
        commonHandle.requestPost("/ota/devicemgr/device_list/query_device_all_type", this, this.selectParam, fn);
      },
      querySelectType (val) {
        this.number = "-1";
        this.hardwareVersion = "-1";
        let fn = function (response, self) {
          self.hardWareData = response.data.result.hwvers;
          self.numberData = response.data.result.part_numbers;
        };
        // 查询下拉框信息
        commonHandle.requestPost("/ota/devicemgr/device_list/query_device_all_type", this, this.selectParam, fn);
      },
      querySelectHw () {
        this.number = "-1";
        let fn = function (response, self) {
          self.numberData = response.data.result.part_numbers;
        };
        // 查询下拉框信息
        commonHandle.requestPost("/ota/devicemgr/device_list/query_device_all_type", this, this.selectParam, fn);
      },
      formCancel () {
        this.importVisible = false;
      },
      formConfirm () {
        let number = this.form.number;
        let name = this.form.name;
        let type = this.form.type;
        let hardwareVersion = this.form.hardwareVersion;
        let textarea = this.form.textarea;
        if ((!name || !type || !number) && this.editType === "add") {
          this.$message.error("请把内容填写完整");
          return;
        }
        let params = {
          "id": this.id,
          "part_number": number,
          "product_area_name": name,
          "product_line_name": type,
          "hwver": hardwareVersion,
          "remarks": textarea
        };
        let fnTable = function (response, self) {
          self.$message({
            type: "success",
            message: "操作成功!"
          });
          self.queryTable();
        };
        let url = "";
        if (this.editType === "add") {
          url = "/ota/devicemgr/device_list/add_device";
        } else if (this.editType === "edit") {
          url = "/ota/devicemgr/device_list/edit_device";
        }
        commonHandle.requestPost(url, this, params, fnTable);
        this.importVisible = false;
      }
    }
  };
</script>
<style lang="less">
  .listWrap{
    min-width: 1280px;
    max-width: 1500px;
    .search{
      margin-bottom: 50px;
      .name{
        padding: 0 10px 0 15px;
      }
      button{
        margin-left: 20px;
      }
      .el_type, .el-select{
        width: 12%;
      }
    }
    .import-data .el-input__inner{
      width: 300px;
    }
    .add{
      margin-bottom: 10px;
    }
    .page-data{
      text-align: center;
      padding: 70px 0;
    }
  }
</style>
