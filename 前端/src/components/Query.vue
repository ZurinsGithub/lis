<template>
  <div class="w-full">
    <button
      @click="btnclick"
      class="
        absolute
        rounded-2xl
        text-white
        border-2 border-white
        hover:text-black hover:bg-white
        m-10
        px-10
        py-2
        text-lg
      "
      :class="{ hidden: !showbtn }"
    >
      首页
    </button>
    <div class="h-full flex flex-row bgimg">
      <div
        :class="{ animate1: isdone, gr1: isdone }"
        class="margin-full mx-auto width-limited grid grid-rows-2 grid-cols-6"
      >
        <p
          :class="{ hidden: isdone }"
          class="w-full text-2xl row-start-1 col-span-6 text-white"
        >
          请输入订单编号查询
        </p>
        <el-input
          size="large"
          class="c1 cs5 rs2 mya"
          style="width: 100%"
          v-model="querynumber"
        />
        <el-button
          size="large"
          class="c6 cs1 rs2 mya"
          style="margin-left: 10px; width: 100%"
          type="primary"
          @click="query"
          >查询</el-button
        >
      </div>
    </div>
  </div>
  <div
    :class="{ hidden: !isdone }"
    class="width-limited m-7"
    v-loading="loading"
  >
    <div border="1px" class="">
      <h2 class="font-bold">订单信息</h2>
      <div class="m-4 border-2 rounded-xl p-2 flex flex-row flex-wrap">
        <div
          v-for="item in order.list"
          :key="item"
          class="flex flex-row my-6 text-lg mx-24"
        >
          <p class="text-center font-bold text-xl" style="width: 150px">
            {{ item.label }}
          </p>
          <div style="width: 3px" class="bg-slate-400"></div>
          <div class="ml-4" style="min-width:200px">
            <p>{{ item.value }}</p>
          </div>
        </div>

        <el-divider />
        
        <div class=" w-full text-center mb-3">
            <button @click="querysource" class=" border-b-2 text-gray-500 hover:text-gray-400">查看溯源信息</button>
            <button @click="querytransport" class=" ml-8 border-b-2 text-gray-500 hover:text-gray-400">查看运输工具信息</button>
            <button @click="feedback" class=" ml-8 border-b-2 text-red-700 hover:text-red-400">订单反馈</button>
        </div>
      </div>


      <div class="text-center" >
        <el-button :class="{hidden:showbtn1}" size="large" type="danger" @click="btnclick">返回首页</el-button>
      </div>
    </div>
  </div>

    <el-dialog v-model=" showsource">
    <SourceInfo :sourceinfo="sourceinfo" />
  </el-dialog>

  <el-dialog v-model="showfeedbackdialog" width="800px">
    <Feedback :ordernumber="querynumber" @feedbackemit="()=>{showfeedbackdialog=false}" />
  </el-dialog>
</template>





<style scoped>
.mya {
  margin-top: auto;
  margin-right: auto;
}
.bgimg {
  background-image: url("../assets/img/login/transbg.jpg");
}
.rs1 {
  @apply row-start-1;
}
.cs1 {
  @apply col-span-1;
}
.c6 {
  @apply col-start-6;
}
.c1 {
  @apply col-start-1;
}
.cs5 {
  @apply col-span-5;
}
.margin-full {
  margin-top: 45vh;
  margin-bottom: 45vh;
  height: 10vh;
}
.width-limited {
  min-width: 500px;
}

@keyframes animation-animate1 {
  from {
    margin-top: 45vh;
    margin-bottom: 45vh;
  }
  to {
    margin-top: 2vh;
    margin-bottom: 2vh;
  }
}
.animate1 {
  animation: animation-animate1 0.7s;
  animation-fill-mode: forwards;
}

@keyframes animation-animate2 {
  from {
    font-size: 1.5rem /* 24px */;
    line-height: 2rem /* 32px */;
  }
  to {
    font-size: 0.875rem /* 14px */;
    line-height: 1.25rem /* 20px */;
  }
}
.animate2 {
  animation: animation-animate2 0.7s;
  animation-fill-mode: forwards;
}
.gr1 {
  @apply grid-rows-1;
}
.mya {
  @apply my-auto;
}
</style>







<script setup>
import axios from "axios";
import { ElMessage } from "element-plus";
import { onMounted, ref } from "vue";
import { useRouter } from "vue-router";
import { useStore } from "vuex";
import example from "../../JSONExample.js";
import Feedback from "./Feedback.vue";

let props = defineProps({showbtn:false})
let rt = useRouter();
let showbtn = ref(!props.showbtn);
let showbtn1 = ref(!props.showbtn);

function btnclick() {
  rt.push({
    path: "/login",
  });
}

let isdone = ref(false);
let querynumber = ref("220428033054123");
let order = ref({ list: [] });

function handleOrder(data) {
    // console.log(showbtn.value);
  let o = {
    list: [],
    sourceinfo: data.sourceinfo,
    transportinfo: data.transportinfo,
  };
  // o.list.push(getListItem("",data.));
  o.list.push(getListItem("产商名", data.productor.name));
  o.list.push(getListItem("订单编号", data.number));
  o.list.push(getListItem("订单状态", data.state.text));
  o.list.push(getListItem("总价", data.productinfo.price));
  o.list.push(getListItem("重量", data.productinfo.weight));
  o.list.push(getListItem("起始时间", data.starttime));
  o.list.push(getListItem("产商联系方式", data.productor.phone));
  o.list.push(getListItem("收货地址", data.receiveraddress.address));
  o.list.push(getListItem("发出地址", data.startaddress.address));
  o.list.push(getListItem("运输方式", getMeanStr(data.productinfo.mean)));
  o.list.push(getListItem("预计运输时间（小时）", data.productinfo.time));
  o.list.push(getListItem("时间限制（小时）", data.productinfo.limitedtime));
  o.list.push(
    getListItem("是否配送", data.productinfo.distribution == 1 ? "是" : "否")
  );

  order.value = o;
}

function getMeanStr(mean) {
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

function getListItem(label, value) {
  return {
    label: label,
    value: value,
  };
}

function query() {
  if (querynumber.value != "") {
    loading.value = true;
    axios
      .post("/api/testproject/apis/queryorder", {
        number: querynumber.value,
      })
      .then((resp) => {
        // Animate
        handleOrder(resp.data);
        isdone.value = true;
        showbtn.value = false;
        loading.value = false;
      })
      .catch((err) => {
        ElMessage("未找到订单");
        console.error(err);
      });
  } else {
    ElMessage("请输入订单编号");
  }
}

let loading = ref(false);

let showsource = ref(false);
let sourceinfo = ref(example.order.sourceinfo);
function querysource(){
    console.log(order.value)
  sourceinfo.value = order.value.sourceinfo;      
//   console.log(sourceinfo.value);
  showsource.value=true;
}

let st = useStore();
function querytransport(){
  getTransportInfo('query',order.value.transportinfo.id);
}
let getTransportInfo = (pagename,transid)=>{
    axios.post('/api/testproject/apis/gettransinfo',{
        id:transid
    })
    .then((resp)=>{
        st.commit('setTransinfo',resp.data);
        rt.push({
            name:'transportinfo',
            params:{name:pagename,transid}
        });
    })
}

let showfeedbackdialog = ref(false);
function feedback(){
  console.log(st.state.user);
  if(st.state.user.id>0)
    showfeedbackdialog.value = true;
  else{
    ElMessage('请先登陆');
    rt.push('/login');
  }

}
</script>
