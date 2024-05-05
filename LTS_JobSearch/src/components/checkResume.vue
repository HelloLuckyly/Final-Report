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
          <div class="content">
              <div class="content1">
                <el-row class="demo-avatar demo-basic">
                  <el-col :span="12">
                    <div class="sub-title"></div>
                    <div class="demo-basic--circle">
                      <div class="block">
                        <el-avatar :size="100" :src="imageUrl" />
                      </div>     
                    </div>
                  </el-col>
                </el-row>
              </div>
            <div class="photoDown" >
                <h2>{{username}}</h2>
                
            </div>
            <div class="photoDown1">
              <text>Gender: {{gender}} And Age:{{age}}</text>
            </div>
                <div class="content2">
                    <el-descriptions
                        title="Basic Information"
                        direction="vertical"
                        :column="3"
                        :size="size"
                        border
                    >
                        <text>Basic Information</text>
                         <el-descriptions-item label="FamilyAddress" :span="1">{{ form.familyAddress }}</el-descriptions-item>
                         <el-descriptions-item label="TelephoneNumber" :span="1">{{ form.telephoneNumber }}</el-descriptions-item>
                        <el-descriptions-item label="Email" :span="3">{{form.email}}</el-descriptions-item>
                    </el-descriptions>



                    <el-descriptions
                        title="Project Experience"
                        direction="vertical"
                        :column="3"
                        :size="size"
                        border
                    >
                    <text>Project Experience</text>
                        <el-descriptions-item label="ProjectName" :span="1">{{form.projectName  }}</el-descriptions-item>
                        <el-descriptions-item label="ProjectDate" :span="1"
                        >{{ form.projectDate1 }} to  {{ form.projectDate2 }}
                        </el-descriptions-item>
                        <el-descriptions-item label="ProjectDesc" :span="3">{{form.projectDesc }}</el-descriptions-item>
                    </el-descriptions>


                    <el-descriptions
                        title="Career Objective"
                        direction="vertical"
                        :column="3"
                        :size="size"
                        border
                    >
                    <br>
                        <text>Career Objective</text>
                        <el-descriptions-item label="PreferredFunction" :span="1">{{form.preferredFunction  }}</el-descriptions-item>
                        <br>
                        <el-descriptions-item label="PreferredLocation" :span="1"
                        >{{ form.preferredLocation }}
                        </el-descriptions-item>
                        <el-descriptions-item label="SalaryExpectation" :span="3" >{{form.salaryExpectation }}</el-descriptions-item>
                    </el-descriptions>
                    
                       
                    <el-descriptions
                        title="Work/Internship Experience"
                        direction="vertical"
                        :column="3"
                        :size="size"
                        border
                    >
                    <br>
                    <text>Work/Internship Experience</text>
                        <el-descriptions-item label="CompanyName" :span="1">{{form.companyName  }}</el-descriptions-item>
                        
                        <el-descriptions-item label="WorkDate" :span="1"
                        >{{ form.workDate1 }} to  {{ form.workDate2 }}
                        </el-descriptions-item>
                        <el-descriptions-item label="Salary" :span="3" >{{form.salary }}</el-descriptions-item>
                        <el-descriptions-item label="JobDesc" :span="3" >{{form.jobDesc }}</el-descriptions-item>
                    </el-descriptions>
                        

                    <el-descriptions
                        title="Personal Advantage"
                        direction="vertical"
                        :column="3"
                        :size="size"
                        border
                    >
                    <br>
                    <text>Personal Advantage</text>
                        <el-descriptions-item label="PersonalAdvantage" :span="3" >{{form.personalAdvantage }}</el-descriptions-item>
                    </el-descriptions>
                        
                </div>     
          </div>
      
  
    </div>
    
  </template>
  
  <script setup >
  import { ref,onMounted } from 'vue'
  import request from'../utils/request'
  import { Search,Hide, View } from '@element-plus/icons-vue'
  import {useRouter} from 'vue-router'
  import { defineJobSeekerIdByApply } from '../store/jobSeekerIdByApply'
  let jobSeekerIdByApply = defineJobSeekerIdByApply()
  const imageUrl = ref('')

  

  
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
onMounted(async ()=>{
     showResume();
     showPersonInfo()
     showImage()
     
})
let age = ref('')
let gender = ref('')
let username = ref('')
async function showPersonInfo(){
  let {data} = await request.get("user/checkProfile",{params:{"jobSeekerId":jobSeekerIdByApply.jobSeekerId}})
  let response = await request.get("user/getUserNameById",{params:{"jobSeekerId":jobSeekerIdByApply.jobSeekerId}})
  age.value = data.data.profiles.age;
  gender.value = data.data.profiles.gender;
  username.value = response.data.data.jobSeeker.username;
}

const form = reactive({
  imagePath:'',
  familyAddress: '',
  telephoneNumber: '',
  email: '',
  projectName: '',
  projectDate1: '',
  projectDate2: '',
  projectDesc: '',
  preferredFunction: '',
  preferredLocation: '',
  salaryExpectation: '',
  companyName: '',
  jobCategory: '',
  workDate1: '',
  workDate2: '',
  salary:'',
  jobDesc: '',
  personalAdvantage: ''
})
async function showResume() {
    let { data } = await request.get("eUser/checkResume", { params: { "jobSeekerId": jobSeekerIdByApply.jobSeekerId } });
    
    // 循环遍历 data.data.resume 中的属性
    for (let key in data.data.resume) {
        if (Object.prototype.hasOwnProperty.call(data.data.resume, key)) {
            // 检查 form 对象是否具有相同的属性
            if (form.hasOwnProperty(key)) {
                // 将 data.data.resume 中的属性值赋给 form 对象中相同的属性
                form[key] = data.data.resume[key];
            }
        }
    }


}
import { reactive} from 'vue'
import { toRefs } from 'vue'

const state = reactive({
  circleUrl:
    '/image/17.jpg',
  sizeList: ['large'],
})

async function showImage() {
  let { data } = await request.get("user/showImage", { params: { "jobSeekerId": jobSeekerIdByApply.jobSeekerId } });
let binaryData = atob(data); // 将 Base64 字符串解码为二进制数据
let arrayBuffer = new ArrayBuffer(binaryData.length);
let uint8Array = new Uint8Array(arrayBuffer);
for (let i = 0; i < binaryData.length; i++) {
  uint8Array[i] = binaryData.charCodeAt(i);
}
let blob = new Blob([uint8Array], { type: 'image/jpeg' }); 
let url = URL.createObjectURL(blob);
imageUrl.value = url;

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

.photoDown{
  margin-left: 920px;
}
.photoDown1{
  margin-left: 860px;
}
.content2{
margin-top: 60px;
margin-left: 20px;
}
.demo-basic {
  text-align: center;
}
.demo-basic .sub-title {
  margin-bottom: 10px;
  font-size: 14px;
  color: var(--el-text-color-secondary);
}
.demo-basic .demo-basic--circle,
.demo-basic .demo-basic--square {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.demo-basic .block:not(:last-child) {
  border-right: 1px solid var(--el-border-color);
}
.demo-basic .block {
  flex: 1;
}
.demo-basic .el-col:not(:last-child) {
  border-right: 1px solid var(--el-border-color);
}
.content1{
  margin-top: 10px;
  margin-left: 560px;
}
  </style>
