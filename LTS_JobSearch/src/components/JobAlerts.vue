<template>
    <div class="profile">
      <el-header>
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
          <div class="content">
            <div>
                Are job alerts turned on?
            </div>
                <div>
                    <el-switch
                        v-model="value1"
                        :active-action-icon="View"
                        :inactive-action-icon="Hide"
                        @change="handleSwitchChange"
                    />

                </div>
                <div v-if="value1 ==true">
                    <el-descriptions
                        title="Here are the new jobs that fit you:"
                        direction="vertical"
                        :column="4"
                        :size="size"
                        border
                        v-for="jobAlerts,index in jobAlerts.jobAlertsList" :key="index"
                    >
                        <el-descriptions-item label="Job title" :span="1">{{jobAlerts.workingTitle}}</el-descriptions-item>
                        <el-descriptions-item label="Salary">{{jobAlerts.salary}}</el-descriptions-item>
                        <el-descriptions-item label="Position" :span="2">{{jobAlerts.position}}</el-descriptions-item>
                        <el-descriptions-item label="Educational Requirements" :span="1"
                        >{{jobAlerts.education}}
                        </el-descriptions-item>
                        <el-descriptions-item label="Location" >{{jobAlerts.workCity}}</el-descriptions-item>
                        <el-descriptions-item label="Click for details" >
                          <router-link to="/recruitmentDetailForJobAlerts" >
                            <el-button type="info" round @click="savaInfo(index,jobAlerts.campusRecruitmentId)">Details</el-button>
                          </router-link>
                          
                        </el-descriptions-item>
                    </el-descriptions>
                </div>
                <div v-else>
                    <h1>Please turn on job alerts</h1>
                </div>
          

          </div>
      
  
    </div>
    
  </template>
  
  <script setup >
  import { reactive, ref,onMounted } from 'vue'
  import request from'../utils/request'
  import { Search,Hide, View } from '@element-plus/icons-vue'
  import {useRouter} from 'vue-router'
  import { defineWorkId } from '../store/workIdStore'
  import {defineJobAlerts} from '../store/jobAlertsStore'
let jobAlerts = defineJobAlerts()
let workId = defineWorkId()
  const router = useRouter()
  const select = ref('')
  
  /* 导入pinia数据 */
  import {defineUser} from "../store/userStore.js"
  let user = defineUser()
  
  import {computed} from 'vue'

const size = ref('default')
const blockMargin = computed(() => {
  const marginMap = {
    large: '32px'
  }
  return {
    marginTop: marginMap.large,
  }
})
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
  let response
const value1 = ref(true)
async function handleSwitchChange(){
  let form ={
        jobSeekerId:user.jobSeekerId,
        mode:1,
        startingTime:(new Date()).toISOString().substring(0, 19)
        }

  console.log(value1)
  if(value1.value == true){
    console.log(value1)
    response = await request.get("user/checkJobAlerts",{params:{"jobSeekerId":user.jobSeekerId}})

    if(response.data.jobAlerts != null){
      let {data}  =  await request.post("user/getJobAlerts",{params:{"jobSeekerId":user.jobSeekerId}})
      jobAlerts.jobAlertsList = data.data.jobAlertsList
    }
   
    let {data}  =  await request.post("user/addJobAlerts",form)
    jobAlerts.jobAlertsList = data.data.jobAlertsList
  }else{
    console.log(value1)
      let {data} = await request.get("user/removeJobAlerts",{params:{"jobSeekerId":user.jobSeekerId}})
      if(data.code==200){
        alert("Successful cancellation and you will not be alerted to the job")
      }
  }
}
onMounted(async ()=>{
     checkJobAlerts();
     
})

async  function checkJobAlerts(){
  let {data} = await request.get("user/checkJobAlerts",{params:{"jobSeekerId":user.jobSeekerId}})
        if(data.data.jobAlerts !=null){
            value1.value = true
            let {data}  =  await request.get("user/getJobAlerts",{params:{"jobSeekerId":user.jobSeekerId}})
             jobAlerts.jobAlertsList = data.data.jobAlertsList
        }else{
          value1.value = false
        }      
    }

import {defineJobAlertById} from '../store/jobAlertByIdStore.js'
let jobAlertById = defineJobAlertById()
function savaInfo(index,id) {
      workId.campusRecruitmentId=index
      workId.employerId = jobAlerts.jobAlertsList[index].employerId
      jobAlertById.campusRecruitmentId = id
}

  </script>
  <style  scoped>
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
  .profile{
  
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
  /* ----------------------- */
  .content{
    margin-top: 130px;
    margin-left: 300px;
  }
  
  .fBtu{
    margin-left: 280px;
  }
  .el-descriptions {
  margin-top: 20px;
}



  </style>