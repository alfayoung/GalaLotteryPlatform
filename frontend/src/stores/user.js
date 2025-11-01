import { ref } from "vue";
import { defineStore } from "pinia";        //使用pinia状态管理库
const bgColors = [
    'rgb(57, 193, 207,0.12)',
    'rgb(66, 105, 245,0.12)',
    'rgb(184, 107, 215,0.12)',
    'rgb(243, 84, 83,0.12)',
    'rgb(250, 116, 20,0.12)',
    'rgb(255, 171, 30,0.12)',
    'rgb(136, 104, 217,0.12)',
    'rgb(42, 184, 230,0.12)',
    'rgb(117, 133, 162,0.12)',]
const colors = [
    '#39c1cf',
    '#4269f5',
    '#b86bd7',
    '#f35453',
    '#FA7414',
    '#FFAB1E',
    '#8868D9',
    '#2AB8E6',
    '#7585A2',]
export const useuserStore = defineStore('user', () =>{
    const userName = ref("")                // 存放用户名
    const jAccount = ref("")                // 存放 jAccount 标识符
    const inputText = ref("")               // 存放用户输入的密码
    const is_admin = ref(false)             // 存放是否为管理员
    const login = ref(false)                // 未登录
    const head = ref("")                    // 存放头像
    const list = ref([])                    // 存放弹幕列表
    const isJoin = ref(false)               // 是否参与抽奖
    const userList = ref([
        {
          name: '张三',
          value: '1',
          bgColor:'rgb(57, 193, 207,0.12)',
          color:'#39c1cf',
        },
        {
          name: '李四',
          value: '8',
          bgColor:'rgb(66, 105, 245,0.12)',
          color:'#4269f5',
        },
        {
          name: '王五',
          value: '9',
          bgColor:'rgb(184, 107, 215,0.12)',
          color:'#b86bd7',
        },
        {
          name: '赵六',
          value: '3',
          bgColor:'rgb(243, 84, 83,0.12)',
          color:'#f35453',
        },
        {
          name: '田七',
          value: '6',
          bgColor:'rgb(250, 116, 20,0.12)',
          color:'#FA7414',
        },
        {
          name: '诸葛亮',
          value: '10',
          bgColor:'rgb(255, 171, 30,0.12)',
          color:'#FFAB1E',
        },
        {
          name: '刘备',
          value: '2',
          bgColor:'rgb(136, 104, 217,0.12)',
          color:'#8868D9',
        },
        {
          name: '曹操',
          value: '5',
          bgColor:'rgb(42, 184, 230,0.12)',
          color:'#2AB8E6',
        },
        {
          name: '孙权',
          value: '7',
          bgColor:'rgb(117, 133, 162,0.12)',
          color:'#7585A2',
        },
        {
          name: '孙良',
          value: '11',
          bgColor:'rgb(117, 133, 162,0.12)',
          color:'#7585A2',
        }
      ])                // 存放用户列表
    function updateuserName(newName) {
      userName.value = newName
      if (newName){
          localStorage.removeItem('userName')         //删除localStorage
          localStorage.setItem('userName', JSON.stringify(newName))   //存入localStorage
      } else {
          localStorage.removeItem('userName')         //删除localStorage
      }
    }                                       //改写用户名
    function updateinputText(newText) {
        inputText.value = newText
    }                                       //改写用户输入的密码
    function updateHead(newHead) {
      head.value = newHead
      if (newHead){
          localStorage.removeItem('head')         //删除localStorage
          localStorage.setItem('head', JSON.stringify(newHead))   //存入localStorage
      } else {
          localStorage.removeItem('head')         //删除localStorage
      }
    }                                       //改写头像
    function updateis_admin(bull) {
      is_admin.value = bull
      if (bull || !bull){
          localStorage.removeItem('is_admin')       //删除localStorage
          localStorage.setItem('is_admin', JSON.stringify(bull))   //存入localStorage
      } else {
          localStorage.removeItem('is_admin')         //删除localStorage
      }
    }                                       //改写是否为管理员
    function updatelogin(bull) {
      login.value = bull
      if (bull || !bull){
          localStorage.removeItem('login')         //删除localStorage 
          localStorage.setItem('login', JSON.stringify(bull))   //存入localStorage
      } else {
          localStorage.removeItem('login')         //删除localStorage
      }    
    }                                       //已登录
    function updateJAccount(newjAccount) {
      jAccount.value = newjAccount
      if (newjAccount){
          localStorage.removeItem('jAccount')         //删除localStorage
          localStorage.setItem('jAccount', JSON.stringify(newjAccount))   //存入localStorage
      } else {
          localStorage.removeItem('jAccount')         //删除localStorage
      }
    }
    function updateList(newList) {
        list.value+=(newList)
    }                                       //加入弹幕列表
    function updateUserList(newList) {
      const ul = ref([])
      for (let i = 0; i < newList.length; i++) {
          ul.value.push({
              name: newList[i],
              value: i,
              bgColor:bgColors[i%9],
              color:colors[i%9],
          })
      }
      userList.value = (ul)
      // if (userList){
      //     localStorage.removeItem('userList')         //删除localStorage
      //     localStorage.setItem('userList', JSON.stringify(userList))   //存入localStorage
      // } else {
      //     localStorage.removeItem('userList')         //删除localStorage
      // }
      }                                       //加入用户列表
    function updateIsJoin(bull) {
        isJoin.value = bull
        if (bull || !bull){
          localStorage.removeItem('isJoin')         //删除localStorage 
          localStorage.setItem('isJoin', JSON.stringify(bull))   //存入localStorage
      } else {
          localStorage.removeItem('isJoin')         //删除localStorage
      }    
    }                                       //是否参与抽奖
    function init() {
        const userName = JSON.parse(localStorage.getItem("userName"));  //这里一定要写成parse而不是stringify
        updateuserName(userName);
        const is_admin = JSON.parse(localStorage.getItem("is_admin"));
        updateis_admin(is_admin);
        const login = JSON.parse(localStorage.getItem("login"));
        updatelogin(login);
        const jAccount = JSON.parse(localStorage.getItem("jAccount"));
        updateJAccount(jAccount);
        const head = JSON.parse(localStorage.getItem("head"));
        updateHead(head);
        const isJoin = JSON.parse(localStorage.getItem("isJoin"));
        updateIsJoin(isJoin);
        // const userList = JSON.parse(localStorage.getItem("userList"));
        // updateUserList(userList);
    }                                       //初始化
    function logout() {
        updateuserName("")
        updateis_admin(false)
        updatelogin(false)
        updateJAccount("")
        updateIsJoin(false)
        // updateHead("")
        // updateUserList([])
    }                                       //登出
    return {
        userName,
        updateuserName,
        inputText,
        updateinputText,
        is_admin,
        updateis_admin,
        login,
        updatelogin,
        jAccount,
        updateJAccount,
        list,
        updateList,
        init,
        logout,
        userList,
        head,
        updateHead,
        updateUserList,
        isJoin,
        updateIsJoin
    }
})
