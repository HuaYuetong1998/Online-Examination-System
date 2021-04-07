<template>
  <div class="add-question-wrapper">
    <el-tabs v-model="activeName" @tab-click="handleClick" type="border-card">
      <el-tab-pane label="选择题" name="first">
        <div class="question-container">
          <el-form
            :model="questionForm"
            :rules="rules"
            ref="questionForm"
            label-width="100px"
            class="questionForm"
          >
            <el-form-item label="学科：">
              <el-select v-model="questionForm.subject" placeholder="选择学科">
                <el-option label="软件工程" value="1"></el-option>
                <el-option label="计算机网络" value="2"></el-option>
                <el-option label="数据结构" value="3"></el-option>
              </el-select>
            </el-form-item>

            <el-form-item label="题型：">
              <el-select v-model="questionForm.type" placeholder="选择题型">
                <el-option label="选择题" value="1"></el-option>
                <el-option label="填空题" value="2"></el-option>
                <el-option label="判断题" value="3"></el-option>
                <el-option label="主观题" value="4"></el-option>
              </el-select>
            </el-form-item>

            <el-form-item label="ID：">
              <el-input placeholder="输入题目ID"></el-input>
            </el-form-item>

            <el-form-item label="题干：">
              <el-input placeholder="输入题目内容"></el-input>
            </el-form-item>

            <el-form-item label-width="0">
              <el-button type="primary" icon="el-icon-search">搜索</el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-tab-pane>
      <el-tab-pane label="填空题" name="second">
        <div class="question-container">
          <el-form
            :model="questionForm"
            :rules="rules"
            ref="questionForm"
            label-width="100px"
            class="questionForm"
          >
            <el-form-item label="学科：">
              <el-select v-model="questionForm.subject" placeholder="选择学科">
                <el-option label="软件工程" value="1"></el-option>
                <el-option label="计算机网络" value="2"></el-option>
                <el-option label="数据结构" value="3"></el-option>
              </el-select>
            </el-form-item>

            <el-form-item label="题型：">
              <el-select v-model="questionForm.type" placeholder="选择题型">
                <el-option label="选择题" value="1"></el-option>
                <el-option label="填空题" value="2"></el-option>
                <el-option label="判断题" value="3"></el-option>
                <el-option label="主观题" value="4"></el-option>
              </el-select>
            </el-form-item>

            <el-form-item label="ID：">
              <el-input placeholder="输入题目ID"></el-input>
            </el-form-item>

            <el-form-item label="题干：">
              <el-input placeholder="输入题目内容"></el-input>
            </el-form-item>

            <el-form-item label-width="0">
              <el-button type="primary" icon="el-icon-search">搜索</el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-tab-pane>
      <el-tab-pane label="判断题" name="third">
        <div class="question-container">
          <el-form
            :model="questionForm"
            :rules="rules"
            ref="questionForm"
            label-width="100px"
            class="questionForm"
          >
            <el-form-item label="学科：">
              <el-select v-model="questionForm.subject" placeholder="选择学科">
                <el-option label="软件工程" value="1"></el-option>
                <el-option label="计算机网络" value="2"></el-option>
                <el-option label="数据结构" value="3"></el-option>
              </el-select>
            </el-form-item>

            <el-form-item label="题型：">
              <el-select v-model="questionForm.type" placeholder="选择题型">
                <el-option label="选择题" value="1"></el-option>
                <el-option label="填空题" value="2"></el-option>
                <el-option label="判断题" value="3"></el-option>
                <el-option label="主观题" value="4"></el-option>
              </el-select>
            </el-form-item>

            <el-form-item label="ID：">
              <el-input placeholder="输入题目ID"></el-input>
            </el-form-item>

            <el-form-item label="题干：">
              <el-input placeholder="输入题目内容"></el-input>
            </el-form-item>

            <el-form-item label-width="0">
              <el-button type="primary" icon="el-icon-search">搜索</el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-tab-pane>
      <el-tab-pane label="主观题" name="fourth">
        <div class="question-container">
          <el-form
            :model="questionForm"
            :rules="rules"
            ref="questionForm"
            label-width="100px"
            class="questionForm"
          >
            <el-form-item label="学科：">
              <el-select v-model="questionForm.subject" placeholder="选择学科">
                <el-option label="软件工程" value="1"></el-option>
                <el-option label="计算机网络" value="2"></el-option>
                <el-option label="数据结构" value="3"></el-option>
              </el-select>
            </el-form-item>

            <el-form-item label="题型：">
              <el-select v-model="questionForm.type" placeholder="选择题型">
                <el-option label="选择题" value="1"></el-option>
                <el-option label="填空题" value="2"></el-option>
                <el-option label="判断题" value="3"></el-option>
                <el-option label="主观题" value="4"></el-option>
              </el-select>
            </el-form-item>

            <el-form-item label="ID：">
              <el-input placeholder="输入题目ID"></el-input>
            </el-form-item>

            <el-form-item label="题干：">
              <el-input placeholder="输入题目内容"></el-input>
            </el-form-item>

            <el-form-item label-width="0">
              <el-button type="primary" icon="el-icon-search">搜索</el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
export default {
  data() {
    return {
      activeName: "first",
      questionForm: {
        subject: "",
        type: "",
      },
      value: "",
      rules: {
        subject: [{ required: true, trigger: "blur" }],
      },
    };
  },
  methods: {
    handleClick(tab, event) {
      console.log(tab, event);
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
  height: 100px;
  text-align: left;
}
.questionForm {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
}
.el-form-item {
  margin-right: 55px;
}
.el-form-item::last-child {
  margin-right: 5px;
}
.el-input {
  width: 210px;
}
</style>