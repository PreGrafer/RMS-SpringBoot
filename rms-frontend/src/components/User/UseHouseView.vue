<script setup>
import {getDataByParam} from "@/net";
import {ref} from "vue";
import {useStore} from "@/stores";


const store = useStore();
const useHouseList = ref([]);
const getStatusTagType = (status) => {
  switch (status) {
    case '已审批':
      return 'success';
    case '未审批':
      return 'warning';
    case '未通过':
      return 'danger';
    case '已过期':
      return 'danger';
    default:
      return 'info';
  }
}
const fetchUseHouseList = () => {
  getDataByParam('/api/use-house/get-house-applications', {userid: store.auth.userid}, (data) => {
    useHouseList.value = data.data;
  })
}

fetchUseHouseList();
</script>

<template>
  <el-table :data="useHouseList" empty-text="无申请" height="100%" max-height="400"
            stripe style="width: 100%">
    <el-table-column label="位置" width="200">
      <template #default="{ row }">
        {{ `${row.place_name}:${row.unit_number}单元-${row.building_number}号楼-${row.floor}层-${row.door_number}` }}
      </template>
    </el-table-column>
    <el-table-column label="使用方式" prop="rent_or_sale" width="100"/>
    <el-table-column label="申请时间" prop="application_date" width="175"/>
    <el-table-column label="开始时间" prop="start_date" width="175"/>
    <el-table-column label="结束时间" prop="end_date" width="175"/>
    <el-table-column label="需付款" prop="money"/>
    <el-table-column label="备注" prop="note"/>
    <el-table-column fixed="right" label="状态">
      <template #default="{ row }">
        <el-tag :type="getStatusTagType(row.status)">{{ row.status }}</el-tag>
      </template>
    </el-table-column>
  </el-table>
</template>

<style scoped>
</style>