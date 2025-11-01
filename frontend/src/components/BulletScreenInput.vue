<template>
  <vue-danmaku ref="danma" useSlot style="height:70vh; width:100vw;">
    <template v-slot:dm="{ danmu }">
      <span :style="danmu.color">{{ danmu.content }}</span>
    </template>
  </vue-danmaku>
  <div class="message-input">
    <div id="cp" class="inl">
    <color-picker format="rgb" shape="circle" disable-alpha v-model:pureColor="pureColor" />
    </div>
    <input v-model="msg" placeholder="发送一条友善的弹幕吧" class="inl" id="inputdm" type="text">
    <el-button type="success" @click="sendMesage(msg)" class="inl" id="senddm">发送<br>Envoyer</el-button>
  </div>
</template>
  
<script setup>
import { onBeforeUnmount, onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import vueDanmaku from 'vue3-danmaku'
import { onBeforeRouteLeave, onBeforeRouteUpdate } from 'vue-router'
import { useuserStore } from '/src/stores/user'
import { ColorPicker } from "vue3-colorpicker";
import "vue3-colorpicker/style.css";
const danma = ref(null);
import constants from '/src/stores/constants'
const wsUrlBase = "wss://" + constants.baseUrl + "/websocket";
const user = useuserStore()
const pureColor = ref("rgb(255, 0, 0)");
const MAX_LENGTH = 1;
// Websoket连接成功事件
const websocketonopen = (res) => {
  ElMessage.success("WebSocket连接成功");
};

const socket = ref(null);
const message = reactive({
    userName: "",
    content: "",
    color: "",
});

// Websoket接收消息事件
const websocketonmessage = (res) => {
  if (res.data) {
    // ElMessage.success("接收到消息：" + JSON.parse(res.data).content);
    message.content = JSON.parse(res.data).content;
    message.userName = JSON.parse(res.data).userName;
    message.color ="color:"+JSON.parse(res.data).color+";";
    danma.value.insert({
      color: message.color,
      content:message.userName + ": " + message.content
    });
    user.updateList({
      color: message.color,
      content:message.userName + ": " + message.content
    });
    if (danma.length > MAX_LENGTH) {
      danma.shift();
      user.popList();
    }
  }
};
// Websoket连接错误事件
const websocketonerror = (res) => {
  ElMessage.error("连接错误");
};
// Websoket断开事件
const websocketclose = (res) => {
  ElMessage.error("断开连接");
};

const sendMesage = (m) => {
  if (m == "" || m == null) {
      ElMessage.warning("消息不能为空");
  } else {
    if (!user.login) {
      ElMessage.warning("未登录，无法发送消息");
    } else {
      ElMessage.success("发送消息：" + m)
      message.content = m;
      message.userName = user.userName;
      message.color = pureColor.value;
      socket.value.send(JSON.stringify(message));
    }
  }
}
// 创建 websocket 的实例
const createSocket = () => {
  console.log(user.jAccount, user.userName);
  const params = new URLSearchParams({
    jAccount: user.jAccount,
    userName: user.userName
  });
  const wsUrl = `${wsUrlBase}?${params.toString()}`;
  socket.value = new WebSocket(wsUrl);
  socket.value.onopen = websocketonopen;
  socket.value.onmessage = websocketonmessage;
  socket.value.onerror = websocketonerror;
  socket.value.onclose = websocketclose;
};

const pauseSocket = () => {
  websocketclose;
  socket.value = null;
};

onBeforeRouteLeave((to, from, next) => {
  pauseSocket()
  next()
});

onBeforeRouteUpdate((to, from, next) => {
  createSocket()
  next()
});

onMounted(() => {
  createSocket()
});
 
// 组件被销毁之前，清空 sock 对象
onBeforeUnmount(() => {
  pauseSocket()
});
</script>

<style scoped>
.message-input {
  position: fixed;
  bottom: 2vh;
  left: 0vw;
  width: 100vw;
  /* background-color: transparent; */
  background-color: aliceblue;
  opacity: 0.8;
  padding: 10px;
  box-sizing: border-box;
  display: flex;
  justify-content: space-evenly;
  align-items: center;
}

.inl{
  display: inline;
}
#cp{
  transform: scale(1.5);
}
#inputdm{
  width:60vw;
  height: 5vh;
  font-size: 3vh;
  border-radius: 1vw;
  display: flex;
  justify-content: center;
}
#senddm{
  height: 5vh;
  font-size: 1.8vh;
  border-radius: 1vw;
} 
</style>