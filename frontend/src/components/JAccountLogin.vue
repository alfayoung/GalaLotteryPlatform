<script setup>
import { ref, onMounted, getCurrentInstance } from 'vue'
import { useuserStore } from "/src/stores/user.js"
import router from "/src/router/index.js"

const user = useuserStore()
const userInfo = ref(null)
const { proxy } = getCurrentInstance()

// 获取授权码并跳转到登录页面
const getAuthorizationCode = () => {
  localStorage.setItem('redirect_uri', window.location.href)
  console.log(localStorage.getItem('redirect_uri'))
  proxy.$axios.get('/api/getAuthorizationCode', {
    params: { redirect_uri: localStorage.getItem('redirect_uri') }
  }).then(response => {
    window.location.href = response.data
  }).catch(error => {
    console.log(error)
  })
}

// 获取用户信息
// 这里有个细节，为了在 updateHead 还没执行的时候就跳转到 /cjmobile 页面，所以要用 await！！！
const getProfile = async () => {
  const urlParams = new URLSearchParams(window.location.search)
  const codes = urlParams.getAll('code') // 如果有多个
  if (codes.length > 0) {
    const code = codes[codes.length - 1] // 取最后一个
    await proxy.$axios.get('/api/getProfile', {
      params: { code: code, redirect_uri: localStorage.getItem('redirect_uri') }
    }).then(response => {
      userInfo.value = response.data
      user.updateuserName(response.data.entities[0].name)
      user.updateJAccount(response.data.entities[0].code)
      user.updatelogin(true)
      user.updateis_admin(false)
      console.log(response.data)

    }).catch(error => {
      console.log(error)
    })
    await proxy.$axios.post('/api/registerUser', null, {
      params: { jAccount: user.jAccount, name: user.userName }
    })
    await proxy.$axios.get('/api/getUserProfile', {
      params: { jAccount: user.jAccount }
    }).then(response => {
      const base64 = btoa(new Uint8Array(response.data).reduce((data, byte) => data + String.fromCharCode(byte), ''))
      user.updateHead('data:image/png;base64,' + base64)
    })
    router.push('/cjmobile')
  }
}

// 在组件挂载后获取用户信息
onMounted(async () => {
  await getProfile()
})
</script>

<template>
  <el-button type="primary" class="login-button" @click="getAuthorizationCode">Jaccount登录<br>Connexion Jaccount</el-button>
</template>

<style scoped>
</style>