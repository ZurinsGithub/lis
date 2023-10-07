<template>
  <div class="m-5">
    <h2>概览</h2>
    <el-divider />

    <div class="flex justify-center" v-loading="loading">
      <p class="mr-2 mt-1">站点名称</p>
      <el-input
        v-model="st.state.user.userName"
        style="width: 200px"
      ></el-input>
      <p class="mr-2 ml-4 mt-1">余量</p>
      <el-input v-model="station.margin" style="width: 200px"></el-input>
      <el-button style="margin-left: 50px" type="primary" @click="modifystation"
        >修改</el-button
      >
    </div>

    <el-divider />
    <div
      id="infochart"
      class="mx-auto"
      style="height: 530px; width: 400px"
    ></div>
    <div class="w-full text-center">
      <el-button size="large" type="success" @click="rtpush('accept', 'accept')"
        >前往接收订单页面</el-button
      >
      <el-button size="large" type="success" @click="rtpush('sort', 'sort')"
        >前往订单分拣页面</el-button
      >
    </div>

    <el-divider />

    <div
      id="transchart"
      class="mx-auto"
      style="height: 430px; width: 600px"
    ></div>
    <div class="w-full text-center">
      <el-button
        size="large"
        type="success"
        @click="rtpush('transport', 'transport')"
        >前往运输管理页面</el-button
      >
    </div>

    <el-divider />

    <div class=" text-center">
      <p class="text-lg font-bold">运输数据</p>
      <div :class="{ hidden: hidetrans }" v-loading="loading">
        <el-form>
          <div class=" flex justify-center flex-wrap">
          <el-form-item v-for="item in station.transittimes" :key="item">
            <div class=" border-2 p-2 rounded-lg m-3" style="min-width:400px">
              <div class="flex flex-row"><p>运输方式：{{item.mean}}；运输方向：{{item.out}}</p></div>
            <div class="flex flex-row w-full my-2">
              <p>运输价格：</p>
              <el-input v-model="item.price" style="width: 100px"></el-input>
            </div>
            <div class="flex flex-row w-full my-2">
              <p>预计到下一站点的运输时间（小时）：</p>
              <el-input style="width: 100px" v-model="item.time"></el-input>
            </div>
            </div>
          </el-form-item>
          </div>
          <el-form-item>
            <div class=" w-full text-center">
             <el-button type="primary" @click="modifystation">修改</el-button>
            </div>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>














<script setup>
import { inject, onMounted, reactive, ref } from "vue";
import { useStore } from "vuex";
import axios from "axios";
import { useRouter } from "vue-router";
import { ElMessage } from "element-plus";

let st = useStore();

let station = ref({ margin: 0,transittimes:[] });

onMounted(() => {
  getOrders();
  getStation();
});

function getStation() {
  // console.log(st.state.user);
  axios
    .post("/api/testproject/station/get", {
      id: st.state.user.info.stationid,
    })
    .then((resp) => {
      console.log(resp.data);
      station.value = resp.data;
      inittranstime();
    })
    .catch((err) => {
      console.error(err);
    });
}

function HandleChartsData() {
  // 处理数据
  let stationorders = st.state.stationorders;
  // 订单与日期
  let arr = [];
  let names = [];
  for (let i = 0; i < stationorders.length; i++) {
    if (arr[stationorders[i].ordernumber.slice(0, 6) + "-"] == undefined) {
      arr[stationorders[i].ordernumber.slice(0, 6) + "-"] = {
        name: DateStr(stationorders[i].ordernumber.slice(0, 6)),
        value: 1,
      };
      names.push(stationorders[i].ordernumber.slice(0, 6) + "-");
    } else {
      arr[stationorders[i].ordernumber.slice(0, 6) + "-"].value++;
    }
  }
  // console.log(arr);
  let data = [];
  for (let i = 0; i < names.length; i++) {
    data.push(arr[names[i]]);
  }
  // console.log(data);
  chartdata = data;
  chartsInit();

  // 交通工具
  axios
    .post("/api/testproject/station/gettransport", {
      stationid: st.state.user.info.stationid,
    })
    .then((resp) => {
      console.log(resp.data);
      let data = resp.data;
      let r0 = [];
      let r1 = [];
      let r2 = [];
      for (let i = 0; i < data.length; i++) {
        r0.push({
          name: data[i].name,
          value: data[i].limitedweight,
        });
        if (r2[data[i].mean] == undefined) {
          r2[data[i].mean] = data[i].limitedweight;
        } else {
          r2[data[i].mean] += data[i].limitedweight;
        }
        r1 = [
          {
            name: "公路",
            value: r2["road"],
          },
          {
            name: "航空",
            value: r2["air"],
          },
          {
            name: "冷链",
            value: r2["cold"],
          },
        ];
        // setdata
        transchartdata1 = r1;
        transchartdata2 = r0;
        setchart2();
      }
    })
    .catch((err) => {
      console.error(err);
    });
}

function DateStr(str) {
  // console.log(str);
  let yy = str.slice(0, 2);
  let mm = str.slice(2, 4);
  let dd = str.slice(4, 6);
  return "20" + yy + "年" + mm + "月" + dd + "日";
}

function getOrders() {
  // 获取所有订单
  axios
    .post("/api/testproject/apis/getstationorders", {
      key: st.state.user.userKey,
    })
    .then((resp) => {
      st.commit("setStationOrders", resp.data);
      HandleChartsData();
    })
    .catch((err) => {
      console.error(err);
    });
}

var chartdata = [];
var transchartdata1 = [];
var transchartdata2 = [];
let echarts = inject("$echarts");
function chartsInit() {
  var chart = echarts.init(document.getElementById("infochart"));
  let option = {
    title: {
      text: "订单与日期统计图",
      left: "center",
    },
    tooltip: {
      trigger: "item",
    },
    legend: {
      top: "5%",
      left: "center",
    },
    series: [
      {
        name: "订单数量统计",
        type: "pie",
        radius: ["40%", "70%"],
        avoidLabelOverlap: false,
        label: {
          show: false,
          position: "center",
        },
        emphasis: {
          label: {
            show: true,
            fontSize: "40",
            fontWeight: "bold",
          },
        },
        labelLine: {
          show: false,
        },
        data: chartdata,
      },
    ],
  };
  chart.setOption(option);
}

function setchart2() {
  // 交通图
  var chart1 = echarts.init(document.getElementById("transchart"));
  console.log(transchartdata1);
  let option1 = {
    title: {
      text: "运输能力统计图",
    },
    tooltip: {
      trigger: "item",
      formatter: "{a} <br/>{b}: {c} ({d}%)",
    },
    legend: {
      data: ["公路", "航空", "冷链"],
    },
    series: [
      {
        name: "",
        type: "pie",
        selectedMode: "single",
        radius: [0, "30%"],
        label: {
          position: "inner",
          fontSize: 14,
        },
        labelLine: {
          show: false,
        },
        data: transchartdata1,
      },
      {
        name: "最大运输重量（kg）",
        type: "pie",
        radius: ["45%", "60%"],
        labelLine: {
          length: 30,
        },
        label: {
          formatter: "{a|{a}}{abg|}\n{hr|}\n  {b|{b}：}{c}  {per|{d}%}  ",
          backgroundColor: "#F6F8FC",
          borderColor: "#8C8D8E",
          borderWidth: 1,
          borderRadius: 4,
          rich: {
            a: {
              color: "#6E7079",
              lineHeight: 22,
              align: "center",
            },
            hr: {
              borderColor: "#8C8D8E",
              width: "100%",
              borderWidth: 1,
              height: 0,
            },
            b: {
              color: "#4C5058",
              fontSize: 14,
              fontWeight: "bold",
              lineHeight: 33,
            },
            per: {
              color: "#fff",
              backgroundColor: "#4C5058",
              padding: [3, 4],
              borderRadius: 4,
            },
          },
        },
        data: transchartdata2,
      },
    ],
  };
  chart1.setOption(option1);
}

let rt = useRouter();
let emit = defineEmits(["resetPage1"]);
function rtpush(name, activename) {
  rt.push({ name: name });
  emit("resetPage1", activename);
}

let loading = ref(false);
function modifystation() {
  loading.value = true;
  axios
    .post("/api/testproject/station/modify", {
      id: station.value.id,
      key: st.state.user.userKey,
      margin: station.value.margin,
      name: station.value.name,
      transittimes:station.value.transittimes
    })
    .then((resp) => {
      ElMessage("修改成功");
      loading.value = false;
    })
    .catch((err) => {
      console.error(err);
    });
}

let hidetrans = ref(true);
function inittranstime() {
  if (station.type != 1) {
    hidetrans.value=false;
  }
}
</script>
