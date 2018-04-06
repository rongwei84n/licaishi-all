<style lang="less">
@import "./EditTable.less";
</style>

<template>
  <div>
    <Table :ref="refs" :columns="columnsList" :data="thisTableData" border disabled-hover></Table>
  </div>
</template>

<script>
// 了解详情按钮
const detailsButton = (vm, h, currentRow, index) => {
  return h(
    "Button",
    {
      props: {
        type: "primary"
      },
      style: {
        margin: "0 5px"
      },
      on: {
        click: () => {
          //输出当前行
          vm.$emit("detailsBtn-click", index);
        }
      }
    },
    // 根据状态值返回不同的文本
    "详情"
  );
};
// 完成合同按钮
const handleContract = (vm, h, currentRow, index) => {
  return h(
    "Button",
    {
      style: {
        margin: "0 5px"
      },
      props: {
        disabled: currentRow.contractStatus == 0 ? false : true
      },
      on: {
        click: () => {
          vm.$emit("on-handleContract", index);
        }
      }
    },
    "完成合同"
  );
};
// 完成打款按钮
const handlePayButton = (vm, h, currentRow, index) => {
  return h(
    "Button",
    {
      style: {
        margin: "0 5px"
      },
      props: {
        disabled: currentRow.payStatus == 0 ? false : true
      },
      on: {
        click: () => {
          vm.$emit("on-handlePay", index);
        }
      }
    },
    "完成打款"
  );
};
// 完成订单按钮
const handleDoneButton = (vm, h, currentRow, index) => {
  return h(
    "Button",
    {
      style: {
        margin: "0 5px"
      },
      props: {
        type: "success"
      },
      on: {
        click: () => {
          vm.$emit("on-handleDone", index);
        }
      }
    },
    "完成"
  );
};
// 订单失败按钮
const failureButton = (vm, h, currentRow, index) => {
  return h(
    "Poptip",
    {
      props: {
        confirm: true,
        title: "您确定要将这条数据置为失败吗?",
        transfer: true
      },
      on: {
        "on-ok": () => {
          vm.$emit("on-failure", index);
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
        "取消"
      )
    ]
  );
};
// 订单结佣
const handleSettled = (vm, h, currentRow, index) => {
  // 当前行-1 禁用  0启用
  // currentRow.status
  return h(
    "Button",
    {
      style: {
        margin: "0 5px"
      },
      on: {
        click: () => {
          vm.$emit("on-handleSettled", index);
        }
      }
    },
    // 根据状态值返回不同的文本
    "结佣"
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
              if (item === "handleContract") {
                //添加修改合同按钮
                children.push(
                  handleContract(this, h, currentRowData, param.index)
                );
              } else if (item === "handleSettled") {
                children.push(
                  handleSettled(this, h, currentRowData, param.index)
                );
              } else if (item === "detailsButton") {
                children.push(
                  detailsButton(this, h, currentRowData, param.index)
                );
              } else if (item === "failureButton") {
                children.push(failureButton(this, h, currentRowData, param.index));
              } else if (item === "handlePayButton") {
                children.push(handlePayButton(this, h, currentRowData, param.index));
              } else if (item === "handleDoneButton") {
                children.push(handleDoneButton(this, h, currentRowData, param.index));
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
