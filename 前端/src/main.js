import { createApp } from 'vue'
import App from './App.vue'

import './index.css'
import router from './route.js'
import 'element-plus/dist/index.css'    
import ElementPlus from 'element-plus'    
import store from './store.js'
import echarts from './echarts.js'
// import 'animate.css'

const app = createApp(App)
.use(ElementPlus)
.use(router)
.use(store)

app.config.globalProperties.echarts = echarts;
app.provide('$echarts',echarts);

app.mount('#app');