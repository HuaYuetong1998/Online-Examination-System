<template>
  <el-row class="container">
    <el-col :lg="10" :md="12" :xs="18" :span="12">
      <div class="main">
        <p class="info">登 录</p>
        <el-form ref="form" :model="form" label-width="80px" class="login-form">
          <el-form-item label="用户名：" class="input">
            <el-input
              v-model="form.username"
              placeholder="请输入用户名"
              clearable
            ></el-input>
          </el-form-item>
          <el-form-item label="密码：" class="input">
            <el-input
              v-model="form.password"
              placeholder="请输入密码"
              show-password
              clearable
            ></el-input>
          </el-form-item>
          <div class="forget">
            <a href="javascript:;">忘记密码？</a>
          </div>
          <el-row class="login-button">
            <el-button type="primary" size="medium" @click="login"
              >登录</el-button
            >
          </el-row>
          <el-row class="register-title">
            <router-link to="/register">
              <span>还没有账号？去注册</span>
            </router-link>
          </el-row>
        </el-form>
      </div>
    </el-col>
  </el-row>
</template>

<script>
import axios from "axios";
import { sha256 } from "js-sha256";

export default {
  name: "login",
  data() {
    return {
      form: {
        username: "",
        password: "",
      },
    };
  },
  methods: {
    login() {
      let username = this.form.username;
      let password = this.form.password;

      axios({
        method: "post",
        url: "/api/login",
        data: {
          userName: this.form.username,
          password: sha256(this.form.password),
        },
      })
        .then((res) => {
          if (res.status === 200) {
            this.$message({
              showClose: true,
              message: "登录成功",
              type: "success",
            });
            this.$store.state.hasLogin = true;
            let resData = res.data.data;
            if (resData != null) {
              let expireTimes = 24 * 60 * 60;
              switch (resData.role) {
                case 0:
                  this.$cookies.set(
                    "cname",
                    resData.userName,
                    expireTimes,
                    "/"
                  );
                  this.$cookies.set(
                    "cpassword",
                    resData.password,
                    expireTimes,
                    "/"
                  );
                  this.$cookies.set(
                    "crealname",
                    resData.realName,
                    expireTimes,
                    "/"
                  );
                  this.$cookies.set("role", resData.role, expireTimes, "/");
                  this.$cookies.set("token", resData.token, expireTimes, "/");
                  sessionStorage.setItem("token", resData.token);
                  this.$router.push({ path: "/manage/index" });
                  break;
                case 1:
                  this.$cookies.set(
                    "cname",
                    resData.userName,
                    expireTimes,
                    "/"
                  );
                  this.$cookies.set(
                    "cpassword",
                    resData.password,
                    expireTimes,
                    "/"
                  );
                  this.$cookies.set(
                    "crealname",
                    resData.realName,
                    expireTimes,
                    "/"
                  );
                  this.$cookies.set("role", resData.role, expireTimes, "/");
                  this.$cookies.set("token", resData.token, 10, "/");
                  sessionStorage.setItem("token", resData.token);
                  this.$router.push({ path: "/manage/index" });
                  break;
                case 2:
                  this.$cookies.set(
                    "cname",
                    resData.userName,
                    expireTimes,
                    "/"
                  );
                  this.$cookies.set(
                    "cpassword",
                    resData.password,
                    expireTimes,
                    "/"
                  );
                  this.$cookies.set(
                    "crealname",
                    resData.realName,
                    expireTimes,
                    "/"
                  );
                  this.$cookies.set("role", resData.role, expireTimes, "/");
                  this.$cookies.set("token", resData.token, expireTimes, "/");
                  sessionStorage.setItem("token", resData.token);
                  this.$router.push({ path: "/student/index" });
                  break;
                default:
                  console.log("role的数值出错了");
              }

              if (resData.studentId != null) {
                this.$cookies.set(
                  "studentId",
                  resData.studentId,
                  expireTimes,
                  "/"
                );
              }
            }
          }
        })
        .catch((error) => {
          if (error.response) {
            if (username === "" || username === null) {
              this.$message({
                showClose: true,
                message: "登录账号为空，请输入账号！",
                type: "error",
              });
            } else if (password === "" || password === null) {
              this.$message({
                showClose: true,
                message: "登录密码为空，请输入密码！",
                type: "error",
              });
            } else {
              switch (error.response.status) {
                case 400:
                  this.$message({
                    showClose: true,
                    message: "登录异常，请重试",
                    type: "error",
                  });
                  console.log("Error", error);
                  break;
                case 500:
                  console.log(error.response.data);
                  this.$message({
                    showClose: true,
                    message: "您输入的账号密码可能有误，请重试！",
                    type: "error",
                  });
                  console.log("Error", error);
                  break;
                default:
                  this.$message({
                    showClose: true,
                    message: "发生了未知的错误！",
                    type: "error",
                  });
                  console.log("Error", error);
              }
            }
          }
        });
    },
  },
  mutations: {},
};
</script>

<style lang="scss" scoped>
.main {
  margin-top: 50px;
  background: #ffffff;
  border-radius: 6px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  justify-content: center;
  padding: 30px 20px 20px 20px;
  height: auto;
}
.info {
  text-align: center;
  font-size: 30px;
  color: rgb(69, 158, 231);
  font-weight: bold;
}
.login-form {
  margin-top: 20px;
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
}
.input {
  margin: 10px 10px 10px 30px;
  width: 80%;
}

.el-form:last-child {
  padding-bottom: 30px;
}
.login-button {
  margin-top: 5px;
}
.register-title {
  margin-top: 10px;
  font-size: 14px;
}
.el-button {
  width: 40%;
  margin-top: 20px;
}
.forget {
  font-size: 12px;
  text-align: right;
  margin-right: 60px;
}
.forget a:link,
a:visited {
  text-decoration: none;
  color: rgb(69, 158, 231);
}
.forget a:hover {
  text-decoration: underline;
  color: rgb(33, 177, 57);
}
</style>