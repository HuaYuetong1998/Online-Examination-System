<template>
  <div class="student-situation-wrapper">
    <div class="situation-container">
      <el-form ref="situationForm" :model="situationForm" class="situationForm">
        <el-form-item label="学号：">
          <el-input v-model="situationForm.studentId"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="searchSituation">查询</el-button>
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
          <el-table-column prop="examTitle" label="考试名称"></el-table-column>
          <el-table-column prop="status" label="交卷情况" width="80">
            <template slot-scope="scope">
              <el-tag type="warning" v-if="scope.row.status == '0'"
                >未交卷</el-tag
              >
              <el-tag type="success" v-if="scope.row.status == '1'"
                >已交卷</el-tag
              >
            </template>
          </el-table-column>
          <el-table-column prop="correct" label="阅卷情况" width="80">
            <template slot-scope="scope">
              <el-tag type="warning" v-if="scope.row.correct == '0'"
                >未批卷</el-tag
              >
              <el-tag type="success" v-if="scope.row.correct == '1'"
                >已阅卷</el-tag
              >
            </template>
          </el-table-column>
          <el-table-column fixed="right" label="操作" width="240">
            <template slot-scope="scope">
              <el-button
                icon="el-icon-video-play"
                type="primary"
                size="small"
                @click.native.prevent="autoCorrect(scope.$index, resultData)"
                v-if="scope.row.correct == '0' && scope.row.status == '1'"
                >自动阅卷</el-button
              >
              <el-button
                icon="el-icon-edit"
                type="success"
                size="small"
                @click.native.prevent="turnCorrect(scope.$index, resultData)"
                v-if="scope.row.correct == '0' && scope.row.status == '1'"
                >人工改卷</el-button
              >
              <el-button
                icon="el-icon-view"
                type="warning"
                size="small"
                @click.native.prevent="turnDetail(scope.$index, resultData)"
                v-if="scope.row.correct == '1'"
                >阅卷情况</el-button
              >
              <el-button
                icon="el-icon-remove-outline"
                type="info"
                disabled
                size="small"
                v-if="scope.row.status == '0'"
              >
                学生暂未未交卷
              </el-button>
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
    <el-card class="card-info" v-if="situationForm.realName != ''">
      <span>当前学生：{{ situationForm.realName }}</span></el-card
    >
  </div>
</template>

<script>
import axios from "axios";
export default {
  inject: ["reload"],
  data() {
    return {
      isShow: false,
      situationForm: {
        studentId: "",
        realName: "",
      },
      resultData: [],
      loading: false,
      pageSize: 6,
      currentPage: 1,
      pageTotal: null,
    };
  },
  created: function () {
    let studentId = localStorage.getItem("studentId");
    if (studentId != null) {
      this.situationForm.studentId = studentId;
      this.searchSituation();
    } else {
      this.initStudent();
    }
  },
  methods: {
    authenJudge() {
      let role = this.$cookies.get("role");
      if (role === 0) {
        this.isShow = true;
      }
    },
    initStudent() {
      let studentId = this.$route.params.id;
      if (studentId != null) {
        this.situationForm.studentId = studentId;
        this.searchSituation();
      }
    },
    compare(property) {
      return (a, b) => {
        let value1 = a[property];
        let value2 = b[property];
        return value1 - value2;
      };
    },
    handleCurrentChange(val) {
      this.currentPage = val;
      this.searchSituation();
    },
    searchSituation() {
      axios({
        method: "get",
        url: "/api/student/situation",
        params: {
          studentId: this.situationForm.studentId,
        },
      }).then((res) => {
        if (res.status === 200) {
          console.log(res);
          this.situationForm.realName = res.data.data.realName;
          localStorage.setItem("studentId", this.situationForm.studentId);
          axios({
            method: "post",
            url: "/api/exam/situation",
            data: {
              studentId: this.situationForm.studentId,
              pageSize: this.pageSize,
              currentPage: this.currentPage,
            },
          }).then((res) => {
            this.resultData = [];
            if (res.status === 200) {
              let result = res.data.data;
              let examStudents = result.examStudents;
              let pageTotal = result.pageTotal;
              this.pageTotal = pageTotal;
              for (let i = 0; i < examStudents.length; i++) {
                this.resultData.push({
                  examId: examStudents[i].examId,
                  examTitle: examStudents[i].examTitle,
                  status: examStudents[i].status,
                  correct: examStudents[i].correct,
                });
              }
              this.resultData.sort(this.compare("examId"));
            }
          });
        }
      });
    },

    // 自动阅卷
    autoCorrect(index, rows) {
      let studentId = this.situationForm.studentId;
      let examId = rows[index].examId;
      axios({
        method: "get",
        url: "/api/exam/getAnswerSheet",
        params: {
          studentId: studentId,
          examId: examId,
        },
      }).then((res) => {
        if (res.status === 200) {
          let result = res.data.data;
          if (result != null) {
            let answerSheet = JSON.parse(result);
            let choice = answerSheet.select;
            let fill = answerSheet.fill;
            let judge = answerSheet.judge;
            let subjective = answerSheet.subjective;

            let count = 0;

            const loading = this.$loading({
              lock: true,
              text: "自动阅卷中，请等待...",
              spinner: "el-icon-loading",
              background: "rgba(0, 0, 0, 0.7)",
            });

            if (choice.length > 0) {
              for (let i = 0; i < choice.length; i++) {
                if (choice[i].questionId != "") {
                  axios({
                    method: "get",
                    url: "/api/exam/getChoiceRightAnswer",
                    params: {
                      questionId: choice[i].questionId,
                    },
                  }).then((res) => {
                    if (res.status === 200) {
                      let result = res.data.data;
                      let rightAnswer = result.rightAnswer;
                      if (choice[i].selected === rightAnswer) {
                        count += result.score;
                      }
                      console.log("choice count" + count);
                    }
                  });
                }
              }
            }

            if (fill.length > 0) {
              for (let i = 0; i < fill.length; i++) {
                if (fill[i].questionId != "") {
                  axios({
                    method: "get",
                    url: "/api/exam/getFillRightAnswer",
                    params: {
                      questionId: fill[i].questionId,
                    },
                  }).then((res) => {
                    if (res.status === 200) {
                      let result = res.data.data;
                      let rightAnswer = result.answer;
                      if (fill[i].filled === rightAnswer) {
                        count += result.score;
                      }
                    }
                  });
                }
              }
            }

            if (judge.length > 0) {
              for (let i = 0; i < judge.length; i++) {
                if (judge[i].questionId != "") {
                  axios({
                    method: "get",
                    url: "/api/exam/getJudgeRightAnswer",
                    params: {
                      questionId: judge[i].questionId,
                    },
                  }).then((res) => {
                    if (res.status === 200) {
                      let result = res.data.data;
                      console.log(result);
                      let rightAnswer = result.answer;
                      if (judge[i].judged === rightAnswer) {
                        count += result.score;
                      }
                    }
                  });
                }
              }
            }

            if (subjective.length > 0) {
              for (let i = 0; i < subjective.length; i++) {
                if (subjective[i].questionId != "") {
                  axios({
                    method: "get",
                    url: "/api/exam/getSubjectiveRightAnswer",
                    params: {
                      questionId: subjective[i].questionId,
                    },
                  }).then((res) => {
                    if (res.status === 200) {
                      let result = res.data.data;
                      console.log(result);
                      let rightAnswer = result.answer;
                      if (subjective[i].subjectived === rightAnswer) {
                        count += result.score;
                      }
                    }
                  });
                }
              }
            }

            setTimeout(() => {
              loading.close();
              this.$notify({
                title: "自动批改成功！",
                message: "总计得分：" + count,
                type: "success",
                position: "top-left",
              });
              axios({
                method: "post",
                url: "/api/exam/updateCorrect",
                data: {
                  studentId: studentId,
                  examId: examId,
                },
              }).then((res) => {
                if (res.status === 200) {
                  axios({
                    method: "get",
                    url: "/api/exam/getSubmitTime",
                    params: {
                      studentId: studentId,
                      examId: examId,
                    },
                  }).then((res) => {
                    if (res.status === 200) {
                      let submitTime = res.data.data;
                      axios({
                        method: "post",
                        url: "/api/score/save",
                        data: {
                          examId: examId,
                          studentId: studentId,
                          score: count,
                          examTime: submitTime,
                        },
                      }).then((res) => {
                        if (res.status === 200) {
                          console.log("录入成功");
                        }
                      });
                    }
                  });
                  this.reload();
                }
              });
            }, 2000);
          }
        }
      });
    },

    // 跳转到人工阅卷
    turnCorrect(index, rows) {
      let studentId = this.situationForm.studentId;
      let examId = rows[index].examId;
      this.$router.push({
        name: "correctPaper",
        query: {
          studentId: studentId,
          examId: examId,
        },
      });
    },

    turnDetail(index, rows) {
      let studentId = this.situationForm.studentId;
      let examId = rows[index].examId;
    },
  },

  beforeRouteLeave(to, from, next) {
    localStorage.removeItem("studentId");
    next();
  },
};
</script>

<style lang="scss" scoped>
.student-situation-wrapper {
  padding: 20px;
}
.situation-container {
  padding: 15px;
  display: flex;
  flex-direction: column;
}
.situationForm {
  display: flex;
  flex-direction: row;
}
.el-form-item {
  display: flex;
  flex-direction: row;
  margin-right: 30px;
}
.card-info {
  position: fixed;
  top: 110px;
  right: 100px;
  width: 400px;
}
.pageination {
  margin-top: 20px;
}
</style>