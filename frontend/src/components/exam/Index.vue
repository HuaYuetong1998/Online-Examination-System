<template>
  <div class="main-wrapper">
    <div class="main">
      <div class="block">
        <span class="block-title" style="background: #409eff">通知公告</span>
        <div class="block-container">
          <el-collapse accordion>
            <el-collapse-item title="考生须知" class="notice-title">
              <div class="notice-container">
                考生须独立完成试卷，并在规定时间内交卷，否则计时结束将自动交卷！
              </div>
            </el-collapse-item>
            <el-collapse-item title="诚信条约" class="notice-title">
              <div class="notice-container">
                考试过程中请勿作弊，一旦发现立即取消考试资格，考试成绩作废！
              </div>
            </el-collapse-item>
            <el-collapse-item title="教师通知" class="notice-title">
              <div class="notice-container">
                您有新的考试需要参加，请尽快参加考试！
              </div>
            </el-collapse-item>
          </el-collapse>
        </div>
      </div>

      <div class="block">
        <span class="block-title" style="background: rgb(230, 162, 60)"
          >考试统计</span
        >
        <div class="block-container">
          <div class="count">
            <el-card class="count-block"
              ><div>
                <i class="el-icon-document-copy" style="font-size: 40px"></i
                ><span>考试总数</span>
              </div>
              <div class="count-num">{{ totalCount }}</div>
            </el-card>
            <el-card class="count-block"
              ><div>
                <i class="el-icon-document" style="font-size: 40px"></i
                ><span>已发布考试数量</span>
              </div>
              <div class="count-num">{{ currentCount }}</div></el-card
            >
            <el-card class="count-block"
              ><div>
                <i class="el-icon-document-checked" style="font-size: 40px"></i
                ><span>已交卷</span>
              </div>
              <div class="count-num">{{ hasSubmitCount }}</div></el-card
            >
            <el-card class="count-block"
              ><div>
                <i class="el-icon-document-delete" style="font-size: 40px"></i
                ><span>未交卷</span>
              </div>
              <div class="count-num">{{ notSubmitCount }}</div></el-card
            >
          </div>

          <div id="CountChart" class="CountChart"></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "StudentIndex",
  data() {
    return {
      message: "hello",
      studentId: this.$cookies.get("studentId"),
      totalCount: 0,
      currentCount: 0,
      hasSubmitCount: 0,
      notSubmitCount: 0,
    };
  },
  created: function () {
    this.getCount();
  },
  methods: {
    getCount() {
      let studentId = this.studentId;
      axios({
        method: "get",
        url: "/api/count/getCount",
        params: {
          studentId: studentId,
        },
      }).then((res) => {
        console.log(res);
        if (res.status === 200) {
          let result = res.data.data;
          this.totalCount = result.totalCount;
          this.currentCount = result.currentCount;
          this.hasSubmitCount = result.hasSubmitCount;
          this.notSubmitCount = result.notSubmitCount;
          this.drawCategory();
          console.log(res);
        }
      });
    },
    drawCategory() {
      let countChart = this.$echarts.init(
        document.getElementById("CountChart")
      );
      countChart.setOption({
        title: { text: "考试数据统计" },
        tooltip: {},
        xAxis: {
          type: "category",
          data: ["考试总数", "已发布考试数", "已交卷数量", "未交卷数量"],
          axisLine: {
            show: true,
            symbol: ["none", "arrow"],
            symbolOffset: [0, 12],
          },
        },
        yAxis: {
          name: "场",
          type: "value",
          axisLine: {
            show: true,
            symbol: ["none", "arrow"],
            symbolOffset: [0, 12],
          },
        },
        series: [
          {
            data: [
              this.totalCount,
              this.currentCount,
              this.hasSubmitCount,
              this.notSubmitCount,
            ],
            type: "bar",
          },
        ],
      });
    },
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
.count {
  display: flex;
  flex-direction: row;
  justify-content: center;
}
.count-block {
  margin-right: 20px;
  padding: 10px;
  width: 200px;
}
.count-num {
  font-size: 30px;
  margin-top: 10px;
}
.CountChart {
  margin-top: 30px;
  margin-left: 150px;
  width: 800px;
  height: 500px;
}
</style>