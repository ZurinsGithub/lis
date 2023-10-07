<script setup>
import areas from "../assets/areas.js";
import { onMounted, reactive, ref } from "vue";
import axios from "axios";
import { useStore } from "vuex";
import { useRouter } from "vue-router";

let rt = useRouter();

let order = ref({
  productorkey: "q1",
  productor: {
    name: "",
    phone: "",
  },
  startaddress: {
    province: "110000",
    city: "110100",
    area: "110105",
    address: "北京市东风一路1号",
    stationid: undefined,
  },
  receiver: {
    name: "wzh",
    phone: "158788889412",
  },
  receiveraddress: {
    province: "440000",
    city: "440100",
    area: "440106",
    address: "广东省广州市天河区中山大道西293号",
    stationid: undefined,
  },
  productinfo: {
    weight: 1,
    limitedtime: 108,
    distribution: undefined,
    mean: "",
    price:0.0,
    time:0
  },
  path:[],
  sourceinfo: {records:[]},
  remark: {},
});

let st = useStore();
onMounted(()=>{
  // 自动填写用户信息
  try{
  order.value.productorkey = st.state.user.userKey;
  order.value.productor.name = st.state.user.userName;
  console.log(st.state.user.info);
  order.value.startaddress.address = st.state.user.info.address;
  order.value.productor.phone = st.state.user.info.phone;
  }catch(err){
    console.log(err)
  }

})

let options = ref([
  {
    value: 1,
    lable: "是",
  },
  {
    value: 0,
    lable: "否",
  },
]);

let means = ref([
  {
    lable: "冷链",
    value: "cold",
  },
  {
    lable: "空运（航运）",
    value: "air",
  },
  {
    lable: "普通（公路）",
    value: "road",
  },
]);

let stationarea = ref("");
let startstations = ref([]);

let hideselectstation = ref(true);
let hideselectendstation = ref(true);

function getStations() {
  let params = {
    weight: order.value.productinfo.weight,
    out: 1,
    mean: order.value.productinfo.mean,
    station: stationarea.value[2],
  };
  // console.log(params);
  if (params.mean != "") {
    axios
      .post("/api/testproject/apis/querystations", params)
      .then(function (resp) {
        startstations.value = resp.data;
        // order.value.startaddress.stationid = resp.data[0].id;
        hideselectstation.value = false;
      })
      .catch(function (err) {
        console.log(err);
      });
  }
}

let hideStations = ref(true);
function ShowStations(){
  hideStations.value=false;
}

let endstations = ref([]);
let endstationarea = ref([]);
function getendStations(){
  let params = {
    weight: order.value.productinfo.weight,
    out: 0,
    mean: order.value.productinfo.mean,
    station: endstationarea.value[2],
  };
    axios
      .post("/api/testproject/apis/querystations", params)
      .then(function(resp){
        endstations.value = resp.data;
        // order.value.receiveraddress.stationid = resp.data[0].id;
        hideselectendstation.value = false;
      })
      .catch(function(err){
        console.log(err);
      })
}

function examine(){
  // 校验数据
  let err = false;
  err = order.value.startaddress.stationid!=order.value.receiveraddress.stationid ? err : true;
  err = order.value.productor.name!="" ? err : true;
  err = (order.value.startaddress.stationid>0 && order.value.receiveraddress.stationid>0) ? err : true;
  err = (order.value.productor.phone!="" &&
  order.value.productinfo.weight>0&&
  order.value.productinfo.weight<501&&
  order.value.productinfo.limitedtime>0&&
  order.value.productinfo.limitedtime<24*14&&
  order.value.productinfo.distribution!=undefined&&
  order.value.productinfo.mean!=""&&
  order.value.startaddress.address!=""&&
  order.value.receiveraddress.address!=""&&
  order.value.receiver.name!=""&&
  order.value.receiver.phone!=""&&
  order.value.productorkey!="") ? err :true
  console.log(err);
  return err;
}

let hideSubmit = ref(true);
function calPrice(){
  // 估计价格
  if(order.value.startaddress.stationid>0&&order.value.receiveraddress.stationid>0&&order.value.receiveraddress.stationid!=order.value.startaddress.stationid){
    axios.post("/api/testproject/apis/calprice",{
      start:order.value.startaddress.stationid,
      end:order.value.receiveraddress.stationid,
      mean:order.value.productinfo.mean,
      weight:order.value.productinfo.weight
    })
    .then(function(resp){
      console.log(resp.data)
      order.value.productinfo.price = resp.data.price;
      order.value.productinfo.time = resp.data.time;
      hideSubmit.value = false;
    })
    .catch(function(err){console.log(err)})
  }
}

function submitOrder(){
  // 校验
  if(!examine()){
    // 自动补充
    order.value.startaddress.province = stationarea.value[0]
    order.value.startaddress.city = stationarea.value[1]
    order.value.startaddress.area = stationarea.value[2]
    order.value.receiveraddress.province = endstationarea.value[0]
    order.value.receiveraddress.city = endstationarea.value[1]
    order.value.receiveraddress.areas = endstationarea.value[2]
    console.log(order.value);
    if(order.value.productinfo.limitedtime>=order.value.productinfo.time){
      doSubmit();
    }else{
      dialogVisible.value=true;
    }
  }else{
    alert("填写的信息有误");
  }
}

let btndisabled = ref(false);
function doSubmit(){
  // btndisabled.value = true;
    axios.post("/api/testproject/apis/createorder",order.value)
    .then(function(resp){
      console.log(resp.data);
      // 跳转
      rt.push({name : "orders",
      params : {number : resp.data.number}
      });
    })
    .catch(function(err){
      console.log(err);
    })
}

let dialogVisible = ref(false);
</script>



<style scoped>
.ini {
  min-width: 200px;
  margin: 24px;
}

.lw {
  max-width: 400px;
}
</style>





<template>
  <div class="m-10">
    <h1>填写订单信息</h1>

    <el-divider />
    <h2>产商信息</h2>
    <p class=" text-gray-400">（已自动补充）</p>
    
    <div class="flex flex-wrap flex-row">
      <div class="ini">
        <p>姓名</p>
        <el-input v-model="order.productor.name"></el-input>
      </div>
      <div class="ini">
        <p>联系方式</p>
        <el-input v-model="order.productor.phone"></el-input>
      </div>
      <div class=" w-full px-8">
        <p>地址</p>
        <el-input v-model="order.startaddress.address" type="textarea"></el-input>
      </div>
    </div>
    <el-divider />
    <h2>收货信息</h2>
    <div class="flex flex-wrap flex-row">
      <div class="ini">
        <p>姓名</p>
        <el-input v-model="order.receiver.name"></el-input>
      </div>
      <div class="ini">
        <p>联系方式</p>
        <el-input v-model="order.receiver.phone"></el-input>
      </div>
      <div class=" w-full px-8">
        <p>地址</p>
        <el-input v-model="order.receiveraddress.address" type="textarea"></el-input>
      </div>
    </div>

    <el-divider />
    <h2>产品信息</h2>
    <div class="flex flex-wrap flex-row">
      <div class="ini">
        <p>重量</p>
        <el-input-number
          v-model="order.productinfo.weight"
          :precision="1"
          :step="0.1"
          :max="1000"
        />
      </div>
      <div class="ini">
        <p>时间限制（小时）</p>
        <el-input-number
          v-model="order.productinfo.limitedtime"
        ></el-input-number>
      </div>
      <div class="ini">
        <p>是否配送？</p>
        <el-select
          placeholder="请选择"
          v-model="order.productinfo.distribution"
        >
          <el-option
            v-for="item in options"
            :key="item.value"
            :value="item.value"
            :label="item.lable"
          ></el-option>
        </el-select>
      </div>
      <div class="ini">
        <p>运输方式</p>
        <el-select @change="ShowStations" v-model="order.productinfo.mean" placeholder="请选择">
          <el-option
            v-for="item in means"
            :key="item.value"
            :value="item.value"
            :label="item.lable"
          ></el-option>
        </el-select>
      </div>
    </div>

    <div :class="{ hidden : hideStations}" class="mx-10 p-2 rounded-xl bgxlb">
      <p class="mb-2">查找你寄出货物的驿站</p>
      <el-cascader
        v-model="stationarea"
        @change="getStations"
        :options="areas"
        placeholder="请选择所在地"
      ></el-cascader>
      <div :class="{ hidden: hideselectstation }" class="ml-4 inline">
        <el-select
          placeholder="请选择起始驿站"
          v-model="order.startaddress.stationid"
          @change="calPrice"
        >
          <el-option
            v-for="item in startstations"
            :key="item.id"
            :value="item.id"
            :label="item.name"
          ></el-option>
        </el-select>
      </div>
    </div>

<div :class="{ hidden : hideStations}" class=" my-4 mx-10 p-2 rounded-xl bgxlb">
      <p class="mb-2">查找收到货物的驿站</p>
      <el-cascader
        v-model="endstationarea"
        @change="getendStations"
        :options="areas"
        placeholder="请选择所在地"
      ></el-cascader>
      <div :class="{ hidden: hideselectendstation }" class="ml-4 inline">
        <el-select
          placeholder="请选择收货驿站"
          v-model="order.receiveraddress.stationid"
          @change="calPrice"
        >
          <el-option
            v-for="item in endstations"
            :key="item.id"
            :value="item.id"
            :label="item.name"
          ></el-option>
        </el-select>
      </div>
    </div>

<div class=" text-center" :class="{hidden:hideSubmit}">
  <p class=" text-xl text-red-700 mb-2">预计价格：{{order.productinfo.price}}元，预计运输时间：{{order.productinfo.time}}小时</p>
    <el-button :disabled="btndisabled" type="primary" @click="submitOrder">提交订单</el-button>
</div>
  </div>

    <el-dialog
    v-model="dialogVisible"
    title="警告"
    width="30%"
  >
    <span>超出运输时间，仍要创建订单吗？</span>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="()=>{
          dialogVisible = false;
          doSubmit()
          }"
          >确定</el-button
        >
      </span>
    </template>
  </el-dialog>
</template>