<script setup>
import Navigation from "./Navigation.vue";
import ex from '../../JSONExample';
import {ref} from 'vue';
import axios from 'axios';
import { ElMessage } from "element-plus";

let form = ref(ex.user);
let show = ref(false);

let loading = ref(false);

let tarKey = '';
function getuser(){
  loading.value = true;
  axios.post('/api/testproject/manage/getuserkey',{
    userKey:form.value.userKey,
  })
  .then((resp)=>{
    console.log(resp.data)
    form.value = resp.data;
    tarKey = resp.data.userKey;
  show.value = true;
  loading.value = false;
  })
  .catch((err)=>{console.error(err);
  ElMessage('查询失败，请检查userKey')
  loading.value=false;
  })
}
function modifyuser(){
  loading.value = true;
  axios.post('/api/testproject/manage/modifyuserkey',{
    userKey:form.value.userKey,
    tarKey,
    role:form.value.role
  })
  .then(()=>{
    ElMessage('修改成功')
  loading.value = false;
  }).catch((err)=>{
    ElMessage('修改失败');
    console.error(err)
  })
}
</script>






<style scoped>
.widthlimited{
  min-width: 200px;
}
.hidden1{
  @apply hidden;
}
</style>







<template>
  <div>
    <!-- 导航栏 -->
    <Navigation />
    <div class="cxb">
      <!-- 页面模板 -->
      <div class=" sm:mx-10 md:mx-60 pt-10">
        <a href="/manage">返回管理页面</a>
        <el-form v-loading="loading" class=" pt-4 border-2 rounded-xl mx-auto my-8" style="width:400px" :model="form" label-width="100px">
          <div class=" ml-4 font-bold">修改用户秘钥或权限</div>
          <el-divider />
          <el-form-item :class="{hidden1:!show}" :label="'用户名'">
            {{form.userName}}
          </el-form-item>
          <el-form-item :label="'userKey'">
            <el-input v-model="form.userKey" clearable style="max-width:200px" />
          </el-form-item>
          <el-form-item :class="{hidden1:!show}" :label="'role'">
            <el-select v-model="form.role">
              <el-option value="1" />
              <el-option value="2" />
              <el-option value="3" />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="getuser">获取用户信息</el-button>
            <el-button type="danger" :class="{hidden1:!show}" @click="modifyuser">修改</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>
