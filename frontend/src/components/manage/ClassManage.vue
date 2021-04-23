<template>
  <div class="class-manage-wrapper">
    <div class="manage-container">
      <el-form ref="classForm" :model="classForm" class="class-form">
        <el-form-item label="年级：">
          <el-select
            v-model="classForm.gradeValue"
            placeholder="请选择年级"
            style="width: 180px"
            filterable
            clearable
          >
            <el-option
              v-for="item in classForm.grades"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="专业：">
          <el-select
            v-model="classForm.majorValue"
            placeholder="请选择专业"
            style="width: 180px"
            filterable
            clearable
          >
            <el-option
              v-for="item in classForm.major"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="班级：">
          <el-select
            v-model="classForm.classValue"
            placeholder="请选择班级"
            style="width: 180px"
            filterable
            clearable
          >
            <el-option
              v-for="item in classForm.className"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="searchClass">查询</el-button>
          <el-button type="success" @click="addClassVisible = true"
            >添加</el-button
          >
        </el-form-item>
      </el-form>

      <el-divider></el-divider>

      <div class="result">
        <el-table :data="resultData" border v-loading="loading">
          <el-table-column
            fixed
            prop="classId"
            label="班级ID"
            width="100"
          ></el-table-column>
          <el-table-column prop="grade" label="年级"></el-table-column>
          <el-table-column prop="major" label="专业"></el-table-column>
          <el-table-column prop="className" label="班级"></el-table-column>
          <el-table-column fixed="right" label="操作" width="280">
            <template slot-scope="scope">
              <el-button
                icon="el-icon-info"
                type="primary"
                size="small"
                @click.native.prevent="gotoDetail(scope.$index, resultData)"
                >详情</el-button
              >
              <el-button
                icon="el-icon-edit"
                type="success"
                size="small"
                @click.native.prevent="showModify(scope.$index, resultData)"
                >修改</el-button
              >

              <el-button
                icon="el-icon-delete"
                type="danger"
                size="small"
                @click.native.prevent="deleteClass(scope.$index, resultData)"
                >删除</el-button
              >
            </template>
          </el-table-column>
        </el-table>
      </div>

      <div class="pageination">
        <el-pagination
          background
          @current-change="handleCurrentChange"
          :current-page="classForm.currentPage"
          :page-size="classForm.pageSize"
          layout="total, prev, pager, next, jumper"
          :total="classForm.pageTotal"
        >
        </el-pagination>
      </div>

      <!-- 添加班级 -->
      <el-dialog
        title="添加班级"
        :visible.sync="addClassVisible"
        :append-to-body="true"
        :close-on-click-modal="false"
        width="35%"
      >
        <el-form :model="addClassForm" :rules="rules">
          <el-form-item label="年级：" class="addClass" prop="addGrade">
            <el-input v-model="addClassForm.addGrade" clearable></el-input>
          </el-form-item>
          <el-form-item label="专业：" class="addClass" prop="addMajor">
            <el-input v-model="addClassForm.addMajor" clearable></el-input>
          </el-form-item>

          <el-form-item label="班级：" class="addClass" prop="addClass">
            <el-input v-model="addClassForm.addClass" clearable></el-input>
          </el-form-item>
        </el-form>

        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="addClass">添加</el-button>
          <el-button @click="addClassVisible = false">关闭</el-button>
        </div>
      </el-dialog>

      <!-- 修改班级 -->
      <el-dialog
        title="添加班级"
        :visible.sync="modifyClassVisible"
        :append-to-body="true"
        :close-on-click-modal="false"
        width="35%"
      >
        <el-form :model="modifyClassForm" :rules="rules">
          <el-form-item label="年级：" class="addClass" prop="modifyGrade">
            <el-input
              v-model="modifyClassForm.modifyGrade"
              clearable
            ></el-input>
          </el-form-item>
          <el-form-item label="专业：" class="addClass" prop="modifyMajor">
            <el-input
              v-model="modifyClassForm.modifyMajor"
              clearable
            ></el-input>
          </el-form-item>

          <el-form-item label="班级：" class="addClass" prop="modifyClass">
            <el-input
              v-model="modifyClassForm.modifyClass"
              clearable
            ></el-input>
          </el-form-item>
        </el-form>

        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="modifyClass">修改</el-button>
          <el-button @click="modifyClassVisible = false">关闭</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  created: function () {
    this.searchAllClass();
    this.searchClass();
  },
  data() {
    return {
      classForm: {
        grades: [],
        major: [],
        className: [],
        gradeValue: "",
        majorValue: "",
        classValue: "",
        pageSize: 6,
        currentPage: 1,
        pageTotal: null,
      },
      addClassForm: {
        addGrade: "",
        addMajor: "",
        addClass: "",
      },
      modifyClassForm: {
        classId: "",
        modifyGrade: "",
        modifyMajor: "",
        modifyClass: "",
      },
      subjects: [],
      resultData: [],
      addClassVisible: false,
      modifyClassVisible: false,
      rules: {
        addGrade: [
          {
            required: true,
            message: "请输入年级",
            trigger: "blur",
          },
        ],
        addMajor: [
          {
            required: true,
            message: "请输入专业",
            trigger: "blur",
          },
        ],
        addClass: [
          {
            required: true,
            message: "请输入班级",
            trigger: "blur",
          },
        ],
        modifyGrade: [
          {
            required: true,
            message: "请输入年级",
            trigger: "blur",
          },
        ],
        modifyMajor: [
          {
            required: true,
            message: "请输入专业",
            trigger: "blur",
          },
        ],
        modifyClass: [
          {
            required: true,
            message: "请输入班级",
            trigger: "blur",
          },
        ],
      },
      loading: false,
    };
  },
  methods: {
    addClass() {
      axios({
        method: "post",
        url: "/api/class/add",
        data: {
          grade: this.addClassForm.addGrade,
          major: this.addClassForm.addMajor,
          className: this.addClassForm.addClass,
        },
      }).then((res) => {
        if (res.status === 200) {
          if (res.data.msg === "班级已存在") {
            this.$message({
              showClose: true,
              message: "班级已存在",
              type: "error",
            });
          } else {
            this.$message({
              showClose: true,
              message: "添加成功",
              type: "success",
            });
            this.addClassVisible = false;
          }
          this.clearAddInfo();
        }
      });
    },
    clearAddInfo() {
      this.addClassForm.addGrade = "";
      this.addClassForm.addMajor = "";
      this.addClassForm.addClass = "";
    },
    handleCurrentChange(val) {
      this.classForm.currentPage = val;
      this.searchClass();
    },
    searchAllClass() {
      axios({
        method: "post",
        url: "/api/class/all",
        data: {},
      }).then((res) => {
        if (res.status === 200) {
          let result = res.data.data;
          let className = result.className;
          let grade = result.grade;
          let major = result.major;
          for (let i = 0; i < className.length; i++) {
            this.classForm.className.push({
              value: className[i],
              label: className[i],
            });
          }
          for (let i = 0; i < grade.length; i++) {
            this.classForm.grades.push({
              value: grade[i],
              label: grade[i],
            });
          }
          for (let i = 0; i < major.length; i++) {
            this.classForm.major.push({
              value: major[i],
              label: major[i],
            });
          }
          console.log(res.data.data);
        }
      });
    },
    searchClass() {
      axios({
        method: "post",
        url: "/api/class/list",
        data: {
          grade: this.classForm.gradeValue,
          major: this.classForm.majorValue,
          className: this.classForm.classValue,
          pageSize: this.classForm.pageSize,
          currentPage: this.classForm.currentPage,
        },
      }).then((res) => {
        this.resultData = [];
        if (res.status === 200) {
          let result = res.data.data;
          this.loading = true;
          this.classForm.pageTotal = result.pageTotal;
          setTimeout(() => {
            for (let i = 0; i < result.pageData.length; i++) {
              this.resultData.push({
                classId: result.pageData[i].classId,
                grade: result.pageData[i].grade,
                major: result.pageData[i].major,
                className: result.pageData[i].className,
              });
            }
            this.loading = false;
          }, 200);
          console.log(res.data.data);
        }
      });
    },
    gotoDetail(index, rows) {
      let id = rows[index].classId;

      this.$router.push({
        name: "classDetail",
        params: {
          id: id,
        },
      });
    },
    showModify(index, rows) {
      this.modifyClassVisible = true;
      let currentId = rows[index].classId;
      axios({
        method: "get",
        url: "/api/class/findById",
        params: {
          classId: currentId,
        },
      }).then((res) => {
        if (res.status === 200) {
          let result = res.data.data;
          this.modifyClassForm.classId = result.classId;
          this.modifyClassForm.modifyGrade = result.grade;
          this.modifyClassForm.modifyMajor = result.major;
          this.modifyClassForm.modifyClass = result.className;
        }
      });
    },
    modifyClass() {
      axios({
        method: "post",
        url: "/api/class/update",
        data: {
          classId: this.modifyClassForm.classId,
          grade: this.modifyClassForm.modifyGrade,
          major: this.modifyClassForm.modifyMajor,
          className: this.modifyClassForm.modifyClass,
        },
      }).then((res) => {
        if (res.status === 200) {
          if (res.data.msg === "班级已存在") {
            this.$message({
              showClose: true,
              message: "班级已存在",
              type: "error",
            });
          } else {
            this.$message({
              showClose: true,
              message: "修改成功",
              type: "success",
            });
            this.searchClass();
            this.modifyClassVisible = false;
          }
        }
      });
    },
    deleteClass(index, rows) {
      let classId = rows[index].classId;
      this.$confirm("此操作将永久删除该班级，是否继续？", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          axios({
            method: "get",
            url: "/api/class/delete",
            params: {
              classId: classId,
            },
          }).then((res) => {
            if (res.status === 200) {
              this.$message({
                message: "删除成功！",
                type: "success",
              });
              this.searchClass();
              return;
            }
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
  },
};
</script>

<style lang="scss" scoped>
.class-manage-wrapper {
  padding: 20px;
}
.manage-container {
  padding: 15px;
  display: flex;
  flex-direction: column;
}
.class-form {
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
.addClass {
  justify-content: center;
}
</style>