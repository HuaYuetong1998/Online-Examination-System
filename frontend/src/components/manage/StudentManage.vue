<template>
  <div class="student-manage-wrapper">
    <div class="manage-container">
      <el-form ref="studentForm" :model="studentForm" class="studentForm">
        <el-form-item label="学号：">
          <el-input v-model="studentForm.studentId"></el-input>
        </el-form-item>
        <el-form-item label="姓名：">
          <el-input v-model="studentForm.realName"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="searchStudent">查询</el-button>
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
            <template slot-scope="scope">
              <el-button icon="el-icon-info" type="primary" size="small"
                >查看成绩</el-button
              >
              <el-button
                icon="el-icon-view"
                type="success"
                size="small"
                @click.native.prevent="gotoSituation(scope.$index, resultData)"
                >查看考试情况</el-button
              >
            </template>
          </el-table-column>
        </el-table>

        <div class="pageination">
          <el-pagination
            background
            @current-change="handleCurrentChange"
            :current-page="currentPage"
            :page-size="pageSize"
            layout="total, prev, pager, next, jumper"
            :total="pageTotal"
          >
          </el-pagination>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      studentForm: {
        studentId: "",
        realName: "",
      },
      currentPage: 1,
      pageSize: 5,
      pageTotal: null,
      resultData: [],
      loading: false,
    };
  },
  created: function () {
    this.searchStudent();
  },
  methods: {
    handleCurrentChange(val) {
      this.currentPage = val;
      this.searchStudent();
    },

    searchStudent() {
      axios({
        method: "post",
        url: "/api/studentManage/search",
        data: {
          studentId: this.studentForm.studentId,
          realName: this.studentForm.realName,
          currentPage: this.currentPage,
          pageSize: this.pageSize,
        },
      }).then((res) => {
        console.log(res);
        this.resultData = [];
        if (res.status === 200) {
          this.loading = true;
          let result = res.data.data;
          let pageTotal = result.pageTotal;
          let studentInfos = result.studentInfos;
          this.pageTotal = pageTotal;
          for (let i = 0; i < studentInfos.length; i++) {
            this.resultData.push({
              studentId: studentInfos[i].studentId,
              realName: studentInfos[i].realName,
              gender: studentInfos[i].gender,
              tel: studentInfos[i].tel,
              email: studentInfos[i].email,
            });
          }
          this.loading = false;
        }
      });
    },

    gotoSituation(index, rows) {
      let id = rows[index].studentId;
      this.$router.push({
        name: "studentSituation",
        params: {
          id: id,
        },
      });
    },
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
.pageination {
  margin-top: 20px;
}
</style>