<template>
    <div class="profile">
      <el-header>
            <div class="icon">
          <img src="/img/14.jpg" alt="Custom Image"><br>      
          </div>
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
              
  
                    <el-radio-button label="Applicant Tracking" value="Applicant Tracking" />
                   
                </div>
                </el-radio-group>
            </div>
          </el-header>
          <div>
            <div>
              <el-dialog v-model="dialogFormVisible" title="Add Interview details" width="500"  ref="ruleFormRef" :rules="rules">
                <el-form ref="ruleFormRef"
                  style="max-width: 600px"
                  :model="form"
                  :rules="rules"
                  label-width="auto"
                  class="demo-ruleForm"
                  :size="formSize"
                  status-icon>

                  <el-form-item label="interview address" prop="location" :label-width="formLabelWidth" >
                    <el-input v-model="form.location" autocomplete="off" />
                  </el-form-item>

                  <el-form-item label="interview time"  prop="time"  :label-width="formLabelWidth">
                    <el-input v-model="form.time" autocomplete="off" />
                    </el-form-item>

                    <el-form-item label="interview tips" prop="tips" :label-width="formLabelWidth">
                    <el-input v-model="form.tips" autocomplete="off" />
                    </el-form-item>
  
                </el-form>
                <template #footer>
                  <div class="dialog-footer">
                    <el-button style="margin-left: 100px;" type="primary" @click="submitForm(ruleFormRef)">
                    Submit
                  </el-button>
                <el-button @click="resetForm(ruleFormRef)">Reset</el-button>
                
                  </div>
                </template>
              </el-dialog>
            </div>
          </div>
          <div class="content">
                <div>
                    <el-descriptions
                        title="---------"
                        direction="vertical"
                        :column="4"
                        :size="size"
                        border
                        v-for="applicationManagement,index in applicationManagement.applicationManagementList" :key="index"
                        
                    >
                         <!-- <el-descriptions-item label="Name of applicant" :span="1">{{ allJobSeeker.value && allJobSeeker.value.length > 0 ? allJobSeeker.value[index] : '' }}</el-descriptions-item> -->
                         <el-descriptions-item label="Name of applicant" :span="1">{{ allJobSeeker[index] }}</el-descriptions-item>
                         <el-descriptions-item label="Job title" :span="1">{{ campusRecruitment.campusRecruitmentList[index].workingTitle }}</el-descriptions-item>
                        <el-descriptions-item label="Application progress">{{applicationManagement.status}}</el-descriptions-item>
                        
                        <el-descriptions-item label="Position" :span="2">{{ campusRecruitment.campusRecruitmentList[index].position }}</el-descriptions-item>
                        <el-descriptions-item label="Number of recruits" :span="1"
                        >{{ campusRecruitment.campusRecruitmentList[index].count }}
                        </el-descriptions-item>
                        <el-descriptions-item label="Location" >{{ campusRecruitment.campusRecruitmentList[index].workCity }}</el-descriptions-item>
                        <el-descriptions-item label="Click for details & checkResume & Arranging interviews " >
                          <router-link to="/recruitmentDetail" >
                            <el-button type="info" round @click="savaInfo(index)">Details</el-button>
                          </router-link>
                          &nbsp;
                            <el-button type="info" round @click="removing(index)">Removing</el-button>
                            &nbsp;
                          <router-link to="/checkResume" >
                            <el-button type="primary" round @click="checkResume(applicationManagement.jobSeekerId)">CheckResume</el-button>
                          </router-link> 
                          &nbsp;
                          <el-button type="success" round @click="arrangingInterviews(applicationManagement.applicationManagementId)">Arranging interviews</el-button>
                          &nbsp;
                          <el-button type="success" plain @click="showDialog(applicationManagement.applicationManagementId)" label="Interview details" value="Interview details" >Interview details</el-button>

                         

                        </el-descriptions-item>
                        
                    </el-descriptions>
                    <div v-if="applicationManagement.applicationManagementList == ''" class="beforeContent">
                      <text>You haven't received any applications!</text>
                  <div class="image">
                    <img src='/img/10.jpg' alt="back">
                  </div>
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
  import { defineCampusRecruitment } from '../store/campusRecruitmentStore'
  let campusRecruitment =defineCampusRecruitment()
let workId = defineWorkId()
  const router = useRouter()
  
  // import {defineApplicationManagement} from '../store/applicationManagement'
  // let applicationManagement = defineApplicationManagement()
  /* 导入pinia数据 */
  import {defineEUser} from "../store/eUserStore"
  let eUser = defineEUser()
  
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
  
  
import { defineApplicationManagement } from '../store/applicationManagement';
let applicationManagement = defineApplicationManagement()



onMounted(async ()=>{
     applicationManagement2();
     
})
const allJobSeeker = ref([]);

async function applicationManagement2() {
  let { data } = await request.get("eUser/getAllApplication", { params: { "employerId": eUser.employerId } });
  applicationManagement.applicationManagementList = data.data.applicationManagementList;
  applicationManagement.applicationManagementList.forEach(async (item, index) => {
        
        let response = await request.get("user/getAllCampusRecruitmentByCampusRecruitmentId", { params: { "campusRecruitmentId": item.campusRecruitmentId } });

        
        campusRecruitment.campusRecruitmentList[index] = response.data.data.campusRecruitment;
      });
  // 获取所有的 jobSeekerId
  const jobSeekerIds = applicationManagement.applicationManagementList.map(item => item.jobSeekerId);
  console.log(jobSeekerIds);

  // 发送所有的异步请求获取用户名
  const responses = await Promise.all(jobSeekerIds.map(async (jobSeekerId) => {
    const response = await request.get("user/getUserNameById", { params: { "jobSeekerId": jobSeekerId } });
    return response.data.data.jobSeeker.username;
  }));
  console.log(responses);

  // 将用户名赋值给 allJobSeeker
  allJobSeeker.value = responses;
  console.log(allJobSeeker.value);
}
import {defineJobSeekerIdByApply} from '../store/jobSeekerIdByApply'
let jobSeekerIdByApply = defineJobSeekerIdByApply()
const id = ref(0)
const status = ref(0)
function checkResume(id){
  jobSeekerIdByApply.jobSeekerId = id;
}

async function arrangingInterviews(id){
  let {data} = await request.get("eUser/arrangingInterviews",{params:{"applicationManagementId":id}})
  if(data.code == 200){
    window.alert("success")
  }
}
const form = reactive({
  location: '',
  time:'',
  tips:'',
})

const aId = ref(0);
const dialogFormVisible = ref(false);
const formLabelWidth = '140px';

async function showDialog(sid) {
  dialogFormVisible.value = true;
  aId.value = sid;
  let {data} = await request.get("eUser/findInterviewDetails",{params:{"applicationManagementId":aId.value}})
  if (data && data.data && data.data.interviewDetails) {
    form.location  =  data.data.interviewDetails.location;
    form.time = data.data.interviewDetails.time;
    form.tips = data.data.interviewDetails.tips;
  }else{
    form.location  =  '';
    form.time = '';
    form.tips = '';
  }

}

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

const formSize = ref('default')
const ruleFormRef = ref()
const rules = reactive({
  location: [
    { required: true, message: 'Please input Interview location', trigger: 'blur' },
    { min: 1, max: 50, message: 'Length should be 1 to 50', trigger: 'blur' },
  ],
    time: [
    { required: true, message: 'Please input Interview time' , trigger: 'blur' },
    ],
    tips: [
    { required: true, message: 'Please input Interview Tips', trigger: 'blur' },
  ],
})
const submitForm = async (formEl) => {
  if (!formEl) return
  await formEl.validate(async(valid) => {
    if (valid) {
      console.log('submit!')
      let form1 ={
        location:form.location,
        time:form.time,
        tips:form.tips,
        applicationManagementId:aId.value
        }
        let response;
        let {data} = await request.get("eUser/findInterviewDetails",{params:{"applicationManagementId":aId.value}})  
        if (data && data.data && data.data.interviewDetails) {   
          if(data.data.interviewDetails.location == form.location&&data.data.interviewDetails.time == form.time&&
              data.data.interviewDetails.tips ==form.tips){
                alert("You haven't modified any information yet")
                 return
              }
              if(aId.value == 0){        
                response== await request.post("eUser/saveInterviewDetails",form1)                      
                  alert("Submit Successful")
                  // router.push("/eUserHomePage")      
                }else{            
                  response== await request.post("eUser/updateInterviewDetails",form1)
                  alert("Update Successful")          
                  // router.push("/eUserHomePage")
              }
           }else{
            response== await request.post("eUser/saveInterviewDetails",form1)                      
            alert("Submit Successful")
            // router.push("/eUserHomePage")    
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

  background-size: cover;
  background-position: center;
  z-index: -1; /* 使图片在内容之后 */
  }.left{
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
.back{
    background-color: rgb(162, 224, 190);
    margin-top: 20px;
}

.beforeContent{
  margin-top: 150px;
  margin-left: 100px;
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
  </style>