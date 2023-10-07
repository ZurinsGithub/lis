
<template>
  <div>
    <!-- 导航栏 -->
    <Navigation />
    <div class="cxb m-10">
      <!-- 页面模板 -->
      <h2>欢迎您，{{ user.userName }}</h2>
      <el-divider />
      <div class="w-full text-center">
        <div id="chart1" style="width: 400px; height: 400px"></div>
        <!-- <div id="chart2" style="width: 400px; height: 400px"></div> -->
      </div>
      <el-divider />
      <p class="text-lg mb-4">功能列表</p>
      <div class="flex flex-row flex-wrap">
        <button
          class="text-lg font-bold px-8 py-2 rounded-lg bgxlb m-3 width-limited"
          v-for="(item, index) in btns"
          :key="index"
          @click="topage(index)"
        >
          {{ item.label }}
        </button>
      </div>
    </div>
  </div>

  <el-dialog v-model="btns[0].value">
    <!-- 溯源信息管理dialog -->
    <div class="flex-row flex">
      <el-input
        placeholder="请输入订单编号以查询"
        v-model="inordernumber"
      ></el-input>
      <el-button type="primary" @click="queryorder" style="margin-left: 20px"
        >查询</el-button
      >
    </div>
    <div class="mt-4">
      <el-container class="w-full1 h-35screen">
        <el-scrollbar class="w-full1">
          <div
            @click="recbtnclick(index)"
            v-for="(item, index) in records"
            :key="index"
            class="n2 bgxlb"
          >
            {{ item.text }}
          </div>
        </el-scrollbar>
      </el-container>
    </div>

    <div class="text-center mt-2" :class="{ hidden: !recarea }">
      <el-button
        type="primary"
        @click="
          () => {
            showaddrecord = true;
          }
        "
        >添加记录</el-button
      >
    </div>
  </el-dialog>

  <el-dialog v-model="showaddrecord">
    <el-form :model="recordform">
      <el-form-item label="内容">
        <el-input type="textarea" :rows="4" v-model="recordform.text" />
      </el-form-item>
      <el-form-item label="JSON字符串">
        <el-input type="textarea" :rows="4" v-model="recordform.json" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="addrecord">添加</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>

  <el-dialog v-model="showtext1">
    <div>{{ text0 }}</div>
  </el-dialog>

  <el-dialog v-model="btns[2].value">
    <div>
      <el-form :model="msgform" label-width="100px">
        <el-form-item label="receiverid">
          <el-input-number v-model="msgform.receiverid" />
        </el-form-item>
        <el-form-item label="sendername">
          <el-input v-model="msgform.sendername" />
        </el-form-item>
        <el-form-item label="title">
          <el-input v-model="msgform.text.title" />
        </el-form-item>
        <el-form-item label="text">
          <el-input v-model="msgform.text.content" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="sendmsg" :disabled="sendbtn"
            >发送</el-button
          >
        </el-form-item>
      </el-form>
    </div>
  </el-dialog>
</template>















<style scoped>
.width-limited {
  min-width: 100px;
}
.w-full1 {
  width: 100%;
}
.n2 {
  @apply my-4 rounded-lg w-full p-6;
}
</style>















<script setup>
import axios from "axios";
import { ElMessage } from "element-plus";
import { onMounted, ref, inject } from "vue";
import { useRouter } from "vue-router";
import { useStore } from "vuex";
import Navigation from "./Navigation.vue";
import example from "../../JSONExample";

let showaddrecord = ref(false);

let st = useStore();
let rt = useRouter();
onMounted(() => {
  // console.log(st.state.user);
  if (checkuser()) {
    chart1init();
  } else {
    rt.push("/login");
  }
});

function checkuser() {
  return true;
}

let user = ref(st.state.user);

let btns = ref([
  {
    label: "溯源信息管理",
    type: "dialog",
    value: false,
  },
  {
    label: "用户管理",
    type: "page",
    value: {
      name: "manageuser",
      params: {},
    },
  },
  {
    label: "发送通知",
    type: "dialog",
    value: false,
  },
  {
    label: "发布公告",
    type: "dialog",
    value: false,
  },
  {
    label: "客户信息管理",
    type: "dialog",
    value: false,
  },
  {
    label: "特殊订单管理",
    type: "dialog",
    value: false,
  },
]);
function topage(index) {
  switch (btns.value[index].type) {
    case "dialog": {
      btns.value[index].value = true;
      break;
    }
    case "page": {
      rt.push({
        name: btns.value[index].value.name,
        params: btns.value[index].value.params,
      });
      break;
    }
  }
}

let order = ref();
let inordernumber = ref("220415072808224");
let recarea = ref(false);
let records = ref([]);
function queryorder() {
  axios
    .post("/api/testproject/apis/queryorder", {
      number: inordernumber.value,
    })
    .then((resp) => {
      records.value = resp.data.sourceinfo.records;
      order.value = resp.data;
      recarea.value = true;
    })
    .catch((err) => {
      console.error(err);
    });
}

let recordform = ref({
  text: "产商补充溯源信息，溯源码：320000300801，查询网址：http://qsst.gdagri.gov.cn/nytsy/portal/index.jsp",
  json: '{"type":"normal","mark":""}',
});
function addrecord() {
  axios
    .post("/api/testproject/addrecord", {
      number: order.value.number,
      text: recordform.value.text,
      json: recordform.value.json,
    })
    .then((resp) => {
      console.log(resp.data);
      showaddrecord.value = false;
      ElMessage("添加成功");
    })
    .catch((err) => {
      console.error(err);
    });
}

function recbtnclick(index) {
  switch (records.value[index].content.type) {
    case "transportinfo": {
      records.value[index].content.params.name = "manage";
      rt.push({
        name: records.value[index].content.pagename,
        params: records.value[index].content.params,
      });
      // console.log(records.value[index].content)
      break;
    }
    default: {
      show1(records.value[index].text);
    }
  }
}

let showtext1 = ref(false);
let text0 = ref("");
function show1(text1) {
  showtext1.value = true;
  text0.value = text1;
}

// msg module
let msgform = ref(example.msgs[0]);
let sendbtn = ref(false);
function sendmsg() {
  sendbtn.value = true;
  let params = {
    sendername: msgform.value.sendername,
    receiverid: msgform.value.receiverid,
    text: {
      title: msgform.value.text.title,
      content: msgform.value.text.title,
    },
    state: "unread",
    remark: {},
  };
  axios
    .post("/api/testproject/msgs/send", params)
    .then((resp) => {
      btns.value[2].value = false;
      sendbtn.value = false;
      ElMessage("发送成功");
    })
    .catch((err) => {
      console.error(err);
      sendbtn.value = false;
      ElMessage("发送失败");
    });
}

let echarts = inject("$echarts");
function chart1init() {
  let chart = echarts.init(document.getElementById("chart1"));
  let option = {
    title: {
      text: "订单统计图",
    },
    tooltip: {
      trigger: "axis",
    },
    legend: {
      data: ["当日订单总数"],
    },
    grid: {
      left: "3%",
      right: "4%",
      bottom: "3%",
      containLabel: true,
    },
    toolbox: {
      feature: {
        saveAsImage: {},
      },
    },
    xAxis: {
      type: "category",
      boundaryGap: false,
      data: [
        "4月28日",
        "4月29日",
        "4月30日",
        "5月1日",
        "5月2日",
        "5月3日",
        "5月4日",
      ],
    },
    yAxis: {
      type: "value",
    },
    series: [
      {
        name: "当日订单总数",
        type: "line",
        stack: "Total",
        data: [120, 132, 210, 200, 250, 230, 210],
      },
    ],
  };
  chart.setOption(option);
}
</script>
