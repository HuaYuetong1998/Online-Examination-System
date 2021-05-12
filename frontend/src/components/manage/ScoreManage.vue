<template>
  <div class="score-wrapper">
    <div class="score-container">
      <el-form ref="scoreForm" :model="scoreForm" class="scoreForm">
        <el-form-item label="学号：">
          <el-input v-model="scoreForm.studentId"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="searchScore">查询</el-button>
        </el-form-item>
      </el-form>

      <el-divider></el-divider>
      <el-card>
        <div slot="header" class="clearfix">
          <span>{{ studentName }}同学的成绩统计</span>
        </div>
        <el-scrollbar>
          <div class="chart">
            <div id="recentScoreChart" class="scoreChart"></div>
            <div id="pieChart" class="pieChart"></div>
            <div>
              <div style="font-weight: 600; font-size: 18px">历史成绩</div>
              <el-table :data="resultData" border>
                <el-table-column
                  prop="examId"
                  label="考试ID"
                  width="100"
                ></el-table-column>
                <el-table-column
                  prop="examTitle"
                  label="考试名称"
                ></el-table-column>
                <el-table-column
                  prop="score"
                  label="考试得分"
                ></el-table-column>
                <el-table-column
                  prop="examTime"
                  label="交卷日期"
                ></el-table-column>
              </el-table>
            </div>
          </div>
        </el-scrollbar>
      </el-card>
    </div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      scoreForm: {
        studentId: "",
      },
      point: [],
      examTitle: [],
      studentName: "",
      count: [0, 0, 0, 0, 0],
      resultData: [],
    };
  },
  created: function () {
    let studentId = this.$route.params.id;
    if (studentId != null) {
      this.scoreForm.studentId = studentId;
      this.searchScore();
    }
  },
  mounted() {
    this.drawLine();
    this.drawPie();
  },
  methods: {
    searchScore() {
      axios({
        method: "get",
        url: "/api/score/getRecently",
        params: {
          studentId: this.scoreForm.studentId,
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
          this.studentName = result.studentInfo.realName;
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
        title: { text: "近期考试情况" },
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
.score-container {
  padding: 20px;
}
.scoreChart {
  width: 800px;
  height: 400px;
  margin: 0 auto;
}
.scoreForm {
  display: flex;
  flex-direction: row;
}
.chart {
  height: 400px;
}
.el-form-item {
  display: flex;
  flex-direction: row;
  margin-right: 30px;
}
.pieChart {
  display: flex;
  margin-left: 10px;
  width: 600px;
  height: 400px;
  margin: 0 auto;
}
.el-scrollbar {
  height: 100%;
}
.el-scrollbar .el-scrollbar__wrap {
  overflow-x: auto;
}
</style>