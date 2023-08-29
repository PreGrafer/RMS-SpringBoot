<script setup>
import {getData, post} from "@/net";
import {ref} from "vue";
import {ElMessage, genFileId} from 'element-plus'
import router from "@/router";
import {useStore} from "@/stores";

const store = useStore();
const upload = ref();

const handleExceed = (files) => {
  upload.value.clearFiles();
  const file = files[0];
  file.uid = genFileId();
  upload.value.handleStart(file);
};

const success = () => {
  ElMessage.success("上传成功");
  fetchHouseList();
}
const failed = () => {
  ElMessage.error("上传或处理文件失败");
}
const submitUpload = () => {
  upload.value.submit();
};

const houseList = ref([]);
const fetchHouseList = () => {
  getData('/api/house/get-houses', (data) => {
    houseList.value = data.data;
  })
};
const change = (row) => {
  router.push(`/${store.auth.role}/houses/${row.house_id}`)
}

const deleteRow = (row) => {
  post("/api/house/delete-house", {house_id: row.house_id}, (message) => {
    ElMessage.success(message);
    fetchHouseList();
  })
}

fetchHouseList();
</script>

<template>
  <el-table :data="houseList" empty-text="无数据" height="100%" max-height="500" stripe style="width: 100%">
    <el-table-column label="房屋编号" prop="house_id" width="100"/>
    <el-table-column label="位置" width="200">
      <template #default="{ row }">
        {{ `${row.place_name}:${row.unit_number}单元-${row.building_number}号楼-${row.floor}层-${row.door_number}` }}
      </template>
    </el-table-column>
    <el-table-column label="面积/㎡" prop="area"/>
    <el-table-column label="使用" prop="rent_or_sale"/>
    <el-table-column label="价格/￥" prop="price"/>
    <el-table-column label="房屋状态" prop="vacancy_status"/>
    <el-table-column label="备注" prop="note"/>
    <el-table-column fixed="right" label="操作项" width="200">
      <template #default="{ row }">
        <el-button round size="small" type="success" @click="change(row)">修改</el-button>
        <el-button round size="small" type="danger" @click="deleteRow(row)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>
  <div style="text-align: center;margin-top: 1%;">
    <div style="font-size: 20px">文件上传</div>
    <el-upload
        ref="upload"
        :auto-upload="false"
        :limit="1"
        :on-error="failed"
        :on-exceed="handleExceed"
        :on-success="success"
        accept=".xlsx"
        action="http://localhost:8080/api/house/update-file"
        with-credentials
    >
      <template #trigger>
        <el-button round type="primary">选择文件</el-button>
      </template>
      <el-button round type="success" @click="submitUpload">提交</el-button>
      <template #tip>
        <div class="el-upload__tip text-red">
          一次只能选择一个文件
        </div>
      </template>
    </el-upload>
  </div>
</template>

<style scoped>

</style>