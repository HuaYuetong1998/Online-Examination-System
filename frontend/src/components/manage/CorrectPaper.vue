<template>
  <div class="paper-correct-wrapper">
    <div class="paper-correct-container">
      <div class="exam-title">
        考试名称<span
          style="
            float: right;
            font-weight: 600;
            font-size: 20px;
            color: red;
            margin-top: 20px;
          "
          >总分：100分</span
        >
      </div>
      <el-divider></el-divider>
      <el-scrollbar>
        <div class="correct-main">
          <div class="choice-part">
            <div class="choice-title" style="display: flex; margin-left: 2%">
              <i class="el-icon-star-on"></i>选择题
            </div>
            <el-card class="question-item">item</el-card>
          </div>
        </div>
      </el-scrollbar>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {};
  },
  created: function () {
    this.getInfo();
  },
  methods: {
    getInfo() {
      let studentId = this.$route.query.studentId;
      let examId = this.$route.query.examId;
      console.log(studentId + ";" + examId);
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
  height: 520px;
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
</style>