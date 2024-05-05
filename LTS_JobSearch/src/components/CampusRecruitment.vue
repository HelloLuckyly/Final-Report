<script setup>
import {ref} from 'vue'
import { Search } from '@element-plus/icons-vue'
import { reactive } from 'vue'
import request from'../utils/request'
const select = ref('')

import {defineEUser} from "../store/eUserStore.js"
let eUser = defineEUser()
import {useRouter} from 'vue-router'
let router = useRouter()

const formSize = ref('default')
const ruleFormRef = ref()
const ruleForm = reactive({
  workingTitle:'',
  salary:'',
  position:'',
  experience:'',
  education:'',
  count: 0,
  workCity:'',
  addressDetails:'',
  workContent:'',
  jobRequirements:'',
  industry:'',
})



const rules = reactive({
    workingTitle: [
    { required: true, message: 'Please input Working Title', trigger: 'blur' },
    { min: 1, max: 50, message: 'Length should be 1 to 50', trigger: 'blur' },
  ],
    salary: [
    { required: true, message: 'Please input Salary', trigger: 'blur' },
    { min: 1, max: 100, message: 'Length should be 1 to 100', trigger: 'blur' },
    ],
     industry: [
    {
      required: true,
      message: 'Please select Type of Industry',
      trigger: 'change',
    },
     ],

     position: [
     { required: true, message: 'Please input Working Title', trigger: 'blur' },
    { min: 1, max: 50, message: 'Length should be 1 to 50', trigger: 'blur' },
     ],
     experience: [
    { required: true, message: 'Please input Experience Requirement' , trigger: 'blur' },
    ],

    education: [
    { required: true, message: 'Please input Education Requirement' , trigger: 'blur' },
    ],
    count: [
    {
      required: true,
      message: 'Please select Number of recruits',
      trigger: 'change',
    },
  ],
  workCity: [
    {
      required: true,
      message: 'Please select Work City',
      trigger: 'change',
    },
     ],
    addressDetails: [
    { required: true, message: 'Please input Address Details', trigger: 'blur' },
  ],
    workContent: [
        { required: true, message: 'Please input Work Contents', trigger: 'blur' },
    ],
    jobRequirements: [
    { required: true, message: 'Please input Job Requirements', trigger: 'blur' },
  ],
})

const submitForm = async (formEl) => {
  if (!formEl) return
  await formEl.validate(async(valid) => {
    if (valid) {
      console.log('submit!')
     let ruleForm1 ={
            workingTitle: ruleForm.workingTitle,
            salary: ruleForm.salary,
            position:ruleForm.position,
            experience:ruleForm.experience,
            education:ruleForm.education,
            count: ruleForm.count,
            workCity:ruleForm.workCity,
            addressDetails:ruleForm.addressDetails,
            workContent:ruleForm.workContent,
            jobRequirements:ruleForm.jobRequirements,
            employerId:eUser.employerId,
            defaultList:eUser.defaultList,
            industry:ruleForm.industry,
            postTime:(new Date()).toISOString().substring(0, 19)
            }
      let  {data}= await request.post("eUser/postCampusRecruitment",ruleForm1)
      if(data.code == 200){
            
            alert("Post Successful")
            //更新到pinia中
            // campusRecruitment.campusRecruitmentList = data.data.campusRecruitmentList
            router.push("/eUserHomePage")
          }else{
            alert("Failed to login")
          }
    } else {
      return alert("Failed to login")
    }
  })
}

const resetForm = (formEl) => {
  if (!formEl) return
  formEl.resetFields()
}

const options = Array.from({ length: 10000 }).map((_, idx) => ({
  value: `${idx + 1}`,
  label: `${idx + 1}`,
}))
</script>

<template>
    <div>      
        <el-header >
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
              
  
                    <router-link to="/applicantTracking">
                      <el-radio-button label="Applicant Tracking" value="Applicant Tracking" />
                    </router-link>
                    
                </div>
                </el-radio-group>
            </div>
            
      
        </el-header>

        <div class="content">
            <h1>Adding new positions</h1>
            <div>
              <h2 style="margin-left: 200px;">Basic information about the job</h2>
                <el-form
                    ref="ruleFormRef"
                    style="max-width: 600px"
                    :model="ruleForm"
                    :rules="rules"
                    label-width="auto"
                    class="demo-ruleForm"
                    :size="formSize"
                    status-icon
                >
                    <el-form-item label="Working title" prop="workingTitle">
                    <el-input v-model="ruleForm.workingTitle" />
                    </el-form-item>

                    <el-form-item label="Salary and Benefits" prop="salary">
                    <el-input v-model="ruleForm.salary" type="textarea" />
                    </el-form-item>


                   
                    <el-form-item label="Type of industry" prop="industry">
                        <el-select v-model="ruleForm.industry" placeholder="Type of industry">
                            <el-option label="Internet" value="Internet" />
                            <el-option label="Financial" value="Financial" />
                            <el-option label="Constructions" value="Constructions" />
                            <el-option label="Education " value="Education " />
                            <el-option label="Markets" value="Markets " />
                            <el-option label="Service Industry" value="Service Industry" />
                            <el-option label="Administrative " value="Administrative " />
                        </el-select>
                    </el-form-item>
                    <el-form-item label="Type of position" prop="position">
                      <el-input v-model="ruleForm.position" />
                    </el-form-item>


                    <el-form-item label="Experience Requirements" prop="experience">
                    <el-input v-model="ruleForm.experience" type="textarea" />
                    </el-form-item>

                    <el-form-item label="Education Requirements" prop="education">
                    <el-input v-model="ruleForm.education" type="textarea" />
                    </el-form-item>


                    <el-form-item label="Number of recruits" prop="count">
                    <el-select-v2
                        v-model="ruleForm.count"
                        placeholder="Number of recruits"
                        :options="options"
                    />
                    </el-form-item>

                    <h2 style="margin-left: 200px;">Workplace</h2>
                    <el-form-item label="Work City" prop="workCity">
                        <el-select v-model="ruleForm.workCity" placeholder="Work City">
                            <el-option label="Beijing" value="Beijing" />
                            <el-option label="Shanghai" value="Shanghai" />
                            <el-option label="Chengdu" value="Chengdu" />
                            <el-option label="Guangzhou" value="Guangzhou" />
                            <el-option label="Shenzhen" value="Shenzhen" />
                        </el-select>
                    </el-form-item>

                    
                    <el-form-item label="Address details" prop="addressDetails">
                    <el-input v-model="ruleForm.addressDetails" type="textarea" />
                    </el-form-item>


                    <h2 style="margin-left: 200px;">Job Description</h2>
              
                    <el-form-item label="Work content" prop="workContent">
                    <el-input v-model="ruleForm.workContent" type="textarea" />
                    </el-form-item>

                    <el-form-item label="Job Requirements" prop="jobRequirements">
                    <el-input v-model="ruleForm.jobRequirements" type="textarea" />
                    </el-form-item>


                    <el-form-item>




                    <el-button style="margin-left: 300px;" type="primary" @click="submitForm(ruleFormRef)">
                        Post
                    </el-button>
                    <el-button @click="resetForm(ruleFormRef)">Reset</el-button>
                    </el-form-item>
                </el-form>
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
    margin-left: 550px;
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
.content{
    margin-top: 100px;
    margin-left: 500px;
}
.content h1{
  margin-left: 200px;
}
.back{
    background-color: rgb(162, 224, 190);
}
</style>