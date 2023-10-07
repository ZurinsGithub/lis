<script setup>
import Navigation from "./Navigation.vue";
import { ref, reactive, onMounted, getCurrentInstance } from "vue";
import { useRouter } from "vue-router";
import { useStore } from "vuex";
import axios from "axios";

let rt = useRouter();
let st = useStore();

// 消息未读的点
let hidemsgp = ref(true);
let user = st.state.user;

let actives = reactive({
  stationinfo: true,
  sort: false,
  pushorder: false,
  msgs: false,
  transport: false,
});
function resetPage1(name){
  // 根据页面地址判断active的页面，用于其他组件调用
  resetActive();
  console.log(name);
  switch(name){
    case "stationinfo":{
      actives.stationinfo = true;
      break;
    }
    case "sort":{
      actives.sort = true;
      break;
    }
    case "accept":{
      actives.pushorder = true;
      break;
    }
    case "transport":{
      actives.transport = true;
      break;
    }
  }
}
function resetActive() {
  actives.stationinfo = false;
  actives.pushorder = false;
  actives.msgs = false;
  actives.sort = false;
  actives.transport = false;
}

onMounted(() => {
  if (user.userKey == "" || user.userKey == null) {
    // 返回登陆页面
    console.log("返回登陆页面");
  }
  getMsgs();

  rt.push({name:'stationinfo'});
});

function getMsgs() {
  // 根据userkey获取消息
  axios
    .post("/api/testproject/msgs/get", {
      key: user.userKey,
    })
    .then(function (resp) {
      st.commit("setMsgs", resp.data);
      for (let i = 0; i < resp.data.length; i++) {
        if (resp.data[i].state == "unread") {
          console.log(resp.data[i].state);
          hidemsgp.value = false;
        }
      }
    })
    .catch(function (err) {
      console.log(err);
    });
}

function logout() {
  rt.push("/login");
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
    <Navigation />
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
            <span class="flex items-center w-full px-3 mt-3">
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
              <span class="ml-2 text-sm font-bold">站点</span>
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
                <router-link
                  v-on:click="
                    () => {
                      resetActive();
                      actives.stationinfo = true;
                    }
                  "
                  :to="{ name: 'stationinfo' }"
                  class="defaultc"
                  :class="{ activec: actives.stationinfo }"
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
                  <span class="ml-2 text-sm font-medium">概览</span>
                </router-link>
                <router-link
                  :to="{ name: 'transport' }"
                  class="defaultc"
                  :class="{ activec: actives.transport }"
                  v-on:click="
                    () => {
                      resetActive();
                      actives.transport = true;
                    }
                  "
                >
                  <svg
                    t="1650282258504"
                    class="w-6 h-6"
                    fill="currentColor"
                    viewBox="0 0 1101 1024"
                    version="1.1"
                    xmlns="http://www.w3.org/2000/svg"
                    p-id="2750"
                  >
                    <path
                      d="M0 877.125162v24.405922l1078.741751 0.439306 1.561979-551.573836-478.356071-0.244059C268.221082 350.176901 0 586.572661 0 877.125162z m512.524361-471.864096v228.244183H120.17476A566.21739 566.21739 0 0 1 512.524361 405.261066zM42.954423 852.71924a420.562847 420.562847 0 0 1 47.957636-170.402147H561.336205V400.575129c12.398208-0.707772 24.723199-1.586385 37.316655-1.586385H707.771737v283.328349h319.424707l-0.488119 170.841453z m984.36405-219.213991H756.583581v-234.443287l271.393852 0.146436z"
                      p-id="2751"
                    ></path>
                    <path
                      d="M0 975.188156h1073.860566v48.811844H0z"
                      p-id="2752"
                    ></path>
                    <path
                      d="M1005.523985 0.000732a97.867747 97.867747 0 0 0-94.182453 96.574233h48.811844a46.371252 46.371252 0 1 1 92.498444 0.756584 49.836893 49.836893 0 0 1-50.227387 48.05526H512.524361v48.811844h489.900072a98.282648 98.282648 0 0 0 99.039231-97.111163A95.549184 95.549184 0 0 0 1005.523985 0.000732z"
                      p-id="2753"
                    ></path>
                    <path
                      d="M854.207269 243.010497h219.653297v48.811844h-219.653297z"
                      p-id="2754"
                    ></path>
                  </svg>

                  <span class="ml-2 text-sm font-medium">交通工具管理</span>
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
                <router-link
                  :to="{ name: 'accept' }"
                  class="defaultc"
                  :class="{ activec: actives.pushorder }"
                  v-on:click="
                    () => {
                      resetActive();
                      actives.pushorder = true;
                    }
                  "
                >
                  <svg
                    t="1650025014608"
                    viewBox="0 0 1024 1024"
                    version="1.1"
                    xmlns="http://www.w3.org/2000/svg"
                    class="w-6 h-6"
                    fill="currentColor"
                  >
                    <path
                      d="M512 256a32 32 0 0 0 32-32V127.04a32 32 0 1 0-64 0V224a32 32 0 0 0 32 32zM342.368 616.032a12.8 12.8 0 0 0-9.056 21.856l170.752 170.752a12.8 12.8 0 0 0 18.112 0l170.752-170.752a12.8 12.8 0 0 0-9.056-21.856H544V352a32 32 0 1 0-64 0v264.032h-137.632z"
                      p-id="1955"
                    ></path>
                    <path
                      d="M892.384 186.496l-174.176-86.08a32.032 32.032 0 0 0-28.384 57.376L864 243.872v502.848l-352 147.52-352-147.52V243.872l174.176-86.08a32 32 0 0 0-28.32-57.376l-174.208 86.08A63.648 63.648 0 0 0 96 243.872v502.848c0 25.888 15.424 49.056 39.264 59.008l364.384 152.704a31.936 31.936 0 0 0 24.768 0l364.352-152.672A63.84 63.84 0 0 0 928 746.72V243.872a63.68 63.68 0 0 0-35.616-57.376z"
                      p-id="1956"
                    ></path>
                  </svg>

                  <span class="ml-2 text-sm font-medium">接收订单</span>
                </router-link>

                <router-link
                  :to="{ name: 'sort' }"
                  class="defaultc"
                  :class="{ activec: actives.sort }"
                  v-on:click="
                    () => {
                      resetActive();
                      actives.sort = true;
                    }
                  "
                >
                  <svg
                    t="1650025014608"
                    class="w-6 h-6"
                    viewBox="0 0 1024 1024"
                    version="1.1"
                    xmlns="http://www.w3.org/2000/svg"
                    fill="currentColor"
                  >
                    <path
                      d="M512 256a32 32 0 0 0 32-32V127.04a32 32 0 1 0-64 0V224a32 32 0 0 0 32 32zM342.368 616.032a12.8 12.8 0 0 0-9.056 21.856l170.752 170.752a12.8 12.8 0 0 0 18.112 0l170.752-170.752a12.8 12.8 0 0 0-9.056-21.856H544V352a32 32 0 1 0-64 0v264.032h-137.632z"
                      p-id="1955"
                    ></path>
                    <path
                      d="M892.384 186.496l-174.176-86.08a32.032 32.032 0 0 0-28.384 57.376L864 243.872v502.848l-352 147.52-352-147.52V243.872l174.176-86.08a32 32 0 0 0-28.32-57.376l-174.208 86.08A63.648 63.648 0 0 0 96 243.872v502.848c0 25.888 15.424 49.056 39.264 59.008l364.384 152.704a31.936 31.936 0 0 0 24.768 0l364.352-152.672A63.84 63.84 0 0 0 928 746.72V243.872a63.68 63.68 0 0 0-35.616-57.376z"
                      p-id="1956"
                    ></path>
                  </svg>

                  <span class="ml-2 text-sm font-medium">分拣</span>
                </router-link>

                <router-link
                  :to="{ name: 'stationmsgs' }"
                  class="defaultc relative flex hover:text-gray-300flex"
                  :class="{ activec: actives.msgs }"
                  v-on:click="
                    () => {
                      resetActive();
                      actives.msgs = true;
                    }
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
                      d="M7 8h10M7 12h4m1 8l-4-4H5a2 2 0 01-2-2V6a2 2 0 012-2h14a2 2 0 012 2v8a2 2 0 01-2 2h-3l-4 4z"
                    />
                  </svg>
                  <span class="ml-2 text-sm font-medium">消息通知</span>
                  <span
                    :class="{ nonedisplay: hidemsgp }"
                    class="
                      absolute
                      top-0
                      left-0
                      w-2
                      h-2
                      mt-2
                      ml-2
                      bg-indigo-500
                      rounded-full
                    "
                  ></span>
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
        <router-view @updatestationorders="getOrders"  v-on:resetPage1="resetPage1" />
      </div>
    </div>
  </div>
</template>
