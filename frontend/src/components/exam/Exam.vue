<template>
  <div class="box-wrapper">
    <div class="box-container">
      <div class="box">
        <span class="box-title" style="background: #409eff">进行中</span>
        <div class="main">
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
        </div>

        <div class="pageination">
          <pageination></pageination>
        </div>
      </div>

      <div class="box">
        <span class="box-title" style="background: #f56c6c">已结束</span>
        <div class="main">
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
        </div>

        <div class="pageination">
          <pageination></pageination>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import pageination from "../exam/Pagination";
export default {
  components: {
    pageination,
  },
};
</script>

<style lang="scss" scoped>
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
  width: 100%;
  margin-top: 50px;
  padding: 40px;
  display: flex;
  justify-content: flex-start;
  flex-direction: column;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}
.main {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  width: 100%;
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
.pageination {
  margin-top: 20px;
}
</style>