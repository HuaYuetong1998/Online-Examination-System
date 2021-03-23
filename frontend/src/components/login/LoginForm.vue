<template>
    <el-row class="container">
        <el-col :lg="10" :md="12" :xs="18" :span="12">
            <div class="main">
                <p class="info">登 录</p>
                <el-form ref="form" :model="form" label-width="80px">
                    <el-form-item label="用户名" class="input">
                        <el-input v-model="form.username" placeholder="请输入用户名" clearable></el-input>
                    </el-form-item>
                    <el-form-item label="密码" class="input">
                        <el-input v-model="form.password" placeholder="请输入密码" show-password clearable></el-input>
                    </el-form-item>
                    <div class="forget">
                        <a href="javascript:;">忘记密码？</a>
                    </div>
                    <el-row class="login-button">
                        <el-button type="primary" size="medium" @click="login">登录</el-button>
                    </el-row>
                    <el-row class="register-title">
                        <router-link to="/register">
                            <!-- <el-button type="primary" size="medium" plain>注册</el-button>   -->
                            <span>还没有账号？去注册</span>
                        </router-link>
                    </el-row>
                </el-form>
            </div>
        </el-col>
    </el-row>
</template>

<script>
import axios from 'axios'
import { sha256 } from 'js-sha256'
export default {
    name:'login',
    data() {
        return {
            form:{
                username:'',
                password:''
            }
        }
    },
    methods: {
        login() {
            let username = this.form.username
            let password = this.form.password
            
            if (username === '' || username === null) {
                this.$message({
                showClose:true,
                message:'登录账号为空，请输入账号！',
                type:'error'
                })
            }else if (password === '' || password === null) {
                this.$message({
                showClose:true,
                message:'登录密码为空，请输入密码！',
                type:'error'
                })
            }

             // axios请求拦截器
            axios.interceptors.request.use((config) => {
                config.headers['X-Requested-With'] = 'XMLHttpRequest'
                return config;
            })
            axios.interceptors.response.use((response) => {
                return response;
            },(error) => {
                switch(error.response.status) {
                    case 400:
                        this.$message({
                        showClose:true,
                        message:'登录异常，请重试',
                        type:'error'
                        })
                        break;
                    case 500:
                        this.$message({
                        showClose:true,
                        message:'您输入的账号密码可能有误，请重试！',
                        type:'error'
                        })
                        //console.log(error.response)
                        break;
                    default:
                        this.$message({
                        showClose:true,
                        message:'发生了未知的错误！',
                        type:'error'
                        })
                }
                Response.Clear()
                return Promise.reject(error);
            })
            axios({
                method: 'post',
                url: '/api/login',
                data: {
                    userName: this.form.username,
                    password: sha256(this.form.password)
                }
            }).then(res => {
                this.$message({
                    showClose:true,
                    message:'登录成功',
                    type:'success'
                })
                this.$router.push({path:'/'})
            })
        }
    }
}
</script>

<style lang="scss" scoped>
.container{
  display: flex;
  justify-content: center;
  align-items: center;
}
.main{
    margin-top:50px;
    background: #ffffff;
    border-radius:6px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1)
}
.info{
    padding-top:30px;
    text-align: center;
    font-size:30px;
    color:rgb(69, 158, 231);
    font-weight: bold;
}
.el-form{
    margin:15px 30px;
}
.input{
    margin-bottom:15px;
    margin-right:20px;
}
.el-form:last-child{
    padding-bottom: 30px;
}
.login-button{
    margin-top:5px;
}
.register-title{
    margin-top:10px;
    font-size:14px;
}
.el-button{
    width:200px;
}
.forget{
    font-size:12px;
    text-align: right;
    margin-right:15px;
}
.forget a:link,a:visited{
    text-decoration: none;
    color:rgb(69, 158, 231);
}
.forget a:hover{
    text-decoration: underline;
    color:rgb(33, 177, 57);
}
</style>