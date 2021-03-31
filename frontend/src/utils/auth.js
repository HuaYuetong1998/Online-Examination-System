import Vue from 'vue'
import Cookies from 'js-cookie'
 
Vue.use(Cookies)

const TokenKey = 'gbx_token'
const LoginInfoKey = 'loginInfo'
 
export function getToken() {
  return Cookies.get(TokenKey)
}
 
export function setToken(token) {
  return Cookies.set(TokenKey, token)
}
 
export function removeToken() {
  return Cookies.remove(TokenKey)
}
 
export function getLoginInfo() {
  return Cookies.get(LoginInfoKey)
}
 
export function setLoginInfo(loginInfo) {
  return Cookies.set(LoginInfoKey,loginInfo)
}
 
export function removeLoginInfo() {
  return Cookies.remove(LoginInfoKey)
}