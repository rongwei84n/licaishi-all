/*
 * @Author: 张浩然 
 * @Date: 2018-03-06 19:27:24 
 * @Last Modified by: 张浩然
 * @Last Modified time: 2018-03-17 17:42:06
 * 产品条件查询组件--此组件用于产品模块
 */

 <template>
    <div id="sunProdQueryModal">
        <Scroll class="scroll-conntent" :data="status">
            <div>
                <div>
                    <p class="title">募集状态</p>
                    <div>
                        <span v-for="(item,index) in pKeyList[0]" :key="index" :class="{ActiveIndex:ActiveIndexList[0]===index}" @click="handleChange(0,index)">{{item}}</span>
                    </div>
                </div>
                <div>
                    <p class="title">起购金额</p>
                    <div>
                        <span v-for="(item,index) in pKeyList[1]" :key="index" :class="{ActiveIndex:ActiveIndexList[1]===index}" @click="handleChange(1,index)">{{item}}</span>
                    </div>
                </div>
                <div>
                    <p class="title">返佣率</p>
                    <div>
                        <span v-for="(item,index) in pKeyList[2]" :key="index" :class="{ActiveIndex:ActiveIndexList[2]===index}" @click="handleChange(2,index)">{{item}}</span>
                    </div>
                </div>
            </div>
        </Scroll>
        <div class="footer-btn-content">
            <span class="reset" @click="reset">重置</span>
            <span class="confirm" @click="confirm">确定</span>
        </div>
    </div>
</template>

<script type="es6">
import Scroll from "base/scroll/scroll";
export default {
  props: {
    //   初始化的选择项
    initList: {
      type: Array,
      default: function() {
        return [0, 0, 0, 0, 0, 0, 0];
      }
    },
    // 初始化key值
    pKeyList: {
      type: Array,
      default: function() {
        return [
          /* 募集状态 */
          /* 目前产品成立状态放置 */
          // ["不限","预热中","募集中","募集结束","产品成立"],
          ["不限", "预热中", "募集中", "募集结束"],
          /* 起购金额 */
          ["不限", "100万以上", "300以上", "500万以上", "1000万以上"],
          /* 返佣率 */
          ["不限", "1%以下", "1%-3%", "3%及以上"]
        ];
      }
    },
    status: ""
  },
  data() {
    return {
      ActiveIndexList: [0, 0, 0] //初始化的选择项
    };
  },
  methods: {
    //   确定事件
    confirm() {
      this.$emit("confirm", this.ActiveIndexList);
    },
    // 取消事件
    reset() {
      this.$emit("reset");
      this.ActiveIndexList = [...this.initList];
    },
    handleChange(key, value) {
      this.$set(this.ActiveIndexList, key, value);
    }
  },
  watch: {
    initList() {
      //   此处深拷贝，避免引用共用，污染父组件的初始值
      this.ActiveIndexList = [...this.initList];
    }
  },
  components: {
    Scroll
  }
};
</script>

<style lang="stylus">
#sunProdQueryModal {
    height: 100%;
    width: 100%;
    position: relative;

    >.scroll-conntent {
        width: 100%;
        position: absolute;
        overflow: hidden;
        bottom: 34px;
        top: 0;

        >div {
            >div {
                padding: 16px;
                border-bottom: 1px solid #BFBFBF;

                .title {
                    font-size: 14px;
                    color: #333333;
                    margin-bottom: 16px;
                }

                >div {
                    >span {
                        display: inline-block;
                        padding: 4px 8px;
                        margin: 6px;
                        border: 1px solid #BFBFBF;
                        border-radius: 16px;
                    }

                    .ActiveIndex {
                        background-color: red;
                        color: #FFFFFF;
                    }
                }
            }
        }
    }

    .footer-btn-content {
        height: 34px;
        width: 100vw;
        display: flex;
        justify-content: space-around;
        align-items: center;
        position: absolute;
        bottom: 0;

        >span {
            flex: 1;
            height: 100%;
            display: flex;
            justify-content: center;
            align-items: center;
            font-size: 14px;
        }

        .reset {
            background: #D8D8D8;
            color: #333333;
        }

        .confirm {
            background: #E10101;
            color: #FFFFFF;
        }
    }
}
</style>

 
 

