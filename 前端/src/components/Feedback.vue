<template>
  <div>
    <el-form :model="form" :rules="rules" ref="formref" label-width="110px">
        
    <el-form-item label="当前订单编号">
      <div class="mb-2">{{ ordernumber }}</div>
    </el-form-item>
      <el-form-item label="联系方式" prop="phone">
        <el-input v-model="form.phone" type="textarea" rows="1" />
      </el-form-item>
      <el-form-item label="反馈内容" prop="text">
        <el-input v-model="form.text" type="textarea" rows="3" />
      </el-form-item>
      <el-form-item type="primary" style="margin-top: 10px">
        <el-button @click="submit" size="large" type="primary"
          >提交反馈</el-button
        >
      </el-form-item>
    </el-form>
  </div>
</template>














<script setup>
import { ElMessage } from "element-plus";
import { reactive, ref } from "vue-demi";
import axios from 'axios';
import { useStore } from "vuex";

let st = useStore();

const props = defineProps({
  ordernumber: ""
});

let ordernumber = ref(props.ordernumber);

let form = reactive({
  text: "",
  phone:"",
  key:""
});

let rules = reactive({
  text: [
    { required: true, message: "请输入反馈信息", trigger: "blur" },
    {
      min: 4,
      max: 500,
      message: "输入的字符数在4到500之间",
      trigger: "blur",
    },
  ],
  phone:[
    { required: true, message: "请输入联系方式", trigger: "blur" }]
});

let formref = ref();

let emit = defineEmits(['feedbackemit'])
function submit() {
  formref.value.validate((valid, fields) => {
    if (valid) {
      console.log(form.text);
      axios.post("/api/testproject/freeback/sendfreeback",{
        ordernumber:ordernumber.value,
        text:form.text,
        info:{
          receiverid:st.state.user.id,
          phone:form.phone
        },
        remark:{}
      }).then((resp)=>{
        ElMessage('提交成功')
        emit("feedbackemit");
      }).catch((err)=>{console.error(err)})
    } else {
      console.log(fields);
    }
  });
}
</script>










