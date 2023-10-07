<script setup>
import Navigation from "./Navigation.vue";
import { ref, reactive } from "vue";
import axios from "axios";
import { useRouter } from "vue-router";
import { createStore, useStore } from 'vuex';
import { ElMessage } from "element-plus";

// 规范：变量声明一定要在头头
const userkey = ref("w");
let available = true;
let rt = useRouter();
let st = useStore();

let doLogin = () => {
  if(available){
  available=false;
  axios
    .post("/api/testproject/login", {
      key: userkey.value,
    })
    .then(function (response) {
      console.log(response.data);
      callbackLogin(response);
    })
    .catch(function (err) {
      console.log(err);
  available=true;
    });
  }
};
// 处理返回的数据
function callbackLogin(resp) {
  console.log(resp.data);
  if (resp.data.role == 1) {
    console.log("是产商登陆");
    // 更新user状态
    st.commit('setUser',resp.data);
    // 跳转页面
    rt.push({
      name:'productor'
    })
  }else if(resp.data.role==2){
    st.commit('setUser',resp.data)
    rt.push({
      name:"station"
    })
  }else if(resp.data.role==3){
    st.commit('setUser',resp.data)
    rt.push({
      name:"manage"
    })
  }
  else{
    ElMessage('秘钥错误')
  }
  available=true;
}

function signup(){
  ElMessage('系统正在内测，请联系客服注册');
}
</script>

















<template>
  <div>
    <!-- 导航栏 -->
    <Navigation />
    <div class="cxb">
      <!-- 页面模板 -->
      <div
        class="flex flex-col items-center justify-center w-screen h-all bgxlg"
      >
        <!-- Component Start -->
        <h1 class="font-bold text-2xl">DerivativeLIS 物流信息管理系统</h1>
        <form
          class="flex flex-col bg-white rounded shadow-lg p-12 mt-12"
          action=""
        >
          <label class="font-semibold text-xs" for="usernameField">秘钥</label>
          <input
            class="
              flex
              items-center
              h-12
              px-4
              w-64
              bg-gray-200
              mt-2
              rounded
              focus:outline-none focus:ring-2
            "
            type="text"
            v-model="userkey"
          />
          <button
            type="button"
            class="
              flex
              items-center
              justify-center
              h-12
              px-6
              w-64
              hover:bg-green-500
              mt-8
              rounded
              font-semibold
              text-sm text-white
              bg-green-600
            "
            v-on:click="doLogin"
          >
            登陆
          </button>
          <div class="flex mt-6 justify-center text-xs">
            <a class="hover:text-blue-500" href="#" @click="signup">注册</a>
          </div>
        </form>
        <!-- Component End  -->
      </div>
    </div>
  </div>
</template>
<style scoped>
.w-this {
  min-width: 200px;
  width: 600px;
  max-width: 1000px;
}
.h-this {
  height: 80vh;
}
</style>