<script setup>
import axios from "axios";
import { onMounted, reactive, ref } from "vue";
import { useRouter } from "vue-router";
import { useStore } from "vuex";
import { ElMessage } from "element-plus";
import SourceInfo from './SourceInfo.vue'
import example from '../../JSONExample.js'

let rt = useRouter();
const emit = defineEmits(["resetPage"]);
let st = useStore();
let loading = ref(true);

let orders = reactive([]);

onMounted(() => {
  console.log("子组件参数：");
  console.log(rt.currentRoute.value.params);
  emit("resetPage", "orders");

  getOrders();
});

function getOrders() {
  axios
    .post(
      "/api/testproject/apis/usergetorders",
      "userKey=" + st.state.user.userKey
    )
    .then(function (resp) {
      orders = resp.data;
      updateItems();
    })
    .catch(function (err) {
      console.log(err);
    });
}

let orderitems = reactive([]);
function updateItems() {
  // 处理订单列表
  orderitems.splice(0);
  for (let i = 0; i < orders.length; i++) {
    let order = {
      name: orders[i].receiver.name,
      selected: false,
      list: [],
      more: [],
    };
    order.list.push(getListItem("订单编号", orders[i].number));
    order.list.push(getListItem("订单状态", orders[i].state.text));
    order.list.push(getListItem("总价", orders[i].productinfo.price));
    order.list.push(getListItem("重量", orders[i].productinfo.weight));
    // order.list.push(getListItem("",orders[i].));
    order.list.push(getListItem("起始时间", orders[i].starttime));
    order.list.push(getListItem("联系方式（接收）", orders[i].receiver.phone));
    order.list.push(getListItem("收货地址", orders[i].receiveraddress.address));
    // order.more.push(getListItem("",orders[i].));
    // order.more.push(getListItem("溯源信息", orders[i].sourceinfo));
    order.more.push(getListItem("预留电话", orders[i].productor.phone));
    order.more.push(getListItem("发出地址", orders[i].startaddress.address));
    order.more.push(getListItem("运输方式", getMeanStr(orders[i].productinfo.mean)));
    // order.more.push(getListItem("运输信息", orders[i].transportinfo));
    order.more.push(getListItem("预计运输时间（小时）", orders[i].productinfo.time));
    order.more.push(getListItem("时间限制（小时）", orders[i].productinfo.limitedtime));
    order.more.push(
      getListItem("是否配送", orders[i].distribution == 1 ? "是" : "否")
    );
    order.sourceinfo = orders[i].sourceinfo;    // c
    order.transportinfo = orders[i].transportinfo;    // c
    order.ordernumber = orders[i].number;

    orderitems.push(order);
  }
  console.log(orderitems);
  loading.value = false;
}
function getListItem(label, value) {
  return {
    label: label,
    value: value,
  };
}

function getMeanStr(mean) {
  console.log(mean)
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

let buntch = ref(false);
let buntchtext = ref("批量操作");
function BuntchHandle() {
  buntch.value = !buntch.value;
  if (buntch.value) {
    buntchtext.value = "退出批量操作";
  } else {
    buntchtext.value = "批量操作";
  }
}

function deleteInBuntch() {
  loading.value = true;

  let b = [];
  for (let i = 0; i < orderitems.length; i++) {
    if (orderitems[i].selected) {
      b.push(orders[i].number);
    }
  }
  console.log(b);
  axios
    .post("/api/testproject/apis/deleteorders", {
      key: st.state.user.userKey,
      ordernums: b,
    })
    .then(function (resp) {
      console.log(resp);
      getOrders();
      ElMessage("删除成功");
    })
    .catch(function (err) {
      console.log(err);
    });
}
let drawer = ref(false);
let orderMore = ref();
function showmore(index) {
  drawer.value = true;
  orderMore.value = orderitems[index];
}

let showsource = ref(false);
let sourceinfo = ref(example.order.sourceinfo)
function querysource(){
  sourceinfo.value = orderMore.value.sourceinfo;
  console.log(sourceinfo.value);
  showsource.value=true;
}

function querytransport(){
  console.log(orderMore.value);
  getTransportInfo('distribution',orderMore.value.transportinfo.id);
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














<style scoped>
.checkboxwidth {
  width: 5%;
  display: initial;
}
.discriptwidth {
  width: 95%;
}
</style>






















<template>
  <div v-loading="loading">
    <div class="my-5 mx-10 h-12">
      <div class="float-left"><h2>订单列表</h2></div>
      <button class="float-right text-gray-400 text-sm" @click="BuntchHandle">
        {{ buntchtext }}
      </button>
      <button
        :class="{ hidden: !buntch }"
        class="mr-4 float-right text-red-600 text-sm"
        @click="deleteInBuntch"
      >
        删除
      </button>
    </div>
    <div class="w-full clear-both">
      <div
        class="p-5 m-5 border-2 rounded-2xl flex flex-row"
        v-for="(listitem, index) in orderitems"
        :key="listitem.number"
      >
        <div class="hidden mt-4" :class="{ checkboxwidth: buntch }">
          <el-checkbox size="large" v-model="listitem.selected" />
        </div>
        <el-descriptions
          class="w-full"
          :class="{ discriptwidth: buntch }"
          :title="listitem.name + '的订单'"
          :column="4"
          size="large"
        >
          <template #extra>
            <el-button type="primary" v-on:click="showmore(index)"
              >查看订单</el-button
            >
          </template>

          <el-descriptions-item v-for="item in listitem.list" :key="item">
            <div class="bgxlb p-2">
              {{ item.label }}
            </div>
            <div class="bgxlg p-2">
            {{ item.value }}</div>
          </el-descriptions-item>
        </el-descriptions>
      </div>
    </div>
  </div>

  <el-drawer v-model="drawer" title="订单详情" size="70%">
    <div class=" rounded-2xl border-2 p-5">
      <el-descriptions :column="3" :title="orderMore.name + '的订单'">
        <el-descriptions-item v-for="item1 in orderMore.list" :key="item1">
          <template #label>
            <div class="bgxlb p-2">
              {{ item1.label }}
            </div>
            <div class="bgxlg p-2">
            {{ item1.value }}</div>
          </template>
        </el-descriptions-item>
        <el-descriptions-item v-for="item2 in orderMore.more" :key="item2">
          <template #label>
            <div class="bgxlb p-2">
              {{ item2.label }}
            </div>
            <div class="bgxlg p-2">
            {{ item2.value }}</div>
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