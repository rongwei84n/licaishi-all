<template>
  <div class="mange-container">
    <table class="data-table">
      <tr>
        <th>uid</th><th>姓名</th><th>电话</th><th>邮箱</th><th>地址</th><th>创建日期</th><th>修改日期</th>
      </tr>
      <tr v-for="financer in financerList">
        <td>{{ financer.uid }}</td><td>{{ financer.name }}</td><td>{{ financer.phone }}</td>
        <td>{{ financer.email }}</td><td>{{ financer.address }}</td>
        <td>{{ financer.createtime}}</td><td>{{ financer.updatetime }}</td>
      </tr>
    </table>
  </div>
</template>

<script>
    export default {
      created: function(){
        this.$http.get('http://127.0.0.1:7074/v1/financerlist')
          .then((res) => {
          this.financerList = res.data.result;
        },(err) => {
          console.log(err);
        });
      },
      data() {
        return {
          financerList: []
        }
      }
    }
</script>

<style scoped>

</style>
