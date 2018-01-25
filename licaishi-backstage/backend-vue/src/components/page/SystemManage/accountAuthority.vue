
<template>
  <div class="authorityWrap">
    <div class="text">
      <el-table
        :data="tableData"
        border
        :header-row-style="headerStyle"
        v-loading="loading"
        element-loading-text="拼命加载中"
        max-height="600"
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
  </div>
</template>
<script>
  import commonHandle from "../../common/common";
  export default {
    data () {
      return {
        type: "",
        name: "",
        email: "",
        role: "",
        id: "",
        currentPage: 1,
        size: 10,
        total: 0,
        loading: true,
        formLabelWidth: "150px",
        defaultProps: {
          children: "children",
          label: "label"
        },
        dialogFormVisible: false,
        form: {
          type: "",
          region: "",
          operate: "",
          detail: ""
        },
        regionData: [],
        operateData: [],
        detailData: [],
        typeData: [],
        tableList: [
          {
            value: "classifyName",
            name: "分类"
          }, {
            value: "descrb",
            name: "权限"
          }, {
            value: "optTime",
            name: "操作时间"
          }],
        tableData: [],
        headerStyle: {
          "background-color": "#eef1f6",
          "color": "#1f2d3d"
        }
      };
    },
    computed: {
      param () {
        return {
          pageSize: this.size,
          curPage: this.currentPage
        };
      }
    },
    created () {
      // 检查页面权限
      commonHandle.checkMenu("/ota/sys/check_sys_user_page_auth", this, this.$route.path);
      this.queryTable();
    },
    methods: {
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
          self.tableData = response.data.result.sysAuthoritys;
          self.total = response.data.result.totalCount;
          self.loading = false;
        };
        commonHandle.requestPost("/ota/sys/query_sys_authority_info_list", this, this.param, fnTable);
      },
      formConfirm () {
        // 确认添加或编辑权限
        let data = this.regionData.length;
        if (data > 0) {
          if (!this.form.type || !this.form.region || !this.form.detail || !this.form.operate) {
            this.$message.error("请把内容填写完整");
            return;
          }
        } else {
          if (!this.form.type || !this.form.detail || !this.form.operate) {
            this.$message.error("请把内容填写完整");
            return;
          }
        }
        let params = {
          classify: this.form.type,
          secondMenuId: this.form.region,
          detail: this.form.detail,
          optType: this.form.operate,
          id: this.id
        };
        let url = "";
        if (this.editType === "add") {
          url = "/ota/sys/add_authority";
        } else {
          url = "/ota/sys/edit_authority";
        }
        let fn = function (response, self) {
          self.queryTable();
        };
        commonHandle.requestPost(url, this, params, fn);
        this.dialogFormVisible = false;
      },
      formCancel () {
        this.dialogFormVisible = false;
      }
    }
  };
</script>
<style lang="less">
  .authorityWrap{
    min-width: 1280px;
    max-width: 1500px;
    .search{
      margin-bottom: 30px;
    }
    .el-input{
      max-width: 300px;
    }
    .el-input__inner{
      max-width: 300px;
    }
    .el-checkbox-group{
      margin-left: 40px;
    }
    i{
      margin-right: 10px;
    }
    .page-data{
      text-align: center;
      padding: 70px 0;
    }
  }
</style>
