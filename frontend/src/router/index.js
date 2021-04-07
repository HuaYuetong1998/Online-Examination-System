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
    name: 'test', //测试
    component: () => import('../components/exam/Pagination.vue'),
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
    path: '/student/index', //学生主页
    name: 'studentIndex',
    component: () => import('../views/Index.vue'),
    meta:{
      title:'软工综合测试系统',
      keepAlive: true
    }
  },
  {
    path: '/student/exam', //我的考试页面
    name: 'exam',
    component: () => import('../views/StudentExamPage.vue'),
    meta:{
      title:'我的考试',
      keepAlive: true
    }
  },
  {
    path: '/student/practice', //练习页面
    name: 'practice',
    component: () => import('../views/StudentPracticePage.vue'),
    meta:{
      title:'考试练习',
      keepAlive: true
    }
  },
  {
    path: '/student/score', //学生成绩页面
    name: 'score',
    component: () => import('../views/StudentScorePage.vue'),
    meta:{
      title:'我的成绩',
      keepAlive: true
    }
  },
  {
    path: '/manage',
    name: 'manage',
    component: () => import('../views/Manage.vue'),
    children:[
      {
        path: 'index',
        name: 'teacherIndex',
        component: () => import('../components/manage/Index.vue'),
        meta:{
          title:'软工综合测试系统',
          keepAlive: true
        }
      },
      {
        path: 'studentManage',
        name: 'studentManage',
        component: () => import('../components/manage/StudentManage.vue'),
        meta:{
          title:'软工综合测试系统',
          keepAlive: true
        }
      },
      {
        path: 'paper',
        name: 'paper',
        component: () => import('../components/manage/PaperManage.vue'),
        meta:{
          title:'软工综合测试系统',
          keepAlive: true
        }
      },
      {
        path: 'addQuestions',
        name: 'addQuestions',
        component: () => import('../components/manage/AddQuestions.vue'),
        meta:{
          title:'软工综合测试系统',
          keepAlive: true
        }
      },
      {
        path: 'questionsList',
        name: 'questionsList',
        component: () => import('../components/manage/QuestionsList.vue'),
        meta:{
          title:'软工综合测试系统',
          keepAlive: true
        }
      },
      {
        path: 'score',
        name: 'scoreManage',
        component: () => import('../components/manage/ScoreManage.vue'),
        meta:{
          title:'软工综合测试系统',
          keepAlive: true
        }
      },
    ],
    meta:{
      title:'软工综合测试系统',
      keepAlive: true
    }
  },
  {
    path: '/admin/index',
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
