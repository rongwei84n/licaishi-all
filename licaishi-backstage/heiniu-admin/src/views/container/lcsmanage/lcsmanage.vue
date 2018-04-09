<style lang="less">
@import "./lcsmanage.less";
</style>

<template>
  <div class="lcsmanage">
    <template>
      <Breadcrumb>
        <BreadcrumbItem to="/home">首页</BreadcrumbItem>
        <BreadcrumbItem to="/lcsmanage">理财师管理</BreadcrumbItem>
      </Breadcrumb>
    </template>
    <div class="lcsmanage-body">
      <div class="lcsmanage-body-header">
        <div>
          <Input v-model="nameSearch" placeholder="请输入搜索理财师姓名" style="width: 300px"></Input>
          <Button type="primary" icon="ios-search" @click="handleSearch">检索</Button>
        </div>
        <div>
          <Button type="primary" icon="plus" @click="handleAdd">新增</Button>
        </div>
      </div>
      <div class="lcsmanage-body-body">
        <edit-table refs="table2" v-model="dataList" @on-delete="handleDelete" @on-change="handleChange" @banBtn-click="handleBan" :columns-list="editInlineColumns"></edit-table>
        <div class="center-content">
          <Page :total="total" :pageSize='pageSize' :current='pageNumber' @on-change="handlePageChange"></Page>
        </div>
      </div>
    </div>
    <!-- 理财师模态框 -->
    <Modal v-model="lcsModalVisible" :title="titleModal" @on-ok="handleSubmit('lcsModalValidate')">
      <Form ref="lcsModalValidate" :model="lcsModalValidate" :rule="ruleValidate" :label-width="40">
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
        <FormItem label="电子邮箱" prop="email" :label-width="80">
          <Input v-model="lcsModalValidate.email" placeholder="请输入电子邮箱"></Input>
        </FormItem>
        <!-- <FormItem label="年龄" prop="age">
          <Input v-model="lcsModalValidate.age" placeholder="请输入年龄"></Input>
        </FormItem> -->
        <FormItem label="生日" prop="birthday">
          <DatePicker type="date" v-model="lcsModalValidate.birthday" placeholder="请选择生日" transfer></DatePicker>
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
      nameSearch: "", //理财师姓名
      dataList: [], //理财师列表
      editInlineColumns: [], //table组件的配置文件
      /**
       * 翻页数据
       */
      total: 0, //总记录数
      pageSize: 10, //每页数据条数,
      pageNumber: 1, //当前第几页,
      /**
       * 模态框
       */
      ModalStaus: [true, false], //模态框状态枚举，0：新增，1：编辑
      titleModal: "", //模态框标题
      lcsModalVisible: false, //模态框状态
      //模态框数据对象
      lcsModalValidate: {
        name: "",
        phone: "",
        sex: -1,
        age: 0,
        birthday: "",
        address: "",
        email: ""
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
  },
  methods: {
    //   获取理财师列表
    handleSearch() {
      this.$ajax({
        url: `/financer/financerlist?nameSearch=${this.nameSearch}&pageNumber=${
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
     * 分页器插件change事件
     * @param currentPage  分页器返回的当前页数
     */
    handlePageChange(currentPage) {
      this.pageNumber = currentPage;
      this.handleSearch();
    },
    /**
     * TODO:此接口应该手动传状态参，这样设计不健壮
     *  改变当前理财师状态
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
          this.$ajax({
            url: "/financer/handleswitch",
            params: {
              uid: currObj.uid
            },
            headers: {
              "Content-Type": "application/x-www-form-urlencoded"
            }
          }).then(res => {
            console.log(res);
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
     * 删除当前理财师
     */
    handleDelete(index) {
      this.$ajax({
        url: `/financer/delfinancer?uid=${this.dataList[index].uid}`
      }).then(res => {
        if (res.data.status == 200) {
          console.log(res);
          this.handleSearch();
        }
      });
    },
    /**
     * 显示编辑页面
     */
    handleChange(index) {
      this.ModalStaus = Array.from({ length: 2 }, () => false);
      this.ModalStaus[1] = true;

      const currObj = this.dataList[index];
      this.titleModal = "编辑理财师";
      this.lcsModalVisible = true;
      this.lcsModalValidate = {
        name: currObj.name,
        phone: currObj.phone,
        sex: currObj.sex,
        birthday: currObj.birthday,
        address: currObj.address,
        email: currObj.email,
        uid: currObj.uid
      };
    },
    //显示新增界面
    handleAdd() {
      this.ModalStaus = Array.from({ length: 2 }, () => false);
      this.ModalStaus[0] = true;
      this.titleModal = "新增理财师";
      this.lcsModalVisible = true;
      this.lcsModalValidate = {
        name: "",
        phone: "",
        sex: -1,
        birthday: "",
        address: "",
        email: ""
      };
    },
    handleSubmit(name) {
      this.lcsModalValidate.birthday = this.$moment(
        this.lcsModalValidate.birthday
      ).format("YYYY-MM-DD");
      this.$refs[name].validate(valid => {
        if (valid) {
          if (this.ModalStaus[0]) {
            this.addLcs();
          } else if (this.ModalStaus[1]) {
            this.editLcs();
          }
        } else {
          this.$Message.error("参数有误");
        }
      });
    },
    // 编辑理财师
    editLcs() {
      this.$ajax({
        url: "/financer/editfinancer",
        params: this.lcsModalValidate,
        headers: {
          "Content-Type": " application/x-www-form-urlencoded"
        }
      }).then(res => {
        console.log(res);
        if (res.data.status === 200) {
          this.$Message.success("编辑成功!");
          this.handleSearch();
        }
      });
    },
    // 新增理财师
    addLcs() {
      this.$ajax({
        url: "/financer/addfinancer",
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

