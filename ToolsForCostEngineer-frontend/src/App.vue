<script>

import {defineComponent} from "vue";
import Login from "@/components/login.vue";

export default defineComponent({
  components: {Login}
})
</script>

<template>
  <div id="home">
    <el-image id="leftImage" src="https://pic.2amok.com/20201014/5a47bc78c324737b72ce033fc3f29e6coutput_00003.jpg"/>

    <div id="left">
    </div>

    <div id="right">
      <h1>登录</h1>
      <login/>
    </div>

  </div>
</template>

<style>
  #home{
    width: 100vw;
    height: 100vh;
    overflow: hidden;
    display: flex;
  }
  #left{
    flex: 1;
  }
  #right{
    width:500px;
  }
  #leftImage{
    width: 100%;
    height: 100%;
    object-fit: cover;
  }
  h1{
    text-align: center;
    margin-top: 150px;
  }

</style>
