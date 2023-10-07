<template>
<h2>溯源信息</h2>
  <el-container class="w-full1 h-35screen">
    <el-scrollbar class=" w-full1">
      <div @click="btnclick(index)" v-for="(item,index) in source" :key="index" class="n2 bgxlb">{{ item.text }}</div>
    </el-scrollbar>
  </el-container>

  <el-dialog v-model=" showtext">
      <div>{{text}}</div>
  </el-dialog>
</template>











<style scoped>
.w-full1{
    width: 100%;
}
.n2{
    @apply my-4 rounded-lg w-full p-6;
}
</style>










<script setup>
import {  onMounted, ref } from "vue";
import {useRouter} from "vue-router";

onMounted(()=>{
    console.log("!!!");
})

let rt = useRouter();
let props = defineProps({sourceinfo:{},pagename:''})
let source = ref(props.sourceinfo.records);

function btnclick(index){
    switch(props.sourceinfo.records[index].content.type){
        case "transportinfo":{
            props.sourceinfo.records[index].content.params.name = props.pagename;
            rt.push({
                name:props.sourceinfo.records[index].content.pagename,
                params:props.sourceinfo.records[index].content.params
            })
            // console.log(props.sourceinfo.records[index].content)
            break;
        }
        default:{
            show(props.sourceinfo.records[index].text);
        }
    }
}

let showtext = ref(false);
let text = ref('');
function show(text1){
    showtext.value = true;
    text.value = text1;
}
</script>