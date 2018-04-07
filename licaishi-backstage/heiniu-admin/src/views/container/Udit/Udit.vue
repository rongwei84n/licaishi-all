/*
 * @Author: 张浩然 
 * @Date: 2018-03-18 23:00:33 
 * @Last Modified by: zhanghr
 * @Last Modified time: 2018-03-26 18:36:45
 *
 * 此组件用于可视化做富文本编辑
 */

 <template>
  <div id="udit">
    <Breadcrumb>
      <BreadcrumbItem to="/home">首页</BreadcrumbItem>
      <BreadcrumbItem to="/Udit">富文本编辑</BreadcrumbItem>
    </Breadcrumb>
    <!-- 编辑器容器 -->
    <div id="udit-content">
      <div class="body">
        <div class="body-btn-content">
          <Button id="copyBtn" type="success" @click="copy"  data-clipboard-text="">复制</Button>
          <Button type="info" @click="previewModal=true">预览</Button>
          <span>提示：
            <span style="color:red;"> 在下方编辑后点击【预览】能看到最终排版</span>
          </span>
        </div>
        <!-- 编辑框区域 -->
        <rtf @onEditorChange="onEditorChange"></rtf>
      </div>
    </div>
    <Modal v-model="previewModal" title="预览">
      <div class="ql-container ql-snow">
        <div class="ql-editor" v-html="htmlCode">
        </div>
      </div>
    </Modal>
  </div>
</template>

<script type="es6">
import rtf from "@/views/components/rtf/rtf.vue";
import Clipboard from "clipboard";

export default {
  data() {
    return {
      htmlCode: "",
      previewModal: false
    };
  },
  methods: {
    onEditorChange(e) {
      this.htmlCode = e.html;
    },
    copy() {
      document
        .querySelector("#copyBtn")
        .setAttribute("data-clipboard-text", this.htmlCode);
      const clipboard = new Clipboard("#copyBtn");
    }
  },
  components: {
    rtf
  }
};
</script>

<style lang="less">
#udit {
  width: 100%;
  height: 100%;
  padding: 20px;
  &-content {
    margin: 40px 0;
    padding: 20px;
    height: 560px;
    background-color: #f9f7f7;
    border-radius: 20px;
    .el-button {
      margin-left: 22px;
      margin-bottom: 12px;
    }
    .body {
      height: 400px;
      &-btn-content {
        margin-bottom: 10px;
        button {
          margin-right: 10px;
        }
      }
    }
  }
}
</style>


 
