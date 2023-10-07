<template>
  <div>
    <div class="my-5 mx-10 h-12">
      <div class="float-left">
        <h2>等待配送（{{ orderitems.length }}）</h2>
      </div>
    </div>
    <div class="w-full clear-both">
      <div
        class="p-5 m-5 border-2 rounded-2xl flex flex-row"
        v-for="(listitem, index) in orderitems"
        :key="listitem.number"
      >
        <el-descriptions
          class="w-full"
          :title="listitem.name + '的订单'"
          :column="4"
          size="large"
        >
          <template #extra>
            <el-button type="primary" v-on:click="showmore(index, 1)"
              >查看订单</el-button
            >
            <el-button type="primary" v-on:click="finishorder(index, 1)"
              >完成配送</el-button
            >
          </template>

          <el-descriptions-item v-for="item in listitem.list" :key="item">
            <div class="bgxlb p-2">
              {{ item.label }}
            </div>
            <div class="bgxlg p-2">
              {{ item.value }}
            </div>
          </el-descriptions-item>
        </el-descriptions>
      </div>
    </div>
  </div>

  <el-divider />
  <div>
    <div class="my-5 mx-10 h-12">
      <div class="float-left">
        <h2>等待提取（{{ distributionitems.length }}）</h2>
      </div>
    </div>
    <div class="w-full clear-both">
      <div
        class="p-5 m-5 border-2 rounded-2xl flex flex-row"
        v-for="(listditem, index1) in distributionitems"
        :key="listditem.number"
      >
        <el-descriptions
          class="w-full"
          :title="listditem.name + '的订单'"
          :column="4"
          size="large"
        >
          <template #extra>
            <el-button type="primary" v-on:click="showmore(index1, 0)"
              >查看订单</el-button
            >
            <el-button type="success" v-on:click="finishorder(index1, 0)"
              >完成提取</el-button
            >
          </template>

          <el-descriptions-item v-for="item2 in listditem.list" :key="item2">
            <div class="bgxlb p-2">
              {{ item2.label }}
            </div>
            <div class="bgxlg p-2">
              {{ item2.value }}
            </div>
          </el-descriptions-item>
        </el-descriptions>
      </div>
    </div>
  </div>
  <el-drawer v-model="drawer" title="订单详情" size="70%">
    <div class="rounded-2xl border-2 p-5">
      <el-descriptions :column="3" :title="orderMore.name + '的订单'">
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
    </div>
      <div class=" w-full text-center"><button @click="feedback" class=" text-red-500 hover:text-red-400">反馈</button></div>
  </el-drawer>

  <el-dialog v-model=" showsource">
    <SourceInfo :sourceinfo="sourceinfo" />
  </el-dialog>
  <el-dialog v-model="showfeedbackdialog" width="800px">
    <Feedback :ordernumber="orderMore.number" @feedbackemit="()=>{showfeedbackdialog=false}" />
  </el-dialog>
</template>











<script setup>
import { onMounted, ref } from "vue";
import axios from "axios";
import { useStore } from "vuex";
import { ElMessage } from 'element-plus'
import { useRouter } from "vue-router";
import SourceInfo from './SourceInfo.vue'
import example from "../../JSONExample.js";

let st = useStore();
let rt = useRouter();
let orderitems = ref([
  {
    name: "",
    number: "",
    list: [
      {
        label: "",
        value: "",
      },
    ],
  },
]);
let distributionordernumbers = [];
let distributionitems = ref([]);

onMounted(() => {
  updateOrders();
});

function updateOrders() {
  // 更新数据
  axios
    .post("/api/testproject/apis/getstationorders", {
      key: st.state.user.userKey,
    })
    .then((resp) => {
      console.log(resp.data);
      st.commit("setStationOrders", resp.data);

      let orders = st.state.stationorders;
      for (let i = 0; i < orders.length; i++) {
        if (orders[i].remark.state == "distribute") {
          distributionordernumbers.push(orders[i].ordernumber);
        }
      }

      QueryAndUpdateOrders();
    })
    .catch((err) => {
      console.log(err);
    });
}

function QueryAndUpdateOrders() {
  // 查询订单信息
  axios
    .post("/api/testproject/apis/getordersbynum", distributionordernumbers)
    .then((resp) => {
      console.log(resp.data);
      orderitems.value = [];
      let orders = resp.data;
      for (let i = 0; i < orders.length; i++) {
        let order = {
          name: orders[i].receiver.name,
          number:orders[i].number,
          selected: false,
          list: [],
          more: [],
        };
        order.list.push(getListItem("订单编号", orders[i].number));
        order.more.push(getListItem("订单状态", orders[i].state.text));
        order.more.push(getListItem("总价", orders[i].productinfo.price));
        order.list.push(getListItem("重量", orders[i].productinfo.weight));
        // order.list.push(getListItem("",orders[i].));
        order.more.push(getListItem("起始时间", orders[i].starttime));
        order.list.push(
          getListItem("联系方式（接收）", orders[i].receiver.phone)
        );
        order.list.push(
          getListItem("收货地址", orders[i].receiveraddress.address)
        );
        // order.more.push(getListItem("",orders[i].));
        order.more.push(getListItem("预留电话", orders[i].productor.phone));
        order.more.push(
          getListItem("发出地址", orders[i].startaddress.address)
        );
        order.more.push(
          getListItem("运输方式", getMeanStr(orders[i].productinfo.mean))
        );
        order.more.push(
          getListItem("预计运输时间（小时）", orders[i].productinfo.time)
        );
        order.more.push(
          getListItem("时间限制（小时）", orders[i].productinfo.limitedtime)
        );
        order.more.push(
          getListItem("是否配送", orders[i].distribution == 1 ? "是" : "否")
        );
        order.transinfo = orders[i].transportinfo;
        order.sourceinfo = orders[i].sourceinfo
        if (orders[i].distribution == 0) orderitems.value.push(order);
        else distributionitems.value.push(order);
      }
    })
    .catch((err) => {
      console.error(err);
    });
}

function getListItem(label, value) {
  return {
    label: label,
    value: value,
  };
}

function getMeanStr(mean) {
  console.log(mean);
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

let drawer = ref(false);
let orderMore = ref({});
function showmore(index, distribution) {
  drawer.value = true;
  if (distribution == 1) orderMore.value = orderitems.value[index];
  else orderMore.value = distributionitems.value[index];
}

function finishorder(index, distribution) {
  let o = {};
  if (distribution == 1) {
    o = orderitems.value[index];
  } else {
    o = distributionitems.value[index];
  }
  console.log(o.number);
  axios.post('/api/testproject/station/finishorder',{
      number:o.number
  })
  .then((resp)=>{
      console.log(resp.data);
      ElMessage('订单完结。');
      updateOrders();
  })
  .catch((err)=>{console.error(err)})
}

function querytransport(){
  console.log(orderMore.value);
  getTransportInfo('distribution',orderMore.value.transinfo.id);
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

let showsource = ref(false);
let sourceinfo = ref(example.order.sourceinfo)
function querysource(){
  sourceinfo.value = orderMore.value.sourceinfo;
  console.log(sourceinfo.value);
  showsource.value=true;
}

let showfeedbackdialog = ref(false);
function feedback(){
  console.log(orderMore.value);
  if(st.state.user.id>0)
    showfeedbackdialog.value = true;
  else{
    ElMessage('请先登陆');
    rt.push('/login');
  }

}
</script>