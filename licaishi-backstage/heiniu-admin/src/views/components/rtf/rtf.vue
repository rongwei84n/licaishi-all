<style lang="less">
.rtf-content {
  height: 400px;
  .quill-editor {
    height: 100%;
  }
}
</style>
<template>
  <div class="rtf-content">
    <quill-editor ref="myTextEditor" v-model="content" :config="editorOption" @change="onEditorChange($event)"></quill-editor>
  </div>
</template>

<script>
import { quillEditor } from "vue-quill-editor";
export default {
  data() {
    return {
      content: "",
      editorOption: {}
    };
  },
  methods: {
    onEditorChange(e) {
      this.$emit("onEditorChange", e);
    }
  },
  components: {
    quillEditor
  }
};
</script>
