<template>
  <el-row class="container">
    <el-col :lg="10" :md="12" :xs="18" :span="12">
      <div class="main">
        <el-row>
          <el-button
            type="primary"
            circle
            icon="el-icon-back"
            @click="back"
            class="back"
            size="mini"
          ></el-button>
          <p class="info">注 册</p>
        </el-row>

        <el-form ref="form" :model="form" label-width="80px">
          <el-form-item label="用户名" class="input">
            <el-input
              v-model="form.username"
              placeholder="请输入用户名"
              clearable
            ></el-input>
          </el-form-item>
          <el-form-item label="密码" class="input">
            <el-input
              v-model="form.password"
              placeholder="请输入密码"
              show-password
              clearable
            ></el-input>
          </el-form-item>
          <el-form-item label="真实姓名" class="input">
            <el-input
              v-model="form.realname"
              placeholder="请输入真实姓名"
              clearable
            ></el-input>
          </el-form-item>
          <el-form-item label="手机号码" class="input">
            <el-input
              v-model="form.tel"
              placeholder="请输入您的手机号码"
              clearable
            ></el-input>
          </el-form-item>
          <el-form-item label="Email" class="input">
            <el-input
              v-model="form.email"
              placeholder="请输入您的Email地址"
              clearable
            ></el-input>
          </el-form-item>
          <el-form-item label="身份" size="medium">
            <el-select
              v-model="form.role"
              placeholder="请选择您的身份"
              class="role"
            >
              <el-option
                v-for="item in form.options"
                :key="item.role"
                :label="item.label"
                :value="item.role"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="性别">
            <el-radio-group v-model="form.gender" class="gender">
              <el-radio label="男"></el-radio>
              <el-radio label="女"></el-radio>
            </el-radio-group>
          </el-form-item>
          <el-row class="register-button">
            <el-button
              type="primary"
              size="medium"
              @click="register"
              class="button"
              >注册</el-button
            >
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
  name: "register",
  data() {
    return {
      form: {
        username: "",
        password: "",
        gender: "",
        realname: "",
        tel: "",
        email: "",
        options: [
          {
            role: 0,
            label: "管理员",
          },
          {
            role: 1,
            label: "教师",
          },
          {
            role: 2,
            label: "学生",
          },
        ],
        role: "",
      },
    };
  },
  methods: {
    register() {
      let username = this.form.username;
      let password = this.form.password;
      let realname = this.form.realname;
      let tel = this.form.tel;
      let email = this.form.email;
      let role = this.form.role;
      let gender = this.form.gender;
      let usernamePattern = /^[a-zA-Z]+[a-zA-Z0-9]+$/;
      let telPattern = /^0{0,1}(13[0-9]|15[0-9]|18[0-9])[0-9]{8}$/;
      let emailPattern = /[\w]+(\.[\w]+)*@[\w]+(\.[\w])+/;
      let rolePattern = /^[0-2]$/;

      // axios请求拦截器
      /* axios.interceptors.request.use((config) => {
                config.headers['X-Requested-With'] = 'XMLHttpRequest'
                return config;
            })
            axios.interceptors.response.use((response) => {
                return response;
            },(error) => {
                
                Response.Clear()
                return Promise.reject(error);
            }) */

      axios({
        method: "post",
        url: "/api/register",
        data: {
          userName: username,
          password: sha256(password),
          realName: realname,
          tel: tel,
          email: email,
          gender: gender,
          role: role,
        },
      })
        .then((res) => {
          if (res.status == 200) {
            this.$message({
              showClose: true,
              message: "注册成功，请您登录",
              type: "success",
            });
            this.$router.replace("/login");
          }
        })
        .catch((error) => {
          if (error.response) {
            if (username === "") {
              this.$message({
                showClose: true,
                message: "用户名不能为空",
                type: "error",
              });
              return;
            } else if (username.length < 6 || username.length > 20) {
              this.$message({
                showClose: true,
                message: "用户名的长度不小于6个字符，不大于20个字符",
                type: "error",
              });
              return;
            } else if (!usernamePattern.test(username)) {
              this.$message({
                showClose: true,
                message:
                  "用户名要以英文字母开头，并只能含有英文字母或阿拉伯数字",
                type: "error",
              });
              return;
            } else if (password === "") {
              this.$message({
                showClose: true,
                message: "密码不能为空",
                type: "error",
              });
              return;
            } else if (password.length < 6 || password.length > 30) {
              this.$message({
                showClose: true,
                message: "密码长度不小于6个字符，不大于30个字符",
                type: "error",
              });
              return;
            } else if (realname === "") {
              this.$message({
                showClose: true,
                message: "真实姓名不能为空",
                type: "error",
              });
              return;
            } else if (realname.length < 2 || realname.length > 10) {
              this.$message({
                showClose: true,
                message: "真实姓名长度不小于2个字符，不大于10个字符",
                type: "error",
              });
              return;
            } else if (tel === "") {
              this.$message({
                showClose: true,
                message: "手机号码不能为空",
                type: "error",
              });
              return;
            } else if (!telPattern.test(tel)) {
              this.$message({
                showClose: true,
                message: "手机号码格式错误",
                type: "error",
              });
              return;
            } else if (email === "") {
              this.$message({
                showClose: true,
                message: "电子邮箱不能为空",
                type: "error",
              });
              return;
            } else if (!emailPattern.test(email)) {
              this.$message({
                showClose: true,
                message: "电子邮箱格式错误",
                type: "error",
              });
              return;
            } else if (!rolePattern.test(role)) {
              this.$message({
                showClose: true,
                message: "必须选择身份",
                type: "error",
              });
              return;
            } else {
              switch (error.response.status) {
                case 400:
                  this.$message({
                    showClose: true,
                    message: "注册异常，请重试",
                    type: "error",
                  });
                  break;
                case 500:
                  this.$message({
                    showClose: true,
                    message: "账号已经存在！",
                    type: "error",
                  });
                  //console.log(error.response)
                  break;
                default:
                  this.$message({
                    showClose: true,
                    message: "发生了未知的错误！",
                    type: "error",
                  });
              }
            }
          }
        });
    },
    back() {
      this.$router.replace("/login");
    },
  },
};
</script>

<style lang="scss" scoped>
.container {
  display: flex;
  justify-content: center;
  align-items: center;
}
.main {
  margin-top: 50px;
  background: #ffffff;
  border-radius: 6px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}
.back {
  display: flex;
  float: left;
  margin-top: 18px;
  margin-left: 18px;
}
.info {
  margin-top: 40px;
  display: flex;
  justify-content: center;
  margin-right: 40px;
  text-align: center;
  font-size: 30px;
  color: rgb(69, 158, 231);
  font-weight: bold;
}
.el-form {
  margin: 15px 30px;
}
.el-form-item {
  margin-bottom: 15px;
  margin-right: 20px;
}
.el-form:last-child {
  padding-bottom: 30px;
}
.el-radio {
  padding-right: 25px;
}
.role {
  display: flex;
  justify-content: left;
}
.gender {
  display: flex;
  justify-content: left;
  margin-top: 12px;
  margin-left: 5px;
}
.button {
  width: 200px;
}
.register-button {
  margin-top: 10px;
}
.forget {
  font-size: 12px;
  text-align: right;
  margin-right: 15px;
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