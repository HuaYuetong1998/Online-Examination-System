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
                        <el-button type="primary" size="medium">登录</el-button>
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