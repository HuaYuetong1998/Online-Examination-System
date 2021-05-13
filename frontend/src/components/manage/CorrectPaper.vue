<template>
  <div class="paper-correct-wrapper">
    <div class="paper-correct-container">
      <div class="exam-title">
        {{ examInfo.examTitle }}
        <div
          style="
            font-weight: 600;
            font-size: 20px;
            color: red;
            margin-top: 20px;
          "
        >
          总分：{{ examInfo.totalScore }} 分（当前得分：{{ getScore }}）
        </div>
      </div>
      <el-divider></el-divider>
      <el-scrollbar>
        <div class="correct-main">
          <div class="choice-part" v-if="paperInfo.choiceNum > 0">
            <div
              class="title"
              style="display: flex; margin-left: 2%; margin-bottom: 20px"
            >
              <i class="el-icon-star-on"></i>选择题
            </div>
            <el-card
              class="question-item"
              v-for="(item, index) in paperInfo.choiceQuestion"
              :key="index"
            >
              <div class="question-box">
                <div class="order">{{ index + 1 }}.</div>
                <div class="container">
                  <div class="question">
                    {{ item.question }} （{{ item.score }}）分
                  </div>
                  <div class="write">
                    <el-radio-group
                      v-model="answerSheet.select[index].selected"
                      disabled
                    >
                      <el-radio label="a" style="margin-bottom: 5px"
                        >A.{{ item.answerA }}</el-radio
                      >
                      <el-radio label="b" style="margin-bottom: 5px"
                        >B.{{ item.answerB }}</el-radio
                      >
                      <el-radio label="c" style="margin-bottom: 5px"
                        >C.{{ item.answerC }}</el-radio
                      >
                      <el-radio label="d" style="margin-bottom: 5px"
                        >D.{{ item.answerD }}</el-radio
                      >
                    </el-radio-group>
                  </div>
                  <div class="studentAnswer">
                    学生作答：{{ answerSheet.select[index].selected }}
                  </div>
                  <div class="answer">标准答案：{{ item.rightAnswer }}</div>

                  <div class="correct">
                    得分：
                    <el-input-number
                      v-model="score.select[index]"
                      @change="handleChange"
                      size="small"
                      :min="0"
                      :max="item.score"
                    ></el-input-number>
                  </div>
                </div>
              </div>
            </el-card>
          </div>

          <div class="fill-part" v-if="paperInfo.fillNum > 0">
            <div
              class="title"
              style="display: flex; margin-left: 2%; margin-bottom: 20px"
            >
              <i class="el-icon-star-on"></i>填空题
            </div>
            <el-card
              class="question-item"
              v-for="(item, index) in paperInfo.fillQuestion"
              :key="index"
            >
              <div class="question-box">
                <div class="order">{{ index + 1 }}.</div>
                <div class="container">
                  <div class="question">
                    {{ item.question }} （{{ item.score }}）分
                  </div>
                  <div class="studentAnswer">
                    学生作答：{{ answerSheet.fill[index].filled }}
                  </div>
                  <div class="answer">标准答案：{{ item.rightAnswer }}</div>

                  <div class="correct">
                    得分：
                    <el-input-number
                      v-model="score.fill[index]"
                      @change="handleChange"
                      size="small"
                      :min="0"
                      :max="item.score"
                    ></el-input-number>
                  </div>
                </div>
              </div>
            </el-card>
          </div>

          <div class="fill-part" v-if="paperInfo.judgeNum > 0">
            <div
              class="title"
              style="display: flex; margin-left: 2%; margin-bottom: 20px"
            >
              <i class="el-icon-star-on"></i>判断题
            </div>
            <el-card
              class="question-item"
              v-for="(item, index) in paperInfo.judgeQuestion"
              :key="index"
            >
              <div class="question-box">
                <div class="order">{{ index + 1 }}.</div>
                <div class="container">
                  <div class="question">
                    {{ item.question }} （{{ item.score }}）分
                  </div>
                  <div class="studentAnswer">
                    学生作答：{{ answerSheet.judge[index].judged }}
                  </div>
                  <div class="answer">标准答案：{{ item.rightAnswer }}</div>

                  <div class="correct">
                    得分：
                    <el-input-number
                      v-model="score.judge[index]"
                      @change="handleChange"
                      size="small"
                      :min="0"
                      :max="item.score"
                    ></el-input-number>
                  </div>
                </div>
              </div>
            </el-card>
          </div>

          <div class="subjective-part" v-if="paperInfo.subjectiveNum > 0">
            <div
              class="title"
              style="display: flex; margin-left: 2%; margin-bottom: 20px"
            >
              <i class="el-icon-star-on"></i>主观题
            </div>
            <el-card
              class="question-item"
              v-for="(item, index) in paperInfo.subjectiveQuestion"
              :key="index"
            >
              <div class="question-box">
                <div class="order">{{ index + 1 }}.</div>
                <div class="container">
                  <div class="question">
                    {{ item.question }} （{{ item.score }}）分
                  </div>
                  <div class="studentAnswer">
                    学生作答：{{ answerSheet.subjective[index].subjectived }}
                  </div>
                  <div class="answer">标准答案：{{ item.rightAnswer }}</div>

                  <div class="correct">
                    得分：
                    <el-input-number
                      v-model="score.subjective[index]"
                      @change="handleChange"
                      size="small"
                      :min="0"
                      :max="item.score"
                    ></el-input-number>
                  </div>
                </div>
              </div>
            </el-card>
          </div>
        </div>
      </el-scrollbar>
    </div>

    <div class="submitCorrect" @click="submitCorrect">提交批阅</div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      examInfo: {
        examTitle: "",
        totalScore: 0,
      },
      paperInfo: {
        choiceNum: "",
        fillNum: "",
        judgeNum: "",
        subjectiveNum: "",
        choiceQuestion: [],
        fillQuestion: [],
        judgeQuestion: [],
        subjectiveQuestion: [],
        choiceTotalScore: 0,
        fillTotalScore: 0,
        judgeTotalScore: 0,
        subjectiveTotalScore: 0,
      },
      answerSheet: [],
      checkedAnswer: [],
      score: {
        select: [],
        fill: [],
        judge: [],
        subjective: [],
      },
      getScore: 0,
    };
  },
  created: function () {
    this.getInfo();
  },
  methods: {
    handleChange(value) {
      console.log(value);
      this.currentScore();
    },
    currentScore() {
      this.getScore = 0;
      for (let i = 0; i < this.score.select.length; i++) {
        this.getScore += this.score.select[i];
      }
      for (let i = 0; i < this.score.fill.length; i++) {
        this.getScore += this.score.fill[i];
      }
      for (let i = 0; i < this.score.judge.length; i++) {
        this.getScore += this.score.judge[i];
      }
      for (let i = 0; i < this.score.subjective.length; i++) {
        this.getScore += this.score.subjective[i];
      }
      console.log();
    },
    getInfo() {
      let studentId = this.$route.query.studentId;
      let examId = this.$route.query.examId;
      axios({
        method: "get",
        url: "/api/examPage/getExamInfo",
        params: {
          examId: this.$route.query.examId,
        },
      }).then((res) => {
        if (res.status === 200) {
          let result = res.data.data;
          let examData = result.examData;
          let paperData = result.paperData;
          this.examInfo.examTitle = examData.examTitle;
          this.examInfo.totalScore = paperData.totalScore;
          this.paperInfo.choiceNum = paperData.choiceNum;
          this.paperInfo.fillNum = paperData.fillNum;
          this.paperInfo.judgeNum = paperData.judgeNum;
          this.paperInfo.subjectiveNum = paperData.subjectiveNum;

          if (paperData.paperId != null) {
            axios({
              method: "get",
              url: "/api/examPage/getQuestions",
              params: {
                paperId: paperData.paperId,
              },
            }).then((res) => {
              if (res.status === 200) {
                let questionRes = res.data.data;
                let choiceQuestions = questionRes.choiceQuestions;
                let fillQuestions = questionRes.fillQuestions;
                let judgeQuestions = questionRes.judgeQuestions;
                let subjectiveQuestions = questionRes.subjectiveQuestions;
                let choiceTotalScore = 0;
                let fillTotalScore = 0;
                let judgeTotalScore = 0;
                let subjectiveTotalScore = 0;

                if (choiceQuestions != null) {
                  for (let i = 0; i < choiceQuestions.length; i++) {
                    this.paperInfo.choiceQuestion.push({
                      question: choiceQuestions[i].question,
                      answerA: choiceQuestions[i].answerA,
                      answerB: choiceQuestions[i].answerB,
                      answerC: choiceQuestions[i].answerC,
                      answerD: choiceQuestions[i].answerD,
                      rightAnswer: choiceQuestions[i].rightAnswer,
                      score: choiceQuestions[i].score,
                      questionId: choiceQuestions[i].questionId,
                    });
                    choiceTotalScore += choiceQuestions[i].score;
                    this.score.select[i] = 0;
                  }
                  this.paperInfo.choiceTotalScore = choiceTotalScore;
                }

                if (fillQuestions != null) {
                  for (let i = 0; i < fillQuestions.length; i++) {
                    this.paperInfo.fillQuestion.push({
                      question: fillQuestions[i].question,
                      score: fillQuestions[i].score,
                      rightAnswer: fillQuestions[i].answer,
                      questionId: fillQuestions[i].questionId,
                    });
                    fillTotalScore += fillQuestions[i].score;
                    this.score.fill[i] = 0;
                  }
                  this.paperInfo.fillTotalScore = fillTotalScore;
                }

                if (judgeQuestions != null) {
                  for (let i = 0; i < judgeQuestions.length; i++) {
                    this.paperInfo.judgeQuestion.push({
                      question: judgeQuestions[i].question,
                      score: judgeQuestions[i].score,
                      rightAnswer: judgeQuestions[i].answer,
                      questionId: judgeQuestions[i].questionId,
                    });
                    judgeTotalScore += judgeQuestions[i].score;
                    this.score.judge[i] = 0;
                  }
                  this.paperInfo.judgeTotalScore = judgeTotalScore;
                }

                if (subjectiveQuestions != null) {
                  for (let i = 0; i < subjectiveQuestions.length; i++) {
                    this.paperInfo.subjectiveQuestion.push({
                      question: subjectiveQuestions[i].question,
                      score: subjectiveQuestions[i].score,
                      rightAnswer: subjectiveQuestions[i].answer,
                      questionId: subjectiveQuestions[i].questionId,
                    });
                    subjectiveTotalScore += subjectiveQuestions[i].score;
                    this.score.subjective[i] = 0;
                  }
                  this.paperInfo.subjectiveTotalScore = subjectiveTotalScore;
                }
                axios({
                  method: "get",
                  url: "/api/exam/getAnswerSheet",
                  params: {
                    studentId: studentId,
                    examId: examId,
                  },
                }).then((res) => {
                  if (res.status === 200) {
                    let answerSheet = JSON.parse(res.data.data);
                    this.answerSheet = answerSheet;
                    console.log(answerSheet);
                  }
                });
                console.log(judgeQuestions);
              }
            });
          }
        }
      });
    },
    submitCorrect() {
      let studentId = this.$route.query.studentId;
      let examId = this.$route.query.examId;
      let score = this.getScore;
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
                  score: score,
                  examTime: submitTime,
                },
              }).then((res) => {
                console.log(res);
                if (res.status === 200) {
                  this.$message({
                    showClose: true,
                    message: "批改已提交！",
                    type: "success",
                  });
                  this.$router.push({
                    name: "studentSituation",
                    params: {
                      id: studentId,
                    },
                  });
                }
              });
            }
          });
        }
      });
    },
  },
};
</script>

<style lang="scss" scoped>
.paper-correct-container {
  padding: 20px;
}
.exam-title {
  font-weight: 600;
  font-size: 26px;
}
.correct-main {
  width: 100%;
  height: 480px;
}
.el-scrollbar {
  height: 100%;
}
.el-scrollbar .el-scrollbar__wrap {
  overflow-x: auto;
}
.question-item {
  width: 95%;
  margin: 0 auto;
  margin-bottom: 10px;
}
.question-box {
  width: 1000px;
  padding: 10px;
  display: flex;
  flex-direction: row;
}
.order {
  margin-right: 10px;
}
.container {
  display: flex;
  flex-direction: column;
  text-align: left;
}
.question {
  text-align: left;
  word-break: break-all;
}
.write {
  margin-top: 20px;
}
.studentAnswer {
  margin-top: 20px;
  font-weight: 600;
}
.answer {
  margin-top: 10px;
  font-weight: 600;
  color: #ff3366;
}
.correct {
  margin-top: 20px;
}
.submitCorrect {
  width: 80px;
  height: 80px;
  position: fixed;
  top: 280px;
  right: 60px;
  border: 1px solid #a0cfff;
  line-height: 80px;
  background: #409eff;
  color: #fff;
  border-radius: 50%;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
  cursor: pointer;
}
.submitCorrect:hover {
  background: #66b1ff;
  border-color: #66b1ff;
  color: #fff;
}
</style>