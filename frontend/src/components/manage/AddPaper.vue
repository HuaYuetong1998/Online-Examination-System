<template>
  <div class="add-paper-wrapper">
    <el-tabs type="border-card" v-model="activeName">
      <el-tab-pane label="创建试卷" name="first">
        <div class="paper-container">
          <el-form
            :model="paperForm"
            ref="paperForm"
            label-width="100px"
            class="paper-form"
          >
            <el-form-item label="试卷名称：" prop="paperName">
              <el-input
                v-model="paperForm.paperName"
                style="width: 300px"
                maxlength="255"
              ></el-input>
            </el-form-item>

            <el-form-item label="学科：" prop="subject">
              <el-select
                v-model="subjectIds"
                placeholder="选择学科"
                filterable
                clearable
                multiple
                collapse-tags
                @change="loadMaxQuestionNum"
              >
                <el-option
                  v-for="item in subjects"
                  :key="item.subjectId"
                  :label="item.subjectName"
                  :value="item.subjectId"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-switch
              v-model="isAuto"
              active-text="自动组卷"
              inactive-text="人工组卷"
              class="isAuto"
              style="width: 180px"
              @change="loadMaxQuestionNum"
            >
            </el-switch>
            <el-form-item
              label="选择题："
              prop="choiceQuestionNum"
              v-if="isAuto == true"
            >
              <span>数量：</span>
              <el-input-number
                v-model="intellPaperForm.choiceNum"
                :min="0"
                :max="intellPaperForm.maxChoiceNum"
                size="small"
              ></el-input-number>

              <span class="tags">分值：</span>
              <el-input-number
                v-model="intellPaperForm.choiceScore"
                :precision="1"
                :step="1"
                :min="0.1"
                :max="20"
                size="small"
              ></el-input-number>
            </el-form-item>

            <el-form-item
              label="填空题："
              prop="fillQuestionNum"
              v-if="isAuto == true"
            >
              <span>数量：</span>
              <el-input-number
                v-model="intellPaperForm.fillNum"
                :min="0"
                :max="intellPaperForm.maxFillNum"
                size="small"
              ></el-input-number>

              <span class="tags">分值：</span>
              <el-input-number
                v-model="intellPaperForm.fillScore"
                :precision="1"
                :step="1"
                :min="0.1"
                :max="20"
                size="small"
              ></el-input-number>
            </el-form-item>

            <el-form-item
              label="判断题："
              prop="judgeQuestionNum"
              v-if="isAuto == true"
            >
              <span>数量：</span>
              <el-input-number
                v-model="intellPaperForm.judgeNum"
                :min="0"
                :max="intellPaperForm.maxJudgeNum"
                size="small"
              ></el-input-number>

              <span class="tags">分值：</span>
              <el-input-number
                v-model="intellPaperForm.judgeScore"
                :precision="1"
                :step="1"
                :min="0.1"
                :max="20"
                size="small"
              ></el-input-number>
            </el-form-item>

            <el-form-item
              label="主观题："
              prop="subjectiveQuestionNum"
              v-if="isAuto == true"
            >
              <span>数量：</span>
              <el-input-number
                v-model="intellPaperForm.subjectiveNum"
                :min="0"
                :max="intellPaperForm.maxSubjectiveNum"
                size="small"
              ></el-input-number>

              <span class="tags">分值：</span>
              <el-input-number
                v-model="intellPaperForm.subjectiveScore"
                :precision="1"
                :step="1"
                :min="0.1"
                :max="20"
                size="small"
              ></el-input-number>
            </el-form-item>

            <el-form-item label="添加题目：" prop="addQuestion" v-else>
              <el-button type="success" @click="initQuestion">添加</el-button>
            </el-form-item>

            <el-form-item label="试卷总分：" prop="totalScore">
              <span style="font-size: 16px">{{ paperForm.totalScore }} 分</span>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="createPaper">创建</el-button>
              <el-button @click="clearPaper">重置</el-button>
              <el-button
                type="success"
                v-if="isAuto == true"
                @click="randomPaper"
                >随机生成</el-button
              >
            </el-form-item>
          </el-form>
        </div>
      </el-tab-pane>
    </el-tabs>

    <!-- 试卷预览 -->
    <el-button class="open" @click="switchPreview">打开试卷预览</el-button>
    <transition name="el-zoom-in-top">
      <el-card class="paper-preview" v-show="isPreview">
        <div slot="header" class="clearfix">
          <span>试卷预览</span>
          <el-button
            style="float: right; padding: 3px 0"
            type="text"
            @click="switchPreview"
            >隐藏</el-button
          >
        </div>
        <div class="paper-content">
          <div
            class="question-item"
            v-for="(item, index) in paperQuestion"
            :key="index"
          >
            <div class="question-title">
              <span
                >{{ index + 1 }}. {{ item.question }} ({{ item.score }}分)</span
              >
            </div>
            <div
              class="choiceSelect"
              v-if="item.questionId >= 100000 && item.questionId < 200000"
            >
              <div class="choiceItem">A. {{ item.answerA }}</div>
              <div class="choiceItem">B. {{ item.answerB }}</div>
              <div class="choiceItem">C. {{ item.answerC }}</div>
              <div class="choiceItem">D. {{ item.answerD }}</div>
            </div>

            <div
              class="fillSheet"
              v-if="item.questionId >= 200000 && item.questionId < 300000"
            >
              <div><span>答题：</span>_____</div>
            </div>

            <div
              class="judgeSheet"
              v-if="item.questionId >= 300000 && item.questionId < 400000"
            >
              <div>
                <span>答题：( ) </span
                ><i style="font-size: 14px; margin-left: 20px">(填写T或者F)</i>
              </div>
            </div>

            <div class="subjectiveSheet" v-if="item.questionId >= 400000">
              <span>答题：</span><span>_____________________________</span>
            </div>

            <div class="right-answer">
              <span>标准答案:</span
              ><span
                style="margin-left: 10px"
                v-if="item.questionId >= 100000 && item.questionId < 200000"
                >{{ item.rightAnswer }}</span
              >
              <span style="margin-left: 10px" v-else>
                {{ item.answer }}
              </span>
            </div>
            <div class="level" style="display: flex; flex-direction: row">
              <span>难度：</span>
              <span
                ><el-rate
                  v-model="paperQuestion[index].level"
                  disabled
                  show-score
                  text-color="#ff9900"
                >
                </el-rate
              ></span>
            </div>
          </div>
        </div>
      </el-card>
    </transition>

    <!-- 手动添加题目 -->
    <el-dialog
      title="添加题目"
      :visible.sync="addQuestionVisible"
      :append-to-body="true"
      :close-on-click-modal="false"
      width="70%"
    >
      <el-form
        :model="questionForm"
        class="question-pool"
        label-position="right"
        label-width="60px"
      >
        <div class="searchLine">
          <el-form-item label="学科：" class="searchItem">
            <el-select
              v-model="subjectId"
              placeholder="选择学科"
              clearable
              @change="selectSubject(subjectId)"
            >
              <el-option
                v-for="item in subjects"
                :key="item.subjectId"
                :label="item.subjectName"
                :value="item.subjectId"
              ></el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="题型：" class="searchItem">
            <el-select
              v-model="searchForm.type"
              placeholder="选择题型"
              clearable
            >
              <el-option label="选择题" value="选择题"></el-option>
              <el-option label="填空题" value="填空题"></el-option>
              <el-option label="判断题" value="判断题"></el-option>
              <el-option label="主观题" value="主观题"></el-option>
            </el-select>
          </el-form-item>
        </div>

        <div class="searchLine">
          <el-form-item label="ID：" class="searchItem">
            <el-input
              placeholder="输入题目ID"
              v-model="searchForm.id"
            ></el-input>
          </el-form-item>

          <el-form-item label="题干：" class="searchItem">
            <el-input
              placeholder="输入题目内容"
              v-model="searchForm.question"
            ></el-input>
          </el-form-item>
        </div>
        <div>
          <el-button @click="toggleSelection">取消选中</el-button>
          <el-button
            type="primary"
            icon="el-icon-search"
            @click="searchList"
            v-on:click="searchClickCount += 1"
            >搜索</el-button
          >
        </div>

        <el-divider></el-divider>
        <el-table
          ref="questionSelection"
          :data="resultData"
          :row-key="rowKey"
          border
          style="width: 100%"
          @selection-change="handleSelectionChange"
        >
          <el-table-column
            fixed
            type="selection"
            width="50"
            :reserve-selection="true"
          >
          </el-table-column>
          <el-table-column fixed prop="questionId" label="ID" width="100">
          </el-table-column>
          <el-table-column prop="type" label="题目类型" width="120">
          </el-table-column>
          <el-table-column prop="subject" label="学科" width="120">
          </el-table-column>
          <el-table-column
            prop="question"
            label="题干"
            width="150"
            :show-overflow-tooltip="true"
          >
          </el-table-column>
          <el-table-column
            prop="answer"
            label="答案"
            width="150"
            :show-overflow-tooltip="true"
          >
          </el-table-column>
          <el-table-column
            prop="analysis"
            label="分析"
            width="150"
            :show-overflow-tooltip="true"
          >
          </el-table-column>
          <el-table-column prop="score" label="分数" width="150">
          </el-table-column>
          <el-table-column prop="level" label="难易度" width="150">
          </el-table-column>
        </el-table>
      </el-form>

      <!-- 分页 -->
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
        <el-button type="primary" @click="addToPreview">确 定</el-button>
        <el-button @click="addQuestionVisible = false">返 回</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import axios from "axios";

export default {
  inject: ["reload"],
  created: function () {
    axios({
      method: "post",
      url: "/api/subject",
    }).then((res) => {
      let subjects = res.data.data;
      this.subjects = subjects;
    });
  },
  data() {
    return {
      isAuto: false,
      isPreview: true,
      addQuestionVisible: false,
      activeName: "first",
      searchClickCount: 0,
      subjects: [],
      subjectIds: [],
      subjectId: "",
      multipleSelection: [],
      searchForm: {
        subject: "",
        type: "",
        id: null,
        question: "",
      },
      currentPage: 1,
      pageSize: 5,
      pageTotal: null,
      resultData: [],
      paperForm: {
        paperName: "",
        addQuestion: "",
        totalScore: 0,
      },
      intellPaperForm: {
        choiceNum: 0,
        fillNum: 0,
        judgeNum: 0,
        subjectiveNum: 0,
        normalChoiceNum: 0,
        normalFillNum: 0,
        normalJudgeNum: 0,
        normalSubjectiveNum: 0,
        choiceScore: 2,
        fillScore: 2,
        judgeScore: 2,
        subjectiveScore: 10,
        maxChoiceNum: 0,
        maxFillNum: 0,
        maxJudgeNum: 0,
        maxSubjectiveNum: 0,
      },
      paperQuestion: [],
      questionForm: {
        questionId: "",
        subjectId: "",
        content: "",
        answerA: "",
        answerB: "",
        answerC: "",
        answerD: "",
        rightAnswer: "",
        analysis: "",
        score: 1,
        level: null,
      },
    };
  },
  methods: {
    switchPreview() {
      this.isPreview = !this.isPreview;
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
      //console.log(this.multipleSelection);
    },
    toggleSelection() {
      this.$nextTick(() => {
        this.$refs.questionSelection.clearSelection();
      });
    },
    searchList() {
      let subjectId = this.subjectId;
      let type = this.searchForm.type;
      let id = this.searchForm.id;
      let question = this.searchForm.question;
      let currentPage = this.currentPage;
      let pageSize = this.pageSize;

      axios({
        method: "post",
        url: "/api/question/list",
        data: {
          questionId: id,
          subjectId: subjectId,
          question: question,
          questionType: type,
          currentPage: currentPage,
          pageSize: pageSize,
        },
      }).then((res) => {
        this.resultData = [];
        if (res.status === 200) {
          this.loading = true;
          setTimeout(() => {
            let result = res.data.data;
            let pageData = result.pageData;
            let resultData = this.resultData;
            let type = "";
            //   console.log(result);
            //   console.log(pageData);
            //   console.log(result.pageTotal);
            this.pageTotal = result.pageTotal;
            //console.log(resultData);
            for (let i = 0; i < pageData.length; i++) {
              if (
                pageData[i].questionId >= 100000 &&
                pageData[i].questionId < 200000
              ) {
                type = "选择题";
              } else if (
                pageData[i].questionId >= 200000 &&
                pageData[i].questionId < 300000
              ) {
                type = "填空题";
              } else if (
                pageData[i].questionId >= 300000 &&
                pageData[i].questionId < 400000
              ) {
                type = "判断题";
              } else if (pageData[i].questionId >= 400000) {
                type = "主观题";
              }
              resultData.push({
                questionId: pageData[i].questionId,
                type: type,
                subject: pageData[i].subject,
                question: pageData[i].question,
                answer: pageData[i].rightAnswer,
                answerA: pageData[i].answerA,
                answerB: pageData[i].answerB,
                answerC: pageData[i].answerC,
                answerD: pageData[i].answerD,
                analysis: pageData[i].analysis,
                score: pageData[i].score,
                level: pageData[i].level,
              });
            }
            this.loading = false;
          }, 500);
        }
      });
    },
    handleCurrentChange(val) {
      this.currentPage = val;
      if (this.searchClickCount > 0) {
        this.searchList();
      } else {
        this.initQuestion();
      }
    },
    selectSubject(subjectId) {
      this.subjectId = subjectId;
      //console.log(this.subjectId);
    },

    // 初始化添加题目的题库
    initQuestion() {
      if (this.subjectIds.length === 0) {
        this.$message({
          showClose: true,
          message: "学科不能为空",
          type: "error",
        });
      } else {
        axios({
          method: "post",
          url: "/api/question/paperQuestion",
          data: {
            subjectIds: this.subjectIds,
            currentPage: this.currentPage,
            pageSize: this.pageSize,
          },
        }).then((res) => {
          this.resultData = [];
          if (res.status === 200) {
            let result = res.data.data;
            let pageData = result.pageData;
            let resultData = this.resultData;
            let type = "";
            this.pageTotal = result.pageTotal;
            for (let i = 0; i < pageData.length; i++) {
              if (
                pageData[i].questionId >= 100000 &&
                pageData[i].questionId < 200000
              ) {
                type = "选择题";
              } else if (
                pageData[i].questionId >= 200000 &&
                pageData[i].questionId < 300000
              ) {
                type = "填空题";
              } else if (
                pageData[i].questionId >= 300000 &&
                pageData[i].questionId < 400000
              ) {
                type = "判断题";
              } else if (pageData[i].questionId >= 400000) {
                type = "主观题";
              }
              resultData.push({
                questionId: pageData[i].questionId,
                type: type,
                subject: pageData[i].subject,
                question: pageData[i].question,
                answer: pageData[i].rightAnswer,
                answerA: pageData[i].answerA,
                answerB: pageData[i].answerB,
                answerC: pageData[i].answerC,
                answerD: pageData[i].answerD,
                analysis: pageData[i].analysis,
                score: pageData[i].score,
                level: pageData[i].level,
              });
            }
          }
        });
        this.addQuestionVisible = true;
      }
    },

    //添加题目到试卷预览
    addToPreview() {
      let questionArray = this.multipleSelection;
      let totalScore = 0;
      this.intellPaperForm.normalChoiceNum = 0;
      this.intellPaperForm.normalFillNum = 0;
      this.intellPaperForm.normalJudgeNum = 0;
      this.intellPaperForm.normalSubjectiveNum = 0;
      this.paperQuestion = questionArray;
      for (let i = 0; i < this.paperQuestion.length; i++) {
        if (
          this.paperQuestion[i].questionId >= 100000 &&
          this.paperQuestion[i].questionId < 200000
        ) {
          this.intellPaperForm.normalChoiceNum++;
        } else if (
          this.paperQuestion[i].questionId >= 200000 &&
          this.paperQuestion[i].questionId < 300000
        ) {
          this.intellPaperForm.normalFillNum++;
        } else if (
          this.paperQuestion[i].questionId >= 300000 &&
          this.paperQuestion[i].questionId < 400000
        ) {
          this.intellPaperForm.normalJudgeNum++;
        } else if (this.paperQuestion[i].questionId >= 400000) {
          this.intellPaperForm.normalSubjectiveNum++;
        }
      }
      console.log(this.paperQuestion);
      console.log(this.intellPaperForm);
      this.paperQuestion.sort(this.sortQuestionList("questionId", true));
      for (let i = 0; i < this.paperQuestion.length; i++) {
        totalScore += this.paperQuestion[i].score;
      }
      this.paperForm.totalScore = totalScore;
      this.addQuestionVisible = false;
    },

    // 多选换页不消失
    rowKey(row) {
      return row.questionId;
    },

    //排序题目
    sortQuestionList(prop, desc) {
      return (object1, object2) => {
        let value1 = object1[prop];
        let value2 = object2[prop];
        if (desc == true) {
          //升序
          return value1 - value2;
        } else {
          return value2 - value1;
        }
      };
    },

    //清空试卷
    clearPaper() {
      this.paperQuestion = [];
      this.paperForm.paperName = "";
      this.paperForm.totalScore = 0;
      this.subjectIds = [];
      this.intellPaperForm.choiceNum = 0;
      this.intellPaperForm.fillNum = 0;
      this.intellPaperForm.judgeNum = 0;
      this.intellPaperForm.subjectiveNum = 0;
      this.intellPaperForm.choiceScore = 2;
      this.intellPaperForm.fillScore = 2;
      this.intellPaperForm.judgeScore = 2;
      this.intellPaperForm.subjectiveScore = 10;
      this.toggleSelection();
    },

    //根据学科加载出现有最大题目数
    loadMaxQuestionNum() {
      if (this.isAuto) {
        if (this.subjectIds.length == 0) {
          this.$message({
            showClose: true,
            message: "学科为空，请选择学科！",
            type: "error",
          });
          this.isAuto = false;
        } else {
          axios({
            method: "post",
            url: "/api/question/getMax",
            data: {
              subjectIds: this.subjectIds,
            },
          }).then((res) => {
            if (res.status === 200) {
              let result = res.data.data;
              this.intellPaperForm.maxChoiceNum = result.choiceNum;
              this.intellPaperForm.maxFillNum = result.fillNum;
              this.intellPaperForm.maxJudgeNum = result.judgeNum;
              this.intellPaperForm.maxSubjectiveNum = result.subjectiveNum;
            }
          });
        }
      }
    },

    //随机组卷
    randomPaper() {
      if (this.subjectIds.length === 0) {
        this.$message({
          showClose: true,
          message: "学科不能为空",
          type: "error",
        });
      } else {
        axios({
          method: "post",
          url: "/api/question/autoCreatePaper",
          data: {
            subjectIds: this.subjectIds,
            choiceNum: this.intellPaperForm.choiceNum,
            fillNum: this.intellPaperForm.fillNum,
            judgeNum: this.intellPaperForm.judgeNum,
            subjectiveNum: this.intellPaperForm.subjectiveNum,
            choiceScore: this.intellPaperForm.choiceScore,
            fillScore: this.intellPaperForm.fillScore,
            judgeScore: this.intellPaperForm.judgeScore,
            subjectiveScore: this.intellPaperForm.subjectiveScore,
          },
        }).then((res) => {
          if (res.status === 200) {
            let result = res.data.data;
            let totalScore = 0;
            this.paperQuestion = [];
            this.paperQuestion = this.paperQuestion
              .concat(result.choiceQuestion)
              .concat(result.fillQuestion)
              .concat(result.judgeQuestion)
              .concat(result.subjectiveQuestion);
            for (let i = 0; i < this.paperQuestion.length; i++) {
              if (
                this.paperQuestion[i].questionId >= 100000 &&
                this.paperQuestion[i].questionId < 200000
              ) {
                this.paperQuestion[i].score = this.intellPaperForm.choiceScore;
              } else if (
                this.paperQuestion[i].questionId >= 200000 &&
                this.paperQuestion[i].questionId < 300000
              ) {
                this.paperQuestion[i].score = this.intellPaperForm.fillScore;
              } else if (
                this.paperQuestion[i].questionId >= 300000 &&
                this.paperQuestion[i].questionId < 400000
              ) {
                this.paperQuestion[i].score = this.intellPaperForm.judgeScore;
              } else if (this.paperQuestion[i].questionId >= 400000) {
                this.paperQuestion[
                  i
                ].score = this.intellPaperForm.subjectiveScore;
              }
            }
            this.paperQuestion.sort(this.sortQuestionList("questionId", true));
            for (let i = 0; i < this.paperQuestion.length; i++) {
              totalScore += this.paperQuestion[i].score;
            }
            this.paperForm.totalScore = totalScore;
          }
        });
      }
    },

    //创建试卷
    createPaper() {
      if (this.paperForm.paperName === "") {
        this.$message({
          showClose: true,
          message: "试卷名称不能为空",
          type: "error",
        });
      } else {
        if (this.paperQuestion.length === 0) {
          this.$message({
            showClose: true,
            message: "试卷还未添加题目",
            type: "error",
          });
        } else {
          let type = 0;
          if (this.isAuto) {
            type = 1;
          }
          axios({
            methos: "get",
            url: "/api/paper/isExist",
            params: {
              paperTitle: this.paperForm.paperName,
            },
          }).then((res) => {
            if (res.status === 200) {
              console.log(res);
              if (res.data.msg === "试卷已存在") {
                this.$message({
                  showClose: true,
                  message: "试卷名已存在",
                  type: "error",
                });
              } else {
                axios({
                  method: "post",
                  url: "/api/paper/add",
                  data: {
                    paperTitle: this.paperForm.paperName,
                    choiceNum: this.intellPaperForm.choiceNum,
                    fillNum: this.intellPaperForm.fillNum,
                    judgeNum: this.intellPaperForm.judgeNum,
                    subjectiveNum: this.intellPaperForm.subjectiveNum,
                    normalChoiceNum: this.intellPaperForm.normalChoiceNum,
                    normalFillNum: this.intellPaperForm.normalFillNum,
                    normalJudgeNum: this.intellPaperForm.normalJudgeNum,
                    normalSubjectiveNum: this.intellPaperForm
                      .normalSubjectiveNum,
                    choiceScore: this.intellPaperForm.choiceScore,
                    fillScore: this.intellPaperForm.fillScore,
                    judgeScore: this.intellPaperForm.judgeScore,
                    subjectiveScore: this.intellPaperForm.subjectiveScore,
                    totalScore: this.paperForm.totalScore,
                    type: type,
                    paperQuestion: this.paperQuestion,
                  },
                }).then((res) => {
                  if (res.status === 200) {
                    let result = res.data.data;
                    this.$message({
                      showClose: true,
                      message: "试卷创建成功",
                      type: "success",
                    });
                    this.reload();
                  }
                });
              }
            }
          });
        }
      }
    },
  },
};
</script>

<style lang="scss" scoped>
* {
  touch-action: pan-y;
  touch-action: pan-x;
}
.el-tab-pane {
  display: flex;
}
.paper-container {
  height: 900px;
  overflow: auto;
  margin: 10px;
  text-align: left;
}
.paper-form {
  display: flex;
  flex-direction: column;
  justify-content: left;
}
.isAuto {
  margin: 25px;
}
.tags {
  margin-left: 30px;
}
.paper-preview {
  position: fixed;
  top: 150px;
  right: 20px;
  width: 580px;
  height: 540px;
}
.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both;
}
.paper-content {
  height: 400px;
  display: flex;
  padding: 10px 20px;
  flex-direction: column;
  overflow-x: hidden;
  overflow-y: auto;
}
.open {
  position: fixed;
  top: 150px;
  right: 20px;
}
.question-item {
  display: flex;
  flex-direction: column;
  text-align: left;
  background-color: rgba(247, 247, 247, 0.871);
  padding: 20px;
  height: auto;
  margin-top: 20px;
  word-break: break-all;
}
.question-title {
  width: 100%;
  white-space: pre-wrap;
}
.choiceSelect {
  margin-top: 10px;
  margin-left: 20px;
}
.choiceItem {
  margin-bottom: 10px;
}
.choiceItem:last-child {
  margin-bottom: 0;
}
.fillSheet {
  margin-top: 10px;
  margin-left: 20px;
}
.judgeSheet {
  margin-top: 10px;
  margin-left: 20px;
}
.subjectiveSheet {
  margin-top: 10px;
  margin-left: 20px;
}
.right-answer {
  margin-top: 20px;
  margin-left: 20px;
  font-weight: bold;
}
.level {
  margin-top: 10px;
  margin-left: 20px;
  font-weight: bold;
}
.question-pool {
  display: flex;
  flex-direction: column;
}
.searchLine {
  display: flex;
  flex-direction: row;
  width: 100%;
}
.searchItem {
  width: 280px;
}
.searchItem .el-input {
  width: 210px;
}
</style>