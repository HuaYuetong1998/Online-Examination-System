<template>
  <div>
    <headBar></headBar>
    <practice></practice>
    <footerBar></footerBar>
    <backTop></backTop>
  </div>
</template>

<script>
import headBar from "../components/exam/Header";
import practice from "../components/exam/Practice";
import footerBar from "../components/exam/Footer";
import backTop from "../components/exam/BackTop";

export default {
  components: {
    headBar,
    practice,
    footerBar,
    backTop,
  },
};
</script>

<style lang="scss" scoped>
</style>