<script setup>
import { onMounted } from "@vue/runtime-core";
import axios from "axios";
import { useStore } from "vuex";
import { ref } from "vue";

let st = useStore();
let user = ref(st.state.user);
let msgs = ref(st.state.msgs);

onMounted(() => {
  ShowMsgs(st.state.msgs);
});

function ShowMsgs(data) {
  // 组织消息显示
  let rarr = [];
  let urarr = [];
  for (let i = 0; i < data.length; i++) {
    if (data[i].state == "unread") {
      data[i].unread = true;
      urarr.push(data[i]);
    } else {
      data[i].unread = false;
      rarr.push(data[i]);
    }
  }
  st.commit("setMsgs", urarr.concat(rarr));
  msgs.value = st.state.msgs;
}

function change(activeNames) {
  if (activeNames < 9999 && activeNames >= 0) {
    if (msgs.value[activeNames].unread == true) {
      console.log(msgs.value[activeNames].unread);
      msgs.value[activeNames].unread = false;
      if (msgs.value[activeNames].state == "unread") {
        msgs.value[activeNames].state = "read";
      }
      saveMsgs();
    }
  }
}

function saveMsgs() {
  st.commit("setMsgs", msgs.value);
  console.log(msgs.value);
  // 提交到后端
  axios
    .post("/api/testproject/msgs/modify", msgs.value)
    .then(function (resp) {
      console.log(resp.data);
    })
    .catch(function (err) {
      console.log(err);
    });
}
</script>






<style scoped>
.displaynone {
  @apply bg-red-600;
}
</style>






<template>
  <div>
    <h2 class="m-8">消息列表</h2>
    <el-collapse accordion @change="change">
      <el-collapse-item
        v-for="(item, index) in msgs"
        :key="index"
        :name="index"
      >
        <template #title>
          <span
            :class="{ displaynone: item.unread }"
            class="w-2 h-2 bg-transparent rounded-xl mx-8"
          ></span>
          <div class="w-all">
            <p class="float-left">{{ item.text.title }}</p>
            <p class="float-right mr-4">{{ item.sendername }}</p>
          </div>
        </template>
        <div class="my-4 mx-20">
          <p>
            {{ item.text.content }}
          </p>
        </div>
      </el-collapse-item>
    </el-collapse>
  </div>
</template>