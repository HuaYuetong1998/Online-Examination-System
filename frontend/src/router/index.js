import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'hello', //登录界面
    component: () => import('../components/HelloWorld.vue')
  },
  {
    path: '/',
    name: 'login', //登录界面
    component: () => import('../components/login/Login.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
