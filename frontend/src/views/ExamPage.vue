<template>
  <div>
    <paperPage></paperPage>
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
  beforeRouteLeave(to, from, next) {
    this.$confirm("正在考试当中，离开将提前交卷，你确定要离开吗？", "警告", {
      confirmButtonText: "离开",
      cancelButtonText: "留下",
      type: "warning",
    })
      .then(() => {
        // TODO 交卷功能

        next();
      })
      .catch(() => {
        next(false);
      });
  },
};
</script>

<style lang="scss" scoped>
</style>