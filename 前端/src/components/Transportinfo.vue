<template>
  <div
    class="
      lg:h-screen
      bg
      lg:flex lg:flex-row lg:flex-wrap
      sm:py-40
      lg:py-40
       overflow-hidden
    "
  >
    <div class="lg:h-full sm:w-full lg:w-5/12 lg:border-r-2">
      <div class="w-full lg:h-screen lg:grid lg:grid-cols-1 lg:grid-rows-6">
        <svg
          t="1651115018561"
          viewBox="0 0 1024 1024"
          version="1.1"
          xmlns="http://www.w3.org/2000/svg"
          p-id="2076"
          width="200"
          height="200"
          fill="#fff"
          class="mx-auto lg:row-start-2 lg:row-span-1"
          :class="{ running: true, hidden: !isroad }"
        >
          <path
            d="M957.6 572.8l-120.8-181.6c-3.2-4.8-8-7.2-13.6-7.2H704V224c0-17.6-14.4-32-32-32H96c-17.6 0-32 14.4-32 32v448c0 17.6 14.4 32 32 32h32c0 70.4 57.6 128 128 128s128-57.6 128-128h256c0 70.4 57.6 128 128 128s128-57.6 128-128h32c17.6 0 32-14.4 32-32V581.6c0-3.2-0.8-6.4-2.4-8.8zM256 768c-35.2 0-64-28.8-64-64s28.8-64 64-64 64 28.8 64 64-28.8 64-64 64z m384-128H366.4c-22.4-38.4-63.2-64-110.4-64s-88.8 25.6-110.4 64H128V256h512v384z m128 128c-35.2 0-64-28.8-64-64s28.8-64 64-64 64 28.8 64 64-28.8 64-64 64z m128-128h-17.6c-22.4-38.4-63.2-64-110.4-64-23.2 0-44.8 6.4-64 17.6V448h95.2c5.6 0 10.4 2.4 12.8 7.2l80.8 116.8c1.6 2.4 3.2 5.6 3.2 8.8V640z"
            p-id="2077"
          ></path>
        </svg>

        <svg
          t="1651139639313"
          viewBox="0 0 1024 1024"
          version="1.1"
          xmlns="http://www.w3.org/2000/svg"
          p-id="2287"
          width="160"
          height="160"
          fill="#fff"
          class="mx-auto lg:row-start-2 lg:row-span-1"
          :class="{ running: true, hidden: !iscold }"
        >
          <path
            d="M514.133333 409.6c-17.066667 0-32-14.933333-32-32V74.666667c0-17.066667 14.933333-32 32-32s32 14.933333 32 32v305.066666c0 17.066667-14.933333 29.866667-32 29.866667z"
            p-id="2288"
          ></path>
          <path
            d="M512 283.733333l-106.666667-106.666666c-12.8-12.8-12.8-32 0-44.8 12.8-12.8 32-12.8 44.8 0L512 194.133333l64-64c12.8-12.8 34.133333-10.666667 44.8 2.133334s10.666667 32 0 42.666666L512 283.733333zM420.266667 450.133333c-8.533333 0-17.066667-4.266667-23.466667-8.533333L181.333333 224c-12.8-12.8-12.8-32 0-44.8 12.8-12.8 32-12.8 44.8 0l217.6 217.6c12.8 12.8 12.8 32 0 44.8-6.4 4.266667-14.933333 8.533333-23.466666 8.533333z"
            p-id="2289"
          ></path>
          <path
            d="M352 349.866667H198.4c-17.066667 0-32-14.933333-32-32s14.933333-32 32-32h87.466667V196.266667c0-17.066667 14.933333-32 32-32s32 14.933333 32 32v153.6zM375.466667 541.866667H74.666667c-17.066667 0-32-14.933333-32-32s14.933333-32 32-32h300.8c17.066667 0 32 14.933333 32 32s-14.933333 32-32 32z"
            p-id="2290"
          ></path>
          <path
            d="M153.6 629.333333c-8.533333 0-17.066667-4.266667-23.466667-8.533333-12.8-12.8-12.8-32 0-44.8l64-64-64-64c-12.8-12.8-12.8-32 0-44.8 12.8-12.8 32-12.8 44.8 0l108.8 108.8-106.666666 106.666667c-6.4 6.4-14.933333 10.666667-23.466667 10.666666zM200.533333 853.333333c-8.533333 0-17.066667-4.266667-23.466666-8.533333-12.8-12.8-12.8-32 0-44.8l219.733333-219.733333c12.8-12.8 32-12.8 44.8 0 12.8 12.8 12.8 32 0 44.8L224 842.666667c-6.4 6.4-14.933333 10.666667-23.466667 10.666666z"
            p-id="2291"
          ></path>
          <path
            d="M320 857.6c-17.066667 0-32-14.933333-32-32v-87.466667H196.266667c-17.066667 0-32-14.933333-32-32s14.933333-32 32-32h153.6v151.466667c0 17.066667-12.8 29.866667-29.866667 32zM512 981.333333c-17.066667 0-32-14.933333-32-32v-298.666666c0-17.066667 14.933333-32 32-32s32 14.933333 32 32v298.666666c0 19.2-14.933333 32-32 32z"
            p-id="2292"
          ></path>
          <path
            d="M426.666667 902.4c-8.533333 0-17.066667-4.266667-23.466667-8.533333-12.8-12.8-12.8-32 0-44.8l108.8-108.8 106.666667 106.666666c12.8 12.8 10.666667 34.133333-2.133334 44.8s-32 10.666667-42.666666 0L512 832l-64 64c-6.4 4.266667-14.933333 6.4-21.333333 6.4zM821.333333 855.466667c-8.533333 0-17.066667-4.266667-23.466666-8.533334l-213.333334-213.333333c-12.8-12.8-10.666667-34.133333 2.133334-44.8s32-10.666667 42.666666 0l213.333334 213.333333c12.8 12.8 12.8 32 0 44.8-6.4 4.266667-12.8 8.533333-21.333334 8.533334z"
            p-id="2293"
          ></path>
          <path
            d="M704 859.733333c-17.066667 0-32-14.933333-32-32v-153.6h151.466667c17.066667 0 32 14.933333 32 32s-14.933333 32-32 32h-87.466667v89.6c0 17.066667-12.8 32-32 32zM949.333333 546.133333h-298.666666c-17.066667 0-32-14.933333-32-32s14.933333-32 32-32h298.666666c17.066667 0 32 14.933333 32 32s-12.8 32-32 32z"
            p-id="2294"
          ></path>
          <path
            d="M870.4 629.333333c-8.533333 0-17.066667-4.266667-23.466667-8.533333l-106.666666-108.8 106.666666-106.666667c12.8-12.8 34.133333-10.666667 44.8 2.133334s10.666667 32 0 42.666666L832 512l64 64c12.8 12.8 12.8 32 0 44.8-6.4 6.4-14.933333 10.666667-25.6 8.533333zM610.133333 448c-8.533333 0-17.066667-4.266667-23.466666-8.533333-10.666667-12.8-10.666667-34.133333 2.133333-46.933334l211.2-211.2c12.8-12.8 32-12.8 44.8 0 12.8 12.8 12.8 32 0 44.8l-211.2 211.2c-6.4 6.4-12.8 10.666667-23.466667 10.666667z"
            p-id="2295"
          ></path>
          <path
            d="M827.733333 352h-153.6V198.4c0-17.066667 14.933333-32 32-32s32 14.933333 32 32v87.466667h89.6c17.066667 0 32 14.933333 32 32s-14.933333 34.133333-32 34.133333zM512 663.466667c-83.2 0-151.466667-68.266667-151.466667-151.466667s68.266667-151.466667 151.466667-151.466667 151.466667 68.266667 151.466667 151.466667c0 83.2-68.266667 151.466667-151.466667 151.466667z m0-238.933334c-49.066667 0-87.466667 38.4-87.466667 87.466667s38.4 87.466667 87.466667 87.466667 87.466667-38.4 87.466667-87.466667-40.533333-87.466667-87.466667-87.466667z"
            p-id="2296"
          ></path>
        </svg>

        <svg
          t="1651139891853"
          fill="#fff"
          class="mx-auto lg:row-start-2 lg:row-span-1"
          :class="{ running: true, hidden: !isair }"
          viewBox="0 0 1024 1024"
          version="1.1"
          xmlns="http://www.w3.org/2000/svg"
          p-id="3359"
          width="170"
          height="170"
        >
          <path
            d="M710.684108 957.190385c-1.619894 0-3.248997-0.191358-4.860704-0.582261-7.05058-1.714038-12.676717-7.013741-14.806217-13.947663L574.782905 564.385091 435.985746 682.001859l1.680269 204.552769c0.044002 5.451152-2.076288 10.69969-5.898337 14.58723l-34.361599 34.976606c-4.898567 4.986571-11.995195 7.14677-18.839067 5.730515-6.845918-1.414209-12.504802-6.206352-15.027252-12.726859l-74.372871-192.261838-199.609177-85.742826c-6.227842-2.675946-10.738575-8.245801-12.059664-14.894222-1.321088-6.64842 0.716314-13.519921 5.448082-18.374486l34.223452-35.101449c4.117784-4.221138 9.865695-6.465249 15.738449-6.186909l209.368448 10.253528 117.92376-144.683217L79.288834 321.166697c-6.809079-2.164293-12.009522-7.703449-13.735839-14.635325-1.728364-6.931876 0.264013-14.263865 5.26184-19.36914l65.386196-66.780963c4.374634-4.466732 10.543124-6.696516 16.762779-6.078439l469.724151 47.295166 133.106554-133.416616c53.017494-53.638641 103.833857-62.058404 129.411395-62.058404 0.12689 0 0.252757 0 0.379647 0 23.74275 0 44.731784 7.024997 56.20407 18.49626 27.703969 27.702945 26.328645 106.726743-48.302099 182.862771-0.052189 0.054235-0.104377 0.155543-0.158612 0.207731L759.046582 401.75513l37.570687 466.417849c0.477884 5.939269-1.641383 11.800767-5.810333 16.055674l-65.425082 66.781986C721.463616 955.008696 716.145493 957.190385 710.684108 957.190385zM585.288167 507.941568c1.933025 0 3.877307 0.271176 5.780657 0.829901 6.636141 1.943258 11.852956 7.090489 13.884218 13.701047l115.239628 375.038652 34.655288-35.372625-37.58092-466.543715c-0.484024-6.02011 1.699712-11.9471 5.973038-16.21224l140.944056-140.691299c34.582633-35.310204 46.758953-66.294892 50.881854-86.074378 5.081739-24.379247-0.685615-36.917818-2.385327-38.714743-7.572466-7.455809-65.029062-19.966751-127.676891 43.456744-0.026606 0.027629-0.054235 0.056282-0.082888 0.083911L644.895753 297.589722c-4.366448 4.370541-10.466376 6.550183-16.616446 5.92699l-469.596238-47.279816-35.077913 35.826973 377.883443 120.004141c6.493901 2.064009 11.544941 7.203052 13.490246 13.732769 1.947352 6.52767 0.534166 13.594623-3.767813 18.874883L367.604332 620.871593c-4.130064 5.069459-10.41828 7.860015-16.95516 7.553023l-210.489992-10.307763-6.839779 7.014764 180.036399 77.333296c5.083785 2.183735 9.072633 6.323009 11.069103 11.482519l65.98483 170.579003 6.03853-6.145977-1.688455-205.676359c-0.050142-6.106068 2.613524-11.918447 7.272637-15.865339l169.95274-144.018068C575.747883 509.629 580.477604 507.941568 585.288167 507.941568z"
            p-id="3360"
          ></path>
        </svg>

        <h2
          class="
            lg:row-start-3 lg:pt-20 lg:row-span-1 lg:my-auto
            w-full
            text-center text-4xl
            font-bold
            text-white
          "
        >
          {{ transinfo.mean }}
        </h2>
      </div>
    </div>

    <div class="lg:h-full lg:w-7/12 text-white">
      <div
        class="
          lg:ml-14
          grid grid-rows-4
          lg:h-full lg:py-8
          sm:text-center
          lg:text-left
          sm:mt-20
          lg:mt-0
        "
      >
        <p :class="{hidden:showrecord}" class="text-4xl row-start-1">{{ transinfo.text }}</p>
        <p :class="{hidden:showrecord}" class="text-3xl row-start-2">
          温度：{{ transinfo.temperature }}℃；湿度：{{ transinfo.humidity }}%
        </p>
        <p :class="{hidden:showrecord}" class="text-xl row-start-3">更新时间：{{ transinfo.time }}</p>
        <el-container :class="{hidden1:!showrecord}" class=" row-start-1 row-span-3 lg:pr-8 mx-auto w-full">
            <el-scrollbar style=" width:100%">
              <div id="chart1" class=" my-4" style="height:300px;width:100%"></div>
              <div id="chart2" class=" my-4" style="height:300px;width:100%"></div>
            </el-scrollbar>
        </el-container>
        <div class="flex flex-col-reverse row-start-4">
          <div>
          <button
            @click="back"
            class="
              sm:mx-auto
              lg:mx-0
              border-2 border-white
              w-40
              text-lg
              py-4
              hover:bg-white hover:text-slate-500
            "
          >
            返回
          </button>
          <button
            @click="btnclick"
            class="
              sm:ml-4
              lg:mx-4
              border-2 border-white
              w-40
              text-lg
              py-4
              hover:bg-white hover:text-slate-500
            "
          >
            {{btntext}}
          </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>





<style scoped>
@keyframes animation-running {
  0% {
    opacity: 0.3;
  }
  50% {
    opacity: 1;
  }
  100% {
    opacity: 0.3;
  }
}
.running {
  animation: animation-running 3s infinite;
}
.bg{
    background-image: url('../assets/img/login/transbg.jpg');
}
.hidden1{
  display: none;
}
</style>









<script setup>
import axios from "axios";
import { onMounted, ref, inject } from "vue";
import { useRouter } from "vue-router";
import example from '../../JSONExample.js'

let showrecord = ref(true);
let rt = useRouter();
let lastpage = "";
let transid = 0;
let transinfo = ref(example.transinfo[0]);

let isair = ref(false);
let iscold = ref(false);
let isroad = ref(false);
let isrunning = ref(false);

onMounted(() => {
  // 获取上级页面
  lastpage = rt.currentRoute.value.params.name;
  transid = rt.currentRoute.value.params.transid;
  // console.log(rt.currentRoute.value.params);

  axios
    .post("/api/testproject/apis/gettransinfo", { id: transid })
    .then((resp) => {
      transinfo.value = resp.data;
      formatdata();
    })
    .catch((err) => {
      // formatdata();     // 调试
      console.error(err);
    });
});

function formatdata() {
  let time = transinfo.value.time;
  let res = time.slice(2, 4) + "月" + time.slice(4, 6) + "日 " + time.slice(7);
  transinfo.value.time = res;

  let mean = transinfo.value.mean;
  res = getMeanStr(mean) + "运输";
  transinfo.value.mean = res;
    switch (mean) {
    case "air": {
        isair.value = true;
        break;
    }
    case "road": {
        isroad.value = true;
        break;
    }
    case "cold": {
        iscold.value = true;
        break;
    }
  }
  if(transinfo.value.mark == 'moving'){
      isrunning.value = true;
  transinfo.value.temperature = transinfo.value.temperatures[transinfo.value.temperatures.length -1].value;
  transinfo.value.humidity = transinfo.value.humiditys[transinfo.value.humiditys.length -1].value;
  }
  else{
    transinfo.value.temperature = '--';
    transinfo.value.humidity = '--';
  }
  chart1init();
  chart2init();
  showrecord.value = false;       // 必须要echarts init完成后才能隐藏
}

function getMeanStr(mean) {
  // console.log(mean);
  switch (mean) {
    case "air": {
      return "空运";
    }
    case "road": {
      return "公路";
    }
    case "cold": {
      return "冷链";
    }
  }
}

function back() {
  console.log(lastpage);
  if (lastpage != "" && lastpage != undefined) rt.push(lastpage);
  else rt.push("/login");
}

let echarts = inject("$echarts");

function chart1init(){
  var chart1 = echarts.init(document.getElementById("chart1"));
  let x = [];
  let y = [];
  console.log(transinfo.value);
  for(let i=0;i<transinfo.value.temperatures.length;i++){
    x.push(transinfo.value.temperatures[i].label);
    y.push(transinfo.value.temperatures[i].value);
  }
  let option1 = {
    title:{text:'温度记录图示（单位：℃）'},
  xAxis: {
    type: 'category',
    data: x
  },
  yAxis: {
    type: 'value'
  },
  series: [
    {
      data: y,
      type: 'line'
    }
  ]
};
  chart1.setOption(option1);
}

function chart2init(){
  var chart2 = echarts.init(document.getElementById("chart2"));
  let x = [];
  let y = [];
  console.log(transinfo.value);
  for(let i=0;i<transinfo.value.humiditys.length;i++){
    x.push(transinfo.value.humiditys[i].label);
    y.push(transinfo.value.humiditys[i].value);
  }
  let option2 = {
    title:{text:'湿度记录图示（单位：%）'},
  xAxis: {
    type: 'category',
    data: x
  },
  yAxis: {
    type: 'value'
  },
  series: [
    {
      data: y,
      type: 'line'
    }
  ]
};
  chart2.setOption(option2);
}

let btntext = ref('查看数据记录')
function btnclick(){
  if(btntext.value=='查看数据记录'){
    btntext.value = '查看最近信息'
  }
  else{
    btntext.value = '查看数据记录'
  }
  showrecord.value = !showrecord.value;
}
</script>
