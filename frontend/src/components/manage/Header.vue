<template>
  <div class="header-wrapper">
    <div class="header">
      <div class="container">
        <h1>
          <i class="el-icon-collection"></i>
          <a href="">软工测试系统后台管理</a>
        </h1>

        <div class="top-nav">
          <el-menu
            :default-active="activeIndex"
            class="el-menu-demo"
            mode="horizontal"
            @select="handleSelect"
            background-color="#545c64"
            text-color="#fff"
            active-text-color="#ffd04b"
          >
            <el-menu-item index="index" class="tag">首页</el-menu-item>
            <el-menu-item index="message" class="tag">我的信息</el-menu-item>
            <div style="padding: 0" class="gap">
              <div class="nav-gap"></div>
            </div>
            <el-dropdown
              @command="handleCommand"
              trigger="click"
              class="nav-item"
            >
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
          </el-menu>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      activeIndex: "index",
      realname: this.$cookies.get("crealname"),
      tabName: this.$route.name,
    };
  },
  methods: {
    handleCommand(command) {
      switch (command) {
        case "info":
          this.$message("info");
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
      this.$router.push({ path: "/manage/" + tabName });
    },
  },
};
</script>

<style lang="scss" scoped>
.header-wrapper {
  height: 80px;
  border-bottom: 1px solid orange;
}
.header {
  height: 81px;
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
  background: #545c64;
  display: flex;
  justify-content: space-between;
  flex-direction: row;
  padding: 0px 40px;
}
.header h1 {
  margin: 0;
  font-size: 32px;
  font-weight: 550;
}
.header h1 > a,
a:visited {
  color: #ffd04b;
  text-decoration: none;
}
.header h1 > i {
  color: #ffd04b;
}
.top-nav .tag {
  height: 79px;
  line-height: 80px;
}
.el-menu.el-menu--horizontal {
  border: 0;
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
.el-dropdown {
  margin-left: 10px;
}
.el-dropdown-link {
  color: #fff;
  cursor: pointer;
}
.el-dropdown-link > i {
  color: #ffd04b;
}
</style>