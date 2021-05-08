<template>
  <div class="main-wrapper">
    <div class="main">
      <div class="block">
        <span class="block-title" style="background: #409eff">通知公告</span>
        <div class="block-container">
          <el-collapse accordion>
            <el-collapse-item title="考试条约" class="notice-title">
              <div class="notice-container">
                与现实生活一致：与现实生活的流程、逻辑保持一致，遵循用户习惯的语言和概念；在界面中一致：所有的元素和结构需保持一致，比如：设计样式、图标和文本、元素的位置等。
              </div>
            </el-collapse-item>
            <el-collapse-item title="考生须知" class="notice-title">
              <div class="notice-container">
                与现实生活一致：与现实生活的流程、逻辑保持一致，遵循用户习惯的语言和概念；在界面中一致：所有的元素和结构需保持一致，比如：设计样式、图标和文本、元素的位置等。
              </div>
            </el-collapse-item>
            <el-collapse-item title="考试通知" class="notice-title">
              <div class="notice-container">
                与现实生活一致：与现实生活的流程、逻辑保持一致，遵循用户习惯的语言和概念；在界面中一致：所有的元素和结构需保持一致，比如：设计样式、图标和文本、元素的位置等。
              </div>
            </el-collapse-item>
          </el-collapse>
        </div>
      </div>

      <div class="block">
        <span class="block-title" style="background: rgb(230, 162, 60)"
          >我的考试</span
        >
        <div
          class="block-container"
          style="display: flex; flex-wrap: wrap; justify-content: space-between"
        >
          <div>
            <selectPaper></selectPaper>
          </div>
          <div class="paper">
            <a href="#" class="urlLink">
              <el-card class="box-card" shadow="hover">
                <ul>
                  <li>考试名称</li>
                  <li>学科:</li>
                  <li>试卷类型：</li>
                  <li>题目数量：</li>
                  <li>试卷总分：</li>
                  <li>建议时长：</li>
                  <li>发布时间：</li>
                </ul>
              </el-card>
            </a>
            <a href="#" class="urlLink">
              <el-card class="box-card" shadow="hover">
                <ul>
                  <li>考试名称</li>
                  <li>学科:</li>
                  <li>试卷类型：</li>
                  <li>题目数量：</li>
                  <li>试卷总分：</li>
                  <li>建议时长：</li>
                  <li>发布时间：</li>
                </ul>
              </el-card>
            </a>

            <a href="#" class="urlLink">
              <el-card class="box-card" shadow="hover">
                <ul>
                  <li>考试名称</li>
                  <li>学科:</li>
                  <li>试卷类型：</li>
                  <li>题目数量：</li>
                  <li>试卷总分：</li>
                  <li>建议时长：</li>
                  <li>发布时间：</li>
                </ul>
              </el-card>
            </a>

            <div class="pageination">
              <pageination></pageination>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import pageination from "../exam/Pagination";
import selectPaper from "../exam/Select";

export default {
  components: {
    pageination,
    selectPaper,
  },
  name: "StudentIndex",
  data() {
    return {
      message: "hello",
    };
  },
  mounted: function () {
    if (location.href.indexOf("") == -1) {
      location.href = location.href + "";
      location.reload();
    }
  },
  scrollBehavior(to, from, savedPosition) {
    if (to.hash) {
      return {
        selector: to.hash,
        behavior: "smooth",
      };
    }
  },
};
</script>

<style lang="scss" scoped>
.main-wrapper {
  display: flex;
  justify-content: center;
}
.main-wrapper .main {
  width: 80%;
  border-radius: 4px;
  height: auto;
  background: #fff;
}
.block {
  padding: 40px 40px;
  display: flex;
  justify-content: flex-start;
  flex-direction: column;
  width: 100%;
  margin-top: 50px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}
.block-title {
  font-size: 16px;
  font-weight: 400;
  padding: 10px 25px;
  color: #fff;
  border-radius: 4px;
  width: 100px;
  position: relative;
  left: -100px;
}
.block-container {
  padding: 30px 0px 10px 0px;
}
.el-collapse-item__header {
  border: 0px;
  font-size: 14px;
  font-weight: 400;
}
.el-collapse-item .notice-container {
  text-align: left;
  padding: 0 20px 0 20px;
}
.box-card {
  margin-top: 30px;
  width: 450px;
  height: auto;
  display: flex;
}
.box-card span {
  font-size: 16px;
  font-weight: 300;
}
.box-card ul {
  list-style-type: none;
}
.box-card li:first-child {
  font-size: 16px;
  font-weight: 400;
  color: #303133;
}
.box-card li {
  font-size: 14px;
  color: #606266;
  padding: 5px 0 5px 0;
  text-align: left;
}
.urlLink {
  text-decoration: none;
}
.paper {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  width: 100%;
}
.pageination {
  width: 100%;
  margin-top: 20px;
}
</style>