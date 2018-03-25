<template>
  <div class="m-header">
    <img src="~@/common/image/sfa.png" class="sfa-icon" alt="">
    <div class="search">
      <img src="~@/common/image/search.png" alt="">
      <input type="text" placeholder="国通信托-方兴4263号">
    </div>
    <!-- <img src="~@/common/image/remind.png" class="message-icon" alt=""> -->
  </div>
</template>

<script type="text/ecmascript-6">
export default {};
</script>

<style scoped lang="stylus" rel="stylesheet/stylus">
@import '~common/stylus/variable';
@import '~common/stylus/mixin';

.m-header {
  position: fixed;
  background: #deae47;
  height: 40px;
  color: $color-theme;
  font-size: 0;
  z-index: 1;
  width: 100vw;
  display: flex;
  align-items: center;

  // padding: 12px 10px;
  .sfa-icon {
    width: 40px;
    margin-right: 9px;
  }

  .message-icon {
    width: 25px;
    margin-top: 18px;
    margin-left: 5px;
  }

  .search {
    display: flex;
    flex: 1;
    font-size: $font-size-large;
    align-items: center;
    position: relative;

    >img {
      position: absolute;
      width: 20px;
      margin: 0 6px;
    }

    >input {
      width: 100%;
      height: 28px;
      background: #fff;
      text-indent: 32px;
      font-size: 15px;
      color: #999999;
      border-radius: 6px;
    }
  }

  .mine {
    position: absolute;
    top: 0;
    right: 0;

    .icon-mine {
      display: block;
      padding: 12px;
      font-size: 20px;
      color: $color-theme;
    }
  }
}
</style>
