<template>
    <el-row class="container">
        <el-col :lg="10" :md="12" :xs="18" :span="12">
            <div class="main">
                <p class="info">注 册</p>
                <el-form ref="form" :model="form" label-width="80px">
                    <el-form-item label="用户名" class="input">
                        <el-input v-model="form.username" placeholder="请输入用户名" clearable></el-input>
                    </el-form-item>
                    <el-form-item label="密码" class="input">
                        <el-input v-model="form.password" placeholder="请输入密码" show-password clearable></el-input>
                    </el-form-item>
                    <el-form-item label="真实姓名" class="input">
                        <el-input v-model="form.realname" placeholder="请输入真实姓名" clearable ></el-input>
                    </el-form-item>
                    <el-form-item label="手机号码" class="input">
                        <el-input v-model="form.tel" placeholder="请输入您的手机号码" clearable></el-input>
                    </el-form-item>
                    <el-form-item label="Email" class="input">
                        <el-input v-model="form.email" placeholder="请输入您的Email地址" clearable></el-input>
                    </el-form-item>
                    <el-form-item label="性别">
                        <el-radio-group v-model="form.gender">
                            <el-radio label="男"></el-radio>
                            <el-radio label="女"></el-radio>
                        </el-radio-group>
                    </el-form-item>
                    <el-row class="register-button">
                        <el-button type="primary" size="medium" v-on:click="valid">注册</el-button>  
                    </el-row>
                </el-form>
            </div>
        </el-col>
    </el-row>
</template>

<script>
export default {
    name:"register",
    data() {
        return {
            form:{
                username:'',
                password:'',
                gender:'',
                realname:'',
                tel:'',
                email:''
            }
        }
    },
    methods: {
    
    }
}
</script>

<style scoped>
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
.el-radio-group{
    margin-right:100px;
}
.el-radio{
    padding-left:50px;
}
.input{
    margin-bottom:15px;
    margin-right:20px;
}
.el-form:last-child{
    padding-bottom: 30px;
}
.el-radop
.register-button{
    margin-top:10px;
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