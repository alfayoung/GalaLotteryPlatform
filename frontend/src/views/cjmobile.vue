<template>
    <div>
      <img src="/assets/speit.png" alt="speit" id="back"/>
    </div>
    <div>
        <div class="centers">
    <h2 id="hh">手机端</h2>
        </div>
        <div class="centers">
    <el-button type="success" round class="but" @click="sendJoin" >
    <p v-if="user.isJoin">已参与<br>Déjà participé</p>
    <p v-else>参与抽奖<br>Participer au tirage</p>
    </el-button>
        </div>
    </div>
    <div id="抽奖池">
        <div v-for="(item,index) of jp" id="信息">
            <div>
        <div :id="generateIdx(index)">
    <p class="cjctxt">{{item[0]}}<br/>{{item[1]}}<br/>数量:{{item[2]}}</p>  
        </div>
    <img :src='item[3]' alt="奖品样图" :id="generateIds(index)"/>
            </div>
        </div>
        <div>
            <br/><br/><br/><br/>
        </div>
    </div>
    <div id="bullet">
    <BulletScreenInput/>
    </div>
    <div id="head">
    <router-link to="/userinfo" class="router-link">
        <Head id="hd"/>
    </router-link>
    </div>
</template>

<script setup>
import BulletScreenInput from '/src/components/BulletScreenInput.vue';
import {useuserStore} from "/src/stores/user.js"
import { ref } from "vue"
import Head from '/src/components/Head.vue';
import {getCurrentInstance} from "vue"
const user = useuserStore() 
const num = ref(0)
const {proxy} = getCurrentInstance()
//保留页面
user.init()
const jp=[['特等奖','小米行李箱',1,'/assets/images/小米行李箱.jpg'],
        ['一等奖','故宫天气瓶',2,'/assets/images/故宫天气瓶.jpg'],
        ['一等奖','故宫 金桂浮月 马克杯套装',2,'/assets/images/故宫.jpg'],
        ['二等奖','小爱音箱',3,'/assets/images/小爱音箱.jpg'],
        ['二等奖','muji颈部靠枕',3,'/assets/images/颈部靠枕.jpg'],
        ['三等奖','天堂伞',10,'/assets/images/天堂伞.jpg'],
        ['三等奖','小米蓝牙鼠标',10,'/assets/images/小米蓝牙鼠标.jpg'],
        ['参与奖','敦煌文创瑞兽香包九色鹿香囊',30,'/assets/images/敦煌文创.jpg']];
function generateIds(index) {
    return 's'+index
}
function generateIdx(index) {
    return 'x'+index
}
function sendJoin(){
    num.value++
    console.log(num.value)
    console.log(user.isJoin)
    if (num.value==1){
    proxy.$axios.post('/api/registerForLottery', null,
    {   
    params:{
        jAccount:user.jAccount,
    }
    }).then(response => {
    user.updateIsJoin(response.data)
    console.log(user.isJoin)
    // result=response.data
    }).catch(error => {
    console.log(error)
    })}
}
</script>

<style>
#head{
    position: fixed;
    top: 2vh;
    right: 5vw;
    z-index: 999;
}
#back{
    height: 100%;
    position: absolute;
    right: 0vw;
    top: 7.5vw;
    opacity: 20%;
}
.but{
    width: 250px;
    height: 80px;
    border-radius: 5vw;
    font-size: 25px;
    z-index: 1;
}
.centers {
    display: flex;
    justify-content: center;
    align-items: center;
    position: relative;
    top:60vh
}
#抽奖池{
    position: absolute;
    left: 0vw;
    width: 100vw;
    position:absolute;
    top: 100vh;
    background-color: #e7ddc2;
}
#信息{
    line-height: 5vh;
}
#x1,#x3,#x5,#x7{
    position: absolute;
    right: 2vw;
    font-size: 5vw;
    text-align: center;
    width: 35vw;
}
#x0,#x2,#x4,#x6{
    position: absolute;
    left: 2vw;
    font-size: 5vw;
    text-align: center;
    width: 35vw;
}
#s1,#s3,#s5,#s7{
    position: relative;
    right: 0vw;
    width: 60vw;
    margin: 1vw 1vw;
    cursor: pointer;
    border-radius: 1vw;
}
#s0,#s2,#s4,#s6{
    position: relative;
    left: 38vw;
    width: 60vw;
    margin: 1vw 1vw;
    cursor: pointer;
    border-radius: 1vw;
}
</style>