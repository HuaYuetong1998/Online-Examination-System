import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'index',
    component: () => import('../views/HomePage.vue')
  },
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


//路由全局前置守卫
/* router.beforeEach((to, from, next) => {
  if (to.path === '/login'|| to.path === '/register') {
    next();
  } else {
    let token = window.sessionStorage.getItem('token');

    if (token === null || token === '') {
      next('/login');
    } else {
      next();
    }
  }
}) */
export default router
