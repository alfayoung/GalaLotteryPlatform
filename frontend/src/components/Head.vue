<template>
    <div class="block">
    <el-avatar :size="50" :src="user.head" />
    </div>
</template>

<script setup>
import { useuserStore } from '../stores/user';
const user = useuserStore()
</script>