import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
    history: createWebHistory(),
    base:'/',
    routes: [
        { name: "login", path: "/", component: () => import("../views/login.vue") },
        { name: "home", path: "/home", component: () => import("../views/home.vue") },
        { name: "cjmobile", path: "/cjmobile", component: () => import("../views/cjmobile.vue") },
        { name: "cj", path: "/cj", component: () => import("../views/cj.vue") },
        { name: "tp", path: "/tp", component: () => import("../views/tp.vue") },
        { name: "userinfo", path: "/userinfo", component: () => import("../views/userinfo.vue") },
        { name: "userlist", path: "/userlist", component: () => import("../views/userlist.vue") },
    ]
})

export default router