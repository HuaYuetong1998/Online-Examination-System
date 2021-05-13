<template>
  <div>
    <headBar></headBar>
    <div style="height: 650px; overflow: auto">
      <info></info>
      <footerBar></footerBar>
      <backTop></backTop>
    </div>
  </div>
</template>

<script>
import headBar from "../components/exam/Header";
import info from "../components/exam/Info";
import footerBar from "../components/exam/Footer";
import backTop from "../components/exam/BackTop";

export default {
  components: {
    headBar,
    info,
    footerBar,
    backTop,
  },
};
</script>

<style lang="scss" scoped>
</style>