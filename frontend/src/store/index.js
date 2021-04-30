import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)


export default new Vuex.Store({
  state: {
    hasLogin:false,
    highLightTabName:'',
    remainTime:localStorage.getItem("remainSecond"),
  },
  mutations: {
    changeLoginStatus: state => {
      if (state.hasLogin) {
        state.hasLogin = false
      }else {
        state.hasLogin = true
      }
    },
    changeTab(state,tabName) {
      state.highLightTabName = tabName
    },
  },
  actions: {
  },
  modules: {
  }
})
