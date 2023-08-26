<script setup>
import router from "@/router";
import {get} from "@/net";
import {ElMessage} from "element-plus";
import {useRoute} from "vue-router";

const route = useRoute();
const handleMenuSelect = (index) => {
  if (index === "exit") {
    get('/api/auth/logout', (message) => {
      ElMessage.success(message)
      router.push('/')
    })
  } else {
    router.push(`/worker/${route.params.username}/${index}`)
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
          工作人员 {{ this.$route.params.username }}
        </div>
      </el-header>
      <el-container>
        <el-aside style="width: 10%">
          <el-menu active-text-color="#FFA500" background-color="#fff"
                   text-color="#000" @select="handleMenuSelect">
            <el-menu-item index="account">管理个人信息</el-menu-item>
            <el-menu-item index="houses">管理住房信息</el-menu-item>
            <el-menu-item index="add-house">添加可用住房</el-menu-item>
            <el-menu-item index="register-manage">管理注册申请</el-menu-item>
            <el-menu-item index="use-house-manage">管理住房申请</el-menu-item>
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