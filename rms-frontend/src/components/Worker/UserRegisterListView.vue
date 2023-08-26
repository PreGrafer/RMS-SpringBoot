<script setup>
import {ref} from 'vue';
import {getData, post, postByJson} from "@/net";
import {ElMessage} from "element-plus";

const registerList = ref([]);

const fetchRegisterList = () => {
  getData('/api/register/user-register-list', (data) => {
    registerList.value = data.data;
  })
};
const getRoleText = (role) => {
  switch (role) {
    case 'user':
      return '用户';
    case 'worker':
      return '工作人员';
    default:
      return '未知';
  }
}
const passRequest = (row) => {
  const registerData = {
    username: row.username,
    password: row.password,
    phone: row.phone,
    personid: row.personid,
    role: row.role
  }
  postByJson("/api/register/pass-register", registerData, (message) => {
    ElMessage.success(message);
    fetchRegisterList();
  })
}
const rejectRequest = (row) => {
  post("/api/register/reject-register", {username: row.username}, (message) => {
    ElMessage.success(message);
    fetchRegisterList();
  })
}

fetchRegisterList();
</script>

<template>
  <div>
    <el-table :data="registerList" empty-text="无申请" height="100%" stripe style="width: 100%">
      <el-table-column label="用户名" prop="username"/>
      <el-table-column label="密码(已加密)" prop="password"/>
      <el-table-column label="电话号码" prop="phone"/>
      <el-table-column label="身份证号" prop="personid"/>
      <el-table-column label="用户类型" prop="role">
        <template #default="{ row }">
          {{ getRoleText(row.role) }}
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作项">
        <template #default="{ row }">
          <el-button round size="small" type="success" @click="passRequest(row)">通过</el-button>
          <el-button round size="small" type="danger" @click="rejectRequest(row)">拒绝</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<style scoped>
</style>