
<script setup>
import {ref,onMounted,reactive} from 'vue'
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
const imageList = ref([
  { url: '/img/2.jpg' },
  { url: '/img/3.jpg' },
  { url: '/img/4.jpg' },
  { url: '/img/1.jpg' }
]);



/* 导入pinia数据 */
import {defineEUser} from "../store/eUserStore.js"
let eUser = defineEUser()
import {defineCampusRecruitment} from "../store/campusRecruitmentStore.js"
let campusRecruitment = defineCampusRecruitment()
import {defineWorkId} from "../store/workIdStore"
let workId = defineWorkId()

import { defineCompanyName } from '../store/companyNameStore';
let companyName1 = defineCompanyName()
    //挂载完毕后,立刻查询信息,赋值给pinia
    onMounted(async ()=>{
      showCampusRecruitmentList()
      findCompanyByEmployerId()
      console.log(company.companyId)
    })
    // 查询当前用户所有信息 并展示到视图的方法

    async function showCampusRecruitmentList(){
        // 发送异步请求,获得当前用户的所有记录
        let {data} = await request.get("eUser/findAllPostCampusRecruitment",{params:{"employerId":eUser.employerId}})
        campusRecruitment.campusRecruitmentList = data.data.campusRecruitmentList ;
    }
    async function findCompanyByEmployerId(){
        let {data}  = await request.get("eUser/findCompanyByEmployerId",{params:{"employerId":eUser.employerId}})
        if (data && data.data && data.data.company) {  
          companyName1.companyName =  data.data.company.companyName|| '';
          company.companyId = data.data.company.companyId || '';
        
        form.companyName = data.data.company.companyName|| '';
        form.companyAddress = data.data.company.companyAddress|| '';
        form.specificAddress = data.data.company.specificAddress|| '';
        form.companyDescription = data.data.company.companyDescription|| '';
        form.companyCulture = data.data.company.companyCulture|| '';
        form.jobOpportunities = data.data.company.jobOpportunities|| '';
      }
    }
function savaInfo(index) {
      workId.campusRecruitmentId=index
      workId.employerId = campusRecruitment.campusRecruitmentList[index].employerId
      campusRecruitment.campusRecruitmentList[index].campusRecruitmentId
}

import {useRouter} from 'vue-router'
let router = useRouter()

function logout(){
    // 清除所有pinia数据
    eUser.$reset()
    workId.$reset()
    company.$reset()
    // 跳转到登录页
    router.push("/EUserHomePage")
  }

  const input3 = ref('')



  //----------公司小弹窗-------------

const dialogFormVisible = ref(false)
const formLabelWidth = '140px'

const form = reactive({
  companyName: '',
  companyAddress: '',
  specificAddress: '',
  companyDescription: '',
  companyCulture: '',
  jobOpportunities:''
})

const formSize = ref('default')
const ruleFormRef = ref()
const rules = reactive({
    companyName: [
    { required: true, message: 'Please input Company Name', trigger: 'blur' },
    { min: 1, max: 50, message: 'Length should be 1 to 50', trigger: 'blur' },
  ],
  companyAddress: [
    {
      required: true,
      message: 'Please select Type of Company Address',
      trigger: 'change',
    },
     ],
     specificAddress: [
    { required: true, message: 'Please input Specific Address' , trigger: 'blur' },
    ],

    companyDescription: [
    { required: true, message: 'Please input Company Description' , trigger: 'blur' },
    ],
    companyCulture: [
    { required: true, message: 'Please input Company Culture', trigger: 'blur' },
  ],
    jobOpportunities: [
    { required: true, message: 'Please input Job Opportunities', trigger: 'blur' },
  ],
})

import {defineCompany} from "../store/companyStore"
import { defineCompanyReviewList } from '../store/companyReviewListStore'
let company = defineCompany()

const submitForm = async (formEl) => {
  if (!formEl) return
  await formEl.validate(async(valid) => {
    if (valid) {
      console.log('submit!')
      let form1 ={
        companyName:form.companyName,
        companyAddress: form.companyAddress,
        specificAddress: form.specificAddress,
        companyDescription: form.companyDescription,
        companyCulture: form.companyCulture,
        employerId:eUser.employerId,
        jobOpportunities:form.jobOpportunities
        }
        let response;
        let {data} = await request.get("eUser/findCompanyByEmployerId",{params:{"employerId":eUser.employerId}})  
        if (data && data.data && data.data.company) {   
          if(data.data.company.companyName == form.companyName&&data.data.company.companyAddress == form.companyAddress&&
              data.data.company.specificAddress ==form.specificAddress&&data.data.company.companyDescription == form.companyDescription&&
              data.data.company.companyCulture == form.companyCulture && data.data.company.jobOpportunities == form.jobOpportunities){
                alert("You haven't modified any information yet")
                 return
              }
              if(company.companyId == 0){        
                response== await request.post("eUser/saveCompanyInfo",form1)                      
                  alert("Submit Successful")
                  router.push("/eUserHomePage")      
                }else{            
                  response== await request.post("eUser/updateCompanyInfo",form1)
                  alert("Update Successful")          
                  router.push("/eUserHomePage")
              }
           }else{
            response== await request.post("eUser/saveCompanyInfo",form1)                      
            alert("Submit Successful")
            router.push("/eUserHomePage")    
           }

      
    } else {
      return alert("Failed to Submit")
    }
  })
}

const resetForm = (formEl) => {
  if (!formEl) return
  formEl.resetFields()
}
import { defineSearchByIndustry } from '../store/searchByIndustry';
let searchByIndustry = defineSearchByIndustry()
async function SearchCandidate(){
  console.log(select.value)
  if(select.value == 1){
    let {data} = await request.get("eUser/getProfileByWorkExperience",{params:{"workExperience":input3.value}})
    searchByIndustry.searchByIndustryList = data.data.searchByWorkExperience
    router.push("/searchByEmployer")
  }else if(select.value == 2){
    let {data} = await request.get("eUser/getProfileByEducation",{params:{"education":input3.value}})
    searchByIndustry.searchByIndustryList = data.data.searchByWorkEducation
    router.push("/searchByEmployer")
  }else if(select.value == 3){
    let {data} = await request.get("eUser/getProfileByAge",{params:{"age":input3.value}})
    searchByIndustry.searchByIndustryList = data.data.searchByAge
    router.push("/searchByEmployer")
  }else if(select.value == 4){
    let {data} = await request.get("eUser/getProfileByPosition",{params:{"expectedPosition":input3.value}})
    searchByIndustry.searchByIndustryList = data.data.searchByPosition
    router.push("/searchByEmployer")
  }else{
    window.alert("Sorry, your search was incorrect.")
  }
 
}



const options = Array.from({ length: 10000 }).map((_, idx) => ({
  value: `${idx + 1}`,
  label: `${idx + 1}`,
}))
</script>

<template>
    <div class="common-layout">
      <el-container>

        <el-header >
            <div class="back">
                <el-radio-group v-model="radio1" size="large">
                <div class="left">
                  <router-link to="/campusRecruitment">
                    <el-radio-button  class="btu1" label="Post a position" value="Post Campus Recruitment" />
                  </router-link>
                </div>
                <div class="right">
                    <router-link to="/EUserHomePage">
                      <el-radio-button label=" home page" value=" home page" />
                    </router-link>      
                    <el-radio-button plain  @click="dialogFormVisible = true" label=" Company Profile" value=" Company Profile" />
                    <router-link to="/applicantTracking">
                      <el-radio-button label="Applicant Tracking" value="Applicant Tracking" />
                    </router-link>
                    
                    
                </div>
                </el-radio-group>
            </div>
            
              
              <div class="mt-4" >
                Hope you receive quality talent!
               <el-input
                v-model="input3"
                style="max-width: 600px"
                placeholder="Please input"
                class="input-with-select"
                
                >
                <template #prepend>
                    <el-select v-model="select" placeholder="Select" style="width: 115px">
                    <el-option label="Work Experience" value="1" />
                    <el-option label="Education" value="2" />
                    <el-option label="Age" value="3" />
                    <el-option label="Position" value="4" />
                    </el-select>
                </template>
                
                <template #append>
                    <el-button  :icon="Search"  @click="SearchCandidate()"></el-button> 
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
                  We work hard with our dreams and look up to the stars.This is a recruiter's world.
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
                  Below are your self-posted positions:
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
                
                <div class="container1" v-if="eUser.username == ''">
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
                    Welcome <span style="color: blue;">{{ eUser.username }}</span> to the heaven of stargazing and dreaming
                  </div>
                  <div class="userInfo1">
                    Of course you can also take a break for a while, please select the
                  </div>
                  <div class="button-container1">
                  <el-button color="#626aef" :dark="isDark" @click="logout()">Logout</el-button>         
                  </div>
                  <div>
                    <el-dialog v-model="dialogFormVisible" title="Add Company Details" width="500"  ref="ruleFormRef" :rules="rules">
                      <el-form ref="ruleFormRef"
                        style="max-width: 600px"
                        :model="form"
                        :rules="rules"
                        label-width="auto"
                        class="demo-ruleForm"
                        :size="formSize"
                        status-icon>

                        <el-form-item label="Company name" prop="companyName" :label-width="formLabelWidth" >
                          <el-input v-model="form.companyName" autocomplete="off" />
                        </el-form-item>

                        <el-form-item label="Address" prop="companyAddress" :label-width="formLabelWidth">
                          <el-select v-model="form.companyAddress" placeholder="Company Address">
                            <el-option label="shanghai" value="shanghai" />
                            <el-option label="beijing" value="beijing" />
                            <el-option label="chengdu" value="chengdu" />
                            <el-option label="shenzhen" value="shenzhen" />
                          </el-select>
                        </el-form-item>

                        <el-form-item label="Specific address"  prop="specificAddress"  :label-width="formLabelWidth">
                          <el-input v-model="form.specificAddress" autocomplete="off" />
                          </el-form-item>

                          <el-form-item label="Description" prop="companyDescription" :label-width="formLabelWidth">
                          <el-input v-model="form.companyDescription" autocomplete="off" />
                          </el-form-item>

                          <el-form-item label="Company Culture" prop="companyCulture" :label-width="formLabelWidth">
                          <el-input v-model="form.companyCulture" autocomplete="off" />
                          </el-form-item>

                          <el-form-item label="Job Opportunity" prop="jobOpportunities" :label-width="formLabelWidth">
                          <el-input v-model="form.jobOpportunities" autocomplete="off" />
                          </el-form-item>

                          
                      </el-form>
                      <template #footer>
                        <div class="dialog-footer">
                          <el-button style="margin-left: 100px;" type="primary" @click="submitForm(ruleFormRef)">
                          Submit
                         </el-button>
                        <router-link to="/companyDetail">
                          <el-button style="margin-left: 100px;" type="primary" >View Details</el-button>
                        </router-link>
                         

                       <el-button @click="resetForm(ruleFormRef)">Reset</el-button>
                       
                        </div>
                      </template>
                    </el-dialog>
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

background-size: cover;
background-position: center;
z-index: -1; /* 使图片在内容之后 */
}
.left{
    margin-left: 550px;
    margin-top: 15px;

}
.right{
    margin-left: 1500px;
    margin-top: 15px;
}
.back{
    background-color: rgb(162, 224, 190);
}

.mt-4{
  margin-left :700px;
  margin-top: 20px;
  font-family: Lucida Sans Unicode; /* 使用 Indie Flower 艺术字体 */
  font-size: 24px; /* 设置字体大小 */
  color: #030314; /* 设置字体颜色 */
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
        margin-left: 450px; /* 设置图片容器右侧距离浏览器窗口右侧的距离 */
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
  background-color: rgb(133, 219, 191) ;

}
/* rgb(133, 219, 191) */
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

</style>
