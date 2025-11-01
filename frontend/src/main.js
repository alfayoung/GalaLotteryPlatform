import { createApp } from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'
import './style.css'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import router from './router/index.js'
// import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import api from './plugins/axios.config'

const pinia = createPinia();
const app = createApp(App);

app.config.globalProperties.$axios = api; // 非常神奇，这句话必须要在 mount 之前，调了半天！！！

app.use(pinia).use(router).use(ElementPlus).mount('#app');
// for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
//     app.component(key, component)
// }