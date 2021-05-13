<template>
  <div>
    <headBar></headBar>
    <div style="height: 650px; overflow: auto">
      <score></score>
      <footerBar></footerBar>
    </div>
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