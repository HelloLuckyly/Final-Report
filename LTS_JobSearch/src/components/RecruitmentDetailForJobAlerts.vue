<script setup>

import { Search } from '@element-plus/icons-vue'
import{reactive,ref,onMounted} from 'vue'
const select = ref('')
import request from'../utils/request'
import {useRouter} from 'vue-router'
let router = useRouter()
import {defineCampusRecruitment} from "../store/campusRecruitmentStore.js"
let campusRecruitment = defineCampusRecruitment()
import {defineWorkId} from "../store/workIdStore"
let workId = defineWorkId()

import {defineEUser} from "../store/eUserStore.js"
let eUser = defineEUser()
import {defineUser} from "../store/userStore.js"
let user = defineUser()
import { defineJobAlerts } from '../store/jobAlertsStore';
let jobAlerts = defineJobAlerts()


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

onMounted(async ()=>{
      findEmployerNameByEmployerId();
      findCompanyNameByEmployerId();
    })
const EmployerName = ref('');
const companyName = ref('')
async function findEmployerNameByEmployerId(){
        let {data} = await request.get("user/findEmployerNameByEmployerId",{params:{"employerId":workId.employerId}})
            console.log(data.data.employer.username)
            console.log(EmployerName)
            EmployerName.value = data.data.employer.username
    }


async function findCompanyNameByEmployerId(){   
        let {data} = await request.get("user/findCompanyNameByEmployerId",{params:{"employerId":workId.employerId}})
        companyName.value = data.data.company.companyName
    }






import { computed} from 'vue'

const size = ref('default')
const blockMargin = computed(() => {
  const marginMap = {
    large: '32px'
  }
  return {
    marginTop: marginMap.large
    // [size.value] || marginMap.default,
  }
})
import {defineJobAlertById} from '../store/jobAlertByIdStore.js'
let jobAlertById = defineJobAlertById()
async function SendApply() {
  let response = await request.get("user/getAllApplication", { params: { "jobSeekerId": user.jobSeekerId } });

  let apply = {
    jobSeekerId: user.jobSeekerId,
    employerId: workId.employerId,
    campusRecruitmentId: jobAlertById.campusRecruitmentId,
    status: 'under review'
  };

  // 检查是否已经应用过相同的 campusRecruitmentId
  let hasApplied = response.data.data.applicationManagementList.some(application => {
    return application.campusRecruitmentId === apply.campusRecruitmentId;
  });

  // 如果已经应用过，则提示用户并返回
  if (hasApplied) {
    alert("You have already applied, no need to apply again.");
    return;
  }

  // 发送申请
  let { data } = await request.post("user/sendApply", apply);
  if (data.code === 200) {
    alert("Request successful!");
  }
}
</script>

<template>
    <div  class="common-layout">

        <el-header >
          <div class="icon">
        <img src="/img/14.jpg" alt="Custom Image"><br>      
        </div>



            <div class="back" v-if="eUser.username == ''">
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

            <div class="back" v-else>
                <el-radio-group v-model="radio1" size="large">
                <div class="left">
                  <router-link to="/campusRecruitment">
                    <el-radio-button class="btu1" label="Post Campus Recruitment" value="Post Campus Recruitment" />
                  </router-link>
                    
                    <el-radio-button label="Post Recruitment for Government and Enterprises" value="Post Recruitment for Government and Enterprises" />
                    <el-radio-button label="Post Overseas Recruitment" value="Post Overseas Recruitment" />
                </div>
                <div class="right">
                    <router-link to="/EUserHomePage">
                      <el-radio-button label=" home page" value=" home page" />
                    </router-link>      
                    
  
                    <el-radio-button label="Applicant Tracking" value="Applicant Tracking" />
                    <el-radio-button label="Communication Tools" value="Communication Tools" />
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
            <div class="main">
                <el-descriptions                  
                    :column="4"
                    :size="horizontal"
                    direction="vertical"
                    :style="blockMargin"
                >
                    <el-descriptions-item label="WorkingTitle" :span="1" >{{jobAlerts.jobAlertsList[workId.campusRecruitmentId].workingTitle}}</el-descriptions-item>
                    <el-descriptions-item label="Salary and Benefits" :span="2">{{jobAlerts.jobAlertsList[workId.campusRecruitmentId].salary}}</el-descriptions-item>
                    <el-descriptions-item label="Academic Qualifications" :span="2">{{jobAlerts.jobAlertsList[workId.campusRecruitmentId].education}}</el-descriptions-item>
                    <el-descriptions-item label="Address"
                    >{{jobAlerts.jobAlertsList[workId.campusRecruitmentId].addressDetails}}
                    </el-descriptions-item>
                    <el-descriptions-item label="Work City" :span="2">{{jobAlerts.jobAlertsList[workId.campusRecruitmentId].workCity}}</el-descriptions-item>
                    <el-descriptions-item label="Number of Recruits" :span="2">{{jobAlerts.jobAlertsList[workId.campusRecruitmentId].count}}</el-descriptions-item>
                  
                </el-descriptions>
                
                <div style="margin-top: 30px;">
                    <el-divider content-position="left" class="bold-text">Job Description</el-divider>

                    <el-descriptions :column="1" border>
                    
                        <el-descriptions-item label="Type of Position" label-align="center" align="center">
                            {{jobAlerts.jobAlertsList[workId.campusRecruitmentId].position}}
                        </el-descriptions-item>
                        <el-descriptions-item label="Type of Industry" label-align="center" align="center">
                            {{jobAlerts.jobAlertsList[workId.campusRecruitmentId].industry}}
                        </el-descriptions-item>
                        <el-descriptions-item label="Work Content" label-align="center" align="center">
                            {{jobAlerts.jobAlertsList[workId.campusRecruitmentId].workContent}}
                        </el-descriptions-item>
                        <el-descriptions-item label="Job Requirements" label-align="center" align="center">
                            {{jobAlerts.jobAlertsList[workId.campusRecruitmentId].jobRequirements}}
                        </el-descriptions-item>
                        <el-descriptions-item label="Work Experience Requirements" label-align="center" align="center">
                            {{jobAlerts.jobAlertsList[workId.campusRecruitmentId].experience}}
                        </el-descriptions-item>
                    </el-descriptions>
                </div>
                Job Poster :{{EmployerName}}<br>
                Company:{{ companyName }}
                <br>
                <div v-if="eUser.username == ''">
                  Click for company details->
                <router-link to="/companyDetailForUser">
                <el-button type="primary" @click="Detail()" >Detail</el-button>
              </router-link>
                </div>
                
                
               <div class="hua"  v-if="eUser.username == ''">
                The opportunity is right before you, do not let it slip away!
               </div>
                <div class="application" v-if="eUser.username == ''">
                  
                  <el-button type="primary" @click="SendApply()"  size="large">apply for a position</el-button>
                </div>
               <div>
                    
               </div>

            </div>
    </div>
        

</template>

<style scoped>
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
.el-descriptions {
  margin-top: 20px;
}
.main{
    margin-left: 100px;
    margin-top: 100px;
}
.carousel-image {
  width: 100%; /* 让图片宽度充满父容器 */
  height: auto; /* 根据宽度等比例缩放高度 */
  display: block; /* 让图片以块级元素显示 */
  object-fit: cover; /* 填充整个容器，保持图片的宽高比 */
}

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
.hua{
  margin-left :1300px;
  margin-top: 10px;
  font-family: Lucida Sans Unicode; /* 使用 Indie Flower 艺术字体 */
  font-size: 26px; /* 设置字体大小 */
  color: #030314; /* 设置字体颜色 */
  position: relative;
}
.application{
position:absolute;
right: 240px;
top: 640px;
}
</style>