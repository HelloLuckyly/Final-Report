<script setup>
import {ref,onMounted} from 'vue'
import request from'../utils/request'
const radio1 = ref('New York')
const handleOpen = (key, keyPath) => {
  console.log(key, keyPath)
}
const handleClose = (key, keyPath) => {
  console.log(key, keyPath)
}
import { Search } from '@element-plus/icons-vue'
 const select = ref('')
 const input3 = ref('')
import{defineSearchByIndustry} from'../store/searchByIndustry'
let searchByIndustry = defineSearchByIndustry()


async function SearchSomething(){
  console.log(select.value)
  if(select.value == 1){
    let {data} = await request.get("user/getAllJobByWorkCity",{params:{"workCity":input3.value}})
    searchByIndustry.searchByIndustryList = data.data.searchByWorkCityList
    router.push("/searchPage")
  }else if(select.value == 2){
    console.log(input3.value)
    let {data} = await request.get("user/getAllJobByIndustry",{params:{"industry":input3.value}})
    searchByIndustry.searchByIndustryList = data.data.searchByIndustryList
    router.push("/searchPage")
  }else if(select.value == 3){
    let {data} = await request.get("user/getAllJobByJobTitle",{params:{"position":input3.value}})
    searchByIndustry.searchByIndustryList = data.data.searchByJobTitleList
    router.push("/searchPage")
  }else{
    window.alert("Sorry, your search was incorrect.")
  }
 
}




import {defineCampusRecruitment} from "../store/campusRecruitmentStore.js"
let campusRecruitment = defineCampusRecruitment()
const imageList = ref([
  { url: '/img/2.jpg' },
  { url: '/img/3.jpg' },
  { url: '/img/4.jpg' },
  { url: '/img/1.jpg' }
]);

onMounted(async ()=>{
      showCampusRecruitmentList()
    })
async function showCampusRecruitmentList() {
    try {
        // 发送异步请求,获得当前用户的所有记录
        let { data } = await request.get("user/findAllPostCampusRecruitment", { params: { "defaultList": user.defaultList } });
        
        // 检查返回的数据是否包含 campusRecruitmentList 属性
        if (data && data.data && data.data.campusRecruitmentList) {
            // 如果 campusRecruitmentList 存在，将其赋值给 campusRecruitment 对象的属性
            campusRecruitment.campusRecruitmentList = data.data.campusRecruitmentList;
        } else {
            // 如果 campusRecruitmentList 不存在，可以做出相应的处理，比如给出提示或者设置默认值
            console.error("未找到 campusRecruitmentList 属性");
        }
    } catch (error) {
        console.error("发生错误:", error);
    }
}


function savaInfo(index) {
      workId.campusRecruitmentId=index
      workId.employerId = campusRecruitment.campusRecruitmentList[index].employerId
      campusRecruitment.campusRecruitmentList[index].campusRecruitmentId
}

/* 导入pinia数据 */
import {defineUser} from "../store/userStore.js"
let user = defineUser()
import {useRouter} from 'vue-router'
let router = useRouter()
import {defineWorkId} from "../store/workIdStore"
let workId = defineWorkId()
function logout(){
    // 清除所有pinia数据
    user.$reset()
    // 跳转到登录页
    router.push("/homePage")
  }



</script>

<template>
    <div class="common-layout">
      
      <el-container>

        <el-header >
          <div class="icon">
        <img src="/img/14.jpg" alt="Custom Image"><br>      
        </div>
            <div class="back">
              <div class="left">
                Go ahead and search for the industry and job postings you want!
              </div>
                <el-radio-group v-model="radio1" size="large">
                
                <div class="right">
                    <router-link to="/homePage">
                      <el-radio-button style="margin-right: 5px;" label=" Home page" value=" Home page" />
                    </router-link>      
                    <router-link to="/checkProfile">        
                    <el-radio-button style="margin-left: 15px;" label=" Personal Details" value=" Personal Details" />
                  </router-link> 
                  <router-link to="/resume">
                    <el-radio-button  style="margin-left: 15px;" label="Creating Resumes" value="Creating Resumes" />
                  </router-link>
                    <router-link to="/jobAlerts">
                      <el-radio-button style="margin-left: 15px;" label="Job Alerts" value="Job Alerts" />
                    </router-link>
                    <router-link to="/applicationManagement">
                    <el-radio-button style="margin-left: 15px;" label="Application Management" value="Application Management" />
                  </router-link>
                </div>
                </el-radio-group>
            </div>

            <div class="mt-4" >
            <el-input
            v-model="input3"
            style="max-width: 600px"
            placeholder="Please input"
            class="input-with-select"
            
            >
            <template #prepend>
                <el-select v-model="select" placeholder="Select" style="width: 115px">
                <el-option label="location" value="1" />
                <el-option label="industry" value="2" />
                <el-option label="job title" value="3" />
                </el-select>
            </template>
            
            <template #append>
                <el-button  :icon="Search"  @click="SearchSomething()"></el-button> 
            </template>
            </el-input>
        </div>
        </el-header>



        <el-container class="container">
          <el-aside width="400px">
            <el-row class="tac">
                <el-col :span="12">
                  <el-text class="mx-1" type="success" size="large">Here's the career you're most comfortable with</el-text>
                  <el-menu
                    active-text-color="#ffd04b"
                    background-color="#545c64"
                    class="el-menu-vertical-demo"
                    default-active="2"
                    text-color="#fff"
                    @open="handleOpen"
                    @close="handleClose"
                  >

                    <el-sub-menu index="1">
                      <template #title>
                        <el-icon><location /></el-icon>
                        <span>Services sector</span>
                      </template>                   
                      <el-menu-item index="1-1">Guide</el-menu-item>
                      <el-menu-item index="1-1">Fitness Coach</el-menu-item>
                    </el-sub-menu>

                    <el-sub-menu index="2">
                      <template #title>
                        <el-icon><location /></el-icon>
                        <span>Internet</span>
                      </template>                   
                      <el-menu-item index="2-1">Java Engineer</el-menu-item>
                      <el-menu-item index="2-1">Web Front-end</el-menu-item>
                      <el-menu-item index="2-1">Artificial Intelligence</el-menu-item>
                    </el-sub-menu>

                    <el-sub-menu index="3">
                      <template #title>
                        <el-icon><location /></el-icon>
                        <span>Building industry</span>
                      </template>                   
                      <el-menu-item index="3-1">Civil Engineer</el-menu-item>
                      <el-menu-item index="3-1">Interior Designer</el-menu-item>
                    </el-sub-menu>

                    <el-sub-menu index="4">
                      <template #title>
                        <el-icon><location /></el-icon>
                        <span>Education</span>
                      </template>                   
                      <el-menu-item index="4-1">English Teacher</el-menu-item>
                      <el-menu-item index="3-1">Maths Teacher</el-menu-item>
                    </el-sub-menu>

                    <el-sub-menu index="5">
                      <template #title>
                        <el-icon><location /></el-icon>
                        <span>Financial industry</span>
                      </template>                   
                      <el-menu-item index="5-1">Investment Manager</el-menu-item>
                      <el-menu-item index="5-1">Financial Advisor</el-menu-item>
                    </el-sub-menu>


                    <el-sub-menu index="6">
                      <template #title>
                        <el-icon><location /></el-icon>
                        <span>Marketing/Sales</span>
                      </template>                   
                      <el-menu-item index="6-1">market planning</el-menu-item>
                      <el-menu-item index="6-1">event planning</el-menu-item>
                    </el-sub-menu>

                    <el-sub-menu index="7">
                      <template #title>
                        <el-icon><location /></el-icon>
                        <span>Administration</span>
                      </template>                   
                      <el-menu-item index="7-1">administrative director</el-menu-item>
                      <el-menu-item index="7-1">financial manager</el-menu-item>
                    </el-sub-menu>

                    <el-menu-item index="8">
                      <el-icon><icon-menu /></el-icon>
                      <span>All industries</span>
                    </el-menu-item>
                   
                   
                  </el-menu>
                </el-col>
              </el-row>
          </el-aside>
        <el-container>

            <el-main class="main">
              <div class="block text-center">
                <div class="bt">
                  We work hard with our dreams and look up to the stars.It's a job seeker's world.
                </div>
                
                <el-carousel height="500px" motion-blur>
                  <el-carousel-item v-for="(item, index) in imageList" :key="index">
                    <img class="carousel-image" :src="item.url" alt="carousel-image">
                  </el-carousel-item>
                </el-carousel>
              </div>
            </el-main>

            <el-footer class="footer">
                <div class="Pc">
                   Popular careers
                </div>
                <div class="flexbox">
                  <div class="part" v-for="campusRecruitment,index in campusRecruitment.campusRecruitmentList" :key="index">
                    <router-link to="/recruitmentDetail">
                      <el-button class="elBtu" @click="savaInfo(index)">
                       <text class="elBtu1" >{{ campusRecruitment.workingTitle }}</text>           
                      <text class="elBtu2">{{ campusRecruitment.salary }}</text> 
                       <text class="elBtu3">{{ campusRecruitment.position }}</text> 
                      </el-button>
                  </router-link>
                  </div>
                </div>
                
                <div class="container1" v-if="user.username == ''">
                  <div class="right-image">
                    <img src="/img/6.jpg" alt="Custom Image">
                  </div>
                  <div class="button-container">
                  <router-link to="/Regist">
                    <el-button color="#626aef" :dark="isDark">Regist</el-button>
                  </router-link>   
                  <router-link to="/Login">
                    <el-button color="#626aef" :dark="isDark">Login</el-button>
                  </router-link> 
                  </div>
                </div>
                <div class="container1" v-else>
                  <div class="right-image">
                    <img src="/img/6.jpg" alt="Custom Image">
                  </div>
                  <div class="userInfo">
                    Welcome <span style="color: blue;">{{ user.username }}</span> to the heaven of stargazing and dreaming
                  </div>
                  <div class="userInfo1">
                    Of course you can also take a break for a while, please select the
                  </div>
                  <div class="button-container1">
                  <el-button color="#626aef" :dark="isDark" @click="logout()">Logout</el-button>         
                  </div>

                </div>
            </el-footer>
          </el-container>
        </el-container>
      </el-container>
    </div>
  </template>
  

<style scoped>


.common-layout{

  top: 0;
  left: 0;
  width: 100%;
  height: 130px;
  background-image: url('/img/16.jpg'); /* 替换为你的图片路径 */
  background-size: cover;
  background-position: center;
  z-index: -1; /* 使图片在内容之后 */
}
.left{
    margin-left: 250px;
    margin-top: 20px;
    position:absolute;
    font-family: Lucida Sans Unicode; /* 使用 Indie Flower 艺术字体 */
  font-size: 24px; /* 设置字体大小 */
  color: #030314; /* 设置字体颜色 */
}
.right{
    margin-left: 1200px;
    margin-top: 15px;
    position: relative;
}


.mt-4{
    margin-left: 600px;
    margin-top: 20px;

}

.container{
  margin-left: 80px;
  margin-top: 80px;
  
}

.demonstration {
  color: var(--el-text-color-secondary);
}


.main{
  margin-right: 400px;
}
.carousel-image {
  width: 100%; /* 让图片宽度充满父容器 */
  height: auto; /* 根据宽度等比例缩放高度 */
  display: block; /* 让图片以块级元素显示 */
  object-fit: cover; /* 填充整个容器，保持图片的宽高比 */
}

.Pc {
        margin-bottom:10px; /* 设置图片容器顶部距离浏览器窗口顶部的距离 */
        margin-left: 650px; /* 设置图片容器右侧距离浏览器窗口右侧的距离 */
        font-family: Lucida Sans Unicode; /* 使用 Indie Flower 艺术字体 */
        font-size: 24px; /* 设置字体大小 */
        color: #030314; /* 设置字体颜色 */
      }

.bt{
  font-family: Lucida Sans Unicode; /* 使用 Indie Flower 艺术字体 */
  font-size: 24px; /* 设置字体大小 */
  color: #030314; /* 设置字体颜色 */
}
.elBtu{
  position: relative;
  display: block; /* 将按钮设置为块级元素 */
  width: 350px; /* 设置按钮宽度 */
  height: 150px; /* 设置按钮高度 */
  background-color: rgb(133, 219, 191);

}
.elBtu1, .elBtu2, .elBtu3 {
  position: absolute; /* 将内部按钮定位设置为绝对定位 */
}


.elBtu1 {
  top: 20px; /* 距离父元素顶部的距离 */
  left: 35px; /* 距离父元素左侧的距离 */
  color: rgb(27, 31, 27); /* 设置文本颜色为黑色 */
  font-weight: bold; /* 设置文本粗体 */
  font-size: 22px; /* 设置字体大小 */
  white-space: nowrap; /* 不换行 */
  overflow: hidden; /* 隐藏溢出部分 */
  text-overflow: ellipsis; /* 显示省略号 */
  max-width: 300px;
  
}
.elBtu1:hover {
  white-space: normal; /* 鼠标悬停时显示全部内容 */
}

.elBtu2 {
  top: 70px;
  left: 100px;
  color: rgb(107, 99, 218); /* 设置文本颜色为红色 */
  font-style: italic; /* 设置文本为斜体 */
  font-size: 20px; /* 设置字体大小 */

}


.elBtu3 {
  top: 103px; /* 距离父元素底部的距离 */
  right: 110px; /* 距离父元素右侧的距离 */
  color: rgb(3, 65, 44); /* 设置文本颜色为白色 */
  font-weight: bold; /* 设置文本粗体 */
  font-size: 20px; /* 设置字体大小 */
  
}


.flexbox {
  display: flex;
  flex-wrap: wrap; /* 当容器宽度不足时，换行显示 */
}

.part {
  flex: 0 0 calc(33.33% - 110px); /* 每行最多显示三个小块，每个小块占据 1/3 的宽度 */
  margin-right: 6px; /* 设置小块之间的间距 */
  margin-bottom: 30px; /* 设置小块下方的间距 */
}
.container {
  position: relative; /* 设置容器为相对定位 */
}
.right-image{

  position: fixed; /* 将图片固定在页面上 */
  top: 160px; /* 设置图片距离页面顶部的距离 */
  right: 50px; /* 设置图片距离页面左侧的距离 */
}
.right-image img {
        width: 300px; /* 设置图片宽度 */
        height: 400px; /* 根据宽度等比例缩放高度 */
      }
.button-container {
  position: fixed; /* 将按钮容器固定在页面上 */
  top: 520px; /* 设置按钮容器距离页面顶部的距离（图片距离顶部距离 + 图片高度 + 间距） */
  right: 100px; /* 设置按钮容器距离页面左侧的距离 */
}
.button-container1 {
  position: fixed; /* 将按钮容器固定在页面上 */
  top: 690px; /* 设置按钮容器距离页面顶部的距离（图片距离顶部距离 + 图片高度 + 间距） */
  right: 70px; /* 设置按钮容器距离页面左侧的距离 */
}
.button-container button {
  margin-right: 10px; /* 设置按钮之间的间距 */
}
.userInfo{
  position: fixed; /* 将按钮容器固定在页面上 */
  top: 500px; /* 设置按钮容器距离页面顶部的距离（图片距离顶部距离 + 图片高度 + 间距） */
  right: 60px; /* 设置按钮容器距离页面左侧的距离 */
  white-space: pre-wrap; /* 允许在单词内部和文本行尾换行 */
  width: 250px; /* 可根据需要设置宽度 */
  font-family: Lucida Sans Unicode; /* 使用 Indie Flower 艺术字体 */
  font-size: 20px; /* 设置字体大小 */
  color: #030314; /* 设置字体颜色 */
}

.userInfo1{
  position: fixed; /* 将按钮容器固定在页面上 */
  top: 630px; /* 设置按钮容器距离页面顶部的距离（图片距离顶部距离 + 图片高度 + 间距） */
  right: 60px; /* 设置按钮容器距离页面左侧的距离 */
  white-space: pre-wrap; /* 允许在单词内部和文本行尾换行 */
  width: 250px; /* 可根据需要设置宽度 */
  font-family: Lucida Sans Unicode; /* 使用 Indie Flower 艺术字体 */
  font-size: 20px; /* 设置字体大小 */
  color: #030314; /* 设置字体颜色 */
}


.icon{
        left:120px; /* 调整图标距离左边的位置 */
        top: 20px; /* 调整图标距离顶部的位置 */
        position: absolute;
        border: none !important;
        
    }

.icon img {
width: 50px; /* 设置图片宽度 */
height: 50px; /* 根据宽度等比例缩放高度 */
border-radius: 40px 10px ; /* 将边框角变为圆形 */
}
.input-with-select .el-input-group__prepend {
  background-color: var(--el-fill-color-blank);
}
</style>
