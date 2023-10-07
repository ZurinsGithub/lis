<script setup>
import axios from "axios";
import { ElMessage } from "element-plus";
import { onMounted, reactive, ref } from "vue-demi";
import { useStore } from "vuex";
import SourceInfo from './SourceInfo.vue'
import example from '../../JSONExample.js'
import { useRouter } from "vue-router";
import Feedback from './Feedback.vue';

let rt = useRouter();
let st = useStore();
let stationorders = reactive(st.state.stationorders);
let emit = defineEmits(["updatestationorders"]);

onMounted(() => {
  getOrders();
});

function getOrders() {
  // 获取所有订单
  axios
    .post("/api/testproject/apis/getstationorders", {
      key: st.state.user.userKey,
    })
    .then((resp) => {
      console.log(resp.data);
      st.commit("setStationOrders", resp.data);
      HandleOrders();
    })
    .catch((err) => {
      console.log(err);
    });
}

let orders = ref([]);
function HandleOrders() {
  // 处理未接收的订单
  console.log("fresh");
  orders.value=[];
  stationorders = st.state.stationorders;
  for (let i = 0; i < stationorders.length; i++) {
    if (stationorders[i].remark.state == "wait"||stationorders[i].remark.state == "out") {
      let o = { number: stationorders[i].ordernumber, list: [] };
      // o.list.push(getListItem("",stationorders[i].));
      o.list.push(getListItem("产商联系方式", stationorders[i].remark.phone));
      o.list.push(getListItem("产商", stationorders[i].remark.productor));
      orders.value.push(o);
    }
  }
}

function getListItem(label, value) {
  return {
    label: label,
    value: value,
  };
}

let orderMore = reactive({
  name: "",
  list: [],
  more: [],
});
let drawer = ref(false);
let loading = ref(true);
function getMore(index) {
  drawer.value = true;
  loading.value = true;
  // 获取详细信息
  axios
    .post("/api/testproject/apis/getorderbynum", {
      number: orders.value[index].number,
    })
    .then((resp) => {
      console.log(resp.data);
      orderMore.list.length = 0;
      orderMore.more.length = 0;
      let orders1 = resp.data;
      orderMore.list.push(getListItem("订单编号", orders1.number));
      orderMore.list.push(getListItem("订单状态", orders1.state.text));
      orderMore.list.push(getListItem("总价", orders1.productinfo.price));
      orderMore.list.push(getListItem("重量", orders1.productinfo.weight));
      // orderMore.list.push(getListItem("",orders1.));
      orderMore.list.push(getListItem("起始时间", orders1.starttime));
      orderMore.list.push(
        getListItem("联系方式（接收）", orders1.receiver.phone)
      );
      orderMore.list.push(
        getListItem("收货地址", orders1.receiveraddress.address)
      );
      // order.more.push(getListItem("",orders1.));
      // orderMore.more.push(getListItem("溯源信息", orders1.sourceinfo));    // c 
      orderMore.more.push(getListItem("预留电话", orders1.productor.phone));
      orderMore.more.push(
        getListItem("发出地址", orders1.startaddress.address)
      );
      orderMore.more.push(
        getListItem("运输方式", getMeanStr(orders1.productinfo.mean))
      );
      // orderMore.more.push(getListItem("运输信息", orders1.transportinfo));
      orderMore.more.push(
        getListItem("预计运输时间（小时）", orders1.productinfo.time)
      );
      orderMore.more.push(
        getListItem("时间限制（小时）", orders1.productinfo.limitedtime)
      );
      orderMore.more.push(
        getListItem("是否配送", orders1.distribution == 1 ? "是" : "否")
      );
      orderMore.sourceinfo = orders1.sourceinfo;    // c
      orderMore.transportinfo = orders1.transportinfo;    // c
    orderMore.ordernumber = orders1.number;
      loading.value = false;
    })
    .catch((err) => {
      console.log(err);
    });
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

let operator = ref();
function accept(index) {
  // console.log(index);
  if(operator.value!=""&&operator.value!=null){
  axios
    .post("/api/testproject/station/accept", {
      number: orders.value[index].number,
      id: st.state.user.info.stationid,
      text: "驿站：" + st.state.user.userName + " 已接收",
      sourcetext:"驿站：" + st.state.user.userName + " 已接收" + "操作员信息：" + operator.value,      // s
      state: "in",
    })
    .then((resp) => {
      ElMessage('接收成功');
      getOrders();
    })
    .catch((err) => console.error(err));
  }
  else{
    ElMessage('请填写操作员信息');
  }
}

let showsource = ref(false);
let sourceinfo = ref(example.order.sourceinfo);
function querysource(){
  sourceinfo.value = orderMore.sourceinfo;
  console.log(sourceinfo.value);
  showsource.value=true;
}

function querytransport(){
  // console.log(orderMore);
  getTransportInfo('accept',orderMore.transportinfo.id);
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
  if(st.state.user.id>0)
    showfeedbackdialog.value = true;
  else{
    ElMessage('请先登陆');
    rt.push('/login');
  }

}
</script>














<template>
  <div class="px-10 py-10">
    <div class="h-16">
      <h2 class="float-left">待接收</h2>
      <el-button @click="getOrders" class="float-right">刷新页面</el-button>
      <el-input style="width:300px;float:right;margin-right:10px" v-model="operator" placeholder="请填写操作员信息" />
    </div>
    <div v-for="(item, index) in orders" :key="index">
      <el-descriptions
        class="px-4 pt-4 my-3 border-2 rounded-lg"
        :title="'订单编号：' + item.number"
        :column="2"
      >
        <template #extra>
          <el-button type="success" @click="accept(index)">接收</el-button>
          <el-button type="primary" @click="getMore(index)">查看详情</el-button>
        </template>
        <el-descriptions-item v-for="item1 in item.list" :key="item1">
          <template #label>
            <div class="p-2 bgxlb">{{ item1.label }}</div>
            <div class="p-2 bgxlg">{{ item1.value }}</div>
          </template>
        </el-descriptions-item>
      </el-descriptions>
    </div>
  </div>
  <el-drawer v-model="drawer" title="订单详情" size="70%">
    <div class="rounded-2xl border-2 p-5" v-loading="loading">
      <el-descriptions :column="3">
        <el-descriptions-item v-for="item1 in orderMore.list" :key="item1">
          <template #label>
            <div class="bgxlb p-2">
              {{ item1.label }}
            </div>
            <div class="bgxlg p-2">
              {{ item1.value }}
            </div>
          </template>
        </el-descriptions-item>
        <el-descriptions-item v-for="item2 in orderMore.more" :key="item2">
          <template #label>
            <div class="bgxlb p-2">
              {{ item2.label }}
            </div>
            <div class="bgxlg p-2">
              {{ item2.value }}
            </div>
          </template>
        </el-descriptions-item>
        
        <el-descriptions-item>
          <template #label>
            <div class="bgxlb p-2">
              运输信息
            </div>
            <div class="bgxlg p-2">
              <button class=" text-green-600" @click="querytransport">点击查看</button>
            </div>
          </template>
        </el-descriptions-item>

        <el-descriptions-item>
          <template #label>
            <div class="bgxlb p-2">
              溯源信息
            </div>
            <div class="bgxlg p-2">
              <button class=" text-green-600" @click="querysource">点击查看</button>
            </div>
          </template>
        </el-descriptions-item>
      </el-descriptions>
      
      <div class=" w-full text-center"><button @click="feedback" class=" text-red-500 hover:text-red-400">反馈</button></div>
    </div>
  </el-drawer>

  <el-dialog v-model=" showsource">
    <SourceInfo :sourceinfo="sourceinfo" />
  </el-dialog>
  <el-dialog v-model="showfeedbackdialog" width="800px">
    <Feedback :ordernumber="orderMore.ordernumber" @feedbackemit="()=>{showfeedbackdialog=false}" />
  </el-dialog>
</template>
