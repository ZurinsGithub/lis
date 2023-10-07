// 暂不启用改js文件
import axios from 'axios';
import {useRouter} from 'vue-router'
import { useStore } from 'vuex';

let rt = useRouter();
let st = useStore();

let getTransportInfo = (pagename,transid)=>{
    axios.post('/api',{
        id:transid
    })
    .then((resp)=>{
        st.commit('setTransinfo',resp.data);
        rt.push({
            path:'/transportinfo',
            params:{name:pagename}
        });
    })
}

export default getTransportInfo;