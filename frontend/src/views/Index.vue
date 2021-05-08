<template>
  <div>
    <headBar></headBar>
    <div style="height: 650px; overflow: auto">
      <index></index>
      <footerBar></footerBar>
    </div>
    <backTop></backTop>
  </div>
</template>

<script>
import headBar from "../components/exam/Header";
import index from "../components/exam/Index";
import footerBar from "../components/exam/Footer";
import backTop from "../components/exam/BackTop";

export default {
  components: {
    headBar,
    index,
    footerBar,
    backTop,
  },
};
</script>

<style lang="scss" scoped>
</style>