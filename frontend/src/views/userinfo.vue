<template>
    <div id="info">
        <div>
            <p style="font-size: 15px;width: 100vw;text-align: center;position: relative;top: 7vh;">
                点击头像可更换<br>Cliquez sur la photo pour la changer
            </p>
        </div>
        <div>
        <UploadPhoto id="upload" class="center"/>
        </div>
        <div class="center">
            <h2 class="form" style="text-align: right;">用户名<br>Nom d'utilisateur</h2>
            <p class="in">{{ user.userName }}</p>
        </div>
        <div class="center">
            <h2 class="form">jAccount</h2>
            <p class="in">{{ user.jAccount }}</p>
        </div>
        <div class="center">
            <h2 class="form" style="text-align: right;">抽奖状态<br>Tirage au sort</h2>
            <p class="in" v-if="user.isJoin">已参与<br>Déjà participé</p>
            <p class="in" v-else>未参与<br>Non participé</p>
        </div>
    </div>
    <RouterLink to="/cjmobile">
        <img src="/assets/登出.png" alt="" id="logout">
    </RouterLink>
</template>

<script setup>
import { useuserStore } from '../stores/user';
import UploadPhoto from '../components/UploadPhoto.vue';
const user = useuserStore()
user.init()
</script>

<style>
#logout{
    width: 8vw;
    position: absolute;
    left: 5vw;
    top: 5vh;
    z-index: 999;
}
#info{
    width: 100vw;
    position: absolute;
    left: 0vw;
    top: 20vw;
    background: transparent;
    /* border: 2px black solid; */
    /* border-radius: 1vw; */
    background-color: rgba(255, 255, 255, 0.4);
    z-index: -1;
}
#head{
    transform: scale(2.2);
    margin: 10vh 0vw;
}
.center {
    display: flex;
    justify-content: center;
    align-items: center;
    margin: 10vh 0vw;
}
input{
    border: 0px solid black;
    width: 45vw;
    height: 5vh;
    border-radius: 3vw;
    font-size: large;
}
.form{
    position: absolute;
    right: 65vw;
}
p{
    width: 45vw;
    font-size:x-large ;
}
.in{
    position: absolute;
    right: 12vw;
}
</style>