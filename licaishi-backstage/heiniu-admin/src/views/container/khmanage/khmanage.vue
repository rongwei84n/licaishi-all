<style lang="less">
@import "./khmanage.less";
</style>

<template>
  <div class="khmanage">
    <template>
      <Breadcrumb>
        <BreadcrumbItem to="/home">首页</BreadcrumbItem>
        <BreadcrumbItem to="/main/khmanage">客户管理</BreadcrumbItem>
      </Breadcrumb>
    </template>
    <div class="khmanage-body">
      <div class="khmanage-body-header">
        <div>
          <Input v-model="nameSearch" placeholder="请输入搜索客户姓名" style="width: 300px"></Input>
          <Button type="primary" icon="ios-search" @click="handleSearch">检索</Button>
        </div>
        <div>
          <Button type="primary" icon="plus" @click="handleAdd">新增</Button>
        </div>
      </div>
      <div class="khmanage-body-body">
        <edit-table refs="table2" v-model="dataList" @on-delete="handleDelete" @on-change="handleChange" @banBtn-click="handleBan" :columns-list="editInlineColumns"></edit-table>
      </div>
    </div>
    <!-- 客户模态框 -->
    <Modal v-model="lcsModalVisible" :title="titleModal" @on-ok="handleSubmit('lcsModalValidate')">
      <Form ref="lcsModalValidate" :model="lcsModalValidate" :rule="ruleValidate" :label-width="60">
        <FormItem label="姓名" prop="name">
          <Input v-model="lcsModalValidate.name" placeholder="请输入姓名"></Input>
        </FormItem>
        <FormItem label="电话" prop="phone">
          <Input v-model="lcsModalValidate.phone" :maxlength="11" placeholder="请输入电话号码"></Input>
        </FormItem>
        <FormItem label="性别" prop="sex">
          <Select v-model="lcsModalValidate.sex" placeholder="请选择性别" transfer>
            <Option value="1">男</Option>
            <Option value="0">女</Option>
          </Select>
        </FormItem>
        <FormItem label="生日" prop="birthday">
          <DatePicker type="date" v-model="lcsModalValidate.birthday" placeholder="请选择生日" transfer></DatePicker>
        </FormItem>
        <FormItem label="电子邮箱" prop="email">
          <Input v-model="lcsModalValidate.email" placeholder="请输入电子邮箱"></Input>
        </FormItem>
        <FormItem label="理财师">
          <!--edit-table列表中financerId是理财师的uid，financer是理财师的姓名-->
          <!--此下拉框中value值为理财师uid，label为理财师姓名-->
          <!-- <Select v-model="lcsModalValidate.financer" filterable remote :remote-method="selectLcs" placeholder="请输入理财师" :loading="loadinglcs"> -->
          <Select v-model="lcsModalValidate.financerId" filterable placeholder="请输入理财师" transfer placement='top'>
            <Option v-for="(option, index) in optionsLcs" :value="option.uid" :key="index">{{option.value}}</Option>
          </Select>
        </FormItem>
        <FormItem label="地址" prop="address">
          <Input v-model="lcsModalValidate.address" type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="请输入现在居住地址"></Input>
        </FormItem>
      </Form>
    </Modal>
  </div>
</template>

<script>
import moment from "moment";
import EditTable from "./table/EditTable";
import { editInlineColumns } from "./tableConf";
export default {
  components: {
    EditTable
  },
  data() {
    return {
      nameSearch: "", //客户姓名
      dataList: [], //客户列表
      editInlineColumns: [], //table组件的配置文件
      /**
       * 翻页数据
       */
      total: 0, //总记录数
      pageSize: 20, //每页数据条数,
      pageNumber: 1, //当前第几页,
      /**
       * 模态框
       */
      ModalStaus: [true, false], //模态框状态枚举，0：新增，1：编辑
      titleModal: "", //模态框标题
      lcsModalVisible: false, //模态框状态

      loadinglcs: false,
      optionsLcs: [], //理财师列表

      //模态框数据对象
      lcsModalValidate: {
        name: "",
        phone: "",
        sex: -1,
        age: 0,
        birthday: "",
        address: "",
        email: "",
        financer: "",
        financerId: 0,
        uid: 0
      },
      // 表单验证规则对象
      ruleValidate: {
        name: [
          {
            required: true,
            message: "请输入姓名",
            trigger: "blur"
          }
        ],
        phone: [
          {
            required: true,
            message: "请输入正确的电话号码",
            trigger: "change"
          }
        ],
        email: [
          {
            message: "请输入电子邮箱",
            trigger: "blur"
          }
        ],
        sex: [{ message: "请选择性别", trigger: "change" }],
        age: [
          {
            type: "number",
            message: "请输入年龄",
            trigger: "change"
          }
        ],
        birthday: [
          {
            type: "string",
            message: "请选择您的生日",
            trigger: "change"
          }
        ],
        address: [
          {
            type: "string",
            message: "请输入您的地址",
            trigger: "blur"
          }
        ]
      }
    };
  },
  created() {
    this.editInlineColumns = editInlineColumns;
    this.handleSearch();
    this.handleLscList();
  },
  methods: {
    //   获取理财师列表
    handleLscList() {
      this.$ajax({
        url: `/financer/queryfinaasync`,
        method: "get"
      }).then(res => {
        if (res.data.status == 200) {
          this.optionsLcs = res.data.result;
        }
      });
    },
    // selectLcs(query) {
    //   if (query !== "") {
    //     this.loadinglcs = true;
    //     setTimeout(() => {
    //       this.loadinglcs = false;
    //       const list = this.allLcs.map(item => {
    //         return {
    //           value: item.uid,
    //           label: item.value
    //         };
    //       });
    //       this.optionsLcs = list.filter(
    //         item => item.label.toLowerCase().indexOf(query.toLowerCase()) > -1
    //       );
    //     }, 200);
    //   } else {
    //     this.optionsLcs = [];
    //   }
    // },
    //   获取客户列表
    handleSearch() {
      this.$ajax({
        url: `/customer/customerlist?nameSearch=${this.nameSearch}&pageNumber=${
          this.pageNumber
        }&pageSize=${this.pageSize}`,
        method: "get"
      }).then(res => {
        if (res.data.status == 200) {
          this.total = res.data.result.total;
          this.pageSize = res.data.result.pageSize;
          this.pageNumber = res.data.result.pageNumber;
          this.dataList = res.data.result.dataList;
          res.data.result.dataList.map(item => {
            item.sex = `${item.sex}`;
            item.sexStr = item.sex === "1" ? "男" : "女";
            item.statusStr = item.status === 0 ? "启用" : "禁用";
          });
        }
      });
    },
    /**
     *  改变当前客户状态
     */
    handleBan(index) {
      const currObj = this.dataList[index];
      this.$Modal.confirm({
        title: "提示",
        content:
          currObj.status === 0
            ? "您确定要禁用该用户吗？"
            : "您确定要启用该用户吗？",
        onOk: () => {
          // TODO:更换地址与参数，
          this.$ajax({
            url: "/customer/handleswitch",
            params: {
              uid: currObj.uid
            },
            headers: {
              "Content-Type": "application/x-www-form-urlencoded"
            }
          }).then(res => {
            if (res.data.status && currObj.status === 0) {
              this.$Message.info("禁用成功");
            } else {
              this.$Message.info("启用成功");
            }
            this.handleSearch();
          });
        }
      });
    },
    /**
     * 删除当前客户
     */
    handleDelete(index) {
      this.$ajax({
        url: "/customer/delcustomer",
        params: {
          uid: this.dataList[index].uid
        },
        headers: {
          "Content-Type": "application/x-www-form-urlencoded"
        }
      }).then(res => {
        this.handleSearch();
      });
    },
    /**
     * 显示编辑页面
     */
    handleChange(index) {
      this.ModalStaus = Array.from({ length: 2 }, () => false);
      this.ModalStaus[1] = true;

      const currObj = this.dataList[index];
      this.titleModal = "编辑客户";
      this.lcsModalVisible = true;

      this.lcsModalValidate = {
        name: currObj.name,
        phone: currObj.phone,
        sex: currObj.sex,
        birthday: currObj.birthday,
        address: currObj.address,
        email: currObj.email,
        financer: currObj.financer,
        financerId: currObj.financerId,
        uid: currObj.uid
      };
      console.log(this.lcsModalValidate);
    },
    //显示新增界面
    handleAdd() {
      this.ModalStaus = Array.from({ length: 2 }, () => false);
      this.ModalStaus[0] = true;
      this.titleModal = "新增客户";
      this.lcsModalVisible = true;
      this.lcsModalValidate = {
        name: "",
        phone: "",
        sex: "",
        birthday: "",
        address: "",
        email: "",
        financerId: ""
      };
    },
    handleSubmit(name) {
      this.lcsModalValidate.birthday = this.$moment(
        this.lcsModalValidate.birthday
      ).format("YYYY-MM-DD");
      this.$refs[name].validate(valid => {
        if (valid) {
          if (this.ModalStaus[0]) {
            this.addKH();
          } else if (this.ModalStaus[1]) {
            this.editKH();
          }
        } else {
          this.$Message.error("参数有误");
        }
      });
    },
    // 编辑客户
    editKH() {
      this.$ajax({
        url: "/customer/editcustomer",
        params: this.lcsModalValidate,
        headers: {
          "Content-Type": " application/x-www-form-urlencoded"
        }
      }).then(res => {
        if (res.data.status === 200) {
          this.$Message.success("编辑成功!");
          this.handleSearch();
        }
      });
    },
    // 新增客户
    // TODO:此处加入的理财师id是否正确？
    addKH() {
      this.$ajax({
        url: "/customer/addcustomer",
        params: this.lcsModalValidate,
        headers: {
          "Content-Type": " application/x-www-form-urlencoded"
        }
      }).then(res => {
        if (res.data.status === 200) {
          this.$Message.success("新增成功!");
          this.handleSearch();
        }
      });
    }
  }
};
</script>

<style>

</style>

