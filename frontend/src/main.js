import Vue from 'vue'
import './plugins/axios'
import App from './App.vue'
import './plugins/element.js'
import router from './router'
import store from './store'
import VueWechatTitle from 'vue-wechat-title'
import VueCookies from 'vue-cookies'
import Axios from 'axios'
import Vuex from 'vuex'

Vue.use(VueWechatTitle)
Vue.use(Vuex)
Vue.use(VueCookies)

Vue.config.productionTip = false


Axios.interceptors.request.use((config) => {
  if (sessionStorage.getItem("token")) {
    config.headers.common['Authorization'] = sessionStorage.getItem("token")
  }
  return config
},(error) => {
  router.push('/login')
  return Promise.reject(error)
})
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
