<template>
  <div class="caifu-product">
    <workRoomHeader @back="back"  :mytitle="mytitle" ></workRoomHeader>
    <div class="active-header">
      <div v-for="(item,index) in headerItemList" :key="index" @click="onClick(index)" class="active-header-item">
        <span :class="{activeItem:index===activeIndex}">{{item}}</span>
      </div>
    </div>

    <div>
      <mt-tab-container  v-model="active" swipeable>
        <mt-tab-container-item id="0">
          <mt-cell v-for="n in 10" title="tab-container 1"></mt-cell>
        </mt-tab-container-item>
        <mt-tab-container-item id="1">
          <mt-cell v-for="n in 5" title="tab-container 2"></mt-cell>
        </mt-tab-container-item>
      </mt-tab-container>
    </div>


  </div>

</template>

<script type="text/ecmascript-6">
  import workRoomHeader from "components/workRoomHeader/workRoomHeader";
  export default {
    data(){
      return {
        headerItemList:["持有中","已结束"],
        active: "0",
        activeIndex:0
      }

    },
    created(){
      //this.selected = 1
    },
    methods: {
      back() {
        this.$router.go(-1)
      },
      onClick(dex){
        this.active = dex+"";
        this.activeIndex = dex;
        //alert(this.selected)
      }
    },
    computed: {
      mytitle () {
        let headerList = [
          "集合信托",
          "集合资管",
          "债权基金",
          "阳光私募",
          "固定类债权"
        ]
        let activeIndex = this.$route.params.activeIndex;
        return headerList[activeIndex-1]
      }
    },
    components: {
      workRoomHeader
    }
  }
</script>

<style lang="stylus" rel="stylesheet/stylus">
  .caifu-product
    width 100vw
    height 100vh
    position absolute
    z-index 2
    background #FFF
    .active-header
      display flex
      height 40px
      background grey
      justify-content: space-between;
      align-items: center;
      .active-header-item
        flex 1
        text-align center
        .activeItem
          color red
          //border-bottom 1px solid green
</style>