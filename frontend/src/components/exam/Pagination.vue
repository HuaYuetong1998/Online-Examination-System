<template>
  <div>
    <el-pagination
      @current-change="handleCurrentChange"
      :current-page="currentPage"
      layout="total, prev, pager, next, jumper"
      :total="100"
    >
    </el-pagination>
  </div>
</template>
<script>
export default {
  name: "pageination",
  methods: {
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
    },
  },
  data() {
    return {
      currentPage: 4,
    };
  },
};
</script>