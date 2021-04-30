<template>
  <div class="exam-wrapper">
    <el-tabs type="border-card">
      <el-tab-pane label="考试发布">
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

            <el-form-item label="选择时间：" prop="examStartTime">
              <el-date-picker
                v-model="examForm.examDate"
                type="date"
                placeholder="选择日期"
                value-format="yyyy-MM-dd"
                :picker-options="datePickerOptions"
                style="margin-right: 20px"
                @change="initStartTime"
              >
              </el-date-picker>
              <el-time-picker
                :disabled="startTimeDisabled"
                v-model="examForm.examStartTime"
                placeholder="选择开始时间"
                format="HH:mm"
                value-format="HH:mm"
                :picker-options="startTimePickerOptions"
                @change="initEndTime"
                style="margin-right: 10px"
              >
              </el-time-picker>
              ~
              <el-time-picker
                :disabled="endTimeDisabled"
                v-model="examForm.examEndTime"
                placeholder="选择结束时间"
                format="HH:mm"
                value-format="HH:mm"
                :picker-options="endTimePickerOptions"
                style="margin-left: 10px"
                @change="countExamTime"
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
      <el-tab-pane label="考试状态管理">考试状态</el-tab-pane>
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
  },
  data() {
    return {
      datePickerOptions: {
        disabledDate: (time) => {
          return time.getTime() < Date.now() - 24 * 60 * 60 * 1000;
        },
      },
      startTimePickerOptions: {
        selectableRange: "",
      },
      endTimePickerOptions: {
        selectableRange: "",
      },
      endTimeDisabled: true,
      startTimeDisabled: true,
      inline: false,
      examForm: {
        title: "",
        paperId: "",
        classId: "",
        examStudentIds: [],
        examDate: "",
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
      if (this.examForm.examDate !== null) {
        this.examForm.examStartTime = "";
        let date = this.examForm.examDate;
        let newDate = new Date();
        let year = newDate.getFullYear();
        let month =
          newDate.getMonth() + 1 < 10
            ? "0" + (newDate.getMonth() + 1)
            : newDate.getMonth() + 1;
        let day =
          newDate.getDate() < 10 ? "0" + newDate.getDate() : newDate.getDate();
        let today = year + "-" + month + "-" + day;

        if (date === today) {
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
        console.log(date);
        console.log(today);
      } else {
        this.examForm.examStartTime = null;
        this.examForm.examEndTime = null;
        this.startTimeDisabled = true;
        this.endTimeDisabled = true;
      }
    },
    initEndTime() {
      if (this.examForm.examStartTime !== "") {
        this.examForm.examEndTime = "";
        let startTime = this.examForm.examStartTime;
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
        } else {
          this.endTimeDisabled = true;
        }
      }
    },
    countExamTime() {
      let startTime = this.examForm.examStartTime;
      let endTime = this.examForm.examEndTime;
      console.log(startTime);
      console.log(endTime);
      if (startTime !== null) {
        if (endTime !== null) {
          let startArr = startTime.split(":");
          let endArr = endTime.split(":");
          let startHour = parseInt(startArr[0]);
          let endHour = parseInt(endArr[0]);
          let startMinute = parseInt(startArr[1]);
          let endMinute = parseInt(endArr[1]);

          let hourCount = endHour - startHour;
          let minCount = endMinute - startMinute;

          this.examForm.examTimeDuration = hourCount * 60 + minCount;
        }
      }
    },
    reset() {
      this.examForm.title = "";
      this.examForm.paperId = "";
      this.examForm.classId = "";
      this.examForm.examStudentIds = [];
      this.examForm.examDate = "";
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
        this.examForm.examDate + " " + this.examForm.examStartTime + ":00";
      let endTime =
        this.examForm.examDate + " " + this.examForm.examEndTime + ":00";
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
</style>