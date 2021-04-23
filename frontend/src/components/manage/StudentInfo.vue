<template>
  <div>AddStudent {{ role }}</div>
</template>

<script>
export default {
  created: function () {},
  data() {
    return {
      role: this.$cookies.get("role"),
    };
  },
};
</script>

<style lang="scss" scoped>
</style>