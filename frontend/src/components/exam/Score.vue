<template>
  <div class="box-wrapper">
    <div class="box-container">
      <div class="box">
        <span class="box-title" style="background: #409eff">近期成绩</span>
        <div class="main">
          <div id="recentScoreChart" class="recentScore"></div>
        </div>
      </div>

      <div class="box">
        <span class="box-title" style="background: #409eff">综合统计</span>
        <div class="main">
          <div id="pieChart" class="pieChart"></div>
        </div>
      </div>

      <div class="box">
        <span class="box-title" style="background: #409eff">历史成绩</span>
        <div class="main">
          <el-table :data="resultData" border style="margin-top: 20px">
            <el-table-column
              prop="examId"
              label="考试ID"
              width="100"
            ></el-table-column>
            <el-table-column
              prop="examTitle"
              label="考试名称"
            ></el-table-column>
            <el-table-column prop="score" label="考试得分"></el-table-column>
            <el-table-column prop="examTime" label="交卷日期"></el-table-column>
          </el-table>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      studentId: this.$cookies.get("studentId"),
      point: [],
      examTitle: [],
      count: [0, 0, 0, 0, 0],
      resultData: [],
    };
  },
  mounted() {
    this.initScore();
  },
  methods: {
    initScore() {
      axios({
        method: "get",
        url: "/api/score/getRecently",
        params: {
          studentId: this.studentId,
        },
      }).then((res) => {
        this.resultData = [];
        if (res.status === 200) {
          let result = res.data.data;
          let point = result.point;
          let examTitle = result.examTitle;
          let score = result.score;
          let historyScore = [];
          console.log(result);
          this.point = point;
          this.examTitle = examTitle;
          this.count = this.countTimes(point);
          this.drawLine();
          this.drawPie();
          for (let i = 0; i < score.length; i++) {
            historyScore.push({
              examId: score[i].examId,
              examTitle: examTitle[i],
              score: score[i].score,
              examTime: score[i].examTime,
            });
          }
          this.resultData = historyScore;
        }
      });
    },
    countTimes(array) {
      let count = [0, 0, 0, 0, 0];
      for (let i = 0; i < array.length; i++) {
        switch (array[i] / 10) {
          case 10:
          case 9:
            count[0]++;
            break;
          case 8:
            count[1]++;
            break;
          case 7:
            count[2]++;
            break;
          case 6:
            count[3]++;
            break;
          default:
            count[4]++;
            break;
        }
      }
      return count;
    },
    drawLine() {
      let lineChart = this.$echarts.init(
        document.getElementById("recentScoreChart")
      );
      // 绘制图表
      lineChart.setOption({
        title: { text: "近5次考试情况" },
        tooltip: {},
        xAxis: {
          name: "考试",
          type: "category",
          data: [
            this.examTitle[0],
            this.examTitle[1],
            this.examTitle[2],
            this.examTitle[3],
            this.examTitle[4],
          ],
          axisLine: {
            symbol: ["none", "arrow"],
            symbolOffset: [0, 12],
          },
        },
        yAxis: {
          name: "分数",
          type: "value",
          max: 100,
          min: 0,
          axisLine: {
            show: true,
            symbol: ["none", "arrow"],
            symbolOffset: [0, 12],
          },
        },
        series: [
          {
            name: "分数",
            type: "line",
            data: [
              this.point[0],
              this.point[1],
              this.point[2],
              this.point[3],
              this.point[4],
            ],

            markLine: {
              data: [{ type: "average", name: "平均分" }],
            },
            markPoint: {
              data: [
                { type: "max", name: "最高分" },
                { type: "min", name: "最低分" },
              ],
            },
          },
        ],
      });
    },

    drawPie() {
      let pieChart = this.$echarts.init(document.getElementById("pieChart"));
      pieChart.setOption({
        title: {
          text: "考试统计",
          left: "center",
        },
        tooltip: {
          trigger: "item",
        },
        legend: {
          orient: "vertical",
          left: "left",
        },
        series: [
          {
            name: "次数",
            type: "pie",
            radius: "50%",
            data: [
              { value: this.count[0], name: "90~100分" },
              { value: this.count[1], name: "80~90分" },
              { value: this.count[2], name: "70~80分" },
              { value: this.count[3], name: "60~70分" },
              { value: this.count[4], name: "60分以下" },
            ],
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: "rgba(0, 0, 0, 0.5)",
              },
            },
          },
        ],
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
  padding: 40px 40px;
  display: flex;
  justify-content: flex-start;
  flex-direction: column;
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

.recentScore {
  width: 800px;
  height: 400px;
  margin: 0 auto;
}

.pieChart {
  width: 800px;
  height: 400px;
  margin: 0 auto;
}
</style>