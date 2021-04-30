<template>
  <div class="student-manage-wrapper">
    <div class="manage-container">
      <el-form ref="studentForm" :model="studentForm" class="studentForm">
        <el-form-item label="学号：">
          <el-input v-model="studentForm.studentId"></el-input>
        </el-form-item>
        <el-form-item label="学号：">
          <el-input v-model="studentForm.studentId"></el-input>
        </el-form-item>
        <el-form-item label="学号：">
          <el-input v-model="studentForm.studentId"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary">查询</el-button>
          <el-button type="success">添加</el-button>
        </el-form-item>
      </el-form>

      <el-divider></el-divider>

      <div class="result">
        <el-table :data="resultData" border v-loading="loading">
          <el-table-column
            fixed
            prop="studentId"
            label="学号"
            width="100"
          ></el-table-column>
          <el-table-column prop="realName" label="真实姓名"></el-table-column>
          <el-table-column prop="gender" label="性别"></el-table-column>
          <el-table-column prop="tel" label="联系方式"></el-table-column>
          <el-table-column prop="email" label="邮箱"></el-table-column>
          <el-table-column fixed="right" label="操作" width="280">
            <template>
              <el-button icon="el-icon-info" type="primary" size="small"
                >详情</el-button
              >
              <el-button icon="el-icon-edit" type="success" size="small"
                >修改</el-button
              >

              <el-button icon="el-icon-delete" type="danger" size="small"
                >删除</el-button
              >
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      studentForm: {
        studentId: "",
      },
    };
  },
};
</script>

<style lang="scss" scoped>
.student-manage-wrapper {
  padding: 20px;
}
.manage-container {
  padding: 15px;
  display: flex;
  flex-direction: column;
}
.studentForm {
  display: flex;
  flex-direction: row;
}
.el-form-item {
  display: flex;
  flex-direction: row;
  margin-right: 30px;
}
</style>