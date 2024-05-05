<script setup>



import { Search } from '@element-plus/icons-vue'
import{ref,reactive,computed,onMounted,onUnmounted} from'vue'
import { defineEUser } from '../store/eUserStore'; 
import request from'../utils/request'
import {useRouter} from 'vue-router'
import {defineCompanyName} from '../store/companyNameStore'
    let companyName = defineCompanyName()

    import {defineCompany} from '../store/companyStore'
    let company2 = defineCompany()
    import {defineCompanyReviewList} from '../store/companyReviewListStore'
let companyReviewList = defineCompanyReviewList()

import { defineEmployerNameList } from '../store/employerNameStore';
let employerNameList = defineEmployerNameList()

let router = useRouter()
let eUser = defineEUser()
const select = ref('')

const size = ref('default')
const blockMargin = computed(() => {
  const marginMap = {
    large: '32px',
    default: '28px',
    small: '24px',
  }
  return {
    marginTop: marginMap[size.value] || marginMap.default,
  }
})


import { defineSearchByIndustry } from '../store/searchByIndustry';
let searchByIndustry = defineSearchByIndustry()
const input3 = ref('')
let response;
async function SearchCandidate(){
  console.log(select.value)
  if(select.value == 1){
    let {data} = await request.get("eUser/getProfileByWorkExperience",{params:{"workExperience":input3.value}})
    searchByIndustry.searchByIndustryList = data.data.searchByWorkExperience
    const jobSeekerIds = searchByIndustry.searchByIndustryList.map(item => item.jobSeekerId);

    // 发送所有的异步请求获取用户名
    const responses = await Promise.all(jobSeekerIds.map(async (jobSeekerId) => {
        const response = await request.get("user/getUserNameById", { params: { "jobSeekerId": jobSeekerId } });
        return response.data.data.jobSeeker.username;
    }));

    // 将用户名添加到 employerNameList
    employerNameList.employerNameList.push(...responses);
    router.push("/searchByEmployer")
  }else if(select.value == 2){
    let {data} = await request.get("eUser/getProfileByEducation",{params:{"education":input3.value}})
     
    console.log(searchByIndustry.searchByIndustryList)
    searchByIndustry.searchByIndustryList = data.data.searchByWorkEducation
    console.log(searchByIndustry.searchByIndustryList)
    const jobSeekerIds = searchByIndustry.searchByIndustryList.map(item => item.jobSeekerId);

    // 发送所有的异步请求获取用户名
    const responses = await Promise.all(jobSeekerIds.map(async (jobSeekerId) => {
        const response = await request.get("user/getUserNameById", { params: { "jobSeekerId": jobSeekerId } });
        return response.data.data.jobSeeker.username;
    }));

    // 将用户名添加到 employerNameList
    employerNameList.employerNameList.push(...responses);
    router.push("/searchByEmployer")
  }else if(select.value == 3){
    let {data} = await request.get("eUser/getProfileByAge",{params:{"age":input3.value}})
    searchByIndustry.searchByIndustryList = data.data.searchByAge
    // 提取所有的 jobSeekerId
    const jobSeekerIds = searchByIndustry.searchByIndustryList.map(item => item.jobSeekerId);

    // 发送所有的异步请求获取用户名
    const responses = await Promise.all(jobSeekerIds.map(async (jobSeekerId) => {
        const response = await request.get("user/getUserNameById", { params: { "jobSeekerId": jobSeekerId } });
        return response.data.data.jobSeeker.username;
    }));

    // 将用户名添加到 employerNameList
    employerNameList.employerNameList.push(...responses);
    router.push("/searchByEmployer")
  }else if(select.value == 4){
    let {data} = await request.get("eUser/getProfileByPosition",{params:{"expectedPosition":input3.value}})
    searchByIndustry.searchByIndustryList = data.data.searchByPosition
    const jobSeekerIds = searchByIndustry.searchByIndustryList.map(item => item.jobSeekerId);

    // 发送所有的异步请求获取用户名
    const responses = await Promise.all(jobSeekerIds.map(async (jobSeekerId) => {
        const response = await request.get("user/getUserNameById", { params: { "jobSeekerId": jobSeekerId } });
        return response.data.data.jobSeeker.username;
    }));

    // 将用户名添加到 employerNameList
    employerNameList.employerNameList.push(...responses);
    router.push("/searchByEmployer")
  }else{
    window.alert("Sorry, your search was incorrect.")
  }
 
}


 





</script>

<template>
    <div class ="common-layout">
        <el-header >
            <div class="back">
                <el-radio-group v-model="radio1" size="large">
                  <div class="left">
                  <router-link to="/campusRecruitment">
                    <el-radio-button class="btu1" label="Post a position" value="Post Campus Recruitment" />
                  </router-link>
                    
                </div>
                <div class="right">
                    <router-link to="/EUserHomePage">
                      <el-radio-button label=" home page" value=" home page" />
                    </router-link>      
                     
  
                    <router-link to="/applicantTracking">
                      <el-radio-button label="Applicant Tracking" value="Applicant Tracking" />
                    </router-link>
                    <el-radio-button label="Communication Tools" value="Communication Tools" />
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
                    <div class="comment">
                      <el-descriptions title="This is what you want:" :column="3" border v-for="searchByIndustry,index in searchByIndustry.searchByIndustryList" :key="index">
                          <el-descriptions-item
                            label="Username"
                            label-align="right"
                            align="center"  
                            label-class-name="my-label"
                            class-name="my-content"
                            width="200px"                          
                            >{{employerNameList.employerNameList[index]}}</el-descriptions-item
                          >
                          <el-descriptions-item label="Work Experience" label-align="right" align="center"
                            >{{ searchByIndustry.workExperience }}</el-descriptions-item
                          >
                          <el-descriptions-item label="Education" label-align="right" align="center"
                            >{{searchByIndustry.education }}</el-descriptions-item
                          >
                          <el-descriptions-item label="Age" label-align="right" align="center"
                            >{{searchByIndustry.age}}</el-descriptions-item
                          >
                          <el-descriptions-item label="Position" label-align="right" align="center"
                            >{{searchByIndustry.expectedPosition}}</el-descriptions-item
                          >
                        </el-descriptions>
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
background-image: url('/img/16.jpg'); /* 替换为你的图片路径 */
background-size: cover;
background-position: center;
z-index: -1; /* 使图片在内容之后 */
}
.left{
    margin-left: 460px;
    margin-top: 15px;

}
.right{
    margin-left: 1500px;
    margin-top: 15px;
}
.mt-4{
    margin-left: 600px;
    margin-top: 20px;

}
.companyDetails{
    margin-left: 100px;
    margin-top: 120px;
}
:deep(.my-label) {
  background: var(--el-color-success-light-9) !important;
}
:deep(.my-content) {
  background: var(--el-color-danger-light-9);
}
.comment{
  margin-top: 40px;
  margin-left: 100px;
  position: relative;
}
.post{
position: absolute;
left: 2200px;
top: 490px;
}
.demo-rate-block {
  padding: 10px 10px;
  text-align: center;
  border-right: solid 1px var(--el-border-color);
  display: inline-block;
  width: 29%;
  box-sizing: border-box;
}
.demo-rate-block:last-child {
  border-right: none;
}
.demo-rate-block .demonstration {
  display: block;
  color: var(--el-text-color-secondary);
  font-size: 5px;
  margin-bottom: 5px;
}
</style>