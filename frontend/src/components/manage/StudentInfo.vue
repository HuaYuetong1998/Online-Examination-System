<template>
  <div class="student-info-wrapper">
    <div class="info-container">
      <el-form> 123 </el-form>
    </div>
  </div>
</template>

<script>
export default {
  created: function () {},
  data() {
    return {
      isShow: false,
    };
  },
  methods: {
    authenJudge() {
      let role = this.$cookies.get("role");
      if (role === 0) {
        this.isShow = true;
      }
    },
  },
};
</script>

<style lang="scss" scoped>
</style>