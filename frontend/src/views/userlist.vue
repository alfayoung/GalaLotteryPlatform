<template>
  <div>
    <img :src="imageUrl" alt="Profile Image" />
  </div>
</template>

<script setup>
import { ref, onMounted, getCurrentInstance } from 'vue'
import { useuserStore } from "/src/stores/user.js"
const { proxy } = getCurrentInstance()

const user = useuserStore()
const imageUrl = ref('')

onMounted(async () => {
  const response = await proxy.$axios.get('/api/getUserProfile', { params:{ jAccount: user.jAccount } })
  const base64 = btoa(new Uint8Array(response.data).reduce((data, byte) => data + String.fromCharCode(byte), ''))
  imageUrl.value = 'data:image/png;base64,' + base64
})
</script>