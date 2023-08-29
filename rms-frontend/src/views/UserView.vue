<script setup>
import router from "@/router";
import {useRoute} from 'vue-router';
import {get} from "@/net";
import {ElMessage} from "element-plus";
import {useStore} from "@/stores";


const store = useStore()
const route = useRoute();
const handleMenuSelect = (index) => {
  if (index === "exit") {
    get('/api/auth/logout', (message) => {
      ElMessage.success(message)
      store.auth.username = null
      store.auth.role = null
      router.push('/')
    })
  } else {
    router.push(`/user/${index}`)
  }
}
</script>

<template>
  <div class="common-layout">
    <el-container>
      <el-header height="75px" style="background-color: cornflowerblue">
        <div style="margin: 0.6%;text-align: center;font-size: 20px;color: coral">
          农民搬迁管理系统
        </div>
        <div style="text-align: center;font-size: 10px;color: ivory">
          用户 {{ store.auth.username }}
        </div>
      </el-header>
      <el-container>
        <el-aside style="width: 10%">
          <el-menu active-text-color="#FFA500" background-color="#fff"
                   text-color="#000" @select="handleMenuSelect">
            <el-menu-item index="account">管理个人信息</el-menu-item>
            <el-menu-item index="use-house">查看我的住房</el-menu-item>
            <el-menu-item index="house-app">申请搬迁住房</el-menu-item>
            <el-menu-item index="exit" style="background-color: indianred">退出登录</el-menu-item>
          </el-menu>
        </el-aside>
        <el-main>
          <router-view/>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<style scoped>

</style>