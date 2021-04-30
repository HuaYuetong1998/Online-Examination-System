<template>
  <div class="add-question-wrapper">
    <div class="question-container">
      <el-form
        :model="searchForm"
        ref="searchForm"
        label-width="100px"
        class="searchForm"
      >
        <el-form-item label="学科：">
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

        <el-form-item label="题型：">
          <el-select
            v-model="searchForm.type"
            placeholder="选择题型"
            clearable
            @change="selectType()"
          >
            <el-option label="选择题" value="选择题"></el-option>
            <el-option label="填空题" value="填空题"></el-option>
            <el-option label="判断题" value="判断题"></el-option>
            <el-option label="主观题" value="主观题"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="ID：">
          <el-input placeholder="输入题目ID" v-model="searchForm.id"></el-input>
        </el-form-item>

        <el-form-item label="题干：">
          <el-input
            placeholder="输入题目内容"
            v-model="searchForm.question"
          ></el-input>
        </el-form-item>

        <el-form-item label-width="0">
          <el-button type="primary" icon="el-icon-search" @click="searchList"
            >搜索</el-button
          >
        </el-form-item>
      </el-form>

      <el-divider></el-divider>

      <div class="result">
        <el-table
          v-loading="loading"
          :data="resultData"
          border
          style="width: 100%"
          lazy
          :load="load"
        >
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
          <el-table-column fixed="right" label="操作" width="260">
            <template slot-scope="scope">
              <el-button
                icon="el-icon-info"
                @click.native.prevent="showDetail(scope.$index, resultData)"
                type="primary"
                size="small"
                >详情</el-button
              >
              <el-button
                icon="el-icon-edit"
                @click.native.prevent="showModify(scope.$index, resultData)"
                type="success"
                size="small"
                >修改</el-button
              >

              <el-button
                icon="el-icon-delete"
                @click.native.prevent="deleteRow(scope.$index, resultData)"
                type="danger"
                size="small"
                >删除</el-button
              >
            </template>
          </el-table-column>
        </el-table>
      </div>

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
    </div>

    <!-- 题目详细信息 -->
    <el-dialog
      title="题目详情"
      :visible.sync="detailVisible"
      :append-to-body="true"
      :close-on-click-modal="false"
      width="35%"
    >
      <div class="detailBox">
        <el-row>
          <span class="itemTitle">题目ID：</span>
          <span class="show">{{ showQuestionDetail.questionId }}</span>
        </el-row>
        <el-row>
          <span class="itemTitle">学科：</span>
          <span class="show">{{ showQuestionDetail.subject }}</span>
        </el-row>
        <el-row>
          <span class="itemTitle">题目：</span>
          <span class="show">{{ showQuestionDetail.question }}</span>
        </el-row>
        <el-row
          v-if="
            showQuestionDetail.questionId >= 100000 &&
            showQuestionDetail.questionId < 200000
          "
        >
          <span class="itemTitle">选项：</span>
          <div class="answerSelect">
            <div class="answerItem">A: {{ showQuestionDetail.answerA }}</div>
            <div class="answerItem">B: {{ showQuestionDetail.answerB }}</div>
            <div class="answerItem">C: {{ showQuestionDetail.answerC }}</div>
            <div class="answerItem">D: {{ showQuestionDetail.answerD }}</div>
          </div>
        </el-row>
        <el-row>
          <span class="itemTitle">答案：</span>
          <span class="show">{{ showQuestionDetail.rightAnswer }}</span>
        </el-row>
        <el-row>
          <span class="itemTitle">解析：</span>
          <span class="show">{{ showQuestionDetail.analysis }}</span>
        </el-row>
        <el-row>
          <span class="itemTitle">分数：</span>
          <span class="show">{{ showQuestionDetail.score }} 分</span>
        </el-row>
        <el-row>
          <span class="itemTitle">难度：</span>
          <span class="show"
            ><el-rate
              v-model="showQuestionDetail.level"
              disabled
              show-score
              text-color="#ff9900"
            >
            </el-rate>
          </span>
        </el-row>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="detailVisible = false">关闭</el-button>
      </div>
    </el-dialog>

    <!-- 修改题目 -->
    <el-dialog
      title="修改题目"
      :visible.sync="modifyVisible"
      :append-to-body="true"
      :close-on-click-modal="false"
      width="50%"
    >
      <el-form
        class="modifyBox"
        :model="modifyQuestion"
        ref="modifyQuestion"
        :label-position="labelPosition"
        label-width="80px"
      >
        <el-form-item label="题目ID：" prop="questionId">
          <el-input
            v-model="modifyQuestion.questionId"
            :disabled="true"
          ></el-input>
        </el-form-item>
        <el-form-item label="学科：" prop="subject">
          <el-select
            v-model="modifySubjectId"
            placeholder="选择学科"
            filterable
            @change="selectSubject(modifySubjectId)"
          >
            <el-option
              v-for="item in modifySubjects"
              :key="item.subjectId"
              :label="item.subjectName"
              :value="item.subjectId"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="题目：" prop="question">
          <el-input
            type="textarea"
            :rows="2"
            v-model="modifyQuestion.question"
            style="width: 300px; font-size: 16px"
            resize="none"
            maxlength="255"
          ></el-input>
        </el-form-item>

        <el-form-item
          label="选项："
          class="choice"
          prop="item"
          v-if="
            modifyQuestion.questionId >= 100000 &&
            modifyQuestion.questionId < 200000
          "
        >
          <el-input
            class="item"
            v-model="modifyQuestion.answerA"
            prop="itemA"
            ref="answerA"
          >
            <template slot="prepend">A</template>
          </el-input>
          <el-input
            class="item"
            v-model="modifyQuestion.answerB"
            prop="itemB"
            ref="answerB"
          >
            <template slot="prepend">B</template>
          </el-input>
          <br />
          <el-input
            class="item"
            v-model="modifyQuestion.answerC"
            prop="itemC"
            ref="answerC"
          >
            <template slot="prepend">C</template>
          </el-input>
          <el-input
            class="item"
            v-model="modifyQuestion.answerD"
            prop="itemD"
            ref="answerD"
          >
            <template slot="prepend">D</template>
          </el-input>
        </el-form-item>

        <el-form-item
          label="答案："
          prop="rightAnswer"
          v-if="
            modifyQuestion.questionId >= 100000 &&
            modifyQuestion.questionId < 200000
          "
        >
          <el-radio
            v-model="modifyQuestion.rightAnswer"
            label="a"
            border
            size="medium"
            >A</el-radio
          >
          <el-radio
            v-model="modifyQuestion.rightAnswer"
            label="b"
            border
            size="medium"
            >B</el-radio
          >
          <el-radio
            v-model="modifyQuestion.rightAnswer"
            label="c"
            border
            size="medium"
            >C</el-radio
          >
          <el-radio
            v-model="modifyQuestion.rightAnswer"
            label="d"
            border
            size="medium"
            >D</el-radio
          >
        </el-form-item>
        <el-form-item label="答案：" prop="rightAnswer" v-else>
          <el-input
            type="textarea"
            :rows="1"
            v-model="modifyQuestion.rightAnswer"
            style="width: 300px; font-size: 16px"
            resize="none"
            maxlength="255"
          ></el-input>
        </el-form-item>
        <el-form-item label="解析：" prop="analysis">
          <el-input
            type="textarea"
            :rows="2"
            v-model="modifyQuestion.analysis"
            style="width: 300px; font-size: 16px"
            resize="none"
            maxlength="255"
          ></el-input>
        </el-form-item>
        <el-form-item label="分数" prop="score">
          <el-input-number
            v-model="modifyQuestion.score"
            :precision="1"
            :step="1"
            :max="20"
            :min="0.1"
          ></el-input-number>
        </el-form-item>
        <el-form-item label="难度：" prop="level">
          <el-rate
            v-model="modifyQuestion.level"
            show-score
            text-color="#ff9900"
            style="line-height: 2.5"
          ></el-rate>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="modifyRow()">修改</el-button>
        <el-button @click="modifyVisible = false">关闭</el-button>
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
    this.searchList();
  },
  data() {
    return {
      subjects: [],
      subjectId: "",
      modifySubjectId: "",
      modifySubjects: [],
      questionType: "",
      resultData: [],
      searchForm: {
        subject: "",
        type: "",
        id: null,
        question: "",
      },
      currentPage: 1,
      pageSize: 5,
      pageTotal: null,
      detailVisible: false,
      showQuestionDetail: {
        questionId: null,
        subject: "",
        question: "",
        answerA: "",
        answerB: "",
        answerC: "",
        answerD: "",
        analysis: "",
        rightAnswer: "",
        score: null,
        level: null,
      },
      modifyQuestion: {
        questionId: null,
        subject: "",
        question: "",
        answerA: "",
        answerB: "",
        answerC: "",
        answerD: "",
        analysis: "",
        rightAnswer: "",
        score: null,
        level: null,
      },
      modifyVisible: false,
      labelPosition: "right",
      loading: false,
    };
  },
  methods: {
    load(resolve) {
      setTimeout(() => {
        resolve(this.resultData);
      });
    },
    getQuestionInfo(currentId, form) {
      if (currentId >= 100000 && currentId < 200000) {
        axios({
          method: "get",
          url: "/api/question/getChoiceQuestionDetail",
          params: {
            questionId: currentId,
          },
        }).then((res) => {
          let result = res.data.data;
          form.questionId = result.questionId;
          form.subject = result.subject;
          form.question = result.question;
          form.answerA = result.answerA;
          form.answerB = result.answerB;
          form.answerC = result.answerC;
          form.answerD = result.answerD;
          form.rightAnswer = result.rightAnswer;
          form.analysis = result.analysis;
          form.score = result.score;
          form.level = result.level;
        });
      } else {
        //console.log(currentId);
        axios({
          method: "get",
          url: "/api/question/getOtherQuestionDetail",
          params: {
            questionId: currentId,
          },
        }).then((res) => {
          let result = res.data.data;
          form.questionId = result.questionId;
          form.subject = result.subject;
          form.question = result.question;
          form.rightAnswer = result.rightAnswer;
          form.analysis = result.analysis;
          form.score = result.score;
          form.level = result.level;
          console.log(res.data.data);
        });
      }
    },
    showDetail(index, rows) {
      this.detailVisible = true;
      let currentId = rows[index].questionId;
      let form = this.showQuestionDetail;
      this.getQuestionInfo(currentId, form);
    },
    showModify(index, rows) {
      this.modifyVisible = true;
      let currentId = rows[index].questionId;
      this.getQuestionInfo(currentId, this.modifyQuestion);
      setTimeout(() => {
        axios({
          method: "get",
          url: "/api/subject/getId",
          params: {
            subject: this.modifyQuestion.subject,
          },
        }).then((res) => {
          let result = res.data.data;
          this.modifySubjects = result.subjects;
          this.modifySubjectId = result.currentSubjectId;
        });
      }, 500);
    },
    modifyRow() {
      console.log(this.modifyQuestion);
      if (
        this.modifyQuestion.questionId >= 100000 &&
        this.modifyQuestion.questionId < 200000
      ) {
        axios({
          method: "post",
          url: "/api/choiceQuestion/save",
          data: {
            questionId: this.modifyQuestion.questionId,
            subjectId: this.modifySubjectId,
            question: this.modifyQuestion.question,
            answerA: this.modifyQuestion.answerA,
            answerB: this.modifyQuestion.answerB,
            answerC: this.modifyQuestion.answerC,
            answerD: this.modifyQuestion.answerD,
            rightAnswer: this.modifyQuestion.rightAnswer,
            analysis: this.modifyQuestion.analysis,
            score: this.modifyQuestion.score,
            level: this.modifyQuestion.level,
          },
        }).then((res) => {
          if (res.status === 200) {
            this.reload();
            this.$message({
              showClose: true,
              message: "修改成功",
              type: "success",
            });
            this.modifyVisible = false;
          }
        });
      } else if (
        this.modifyQuestion.questionId >= 200000 &&
        this.modifyQuestion.questionId < 300000
      ) {
        axios({
          method: "post",
          url: "/api/fillQuestion/save",
          data: {
            questionId: this.modifyQuestion.questionId,
            subjectId: this.modifySubjectId,
            question: this.modifyQuestion.question,
            answer: this.modifyQuestion.rightAnswer,
            analysis: this.modifyQuestion.analysis,
            score: this.modifyQuestion.score,
            level: this.modifyQuestion.level,
          },
        }).then((res) => {
          if (res.status === 200) {
            this.reload();
            this.$message({
              showClose: true,
              message: "修改成功",
              type: "success",
            });
            this.modifyVisible = false;
          }
        });
      } else if (
        this.modifyQuestion.questionId >= 300000 &&
        this.modifyQuestion.questionId < 400000
      ) {
        axios({
          method: "post",
          url: "/api/judgeQuestion/save",
          data: {
            questionId: this.modifyQuestion.questionId,
            subjectId: this.modifySubjectId,
            question: this.modifyQuestion.question,
            answer: this.modifyQuestion.rightAnswer,
            analysis: this.modifyQuestion.analysis,
            score: this.modifyQuestion.score,
            level: this.modifyQuestion.level,
          },
        }).then((res) => {
          if (res.status === 200) {
            this.reload();
            this.$message({
              showClose: true,
              message: "修改成功",
              type: "success",
            });
            this.modifyVisible = false;
          }
        });
      } else if (this.modifyQuestion.questionId >= 400000) {
        axios({
          method: "post",
          url: "/api/subjectiveQuestion/save",
          data: {
            questionId: this.modifyQuestion.questionId,
            subjectId: this.modifySubjectId,
            question: this.modifyQuestion.question,
            answer: this.modifyQuestion.rightAnswer,
            analysis: this.modifyQuestion.analysis,
            score: this.modifyQuestion.score,
            level: this.modifyQuestion.level,
          },
        }).then((res) => {
          if (res.status === 200) {
            this.reload();
            this.$message({
              showClose: true,
              message: "修改成功",
              type: "success",
            });
            this.modifyVisible = false;
          }
        });
      }
    },
    deleteRow(index, rows) {
      let questionId = rows[index].questionId;
      console.log(questionId);
      this.$confirm("此操作将永久删除该题目，是否继续？", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          if (questionId >= 100000 && questionId < 200000) {
            axios({
              method: "get",
              url: "/api/choiceQuestion/delete",
              params: {
                questionId: questionId,
              },
            }).then((res) => {
              this.$message({
                type: "success",
                message: "删除成功!",
              });
              this.reload();
            });
          } else if (questionId >= 200000 && questionId < 300000) {
            axios({
              method: "get",
              url: "/api/fillQuestion/delete",
              params: {
                questionId: questionId,
              },
            }).then((res) => {
              this.$message({
                type: "success",
                message: "删除成功!",
              });
              this.reload();
            });
          } else if (questionId >= 300000 && questionId < 400000) {
            axios({
              method: "get",
              url: "/api/judgeQuestion/delete",
              params: {
                questionId: questionId,
              },
            }).then((res) => {
              this.$message({
                type: "success",
                message: "删除成功!",
              });
              this.reload();
            });
          } else if (questionId >= 400000) {
            axios({
              method: "get",
              url: "/api/subjectiveQuestion/delete",
              params: {
                questionId: questionId,
              },
            }).then((res) => {
              this.$message({
                type: "success",
                message: "删除成功!",
              });
              this.reload();
            });
          }
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
    selectSubject(subjectId) {
      this.subjectId = subjectId;
      //console.log(this.subjectId);
    },
    selectType() {
      console.log(this.searchForm.type);
    },
    handleCurrentChange(val) {
      this.currentPage = val;
      this.searchList();
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
  },
};
</script>

<style lang="scss" scoped>
.add-question-wraper {
  height: auto;
}
.question-container {
  margin: 20px;
  height: 900px;
  text-align: left;
}
.searchForm {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
}
.el-form-item {
  margin-right: 60px;
}
.el-form-item::last-child {
  margin-right: 5px;
}
.el-input {
  width: 210px;
}
.pageination {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}
.detailBox .el-row {
  display: flex;
  flex-direction: row;
  font-size: 14px;
  padding: 5px;
}
.itemTitle {
  width: 15%;
  text-align: right;
  padding: 0 10px 5px 0;
}
.show {
  width: 70%;
  overflow: auto;
  max-height: 60px;
}
.answerSelect {
  display: flex;
  flex-direction: column;
  width: 70%;
  overflow: auto;
  max-height: 100px;
}
.answerItem {
  width: 100%;
  padding-bottom: 5px;
}
.modifyBox {
  display: flex;
  flex-direction: column;
  padding: 5px;
}
.item {
  margin-top: 10px;
  margin-right: 10px;
}
.el-scrollbar__wrap {
  overflow-x: auto;
  height: calc(100% + 20px);
}
.el-scrollbar .el-scrollbar__wrap .el-scrollbar__view {
  white-space: nowrap;
  display: inline-block;
}
</style>