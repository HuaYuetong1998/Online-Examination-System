<template>
  <div oncopy="return false">
      <button>click me</button>
      <p>
          复制不了吧？
      </p>
  </div>
</template>

<script>
window.onfocus = function() {
    document.title = "亲，欢迎回来"
}
window.onblur = function () {
    document.title = "请你不要作弊"
}
export default {
    data() {
        return {

        }
    },
    methods:{
        ban() {
            
        }
    }
}
</script>

<style>

</style>