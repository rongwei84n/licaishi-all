/*
 * @Author: 张浩然 
 * @Date: 2018-03-18 23:00:33 
 * @Last Modified by: 张浩然
 * @Last Modified time: 2018-03-19 00:28:24
 *
 * 此组件用于可视化做富文本编辑
 */

 <template>
  <div id="udit">
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/home' }">
        <i class="el-icon-menu"></i> 首页</el-breadcrumb-item>
      <el-breadcrumb-item>可视化编辑</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 编辑器容器 -->

    <div id="udit-content">
      <el-button type="primary" @click="save">刷新视图</el-button>
      <div class="body">
        <!-- 编辑框区域 -->
        <div id="udit-body"></div>
        <!-- 实时展示页面 -->
        <div id="view" v-html="htmlCode">
        </div>
      </div>
    </div>
  </div>
</template>

<script type="es6">
import "../../../static/utf8-jsp/ueditor.config";
import "../../../static/utf8-jsp/ueditor.parse.js";
import "../../../static/utf8-jsp/ueditor.all";
import "../../../static/utf8-jsp/lang/zh-cn/zh-cn";
// import Event from "../assets/js/bus.js"; //这个是兄弟组件中传输数据的。在本人其他文章中有介绍

export default {
  data() {
    return {
      htmlCode: "",
      editor: null
    };
  },
  mounted() {
    this.init_udit();
  },
  destroyed() {
    // 销毁对象
    this.editor.destroy();
  },
  methods: {
    /**`
     * 初始化富文本编辑器对象
     */
    init_udit() {
      console.log(111);
      this.editor = UE.getEditor("udit-body", {
        BaseUrl: "",
        UEDITOR_HOME_URL: "static/utf8-jsp/",
        toolbars: [
          ["fullscreen", "source", "undo", "redo"],
          [
            "bold",
            "italic",
            "underline",
            "fontborder",
            "strikethrough",
            "superscript",
            "subscript",
            "removeformat",
            "formatmatch",
            "autotypeset",
            "blockquote",
            "pasteplain",
            "|",
            "forecolor",
            "backcolor",
            "insertorderedlist",
            "insertunorderedlist",
            "selectall",
            "cleardoc"
          ]
        ]
      });
      // ue.setHeight(400);
      const uP = uParse("#view", {
        rootPath: "../"
      });
    },
    // 保存编辑
    save() {
      this.htmlCode = this.editor.getContent();
    }
  }
};
</script>

<style lang="scss">
#udit {
  #udit-content {
    margin: 40px 0;
    padding: 20px;
    padding-right: 60px;
    height: 560px;
    background-color: #f9f7f7;
    border-radius: 20px;
    .el-button {
      margin-left: 22px;
      margin-bottom: 12px;
    }
    .body {
      display: flex;
      flex-direction: row;
      justify-content: space-around;
      #udit-body {
        width: 400px;
        height: 400px;
        // flex: 1 !important;
      }
      #view {
        width: 375px;
        height: 468px;
        border: 1px solid red;
      }
    }
  }
}
</style>


 
