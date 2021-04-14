<template>
  <el-row :span="24" class="container">
    <el-col class="menu-col" :span="6">
      <el-menu
        default-active="1-4-1"
        class="el-menu-vertical-demo sideBar"
        @open="handleOpen"
        @close="handleClose"
        :collapse="isCollapse"
        background-color="#545c64"
        text-color="#fff"
        active-text-color="#ffd04b"
        :unique-opened="true"
        router
      >
        <!-- 展开收起菜单功能 -->
        <!-- <el-menu-item @click="isCollapse = !isCollapse">
          <i
            :class="{
              'el-icon-arrow-left': !isCollapse,
              'el-icon-arrow-right': isCollapse,
            }"
          ></i>
          <span slot="title">展开/收起</span>
        </el-menu-item> -->
        <div class="menu-header">
          <span>当前时间</span>
          <div style="margin-top: 15px">
            <i class="el-icon-time" style="margin-right: 10px"></i>{{ time }}
          </div>
        </div>
        <el-divider><i class="el-icon-notebook-2"></i></el-divider>
        <el-submenu class="item" index="1">
          <template slot="title">
            <i class="el-icon-receiving"></i>
            <span>题库管理</span>
          </template>
          <el-menu-item-group>
            <el-menu-item index="/manage/addQuestions">录入题目</el-menu-item>
            <el-menu-item index="/manage/questionsList">查看题库</el-menu-item>
          </el-menu-item-group>
        </el-submenu>
        <el-submenu class="item" index="2">
          <template slot="title">
            <i class="el-icon-document"></i>
            <span>试卷管理</span>
          </template>
          <el-menu-item-group>
            <el-menu-item index="/manage/paper">添加试卷</el-menu-item>
            <el-menu-item index="2">发布试卷</el-menu-item>
          </el-menu-item-group>
        </el-submenu>
        <el-submenu class="item" index="3">
          <template slot="title">
            <i class="el-icon-user-solid"></i>
            <span>学生管理</span>
          </template>
          <el-menu-item-group>
            <el-menu-item index="/manage/studentManage"
              >添加学生信息</el-menu-item
            >
            <el-menu-item index="2">查看学生信息</el-menu-item>
          </el-menu-item-group>
        </el-submenu>
        <el-submenu class="item" index="4">
          <template slot="title">
            <i class="el-icon-data-line"></i>
            <span>成绩查看</span>
          </template>
          <el-menu-item-group>
            <el-menu-item index="/manage/score">查看成绩</el-menu-item>
            <el-menu-item index="2">查看题库</el-menu-item>
          </el-menu-item-group>
        </el-submenu>

        <!-- <el-menu-item index="/teacher/score">
          <i class="el-icon-data-line"></i>
          <span slot="title">成绩查看</span>
        </el-menu-item> -->
      </el-menu>
    </el-col>

    <el-col class="main-part">
      <router-view></router-view>
    </el-col>
  </el-row>
</template>

<script>
export default {
  data() {
    return {
      isCollapse: false,
      time: "",
    };
  },
  methods: {
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    },
    currentTime() {
      setInterval(this.getTime, 500);
    },
    getTime() {
      let that = this;
      let yy = new Date().getFullYear();
      let mm = new Date().getMonth() + 1;
      let dd = new Date().getDate();
      let hh = new Date().getHours();
      let mf =
        new Date().getMinutes() < 10
          ? "0" + new Date().getMinutes()
          : new Date().getMinutes();
      let ss =
        new Date().getSeconds() < 10
          ? "0" + new Date().getSeconds()
          : new Date().getSeconds();
      that.time = yy + "年" + mm + "月" + dd + "日 " + hh + ":" + mf + ":" + ss;
    },
  },
  mounted() {
    this.currentTime();
  },
  beforeDestroy: function () {
    if (this.getTime) {
      clearInterval(this.getTime);
    }
  },
};
</script>

<style lang="scss" scoped>
.container {
  display: flex;
  justify-content: flex-start;
  flex-direction: row;
}
.sideBar {
  z-index: 100;
}
.menu-header {
  padding-top: 20px;
  border-top: 1px solid orange;
  height: 80px;
  color: #fff;
}
.el-divider__text {
  background-color: #909399;
}
.el-menu-vertical-demo {
  min-height: 100%;
}
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 100%;
  min-height: 900px;
}
.main-part {
  max-height: 900px;
  overflow: auto;
}
.item {
  text-align: left;
  margin: 0 20px !important;
}
</style>