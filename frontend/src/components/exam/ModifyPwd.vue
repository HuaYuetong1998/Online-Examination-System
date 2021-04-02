<template>
  <div>
    <el-form :model="form" :label-width="formLabelWidth">
      <el-form-item label="旧密码：">
        <el-input v-model="form.oldPassword" show-password clearable></el-input>
      </el-form-item>
      <el-form-item label="新密码：">
        <el-input v-model="form.password" show-password clearable></el-input>
      </el-form-item>
      <el-form-item label="重复密码：">
        <el-input
          v-model="form.repeatPassword"
          show-password
          clearable
        ></el-input>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      form: {
        oldPassword: "",
        password: "",
        repeatPassword: "",
      },
      formLabelWidth: "100px",
    };
  },
};
</script>

<style lang="scss">
.el-form {
  display: flex;
  flex-direction: columns;
  flex-wrap: wrap;
  height: auto;
}
.el-form-item {
  width: 100%;
}
.el-form-item_label {
  text-align: left;
}
.el-input {
  width: 80%;
}
</style>