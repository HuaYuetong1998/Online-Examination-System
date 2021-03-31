<template>
  <div>
    <headBar></headBar>
    <exam></exam>
    <footerBar></footerBar>
    <backTop></backTop>
  </div>
</template>

<script>
import headBar from "../components/exam/Header";
import exam from "../components/exam/Exam";
import footerBar from "../components/exam/Footer";
import backTop from "../components/exam/BackTop";

export default {
  components: {
    headBar,
    exam,
    footerBar,
    backTop,
  },
};
</script>

<style lang="scss" scoped>
</style>