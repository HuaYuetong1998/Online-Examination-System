<template>
  <div class="exam-wrapper">
    <el-tabs type="border-card">
      <el-tab-pane label="考试发布">
        <div class="exam-container">
          <el-form
            ref="examForm"
            :model="examForm"
            label-width="100px"
            label-position="right"
            inline="inline"
          >
            <el-form-item label="考试标题：">
              <el-input v-model="examForm.title"> </el-input>
            </el-form-item>
            <el-form-item label="选择试卷：">
              <el-select
                v-model="paperId"
                filterable
                placeholder="请选择"
                ref="selector"
                @visible-change="
                  (v) => visibleChange(v, 'selector', selectClick)
                "
              >
                <el-option
                  v-for="item in paperSelect"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="群体选择：">
              <el-switch
                v-model="studentSwitch"
                active-text="指定学生"
                inactive-text="班级发布"
              >
              </el-switch>
            </el-form-item>
            <el-form-item v-if="!studentSwitch" label="班级选择：">
              <el-select v-model="classId" filterable placeholder="请选择">
                <el-option
                  v-for="item in classSelect"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
            </el-form-item>

            <el-form-item v-else label="指定学生：">
              <el-button type="success">指定</el-button>
            </el-form-item>

            <el-form-item label="开始时间：">
              <el-date-picker
                v-model="examStartTime"
                type="datetime"
                placeholder="选择日期时间"
              >
              </el-date-picker>
            </el-form-item>

            <el-form-item label="考试时间：">
              <el-input-number
                v-model="examTime"
                :min="1"
                label="考试时间"
              ></el-input-number>
              <span>（分钟）</span>
            </el-form-item>

            <el-form-item label="防作弊：">
              <el-checkbox-group v-model="antiCheatList">
                <el-checkbox label="打乱题序"></el-checkbox>
                <el-checkbox label="切屏提醒"></el-checkbox>
                <el-checkbox label="选项乱序"></el-checkbox>
              </el-checkbox-group>
            </el-form-item>
            <el-form-item style="margin-left: 100px">
              <el-button type="primary">发布试卷</el-button>
              <el-button>重置</el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-tab-pane>
      <el-tab-pane label="考试状态管理">考试状态</el-tab-pane>
    </el-tabs>

    <el-card> </el-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      inline: false,
      examForm: {
        title: "123",
      },
      paperSelect: [
        {
          value: "1",
          label: "试卷1",
        },
      ],
      classSelect: [{ value: "1", label: "软工1班" }],
      paperId: "",
      classId: "",
      studentSwitch: true,
      examStartTime: "",
      examTime: "",
      antiCheatList: [],
    };
  },
  methods: {
    visibleChange(visible, refName, onClick) {
      if (visible) {
        const ref = this.$refs[refName];
        let popper = ref.$refs.popper;
        if (popper.$el) popper = popper.$el;
        if (
          !Array.from(popper.children).some(
            (v) => v.className === "el-cascader-menu__list"
          )
        ) {
          const el = document.createElement("ul");
          el.className = "el-cascader-menu__list";
          el.style =
            "border-top: solid 1px #E4E7ED; padding:0; color: #606266;";
          el.innerHTML = `<li class="el-cascader-node" style="height:38px;line-height: 38px;font-size:14px;">
<i class="el-icon-circle-plus-outline"></i>
<span class="el-cascader-node__label">全部试卷列表</span>
</li>`;
          popper.appendChild(el);
          el.onclick = () => {
            // 底部按钮的点击事件 点击后想触发的逻辑也可以直接写在这
            onClick && onClick();

            // 以下代码实现点击后弹层隐藏 不需要可以删掉
            if (ref.toggleDropDownVisible) {
              ref.toggleDropDownVisible(false);
            } else {
              ref.visible = false;
            }
          };
        }
      }
    },
    selectClick() {
      console.log("onCLick");
    },
  },
};
</script>

<style lang="scss" scoped>
.exam-container {
  padding: 15px;
  display: flex;
  flex-direction: column;
  height: 900px;
  text-align: left;
}
.el-form-item {
  display: flex;
}
</style>