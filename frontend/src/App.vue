<template>
  <div id="app">
    <router-view
      v-wechat-title="$route.meta.title"
      v-if="isRouterAlive"
    ></router-view>
  </div>
</template>

<script>
export default {
  name: "app",
  provide() {
    return {
      reload: this.reload,
    };
  },
  data() {
    return {
      isRouterAlive: true,
    };
  },
  methods: {
    reload() {
      this.isRouterAlive = false;
      this.$nextTick(function () {
        this.isRouterAlive = true;
      });
    },
  },
};
</script>

<style>
* {
  touch-action: pan-y;
}
#app {
  font-family: Helvetica Neue, Helvetica, PingFang SC, Hiragino Sans GB,
    Microsoft YaHei, SimSun, sans-serif;
  font-weight: 400;
  -webkit-font-smoothing: antialiased;
  -webkit-tap-highlight-color: transparent;
  text-align: center;
}
body,
html,
ul,
li,
p {
  margin: 0;
  padding: 0;
}
body .el-table th.gutter {
  display: table-cell !important;
}
.el-popup-parent--hidden {
  padding-right: 0 !important;
}
</style>
