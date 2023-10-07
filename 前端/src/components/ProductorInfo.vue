<script setup>
import axios from "axios";
import { reactive, ref,createApp, onMounted } from "vue";
import { useStore } from 'vuex';
import areas from '../assets/areas.js';

let st = useStore();
let user = ref(st.state.user);

let keybtn = ref('显示');

function showkey(){
  keybtn.value=user.value.userKey;
}

function save(){
  axios.post("/api/testproject/modifyproductor",user.value)
  .then(function(res){
    console.log(res.data)
    if(res.data == '1'){
      alert('修改成功')
    }else{
      alert('修改失败')
    } 
  })
  .catch(function(err){
    console.log(err)
  })
}

function test(){
  console.log(user.value.info.area);
}

</script>







<style scoped>
.items {
  @apply mt-4 ml-4;
  min-width: 100px;
}
.width-div1 {
  max-width: 1000px;
  width: 50%;
  min-width: 350px;
}
.itemsdiv{
  @apply flex flex-wrap;
}
</style>

<template>
  <div>
    <div class="mx-auto my-1 width-div1">
      <div class="mt-5 mx-auto">
        <h2>欢迎，{{ user.userName }}</h2>
        <p>您的基本资料将显示在这里:</p>
      </div>
      <el-divider/>
      <div class="itemsdiv">
        <div class="items">
          <p class="float-left">登陆秘钥：</p>
          <button class="float-left text-gray-400" type="button" v-on:click="showkey">{{keybtn}}</button>
        </div>
      </div>
      <el-divider/>
      <div class="itemsdiv">
        <div class="items">
          <p>用户名</p>
          <el-input v-model="user.userName"></el-input>
        </div>
        <div class="items">
          <p>联系方式</p>
          <el-input v-model="user.info.phone"></el-input>
        </div>
        <div class="items">
          <p>地址</p>
          <div>
            <el-cascader v-model="user.info.area" :options="areas"/>
          </div>
          <div class=" mt-2">
            <el-input v-model="user.info.address"></el-input>
          </div>
        </div>
        <div class=" w-all mt-5 text-center">
          <el-divider />
          <el-button v-on:click="save" round type="primary">保存</el-button>
        </div>
      </div>
    </div>
  </div>
</template>
