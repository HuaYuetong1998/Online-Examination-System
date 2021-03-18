import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/login',
    name: 'login', //登录界面
    component: () => import('../views/LoginPage.vue')
  },
  {
    path: '/register',
    name: 'register', //注册界面
    component: () => import('../views/RegisterPage.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
