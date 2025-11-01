<script setup>
// import BulletScreen from '/src/components/BulletScreen.vue';
// 保留页面
import {useuserStore} from "/src/stores/user.js"
const user = useuserStore()
user.init()
</script>

<template>
    <router-link to="/userlist">
    <div>
        <img src="/assets/sjtu.png" alt="用户" id="user"/>
    </div>
    </router-link>

    <div>
      <img src="/assets/speit.png" alt="speit" id="back"/>
    </div>
    <!-- <BulletScreen/> -->
    <RouterLink to="/home">
        <img src="/assets/登出.png" alt="" id="logout">
    </RouterLink>
</template>

<style>
#logout{
    width: 5vw;
    position: absolute;
    left: 3vw;
    top: 15vh;
    z-index: 999;
}
#user{
    position: absolute;
    left: 40vw;
    top: 40vw;
}
#back {
    height: 100%;
    position: absolute;
    right: 0vw;
    top: 7.5vw;
    opacity: 20%;
}
</style>

