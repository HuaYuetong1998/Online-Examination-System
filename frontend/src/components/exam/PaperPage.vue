<template>
  <div
    class="examPage-wrapper"
    oncopy="return false"
    onpaste="return false"
    oncut="return false"
  >
    <div class="exam-page">
      <el-card class="left-menu">
        <div class="title">考试卡</div>
        <el-divider></el-divider>
        <div class="exam-info">
          <span style="margin-right: 30%"
            >总分：{{ examInfo.totalScore }} 分</span
          ><span>时长：{{ examInfo.timeLimit }} 分钟</span>
        </div>
        <div style="margin-top: 20px">
          倒计时：<span style="color: red; font-size: 20px">{{
            timeCountDown.hour
              ? hourString + ":" + minuteString + ":" + secondString
              : minuteString + ":" + secondString
          }}</span>
        </div>
        <el-divider></el-divider>

        <div style="height: 380px">
          <el-scrollbar>
            <div class="answer-card" v-if="paperInfo.choiceNum > 0">
              <div class="answer-list">
                <div class="list-title">选择题</div>
                <div class="block-list">
                  <a
                    v-bind:class="[
                      answerListener.choiceListen[index - 1]
                        ? 'answerBlockActive'
                        : 'answerBlock',
                    ]"
                    v-for="index of paperInfo.choiceNum"
                    :key="index"
                    @click="anchor('choiceQuestion', index)"
                    >{{ index }}</a
                  >
                </div>
              </div>

              <div class="answer-list" v-if="paperInfo.fillNum > 0">
                <div class="list-title">填空题</div>
                <div class="block-list">
                  <a
                    v-bind:class="[
                      answerListener.fillListen[index - 1]
                        ? 'answerBlockActive'
                        : 'answerBlock',
                    ]"
                    v-for="index of paperInfo.fillNum"
                    :key="index"
                    @click="anchor('fillQuestion', index)"
                    >{{ index }}</a
                  >
                </div>
              </div>

              <div class="answer-list" v-if="paperInfo.judgeNum > 0">
                <div class="list-title">判断题</div>
                <div class="block-list">
                  <a
                    v-bind:class="[
                      answerListener.judgeListen[index - 1]
                        ? 'answerBlockActive'
                        : 'answerBlock',
                    ]"
                    v-for="index of paperInfo.judgeNum"
                    :key="index"
                    @click="anchor('judgeQuestion', index)"
                    >{{ index }}</a
                  >
                </div>
              </div>

              <div class="answer-list" v-if="paperInfo.subjectiveNum > 0">
                <div class="list-title">主观题</div>
                <div class="block-list">
                  <a
                    v-bind:class="[
                      answerListener.subjectiveListen[index - 1]
                        ? 'answerBlockActive'
                        : 'answerBlock',
                    ]"
                    v-for="index of paperInfo.subjectiveNum"
                    :key="index"
                    @click="anchor('subjectiveQuestion', index)"
                    >{{ index }}</a
                  >
                </div>
              </div>
            </div>
          </el-scrollbar>
        </div>

        <el-button class="submit" type="primary" @click="submitPaper"
          >交卷</el-button
        >
      </el-card>
      <el-card class="paper-container">
        <div class="exam-title">{{ examInfo.examTitle }}</div>
        <el-divider></el-divider>
        <el-scrollbar>
          <div class="exam-question-container">
            <div
              class="question-title"
              v-if="paperInfo.choiceQuestion.length > 0"
            >
              <span style="margin-right: 10px; margin-left: 10px"
                ><i class="el-icon-star-on"></i></span
              >选择题（共{{ paperInfo.choiceNum }}题，{{
                paperInfo.choiceTotalScore
              }}分）
            </div>
            <div class="questions" v-if="paperInfo.choiceNum > 0">
              <div
                class="question-Item"
                :id="'choiceQuestion' + (index + 1)"
                v-for="(item, index) in paperInfo.choiceQuestion"
                :key="index"
              >
                <div class="stem">
                  <span style="margin-right: 10px">{{ index + 1 }}.</span
                  ><span style="margin-right: 10px"
                    >{{ item.question }} <b>（{{ item.score }}分）</b></span
                  >
                </div>
                <el-radio-group
                  v-model="answerSheet.select[index].selected"
                  class="selectItem"
                  @change="selectListener('choiceListen', index)"
                >
                  <el-radio label="a">A.{{ item.answerA }}</el-radio>
                  <el-radio label="b">B.{{ item.answerB }}</el-radio>
                  <el-radio label="c">C.{{ item.answerC }}</el-radio>
                  <el-radio label="d">D.{{ item.answerD }}</el-radio>
                </el-radio-group>
              </div>
            </div>

            <div class="question-title" v-if="paperInfo.fillNum > 0">
              <span style="margin-right: 10px; margin-left: 10px"
                ><i class="el-icon-star-on"></i></span
              >填空题（共{{ paperInfo.fillNum }}题，{{
                paperInfo.fillTotalScore
              }}分）
            </div>
            <div class="questions">
              <div
                :id="'fillQuestion' + (index + 1)"
                class="question-Item"
                v-for="(item, index) in paperInfo.fillQuestion"
                :key="index"
              >
                <div class="stem">
                  <span style="margin-right: 10px">{{ index + 1 }}.</span
                  ><span style="margin-right: 10px"
                    >{{ item.question }} <b>（{{ item.score }}分）</b></span
                  >
                </div>
                <el-input
                  placeholder="请输入答案"
                  v-model="answerSheet.fill[index].filled"
                  clearable
                  class="fillBlank"
                  @change="selectListener('fillListen', index)"
                ></el-input>
              </div>
            </div>

            <div class="question-title" v-if="paperInfo.judgeNum > 0">
              <span style="margin-right: 10px; margin-left: 10px"
                ><i class="el-icon-star-on"></i></span
              >判断题（共{{ paperInfo.judgeNum }}题，{{
                paperInfo.judgeTotalScore
              }}分）
            </div>
            <div class="questions">
              <div
                :id="'judgeQuestion' + (index + 1)"
                class="question-Item"
                v-for="(item, index) in paperInfo.judgeQuestion"
                :key="index"
              >
                <div class="stem">
                  <span style="margin-right: 10px">{{ index + 1 }}.</span
                  ><span style="margin-right: 10px"
                    >{{ item.question }}<b>（{{ item.score }}分）</b></span
                  >
                </div>
                <el-radio-group
                  v-model="answerSheet.judge[index].judged"
                  class="selectItem"
                  @change="selectListener('judgeListen', index)"
                >
                  <el-radio label="T">正确</el-radio>
                  <el-radio label="F">错误</el-radio>
                </el-radio-group>
              </div>
            </div>

            <div class="question-title" v-if="paperInfo.subjectiveNum > 0">
              <span style="margin-right: 10px; margin-left: 10px"
                ><i class="el-icon-star-on"></i></span
              >主观题（共{{ paperInfo.subjectiveNum }}题，{{
                paperInfo.subjectiveTotalScore
              }}分）
            </div>
            <div class="questions">
              <div
                :id="'subjectiveQuestion' + (index + 1)"
                class="question-Item"
                v-for="(item, index) in paperInfo.subjectiveQuestion"
                :key="index"
              >
                <div class="stem">
                  <span style="margin-right: 10px">{{ index + 1 }}.</span
                  ><span style="margin-right: 10px"
                    >{{ item.question }} <b>（{{ item.score }}分）</b></span
                  >
                </div>
                <el-input
                  class="subjectiveBlank"
                  type="textarea"
                  placeholder="在此填写答案"
                  v-model="answerSheet.subjective[index].subjectived"
                  resize="none"
                  :autosize="{ minRows: 3, maxRows: 5 }"
                  @change="selectListener('subjectiveListen', index)"
                >
                </el-input>
              </div>
            </div>
          </div>
        </el-scrollbar>
      </el-card>
    </div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "paperPage",
  data() {
    return {
      examId: "",
      examInfo: {
        totalScore: "",
        timeLimit: "",
        remainSecond: "",
        examTitle: "",
        isRandom: "",
        isSnap: "",
      },
      timeCountDown: {
        hour: 0,
        minute: 0,
        second: 0,
        promiseTimer: "",
      },
      answerListener: {
        choiceListen: [],
        fillListen: [],
        judgeListen: [],
        subjectiveListen: [],
      },
      paperInfo: {
        choiceNum: "",
        fillNum: "",
        judgeNum: "",
        subjectiveNum: "",
        choiceTotalScore: "",
        fillTotalScore: "",
        judgeTotalScore: "",
        subjectiveTotalScore: "",
        choiceQuestion: [],
        fillQuestion: [],
        judgeQuestion: [],
        subjectiveQuestion: [],
      },
      answerSheet: {
        select: [],
        fill: [],
        judge: [],
        subjective: [],
      },
    };
  },
  created() {
    this.examId = this.$route.query.examId;

    this.getPaper();
    this.snap();
  },

  methods: {
    //获取试卷
    getPaper() {
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
          this.examInfo.timeLimit = examData.timeLimit;
          this.examInfo.totalScore = paperData.totalScore;
          this.examInfo.remainSecond = examData.timeLimit * 60;
          this.examInfo.isRandom = examData.isRandom;
          this.examInfo.isSnap = examData.isSnap;
          this.paperInfo.choiceNum = paperData.choiceNum;
          this.paperInfo.fillNum = paperData.fillNum;
          this.paperInfo.judgeNum = paperData.judgeNum;
          this.paperInfo.subjectiveNum = paperData.subjectiveNum;

          this.initTime();
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
                      score: choiceQuestions[i].score,
                      questionId: choiceQuestions[i].questionId,
                    });
                    choiceTotalScore += choiceQuestions[i].score;
                    this.answerListener.choiceListen[i] = false;
                  }
                  /* if (examData.isRandom === 1) {
                    this.paperInfo.choiceQuestion = this.shuffle(
                      this.paperInfo.choiceQuestion
                    );
                  } */
                  this.paperInfo.choiceTotalScore = choiceTotalScore;
                }

                if (fillQuestions != null) {
                  for (let i = 0; i < fillQuestions.length; i++) {
                    this.paperInfo.fillQuestion.push({
                      question: fillQuestions[i].question,
                      score: fillQuestions[i].score,
                      questionId: fillQuestions[i].questionId,
                    });
                    fillTotalScore += fillQuestions[i].score;
                    this.answerListener.fillListen[i] = false;
                  }
                  this.paperInfo.fillTotalScore = fillTotalScore;
                }

                if (judgeQuestions != null) {
                  for (let i = 0; i < judgeQuestions.length; i++) {
                    this.paperInfo.judgeQuestion.push({
                      question: judgeQuestions[i].question,
                      score: judgeQuestions[i].score,
                      questionId: judgeQuestions[i].questionId,
                    });
                    judgeTotalScore += judgeQuestions[i].score;
                    this.answerListener.judgeListen[i] = false;
                  }
                  this.paperInfo.judgeTotalScore = judgeTotalScore;
                }

                if (subjectiveQuestions != null) {
                  for (let i = 0; i < subjectiveQuestions.length; i++) {
                    this.paperInfo.subjectiveQuestion.push({
                      question: subjectiveQuestions[i].question,
                      score: subjectiveQuestions[i].score,
                      questionId: subjectiveQuestions[i].questionId,
                    });
                    subjectiveTotalScore += subjectiveQuestions[i].score;
                    this.answerListener.subjectiveListen[i] = false;
                  }
                  this.paperInfo.subjectiveTotalScore = subjectiveTotalScore;
                }

                // console.log(questionRes);

                this.initAnswerSheet();
              }
            });
          }
          if (this.examInfo.isSnap === 1) {
            this.snap();
          } else {
            console.log("isSnapTrue");
          }
        }
      });
    },

    //数组随机洗牌
    shuffle(array) {
      let len = array.length,
        temp,
        index;
      while (len) {
        index = Math.floor(Math.random() * len--);
        temp = array[len];
        array[len] = array[index];
        array[index] = temp;
      }
      return array;
    },
    // 切屏提示
    snap() {
      let count = 0;
      window.onblur = function () {
        document.title = "请你不要作弊";
        count += 1;
        /* if (count > 3) {
          console.log("自动交卷！");
        } else {
          console.log(count);
          alert(
            "请勿离开考试界面，离开三次以上将会自动交卷!你已经离开了" +
              count +
              "次"
          );
        } */
      };
    },

    // 初始化答题数据
    initAnswerSheet() {
      let choiceLen = this.paperInfo.choiceQuestion.length;
      let fillLen = this.paperInfo.fillQuestion.length;
      let judgeLen = this.paperInfo.judgeQuestion.length;
      let subjectiveLen = this.paperInfo.subjectiveQuestion.length;
      if (choiceLen > 0) {
        for (let i = 0; i < choiceLen; i++) {
          this.answerSheet.select.push({
            questionId: "",
            selected: "",
          });
        }
      }
      if (fillLen > 0) {
        for (let i = 0; i < fillLen; i++) {
          this.answerSheet.fill.push({
            questionId: "",
            filled: "",
          });
        }
      }
      if (judgeLen > 0) {
        for (let i = 0; i < judgeLen; i++) {
          this.answerSheet.judge.push({
            questionId: "",
            judged: "",
          });
        }
      }
      if (subjectiveLen > 0) {
        for (let i = 0; i < subjectiveLen; i++) {
          this.answerSheet.subjective.push({
            questionId: "",
            subjectived: "",
          });
        }
      }
      let answerSheet = localStorage.getItem("answerSheet");
      let hasWriten = localStorage.getItem("hasWriten");
      if (answerSheet != null) {
        this.answerSheet = JSON.parse(answerSheet);
      }
      if (hasWriten != null) {
        this.answerListener = JSON.parse(hasWriten);
      }
    },
    // 初始化计时器
    initTime() {
      let remainSecond = localStorage.getItem("remainSecond");
      if (remainSecond != null) {
        this.examInfo.remainSecond = remainSecond;
      } else {
        this.examInfo.remainSecond = this.examInfo.timeLimit * 60;
        localStorage.setItem("remainSecond", this.examInfo.remainSecond);
      }
      this.countDown(this.examInfo.remainSecond);
    },
    // 题目锚点
    anchor(questionType, index) {
      document.querySelector(`#${questionType}${index}`).scrollIntoView({
        behavior: "smooth",
      });
    },

    //监听题目改变状态
    selectListener(questionType, index) {
      let answerStr = JSON.stringify(this.answerSheet);
      let hasWritenStr = JSON.stringify(this.answerListener);
      this.answerListener[questionType][index] = true;

      hasWritenStr = JSON.stringify(this.answerListener);
      if (questionType === "choiceListen") {
        this.answerSheet.select[
          index
        ].questionId = this.paperInfo.choiceQuestion[index].questionId;
      }
      if (questionType === "judgeListen") {
        this.answerSheet.judge[index].questionId = this.paperInfo.judgeQuestion[
          index
        ].questionId;
      }
      if (questionType === "fillListen") {
        if (this.answerSheet.fill[index] === "") {
          this.answerListener[questionType][index] = false;
          hasWritenStr = JSON.stringify(this.answerListener);
        }
        this.answerSheet.fill[index].questionId = this.paperInfo.fillQuestion[
          index
        ].questionId;
      }
      if (questionType === "subjectiveListen") {
        if (this.answerSheet.subjective[index] === "") {
          this.answerListener[questionType][index] = false;
          hasWritenStr = JSON.stringify(this.answerListener);
        }
        this.answerSheet.subjective[
          index
        ].questionId = this.paperInfo.subjectiveQuestion[index].questionId;
      }
      localStorage.setItem("answerSheet", answerStr);
      localStorage.setItem("hasWriten", hasWritenStr);
    },

    // 考试计时器
    countDown(remainSecond) {
      let that = this;
      this.timeCountDown.promiseTimer = setInterval(() => {
        if (remainSecond >= 0) {
          this.timeCountDown.hour = Math.floor((remainSecond / 3600) % 24);
          this.timeCountDown.minute = Math.floor((remainSecond / 60) % 60);
          this.timeCountDown.second = Math.floor(remainSecond % 60);
          remainSecond -= 1;
          if (remainSecond === 1800) {
            this.$notify({
              title: "提醒",
              message: "还剩30分钟交卷，请注意时间！",
              type: "warning",
            });
          }
          if (remainSecond === 600) {
            this.$notify({
              title: "提醒",
              message: "还剩10分钟交卷，请注意时间！！",
              type: "warning",
            });
          }
          if (remainSecond === 300) {
            this.$notify({
              title: "提醒",
              message: "还剩5分钟交卷，请抓紧时间！！！",
              type: "warning",
            });
          }
          localStorage.setItem("remainSecond", remainSecond);
        } else {
          clearInterval(this.timeCountDown.promiseTimer);
          this.$message({
            showClose: true,
            message: "自动交卷！",
            type: "success",
          });
          localStorage.removeItem("remainSecond");
          this.$router.push({ path: "/student/exam" });
          // TODO 交卷功能
        }
      }, 1000);
    },

    formatNum(num) {
      return num < 10 ? "0" + num : "" + num;
    },

    submitPaper() {
      this.$router.push({ path: "/student/exam" });
    },

    submit() {
      let answerStr = JSON.stringify(this.answerSheet);
      localStorage.setItem("answerSheet", answerStr);
      let answerSheet = localStorage.getItem("answerSheet");
      let examId = this.examId;
      let studentId = this.$cookies.get("studentId");

      axios({
        method: "post",
        url: "/api/examPage/answerSheet",
        data: {
          answerSheet: answerSheet,
          examId: examId,
          studentId: studentId,
        },
      }).then((res) => {
        if (res.status === 200) {
          axios({
            method: "post",
            url: "/api/examPage/submitStatus",
            data: {
              examId: examId,
              studentId: studentId,
            },
          }).then((res) => {
            if (res.status === 200) {
              axios({
                method: "get",
                url: "/api/examPage/examStatus",
                params: {
                  examId: examId,
                },
              }).then(() => {
                if (res.status === 200) {
                  console.log("修改成功");
                }
              });
            }
          });
        }
      });
    },
  },
  computed: {
    hourString() {
      return this.formatNum(this.timeCountDown.hour);
    },
    minuteString() {
      return this.formatNum(this.timeCountDown.minute);
    },
    secondString() {
      return this.formatNum(this.timeCountDown.second);
    },
  },
};
</script>

<style lang="scss" >
.examPage-wrapper {
  background-color: #f4f6f7;
}
.exam-page {
  display: flex;
  flex-direction: row;
  padding: 30px 50px 30px 50px;
  clear: both;
}
.exam-page:after {
  content: " ";
  display: block;
  height: 0;
  visibility: hidden;
  clear: both;
}
.paper-container {
  width: 70%;
  height: 670px;
  position: relative;
  left: 30%;
}
.left-menu {
  position: fixed;
  top: 30px;
  width: 25%;
  height: 650px;
  padding-bottom: 20px;
  overflow: hidden;
}
.answer-card {
  display: flex;
  flex-direction: column;
  height: auto;
  padding: 5px;
  width: 95%;
  height: 350px;
}
.el-scrollbar {
  height: 100%;
}
.el-scrollbar .el-scrollbar__wrap {
  overflow-x: auto;
}
.answer-list {
  display: flex;
  flex-direction: column;
  height: auto;
  width: 100%;
}
.list-title {
  padding: 5px;
  background: #f2f4f4;
  border: 1px solid #dcdfe6;
  margin-bottom: 10px;
}
.block-list {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
}
.answerBlock {
  display: block;
  width: 49px;
  height: 30px;
  border: 1px solid #dcdfe6;
  text-decoration: none;
  color: #606266;
  line-height: 30px;
  font-size: 14px;
  margin-right: 10px;
  margin-bottom: 10px;
  transition: 0.1s;
  font-weight: 500;
  cursor: pointer;
}
.answerBlockActive {
  display: block;
  width: 49px;
  height: 30px;
  border: 1px solid #79bbff;
  background: #409eff;
  text-decoration: none;
  color: #ffffff;
  line-height: 30px;
  font-size: 14px;
  margin-right: 10px;
  margin-bottom: 10px;
  transition: 0.1s;
  font-weight: 500;
  cursor: pointer;
}
.answerBlock:nth-child(5n + 5) {
  margin-right: 0;
}
.answerBlockActive:nth-child(5n + 5) {
  margin-right: 0;
}
.answerBlock:hover {
  background: #ecf5ff;
  color: #409eff;
  border-color: #c6e2ff;
}
.answer-list {
  margin-bottom: 20px;
}

.scrollbar {
  overflow-x: hidden;
}
.el-scrollbar__wrap {
  overflow-x: hidden;
}
.submit {
  margin-top: 20px;
  width: 200px;
}
.paper-container {
  display: flex;
  flex-direction: column;
}
.exam-title {
  font-weight: 600;
  font-size: 26px;
}
.question-title {
  font-size: 18px;
  font-weight: 500;
  padding: 10px;
  text-align: left;
  margin-top: 10px;
  margin-bottom: 20px;
  background: #409eff;
  color: #fff;
  border-radius: 3px;
}
.questions {
  display: flex;
  flex-direction: column;
  width: 100%;
  height: auto;
}
.question-Item {
  padding: 20px;
  display: flex;
  flex-direction: column;
  background: #f8f9f9;
  margin: 0 auto;
  width: 90%;
  height: auto;
  text-align: left;
  border-radius: 3px;
  margin-bottom: 30px;
  word-break: break-all;
}
.stem {
  margin-bottom: 10px;
}
.selectItem {
  padding: 10px 10px 0 10px;
  height: auto;
}
.selectItem .el-radio {
  padding: 10px;
  word-break: break-all;
  overflow: hidden;
  white-space: normal;
  line-height: 20px;
}
.fillBlank {
  margin-top: 10px;
  margin-bottom: 10px;
  margin-left: 20px;
  width: 40%;
}
.subjectiveBlank {
  margin-top: 10px;
  margin-bottom: 10px;
  margin-left: 20px;
  width: 60%;
}
.exam-question-container {
  padding: 0 10px 10px 0;
  height: 450px;
}
</style>