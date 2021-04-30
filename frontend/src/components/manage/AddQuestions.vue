<template>
  <div class="add-question-wrapper">
    <el-tabs v-model="activeName" type="border-card">
      <!-- 添加选择题 -->
      <el-tab-pane label="选择题" name="first">
        <div class="question-container">
          <el-form
            :model="choiceForm"
            :rules="rules"
            ref="choiceForm"
            label-width="100px"
            class="question-form"
          >
            <el-form-item label="学科：" prop="subject">
              <el-select
                v-model="choiceForm.subjectId"
                placeholder="选择学科"
                filterable
              >
                <el-option
                  v-for="item in subjects"
                  :key="item.subjectId"
                  :label="item.subjectName"
                  :value="item.subjectId"
                ></el-option>
              </el-select>
            </el-form-item>

            <el-form-item label="题目：" prop="content">
              <el-input
                type="textarea"
                v-model="choiceForm.content"
                style="width: 400px; font-size: 16px"
                maxlength="255"
                resize="none"
                :autosize="{ minRows: 1, maxRows: 2 }"
              ></el-input>
            </el-form-item>

            <el-form-item label="选项：" class="choice" prop="item">
              <el-input
                class="item"
                v-model="choiceForm.answerA"
                prop="itemA"
                ref="answerA"
              >
                <template slot="prepend">A</template>
              </el-input>
              <el-input
                class="item"
                v-model="choiceForm.answerB"
                prop="itemB"
                ref="answerB"
              >
                <template slot="prepend">B</template>
              </el-input>
              <br />
              <el-input
                class="item"
                v-model="choiceForm.answerC"
                prop="itemC"
                ref="answerC"
              >
                <template slot="prepend">C</template>
              </el-input>
              <el-input
                class="item"
                v-model="choiceForm.answerD"
                prop="itemD"
                ref="answerD"
              >
                <template slot="prepend">D</template>
              </el-input>
            </el-form-item>

            <el-form-item label="答案：" prop="rightAnswer">
              <el-radio
                v-model="choiceForm.rightAnswer"
                label="a"
                border
                size="medium"
                >A</el-radio
              >
              <el-radio
                v-model="choiceForm.rightAnswer"
                label="b"
                border
                size="medium"
                >B</el-radio
              >
              <el-radio
                v-model="choiceForm.rightAnswer"
                label="c"
                border
                size="medium"
                >C</el-radio
              >
              <el-radio
                v-model="choiceForm.rightAnswer"
                label="d"
                border
                size="medium"
                >D</el-radio
              >
            </el-form-item>

            <el-form-item label="解析：" prop="analysis">
              <el-input
                type="textarea"
                v-model="choiceForm.analysis"
                style="width: 400px; font-size: 16px"
                resize="none"
                :autosize="{ minRows: 1, maxRows: 2 }"
                maxlength="255"
              ></el-input>
            </el-form-item>

            <el-form-item label="分值：" prop="score">
              <el-input-number
                v-model="choiceForm.score"
                :precision="1"
                :step="1"
                :max="20"
                :min="0.1"
              ></el-input-number>
            </el-form-item>

            <el-form-item label="难易度：" prop="level">
              <el-rate
                v-model="choiceForm.level"
                show-text
                :texts="texts"
              ></el-rate>
            </el-form-item>

            <el-form-item>
              <el-button type="primary" @click="addChoiceQuestion"
                >添加</el-button
              >
              <el-button native-type="submit">重置</el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-tab-pane>

      <!-- 添加填空题 -->
      <el-tab-pane label="填空题" name="second">
        <div class="question-container">
          <el-form
            :model="fillForm"
            :rules="rules"
            ref="fillForm"
            label-width="100px"
            class="question-form"
          >
            <el-form-item label="学科：" prop="subject">
              <el-select
                v-model="fillForm.subjectId"
                placeholder="选择学科"
                filterable
              >
                <el-option
                  v-for="item in subjects"
                  :key="item.subjectId"
                  :label="item.subjectName"
                  :value="item.subjectId"
                ></el-option>
              </el-select>
            </el-form-item>

            <el-form-item label="题目：" prop="content">
              <el-input
                type="textarea"
                v-model="fillForm.content"
                style="width: 400px; font-size: 16px"
                resize="none"
                :autosize="{ minRows: 1, maxRows: 2 }"
                maxlength="255"
              ></el-input>
            </el-form-item>

            <el-form-item label="答案：" prop="rightAnswer">
              <el-input
                type="textarea"
                v-model="fillForm.rightAnswer"
                style="width: 400px; font-size: 16px"
                resize="none"
                autosize
                maxlength="255"
              ></el-input>
            </el-form-item>

            <el-form-item label="解析：">
              <el-input
                type="textarea"
                v-model="fillForm.analysis"
                style="width: 400px; font-size: 16px"
                resize="none"
                :autosize="{ minRows: 1, maxRows: 2 }"
                maxlength="255"
              ></el-input>
            </el-form-item>

            <el-form-item label="分值：" prop="score">
              <el-input-number
                v-model="fillForm.score"
                :precision="1"
                :step="1"
                :max="20"
                :min="0.1"
              ></el-input-number>
            </el-form-item>

            <el-form-item label="难易度：" prop="level">
              <el-rate
                v-model="fillForm.level"
                show-text
                :texts="texts"
              ></el-rate>
            </el-form-item>

            <el-form-item>
              <el-button type="primary" @click="addFillQuestion"
                >添加</el-button
              >
              <el-button native-type="submit">重置</el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-tab-pane>

      <!-- 添加判断题 -->
      <el-tab-pane label="判断题" name="third">
        <div class="question-container">
          <el-form
            :model="judgeForm"
            :rules="rules"
            ref="judgeForm"
            label-width="100px"
            class="question-form"
          >
            <el-form-item label="学科：" prop="subject">
              <el-select
                v-model="judgeForm.subjectId"
                placeholder="选择学科"
                filterable
              >
                <el-option
                  v-for="item in subjects"
                  :key="item.subjectId"
                  :label="item.subjectName"
                  :value="item.subjectId"
                ></el-option>
              </el-select>
            </el-form-item>

            <el-form-item label="题目：" prop="content">
              <el-input
                type="textarea"
                v-model="judgeForm.content"
                style="width: 400px; font-size: 16px"
                resize="none"
                :autosize="{ minRows: 1, maxRows: 2 }"
                maxlength="255"
              ></el-input>
            </el-form-item>

            <el-form-item label="答案：" prop="rightAnswer">
              <el-radio
                v-model="judgeForm.rightAnswer"
                label="T"
                border
                size="medium"
                >对</el-radio
              >
              <el-radio
                v-model="judgeForm.rightAnswer"
                label="F"
                border
                size="medium"
                >错</el-radio
              >
            </el-form-item>

            <el-form-item label="解析：">
              <el-input
                type="textarea"
                v-model="judgeForm.analysis"
                style="width: 400px; font-size: 16px"
                resize="none"
                :autosize="{ minRows: 1, maxRows: 2 }"
                maxlength="255"
              ></el-input>
            </el-form-item>

            <el-form-item label="分值：" prop="score">
              <el-input-number
                v-model="judgeForm.score"
                :precision="1"
                :step="1"
                :max="20"
                :min="0.1"
              ></el-input-number>
            </el-form-item>

            <el-form-item label="难易度：" prop="level">
              <el-rate
                v-model="judgeForm.level"
                show-text
                :texts="texts"
              ></el-rate>
            </el-form-item>

            <el-form-item>
              <el-button type="primary" @click="addJudgeQuestion"
                >添加</el-button
              >
              <el-button native-type="submit">重置</el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-tab-pane>

      <!-- 添加主观题 -->
      <el-tab-pane label="主观题" name="fourth">
        <div class="question-container">
          <el-form
            :model="subjectiveForm"
            :rules="rules"
            ref="subjectiveForm"
            label-width="100px"
            class="question-form"
          >
            <el-form-item label="学科：" prop="subject">
              <el-select
                v-model="subjectiveForm.subjectId"
                placeholder="选择学科"
                filterable
              >
                <el-option
                  v-for="item in subjects"
                  :key="item.subjectId"
                  :label="item.subjectName"
                  :value="item.subjectId"
                ></el-option>
              </el-select>
            </el-form-item>

            <el-form-item label="题目：" prop="content">
              <el-input
                type="textarea"
                v-model="subjectiveForm.content"
                style="width: 400px; font-size: 16px"
                resize="none"
                :autosize="{ minRows: 1, maxRows: 2 }"
                maxlength="255"
              ></el-input>
            </el-form-item>

            <el-form-item label="答案：" prop="rightAnswer">
              <el-input
                type="textarea"
                v-model="subjectiveForm.rightAnswer"
                style="width: 400px; font-size: 16px"
                resize="none"
                autosize
                maxlength="255"
              ></el-input>
            </el-form-item>

            <el-form-item label="解析：">
              <el-input
                type="textarea"
                v-model="subjectiveForm.analysis"
                style="width: 400px; font-size: 16px"
                resize="none"
                :autosize="{ minRows: 1, maxRows: 2 }"
                maxlength="255"
              ></el-input>
            </el-form-item>

            <el-form-item label="分值：" prop="score">
              <el-input-number
                v-model="subjectiveForm.score"
                :precision="1"
                :step="1"
                :max="20"
                :min="0.1"
              ></el-input-number>
            </el-form-item>

            <el-form-item label="难易度：" prop="level">
              <el-rate
                v-model="subjectiveForm.level"
                show-text
                :texts="texts"
              ></el-rate>
            </el-form-item>

            <el-form-item>
              <el-button type="primary" @click="addSubjectiveQuestion"
                >添加</el-button
              >
              <el-button native-type="submit">重置</el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-tab-pane>
    </el-tabs>

    <!-- 学科操作卡片 -->
    <section class="card">
      <el-card class="subject-card">
        <div slot="header">
          <span>学科操作</span>
        </div>
        <div class="subject-option">
          <el-button type="primary" @click="subjectAddVisible = true"
            >添加学科</el-button
          >
          <el-button type="danger" @click="subjectDeleteVisible = true"
            >删除学科</el-button
          >
        </div>
      </el-card>
    </section>

    <!-- 新增学科 -->
    <el-dialog
      title="新增学科"
      :visible.sync="subjectAddVisible"
      :append-to-body="true"
      :close-on-click-modal="false"
      width="35%"
    >
      <div>
        <el-form :model="subjectForm" :label-width="formLabelWidth">
          <el-form-item label="学科名称：">
            <el-input v-model="subjectForm.subjectName"></el-input>
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="addSubject">添加</el-button>
        <el-button @click="subjectAddVisible = false">取消</el-button>
      </div>
    </el-dialog>

    <!-- 删除学科 -->
    <el-dialog
      title="删除学科"
      :visible.sync="subjectDeleteVisible"
      :append-to-body="true"
      :close-on-click-modal="false"
      width="35%"
    >
      <el-dialog
        width="30%"
        title="确认框"
        :visible.sync="innerVisible"
        :append-to-body="true"
        :close-on-click-modal="false"
      >
        <p style="font-size: 16px">
          警告：你真的确定要删除吗？该操作不可逆!!且题库中包含删除学科的题目将被一起删除！
        </p>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="deleteSubject">确定</el-button>
          <el-button @click="innerVisible = false">取消</el-button>
        </div>
      </el-dialog>
      <div class="delete-list-wrapper">
        <div class="deleteList">
          <el-checkbox
            :indeterminate="isIndeterminate"
            v-model="checkAll"
            class="checkAll"
            @change="handleCheckAllChange"
            >全选</el-checkbox
          >
          <el-checkbox-group
            v-model="subjectDeleteList"
            size="medium"
            @change="handleCheckedSubjectChange"
          >
            <el-checkbox
              v-for="item in subjects"
              :key="item.subjectId"
              :label="item.subjectName"
              border
            ></el-checkbox>
          </el-checkbox-group>
        </div>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="danger" @click="innerVisible = true">删除</el-button>
        <el-button @click="subjectDeleteVisible = false">取消</el-button>
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
      //console.log(res);
      //console.log(res.data.data);
      let subjects = res.data.data;
      this.subjects = subjects;
    });
  },
  data() {
    let checkAnswerItem = (rule, value, callback) => {
      let answerA = this.$refs.answerA.value;
      let answerB = this.$refs.answerB.value;
      let answerC = this.$refs.answerC.value;
      let answerD = this.$refs.answerD.value;
      if (answerA && answerB && answerC && answerD) {
        callback();
      } else {
        if (!answerA) {
          console.log(value);
          console.log(answerB);
          callback(new Error("请输入选项A"));
        }
        if (!answerB) {
          callback(new Error("请输入选项B"));
        }
        if (!answerC) {
          callback(new Error("请输入选项C"));
        }
        if (!answerD) {
          callback(new Error("请输入选项D"));
        }
      }
    };
    return {
      activeName: "first",
      texts: ["轻而易举", "简单", "中等", "略有挑战", "困难"],
      subjects: [],
      subjectDeleteList: [],
      choiceForm: {
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
      fillForm: {
        subjectId: "",
        content: "",
        rightAnswer: "",
        score: 1,
        level: null,
        analysis: "",
      },
      judgeForm: {
        subjectId: "",
        content: "",
        rightAnswer: "",
        score: 1,
        level: null,
        analysis: "",
      },
      subjectiveForm: {
        subjectId: "",
        content: "",
        rightAnswer: "",
        score: 1,
        level: null,
        analysis: "",
      },
      value: "",
      rules: {
        subject: [
          { required: true, message: "请输入题目学科", trigger: "blur" },
        ],
        content: [
          {
            required: true,
            message: "请输入题目内容，最多255个字符",
            trigger: "blur",
          },
        ],
        item: [
          {
            required: true,
            validator: checkAnswerItem,
            trigger: "blur",
          },
        ],
        rightAnswer: [
          { required: true, message: "必须输入正确答案", trigger: "blur" },
        ],
        score: [{ required: true, message: "必须选定分值", trigger: "blur" }],
        level: [{ required: true, message: "必须选择难度", trigger: "blur" }],
        analysis: [{ required: false }],
      },
      subjectForm: {
        subjectName: "",
      },
      subjectAddVisible: false,
      subjectDeleteVisible: false,
      innerVisible: false,
      formLabelWidth: "100px",
      checkAll: false,
      isIndeterminate: true,
    };
  },
  methods: {
    addChoiceQuestion() {
      axios({
        method: "post",
        url: "/api/choiceQuestion/add",
        data: {
          subjectId: this.choiceForm.subjectId,
          question: this.choiceForm.content,
          answerA: this.choiceForm.answerA,
          answerB: this.choiceForm.answerB,
          answerC: this.choiceForm.answerC,
          answerD: this.choiceForm.answerD,
          rightAnswer: this.choiceForm.rightAnswer,
          analysis: this.choiceForm.analysis,
          score: this.choiceForm.score,
          level: this.choiceForm.level,
        },
      }).then((res) => {
        if (res.status === 200) {
          this.$message({
            showClose: true,
            message: "添加成功",
            type: "success",
          });
          this.choiceForm = {};
        }
      });
    },

    addFillQuestion() {
      axios({
        method: "post",
        url: "/api/fillQuestion/add",
        data: {
          subjectId: this.fillForm.subjectId,
          question: this.fillForm.content,
          answer: this.fillForm.rightAnswer,
          analysis: this.fillForm.analysis,
          score: this.fillForm.score,
          level: this.fillForm.level,
        },
      }).then((res) => {
        if (res.status === 200) {
          this.$message({
            showClose: true,
            message: "添加成功",
            type: "success",
          });
          this.fillForm = {};
        }
      });
    },

    addJudgeQuestion() {
      axios({
        method: "post",
        url: "/api/judgeQuestion/add",
        data: {
          subjectId: this.judgeForm.subjectId,
          question: this.judgeForm.content,
          answer: this.judgeForm.rightAnswer,
          analysis: this.judgeForm.analysis,
          score: this.judgeForm.score,
          level: this.judgeForm.level,
        },
      }).then((res) => {
        if (res.status === 200) {
          this.$message({
            showClose: true,
            message: "添加成功",
            type: "success",
          });
          this.judgeForm = {};
        }
      });
    },

    addSubjectiveQuestion() {
      axios({
        method: "post",
        url: "/api/subjectiveQuestion/add",
        data: {
          subjectId: this.subjectiveForm.subjectId,
          question: this.subjectiveForm.content,
          answer: this.subjectiveForm.rightAnswer,
          analysis: this.subjectiveForm.analysis,
          score: this.subjectiveForm.score,
          level: this.subjectiveForm.level,
        },
      }).then((res) => {
        if (res.status === 200) {
          this.$message({
            showClose: true,
            message: "添加成功",
            type: "success",
          });
          this.subjectiveForm = {};
        }
      });
    },

    addSubject() {
      axios({
        method: "post",
        url: "/api/subject/add",
        data: {
          subjectName: this.subjectForm.subjectName,
        },
      }).then((res) => {
        if (res.status === 200) {
          console.log(res);
          if (res.data.code === "0") {
            this.$message({
              showClose: true,
              message: "添加成功",
              type: "success",
            });
            this.subjectAddVisible = false;
            this.reload();
          }
          if (res.data.code === "-1") {
            this.$message({
              showClose: true,
              message: "学科已经存在",
              type: "error",
            });
            this.subjectForm.subjectName = "";
          }
        }
      });
    },

    deleteSubject() {
      axios({
        method: "post",
        url: "/api/subject/delete",
        data: {
          deleteList: this.subjectDeleteList,
        },
      }).then((res) => {
        if (res.status === 200) {
          this.$message({
            showClose: true,
            message: "删除成功",
            type: "success",
          });
        }
        this.innerVisible = false;
        this.subjectDeleteVisible = false;
        this.reload();
      });
    },

    handleCheckAllChange(val) {
      let checked = this.subjects.map((item) => {
        return item.subjectName;
      });
      this.dval = val;
      this.subjectDeleteList = val ? checked : [];
      this.isIndeterminate = false;
    },

    handleCheckedSubjectChange(value) {
      let checkedCount = value.length;
      this.checkAll = checkedCount === this.subjects.length;
      this.isIndeterminate =
        checkedCount > 0 && checkedCount < this.subjects.length;
    },
  },
};
</script>

<style lang="scss" scoped>
.el-main {
  overflow: hidden;
}
.add-question-wraper {
  height: auto;
}
.question-container {
  margin: 10px;
  height: 900px;
  text-align: left;
}
.question-form {
  display: flex;
  flex-direction: column;
}
.item {
  width: 300px;
  margin: 0 15px 15px 0;
}
.el-form-item__content {
  display: flex;
  flex-direction: row;
  justify-content: center;
}
.el-rate {
  line-height: 2.5;
}

.card {
  position: fixed;
  top: 150px;
  right: 30px;
  width: 250px;
}
.delete-list-wrapper {
  width: 100%;
  display: flex;
  justify-content: center;
}
.deleteList {
  display: flex;
  flex-direction: column;
  justify-content: center;
}
.deleteList .el-checkbox-group {
  padding: 20px;
  width: 200px;
  height: 200px;
  border: 1px solid #efefef;
  display: flex;
  flex-direction: column;
  overflow: auto;
}
.deleteList .el-checkbox:last-child {
  margin-bottom: 0px;
}
.deleteList .el-checkbox {
  margin-left: 0px !important;
  margin-right: 0px !important;
  margin-bottom: 10px;
}
.checkAll {
  position: relative;
  bottom: 20px;
}
</style>