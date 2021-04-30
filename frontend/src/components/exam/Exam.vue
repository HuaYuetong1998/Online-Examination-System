<template>
  <div class="box-wrapper">
    <div class="box-container">
      <div class="box">
        <span class="box-title" style="background: #409eff">进行中</span>
        <div class="main">
          <a
            class="urlLink"
            @click="examPrompt(item.examId)"
            v-for="item in examingInfo"
            :key="item.examId"
          >
            <el-card class="box-card" shadow="hover">
              <ul>
                <li>考试名称：{{ item.examTitle }}</li>
                <li>题目数量：{{ item.questionsNum }}</li>
                <li>试卷总分：{{ item.totalScore }}</li>
                <li>考试时长：{{ item.timeLimit }} 分钟</li>
                <li>开始时间：{{ item.startTime }}</li>
                <li>截止时间：{{ item.overTime }}</li>
              </ul>
            </el-card>
          </a>
        </div>

        <div class="pageination">
          <el-pagination
            background
            @current-change="handleOnGoingCurrentChange"
            :current-page="onGoingCurrentPage"
            :page-size="onGoingPageSize"
            layout="total, prev, pager, next, jumper"
            :total="onGoingPageTotal"
          ></el-pagination>
        </div>
      </div>

      <div class="box">
        <span class="box-title" style="background: #f56c6c">已结束</span>
        <div class="main">
          <a
            href="#"
            class="urlLink"
            v-for="item in overedExamInfo"
            :key="item.examId"
          >
            <el-card class="box-card" shadow="hover">
              <ul>
                <li>考试名称：{{ item.examTitle }}</li>
                <li>题目数量：{{ item.questionsNum }}</li>
                <li>试卷总分：{{ item.totalScore }}</li>
                <li>考试时长：{{ item.timeLimit }} 分钟</li>
                <li>开始时间：{{ item.startTime }}</li>
                <li>截止时间：{{ item.overTime }}</li>
              </ul>
            </el-card>
          </a>
        </div>

        <div class="pageination">
          <el-pagination
            background
            @current-change="handleOveredCurrentChange"
            :current-page="overedCurrentPage"
            :page-size="overedPageSize"
            layout="total, prev, pager, next, jumper"
            :total="overedPageTotal"
          ></el-pagination>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  created: function () {
    this.initExamOnGoing();
    this.initExamOvered();
  },
  data() {
    return {
      examingInfo: [],
      overedExamInfo: [],
      onGoingCurrentPage: 1,
      onGoingPageSize: 6,
      overedCurrentPage: 1,
      overedPageSize: 6,
      onGoingPageTotal: null,
      overedPageTotal: null,
    };
  },
  methods: {
    initExamOnGoing() {
      let studentId = this.$cookies.get("studentId");
      if (studentId != null) {
        axios({
          method: "post",
          url: "/api/front/exam/getOnGoing",
          data: {
            studentId: studentId,
            onGoingCurrentPage: this.onGoingCurrentPage,
            onGoingPageSize: this.onGoingPageSize,
          },
        }).then((res) => {
          this.examingInfo = [];
          if (res.status === 200) {
            let result = res.data.data;
            console.log(result);
            let onGoing = result.examOnGoingData;
            this.onGoingPageTotal = result.examOnGoingTotal;
            for (let i = 0; i < onGoing.length; i++) {
              this.examingInfo.push({
                examId: onGoing[i].examId,
                examTitle: onGoing[i].examTitle,
                timeLimit: onGoing[i].timeLimit,
                startTime: onGoing[i].startTime,
                questionsNum: onGoing[i].questionsNum,
                totalScore: onGoing[i].totalScore,
                overTime: onGoing[i].overTime,
              });
            }
          }
        });
      }
    },
    initExamOvered() {
      let studentId = this.$cookies.get("studentId");
      if (studentId != null) {
        axios({
          method: "post",
          url: "/api/front/exam/getOvered",
          data: {
            studentId: studentId,
            overedCurrentPage: this.overedCurrentPage,
            overedPageSize: this.overedPageSize,
          },
        }).then((res) => {
          this.overedExamInfo = [];
          if (res.status === 200) {
            let result = res.data.data;
            console.log(result);
            let overed = result.examOveredData;
            this.overedPageTotal = result.examOveredTotal;

            for (let i = 0; i < overed.length; i++) {
              this.overedExamInfo.push({
                examId: overed[i].examId,
                examTitle: overed[i].examTitle,
                timeLimit: overed[i].timeLimit,
                startTime: overed[i].startTime,
                questionsNum: overed[i].questionsNum,
                totalScore: overed[i].totalScore,
                overTime: overed[i].overTime,
              });
            }
          }
        });
      }
    },
    handleOnGoingCurrentChange(val) {
      this.onGoingCurrentPage = val;
      this.initExamOnGoing();
    },
    handleOveredCurrentChange(val) {
      this.overedCurrentPage = val;
      this.initExamOvered();
    },

    examPrompt(examId) {
      this.$confirm("考试开始计时后无法中途暂停，是否确定开始考试？", "提醒", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        this.$message({
          type: "success",
          message: "开始考试!",
        });
        this.$router.push({
          path: "/examPage",
          query: {
            examId: examId,
          },
        });
        console.log(examId);
      });
    },
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
  width: 350px;
  margin-right: 25px;
  height: auto;
  display: flex;
}
.box-card:nth-child(3n + 3) {
  margin-right: 0;
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
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  text-decoration: none;
  width: 350px;
  cursor: pointer;
}
.pageination {
  margin-top: 20px;
}
</style>