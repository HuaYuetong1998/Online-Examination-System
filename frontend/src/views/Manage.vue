<template>
  <div class="manage">
    <headerBar></headerBar>
    <mainPart></mainPart>
  </div>
</template>

<script>
import headerBar from "../components/manage/Header";
import mainPart from "../components/manage/Main";
export default {
  components: {
    headerBar,
    mainPart,
  },
};
</script>

<style lang="scss" scoped>
html {
  overflow-x: hidden;
  overflow-y: hidden;
}
</style>