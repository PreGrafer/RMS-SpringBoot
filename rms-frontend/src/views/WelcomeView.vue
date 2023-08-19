<script setup>

</script>

<template>
  <div style="width: 100vw;height: 100vh;overflow: hidden;display: flex">
    <div style="flex: 1">
      <el-image fit="cover" src="./WelComeBackground.JPG"
                style="width: 100%;height: 100%"/>
    </div>
    <div style="width: 25%;background-color: white;text-align: center;z-index: 1">
      <router-view/>
    </div>
  </div>
</template>

<style scoped>

</style>