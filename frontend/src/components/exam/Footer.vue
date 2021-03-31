<template>
  <div class="footerWrapper">
    <div class="footer">
      <div class="container">
        <h4>Author：袁其凯</h4>
        <div class="msg">
          本考试系统由本人独立完成，仅用于学术交流，不得用作其他用途！
        </div>

        <div class="msg">Copyright © 2021 袁其凯 个人所有</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "footerBar",
  data() {
    return {};
  },
};
</script>

<style lang="scss">
.footerWrapper {
  margin-top: 30px;
  width: 100%;
  height: auto;
  background: #f7fbfd;
  padding: 40px 150px;
  box-sizing: border-box;
}
.msg {
  margin: 10px auto;
  color: #606266;
}
</style>