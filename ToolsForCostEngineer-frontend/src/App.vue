<script setup>

</script>

<template>
  <div id="app">
    <h1>工具箱</h1>
    <table>
      <tr>
        <th><router-link to="/b">清单定额对比</router-link></th>
        <th><router-link to="/a" >功能二</router-link></th>
        <th><router-link to="/c">功能三</router-link></th>
      </tr>
    </table>
    <p></p>
    <router-view></router-view>
  </div>
</template>

<style scoped>

  h1{
    text-align: center;
    margin-top: 50px;
  }
  table{
    width: 500px;
    margin-left: auto;
    margin-right: auto;

  }

  th{
    border-width: 2px;
    background-color: rgba(77, 72, 72, 0.25);
  }


  a:link,a:visited
  {
    display:block;
    width:140px;
    font-weight:bold;
    color: white;
    text-align:center;
    padding:15px;
    text-decoration:none;
    text-transform:uppercase;
  }
  a:hover,a:active
  {
    background-color: cornflowerblue;
  }
  .router-link-active {
    background-color: cornflowerblue;
  }
</style>
