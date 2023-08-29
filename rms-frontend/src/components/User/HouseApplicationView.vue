<script setup>
import {getData} from "@/net";
import {reactive, ref} from "vue";
import router from "@/router";
import {useStore} from "@/stores";

const store = useStore();

const unUsedHouseList = ref([]);
const fetchUnUsedHouseList = () => {
  getData('/api/house/get-unUsedHouses', (data) => {
    unUsedHouseList.value = data.data;
  })
};
const app = (row) => {
  router.push(`/user/house-app/${row.house_id}`)
}


fetchUnUsedHouseList();
</script>

<template>
  <el-table :data="unUsedHouseList" empty-text="无数据" height="100%" max-height="600" stripe style="width: 100%">
    <el-table-column label="房屋编号" prop="house_id" width="100"/>
    <el-table-column label="位置">
      <template #default="{ row }">
        {{ `${row.place_name}:${row.unit_number}单元-${row.building_number}号楼-${row.floor}层-${row.door_number}` }}
      </template>
    </el-table-column>
    <el-table-column label="面积/㎡" prop="area" width="100"/>
    <el-table-column label="使用" prop="rent_or_sale" width="100"/>
    <el-table-column label="价格/￥" prop="price"/>
    <el-table-column label="备注" prop="note"/>
    <el-table-column fixed="right" label="操作项" width="100">
      <template #default="{ row }">
        <el-button round size="small" type="success" @click="app(row)">申请</el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<style scoped>

</style>