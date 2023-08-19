<script setup>
import {Avatar, Lock, Message, User, Link, Bell} from "@element-plus/icons-vue";
import {ref, reactive} from "vue";
import router from "@/router";
import {ElMessage} from "element-plus";
import {post} from "@/net";

const registerForm = reactive({
  username: '',
  password: '',
  password_repeat: '',
  phone: '',
  personid: '',
  role: ''
})

const goToLogin = () => {
  router.push("/login");
};

const options = [
  {roleName: '用户', role: 'user'},
  {roleName: '工作人员', role: 'worker'}
]

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
  code: [
    {required: true, message: '验证码不能为空', trigger: 'blur'}
  ],
  personid: [
    {required: true, message: '身份证号码不能为空', trigger: 'blur'},
    {validator: validatePersonId, trigger: 'blur'}
  ],
  role: [
    {required: true, message: '请选择角色', trigger: 'change'}
  ]
}

function validatePasswordRepeat(rule, value, callback) {
  if (value === '') {
    callback(new Error('请再次输入密码'))
  } else if (value !== registerForm.password) {
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

const registerFormRef = ref()

const register = () => {
  registerFormRef.value.validate((isValid) => {
    if (isValid) {
      post('/api/auth/register-post', {
        username: registerForm.username,
        password: registerForm.password,
        phone: registerForm.phone,
        personid: registerForm.personid,
        role: registerForm.role
      }, (message) => {
        ElMessage.success(message)
      })
    } else {
      ElMessage.warning("请完整填写信息!")
    }
  })
}
</script>

<template>
  <div style="margin-top: 20%">
    <div style="font-size: 30px;font-weight: bold">注册</div>
    <div style="font-size: 15px;color: gray;margin-top: 5%">请输入信息进行注册</div>
  </div>

  <div style="margin: 0 15%">
    <el-form ref="registerFormRef" :model="registerForm" :rules="rules" style="margin-top: 5%">
      <el-form-item prop="username">
        <el-input v-model="registerForm.username" placeholder="用户名" type="text">
          <template #prefix>
            <el-icon>
              <User/>
            </el-icon>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input v-model="registerForm.password" placeholder="密码" type="password">
          <template #prefix>
            <el-icon>
              <Lock/>
            </el-icon>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item prop="password_repeat">
        <el-input v-model="registerForm.password_repeat" placeholder="重复密码" type="password">
          <template #prefix>
            <el-icon>
              <Bell/>
            </el-icon>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item prop="phone">
        <el-input v-model="registerForm.phone" placeholder="电话号码" type="text">
          <template #prefix>
            <el-icon>
              <Message/>
            </el-icon>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item prop="personid">
        <el-input v-model="registerForm.personid" placeholder="身份证号" type="text">
          <template #prefix>
            <el-icon>
              <el-icon>
                <Link/>
              </el-icon>
            </el-icon>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item prop="role">
        <el-select v-model="registerForm.role" placeholder="选择你需要注册的身份"
                   style="width: 100%">
          <el-option
              v-for="item in options"
              :key="item.role"
              :label="item.roleName"
              :value="item.role"
          />
          <template #prefix>
            <el-icon>
              <Avatar/>
            </el-icon>
          </template>
        </el-select>
      </el-form-item>
    </el-form>
  </div>

  <div>
    <el-button round style="margin-top: 10%;width: 30%" type="default" @click="register">注册</el-button>
    <el-divider style="color: gray;font-size: 15px;margin-top: 10%">已有账号</el-divider>
    <el-button round style="margin-top: 0;width: 30%" type="info" @click="goToLogin">登录</el-button>
  </div>
</template>

<style scoped>

</style>