<template>
  <div class="paper-list">
    <el-form
      :model="selectForm"
      ref="selectForm"
      label-width="100px"
      class="selectForm"
    >
      <div class="form-line">
        <el-form-item label="试卷ID：">
          <el-input
            placeholder="输入试卷ID"
            v-model="selectForm.paperId"
            clearable
          ></el-input>
        </el-form-item>

        <el-form-item label="试卷类型：">
          <el-select
            v-model="paperType"
            clearable
            placeholder="请选择"
            @change="selectType(paperType)"
          >
            <el-option
              v-for="item in selectForm.type"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
      </div>

      <div class="form-line">
        <el-form-item label="试卷名称：">
          <el-input
            placeholder="输入试卷名称"
            v-model="selectForm.paperTitle"
            clearable
          ></el-input>
        </el-form-item>

        <el-form-item label-width="15px">
          <el-button type="primary" icon="el-icon-search" @click="searchPaper"
            >搜索</el-button
          >
        </el-form-item>
      </div>
    </el-form>

    <el-divider></el-divider>

    <div class="result-table">
      <el-table
        :data="paperData"
        border
        style="width: 100%"
        v-loading="loading"
      >
        <el-table-column fixed prop="paperId" label="ID" width="100">
        </el-table-column>
        <el-table-column prop="paperTitle" label="试卷名称" width="120">
        </el-table-column>
        <el-table-column prop="totalScore" label="试卷总分" width="120">
        </el-table-column>
        <el-table-column prop="type" label="创建类型" width="100">
        </el-table-column>
        <el-table-column prop="choiceNum" label="选择题数量" width="100">
        </el-table-column>
        <el-table-column prop="fillNum" label="填空题数量" width="100">
        </el-table-column>
        <el-table-column prop="judgeNum" label="判断题数量" width="100">
        </el-table-column>
        <el-table-column prop="subjectiveNum" label="主观题数量" width="100">
        </el-table-column>
        <el-table-column prop="createTime" label="创建日期" width="200">
        </el-table-column>
        <el-table-column prop="updateTime" label="修改日期" width="200">
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="260">
          <template slot-scope="scope">
            <el-button
              icon="el-icon-info"
              @click.native.prevent="showDetail(scope.$index, paperData)"
              type="primary"
              size="small"
              >详情</el-button
            >
            <el-button
              icon="el-icon-edit"
              @click.native.prevent="showModify(scope.$index, paperData)"
              type="success"
              size="small"
              >修改</el-button
            >

            <el-button
              icon="el-icon-delete"
              @click.native.prevent="deletePaper(scope.$index, paperData)"
              type="danger"
              size="small"
              >删除</el-button
            >
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

    <!-- 查看试卷详情 -->
    <el-dialog
      title="试卷详情"
      :visible.sync="detailVisible"
      :append-to-body="true"
      :close-on-click-modal="false"
      width="50%"
    >
      <div class="detail">
        <el-form>
          <el-form-item style="margin-bottom: 5px" label="试卷ID：">
            <span>{{ showPaperInfo.paperId }}</span>
          </el-form-item>
          <el-form-item style="margin-bottom: 5px" label="试卷名称：">
            <span>{{ showPaperInfo.paperTitle }}</span>
          </el-form-item>
          <el-form-item style="margin-bottom: 10px" label="试卷总分：">
            <span>{{ showPaperInfo.totalScore }}</span>
          </el-form-item>
          <el-form-item style="margin-bottom: 5px" label="试卷类型：">
            <span>{{ showPaperInfo.type }}</span>
          </el-form-item>
          <el-form-item style="margin-bottom: 5px" label="题目数量：">
            <span>选择题（{{ showPaperInfo.choiceNum }}题）</span>
            <span style="margin-left: 10px"
              >填空题（{{ showPaperInfo.fillNum }}题）</span
            >
            <span style="margin-left: 10px"
              >判断题（{{ showPaperInfo.judgeNum }}题）</span
            >
            <span style="margin-left: 10px"
              >主观题（{{ showPaperInfo.subjectiveNum }}题）</span
            >
          </el-form-item>
          <el-form-item style="margin-bottom: 5px" label="创建时间：">
            <span>{{ showPaperInfo.createTime }}</span>
          </el-form-item>
          <el-form-item style="margin-bottom: 5px" label="修改时间：">
            <span>{{ showPaperInfo.updateTime }}</span>
          </el-form-item>
        </el-form>
        <el-divider></el-divider>
        <div style="">试卷预览：</div>
        <div class="questionList">
          <div
            class="question-item"
            v-for="(item, index) in showQuestionItem"
            :key="item.questionId"
          >
            <div class="question-title">
              <span
                >{{ index + 1 }}. {{ item.question }} ({{ item.score }}分)</span
              >
            </div>
            <div
              style="margin-left: 15px"
              v-if="item.questionId >= 100000 && item.questionId < 200000"
            >
              <div class="choiceItem">A. {{ item.answerA }}</div>
              <div class="choiceItem">B. {{ item.answerB }}</div>
              <div class="choiceItem">C. {{ item.answerC }}</div>
              <div class="choiceItem">D. {{ item.answerD }}</div>
            </div>

            <div
              style="margin-top: 10px"
              v-if="item.questionId >= 200000 && item.questionId < 300000"
            >
              <div><span>答题：</span>_____</div>
            </div>

            <div
              style="margin-top: 10px"
              v-if="item.questionId >= 300000 && item.questionId < 400000"
            >
              <div>
                <span>答题：( ) </span
                ><i style="font-size: 14px; margin-left: 20px">(填写T或者F)</i>
              </div>
            </div>

            <div style="margin-top: 10px" v-if="item.questionId >= 400000">
              <span>答题：</span><span>_____________________________</span>
            </div>

            <div style="margin-top: 10px">
              <span>标准答案:</span
              ><span style="margin-left: 10px">{{ item.rightAnswer }}</span>
            </div>
            <div
              class="level"
              style="display: flex; flex-direction: row; margin-top: 10px"
            >
              <span>难度：</span>
              <span
                ><el-rate
                  v-model="showQuestionItem[index].level"
                  disabled
                  show-score
                  text-color="#ff9900"
                >
                </el-rate
              ></span>
            </div>
          </div>
        </div>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="detailVisible = false">关闭</el-button>
      </div>
    </el-dialog>

    <!-- 修改试卷 -->
    <el-dialog
      title="修改试卷"
      :visible.sync="modifyVisible"
      :append-to-body="true"
      :close-on-click-modal="false"
      width="50%"
    >
      <div class="detail">
        <el-form class="modify-form" label-width="100" label-position="right">
          <el-form-item
            class="paper-item"
            label="试卷ID："
            style="margin-left: 13px; margin-bottom: 15px"
          >
            <el-input v-model="modifyPaperInfo.paperId" disabled></el-input>
          </el-form-item>
          <el-form-item
            class="paper-item"
            label="试卷名称："
            style="margin-bottom: 15px"
          >
            <el-input v-model="modifyPaperInfo.paperTitle"></el-input>
          </el-form-item>

          <el-form-item
            class="paper-item"
            label="题目操作："
            style="margin-bottom: 15px"
          >
            <el-button type="success" @click="handleChooseQuestion"
              >添加题目</el-button
            >
          </el-form-item>
        </el-form>

        <el-divider></el-divider>
        <div style="">试卷预览：</div>
        <div class="questionList">
          <div
            class="question-item"
            v-for="(item, index) in modifyQuestionItem"
            :key="item.questionId"
          >
            <div class="question-title">
              <span
                >{{ index + 1 }}. {{ item.question }} ({{ item.score }}分)</span
              >
              <!-- <el-input-number
                v-model="item.score"
                :min="0"
                :max="20"
                size="mini"
                style="float: right"
              ></el-input-number> -->
            </div>
            <div
              style="margin-left: 15px; margin-top: 10px"
              v-if="item.questionId >= 100000 && item.questionId < 200000"
            >
              <!--  <el-button
                type="danger"
                size="small"
                style="float: right"
                @click="deleteQuestion(index)"
                >删除题目</el-button
              > -->
              <div class="choiceItem">A. {{ item.answerA }}</div>
              <div class="choiceItem">B. {{ item.answerB }}</div>
              <div class="choiceItem">C. {{ item.answerC }}</div>
              <div class="choiceItem">D. {{ item.answerD }}</div>
            </div>

            <div
              style="margin-top: 10px"
              v-if="item.questionId >= 200000 && item.questionId < 300000"
            >
              <div><span>答题：</span>_____</div>
            </div>

            <div
              style="margin-top: 10px"
              v-if="item.questionId >= 300000 && item.questionId < 400000"
            >
              <div>
                <span>答题：( ) </span
                ><i style="font-size: 14px; margin-left: 20px">(填写T或者F)</i>
              </div>
            </div>

            <div style="margin-top: 10px" v-if="item.questionId >= 400000">
              <span>答题：</span><span>_____________________________</span>
            </div>

            <div style="margin-top: 10px">
              <span>标准答案:</span
              ><span style="margin-left: 10px">{{ item.rightAnswer }}</span>
            </div>
            <div
              class="level"
              style="display: flex; flex-direction: row; margin-top: 10px"
            >
              <span>难度：</span>
              <span
                ><el-rate
                  v-model="modifyQuestionItem[index].level"
                  disabled
                  show-score
                  text-color="#ff9900"
                >
                </el-rate
              ></span>
            </div>
          </div>
        </div>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="modifyPaper">修改</el-button>
        <el-button @click="modifyVisible = false">关闭</el-button>
      </div>
      <!-- 添加题目 -->
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
                style="width: 210px"
              ></el-input>
            </el-form-item>

            <el-form-item label="题干：" class="searchItem">
              <el-input
                placeholder="输入题目内容"
                v-model="searchForm.question"
                style="width: 210px"
              ></el-input>
            </el-form-item>
            <el-form-item label-width="20px">
              <el-button
                type="primary"
                icon="el-icon-search"
                @click="searchList"
                >搜索</el-button
              >
            </el-form-item>
          </div>

          <el-divider></el-divider>
          <el-table
            ref="questionSelection"
            :data="resultData"
            border
            style="width: 100%"
            @selection-change="handleSelectionChange"
          >
            <el-table-column fixed type="selection" width="50">
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

          <!-- 分页 -->
          <div class="pageination">
            <el-pagination
              background
              @current-change="handleQuestionCurrentChange"
              :current-page="questionCurrentPage"
              :page-size="questionPageSize"
              layout="total, prev, pager, next, jumper"
              :total="questionPageTotal"
            >
            </el-pagination>
          </div>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="handleAddQuestion">添加</el-button>
          <el-button @click="addQuestionVisible = false">关闭</el-button>
        </div>
      </el-dialog>
    </el-dialog>
  </div>
</template>

<script>
import axios from "axios";
export default {
  created: function () {
    axios({
      method: "post",
      url: "/api/subject",
    }).then((res) => {
      let subjects = res.data.data;
      this.subjects = subjects;
    });
    this.searchPaper();
  },
  data() {
    return {
      paperType: "",
      selectForm: {
        paperId: "",
        type: [
          {
            value: 0,
            label: "人工组卷",
          },
          {
            value: 1,
            label: "自动组卷",
          },
        ],
        paperTitle: "",
      },
      paperData: [],
      showPaperInfo: {},
      showQuestionItem: [],
      modifyPaperInfo: {},
      modifyQuestionItem: [],
      modifyQuestionPreview: [],
      selections: {},
      subjectIds: [],
      subjects: [],
      subjectId: "",
      searchForm: {
        subject: "",
        type: "",
        id: null,
        question: "",
      },
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
      resultData: [],
      multipleSelectionAll: [], // 所有选中的数据包含跨页数据
      multipleSelection: [], //当前页选中的数据
      idKey: "questionId",
      currentPage: 1,
      pageSize: 5,
      pageTotal: null,
      questionCurrentPage: 1,
      questionPageSize: 5,
      questionPageTotal: null,
      loading: false,
      detailVisible: false,
      modifyVisible: false,
      addQuestionVisible: false,
    };
  },
  methods: {
    selectType(paperType) {
      this.paperType = paperType;
      //console.log(this.paperType);
    },
    handleCurrentChange(val) {
      this.currentPage = val;
      this.searchPaper();
    },
    searchPaper() {
      axios({
        method: "post",
        url: "/api/paper/list",
        data: {
          paperId: this.selectForm.paperId,
          type: this.paperType,
          paperTitle: this.selectForm.paperTitle,
          currentPage: this.currentPage,
          pageSize: this.pageSize,
        },
      }).then((res) => {
        this.paperData = [];
        if (res.status === 200) {
          this.loading = true;
          let result = res.data.data;
          let type = "";
          this.pageTotal = result.pageTotal;
          setTimeout(() => {
            for (let i = 0; i < result.pageData.length; i++) {
              if (result.pageData[i].type === 0) {
                type = "人工组卷";
              } else if (result.pageData[i].type === 1) {
                type = "自动组卷";
              }
              this.paperData.push({
                paperId: result.pageData[i].paperId,
                paperTitle: result.pageData[i].paperTitle,
                totalScore: result.pageData[i].totalScore,
                type: type,
                choiceNum: result.pageData[i].choiceNum,
                fillNum: result.pageData[i].fillNum,
                judgeNum: result.pageData[i].judgeNum,
                subjectiveNum: result.pageData[i].subjectiveNum,
                createTime: result.pageData[i].createTime,
                updateTime: result.pageData[i].updateTime,
              });
            }
            this.loading = false;
          }, 500);
        }
      });
    },
    selectSubject(subjectId) {
      this.subjectId = subjectId;
      console.log(this.subjectId);
    },
    searchList() {
      let subjectId = this.subjectId;
      let type = this.searchForm.type;
      let id = this.searchForm.id;
      let question = this.searchForm.question;
      let currentPage = this.questionCurrentPage;
      let pageSize = this.questionPageSize;

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

          let result = res.data.data;
          let pageData = result.pageData;
          let type = "";
          //   console.log(result);
          //   console.log(pageData);
          //   console.log(result.pageTotal);
          this.questionPageTotal = result.pageTotal;
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
            this.resultData.push({
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
          setTimeout(() => {
            console.log("searchList");
            this.setSelectRow();
          }, 20);
        }
      });
    },
    toggleSelection(rows) {
      if (rows) {
        rows.forEach((row) => {
          setTimeout(() => {
            this.$refs.questionSelection.toggleRowSelection(row, true);
          }, 150);
        });
      } else {
        this.$nextTick()
          .then(() => {})
          .catch((error) => {});
        setTimeout(() => {
          this.$refs.questionSelection.clearSelection();
        }, 100);
      }
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
      console.log("====change");
      console.log(this.multipleSelection);
    },
    handleQuestionCurrentChange(val) {
      this.changePageCoreRecordData();
      this.questionCurrentPage = val;
      this.searchList();
    },
    handleChooseQuestion() {
      this.questionCurrentPage = 1;
      this.searchList();
      this.addQuestionVisible = true;
    },
    handleAddQuestion() {
      this.changePageCoreRecordData();
      this.modifyQuestionItem = this.multipleSelectionAll;
      this.addQuestionVisible = false;
      if (this.multipleSelectionAll.length <= 0) {
        this.$message({
          message: "无勾选数据！",
          type: "warning",
        });
        return;
      }
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
      this.$nextTick().then(() => {
        this.$refs.questionSelection.clearSelection();
      });

      for (let i = 0; i < this.resultData.length; i++) {
        if (selectAllIds.indexOf(this.resultData[i][idKey]) >= 0) {
          this.$nextTick().then(() => {
            this.$refs.questionSelection.toggleRowSelection(
              this.resultData[i],
              true
            );
          });
        }
      }
    },

    changePageCoreRecordData() {
      let idKey = this.idKey;
      let that = this;

      if (this.multipleSelectionAll.length <= 0) {
        console.log("multipleSelection is empty");
        this.multipleSelectionAll = this.multipleSelection;
        return;
      }

      let selectAllIds = [];
      this.multipleSelectionAll.forEach((row) => {
        selectAllIds.push(row[idKey]);
      });

      console.log("===selectAllIds:");
      console.log(selectAllIds);

      let selectIds = [];
      this.multipleSelection.forEach((row) => {
        selectIds.push(row[idKey]);
        if (selectAllIds.indexOf(row[idKey]) < 0) {
          that.multipleSelectionAll.push(row);
        }
      });

      console.log("===selectIds:");
      console.log(selectIds);

      let noSelectIds = [];
      this.resultData.forEach((row) => {
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
    showDetail(index, rows) {
      let currentId = rows[index].paperId;
      axios({
        method: "get",
        url: "/api/paper/detail",
        params: {
          paperId: currentId,
        },
      }).then((res) => {
        this.showPaperInfo = {};
        this.showQuestionItem = [];
        if (res.status === 200) {
          let result = res.data.data;
          console.log(result);

          let paperInfo = result.paper;
          console.log(paperInfo);
          let questionDetail = result.questionDetail;

          this.showPaperInfo = paperInfo;
          if (paperInfo.type === 0) {
            this.showPaperInfo.type = "人工组卷";
          } else if (paperInfo.type === 1) {
            this.showPaperInfo.type = "自动组卷";
          }

          for (let i = 0; i < questionDetail.length; i++) {
            this.showQuestionItem.push({
              questionId: questionDetail[i].questionId,
              question: questionDetail[i].question,
              subject: questionDetail[i].subject,
              answerA: questionDetail[i].answerA,
              answerB: questionDetail[i].answerB,
              answerC: questionDetail[i].answerC,
              answerD: questionDetail[i].answerD,
              rightAnswer: questionDetail[i].rightAnswer,
              analysis: questionDetail[i].analysis,
              score: questionDetail[i].score,
              level: questionDetail[i].level,
            });
          }
          this.detailVisible = true;

          console.log(this.showPaperInfo);
          //console.log(this.showQuestionItem);
        }
      });
    },
    showModify(index, rows) {
      let currentId = rows[index].paperId;
      this.questionCurrentPage = 1;
      axios({
        method: "get",
        url: "/api/paper/detail",
        params: {
          paperId: currentId,
        },
      }).then((res) => {
        this.modifyPaperInfo = {};
        this.modifyQuestionPreview = [];
        //this.modifyQuestionItem = [];
        if (res.status === 200) {
          let result = res.data.data;
          //console.log(result);

          let paperInfo = result.paper;
          //console.log(paperInfo);
          let questionDetail = result.questionDetail;

          this.modifyPaperInfo = paperInfo;
          if (paperInfo.type === 0) {
            this.modifyPaperInfo.type = "人工组卷";
          } else if (paperInfo.type === 1) {
            this.modifyPaperInfo.type = "自动组卷";
          }

          for (let i = 0; i < questionDetail.length; i++) {
            this.modifyQuestionPreview.push({
              questionId: questionDetail[i].questionId,
              question: questionDetail[i].question,
              subject: questionDetail[i].subject,
              answerA: questionDetail[i].answerA,
              answerB: questionDetail[i].answerB,
              answerC: questionDetail[i].answerC,
              answerD: questionDetail[i].answerD,
              rightAnswer: questionDetail[i].rightAnswer,
              analysis: questionDetail[i].analysis,
              score: questionDetail[i].score,
              level: questionDetail[i].level,
            });
          }
          this.modifyQuestionItem = this.modifyQuestionPreview;
          this.multipleSelectionAll = this.modifyQuestionPreview;
          this.modifyVisible = true;
        }
      });
    },

    modifyPaper() {
      if (
        this.modifyPaperInfo.paperTitle !== "" &&
        this.modifyQuestionPreview.length !== 0
      ) {
        console.log("========= modifyPaper =======");
        console.log(this.modifyQuestionPreview);
        console.log(this.multipleSelection);
        axios({
          method: "post",
          url: "/api/paper/modify",
          data: {
            paperQuestion: this.modifyQuestionPreview,
            paperId: this.modifyPaperInfo.paperId,
            paperTitle: this.modifyPaperInfo.paperTitle,
            choiceNum: this.modifyPaperInfo.choiceNum,
            fillNum: this.modifyPaperInfo.fillNum,
            judgeNum: this.modifyPaperInfo.judgeNum,
            subjectiveNum: this.modifyPaperInfo.subjectiveNum,
            choiceScore: this.modifyPaperInfo.choiceScore,
            fillScore: this.modifyPaperInfo.fillScore,
            judgeScore: this.modifyPaperInfo.judgeScore,
            subjectiveScore: this.modifyPaperInfo.subjectiveScore,
            status: this.modifyPaperInfo.status,
          },
        }).then((res) => {
          if (res.status === 200) {
            this.$message({
              showClose: true,
              message: "修改成功！",
              type: "success",
            });
            this.searchPaper();
          }
        });
      } else {
        this.$message({
          showClose: true,
          message: "修改题目和试卷题目列表不能为空！",
          type: "error",
        });
      }
      this.modifyVisible = false;
    },

    deletePaper(index, rows) {
      let currentId = rows[index].paperId;
      axios({
        method: "get",
        url: "/api/paper/delete",
        params: {
          paperId: currentId,
        },
      }).then((res) => {
        if (res.status === 200) {
          this.$message({
            message: "删除成功！",
            type: "success",
          });
          this.searchPaper();
          return;
        }
      });
    },
  },
};
</script>

<style lang="scss" scoped>
.paper-list {
  padding: 20px;
  display: flex;
  flex-direction: column;
}
.selectForm {
  display: flex;
  flex-direction: column;
  padding: 10px;
}
.form-line {
  display: flex;
  flex-direction: row;
}
.form-line .el-form-item {
  margin-right: 15px;
}
.pageination {
  display: flex;
  justify-content: center;
  margin-top: 15px;
}
.detail {
  padding: 0 20px 20px 20px;
  max-height: 350px;
  overflow: auto;
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
.choiceItem {
  margin-top: 5px;
}
.choiceItem:first-child {
  margin-top: 0px;
}
.modify-form {
  display: flex;
  flex-direction: column;
}
.paper-item {
  margin-bottom: 5px;
  display: flex;
  flex-direction: row;
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
  margin-right: 10px;
}
</style>