<script setup>
import {User, Lock} from "@element-plus/icons-vue";
import {ElInput, ElCheckbox, ElLink, ElButton, ElDivider} from "element-plus";
import {reactive} from "vue";
import {get, post} from "@/net";
import {ElMessage} from "element-plus";
import router from "@/router";

const loginForm = reactive({
  username: "",
  password: "",
  rememberMe: false
});

const login = () => {
  if (!loginForm.username || !loginForm.password) {
    ElMessage.warning("请填写用户名和密码!")
  } else {
    const loginData = {
      username: loginForm.username,
      password: loginForm.password,
      rememberMe: loginForm.rememberMe
    };
    post('/api/auth/login', loginData, (message) => {
      ElMessage.success(message)
      get(`/api/auth/get-role/${loginForm.username}`, (role) => {
        if (role === 'user') {
          router.push('/user');
        } else if (role === 'worker') {
          router.push('/worker');
        } else if (role === 'admin') {
          router.push('/admin');
        }
      })
    })
  }
}

const goToRegister = () => {
  router.push("/register");
};
const goToForgot = () => {
  router.push("/forgot");
};
</script>

<template>
  <div style="text-align: center;font-size: 25px;color: coral;margin-top: 20%">
    <div>欢迎使用农民搬迁管理系统</div>
  </div>
  <div style="margin-top: 20%">
    <div style="font-size: 30px;font-weight: bold">登录</div>
    <div style="font-size: 15px;color: rgb(128,128,128);margin-top: 5%">请输入用户名与密码进行登录</div>
  </div>

  <div style="margin: 0 15%">
    <el-input v-model="loginForm.username" placeholder="用户名/电话号码" style="margin-top: 20%" type="text">
      <template #prefix>
        <el-icon>
          <User/>
        </el-icon>
      </template>
    </el-input>
    <el-input v-model="loginForm.password" placeholder="密码" style="margin-top: 5%" type="password">
      <template #prefix>
        <el-icon>
          <Lock/>
        </el-icon>
      </template>
    </el-input>
    <el-row style="margin-top: 1%;font-size: 20px">
      <el-col :span="12" style="text-align: left">
        <el-checkbox v-model="loginForm.rememberMe" label="记住我"/>
      </el-col>
      <el-col :span="12" style="text-align: right">
        <el-link @click="goToForgot">忘记密码？
        </el-link>
      </el-col>
    </el-row>
  </div>

  <div>
    <el-button round style="margin-top: 10%;width: 30%" type="default" @click="login">登录</el-button>
    <el-divider style="color: rgb(128,128,128);font-size: 15px;margin-top: 10%">没有账号</el-divider>
    <el-button round style="margin-top: 0;width: 30%" type="info" @click="goToRegister">注册账号
    </el-button>
  </div>
</template>


<style scoped>

</style>