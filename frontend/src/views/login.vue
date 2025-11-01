<template>
    <div>
      <img src="/assets/speit.png" alt="speit" id="back"/>
    </div>
    <div id="log">
        <div class="center" id="title">
            <h2>登录</h2>
            <h2>Connexion</h2>
        </div>
        <div v-if="!showInput">
        <div class="center">
            <el-button type="primary" class="login-button" @click="showInput = !showInput">管理员登录<br>Connexion Administrateur</el-button>
        </div>
        <div class="center">

        <JAccountLogin />
        
        </div>
        </div>
        <div v-if="showInput">
        <div class="center" id="in">
        <input id="myInput" type="text" placeholder="密码 Mot de passe" v-model="password">
        </div>
        <div class="center">
            <el-button type="primary" class="login-button" @click="enter()">登录<br>Connexion</el-button>
        </div>
        <div class="center">
            <el-button type="primary" class="login-button" @click="showInput = !showInput">返回<br>Retour</el-button>
        </div>
        </div>
    </div>
</template>
<script>
import { ref, getCurrentInstance } from 'vue';
import JAccountLogin from '/src/components/JAccountLogin.vue';
import { useuserStore } from "/src/stores/user.js";
import router from '/src/router/index.js'
import { ElMessage } from 'element-plus';
export default {
  components: {
    JAccountLogin
  },
  setup() {
    const user = useuserStore();
    const showInput = ref(false);
    const { proxy } = getCurrentInstance();
    const password = ref("");
    const saveText = () => {
        user.updateinputText(document.getElementById("myInput").value);
    };
    const enter = () => {
        console.log(password.value)
        proxy.$axios.get("/api/checkPassword", {
            params: {
                password: password.value
            }
        }).then(response => {
            if (response.data) {
                user.updateis_admin(true)
                user.updatelogin(true)
                router.push('/home')
            } else {
                ElMessage.error("密码错误")
            }
        })
        user.logout()
    }
    return {
      showInput,
      saveText,
      enter,
      password
    };
  }
};
</script>

<style>
#back{
    height: 100%;
    position: absolute;
    right: 0vw;
    top: 7.5vw;
    opacity: 20%;
    z-index: -2;
}
.center {
    display: flex;
    justify-content: center;
    align-items: center;
    margin: 28px;
    position: relative;
    bottom: 10px;
}
#log{
    width: 30vw;
    position: absolute;
    left: 35vw;
    top:15vw;
    border: 2px black solid;
    border-radius: 1vw;
    background-color: rgba(255, 255, 255, 0.4);
    z-index: -1;
}
.login-button {
  width: 20vw;
  height: 4vw;
  font-size: large;
}
input{
    width: 15vw;
    height: 2.5vw;
    border-radius: 0.5vw;
    opacity: 0.4;
    font-size:medium;
}
@media screen and (max-width: 768px) {
#log{
    width: 70vw;
    position: absolute;
    left: 15vw;
    top: 20vw;
}
.login-button,input {
  width: 25vh;
  height: 6vh;
  /* font-size: large; */
}
input{
    border-radius: 2vw;
}   
}
</style>
