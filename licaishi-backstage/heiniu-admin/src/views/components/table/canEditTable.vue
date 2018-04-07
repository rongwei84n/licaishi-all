<style lang="less">
@import "./editable-table.less";
</style>

<template>
  <div>
    <Table :ref="refs" :columns="columnsList" :data="thisTableData" border disabled-hover></Table>
  </div>
</template>

<script>
// 编辑||保存按钮
const editButton = (vm, h, currentRow, index) => {
  return h(
    "Button",
    {
      props: {
        type: currentRow.editting ? "success" : "primary",
        loading: currentRow.saving
      },
      style: {
        margin: "0 5px"
      },
      on: {
        click: () => {
          // //   debugger;
          // //   判断当前是否是编辑状态
          // if (!currentRow.editting) {
          //   //   判断当前行对象有无可设置的单元格
          //   if (currentRow.edittingCell) {
          //     // 遍历对象，将当前行的单元格属性设置为false
          //     // 将table组件的编辑行设为false
          //     //   完成过滤，准且只有一行能处于编辑状态
          //     for (let name in currentRow.edittingCell) {
          //       currentRow.edittingCell[name] = false;
          //       vm.edittingStore[index].edittingCell[name] = false;
          //     }
          //   }
          //   // 将当前行设置成可编辑
          //   vm.edittingStore[index].editting = true;
          //   vm.thisTableData = JSON.parse(JSON.stringify(vm.edittingStore));
          // } else {
          //   vm.edittingStore[index].saving = true;
          //   vm.thisTableData = JSON.parse(JSON.stringify(vm.edittingStore));
          //   let edittingRow = vm.edittingStore[index];
          //   edittingRow.editting = false;
          //   edittingRow.saving = false;
          //   vm.thisTableData = JSON.parse(JSON.stringify(vm.edittingStore));
          //   vm.$emit("input", vm.handleBackdata(vm.thisTableData));
          // vm.$emit("on-change", vm.handleBackdata(vm.thisTableData), index);

          // 触发编辑按钮事件
          vm.$emit("on-change");
          // }
        }
      }
    },
    currentRow.editting ? "保存" : "编辑"
  );
};
// 禁用||启用按钮
const banButton = (vm, h, currentRow, index) => {
  // 当前行-1 禁用  0启用
  // currentRow.status
  return h(
    "Button",
    {
      props: {
        type: currentRow.status === 0 ? "success" : "primary"
      },
      style: {
        margin: "0 5px"
      },
      on: {
        click: () => {
          // 判断当前是什么状态
          //   启用状态
          if (currentRow.status === 0) {
            currentRow.status = -1;
          } else {
            //禁用状态
            currentRow.status = 0;
          }
          vm.$emit("banBtn-click", currentRow.status);
        }
      }
    },
    // 根据状态值返回不同的文本
    currentRow.status === 0 ? "禁用" : "启用"
  );
};
// 删除按钮
const deleteButton = (vm, h, currentRow, index) => {
  return h(
    "Poptip",
    {
      props: {
        confirm: true,
        title: "您确定要删除这条数据吗?",
        transfer: true
      },
      on: {
        "on-ok": () => {
          //   vm.thisTableData.splice(index, 1);
          //   vm.$emit("input", vm.handleBackdata(vm.thisTableData));
          //   vm.$emit("on-delete", vm.handleBackdata(vm.thisTableData), index);
          /**
           *   抛出删除确定按钮事件，传递当前点击的index
           */
          vm.$emit("on-delete", index);
        }
      }
    },
    [
      h(
        "Button",
        {
          style: {
            margin: "0 5px"
          },
          props: {
            type: "error",
            placement: "top"
          }
        },
        "删除"
      )
    ]
  );
};
const incellEditBtn = (vm, h, param) => {
  if (vm.hoverShow) {
    return h(
      "div",
      {
        class: {
          "show-edit-btn": vm.hoverShow
        }
      },
      [
        h("Button", {
          props: {
            type: "text",
            icon: "edit"
          },
          on: {
            click: event => {
              vm.edittingStore[param.index].edittingCell[
                param.column.key
              ] = true;
              vm.thisTableData = JSON.parse(JSON.stringify(vm.edittingStore));
            }
          }
        })
      ]
    );
  } else {
    return h("Button", {
      props: {
        type: "text",
        icon: "edit"
      },
      on: {
        click: event => {
          vm.edittingStore[param.index].edittingCell[param.column.key] = true;
          vm.thisTableData = JSON.parse(JSON.stringify(vm.edittingStore));
        }
      }
    });
  }
};
const saveIncellEditBtn = (vm, h, param) => {
  return h("Button", {
    props: {
      type: "text",
      icon: "checkmark"
    },
    on: {
      click: event => {
        vm.edittingStore[param.index].edittingCell[param.column.key] = false;
        vm.thisTableData = JSON.parse(JSON.stringify(vm.edittingStore));
        vm.$emit("input", vm.handleBackdata(vm.thisTableData));
        vm.$emit(
          "on-cell-change",
          vm.handleBackdata(vm.thisTableData),
          param.index,
          param.column.key
        );
      }
    }
  });
};
// 单元格输入框
const cellInput = (vm, h, param, item) => {
  return h("Input", {
    props: {
      type: "text",
      value: vm.edittingStore[param.index][item.key]
    },
    on: {
      "on-change"(event) {
        let key = item.key;
        vm.edittingStore[param.index][key] = event.target.value;
      }
    }
  });
};
export default {
  name: "canEditTable",
  props: {
    refs: String,
    columnsList: Array,
    value: Array,
    url: String,
    editIncell: {
      type: Boolean,
      default: false
    },
    hoverShow: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      columns: [],
      thisTableData: [],
      edittingStore: []
    };
  },
  created() {
    this.init();
  },
  methods: {
    init() {
      let vm = this;
      let editableCell = this.columnsList.filter(item => {
        if (item.editable) {
          if (item.editable === true) {
            return item;
          }
        }
      });
      let cloneData = JSON.parse(JSON.stringify(this.value));
      let res = [];
      res = cloneData.map((item, index) => {
        let isEditting = false;
        if (this.thisTableData[index]) {
          if (this.thisTableData[index].editting) {
            isEditting = true;
          } else {
            for (const cell in this.thisTableData[index].edittingCell) {
              if (this.thisTableData[index].edittingCell[cell] === true) {
                isEditting = true;
              }
            }
          }
        }
        if (isEditting) {
          return this.thisTableData[index];
        } else {
          this.$set(item, "editting", false);
          let edittingCell = {};
          editableCell.forEach(item => {
            edittingCell[item.key] = false;
          });
          this.$set(item, "edittingCell", edittingCell);
          return item;
        }
      });
      this.thisTableData = res;
      this.edittingStore = JSON.parse(JSON.stringify(this.thisTableData));
      this.columnsList.forEach(item => {
        if (item.editable) {
          item.render = (h, param) => {
            let currentRow = this.thisTableData[param.index];
            if (!currentRow.editting) {
              if (this.editIncell) {
                return h(
                  "Row",
                  {
                    props: {
                      type: "flex",
                      align: "middle",
                      justify: "center"
                    }
                  },
                  [
                    h(
                      "Col",
                      {
                        props: {
                          span: "22"
                        }
                      },
                      [
                        !currentRow.edittingCell[param.column.key]
                          ? h("span", currentRow[item.key])
                          : cellInput(this, h, param, item)
                      ]
                    ),
                    h(
                      "Col",
                      {
                        props: {
                          span: "2"
                        }
                      },
                      [
                        currentRow.edittingCell[param.column.key]
                          ? saveIncellEditBtn(this, h, param)
                          : incellEditBtn(this, h, param)
                      ]
                    )
                  ]
                );
              } else {
                return h("span", currentRow[item.key]);
              }
            } else {
              return h("Input", {
                props: {
                  type: "text",
                  value: currentRow[item.key]
                },
                on: {
                  "on-change"(event) {
                    let key = param.column.key;
                    vm.edittingStore[param.index][key] = event.target.value;
                  }
                }
              });
            }
          };
        }
        if (item.handle) {
          item.render = (h, param) => {
            let currentRowData = this.thisTableData[param.index];
            let children = [];
            item.handle.forEach(item => {
              if (item === "edit") {
                //添加编辑按钮
                children.push(editButton(this, h, currentRowData, param.index));
              } else if (item === "ban") {
                //添加启用禁用状态按钮
                children.push(banButton(this, h, currentRowData, param.index));
              } else if (item === "delete") {
                //添加删除按钮
                children.push(
                  deleteButton(this, h, currentRowData, param.index)
                );
              }
            });
            return h("div", children);
          };
        }
      });
    },
    handleBackdata(data) {
      let clonedData = JSON.parse(JSON.stringify(data));
      clonedData.forEach(item => {
        delete item.editting;
        delete item.edittingCell;
        delete item.saving;
      });
      return clonedData;
    }
  },
  watch: {
    value(data) {
      this.init();
    }
  }
};
</script>
