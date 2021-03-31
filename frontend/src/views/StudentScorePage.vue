<template>
  <div>
    <headBar></headBar>
    <score></score>
    <footerBar></footerBar>
    <backTop></backTop>
  </div>
</template>

<script>
import headBar from "../components/exam/Header";
import score from "../components/exam/Score";
import footerBar from "../components/exam/Footer";
import backTop from "../components/exam/BackTop";

export default {
  components: {
    headBar,
    score,
    footerBar,
    backTop,
  },
};
</script>

<style lang="scss" scoped>
</style>