<template>
  <div class="paper-correct-wrapper">
    <div></div>
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
</style>