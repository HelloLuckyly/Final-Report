<script setup>

import {defineWorkId} from "../store/workIdStore"
let workId = defineWorkId()

import{ref,reactive,computed,onMounted,onUnmounted} from'vue'

import request from'../utils/request'
import {useRouter} from 'vue-router'

import {defineCompanyName} from '../store/companyNameStore'
let companyName1 = defineCompanyName()


import {defineCompanyReviewList} from '../store/companyReviewListStore'
let companyReviewList = defineCompanyReviewList()

import { defineEmployerIdListStore } from '../store/employerIdListStore';
let employerIdList = defineEmployerIdListStore()

let router = useRouter()

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

const CompanyDetail = reactive({
  companyName: '',
  companyAddress: '',
  specificAddress: '',
  companyDescription: '',
  companyCulture: '',
  jobOpportunities: ''
})

    //挂载完毕后,立刻查询信息,赋值给pinia
    onMounted(async ()=>{
      console.log(companyName1.companyName)
      showCompanyDetail()
      console.log(companyName1.companyName)
      showALLCompanyReview()
      
      console.log(companyName1.companyName)
    })
    onUnmounted(() => {
  // 在这里清除 Pinia 数据
  companyReviewList.$reset()
  employerNameList.$reset()
})
    async function showCompanyDetail(){
        // 发送异步请求,获得当前用户的所有记录
        let {data} = await request.get("eUser/findCompanyByEmployerId",{params:{"employerId":workId.employerId}})
         // 检查返回的数据对象是否存在以及 company 对象是否存在
    if (data && data.data && data.data.company) {
        // 从返回的数据中获取 company 对象
        let companyData = data.data.company;
        
        // 判断 company 对象的各个属性是否存在，如果存在则赋值给 CompanyDetail 对应的属性
        companyName1.companyName = companyData.companyName || '';
        console.log(companyName1.companyName)
        CompanyDetail.companyName = companyData.companyName || '';
        CompanyDetail.companyDescription = companyData.companyDescription || '';
        CompanyDetail.companyCulture = companyData.companyCulture || '';
        CompanyDetail.companyAddress = companyData.companyAddress || '';
        CompanyDetail.specificAddress = companyData.specificAddress || '';
        CompanyDetail.jobOpportunities = companyData.jobOpportunities || '';
    } else {
        router.push("/HomePage");
    }
    }

import { defineEmployerNameList } from '../store/employerNameStore';
let employerNameList = defineEmployerNameList();

async function showALLCompanyReview() {
  console.log(companyName1.companyName)
  console.log(companyName1.companyName)
  let { data } = await request.get("eUser/findAllCompanyReview", { params: { "companyName": companyName1.companyName} });
    if (data && data.data && data.data.employerIdList) {
       employerIdList.employerIdList = data.data.employerIdList;
        for (const employerId of employerIdList.employerIdList) {
            const response1 = await request.get("eUser/findCompanyReviewByEmployerId", { params: { "employerId": employerId } });
            if (response1 && response1.data && response1.data.data && response1.data.data.review) {
                const companyReview = response1.data.data.review;
                companyReviewList.companyReviewList.push(companyReview);
            }          
          const response2 = await request.get("eUser/findNameByEmployerId", { params: { "employerId": employerId } });
          if(response2 && response2.data && response2.data.data && response2.data.data.employer){
            const employerName = response2.data.data.employer.username;
            employerNameList.employerNameList.push(employerName);
            }

        }
        return companyReviewList.companyReviewList ;
    } else {
        return []; // 或者根据需要返回其他默认值
    }

}



// async function showALLCompanyReview() {
//   try {
//     let { data } = await request.get("eUser/findAllCompanyReview", { params: { "companyName": companyName.companyName} });
//     if (!data || !data.data || !data.data.employerIdList) {
//       return []; // 没有数据则返回空数组
//     }

//     const employerIdList = data.data.employerIdList;

//     // 并行请求公司评论和雇主姓名
//     const responses = await Promise.all(
//       employerIdList.map(async (employerId) => {
//         const response1 = request.get("eUser/findCompanyReviewByEmployerId", { params: { "employerId": employerId } });
//         const response2 = request.get("eUser/findNameByEmployerId", { params: { "employerId": employerId } });
//         const [reviewResponse, nameResponse] = await Promise.all([response1, response2]);
//         return { reviewResponse, nameResponse };
//       })
//     );

//     // 提取公司评论和雇主姓名
//     const companyReviewList = responses.map(({ reviewResponse }) => {
//       if (reviewResponse && reviewResponse.data && reviewResponse.data.data && reviewResponse.data.data.review) {
//         return reviewResponse.data.data.review;
//       }
//       return null;
//     }).filter(review => review !== null);

//     const employerNameList = responses.map(({ nameResponse }) => {
//       if (nameResponse && nameResponse.data && nameResponse.data.data && nameResponse.data.data.employer) {
//         return nameResponse.data.data.employer.username;
//       }
//       return null;
//     }).filter(name => name !== null);

//     return { companyReviewList, employerNameList };
//   } catch (error) {
//     console.error("Error fetching company reviews:", error);
//     return []; // 发生错误时返回空数组
//   }
// }

</script>

<template>
    <div class ="common-layout">
        <el-header >
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
          
        </div>
        </el-header>

        <div class="companyDetails">
            <el-descriptions
                title="Company Details"
                direction="vertical"
                :column="4"
                :size="size"
                border
            >
                <el-descriptions-item label="Company Name">{{CompanyDetail.companyName}}</el-descriptions-item>
                <el-descriptions-item label="Company Address">{{CompanyDetail.companyAddress}}</el-descriptions-item>
                <el-descriptions-item label="Specific Address" :span="2">{{CompanyDetail.specificAddress}}</el-descriptions-item>
                <el-descriptions-item label="Company Description" :span="2">{{CompanyDetail.companyDescription}}</el-descriptions-item>
                <el-descriptions-item label="Company Culture" :span="2">{{CompanyDetail.companyCulture}}</el-descriptions-item>
                <el-descriptions-item label="Job Opportunities" :span="2">{{CompanyDetail.jobOpportunities}}</el-descriptions-item>
            </el-descriptions>
                    </div>                      
                    <div class="comment">
                      <el-descriptions title="Company Review" :column="4" border v-for="companyReview,index in companyReviewList.companyReviewList" :key="index">
                          <el-descriptions-item
                            label="Employer"
                            label-align="right"
                            align="center"  
                            label-class-name="my-label"
                            class-name="my-content"
                            width="200px"                          
                            >{{employerNameList.employerNameList[index]}}</el-descriptions-item
                          >
                          <el-descriptions-item label="Company Review" label-align="right" align="center"
                            >{{ companyReview.companyReview }}</el-descriptions-item
                          >
                          <el-descriptions-item label="Modification time" label-align="right" align="center"
                            >{{companyReview.companyReviewTime}}</el-descriptions-item
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
</style>