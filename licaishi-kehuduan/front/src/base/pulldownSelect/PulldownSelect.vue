<template>
  <div id="pulldown">
    <div :class="{bankPicker:true,moveIn:moveIn,moveOut:moveOut }">
      <div class="mint-datetime">
        <span class="cancel" @click="_cancel">取消</span>
        请选择
        <span class="confirm" @click="_confirm">确定</span>
      </div>
      <mt-picker :slots="slots" @change="onValuesChange" class="picker" :valueKey="valueKey"></mt-picker>
    </div>
    <div class="v-modal" @click="_cancel"></div>
  </div>
</template>

<script type="es6">
import { Picker } from "mint-ui";

export default {
  props: {
    values: {
      type: Array,
      default: function() {
        return [
          {
            values: [" ", " ", "暂无数据"],
            className: "slot",
            textAlign: "center",
            flex: 1
          }
        ];
      }
    },
    valueKey: {
      type: String,
      default: ""
    }
  },
  data() {
    return {
      slots: this.values,
      currBankText: "",

      moveIn: true, //组件进入动画状态
      moveOut: false //组件离开动画状态
    };
  },
  methods: {
    onValuesChange(picker, values) {
      this.currBankText = picker.getValues()[0];
    },
    // 外部接口，取消按钮事件
    _cancel() {
      // 利用Promise完成取消动画
      new Promise((resolve, reject) => {
        this.moveIn = !this.moveIn;
        this.moveOut = !this.moveOut;
        // 别删，因为移除动画时间是200ms
        setTimeout(function() {
          resolve();
        }, 200);
      }).then(() => {
        this.$emit("cancel");
      });
    },
    // 外部接口，确定按钮事件
    _confirm() {
      // 利用Promise完成取消动画
      new Promise((resolve, reject) => {
        this.moveIn = !this.moveIn;
        this.moveOut = !this.moveOut;
        // 别删，因为移除动画时间是200ms
        setTimeout(function() {
          resolve();
        }, 200);
      }).then(() => {
        // 获取当前被选中的文本
        this.$emit("confirm", this.currBankText);
      });
    }
  }
};
</script>


<style lang="stylus">
#pulldown {
  .bankPicker {
    position: fixed;
    left: 0;
    right: 0;
    background-color: #FFF;
    z-index: 2017;
    backface-visibility: hidden;

    .mint-datetime {
      height: 40px;
      line-height: 40px;
      overflow: hidden;
      text-align: center;
      border-bottom: solid 1px #eaeaea;

      .cancel {
        float: left;
        padding: 0 20px;
        color: #26a2ff;
      }

      .confirm {
        float: right;
        padding: 0 20px;
        color: #26a2ff;
      }
    }
  }

  .moveIn {
    bottom: 0;
    animation: mymove-in 0.2s 1;
  }

  .moveOut {
    bottom: -220px;
    animation: mymove-out 0.2s 1;
  }

  @keyframes mymove-out {
    from {
      bottom: 0;
    }

    to {
      bottom: -220px;
    }
  }

  @keyframes mymove-in {
    from {
      bottom: -220px;
    }

    to {
      bottom: 0;
    }
  }
}
</style>



