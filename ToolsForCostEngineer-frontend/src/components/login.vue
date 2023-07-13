<script setup>
import {User, Lock} from "@element-plus/icons-vue";

const user = '';
const pwd = '';

</script>

<template>
  <div class="input">
    <div class="input">
      <el-input type="text" v-model="user" placeholder="请输入用户名">
        <template #prefix>
          <el-icon><User /></el-icon>
        </template>
      </el-input>
    </div>

    <div class="input">
      <el-input type="password" v-model="pwd" placeholder="请输入密码">
        <template #prefix>
          <el-icon><Lock /></el-icon>
        </template>
      </el-input>
    </div>

    <el-row style="font-size: 14px; margin: 20px">
      <el-col :span="12" style="text-align: left">
        <el-checkbox label="记住我"></el-checkbox>
      </el-col>
      <el-col :span="12" style="text-align: right">
        <el-link>忘记密码</el-link>
      </el-col>
    </el-row>

    <el-button class="button" type="success" native-type="submit" plain>登录</el-button>

    <el-divider>
      <span>没有账号</span>
    </el-divider>

    <el-button class="button" type="warning" native-type="submit" plain>马上注册</el-button>

  </div>
</template>

<style scoped>
  div{
    text-align: center;
  }

  .button{
    width: 250px;
  }

  .input{
    margin: 20px;
  }
</style>

