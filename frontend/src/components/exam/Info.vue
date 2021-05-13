<template>
  <div class="box-wrapper">
    <div class="box-container">
      <div class="box">
        <span class="box-title" style="background: #409eff">个人信息</span>
        <div class="main">
          <el-form
            ref="studentInfoForm"
            :model="studentInfoForm"
            label-width="80px"
            class="infoForm"
          >
            <el-form-item label="学号：" class="input">
              <el-input v-model="studentInfoForm.studentId" disabled></el-input>
            </el-form-item>
            <el-form-item label="真实姓名" class="input">
              <el-input
                v-model="studentInfoForm.realName"
                placeholder="请输入真实姓名"
                clearable
              ></el-input>
            </el-form-item>
            <el-form-item label="手机号码" class="input">
              <el-input
                v-model="studentInfoForm.tel"
                placeholder="请输入您的手机号码"
                clearable
              ></el-input>
            </el-form-item>
            <el-form-item label="Email" class="input">
              <el-input
                v-model="studentInfoForm.email"
                placeholder="请输入您的Email地址"
                clearable
              ></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="saveInfo">保存</el-button>
            </el-form-item>
          </el-form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  inject: ["reload"],
  data() {
    return {
      studentInfoForm: {
        studentId: "",
        realName: "",
        tel: "",
        email: "",
      },
    };
  },
  mounted() {
    this.getInfo();
  },
  methods: {
    getInfo() {
      let studentId = this.$cookies.get("studentId");
      axios({
        method: "get",
        url: "/api/student/situation",
        params: {
          studentId: studentId,
        },
      }).then((res) => {
        if (res.status === 200) {
          let result = res.data.data;
          this.studentInfoForm.studentId = result.studentId;
          this.studentInfoForm.realName = result.realName;
          this.studentInfoForm.tel = result.tel;
          this.studentInfoForm.email = result.email;
        }
      });
    },
    saveInfo() {
      console.log(this.studentInfoForm.studentId);
      axios({
        method: "post",
        url: "/api/student/saveInfo",
        data: {
          studentId: this.studentInfoForm.studentId,
          realName: this.studentInfoForm.realName,
          tel: this.studentInfoForm.tel,
          email: this.studentInfoForm.email,
        },
      }).then((res) => {
        if (res.status === 200) {
          this.$message({
            showClose: true,
            message: "修改成功！",
            type: "success",
          });
          this.reload();
        }
      });
    },
  },
};
</script>

<style lang="scss" scoped>
.box-wrapper {
  display: flex;
  justify-content: center;
}
.box-container {
  width: 80%;
  border-radius: 4px;
  height: auto;
  background: #fff;
}
.box {
  display: flex;
  justify-content: flex-start;
  flex-direction: column;
  width: 100%;
  margin-top: 50px;
  padding: 40px 40px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}
.box-title {
  font-size: 16px;
  font-weight: 400;
  padding: 10px 25px;
  color: #fff;
  border-radius: 4px;
  width: 100px;
  position: relative;
  left: -100px;
}
.infoForm {
  margin: 0 auto;
  width: 50%;
}
</style>