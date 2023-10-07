<!-- 这是导航栏，参照HelloWorld.vue -->
<template>
    <div class="barxh bgxg px-5 bar-min-width fixed w-full" style=" z-index:100">
      <div class="my-2 float-left w-20 h-12">
        <img src="../assets/img/logo.png" class="px-4"/>
      </div>
      <div class=" float-left pt-5 cxlb">
        <a href="/index" class="px-5">首页</a>
        <button @click="gotoquery" class="px-5">订单查询</button>
      </div>
      <div class=" float-right flex justify-center p-5 barxh">
        <a href="/login" class="a1">
          <img src="../assets/img/personal.png" class="img1" />
        </a>
        <!-- <a href="/login" class="a1">
          <img src="../assets/img/personal.png" class="img1" />
        </a> -->
      </div>
    </div>
    <div class=" w-full" style="height:65px"></div>
</template>

<style scoped>
.barxh{
  @apply h-16;
}
.img1{
  @apply h-6 w-6;
}
.a1{
  @apply px-3;
}
.bar-min-width{
  min-width: 600px;
}
</style>

<script setup>
import { useRouter } from "vue-router"


let rt = useRouter();
function gotoquery(){
  rt.push({
    name:'query'
  })
}
</script>