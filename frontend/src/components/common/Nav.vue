<template>
  <div id="navbar">
      <el-row :span="10">
          <el-col>{{ message }},欢迎您成功登录!</el-col>
      </el-row>
  </div>
</template>

<script>
export default {
    name:'navbar',
    data() {
        return {
            message:'user'
        }
    }
}
</script>

<style lang="sass" scoped>

</style>