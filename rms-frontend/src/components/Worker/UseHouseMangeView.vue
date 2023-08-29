<script setup>
import {ref} from 'vue';
import {getData, post, postByJson} from "@/net";
import {ElMessage} from "element-plus";

const applicationList = ref([]);

const fetchApplicationList = () => {
  getData('/api/use-house/get-applications', (data) => {
    applicationList.value = data.data;
  })
};

const passApplication = (row) => {
  postByJson('/api/use-house/pass-application', row, (message) => {
    ElMessage.success(message)
    fetchApplicationList();
  })
}
const rejectApplication = (row) => {
  postByJson('/api/use-house/reject-application', row, (message) => {
    ElMessage.success(message)
    fetchApplicationList();
  })
}

fetchApplicationList();
</script>

<template>
  <div>
    <el-table :data="applicationList" empty-text="无申请" height="100%" max-height="600"
              stripe style="width: 100%">
      <el-table-column fixed="left" label="用户编号" prop="user_id"/>
      <el-table-column fixed="left" label="用户名" prop="username"/>
      <el-table-column label="房屋编号" prop="house_id"/>
      <el-table-column label="位置" width="200">
        <template #default="{ row }">
          {{ `${row.place_name}:${row.unit_number}单元-${row.building_number}号楼-${row.floor}层-${row.door_number}` }}
        </template>
      </el-table-column>
      <el-table-column label="使用方式" prop="rent_or_sale"/>
      <el-table-column label="申请时间" prop="application_date" width="175"/>
      <el-table-column label="开始时间" prop="start_date" width="175"/>
      <el-table-column label="结束时间" prop="end_date" width="175"/>
      <el-table-column label="需付款" prop="money"/>
      <el-table-column label="备注" prop="note"/>
      <el-table-column fixed="right" label="操作项" width="200">
        <template #default="{ row }">
          <el-button round size="small" type="success" @click="passApplication(row)">通过</el-button>
          <el-button round size="small" type="danger" @click="rejectApplication(row)">拒绝</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<style scoped>
</style>