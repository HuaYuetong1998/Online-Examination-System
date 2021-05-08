<template>
  <div class="exam-wrapper">
    <el-tabs v-model="activeName" type="border-card" @tab-click="handleClick">
      <el-tab-pane label="考试发布" name="publish">
        <div class="exam-container">
          <el-form
            ref="examForm"
            :model="examForm"
            label-width="100px"
            label-position="right"
            inline="inline"
            :rules="rules"
          >
            <el-form-item label="考试标题：" prop="title">
              <el-input v-model="examForm.title"> </el-input>
            </el-form-item>
            <el-form-item label="选择试卷：" prop="paperId">
              <el-select
                v-model="examForm.paperId"
                filterable
                placeholder="请选择"
                ref="selector"
                @visible-change="
                  (v) => visibleChange(v, 'selector', selectClick)
                "
              >
                <el-option
                  v-for="item in paperSelect"
                  :key="item.paperId"
                  :label="item.paperTitle"
                  :value="item.paperId"
                >
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="群体选择：">
              <el-switch
                v-model="studentSwitch"
                active-text="指定学生"
                inactive-text="班级发布"
                @change="switchStudent"
              >
              </el-switch>
            </el-form-item>
            <el-form-item
              v-if="!studentSwitch"
              label="班级选择："
              prop="classId"
            >
              <el-select
                v-model="examForm.classId"
                filterable
                placeholder="请选择"
              >
                <el-option
                  v-for="item in classSelect"
                  :key="item.classId"
                  :label="item.classTitle"
                  :value="item.classId"
                >
                </el-option>
              </el-select>
            </el-form-item>

            <el-form-item v-else label="指定学生：">
              <el-button type="success" @click="assign">指定</el-button>
            </el-form-item>

            <!-- 开始日期 -->
            <el-form-item label="开始时间：" prop="examStartTime">
              <el-date-picker
                v-model="examForm.examStartDate"
                type="date"
                placeholder="开始日期"
                value-format="yyyy-MM-dd"
                :picker-options="startDatePickerOptions"
                style="margin-right: 20px"
                @change="initStartTime"
              >
              </el-date-picker>
              <el-time-picker
                :disabled="startTimeDisabled"
                v-model="examForm.examStartTime"
                placeholder="开始时间"
                format="HH:mm"
                value-format="HH:mm"
                :picker-options="startTimePickerOptions"
                @change="initOverDate"
                style="margin-right: 10px"
              >
              </el-time-picker>
            </el-form-item>

            <!-- 结束日期 -->
            <el-form-item label="结束时间：" prop="examOverTime">
              <el-date-picker
                v-model="examForm.examOverDate"
                type="date"
                placeholder="结束日期"
                value-format="yyyy-MM-dd"
                :picker-options="OverDatePickerOptions"
                style="margin-right: 20px"
                :disabled="overDateDisabled"
                @change="initEndTime"
              >
              </el-date-picker>
              <el-time-picker
                :disabled="endTimeDisabled"
                v-model="examForm.examEndTime"
                placeholder="结束时间"
                format="HH:mm"
                value-format="HH:mm"
                :picker-options="endTimePickerOptions"
              >
              </el-time-picker>
            </el-form-item>

            <el-form-item label="考试时间：" prop="examTimeDuration">
              <el-input-number
                v-model="examForm.examTimeDuration"
                :min="1"
                label="考试时间"
              ></el-input-number>
              <span>（分钟）</span>
            </el-form-item>

            <el-form-item label="防作弊：">
              <el-checkbox-group v-model="examForm.antiCheatList">
                <el-checkbox label="打乱题序"></el-checkbox>
                <el-checkbox label="切屏提醒"></el-checkbox>
              </el-checkbox-group>
            </el-form-item>
            <el-form-item style="margin-left: 100px">
              <el-button type="primary" @click="publish">发布试卷</el-button>
              <el-button @click="reset">重置</el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-tab-pane>
      <el-tab-pane label="考试状态管理" name="status">
        <div class="status-container">
          <el-form
            ref="examStatusForm"
            :model="examStatusForm"
            class="examStatusForm"
          >
            <el-form-item label="考试ID：">
              <el-input v-model="examStatusForm.examId"></el-input>
            </el-form-item>
            <el-form-item label="考试标题：">
              <el-input v-model="examStatusForm.examTitle"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="searchExam">搜索</el-button>
            </el-form-item>
          </el-form>

          <el-divider></el-divider>

          <div class="result">
            <el-table :data="resultData" border v-loading="loading">
              <el-table-column
                fixed
                prop="examId"
                label="考试ID"
                width="100"
              ></el-table-column>
              <el-table-column
                prop="examTitle"
                label="考试标题"
              ></el-table-column>
              <el-table-column prop="timeLimit" label="时限"></el-table-column>
              <el-table-column
                prop="startTime"
                label="开始时间"
              ></el-table-column>
              <el-table-column
                prop="overTime"
                label="结束时间"
              ></el-table-column>
              <el-table-column prop="status" label="状态" width="80">
                <template slot-scope="scope">
                  <el-tag v-if="scope.row.status == '0'">进行中</el-tag>
                  <el-tag type="danger" v-if="scope.row.status == '1'"
                    >已结束</el-tag
                  >
                </template>
              </el-table-column>
              <el-table-column fixed="right" label="操作" width="120">
                <template slot-scope="scope">
                  <el-button
                    v-if="scope.row.status == '0'"
                    icon="el-icon-error"
                    type="danger"
                    size="small"
                    style="width: 95px"
                    @click.native.prevent="endExam(scope.$index, resultData)"
                    >提前结束</el-button
                  >
                  <el-button
                    v-if="scope.row.status == '1'"
                    icon="el-icon-remove-outline"
                    type="info"
                    size="small"
                    style="width: 95px"
                    >无操作</el-button
                  >
                </template>
              </el-table-column>
            </el-table>

            <div class="pageination">
              <el-pagination
                background
                @current-change="handleStatusCurrentChange"
                :current-page="statusCurrentPage"
                :page-size="statusPageSize"
                layout="total, prev, pager, next, jumper"
                :total="statusPageTotal"
              >
              </el-pagination>
            </div>
          </div>
        </div>
      </el-tab-pane>
    </el-tabs>

    <el-dialog
      title="指定学生参加考试"
      :visible.sync="AssignVisible"
      :append-to-body="true"
      :close-on-click-modal="false"
      width="70%"
    >
      <el-table
        ref="studentList"
        :data="studentList"
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
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-size="pageSize"
          layout="total, prev, pager, next, jumper"
          :total="pageTotal"
        >
        </el-pagination>
      </div>

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="handleAssignStudent">指定</el-button>
        <el-button @click="AssignVisible = false">关闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import axios from "axios";
export default {
  inject: ["reload"],
  created: function () {
    this.loadPaper();
    this.loadClass();
    this.searchExam();
    this.getLocalTab();
  },
  data() {
    return {
      activeName: "publish",
      startDatePickerOptions: {
        disabledDate: (time) => {
          return time.getTime() < Date.now() - 24 * 60 * 60 * 1000;
        },
      },
      OverDatePickerOptions: {
        disabledDate: (time) => {
          return time.getTime() < this.startDateTimeStamp - 24 * 60 * 60 * 1000;
        },
      },
      startDateTimeStamp: "",
      startTimePickerOptions: {
        selectableRange: "",
      },
      endTimePickerOptions: {
        selectableRange: "",
      },
      endTimeDisabled: true,
      startTimeDisabled: true,
      overDateDisabled: true,
      inline: false,
      examForm: {
        title: "",
        paperId: "",
        classId: "",
        examStudentIds: [],
        examStartDate: "",
        examOverDate: "",
        examStartTime: "",
        examEndTime: "",
        examTimeDuration: "",
        antiCheatList: [],
      },
      paperSelect: [],
      classSelect: [],
      studentSwitch: false,
      AssignVisible: false,
      loading: false,
      multipleSelectionAll: [],
      multipleSelection: [],
      studentList: [],
      idKey: "studentId",
      currentPage: 1,
      pageSize: 5,
      pageTotal: null,
      rules: {
        title: [{ required: true, message: "请输入考试标题", trigger: "blur" }],
        paperId: [
          { required: true, message: "请输入考试标题", trigger: "blur" },
        ],
      },

      examStatusForm: {
        examId: "",
        examTitle: "",
      },
      resultData: [],
      statusCurrentPage: 1,
      statusPageSize: 5,
      statusPageTotal: null,
    };
  },
  methods: {
    switchStudent() {
      if (this.studentSwitch) {
        this.examForm.classId = "";
      } else {
        this.examForm.examStudentIds = [];
      }
      console.log(this.examForm.examStudentIds);
      console.log(this.examForm.classId);
    },
    visibleChange(visible, refName, onClick) {
      if (visible) {
        const ref = this.$refs[refName];
        let popper = ref.$refs.popper;
        if (popper.$el) popper = popper.$el;
        if (
          !Array.from(popper.children).some(
            (v) => v.className === "el-cascader-menu__list"
          )
        ) {
          const el = document.createElement("ul");
          el.className = "el-cascader-menu__list";
          el.style =
            "border-top: solid 1px #E4E7ED; padding:0; color: #606266;";
          el.innerHTML = `<li class="el-cascader-node" style="height:38px;line-height: 38px;font-size:14px;">
<i class="el-icon-circle-plus-outline"></i>
<span class="el-cascader-node__label">全部试卷列表</span>
</li>`;
          popper.appendChild(el);
          el.onclick = () => {
            // 底部按钮的点击事件 点击后想触发的逻辑也可以直接写在这
            onClick && onClick();

            // 以下代码实现点击后弹层隐藏 不需要可以删掉
            if (ref.toggleDropDownVisible) {
              ref.toggleDropDownVisible(false);
            } else {
              ref.visible = false;
            }
          };
        }
      }
    },
    selectClick() {
      console.log("onCLick");
    },
    loadPaper() {
      axios({
        method: "post",
        url: "/api/paper/getAll",
      }).then((res) => {
        if (res.status === 200) {
          let result = res.data.data;
          this.paperSelect = result;
        }
      });
    },
    loadClass() {
      axios({
        method: "post",
        url: "/api/class/getAll",
      }).then((res) => {
        if (res.status === 200) {
          let result = res.data.data;
          this.classSelect = result;
        }
      });
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    handleCurrentChange(val) {
      this.changePageCoreRecordData();
      this.currentPage = val;
      this.searchStudentList();
    },
    assign() {
      this.AssignVisible = true;
      this.searchStudentList();
    },
    searchStudentList() {
      axios({
        method: "get",
        url: "/api/addToClass/student",
        params: {
          currentPage: this.currentPage,
          pageSize: this.pageSize,
        },
      }).then((res) => {
        this.studentList = [];
        this.loading = true;
        let result = res.data.data;
        let pageData = result.pageData;
        this.pageTotal = result.pageTotal;
        for (let i = 0; i < pageData.length; i++) {
          this.studentList.push({
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
      });
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
      this.studentList.forEach((row) => {
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
      this.$refs.studentList.clearSelection();
      for (let i = 0; i < this.studentList.length; i++) {
        if (selectAllIds.indexOf(this.studentList[i][idKey]) >= 0) {
          this.$refs.studentList.toggleRowSelection(this.studentList[i], true);
        }
      }
    },
    handleAssignStudent() {
      this.changePageCoreRecordData();
      if (this.multipleSelectionAll.length <= 0) {
        this.$message({
          showClose: true,
          message: "无勾选数据",
          type: "warning",
        });
      } else {
        this.examForm.examStudentIds = [];
        let students = this.multipleSelectionAll;
        for (let i = 0; i < students.length; i++) {
          this.examForm.examStudentIds.push(students[i].studentId);
        }
        this.$message({
          showClose: true,
          message: "指定成功！",
          type: "success",
        });
      }
      this.AssignVisible = false;
    },
    initStartTime() {
      if (this.examForm.examStartDate !== null) {
        this.examForm.examStartTime = "";
        let startDate = this.examForm.examStartDate;
        let newDate = new Date();
        let year = newDate.getFullYear();
        let month =
          newDate.getMonth() + 1 < 10
            ? "0" + (newDate.getMonth() + 1)
            : newDate.getMonth() + 1;
        let day =
          newDate.getDate() < 10 ? "0" + newDate.getDate() : newDate.getDate();
        let today = year + "-" + month + "-" + day;

        if (startDate === today) {
          let hour =
            newDate.getHours() < 10
              ? "0" + newDate.getHours()
              : newDate.getHours();
          let minute =
            newDate.getMinutes() < 10
              ? "0" + newDate.getMinutes()
              : newDate.getMinutes();
          let time = hour + ":" + minute + ":00 - 23:59:00";
          this.startTimePickerOptions.selectableRange = time;
        } else {
          this.startTimePickerOptions.selectableRange = "00:00:00 - 23:59:00";
        }
        this.startTimeDisabled = false;
        console.log(today);
      } else {
        this.examForm.examOverDate = null;
        this.examForm.examStartTime = null;
        this.examForm.examEndTime = null;
        this.overDateDisabled = true;
        this.startTimeDisabled = true;
        this.endTimeDisabled = true;
      }
    },
    initOverDate() {
      if (this.examForm.examStartTime !== null) {
        this.examForm.examOverDate = "";
        let startDateStamp = new Date(this.examForm.examStartDate);
        this.startDateTimeStamp = startDateStamp;
        this.overDateDisabled = false;
      } else {
        this.examForm.examEndTime = null;
        this.examForm.examOverDate = null;
        this.overDateDisabled = true;
        this.endTimeDisabled = true;
      }
    },
    initEndTime() {
      if (this.examForm.examOverDate !== null) {
        this.examForm.examEndTime = "";
        let startTime = this.examForm.examStartTime;
        if (this.examForm.examStartDate === this.examForm.examOverDate) {
          if (startTime !== null) {
            let limitTime = startTime.split(":");
            let hour = limitTime[0];
            let minute =
              parseInt(limitTime[1]) + 1 < 10
                ? "0" + (parseInt(limitTime[1]) + 1)
                : parseInt(limitTime[1]) + 1;
            let time = hour + ":" + minute + ":00 - 23:59:00";
            console.log(time);
            this.endTimePickerOptions.selectableRange = time;
            this.endTimeDisabled = false;
          }
        } else {
          this.endTimePickerOptions.selectableRange = "00:00:00 - 23:59:00";
          this.endTimeDisabled = false;
        }
      } else {
        this.examForm.examEndTime = null;
        this.endTimeDisabled = true;
      }
      console.log(this.endTimeDisabled);
    },
    reset() {
      this.examForm.title = "";
      this.examForm.paperId = "";
      this.examForm.classId = "";
      this.examForm.examStudentIds = [];
      this.examForm.examStartDate = "";
      this.examForm.examOverDate = "";
      this.examForm.examStartTime = "";
      this.examForm.examEndTime = "";
      this.examForm.examTimeDuration = "";
      this.examForm.antiCheatList = [];
      this.multipleSelectionAll = [];
    },
    publish() {
      let antiCheat = this.examForm.antiCheatList;
      let isRandom = 0;
      let isSnap = 0;

      if (antiCheat.includes("打乱题序")) {
        isRandom = 1;
      }
      if (antiCheat.includes("切屏提醒")) {
        isSnap = 1;
      }

      let startTime =
        this.examForm.examStartDate + " " + this.examForm.examStartTime + ":00";
      let endTime =
        this.examForm.examOverDate + " " + this.examForm.examEndTime + ":00";
      axios({
        method: "post",
        url: "/api/exam/add",
        data: {
          examTitle: this.examForm.title,
          paperId: this.examForm.paperId,
          classId: this.examForm.classId,
          examStudentIds: this.examForm.examStudentIds,
          examStartTime: startTime,
          examEndTime: endTime,
          examTimeDuration: this.examForm.examTimeDuration,
          isRandom: isRandom,
          isSnap: isSnap,
        },
      }).then((res) => {
        if (res.status === 200) {
          this.$message({
            showClose: true,
            message: "试卷已经成功发布！",
            type: "success",
          });
          this.reload();
          console.log(res);
        }
      });
    },

    handleStatusCurrentChange(val) {
      this.statusCurrentPage = val;
      this.searchExam();
    },
    searchExam() {
      axios({
        method: "post",
        url: "/api/exam/search",
        data: {
          examId: this.examStatusForm.examId,
          examTitle: this.examStatusForm.examTitle,
          currentPage: this.statusCurrentPage,
          pageSize: this.statusPageSize,
        },
      }).then((res) => {
        this.resultData = [];
        if (res.status === 200) {
          this.loading = true;
          let result = res.data.data;
          let exams = result.exams;
          let pageTotal = result.pageTotal;
          this.statusPageTotal = pageTotal;
          for (let i = 0; i < exams.length; i++) {
            this.resultData.push({
              examId: exams[i].examId,
              examTitle: exams[i].examTitle,
              timeLimit: exams[i].timeLimit,
              startTime: exams[i].startTime,
              overTime: exams[i].overTime,
              status: exams[i].status,
            });
          }
          this.loading = false;
        }
      });
    },

    endExam(index, rows) {
      let examId = rows[index].examId;
      this.$confirm("是否要提前终止考试？", "警告", {
        confirmButtonText: "终止",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        axios({
          method: "get",
          url: "/api/examPage/examStatus",
          params: {
            examId: examId,
          },
        })
          .then((res) => {
            if (res.status === 200) {
              this.$message({
                showClose: true,
                message: "已经终止考试",
                type: "success",
              });
              this.reload();
              this.activeName = "status";
            }
          })
          .catch(() => {
            this.$message({
              showClose: true,
              message: "取消操作",
              type: "warning",
            });
          });
      });
    },

    getLocalTab() {
      if (localStorage.getItem("tabs") != null) {
        this.activeName = localStorage.getItem("tabs");
      }
    },
    handleClick(tab) {
      localStorage.setItem("tabs", tab.name);
    },
  },
};
</script>

<style lang="scss" scoped>
.exam-container {
  padding: 15px;
  display: flex;
  flex-direction: column;
  height: 900px;
  text-align: left;
}
.el-form-item {
  display: flex;
}
.pageination {
  margin-top: 20px;
  display: flex;
  justify-content: center;
}
.status-container {
  padding: 15px;
  display: flex;
  flex-direction: column;
  height: 900px;
  text-align: left;
}
.examStatusForm {
  display: flex;
  flex-direction: row;
}
.el-form-item {
  display: flex;
  flex-direction: row;
  margin-right: 30px;
}
</style>