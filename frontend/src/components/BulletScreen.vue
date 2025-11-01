<template>
<vue-danmaku ref="danma" useSlot style="height:70vh; width:100vw;">
  <template v-slot:dm="{ danmu }">
    <span :style="danmu.color">{{ danmu.content }}</span>
  </template>
</vue-danmaku>
</template>
  
<script setup>
import { onBeforeUnmount, onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import vueDanmaku from 'vue3-danmaku'
import { useuserStore } from '/src/stores/user'
import "vue3-colorpicker/style.css";
import { onBeforeRouteLeave, onBeforeRouteUpdate } from 'vue-router';
const danma = ref(null);
import constants from '/src/stores/constants'
const wsUrlBase = "wss://" + constants.baseUrl + "/websocket";
const user = useuserStore()
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
}

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
