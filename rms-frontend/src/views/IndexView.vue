<script setup>
import {get} from "@/net";
import {ElMessage} from "element-plus";
import router from "@/router";

const logout = () => {
  get('/api/auth/logout', (message) => {
    ElMessage.success(message)
    router.push('/')
  })
}
</script>

<template>
  <div>
    <el-button plain round type="danger" @click="logout()">退出登录</el-button>
  </div>
</template>

<style scoped>

</style>