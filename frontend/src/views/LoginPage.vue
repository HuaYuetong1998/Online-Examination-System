<template>
  <div v-bind:id="message">
    <div class="bg"></div>
    <el-row class="container">
        <el-col :lg="8" :md="10" :xs="16" :span="10">
            <div class="head">
                <i class="el-icon-collection icon-font"></i><span class="title">软工综合测试系统</span>
            </div>
        </el-col>
    </el-row>
    <login></login>
  </div>
</template>

<script>
import login from '../components/login/LoginForm'

export default {
    data() {
        return {
            message:'login'
        }
    },
    components: {
        login
    }
}
</script>

<style lang="scss" scoped>
html,body {
  margin:0;
  padding:0;
}
.bg {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  overflow-y: auto;
  height: 100%;
  background: url('../assets/bg.png')center top / cover no-repeat;
  background-color: #b6bccdd1 !important;
}
.container{
  display: flex;
  justify-content: center;
  align-items: center;
}
.head{
    font-size:40px;
    margin-top:50px;
    color: #284df1bd;
    display: flex;
    justify-content: center;
    font-weight: bold;
}
.icon-font{
    font-size:50px;
    margin-right:5px;
}
</style>