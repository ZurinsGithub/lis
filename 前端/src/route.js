import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
    history: createWebHistory(),
    routes: [
        { path: '/', component: () => import('./components/Test.vue') },
        {
            path: '/productor', component: () => import('./components/Productor.vue'),
            children: [{
                name: 'productorinfo',
                path: '',
                component: () => import('./components/ProductorInfo.vue')
            }, {
                name: 'orders',
                path: '/orders',
                component: () => import('./components/Orders.vue')
            }, {
                name: 'productormsgs',
                path: '/msgs',
                component: () => import('./components/Msgs.vue')
            }, {
                name: 'createorder',
                path: '/createorder',
                component: () => import('./components/CreateOrder.vue')
            }]
        },
        { path: '/login', component: () => import('./components/Login.vue') },
        { name: 'productor', path: '/productor', component: () => import('./components/Productor.vue') },
        {
            name: "station", path: '/station', component: () => import('./components/Station.vue'),
            children: [
                { name: 'accept', path: '/accept', component: () => import('./components/Accept.vue') },
                {name:'sort', path:'/sort',component:()=>import('./components/sort.vue')},
                {name:'transport',path:'/transport',component:()=>import('./components/Transport.vue')},                
                {name:'distribution',path:'/distribution',component:()=>import('./components/Distribution.vue')},
                {name:'stationinfo',path:'',component:()=>import('./components/StationInfo.vue')},{
                    name: 'stationmsgs',
                    path: '/msgs',
                    component: () => import('./components/Msgs.vue')
                }
            ]
        },
        {name:'transportinfo',path:'/transportinfo',component:()=>import('./components/Transportinfo.vue')},
        {name:'query',path:'/query',component:()=>import('./components/Query.vue')},
        {name:'manage',path:'/manage',component:()=>import('./components/Manage.vue')},
        {name:'manageuser',path:'/manageuser',component:()=>import('./components/ManageUser.vue')}
        ,{name:'index',path:'/index',component:()=>import('./components/Index.vue')}
        // {name:'',path:'/',component:()=>import('./components/.vue')}
    ]
})

export default router