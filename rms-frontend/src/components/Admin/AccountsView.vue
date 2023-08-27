<script setup>
import {getData, post} from "@/net";
import {ref} from "vue";
import {ElMessage} from "element-plus";

const getRoleText = (role) => {
  switch (role) {
    case 'user':
      return '用户';
    case 'worker':
      return '工作人员';
    case 'admin':
      return '管理员';
    default:
      return '未知';
  }
}
const accountList = ref([]);
const fetchAccountList = () => {
  getData('/api/account/account-list', (data) => {
    accountList.value = data.data;
  })
};

const deleteRow = (row) => {
  post("/api/account/delete-account", {userid: row.userid}, (message) => {
    ElMessage.success(message);
    fetchAccountList();
  })
}

fetchAccountList();
</script>

<template>
  <el-table :data="accountList" empty-text="无数据" height="100%" max-height="500" stripe style="width: 100%">
    <el-table-column fixed="left" label="用户编号" prop="userid" width="100"/>
    <el-table-column label="用户名" prop="username" width="100"/>
    <el-table-column label="姓名" prop="name" width="100"/>
    <el-table-column label="手机号" prop="phone" width="150"/>
    <el-table-column label="身份证号" prop="personid" width="200"/>
    <el-table-column label="性别" prop="gender" width="75"/>
    <el-table-column label="年龄" prop="age" width="75"/>
    <el-table-column label="用户类型" prop="role" width="100">
      <template #default="{ row }">
        {{ getRoleText(row.role) }}
      </template>
    </el-table-column>
    <el-table-column label="原房屋" prop="old_house" width="200"/>
    <el-table-column label="健康状态" prop="health_status" width="200"/>
    <el-table-column label="教育背景" prop="education_background" width="200"/>
    <el-table-column label="工作经验" prop="work_experience" width="200"/>
    <el-table-column label="备注" prop="notes" width="200"/>

    <el-table-column fixed="right" label="操作项" width="200">
      <template #default="{ row }">
        <el-button round size="small" type="success" @click="change(row)">修改</el-button>
        <el-button round size="small" type="danger" @click="deleteRow(row)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<style scoped>

</style>