<template>
  <div class="signin-form">
        <h3 class="sign-title">ticket-sys 登录</h3>
        <div>
            <el-form :model="loginForm" :rules="rules" status-icon ref="ruleForm" class="demo-ruleForm">
                <el-form-item prop="username">
                    <el-input
                            v-model="loginForm.username"
                            autocomplete="off"
                            placeholder="用户名"
                            prefix-icon="el-icon-user-solid"
                    ></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input
                            type="password"
                            v-model="loginForm.password"
                            autocomplete="off"
                            placeholder="请输入密码"
                            prefix-icon="el-icon-lock"
                    ></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="submitForm" id="login-btn">登录</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
export default {

}
</script>

<style>

</style>