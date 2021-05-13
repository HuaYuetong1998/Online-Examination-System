<template>
  <div class="header-wrapper">
    <div class="header">
      <div class="container">
        <h1>
          <i class="el-icon-collection"></i>
          <a href="">软工综合测试系统</a>
        </h1>
        <el-menu
          class="nav"
          :default-active="tabName"
          mode="horizontal"
          @select="handleSelect"
          active-text-color="#409EFF"
        >
          <el-menu-item class="nav-item" index="index"
            ><span>首页</span></el-menu-item
          >
          <el-menu-item class="nav-item" index="exam">我的考试</el-menu-item>
          <el-menu-item class="nav-item" index="info">个人信息</el-menu-item>
          <el-menu-item class="nav-item" index="score">我的成绩</el-menu-item>
          <div style="padding: 0" class="gap">
            <div class="nav-gap"></div>
          </div>
          <el-col class="nav-item lang-item">
            <el-dropdown @command="handleCommand" trigger="click">
              <p class="el-dropdown-link">
                <i class="el-icon-s-custom"></i>
                {{ realname }}
                <i class="el-icon-arrow-down el-icon--right"></i>
              </p>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item command="info">个人信息</el-dropdown-item>
                  <el-dropdown-item command="modifyPwd"
                    >修改密码</el-dropdown-item
                  >
                  <el-dropdown-item command="logout">退出登录</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </el-col>
        </el-menu>
      </div>

      <!-- 修改密码模块 -->
      <el-dialog
        title="修改密码"
        :visible.sync="dialogFormVisible"
        :append-to-body="true"
        :close-on-click-modal="false"
      >
        <modifyPwd></modifyPwd>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="dialogFormVisible = false"
            >确 定</el-button
          >
          <el-button @click="dialogFormVisible = false">取 消</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import modifyPwd from "../exam/ModifyPwd";
export default {
  components: {
    modifyPwd,
  },
  name: "headBar",
  data() {
    return {
      realname: this.$cookies.get("crealname"),
      dialogFormVisible: false,
      tabName: this.$route.name,
    };
  },
  methods: {
    handleCommand(command) {
      switch (command) {
        case "info":
          this.$router.push({
            name: "info",
          });
          break;
        case "modifyPwd":
          this.dialogFormVisible = true;
          break;
        case "logout":
          this.$router.push("/login");
          break;
      }
    },
    handleSelect(tabName) {
      this.$store.commit("changeTab", tabName);
      this.$router.push({ path: "/student/" + tabName });
    },
  },
};
</script>

<style lang="scss" scoped>
.header-wrapper {
  height: 80px;
}
.header {
  height: 80px;
  background-color: #fff;
  color: #fff;
  top: 0;
  left: 0;
  width: 100%;
  line-height: 80px;
  z-index: 100;
  position: relative;
}
.header .container {
  height: 100%;
  box-sizing: border-box;
  border-bottom: 1px solid #dcdfe6;
}
.container {
  padding: 0 40px;
}
.header h1 {
  margin: 0;
  float: left;
  font-size: 32px;
  font-weight: 550;
}
.header i {
  font-size: 40px;
  margin-right: 5px;
  color: #409eff;
}
a,
a:visited {
  text-decoration: none;
  color: #409eff;
}
.header .nav {
  float: right;
  height: 100%;
  line-height: 80px;
  background: transparent;
  padding: 0;
  margin: 0;
}
.header .nav-item:first-child {
  width: 60px;
}
.header .nav-item {
  height: 79px;
  line-height: 80px;
  width: 80px;
  font-size: 16px;
  margin-right: 20px;
}
.header .nav-item:last-child {
  width: 150px;
  margin-right: 0;
}
.header .el-dropdown {
  width: 150px;
}

.header .nav-item:hover {
  color: #409eff;
}
.header .nav-gap {
  padding: 0 8px;
  height: 80px;
  position: relative;
}
.gap {
  float: left;
}
.header .nav-gap::before {
  content: "";
  position: absolute;
  top: calc(50% - 8px);
  width: 1px;
  height: 20px;
  background: #ebebeb;
}
.header .nav-item.lang-item {
  height: 100%;
}
.el-menu-item {
  padding: 0;
}
.header .nav-item .el-dropdown-link {
  cursor: pointer;
  color: #409eff;
}
.header .nav-item .el-icon-arrow-down {
  font-size: 12px;
}
.header .nav-item .el-icon-s-custom {
  font-size: 16px;
}
.dialog-footer {
  text-align: center;
}
.dialog-footer .el-button {
  width: 30%;
}
</style>