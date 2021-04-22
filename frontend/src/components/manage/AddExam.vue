<template>
  <div class="exam">
    <p>addExam</p>
  </div>
</template>

<script>
export default {
  methods: {
    removeDuplicate(obj) {
      var uniques = [];
      var stringify = {};
      for (var i = 0; i < obj.length; i++) {
        var keys = Object.keys(obj[i]);
        keys.sort(function (a, b) {
          return Number(a) - Number(b);
        });
        var str = "";
        for (var j = 0; j < keys.length; j++) {
          str += JSON.stringify(keys[j]);
          str += JSON.stringify(obj[i][keys[j]]);
        }
        if (!Object.prototype.hasOwnProperty.call(stringify, str)) {
          uniques.push(obj[i]);
          stringify[str] = true;
        }
      }
      return uniques;
    },
  },
  created: function () {
    let arr1 = [
      { id: 1, name: "123" },
      { id: 1, name: "123" },
    ];
  },
};
</script>

<style>
</style>