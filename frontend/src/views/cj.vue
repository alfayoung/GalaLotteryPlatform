<template>
    <div>
      <img src="/assets/speit.png" alt="speit" id="back"/>
    </div>
    <div id="奖项">
    <p>{{ jp[classe][0] }}</p>
    <p>*</p>
    <p>{{ jp[classe][1] }}</p>
    </div>
    <div>
        <div id="tx" v-if="num%3==0 || num%3 ==1">
            <wordcloud :vRoll="(num%3==0)?60:10" :key="componentKey"/>
        </div>
        <el-button type="success" round class="but" @click="click1" >
            <p v-if="num%3==0">抽奖<br>Tirage</p>
            <p v-else-if="num%3==1">抽奖中<br>En cours de tirage</p>
            <p v-else>中奖了<br>Gagné</p>
        </el-button>
        <el-button type="success" round id="refresh" @click="refresh" >
            刷新
        </el-button>
    </div>

    <div v-if="num%3==2">
        <div id="获奖">
        <div v-for="(index) in result.value.length">
            <div v-if="currentIndex>index" id="卡">
                <h2 id="名字">{{result.value[index-1].name}}</h2>
                <img :src="getPhoto(result.value[index-1].profile)" alt="" id="big">
            </div>
        </div>
        </div>
    </div>

    <div id="抽奖池">
        <div v-for="(item,index) of jp" id="信息">
            <div>
    <p class="cjctxt">{{item[0]}}<br/>{{item[1]}}<br/>数量Nombre:{{item[2]}}</p>  
    <img :src='item[3]' alt="奖品样图" id="样图" @click="changeclass(index)"/>
            </div>
        </div>
    </div>
    <audio autoplay loop src="/assets/好运来.mp3"></audio>
    <!-- controls用于加上调节组件 -->
    <div id="bullet">
    <BulletScreen/>
    </div>
    <RouterLink to="/home">
        <img src="/assets/登出.png" alt="" id="logout">
    </RouterLink>
</template>
<script setup>
// 保留页面
import {useuserStore} from "/src/stores/user.js"
import wordcloud from "/src/components/cloud.vue"
import {getCurrentInstance} from "vue"
import {ref} from "vue"
import { random } from "lodash";
import BulletScreen from '/src/components/BulletScreen.vue';
const user = useuserStore()
user.init()
const {proxy} = getCurrentInstance()
const componentKey = ref(0)
const count = ref(0)
const num = ref(0)
const total = ref(3)
const classe = ref(0)
const currentIndex = ref(-30)
function getPhoto (index) {
    const base64 = btoa(new Uint8Array(index).reduce((data, byte) => data + String.fromCharCode(byte), ''))
    return 'data:image/png;base64,' + base64
}
function refresh (){
    proxy.$axios.get('/api/getAllRegistered', {})
    .then(response => {
    user.updateUserList(response.data)
    console.log(response.data)
    componentKey.value += 1
  }).catch(error => {
    console.log(error)
  })
}
refresh()
function getprize(){
    console.log(jp[classe.value][2])
    proxy.$axios.post('/api/setAndGetPrizeWinners', null,
    {   
    params:{
        prize:jp[classe.value][0]+jp[classe.value][1],
        num:jp[classe.value][2]
    }
    })
    .then(response => {
    result.value = response.data
    console.log(result.value)
    // result=response.data
    }).catch(error => {
    console.log(error)
    })
}
function changeclass(index){
        classe.value=index;
    }
function click1(event){
    num.value++;
    currentIndex.value = -35; // 重置当前显示的索引
    if (num.value%3==1){
        getprize();
        for (let i = 0; i < 30; i++) {
            setTimeout(function () {
            count.value =random(0,total-1);
            }, 100 * i)};
        setTimeout(function () {
        num.value++;
        }, 2900);
    };
    if (num.value%3==1){
        const intervalId=setInterval(function () {
        currentIndex.value +=1;      // 更新当前显示的元素索引
        console.log(currentIndex.value);
        // const element = document.getElementById('s'+currentIndex);
        // element.style.backgroundColor = 'red';
        // element.style.fontSize = '20px';
        }, 100);
        setTimeout(function () {
        clearInterval(intervalId);
        }, 7000);        //6500为临界值
    };
}
// 图片库
const result=[{
                "order": "/assets/images/敦煌文创.jpg",
                "userName":"小明",
                "index":1
            },{
                "order": "/assets/images/故宫.jpg",
                "userName":"小红",
                "index":2
            },{
                "order": "/assets/images/故宫天气瓶.jpg",
                "userName":"小蓝",
                "index":3
            }];
const jp=[['特等奖','小米行李箱',1,'/assets/images/小米行李箱.jpg'],
        ['一等奖','故宫天气瓶',2,'/assets/images/故宫天气瓶.jpg'],
        ['一等奖','故宫 金桂浮月 马克杯套装',2,'/assets/images/故宫.jpg'],
        ['二等奖','小爱音箱',3,'/assets/images/小爱音箱.jpg'],
        ['二等奖','muji颈部靠枕',3,'/assets/images/颈部靠枕.jpg'],
        ['三等奖','天堂伞',10,'/assets/images/天堂伞.jpg'],
        ['三等奖','小米蓝牙鼠标',10,'/assets/images/小米蓝牙鼠标.jpg'],
        ['参与奖','敦煌文创瑞兽香包九色鹿香囊',30,'/assets/images/敦煌文创.jpg']];
</script>

<style>
#logout{
    width: 5vw;
    position: absolute;
    left: 3vw;
    top: 15vh;
    z-index: 999;
}
#refresh{
    position: absolute;
    right: 5vw;
    top: 10vw;
    font-size: 1vw;

}
#back{
    height: 110%;
    position: absolute;
    right: 0vw;
    top: 7.5vw;
    opacity: 20%;
    z-index: -2;
}
#tx{
    position: relative;
    top: 5vw;
}
#big{
    width: 8vw;
    bottom: 0.2vw;
    border-radius: 1vw;
}
.but{
    width: 18vw;
    height: 6vw;
    border-radius: 5vw;
    position: absolute;
    left: 45vw;
    right: 45vw;
    bottom: -57vh;
    font-size: 2vw;
}
#获奖{
    display: flex;
    flex-wrap: wrap;
}
#名字{
    display: flex;
    justify-content: center;
    color: white;
}
#卡{
    margin: 0vw 0.5vw;
    padding: 0vw 0.5vw;   
    background:linear-gradient(to bottom, rgb(14, 2, 43), rgb(163, 48, 201));
    border-radius: 1vw;
    display: flex;
    flex-direction: column;
    justify-content: center;
    /* border: 1px solid rgb(60, 210, 244); */
}
#抽奖池{
    display: flex;
    /* justify-content: center; */
    width: 98%;
    flex-wrap: wrap;
    position:absolute;
    top: 160vh;
    background-color: #e7ddc2;
    /* background-image: linear-gradient(white,#e7c2ca); */
    border:10px #29AFD4 solid;
    border-radius: 10px;
}
#信息{
    line-height: 1.5vw;
}
.cjctxt{
    font-size: 1.5vw;
    text-align: center;
    margin: 0vw 1vw;
}
#样图{
    width: 15vw;
    display: flex;
    justify-content: center;
    /* 横 */
    align-items: center;
    /* 纵 */
    margin: 1vw 4.5vw;
    cursor: pointer;
    border-radius: 1vw;
}
#样图:active{
    opacity: 50%;
}
#奖项{
    font-size: 3vw;
    display: flex;
    justify-content: center;
    position: relative;
    top:4vw
}
audio{
    position: absolute;
    right: 35vw;
    top: 1vw;
}
#bullet{
    position: absolute;
    top: 10vw;
}
</style>