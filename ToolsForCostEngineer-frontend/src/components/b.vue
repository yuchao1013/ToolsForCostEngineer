<script setup>
import CheckBillQuotaTableV2 from "@/components/CheckBillQuotaTableV2.vue";
import {ref} from 'vue'
let componentKey = ref(0);
const updateClick = () => {
  componentKey.value += 1;
  console.log(componentKey);
}
</script>

<template>
  <div>
    <p></p>
    <p>上传需要对比的excel文件（后缀：*.xls） <a href="#" :onclick="updateClick">重置</a></p>
    <CheckBillQuotaTableV2 :key="componentKey"></CheckBillQuotaTableV2>
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