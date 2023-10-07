export default {
  user:
  {
    "role": 1,
    "remark": {},
    "userName": "我是用户名",
    "userKey": "q1",
    "info": {
      "phone": "158",
      "address": "广东技术师范大学",
      "area": {
        0: "210000", 1: "210200", 2: "210211"
      }
    }
  },
  msgs: [{
    "id": 2,
    "sendername": "DerivativeLIS官方团队",
    "receiverid": 2,
    "text": { "title": "欢迎使用", "content": "感谢您选择DerivativeLIS！" },
    "state": "unread",
    "remark": {}
  }, {
    "id": 3,
    "sendername": "syx",
    "receiverid": 1,
    "text": { "title": "This is the title", "content": "This is a msg." },
    "state": "unread",
    "remark": {}
  }],
  stations: [{
    "id": 1,
    "margin": 499.9,
    "belong": "440000",
    "name": "广东省中转站珠三角站",
    "type": 1,
    "transittimes": [{
      "mean": "air",
      "time": 0.5
    }, {
      "mean": "road",
      "time": 3
    }, {
      "mean": "cold",
      "time": 2
    }],
    "info": {},
    "remark": { "bigarea": 4 }
  }, {
    "id": 2,
    "margin": 499.9,
    "belong": "440100",
    "name": "广州市中转站",
    "type": 2,
    "transittimes": [{
      "mean": "cold",
      "out": 1,
      "time": 14,
      "price": 10
    }, {
      "mean": "cold",
      "out": 0,
      "time": 10,
      "price": 11
    }, {
      "mean": "road",
      "out": 1,
      "time": 14,
      "price": 4
    }, {
      "mean": "road",
      "out": 0,
      "time": 16,
      "price": 4.5
    }],
    "info": {
      "tsid": 1
    },
    "remark": {}
  }, {
    "id": 3,
    "margin": 499.9,
    "belong": "440106",
    "name": "天河区广东技术师范大学驿站",
    "type": 3,
    "transittimes": [{
      "mean": "cold",
      "out": 1,
      "time": 13,
      "price": 8.5
    }, {
      "mean": "cold",
      "out": 0,
      "time": 11,
      "price": 8
    }, {
      "mean": "road",
      "out": 1,
      "time": 15,
      "price": 4
    }, {
      "mean": "road",
      "out": 0,
      "time": 17,
      "price": 3.5
    }],
    "info": {
      "tsid": 2
    },
    "remark": {}
  }, {
    "id": 4,
    "margin": 499.9,
     
    "belong": "110000",
    "name": "北京市中转站",
    "type": 1,
    "transittimes": [{
      "mean": "air",
      "time": 0.4
    }, {
      "mean": "road",
      "time": 3.5
    }, {
      "mean": "cold",
      "time": 2.1
    }],
    "info": {},
    "remark": { "bigarea": 1 }
  }, {
    "id": 5,
    "name": "北京市中转站",
    "type": 2,
    "margin": 499.9,
    "belong": "110100",
    "transittimes": [{
      "mean": "cold",
      "out": 1,
      "time": 15,
      "price": 8
    }, {
      "mean": "cold",
      "out": 0,
      "time": 11,
      "price": 8.5
    }, {
      "mean": "road",
      "out": 1,
      "time": 15,
      "price": 3.5
    }, {
      "mean": "road",
      "out": 0,
      "time": 15,
      "price": 3.5
    }],
    "info": {
      "tsid": 4
    },
    "remark": {}
  }, {
    "id": 6,
    "margin": 499.9,
    "belong": "110105",
    "name": "北京市朝阳区中转站",
    "type": 3,
    "transittimes": [{
      "mean": "cold",
      "out": 1,
      "time": 13,
      "price": 8
    }, {
      "mean": "cold",
      "out": 0,
      "time": 11,
      "price": 7
    }, {
      "mean": "road",
      "out": 1,
      "time": 15,
      "price": 2.5
    }, {
      "mean": "road",
      "out": 0,
      "time": 17,
      "price": 3
    }],
    "info": {
      "tsid": 5
    },
    "remark": {}
  }, {
    "id": 7,
    "margin": 499.9,
     
    "belong": "540000",
    "name": "西藏自治区中转站",
    "type": 1,
    "transittimes": [{
      "mean": "air",
      "time": 0.7
    }, {
      "mean": "road",
      "time": 4.3
    }, {
      "mean": "cold",
      "time": 2.5
    }],
    "info": {},
    "remark": { "bigarea": 3 }
  }, {
    "id": 8,
    "name": "拉萨市中转站",
    "type": 2,
    "margin": 499.9,
    "belong": "540100",
    "transittimes": [{
      "mean": "cold",
      "out": 1,
      "time": 17,
      "price": 10
    }, {
      "mean": "cold",
      "out": 0,
      "time": 14,
      "price": 11
    }, {
      "mean": "road",
      "out": 1,
      "time": 11,
      "price": 4
    }, {
      "mean": "road",
      "out": 0,
      "time": 16,
      "price": 4
    }],
    "info": {
      "tsid": 7
    },
    "remark": {}
  }, {
    "id": 9,
    "margin": 499.9,
    "belong": "540102",
    "name": "城关区中转站",
    "type": 3,
    "transittimes": [{
      "mean": "cold",
      "out": 1,
      "time": 14,
      "price": 8
    }, {
      "mean": "cold",
      "out": 0,
      "time": 15,
      "price": 9
    }, {
      "mean": "road",
      "out": 1,
      "time": 16,
      "price": 3
    }, {
      "mean": "road",
      "out": 0,
      "time": 17,
      "price": 3
    }],
    "info": {
      "tsid": 8
    },
    "remark": {}
  }],
  path: {
    "mean":"cold",
    "weight":1,
    "start": {
      "id": 9,
      "margin": 499.9,
      "belong": "540102",
      "name": "城关区中转站",
      "type": 3,
      "transittimes": [{
        "mean": "cold",
        "out": 1,
        "time": 14,
        "price": 8
      }, {
        "mean": "cold",
        "out": 0,
        "time": 15,
        "price": 9
      }, {
        "mean": "road",
        "out": 1,
        "time": 16,
        "price": 3
      }, {
        "mean": "road",
        "out": 0,
        "time": 17,
        "price": 3
      }],
      "info": {
        "tsid": 8
      },
      "remark": {}
    },
    "end": {
      "id": 3,
      "margin": 499.9,
      "belong": "440106",
      "name": "天河区广东技术师范大学驿站",
      "type": 3,
      "transittimes": [{
        "mean": "cold",
        "out": 1,
        "time": 13,
        "price": 8.5
      }, {
        "mean": "cold",
        "out": 0,
        "time": 11,
        "price": 8
      }, {
        "mean": "road",
        "out": 1,
        "time": 15,
        "price": 4
      }, {
        "mean": "road",
        "out": 0,
        "time": 17,
        "price": 3.5
      }],
      "info": {
        "tsid": 2
      },
      "remark": {}
    }
  },
  createOrder:{
    "productorkey":"q1",
    "productor":{
      "name":"syx",
      "phone":"15812229888"
    },
    "startaddress":{
      "province":"110000",
      "city":"110100",
      "area":"110105",
      "address":"北京市东风一路1号",
      "stationid":6
    },
    "receiver":{
      "name":"wzh",
      "phone":"158788889412"
    },
    "receiveraddress":{
      "province":"440000",
      "city":"440100",
      "area":"440106",
      "address":"广东省广州市天河区中山大道西293号",
      "stationid":3
    },
    "productinfo":{
      "weight":2.4,
      "limitedtime":48,
      "distribution":0,
      "mean":"cold"
    },
    "sourceinfo":{},
    "remark":{}
  },
  query:{
    "station":"440106",
    "weight":1,
    "mean":"road",
    "out":1
  },
  order : {
    "sourceinfo": {
      records:[{
        text:'货物寄出，产商未提供溯源信息',
        time:'220407 22:18:03',
        content:{
          "type":"normal",
          "mark":""
        }
      },{
        text:'货物已经被驿站揽收，操作员：陈豪',
        time:'220407 22:18:03',
        content:{
          type:'sort',
          mark:''
        }
      },{
        text:'产商补充溯源信息，溯源码：XXXXXXXX',
        time:'220407 22:18:03',
        content:{
          type:'normal',
          mark:''
        }
      },{
        text:'大型卡车运输信息，点击查看',
        time:'220408 17:58:41',
        content:{
          type:'transportinfo',
          pagename:'transportinfo',
          params:{
            transid:7
          },
          mark:''
        }
      },{
        text:'分拣信息，货物由工作人员 谢良杰 进行分拣，工作环境温度20℃，湿度50%，货物状态良好。',
        time:'220409 09:25:55',
        content:{
          type:'sort',
          mark:''
        }
      },{
        text:'中型卡车运输信息，点击查看',
        time:'220410 03:58:01',
        content:{
          type:'transportinfo',
          pagename:'transportinfo',
          params:{
            transid:8
          },
          mark:''
        }
      }]
    },
    "receiver": {
        "phone": "158788889412",
        "name": "wzh"
    },
    "startaddress": {
        "area": "110105",
        "address": "广东技术师范大学",
        "province": "110000",
        "city": "110100",
        "stationid": 6
    },
    "remark": {},
    "starttime": "2022-04-14 04:40:20",
    "number": "220414044020342",
    "path": [
        6,
        5,
        4,
        1,
        2,
        3
    ],
    "productorkey": "q1",
    "transportinfo": {},
    "receiveraddress": {
        "area": "440106",
        "address": "广东省广州市天河区中山大道西293号",
        "province": "440000",
        "city": "440100",
        "areas": "440106",
        "stationid": 3
    },
    "state": {},
    "productor": {
        "phone": "158",
        "name": "我是用户名"
    },
    "productinfo": {
        "mean": "air",
        "price": 60,
        "weight": 1,
        "limitedtime": 108,
        "time": 79,
        "distribution": 1
    }
},
transinfo:[{
  text:'运输中，在广东省阳江市内',
  mark:'moving',
  temperatures:[
    {label:'220408 19:00:01',value:20.5},
    {label:'220408 20:00:01',value:22.5},
    {label:'220408 21:00:01',value:21.5},
    {label:'220408 22:00:01',value:19.5}
  ],
  humiditys:[
    {label:'220408 19:00:01',value:60},
    {label:'220408 20:00:01',value:64},
    {label:'220408 21:00:01',value:58},
    {label:'220408 22:00:01',value:66}
  ],
  position:'111.95516,21.86182',
  time:'220420 09:20:10',
  mean:'road'
},{
  text:'在站点中',
  mark:'staying,6',
  temperature:[
    {label:'220408 19:00:01',value:20.5},
    {label:'220408 20:00:01',value:22.5},
    {label:'220408 21:00:01',value:21.5},
    {label:'220408 22:00:01',value:19.5}
  ],
  humidity:[
    {label:'220408 19:00:01',value:60},
    {label:'220408 20:00:01',value:64},
    {label:'220408 21:00:01',value:58},
    {label:'220408 22:00:01',value:66}
  ],
  position:'111.95516,21.86182',
  time:'220420 09:20:10',
  mean:'road'
}],
su:{
            "type":"normal",
            "mark":""
          }
}
/*
// 列关联：Productor.vue,ProductorService.java,
// info关联：ProductorInfo.vue,store.js
*/