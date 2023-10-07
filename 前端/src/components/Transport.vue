<template>
  <el-dialog v-model="adddialog" width="400px">
    <div>
      <p class=" text-xl mb-8">添加交通工具信息</p>
      <el-form :model="form" :rules="formrules" ref="formref">
        <el-form-item label="名称" prop="name">
          <el-input class="input1" v-model="form.name" />
        </el-form-item>
        <el-form-item label="运输方式">
          <el-select v-model="form.mean">
            <el-option
              v-for="item in means"
              :key="item"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="限制重量">
          <div>
            <el-input-number class="input1" v-model="form.limitedweight" /> kg
          </div>
        </el-form-item>
        <el-form-item>
          <div class="text-center w-full">
            <el-button
              :disabled="!btnSunmitable"
              type="primary"
              @click="Submitcreate"
              >保存</el-button
            >
          </div>
        </el-form-item>
      </el-form>
    </div>
  </el-dialog>

  <div class="m-10 flex flex-row">
    <h2 class=" w-9/12">交通工具信息管理</h2>
    <el-button class="" @click="show" size="large" type="primary">添加交通工具信息</el-button>
  </div>
  <div v-loading="loading" class=" rounded-xl m-6 bgxlb p-3" v-for="item in trans" :key="item">
    <el-descriptions border column="3" :title="item.name">
      <template #extra>
        <el-button type="danger" @click="deleteitem(item.id)">删除 </el-button>
      </template>
      <el-descriptions-item v-for="item1 in item.list" :key="item1">
        <template #label>
          <div class=" bgxlg p-2">{{item1.label}}</div>
        </template>
        <div class=" px-2">{{item1.value}}</div>
      </el-descriptions-item>
    </el-descriptions>
  </div>
</template>








<script setup>
import { ElMessage } from "element-plus";
import { ref, reactive, onMounted } from "vue";
import axios from "axios";
import { useStore } from "vuex";
let formref = ref();

let means = ref([
  {
    label: "公路运输",
    value: "road",
  },
  {
    label: "航空运输",
    value: "air",
  },
  {
    label: "冷链运输",
    value: "cold",
  },
]);

let form = reactive({
  name: "",
  limitedweight: 50,
  info: {},
  remark: {},
  mean: "road",
});

const formrules = reactive({
  name: [
    { required: true, message: "请输入名称", trigger: "blur" },
    { min: 5, max: 50, message: "长度为5-50", trigger: "blur" },
  ],
});

let adddialog = ref(false);

function show() {
  adddialog.value = true;
}

let btnSunmitable = ref(true);
function Submitcreate() {
  btnSunmitable.value = false;
  formref.value.validate((valid, fields) => {
    if (valid) {
      // 提交
      axios
        .post("/api/testproject/station/createtransport", {
          limitedweight: form.limitedweight,
          info: form.info,
          remark: form.remark,
          name: form.name,
          mean: form.mean,
          stationid: st.state.user.info.stationid,
        })
        .then((resp) => {
          if (resp.data == "1") {
            ElMessage("提交成功");
            adddialog.value = false;
            updatetransport();
          } else ElMessage("提交失败");
          btnSunmitable.value = true;
        })
        .catch((err) => {
          console.log(err);
          btnSunmitable.value = true;
        });
    } else {
      ElMessage(fields.name[0].message);
    }
  });
}

let st = useStore();

let trans = ref([]);
onMounted(() => {
  // 获取交通信息
  updatetransport();
});

let loading = ref(true);
function updatetransport(){
  loading.value=true;
  axios
    .post("/api/testproject/station/gettransport", {
      stationid: st.state.user.info.stationid,
    })
    .then((resp) => {
      console.log(resp.data);
      let data = resp.data;
      trans.value=[];
      for(let i=0;i<data.length;i++){
        let list = [];
        list.push(additem('运输方式',getMeanStr(data[i].mean)));
        list.push(additem('重量限制',data[i].limitedweight));
        trans.value.push({
          id:data[i].id,
          name:data[i].name,
          list
        })
      }
      console.log(trans.value);
      loading.value=false;
    })
    .catch((err) => {
      console.error(err);
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

function additem(label,value){
  return {
    label,
    value
  }
}

function deleteitem(id){
  axios.post('/api/testproject/station/deletetransport',{
    id
  })
  .then((resp)=>{
    ElMessage('删除成功');
    updatetransport();
  }).catch((err)=>{console.error(err)})
}
</script>






<style scoped>
.input1 {
  @apply max-w-xs;
}
</style>