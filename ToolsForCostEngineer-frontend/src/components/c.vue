<script setup lang="ts">


let number = 0;
const select =  function () {
  for (let i = 1; i <= 6; i++){
    number = i;
    console.log(number)
  }
}
</script>

<template>
<div>
  <input :value="number">
  <button :onclick="select">开始</button>
</div>
</template>

<style scoped>
div{
  width: 70vw;
  margin-left: auto;
  margin-right: auto;
  text-align: center;
}
</style>