<script setup>
import Navigation from "./Navigation.vue";
import { ref, reactive, onMounted } from "vue";
import { useRouter } from "vue-router";
import {useStore} from 'vuex';
import axios from "axios";

let rt = useRouter();
let st = useStore();

// 消息未读的点
let hidemsgp = ref(true);
let user = st.state.user;

let actives = reactive({
  productorinfo:true,
  orders:false,
  pushorder:false,
  msgs:false
});
function resetPage(name){
  // 根据页面地址判断active的页面，用于其他组件调用
  resetActive();
  console.log(name);
  switch(name){
    case "productorinfo":{
      actives.productorinfo = true;
      break;
    }
    case "orders":{
      actives.orders = true;
      break;
    }
    case "createorder":{
      actives.pushorder = true;
      break;
    }
    case "productormsgs":{
      actives.msgs = true;
      break;
    }
  }
  // rt.push({name : name});
}
function resetActive(){
  actives.productorinfo = false;
  actives.orders=false;
  actives.pushorder=false;
  actives.msgs=false;
}

onMounted( ()=>{

  if(user.userKey==""||user.userKey==null){
    // 返回登陆页面
    console.log("返回登陆页面");
  }else{
    rt.push({
      name:'productorinfo',
      params:user
    })
  }

  getMsgs();
})


function getMsgs(){
  // 根据userkey获取消息
    axios
    .post("/api/testproject/msgs/get", {
      key: user.userKey,
    })
    .then(function (resp) {
      st.commit('setMsgs',resp.data)
      for(let i = 0;i<resp.data.length;i++){
        if(resp.data[i].state == 'unread'){
        console.log(resp.data[i].state)
          hidemsgp.value = false  
        }
      }
    })
    .catch(function (err) {
      console.log(err);
    });
}

function logout(){
  rt.push('/login')
}
</script>




<style scoped>
.h-this {
  height: 90vh;
}
.nonedisplay {
  display: none;
}
/* 菜单栏选中样式 */
.activec {
  @apply flex items-center w-full h-12 px-3 mt-2 text-gray-200 bg-gray-700 rounded;
}
.defaultc {
  @apply flex items-center w-full h-12  px-3 mt-2 rounded hover:bg-green-800;
}
</style>
<template>
  <div>
    <!-- 导航栏 -->
    <Navigation/>
    <div class="cxb">
      <!-- 页面模板 -->

      <div class="w-40 fixed left-0">
        <div class="flex items-center justify-center h-this space-x-6 m-1">
          <!-- Component Start -->
          <div
            class="
              flex flex-col
              items-center
              w-40
              h-this
              overflow-hidden
              text-indigo-300
              bgxg
              rounded-md
            "
          >
            <span class="flex items-center w-full px-3 mt-3" >
              <svg
                class="w-8 h-8 fill-current"
                xmlns="http://www.w3.org/2000/svg"
                viewBox="0 0 20 20"
                fill="currentColor"
              >
                <path
                  d="M11 17a1 1 0 001.447.894l4-2A1 1 0 0017 15V9.236a1 1 0 00-1.447-.894l-4 2a1 1 0 00-.553.894V17zM15.211 6.276a1 1 0 000-1.788l-4.764-2.382a1 1 0 00-.894 0L4.789 4.488a1 1 0 000 1.788l4.764 2.382a1 1 0 00.894 0l4.764-2.382zM4.447 8.342A1 1 0 003 9.236V15a1 1 0 00.553.894l4 2A1 1 0 009 17v-5.764a1 1 0 00-.553-.894l-4-2z"
                />
              </svg>
              <span class="ml-2 text-sm font-bold">产商</span>
            </span>
            <div class="w-full px-2">
              <div
                class="
                  flex flex-col
                  items-center
                  w-full
                  mt-3
                  border-t border-gray-700
                "
              >
                <router-link v-on:click="()=>{
                  resetActive();
                  actives.productorinfo = true;
                  }" :to="{name:'productorinfo',params:user}"
                  class="defaultc"
                  :class="{activec:actives.productorinfo}"
                >
                  <svg
                    class="w-6 h-6 stroke-current"
                    xmlns="http://www.w3.org/2000/svg"
                    fill="none"
                    viewBox="0 0 24 24"
                    stroke="currentColor"
                  >
                    <path
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      stroke-width="2"
                      d="M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 01-1 1h-3m-6 0a1 1 0 001-1v-4a1 1 0 011-1h2a1 1 0 011 1v4a1 1 0 001 1m-6 0h6"
                    />
                  </svg>
                  <span class="ml-2 text-sm font-medium">信息资料</span>
                </router-link>
                <router-link :to="{name:'orders'}"
                  class="defaultc"
                  :class="{activec:actives.orders}"
                  v-on:click="()=>{
                  resetActive();
                  actives.orders = true;
                  }"
                >
                  <svg
                    class="w-6 h-6 stroke-current"
                    xmlns="http://www.w3.org/2000/svg"
                    fill="none"
                    viewBox="0 0 24 24"
                    stroke="currentColor"
                  >
                    <path
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      stroke-width="2"
                      d="M8 7v8a2 2 0 002 2h6M8 7V5a2 2 0 012-2h4.586a1 1 0 01.707.293l4.414 4.414a1 1 0 01.293.707V15a2 2 0 01-2 2h-2M8 7H6a2 2 0 00-2 2v10a2 2 0 002 2h8a2 2 0 002-2v-2"
                    />
                  </svg>
                  <span class="ml-2 text-sm font-medium">订单管理</span>
                </router-link>
              </div>
              <div
                class="
                  flex flex-col
                  items-center
                  w-full
                  mt-2
                  border-t border-gray-700
                "
              >
                <router-link :to="{name:'createorder'}"
                  class="defaultc"
                  :class="{activec:actives.pushorder}"
                  v-on:click="()=>{
                  resetActive();
                  actives.pushorder = true;
                  }"
                >
                  <svg
                    class="w-6 h-6 stroke-current"
                    xmlns="http://www.w3.org/2000/svg"
                    fill="none"
                    viewBox="0 0 24 24"
                    stroke="currentColor"
                  >
                    <path
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      stroke-width="2"
                      d="M3 3h2l.4 2M7 13h10l4-8H5.4M7 13L5.4 5M7 13l-2.293 2.293c-.63.63-.184 1.707.707 1.707H17m0 0a2 2 0 100 4 2 2 0 000-4zm-8 2a2 2 0 11-4 0 2 2 0 014 0z"
                    />
                  </svg>
                  <span class="ml-2 text-sm font-medium">下单</span>
                </router-link>

	<router-link :to="{name:'productormsgs'}" class=" defaultc relative flex  hover:text-gray-300flex" :class="{activec:actives.msgs}" v-on:click="()=>{
                  resetActive();
                  actives.msgs= true;
                  }">
					<svg class="w-6 h-6 stroke-current" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
						<path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M7 8h10M7 12h4m1 8l-4-4H5a2 2 0 01-2-2V6a2 2 0 012-2h14a2 2 0 012 2v8a2 2 0 01-2 2h-3l-4 4z" />
					</svg>
					<span class="ml-2 text-sm font-medium">消息通知</span>
					<span :class="{nonedisplay:hidemsgp}" class="absolute top-0 left-0 w-2 h-2 mt-2 ml-2 bg-indigo-500 rounded-full"></span>
				</router-link>


              </div>
            </div>
            <button
            type="button"
            v-on:click="logout"
              class="
                flex
                items-center
                justify-center
                w-full
                h-16
                mt-auto
                bg-red-600
                hover:bg-red-400
              "
              
            >
              <svg
                class="w-6 h-6 stroke-current"
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M5.121 17.804A13.937 13.937 0 0112 16c2.5 0 4.847.655 6.879 1.804M15 10a3 3 0 11-6 0 3 3 0 016 0zm6 2a9 9 0 11-18 0 9 9 0 0118 0z"
                />
              </svg>
              <span class="m-2 text-sm font-medium w-20 text-center">{{
                user.userName
              }}</span>
            </button>
          </div>
          <!-- Component End  -->
        </div>
      </div>
    <div class=" ml-40 pt-1">
        <router-view v-on:resetPage="resetPage" />
    </div>
    </div>
  </div>
</template>
