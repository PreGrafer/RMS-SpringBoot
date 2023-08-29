<script setup>
import {useStore} from "@/stores";
import {get} from "@/net";
import router from "@/router";

const store = useStore()
if (store.auth.userid == null || store.auth.username == null || store.auth.role == null) {
  get('/api/account/me', (message) => {
    store.auth.userid = message.userid
    store.auth.username = message.username
    store.auth.role = message.role
    router.beforeEach((to, from, next) => {
      if (to.meta.requiresAuth) {
        // 检查用户是否已登录
        if (store.auth.role == null) {
          // 未登录，跳转到登录页面
          next('/login')
        } else { // 检查用户角色
          if (store.auth.role !== to.meta.role) {
            next(`/${store.auth.role}`)
          } else {
            next()
          }
        }
      } else if (to.matched.length === 0) {
        next('/login')
      } else {
        next()
      }
    })
  }, () => {
    store.auth.userid = null
    store.auth.username = null
    store.auth.role = null
  })
}


</script>

<template>
  <router-view/>
</template>

<style scoped>

</style>