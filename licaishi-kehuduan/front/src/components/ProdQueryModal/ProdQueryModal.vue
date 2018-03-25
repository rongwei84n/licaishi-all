/*
 * @Author: 张浩然 
 * @Date: 2018-03-06 19:27:24 
 * @Last Modified by: 张浩然
 * @Last Modified time: 2018-03-17 17:39:01
 * 产品条件查询组件--此组件用于产品模块
 */

 <template>
  <div id="prodQueryModal">
    <Scroll class="scroll-conntent" :data="status">
      <div>
        <div>
          <p class="title">募集状态</p>
          <div>
            <span v-for="(item,index) in pKeyList[0]" :key="index" :class="{ActiveIndex:ActiveIndexList[0]===index}" @click="handleChange(0,index)">{{item}}</span>
          </div>
        </div>
        <div>
          <p class="title">产品期限</p>
          <div>
            <span v-for="(item,index) in pKeyList[1]" :key="index" :class="{ActiveIndex:ActiveIndexList[1]===index}" @click="handleChange(1,index)">{{item}}</span>
          </div>
        </div>
        <div>
          <p class="title">预期年化收益</p>
          <div>
            <span v-for="(item,index) in pKeyList[2]" :key="index" :class="{ActiveIndex:ActiveIndexList[2]===index}" @click="handleChange(2,index)">{{item}}</span>
          </div>
        </div>
        <div>
          <p class="title">付息方式</p>
          <div>
            <span v-for="(item,index) in pKeyList[3]" :key="index" :class="{ActiveIndex:ActiveIndexList[3]===index}" @click="handleChange(3,index)">{{item}}</span>
          </div>
        </div>
        <div>
          <p class="title">投资领域</p>
          <div>
            <span v-for="(item,index) in pKeyList[4]" :key="index" :class="{ActiveIndex:ActiveIndexList[4]===index}" @click="handleChange(4,index)">{{item}}</span>
          </div>
        </div>
        <div>
          <p class="title">起购金额</p>
          <div>
            <span v-for="(item,index) in pKeyList[5]" :key="index" :class="{ActiveIndex:ActiveIndexList[5]===index}" @click="handleChange(5,index)">{{item}}</span>
          </div>
        </div>
        <div>
          <p class="title">大小配比</p>
          <div>
            <span v-for="(item,index) in pKeyList[6]" :key="index" :class="{ActiveIndex:ActiveIndexList[6]===index}" @click="handleChange(6,index)">{{item}}</span>
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
          /* 产品期限 */
          ["不限", "12个月以内", "12个月", "13-23个月", "24个月", "24个月以上"],
          /* 预期年化收益 */
          [
            "不限",
            "7%以内",
            "7%(包含)-8%",
            "8%(包含)-9%",
            "9%(包含)-10%",
            "10%及以上",
            "浮动"
          ],
          /* 付息方式 */
          [
            "不限",
            "按月付息",
            "按季付息",
            "按半年付息",
            "按年付息",
            "到本期付息"
          ],
          /* 投资领域 */
          [
            "不限",
            "房地产类",
            "金融市场",
            "基础设施",
            "资金池",
            "工商企业",
            "其他"
          ],
          /* 起购金额 */
          ["不限", "50万以上", "100万以上", "200万以上", "300万以上"],
          /* 大小配比 */
          ["不限", "小额畅打", "已配出小额", "严格配比", "全大额"]
        ];
      }
    },
    status: ""
  },
  data() {
    return {
      ActiveIndexList: [0, 0, 0, 0, 0, 0, 0] //初始化的选择项
      // KeyList: []
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
#prodQueryModal {
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

 
 
