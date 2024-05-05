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
          <div v-if="applicationManagement.applicationManagementList == ''" class="beforeContent">
              <text>You haven't applied for any positions yet, go ahead and apply!</text>
           <div class="image">
            <img src='/img/10.jpg' alt="back">
          </div>
          </div>
          <div class="content" v-else>

                <div>
                    <el-descriptions
                        title="---------"
                        direction="vertical"
                        :column="4"
                        :size="size"
                        border
                        v-for="applicationManagement,index in applicationManagement.applicationManagementList" :key="index"
                        
                    >
                  
                        <el-descriptions-item label="Job title" :span="1">{{ campusRecruitment.campusRecruitmentList[index].workingTitle }}</el-descriptions-item>
                        <el-descriptions-item label="Application progress">{{applicationManagement.status}}</el-descriptions-item>
                        
                        <el-descriptions-item label="Position" :span="2">{{ campusRecruitment.campusRecruitmentList[index].position }}</el-descriptions-item>
                        <el-descriptions-item label="Number of recruits" :span="1"
                        >{{ campusRecruitment.campusRecruitmentList[index].count }}
                        </el-descriptions-item>
                        <el-descriptions-item label="Location" >{{ campusRecruitment.campusRecruitmentList[index].workCity }}</el-descriptions-item>
                        <el-descriptions-item label="Click for details & Removing" >
                          <router-link to="/recruitmentDetail" >
                            <el-button type="info" round @click="savaInfo(index)">Details</el-button>
                          </router-link>
                          ~~~
                            <el-button type="info" round @click="removing(index)">Removing</el-button>
                          ~~~
                          <!-- <el-button plain @click="dialogTableVisible = true">
                            Open a Table nested Dialog
                          </el-button> -->
                          <el-button type="success" plain @click="showDialog1(applicationManagement.applicationManagementId)" label="View interview details" value="View interview details" >View interview details</el-button>
                          
                        </el-descriptions-item>
                        
                    </el-descriptions>
                    <div>
                      <el-dialog v-model="dialogTableVisible" title="" width="800">
                        <el-descriptions title="View interview details" :column="3" border>
                            <el-descriptions-item
                              label="Location"
                              label-align="right"
                              align="center"
                              label-class-name="my-label"
                              class-name="my-content"
                              width="150px"
                            >
                              {{form2.location}}
                            </el-descriptions-item>
                            <el-descriptions-item label="Time" label-align="right" align="center">
                              {{form2.time}}
                            </el-descriptions-item>     
                            <el-descriptions-item label="Tips" label-align="right" align="center">
                              {{form2.tips}}
                            </el-descriptions-item>
                          </el-descriptions>
                        </el-dialog>
                      </div>
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
  import { defineCampusRecruitment } from '../store/campusRecruitmentStore'
  let campusRecruitment =defineCampusRecruitment()
let jobAlerts = defineJobAlerts()
let workId = defineWorkId()
  const router = useRouter()
  const select = ref('')
  
  // import {defineApplicationManagement} from '../store/applicationManagement'
  // let applicationManagement = defineApplicationManagement()
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

import { defineApplicationManagement } from '../store/applicationManagement';
let applicationManagement = defineApplicationManagement()



onMounted(async ()=>{
     applicationManagement1();
     
})
async function applicationManagement1(){
  let {data} = await request.get("user/getAllApplication",{params:{"jobSeekerId":user.jobSeekerId}})
      applicationManagement.applicationManagementList = data.data.applicationManagementList
      
      applicationManagement.applicationManagementList.forEach(async (item, index) => {
        
        let response = await request.get("user/getAllCampusRecruitmentByCampusRecruitmentId", { params: { "campusRecruitmentId": item.campusRecruitmentId } });

        
        campusRecruitment.campusRecruitmentList[index] = response.data.data.campusRecruitment;
      });
      console.log(campusRecruitment.campusRecruitmentList)

}

const status = ref(0)
function savaInfo(index) {
      workId.campusRecruitmentId=index
      workId.employerId = applicationManagement.applicationManagementList[index].employerId
}

async function removing(index){
  let {data} = await request.get("user/removeApply",{params:{"campusRecruitmentId":campusRecruitment.campusRecruitmentList[index].campusRecruitmentId}})
  if(data.code == 200){
    alert("Deleted successfully")
  }
}
let hid=ref(0)
const dialogTableVisible = ref(false)
const form2 = reactive({
  location:'',
  time:'',
  tips:''
})
async function showDialog1(id) {
  dialogTableVisible.value = true;
  hid.value = id;
  let {data} = await request.get("eUser/findInterviewDetails",{params:{"applicationManagementId":hid.value}})
  if (data && data.data && data.data.interviewDetails) {
    form2.location  =  data.data.interviewDetails.location;
    form2.time = data.data.interviewDetails.time;
    form2.tips = data.data.interviewDetails.tips;
  }else{
    form2.location  =  'No feedback yet.';
    form2.time = 'No feedback yet.';
    form2.tips = 'No feedback yet.';
  }
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

.beforeContent{
  margin-top: 200px;
  margin-left: 400px;
  font-family: Lucida Sans Unicode; /* 使用 Indie Flower 艺术字体 */
  font-size: 30px; /* 设置字体大小 */
  color: #030314; /* 设置字体颜色 */
}

.image {
    /* border: 1px solid black; */
    width: 1726px; /* 设置容器宽度 */
    height: 850px; /* 设置容器高度 */
    overflow: hidden; /* 隐藏溢出的部分，以防止图片超出边框 */
    margin-left: 10px;
}

img {
    display: block;
    width: 100%; /* 让图片宽度充满容器 */
    height: 100%; /* 让图片高度充满容器 */
}
:deep(.my-label) {
  background: var(--el-color-success-light-9) !important;
}
:deep(.my-content) {
  background: var(--el-color-danger-light-9);
}
  </style>