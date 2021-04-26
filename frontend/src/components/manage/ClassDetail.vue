<template>
  <div class="class-detail-wrapper">
    <div class="detail-container">
      <el-form ref="detailForm" :model="detailForm" class="detail-form">
        <el-form-item label="班级ID：" style="margin-right: 30px">
          <el-input v-model="detailForm.classId"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="searchClassInfo">查看</el-button>
          <el-button type="success" @click="addToClass">添加学生</el-button>
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
          <el-table-column fixed="right" label="操作" width="100">
            <template slot-scope="scope">
              <el-button
                icon="el-icon-delete"
                type="danger"
                size="small"
                @click.native.prevent="deleteStudent(scope.$index, resultData)"
                >移除</el-button
              >
            </template>
          </el-table-column>
        </el-table>
      </div>

      <div class="pageination">
        <el-pagination
          background
          @current-change="handleSearchClassStudentCurrentChange"
          :current-page="searchClassStudentCurrentPage"
          :page-size="searchClassStudentPageSize"
          layout="total, prev, pager, next, jumper"
          :total="searchClassStudentPageTotal"
        >
        </el-pagination>
      </div>

      <el-card class="class-info" v-if="isCardShow">
        <div class="card-container">
          <div class="card-item">当前班级ID：{{ classInfo.classId }}</div>
          <div class="card-item">年级：{{ classInfo.grade }}</div>
          <div class="card-item">专业：{{ classInfo.major }}</div>
          <div class="card-item">班级：{{ classInfo.className }}</div>
        </div>
      </el-card>

      <el-dialog
        title="添加学生到班级"
        :visible.sync="addStudentVisible"
        :append-to-body="true"
        :close-on-click-modal="false"
        width="70%"
      >
        <el-table
          ref="studentAddList"
          :data="studentAddList"
          border
          v-loading="loading"
          @selection-change="handleSelectionChange"
        >
          <el-table-column fixed type="selection" width="50"> </el-table-column>
          <el-table-column
            fixed
            prop="studentId"
            label="学号"
            width="100"
          ></el-table-column>
          <el-table-column
            prop="studentRealName"
            label="真实姓名"
          ></el-table-column>
          <el-table-column prop="studentGender" label="性别"></el-table-column>
          <el-table-column prop="studentTel" label="电话"></el-table-column>
          <el-table-column prop="studentEmail" label="邮箱"></el-table-column>
        </el-table>

        <div class="pageination">
          <el-pagination
            background
            @current-change="handleAddToClassCurrentChange"
            :current-page="addToClassCurrentPage"
            :page-size="addToClassPageSize"
            layout="total, prev, pager, next, jumper"
            :total="addToClassPageTotal"
          >
          </el-pagination>
        </div>

        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="handleAddData">添加</el-button>
          <el-button @click="addStudentVisible = false">关闭</el-button>
        </div>
      </el-dialog>
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
      studentAddList: [],
      classInfo: {
        classId: "",
        grade: "",
        major: "",
        className: "",
      },
      multipleSelectionAll: [],
      multipleSelection: [],
      idKey: "studentId",
      addToClassCurrentPage: 1,
      addToClassPageSize: 5,
      addToClassPageTotal: null,
      searchClassStudentCurrentPage: 1,
      searchClassStudentPageSize: 5,
      searchClassStudentPageTotal: null,
      addStudentVisible: false,
      isCardShow: false,
      loading: false,
    };
  },
  created: function () {
    this.initClassInfo();
    if (this.detailForm.classId != "" && this.detailForm.classId != null) {
      this.searchClassList();
    }
  },
  methods: {
    initClassInfo() {
      let classId = this.$route.params.id;
      if (classId != null) {
        this.detailForm.classId = classId;
        this.searchClassInfo();
      }
    },
    initAddToClass() {
      axios({
        method: "get",
        url: "/api/addToClass/init",
        params: {
          classId: this.detailForm.classId,
        },
      }).then((res) => {
        console.log(res);
        if (res.status === 200) {
          let result = res.data.data;
          this.multipleSelectionAll = result;
        }
      });
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
            }
          }, 200);
          this.searchClassList();
        });
      } else {
        this.$message({
          showClose: true,
          message: "班级ID为空",
          type: "error",
        });
      }
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
      console.log("====change");
      console.log(this.multipleSelection);
    },
    handleSearchClassStudentCurrentChange(val) {
      this.searchClassStudentCurrentPage = val;
      this.searchClassList();
    },
    handleAddToClassCurrentChange(val) {
      this.changePageCoreRecordData();
      this.addToClassCurrentPage = val;
      this.searchStudentAddList();
    },
    searchStudentAddList() {
      this.searchClassInfo();
      setTimeout(() => {
        axios({
          method: "get",
          url: "/api/addToClass/student",
          params: {
            currentPage: this.addToClassCurrentPage,
            pageSize: this.addToClassPageSize,
          },
        }).then((res) => {
          this.studentAddList = [];
          if (res.status === 200) {
            this.loading = true;
            let result = res.data.data;
            let pageData = result.pageData;
            this.addToClassPageTotal = result.pageTotal;

            console.log(result);
            for (let i = 0; i < pageData.length; i++) {
              this.studentAddList.push({
                studentId: pageData[i].studentId,
                studentGender: pageData[i].gender,
                studentRealName: pageData[i].realName,
                studentTel: pageData[i].tel,
                studentEmail: pageData[i].email,
              });
            }
            this.loading = false;
            setTimeout(() => {
              this.setSelectRow();
            }, 20);
          }
        });
      }, 200);
    },

    searchClassList() {
      axios({
        method: "post",
        url: "/api/class/searchStudent",
        data: {
          classId: this.detailForm.classId,
          currentPage: this.searchClassStudentCurrentPage,
          pageSize: this.searchClassStudentPageSize,
        },
      }).then((res) => {
        this.resultData = [];
        if (res.status === 200) {
          this.loading = true;
          let result = res.data.data;
          let pageData = result.pageData;
          this.searchClassStudentPageTotal = result.pageTotal;
          console.log(result);
          setTimeout(() => {
            for (let i = 0; i < pageData.length; i++) {
              this.resultData.push({
                studentId: pageData[i].studentId,
                realName: pageData[i].realName,
                gender: pageData[i].gender,
                tel: pageData[i].tel,
                email: pageData[i].email,
              });
            }
            this.loading = false;
          }, 200);
        }
      });
    },
    addToClass() {
      let classId = this.detailForm.classId;
      this.initAddToClass();
      if (classId == null || classId === "") {
        this.$message({
          showClose: true,
          message: "所需添加学生的班级ID不能为空！",
          type: "error",
        });
      } else {
        this.addToClassCurrentPage = 1;
        this.searchStudentAddList();
        this.addStudentVisible = true;
      }
    },
    handleAddData() {
      this.resultData = [];
      this.changePageCoreRecordData();
      if (this.multipleSelectionAll.length <= 0) {
        this.$message({
          showClose: true,
          message: "无勾选数据",
          type: "warning",
        });
      } else {
        axios({
          method: "post",
          url: "/api/class/addStudent",
          data: {
            classId: this.detailForm.classId,
            studentInfos: this.multipleSelectionAll,
          },
        }).then((res) => {
          if (res.status === 200) {
            this.searchClassList();
          }
        });
      }
      this.addStudentVisible = false;
    },
    changePageCoreRecordData() {
      let idKey = this.idKey;
      let that = this;
      if (this.multipleSelectionAll.length <= 0) {
        this.multipleSelectionAll = this.multipleSelection;
        return;
      }
      let selectAllIds = [];
      this.multipleSelectionAll.forEach((row) => {
        selectAllIds.push(row[idKey]);
      });
      let selectIds = [];
      this.multipleSelection.forEach((row) => {
        selectIds.push(row[idKey]);
        if (selectAllIds.indexOf(row[idKey]) < 0) {
          this.multipleSelectionAll.push(row);
        }
      });
      let noSelectIds = [];
      this.studentAddList.forEach((row) => {
        if (selectIds.indexOf(row[idKey]) < 0) {
          noSelectIds.push(row[idKey]);
        }
      });

      noSelectIds.forEach((id) => {
        if (selectAllIds.indexOf(id) >= 0) {
          for (let i = 0; i < that.multipleSelectionAll.length; i++) {
            if (that.multipleSelectionAll[i][idKey] == id) {
              that.multipleSelectionAll.splice(i, 1);
              break;
            }
          }
        }
      });
    },
    setSelectRow() {
      if (!this.multipleSelectionAll || this.multipleSelectionAll.length <= 0) {
        return;
      }
      let idKey = this.idKey;
      let selectAllIds = [];
      let that = this;
      this.multipleSelectionAll.forEach((row) => {
        selectAllIds.push(row[idKey]);
      });
      this.$refs.studentAddList.clearSelection();
      for (let i = 0; i < this.studentAddList.length; i++) {
        if (selectAllIds.indexOf(this.studentAddList[i][idKey]) >= 0) {
          this.$refs.studentAddList.toggleRowSelection(
            this.studentAddList[i],
            true
          );
        }
      }
    },
    deleteStudent(index, rows) {
      let currentId = rows[index].studentId;
      axios({
        method: "post",
        url: "/api/addToClass/delete",
        data: {
          classId: this.detailForm.classId,
          studentId: currentId,
        },
      }).then((res) => {
        if (res.status === 200) {
          console.log(res);
          this.searchClassList();
        }
      });
      console.log(currentId);
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
  width: 540px;
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
  width: 240px;
}
.pageination {
  margin-top: 20px;
  display: flex;
  justify-content: center;
}
</style>