//import Cookies from 'js-cookie'
import Vue from 'vue'
import VueRouter from 'vue-router'
import VueCookies from 'vue-cookies'
import Cookies from 'js-cookie'

Vue.use(VueRouter)
Vue.use(VueCookies)

const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
    return originalPush.call(this, location).catch(err => err)
}

const routes = [
  {
    path: '/',
    name: 'test', //登录界面
    component: () => import('../components/exam/Test.vue'),
    meta:{
      title:'test',
      keepAlive:true
    }
  },
  {
    path: '/login',
    name: 'login', //登录界面
    component: () => import('../views/LoginPage.vue'),
    meta:{
      title:'登录',
      keepAlive:false
    }
  },
  {
    path: '/register',
    name: 'register', //注册界面
    component: () => import('../views/RegisterPage.vue'),
    meta:{
      title:'注册',
      keepAlive:false
    }
  },
  {
    path: '/student/index',
    name: 'index',
    component: () => import('../views/Index.vue'),
    meta:{
      title:'软工综合测试系统',
      keepAlive: true
    }
  },
  {
    path: '/student/exam',
    name: 'exam',
    component: () => import('../views/StudentExamPage.vue'),
    meta:{
      title:'我的考试',
      keepAlive: true
    }
  },
  {
    path: '/student/practice',
    name: 'practice',
    component: () => import('../views/StudentPracticePage.vue'),
    meta:{
      title:'考试练习',
      keepAlive: true
    }
  },
  {
    path: '/student/score',
    name: 'score',
    component: () => import('../views/StudentScorePage.vue'),
    meta:{
      title:'我的成绩',
      keepAlive: true
    }
  },
  {
    path: '/teacher',
    name: 'teacher',
    component: () => import('../views/TeacherPage.vue'),
    meta:{
      title:'软工综合测试系统',
      keepAlive: true
    }
  },
  {
    path: '/admin',
    name: 'admin',
    component: () => import('../views/AdminPage.vue'),
    meta:{
      title:'软工综合测试系统',
      keepAlive: true
    }
  },
]


const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})


//路由全局前置守卫
router.beforeEach((to, from, next) => {
  if (to.path === '/login'|| to.path === '/register') {
    // 清空cookie
    Cookies.remove('cname')
    Cookies.remove('cpassword')
    Cookies.remove('crealname')
    Cookies.remove('role')
    Cookies.remove('token')
    sessionStorage.removeItem("token")
    next();
  } else {
    let token = Cookies.get('token')  
    if (token === null || token === '') {
      next('/login');
    } else {
      next();
    }
  } 
})
export default router
