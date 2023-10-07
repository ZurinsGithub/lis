import {createStore} from "vuex";
import example from "../JSONExample.js"

const store = createStore({
    state () {
      return {
          // store数据
        count: 0,
        user:{
          "role": 1,
          "remark": "我是备注",
          "userName": "我是用户名",
          "userKey": "q1",
          "info": {
            "phone": "158",
              "address": "广东技术师范大学",
              "area":["440000","440100","440106"]
          }
      },
      msgs:{},
      stationorders:[],
      transinfo:{
        text:'运输中，在广东省阳江市内',
        mark:'moving',
        temperature:20.5,
        humidity:60.0,
        position:'111.95516,21.86182',
        time:'220420 09:20:10'
      },
      }
    },
    // store方法
    mutations: {
      increment (state) {
        state.count++
      },
      setUser(state , user){
        state.user = user
      },
      setMsgs(state,msgs){
        state.msgs = msgs
      },
      setStationOrders(state,data){
        state.stationorders = data;
      },
      setTransinfo(state,data){
        state.transinfo = data;
      },
    }
  })

  export default store;