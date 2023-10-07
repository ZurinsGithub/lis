<template>
<div class=" hidden xl:inline">
  <div class="fixed bg-black">
    <video width="1920" height="1080" autoplay muted="muted" loop>
      <source src="../assets/1.MP4" type="video/mp4" />
    </video>
  </div>
  <div class="w-screen h-screen opacity-70 bg-black fixed"></div>
  <div class="w-screen h-screen fixed text-white text-2xl text-center">
  <div class=" my-2 mx-4 p-4 flex flex-row">
      <img class=" w-16 h-16" src="../assets/img/logo.png" />
      <div class=" text-xl mb-3 mt-auto mr-10 ml-auto">
          <a class=" p-2 mr-4 border-b-2">首页</a>
          <a class=" p-2 mr-4 hover:border-b-2 hover:opacity-70" href="/login">登陆</a>
          <a class=" p-2 mr-4 hover:border-b-2 hover:opacity-70 " href="/query">订单查询</a>
          <a class=" p-2 mr-4 hover:border-b-2 hover:opacity-70 " href="/">关于网站</a>
      </div>
  </div>
    <p class=" text-5xl font-bold" style="margin-top:15%;line-height:4rem">DerivativeLIS<br>纯粹激情，塑造未来</p>
    <div class=" absolute bottom-0 text-sm my-2 text-center w-full">
        本网站由  广东技术师范大学  18信管2班  吴志航  设计，仅用于学习和交流。未经允许，请勿转载。
    </div>
  </div>
</div>
</template>