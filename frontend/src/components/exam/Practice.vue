<template>
  <div class="box-wrapper">
    <div class="box-container">
      <div class="box">
        <span class="box-title" style="background: #409eff">练习卷</span>
        <div class="main"></div>
      </div>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style lang="scss">
.box-wrapper {
  display: flex;
  justify-content: center;
}
.box-container {
  width: 80%;
  border-radius: 4px;
  height: auto;
  background: #fff;
}
.box {
  display: flex;
  justify-content: flex-start;
  flex-direction: column;
  width: 100%;
  margin-top: 50px;
  padding: 40px 40px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}
.box-title {
  font-size: 16px;
  font-weight: 400;
  padding: 10px 25px;
  color: #fff;
  border-radius: 4px;
  width: 100px;
  position: relative;
  left: -100px;
}
</style>