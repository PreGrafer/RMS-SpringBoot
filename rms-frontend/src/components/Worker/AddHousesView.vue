<script setup>
import {ref} from 'vue'
import {ElMessage, genFileId} from 'element-plus'
import {post} from "@/net";

const upload = ref();

const handleExceed = (files) => {
  upload.value.clearFiles();
  const file = files[0];
  file.uid = genFileId();
  upload.value.handleStart(file);
};

const success = () => {
  ElMessage.success("上传成功");
}
const failed = () => {
  ElMessage.error("上传或处理文件失败");
}
const submitUpload = () => {
  upload.value.submit();
};

</script>

<template>
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
      <el-button type="primary">选择文件</el-button>
    </template>
    <el-button class="ml-3" type="success" @click="submitUpload">
      提交
    </el-button>
    <template #tip>
      <div class="el-upload__tip text-red">
        一次只能选择一个文件
      </div>
    </template>
  </el-upload>
</template>

<style scoped>

</style>