<template>
  <div>
    <paperPage ref="paperPage"></paperPage>
    <backTop></backTop>
  </div>
</template>

<script>
import paperPage from "../components/exam/PaperPage";
import backTop from "../components/exam/BackTop";

export default {
  components: {
    paperPage,
    backTop,
  },
  created() {},
  methods: {
    clear() {
      let child = this.$refs.paperPage;
      console.log(child.timeCountDown.promiseTimer);
      clearInterval(child.timeCountDown.promiseTimer);
      localStorage.removeItem("remainSecond");
    },
  },
  beforeRouteLeave(to, from, next) {
    if (localStorage.getItem("remainSecond")) {
      this.$confirm("正在考试当中，你确定要提前交卷吗？", "警告", {
        confirmButtonText: "交卷",
        cancelButtonText: "留下",
        type: "warning",
      })
        .then(() => {
          this.$refs.paperPage.submit();
          localStorage.removeItem("remainSecond");
          localStorage.removeItem("answerSheet");
          localStorage.removeItem("hasWriten");
          this.clear();
          this.$message({
            showClose: true,
            message: "成功交卷",
            type: "success",
          });
          next();
        })
        .catch(() => {
          next(false);
        });
    }
  },
};
</script>

<style lang="scss" scoped>
</style>