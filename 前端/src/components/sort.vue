<script setup>
import { onMounted } from "vue-demi";
import { useStore } from "vuex";
import { ref } from "vue";
import { ElMessage } from "element-plus";
import axios from "axios";
import { useRouter } from "vue-router";

onMounted(() => {
  HandleOrders();
});

let st = useStore();

let list = ref([]);
function HandleOrders() {
  // 更新订单
  axios
    .post("/api/testproject/apis/getstationorders", {
      key: st.state.user.userKey,
    })
    .then((resp) => {
      console.log(resp.data);
      st.commit("setStationOrders", resp.data);
      list.value = [];
      let orders = st.state.stationorders;
      for (let i = 0; i < orders.length; i++) {
        if (orders[i].remark.state == "in") {
          let next = getNextStation(orders[i]);
          AddInList(next, orders[i]);
        }
      }
      console.log(list.value);
    })
    .catch((err) => {
      console.log(err);
    });
}

function AddInList(next, order) {
  // 判断是否存在
  let exist = false;
  let i = 0;
  for (i = 0; i < list.value.length; i++) {
  if(next == 0 && order.mean!='cold'){
    order.mean = 'road';
  }
    if (next == list.value[i].nextid && order.mean == list.value[i].mean) {
      exist = true;
      break;
    }
  }
  if (exist) {
    i=list.value.length-1;
    list.value[i].orders.push({ order, selected: false });
  } else {
    let text = "发往站点" + next + "，运输方式：" + getMeanStr(order.mean);
    let mean = order.mean;
    if (next == 0) {
      text = "等待配送：" + getMeanStr(order.mean) + "运输";
      mean = "road";
    }
    list.value.push({
      nextid: next,
      orders: [{ order, selected: false }],
      mean: mean,
      selectable: true,
      text: text,
    });
  }
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

function getNextStation(order) {
  let path = toArr(order.path);
  let res = 0;
  for (let i = 0; i < path.length; i++) {
    if (path[i] == order.stationid) {
      if (i != path.length - 1) {
        res = path[i + 1];
        break;
      }
    }
  }
  // console.log('next:' + res);
  return res;
}

function toArr(str) {
  str = str.slice(1, str.length - 1);
  return str.split(",");
}

let count = {
  selectedcount: 0,
  mean: "",
  stationid: 0,
  orders: [],
};
function checkboxChange(item, selected, item2) {
  count.mean = item.mean;
  count.stationid = item.nextid;
  if (st.state.user.remark.type != 1 && item.mean == "air") {
    count.mean = "road";
  }

  if (selected) {
    count.selectedcount++;
    console.log(st.state.user)
    for (let i = 0; i < list.value.length; i++) {
      if (st.state.user.remark.type != 1) {
        if (
          count.stationid != list.value[i].nextid ||
          (list.value[i].mean != count.mean &&
            !(count.mean == "road" && list.value[i].mean == "air"))
        ) {
          list.value[i].selectable = false;
        } else {
          list.value[i].selectable = true;
        }
      } else {
        if (
          count.stationid != list.value[i].nextid ||
          list.value[i].mean != count.mean
        ) {
          list.value[i].selectable = false;
        } else {
          list.value[i].selectable = true;
        }
      }
    }
    count.orders.push(item2.order);
  } else {
    count.selectedcount--;
    if (count.selectedcount == 0) {
      for (let i = 0; i < list.value.length; i++) {
        list.value[i].selectable = true;
      }
    }
    // 删除
    for (let i = 0; i < count.orders.length; i++) {
      if (item2.order.ordernumber == count.orders[i].ordernumber) {
        count.orders.splice(i, i);
        break;
      }
    }
  }
}

let selecttransport = ref(false);
let transports = ref([]);
let transportid = ref();
function confirm() {
  if (count.selectedcount != 0) {
    if (count.stationid != 0) {
      console.log(count);
      selecttransport.value = true;
      // 获取交通工具信息
      transports.value = [];
      axios
        .post("/api/testproject/station/gettransport", {
          stationid: st.state.user.info.stationid,
        })
        .then((resp) => {
          let data = resp.data;
          for (let i = 0; i < data.length; i++) {
            if (data[i].mean == count.mean) {
              transports.value.push(data[i]);
            }
          }
        });
    } else {
      // 是进行配送的订单

      let ordernumbers = [];
      for (let i = 0; i < count.orders.length; i++) {
        ordernumbers.push(count.orders[i].ordernumber);
      }
      let params = {
        id: st.state.user.info.stationid,
        ordernumbers,
        text: st.state.user.userName + " 已经分拣完毕",
        state: "distribute",
        transportinfo: {},
      };

      axios.post("/api/testproject/apis/modifystateinbuntch", params)
      .then((resp)=>{
        console.log(resp.data)
        ElMessage('已出库，请前往配送页面查看。')
        HandleOrders();
      }).catch((err)=>{console.error(err)})
    }
  } else {
    ElMessage("请选择需要分拣的订单");
  }
}

let confirmWeight = ref(false);
function confirmSend() {
  if(transportor.value!=""&&transportor.value!=null){
    
  let t = 0;
  for (let i = 0; i < count.orders.length; i++) {
    t += count.orders[i].weight;
  }

  console.log(transports.value);

  let n = 0;
  for (let i = 0; i < transports.value.length; i++) {
    if (transportid.value == transports.value[i].id) {
      n = transports.value[i].limitedweight;
      break;
    }
  }

  // console.log(" " + t + " " + n);

  if (t > n) {
    // 弹出确认框
    confirmWeight.value = true;
  } else {
    confirmSend1();
  }
  }else{
    ElMessage('请填写运输人员信息')
  }
}

let transportor = ref();
function confirmSend1() {
  console.log(transportid);
  console.log(count.orders);
  let ordernumbers = [];
  for (let i = 0; i < count.orders.length; i++) {
    ordernumbers.push(count.orders[i].ordernumber);
  }
  let params = {
    id: count.stationid,
    ordernumbers,
    text: st.state.user.userName + " 已发出",
    state: "out",
    transportinfo: {
      id: transportid.value,
      transportor:transportor.value
    },
    sourcetext:"货物分拣出库，操作员："+transportor.value+"，点击查看运输信息"
  };
  console.log(params);
  axios
    .post("/api/testproject/station/sort", params)
    .then((resp) => {
      console.log(resp.data);
      ElMessage("出库成功");
      HandleOrders();
      selecttransport.value = false;
    })
    .catch((err) => {
      console.error(err);
    });
}

let rt = useRouter();
function gotodistribute() {
  rt.push({
    name: "distribution",
  });
}
</script>





<style scoped>
.selected {
  @apply border-green-500;
}
</style>









<template>
  <div class="p-10">
    <div class="flex flex-row">
      <p class="text-2xl w-6/12">选择订单进行分拣</p>
      <div class="w-6/12 pr-20">
        <el-button
          class="float-right"
          size="large"
          type="primary"
          @click="confirm"
          >确定</el-button
        >
        <el-button
          class="float-right mr-4"
          size="large"
          type="success"
          @click="gotodistribute"
          >前往配送页面</el-button
        >
      </div>
    </div>
    <el-divider />
    <div v-for="item1 in list" :key="item1">
      <div class="my-4">
        <p class="text-lg">{{ item1.text }}</p>
      </div>
      <el-collapse>
        <div class="flex flex-row" v-for="item2 in item1.orders" :key="item2">
          <div class="w-6 mr-4 py-2">
            <el-checkbox
              :disabled="!item1.selectable"
              @change="checkboxChange(item1, item2.selected, item2)"
              v-model="item2.selected"
              size="large"
            />
          </div>
          <div
            :class="{ selected: item2.selected }"
            class="rounded-md px-2 w-11/12 border-2 my-1"
          >
            <el-collapse-item :title="'订单编号：' + item2.order.ordernumber">
              <div>
                <el-descriptions>
                  <el-descriptions-item label="产商名">{{
                    item2.order.remark.productor
                  }}</el-descriptions-item>
                  <el-descriptions-item label="联系方式">{{
                    item2.order.remark.phone
                  }}</el-descriptions-item>
                  <el-descriptions-item label="重量"
                    >{{ item2.order.weight }} kg</el-descriptions-item
                  >
                </el-descriptions>
              </div>
            </el-collapse-item>
          </div>
        </div>
      </el-collapse>
    </div>
  </div>
  <el-dialog v-model="selecttransport">
    <p class="text-xl mb-6">请选择交通工具信息</p>
    <div>
      <div class="mb-2">
      <el-select placeholder="请选择交通工具" v-model="transportid">
        <el-option
          v-for="item in transports"
          :key="item"
          :label="item.name"
          :value="item.id"
        />
      </el-select></div>
      <div class=" mb-2" style="width:300px">
      <el-input placeholder="请填写运输人员信息" v-model="transportor"></el-input>
      </div>
      <el-button type="primary" @click="confirmSend"
        >确认发出</el-button
      >
    </div>
  </el-dialog>
  <el-dialog v-model="confirmWeight">
    <p>当前选择的订单总重量已超出运输工具的限制重量，是否仍要发出？</p>
    <div class="my-4 text-center">
      <el-button type="danger" @click="confirmSend1">继续</el-button>
      <el-button
        type="success"
        @click="
          () => {
            confirmWeight = false;
          }
        "
        >取消</el-button
      >
    </div>
  </el-dialog>
</template>