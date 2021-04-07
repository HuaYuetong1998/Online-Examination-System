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
                v-model="choiceForm.subject"
                placeholder="选择学科"
                filterable
              >
                <el-option label="软件工程" value="软件工程"></el-option>
                <el-option label="计算机网络" value="计算机网络"></el-option>
                <el-option label="数据结构" value="数据结构"></el-option>
              </el-select>
            </el-form-item>

            <el-form-item label="题干：" prop="content">
              <el-input
                type="textarea"
                v-model="choiceForm.content"
                style="width: 400px; font-size: 16px"
                resize="none"
                autosize
                maxlength="255"
              ></el-input>
            </el-form-item>

            <el-form-item label="选项：" class="choice" prop="item">
              <el-input class="item" v-model="choiceForm.answerA" prop="itemA">
                <template slot="prepend">A</template>
              </el-input>
              <el-input class="item" v-model="choiceForm.answerB" prop="itemB">
                <template slot="prepend">B</template>
              </el-input>
              <br />
              <el-input class="item" v-model="choiceForm.answerC" prop="itemC">
                <template slot="prepend">C</template>
              </el-input>
              <el-input class="item" v-model="choiceForm.answerD" prop="itemD">
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
                autosize
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
                v-model="fillForm.subject"
                placeholder="选择学科"
                filterable
              >
                <el-option label="软件工程" value="软件工程"></el-option>
                <el-option label="计算机网络" value="计算机网络"></el-option>
                <el-option label="数据结构" value="数据结构"></el-option>
              </el-select>
            </el-form-item>

            <el-form-item label="题干：" prop="content">
              <el-input
                type="textarea"
                v-model="fillForm.content"
                style="width: 400px; font-size: 16px"
                resize="none"
                autosize
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
                autosize
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
                v-model="judgeForm.subject"
                placeholder="选择学科"
                filterable
              >
                <el-option label="软件工程" value="软件工程"></el-option>
                <el-option label="计算机网络" value="计算机网络"></el-option>
                <el-option label="数据结构" value="数据结构"></el-option>
              </el-select>
            </el-form-item>

            <el-form-item label="题干：" prop="content">
              <el-input
                type="textarea"
                v-model="judgeForm.content"
                style="width: 400px; font-size: 16px"
                resize="none"
                autosize
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
                autosize
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
                v-model="subjectiveForm.subject"
                placeholder="选择学科"
                filterable
              >
                <el-option label="软件工程" value="软件工程"></el-option>
                <el-option label="计算机网络" value="计算机网络"></el-option>
                <el-option label="数据结构" value="数据结构"></el-option>
              </el-select>
            </el-form-item>

            <el-form-item label="题干：" prop="content">
              <el-input
                type="textarea"
                v-model="subjectiveForm.content"
                style="width: 400px; font-size: 16px"
                resize="none"
                autosize
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
                autosize
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
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      activeName: "first",
      texts: ["轻而易举", "简单", "中等", "略有挑战", "困难"],
      choiceForm: {
        subject: "",
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
        subject: "",
        content: "",
        rightAnswer: "",
        score: 1,
        level: null,
        analysis: "",
      },
      judgeForm: {
        subject: "",
        content: "",
        rightAnswer: "",
        score: 1,
        level: null,
        analysis: "",
      },
      subjectiveForm: {
        subject: "",
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
        item: [{ required: true, message: "必须输入选项", trigger: "blur" }],
        rightAnswer: [
          { required: true, message: "必须输入正确答案", trigger: "blur" },
        ],
        score: [{ required: true, message: "必须选定分值", trigger: "blur" }],
        level: [{ required: true, message: "必须选择难度", trigger: "blur" }],
        analysis: [{ required: false }],
      },
    };
  },
  methods: {
    addChoiceQuestion() {
      axios({
        method: "post",
        url: "/api/choiceQuestion/add",
        data: {
          subject: this.choiceForm.subject,
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
          subject: this.fillForm.subject,
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
          subject: this.judgeForm.subject,
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
          subject: this.subjectiveForm.subject,
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
  },
};
</script>

<style lang="scss" scoped>
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
.preview {
  position: fixed;
  right: 50px;
  top: 150px;
  width: 320px;
  height: auto;
}
</style>