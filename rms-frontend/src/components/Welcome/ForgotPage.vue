<script setup>
import {Bell, Link, Lock, Message, User} from "@element-plus/icons-vue";
import {ElButton, ElDivider, ElInput, ElMessage} from "element-plus";
import {ref, reactive} from "vue";
import router from "@/router";
import {postByJson} from "@/net";

const rules = {
  username: [
    {required: true, message: '用户名不能为空', trigger: 'blur'},
    {pattern: /^[a-zA-Z0-9]*$/, message: '用户名只能包含英文和数字', trigger: 'blur'},
    {min: 3, max: 20, message: '用户名长度在 3 到 20 个字符之间', trigger: 'blur'}
  ],
  password: [
    {required: true, message: '密码不能为空', trigger: 'blur'},
    {min: 6, message: '密码长度至少为 6 个字符', trigger: 'blur'}
  ],
  password_repeat: [
    {required: true, message: '请再次输入密码', trigger: 'blur'},
    {validator: validatePasswordRepeat, trigger: 'blur'}
  ],
  phone: [
    {required: true, message: '电话号码不能为空', trigger: 'blur'},
    {pattern: /^[0-9]{11}$/, message: '请输入有效的11位电话号码', trigger: ['blur']},
  ],
  personid: [
    {required: true, message: '身份证号码不能为空', trigger: 'blur'},
    {validator: validatePersonId, trigger: 'blur'}
  ]
}

function validatePasswordRepeat(rule, value, callback) {
  if (value === '') {
    callback(new Error('请再次输入密码'))
  } else if (value !== forgotForm.password) {
    callback(new Error('两次输入的密码不一致'))
  } else {
    callback()
  }
}

function validatePersonId(rule, value, callback) {
  // 假设身份证号为 18 位数字，最后一位可能是数字或字母X
  const pattern = /^\d{17}(\d|X)$/i; // 使用 i 标志表示不区分大小写
  if (!pattern.test(value)) {
    callback(new Error('身份证号格式不正确'));
  } else {
    callback();
  }
}

const forgotForm = reactive({
  username: '',
  password: '',
  password_repeat: '',
  phone: '',
  personid: ''
})

const forgotFormRef = ref()

const resetPassword = () => {
  forgotFormRef.value.validate((isValid) => {
    if (isValid) {
      const resetData = {
        username: forgotForm.username,
        password: forgotForm.password,
        phone: forgotForm.phone,
        personid: forgotForm.personid
      };

      postByJson('/api/auth/reset-password', resetData, (message) => {
        ElMessage.success(message);
        goToLogin();
      });
    } else {
      ElMessage.warning("请完整填写信息!");
    }
  });
};

const goToLogin = () => {
  router.push("/login");
};
</script>

<template>
  <div style="margin-top: 20%">
    <div style="font-size: 30px; font-weight: bold">重置密码</div>
    <div style="font-size: 15px; color: rgb(128,128,128); margin-top: 5%">请输入信息进行密码重置</div>
  </div>

  <div style="margin: 0 15%">
    <el-form ref="forgotFormRef" :model="forgotForm" :rules="rules" style="margin-top: 5%">
      <el-form-item prop="username">
        <el-input v-model="forgotForm.username" placeholder="用户名" type="text">
          <template #prefix>
            <el-icon>
              <User/>
            </el-icon>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input v-model="forgotForm.password" placeholder="新密码" type="password">
          <template #prefix>
            <el-icon>
              <Lock/>
            </el-icon>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item prop="password_repeat">
        <el-input v-model="forgotForm.password_repeat" placeholder="重复密码" type="password">
          <template #prefix>
            <el-icon>
              <Bell/>
            </el-icon>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item prop="phone">
        <el-input v-model="forgotForm.phone" placeholder="电话号码" type="text">
          <template #prefix>
            <el-icon>
              <Message/>
            </el-icon>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item prop="personid">
        <el-input v-model="forgotForm.personid" placeholder="身份证号" type="text">
          <template #prefix>
            <el-icon>
              <Link/>
            </el-icon>
          </template>
        </el-input>
      </el-form-item>
    </el-form>
  </div>

  <div>
    <el-button round style="margin-top: 10%; width: 30%" type="default" @click="resetPassword">重置密码</el-button>
    <el-divider style="color: rgb(128,128,128); font-size: 15px; margin-top: 10%">回到登录</el-divider>
    <el-button round style="margin-top: 0; width: 30%" type="info" @click="goToLogin">登录</el-button>
  </div>
</template>

<style scoped>

</style>