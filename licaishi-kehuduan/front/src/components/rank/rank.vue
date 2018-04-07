<template>
  <div class="rank">
    <div class="rank-context">
      <div class="rank-top">
        <div class="rank-top-title">财富</div>
        <div class="rank-top-caifu">
          <ul>
            <li>
              8888.00
            </li>
            <li>
              我的资产(元)
            </li>
          </ul>
        </div>
      </div>
      <div class="cai-fu-context">
        <div>
          <ul>
            <li>
              3333.00
            </li>
            <li>
              总期待回报(元)
            </li>
          </ul>
        </div>
        <div>
          <ul>
            <li>
              444.00
            </li>
            <li>
              已完成回报(元)
            </li>
          </ul>
        </div>
      </div>
      <div class="mi-zu-context">
        <ul>
          <li>
            米族账号：12234343
          </li>
          <li>
            余额：￥5555.00
          </li>
        </ul>
      </div>
      <split :sh="6"></split>
      <div class="rank-product-kind">
        <mt-cell title="集合信托" to="/rank" is-link value="0.00">
          <img slot="icon" src="../../common/image/icon_21.jpg" width="18" height="14">
        </mt-cell>
        <mt-cell title="集合资管" to="/rank/mycustom" is-link  value="0.00">
          <img slot="icon" src="../../common/image/my_settings_my_customer.png" width="18" height="14">
        </mt-cell>
        <mt-cell title="债券基金" is-link value="0.00">
          <img slot="icon" src="../../common/image/my_settings_spread.png" width="18" height="14">
        </mt-cell>
        <mt-cell title="阳光私募" is-link value="0.00">
          <img slot="icon" src="../../common/image/my_settings_hotline.png" width="18" height="14">
        </mt-cell>
        <mt-cell title="固定类债权" is-link value="0.00">
          <img slot="icon" src="../../common/image/my_settings_hotline.png" width="18" height="14">
        </mt-cell>
      </div>
    </div>
    <router-view></router-view>
  </div>
</template>

<script>
    import split from "components/split/split";
    export default {
        data () {
            return {}
        },
        methods:{
            rankClick(e){
                let flag = parseInt(e.currentTarget.id);

                if(flag===1){
                    this.$router.push("/rank/workroominfo")
                }else if(flag===2){
                    this.$router.push("/rank/mycustom")
                }else if(flag===3){
                    this.$router.push("/rank/mycommission")
                }else if(flag===4){
                    this.$router.push("/rank/generalize")
                }
            }
        },
        components: {
            split
        }
    }
</script>

<style lang="stylus" lang="stylus" rel="stylesheet/stylus">
  .rank
    position relative
    width 100%
    height 100vh
    background #EFF3F6
    .rank-context
      position absolute
      bottom: 46px
      width 100%
      top 0px
      z-index 2
      .rank-top
        height 20vh
        background green
        font-size 16px
        color:#FFF
        background-image url("~@/common/image/my-co.png")
        background-size 100% 100%
        background-repeat no-repeat
        .rank-top-title
          line-height 8vh
          text-align center


        .rank-top-caifu
          height 12vh
          padding-left 5vw
          padding-top 4vh
          ul
            li
              line-height 20px
              &:first-child
                font-size 19px
                font-weight 600
              &:last-child
                font-size 13px
      .cai-fu-context
        height 8vh

        display flex
        padding-top 2vh
        background #FFF
        div
          flex 1
          padding-left 5vw
          &:last-child
            padding-left 2vw
          ul
            li
              line-height 20px
              &:first-child
                font-size 16px
                color goldenrod
              &:last-child
                font-size 13px
                color grey

      .mi-zu-context
        height 8vh
        line-height 8vh
        font-size 13px
        padding-left 5vw
        background #FFF
      .rank-product-kind
        .mint-cell-value
          color:goldenrod
        .mint-cell-text
          font-size 14px
</style>
