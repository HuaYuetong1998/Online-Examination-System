<template>
  <div class="examPage-wrapper">
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
                    :href="'#choiceQuestion' + index"
                    :id="'choiceBlock' + index"
                    class="answerBlock"
                    v-bind:class="{
                      answerBlockActive: answerListener.choiceListen[index - 1],
                    }"
                    v-for="index of paperInfo.choiceNum"
                    :key="index"
                    >{{ index }} {{ answerListener.choiceListen[index - 1] }}</a
                  >
                </div>
              </div>

              <div class="answer-list" v-if="paperInfo.fillNum > 0">
                <div class="list-title">填空题</div>
                <div class="block-list">
                  <a
                    href="#"
                    class="answerBlock"
                    v-for="index of paperInfo.fillNum"
                    :key="index"
                    >{{ index }}</a
                  >
                </div>
              </div>

              <div class="answer-list" v-if="paperInfo.judgeNum > 0">
                <div class="list-title">判断题</div>
                <div class="block-list">
                  <a
                    href="#"
                    class="answerBlock"
                    v-for="index of paperInfo.judgeNum"
                    :key="index"
                    >{{ index }}</a
                  >
                </div>
              </div>

              <div class="answer-list" v-if="paperInfo.subjectiveNum > 0">
                <div class="list-title">主观题</div>
                <div class="block-list">
                  <a
                    href="#"
                    class="answerBlock"
                    v-for="index of paperInfo.subjectiveNum"
                    :key="index"
                    >{{ index }}</a
                  >
                </div>
              </div>
            </div>
          </el-scrollbar>
        </div>

        <el-button class="submit" type="primary">交卷</el-button>
      </el-card>
      <el-card class="paper-container">
        <div class="exam-title">{{ examInfo.examTitle }}</div>
        <el-divider></el-divider>
        <div class="question-title" v-if="paperInfo.choiceQuestion.length > 0">
          <span style="margin-right: 10px; margin-left: 10px">一.</span
          >选择题（共{{ paperInfo.choiceNum }}题，30分）
        </div>
        <div class="questions" v-if="paperInfo.choiceQuestion.length > 0">
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
              v-model="answerSheet.select[index]"
              class="selectItem"
              @change="selectListener(index)"
            >
              <el-radio :label="1">A.{{ item.answerA }}</el-radio>
              <el-radio :label="2">B.{{ item.answerB }}</el-radio>
              <el-radio :label="3">C.{{ item.answerC }}</el-radio>
              <el-radio :label="4">D.{{ item.answerD }}</el-radio>
            </el-radio-group>
          </div>
        </div>

        <div class="question-title">
          <span style="margin-right: 10px; margin-left: 10px">二.</span
          >填空题（共{{ paperInfo.fillNum }}题，30分）
        </div>
        <div class="questions">
          <div
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
              v-model="answerSheet.fill[index]"
              clearable
              class="fillBlank"
            ></el-input>
          </div>
        </div>

        <div class="question-title">
          <span style="margin-right: 10px; margin-left: 10px">三.</span
          >判断题（共{{ paperInfo.judgeNum }}题，30分）
        </div>
        <div class="questions">
          <div
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
              v-model="answerSheet.judge[index]"
              class="selectItem"
            >
              <el-radio :label="1">正确</el-radio>
              <el-radio :label="2">错误</el-radio>
            </el-radio-group>
          </div>
        </div>

        <div class="question-title">
          <span style="margin-right: 10px; margin-left: 10px">四.</span
          >主观题（共{{ paperInfo.subjectiveNum }}题，30分）
        </div>
        <div class="questions">
          <div
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
              v-model="answerSheet.subjective[index]"
              resize="none"
              :autosize="{ minRows: 3, maxRows: 5 }"
            >
            </el-input>
          </div>
        </div>
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
      isActive: true,
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
        choiceListen: [false],
        fillListen: [],
        judgeListen: [],
        subjectiveListen: [],
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

    let remainSecond = localStorage.getItem("remainSecond");
    if (remainSecond != null) {
      this.examInfo.remainSecond = remainSecond;
    }
    console.log(this.examInfo.remainSecond);
    this.countDown(this.examInfo.remainSecond);
  },

  methods: {
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

                if (choiceQuestions != null) {
                  for (let i = 0; i < choiceQuestions.length; i++) {
                    this.paperInfo.choiceQuestion.push({
                      question: choiceQuestions[i].question,
                      answerA: choiceQuestions[i].answerA,
                      answerB: choiceQuestions[i].answerB,
                      answerC: choiceQuestions[i].answerC,
                      answerD: choiceQuestions[i].answerD,
                      score: choiceQuestions[i].score,
                    });
                    //this.answerListener.choiceListen[i] = true;
                  }
                }

                if (fillQuestions != null) {
                  for (let i = 0; i < fillQuestions.length; i++) {
                    this.paperInfo.fillQuestion.push({
                      question: fillQuestions[i].question,
                      score: fillQuestions[i].score,
                    });
                    this.answerListener.fillListen[i] = false;
                  }
                }

                if (judgeQuestions != null) {
                  for (let i = 0; i < judgeQuestions.length; i++) {
                    this.paperInfo.judgeQuestion.push({
                      question: judgeQuestions[i].question,
                      score: judgeQuestions[i].score,
                    });
                    this.answerListener.judgeListen[i] = false;
                  }
                }

                if (subjectiveQuestions != null) {
                  for (let i = 0; i < subjectiveQuestions.length; i++) {
                    this.paperInfo.subjectiveQuestion.push({
                      question: subjectiveQuestions[i].question,
                      score: subjectiveQuestions[i].score,
                    });

                    this.answerListener.subjectiveListen[i] = false;
                  }
                }

                console.log(questionRes);
              }
            });
          }

          console.log(result);
        }
      });
    },
    selectListener(index) {
      console.log(index);
      this.answerListener.choiceListen[index] = true;
      this.isActive = false;
      console.log(this.answerListener.choiceListen[index]);
      console.log(this.answerListener.choiceListen);
    },
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
          localStorage.removeItem("remainSecond");

          // TODO 交卷功能
        }
      }, 1000);
    },

    formatNum(num) {
      return num < 10 ? "0" + num : "" + num;
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
  min-height: 700px;
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
}
.answerBlockActive {
  background: skyblue;
  border: 1px solid #658be6;
  text-decoration: none;
  color: #474747;
}
.answerBlock:nth-child(5n + 5) {
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
</style>