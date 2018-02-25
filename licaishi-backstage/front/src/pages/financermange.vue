<template>
  <div>理财师管理</div>
</template>

<script>
    export default {
      created: function(){
        this.$http.get('http://127.0.0.1:7094/v1/financerlist').then(function(data){
          console.log(data);
        },function(err){
          console.log(err);
        });
      },
    }
</script>

<style scoped>

</style>
