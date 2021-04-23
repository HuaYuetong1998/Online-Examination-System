<template>
  <div class="class-detail-wrapper">
    <div class="detail-container">
      <el-form ref="detailForm" :model="detailForm" class="detail-form">
        <el-form-item label="班级ID：" style="margin-right: 30px">
          <el-input v-model="detailForm.classId"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="searchClassInfo">查看</el-button>
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
          <el-table-column prop="tel" label="电话"></el-table-column>
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

      <el-card class="class-info" v-if="isCardShow">
        <div class="card-container">
          <div class="card-item">当前班级ID：{{ classInfo.classId }}</div>
          <div class="card-item">年级：{{ classInfo.grade }}</div>
          <div class="card-item">专业：{{ classInfo.major }}</div>
          <div class="card-item">班级：{{ classInfo.className }}</div>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      detailForm: {
        classId: "",
      },
      resultData: [],
      classInfo: {
        classId: "",
        grade: "",
        major: "",
        className: "",
      },
      isCardShow: false,
      loading: false,
    };
  },
  created: function () {
    this.initClassInfo();
  },
  methods: {
    initClassInfo() {
      let classId = this.$route.params.id;
      console.log(classId);
      if (classId != null) {
        this.detailForm.classId = classId;
        this.searchClassInfo();
      }
    },
    searchClassInfo() {
      let classId = this.detailForm.classId;
      if (classId != null && classId != "") {
        setTimeout(() => {
          axios({
            method: "get",
            url: "/api/class/findById",
            params: {
              classId: this.detailForm.classId,
            },
          }).then((res) => {
            if (res.status === 200) {
              let result = res.data.data;
              this.classInfo.classId = result.classId;
              this.classInfo.grade = result.grade;
              this.classInfo.major = result.major;
              this.classInfo.className = result.className;
              this.isCardShow = true;
              console.log(result);
            }
          }, 200);
        });
      } else {
        this.$message({
          showClose: true,
          message: "班级ID为空",
          type: "error",
        });
      }
    },
  },
};
</script>

<style lang="scss" scoped>
.class-detail-wrapper {
  padding: 20px;
}
.detail-container {
  padding: 15px;
  display: flex;
  flex-direction: column;
}
.detail-form {
  display: flex;
  margin-bottom: 10px;
}
.el-form-item {
  display: flex;
  flex-direction: row;
}
.class-info {
  position: fixed;
  top: 90px;
  right: 30px;
  width: 640px;
  height: 100px;
  overflow: auto;
  text-align: left;
}
.card-container {
  height: 60px;
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
}
.card-item {
  width: 300px;
}
</style>