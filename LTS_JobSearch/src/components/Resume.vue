<template>
    <div class="Resume">
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

          <div class="touxiang">
            <el-upload
                class="avatar-uploader"
                action="http://localhost:8080/user/saveFile"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload"
                 enctype="multipart/form-data"
              >
                <el-avatar v-if="imageUrl" :src="imageUrl" class="avatar" />
                <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
              </el-upload> 
                
            <div class="photoDown" >
                <h2>{{username}}</h2> 
            </div>
            <div class="photoDown1">
              <text v-if="gender">Gender: {{gender}} And Age:{{age}}</text>
            </div>
          </div>
          <div class="basic information">
          </div>
            <div class="demo-collapse">

                <el-collapse v-model="activeName" accordion>
                  <el-collapse-item class="abc"  title="Basic Information" name="1" >
                <div>
                    <el-form
                            ref="ruleFormRef0"
                            style="max-width: 600px"
                            :model="ruleForm0"
                            :rules="rules0"
                            label-width="auto"
                            class="demo-ruleForm0"
                            :size="formSize"    
                            status-icon
                        >
                        <el-form-item label="Family Address" prop="familyAddress" class="item">
                            <el-input v-model="ruleForm0.familyAddress" type="textarea" />
                         </el-form-item>
                         <el-form-item label="Telephone Number" prop="telephoneNumber" class="item">
                            <el-input v-model="ruleForm0.telephoneNumber" type="textarea" />
                         </el-form-item>
                         <el-form-item label="Email" prop="email" class="item">
                            <el-input v-model="ruleForm0.email" type="textarea" />
                         </el-form-item>                                                    
                            <el-form-item>
                            <el-button  type="primary" @click="submitForm0(ruleFormRef0)">
                                Save and update
                            </el-button>
                            <el-button :plain="true" @click="resetForm0(ruleFormRef0)">Reset</el-button>
                            </el-form-item>
                        </el-form>
                </div>
            </el-collapse-item>







                <el-collapse-item class="abc" title="Project Experience" name="2"   >
                    <div>
                        <el-form
                            ref="ruleFormRef1"
                            style="max-width: 600px;" 
                            :model="ruleForm1"
                            :rules="rules1"
                            label-width="auto"
                            class="demo-ruleForm1"
                            :size="formSize"
                            status-icon
                        >
                            <el-form-item label="Project Name" prop="projectName" class="item">
                            <el-input v-model="ruleForm1.projectName" />
                            </el-form-item>

                            <el-form-item label="Time Period" required class="item">

                            <el-col :span="11">
                                <el-form-item prop="projectDate1" >
                                <el-date-picker
                                    v-model="ruleForm1.projectDate1"
                                    type="date"
                                    label="Pick a date"
                                    placeholder="Pick a date"
                                    style="width: 100%"
                                />
                                </el-form-item>
                            </el-col>
                            <el-col class="text-center" :span="2">
                                <span class="text-gray-500">-</span>
                            </el-col>
                            <el-col :span="11">
                                <el-form-item prop="projectDate2">
                                <el-date-picker
                                    v-model="ruleForm1.projectDate2"
                                    label="Pick a date"
                                    placeholder="Pick a date"
                                    style="width: 100%"
                                />
                                </el-form-item>
                            </el-col>
                            </el-form-item>                            
                            <el-form-item label="Project Desc" prop="projectDesc" class="item">
                            <el-input v-model="ruleForm1.projectDesc" type="textarea" />
                            </el-form-item>
                            <el-form-item>
                            <el-button type="primary" @click="submitForm1(ruleFormRef1)">
                                Save and update
                            </el-button>
                            <el-button @click="resetForm1(ruleFormRef1)">Reset</el-button>
                            </el-form-item>
                        </el-form>
                </div>
                <div>
                        
                </div>
            </el-collapse-item>


            <el-collapse-item class="abc" title="Career Objective" name="3">
                <div>
                    <el-form
                            ref="ruleFormRef2"
                            style="max-width: 600px"
                            :model="ruleForm2"
                            :rules="rules2"
                            label-width="auto"
                            class="demo-ruleForm2"
                            :size="formSize"    
                            status-icon
                        >
                        <el-form-item label="Preferred Function" prop="preferredFunction" class="item">
                        <el-select v-model="ruleForm2.preferredFunction" placeholder="Preferred Function">
                            <el-option label="java engineer" value="java engineer" />
                            <el-option label="doctor" value="doctor" />
                            <el-option label="teacher" value="teacher" />
                            <el-option label="sales manager" value="sales manager" />
                            <el-option label="algorithm engineer" value="algorithm engineer" />
                        </el-select>
                        </el-form-item>

                            
                        <el-form-item label="Preferred Location" prop="preferredLocation" class="item">
                        <el-select v-model="ruleForm2.preferredLocation" placeholder="Preferred Location">
                            <el-option label="Beijing" value="Beijing" />
                            <el-option label="Shanghai" value="Shanghai" />
                            <el-option label="Chengdu" value="Chengdu" />
                            <el-option label="Guangzhou" value="Guangzhou" />
                            <el-option label="Shenzhen" value="Shenzhen" />
                        </el-select>
                        </el-form-item>

                                                     
                            <el-form-item label="Salary Expectation" prop="salaryExpectation" class="item">
                            <el-input v-model="ruleForm2.salaryExpectation" type="textarea" />
                            </el-form-item>
                            <el-form-item>
                            <el-button type="primary" @click="submitForm2(ruleFormRef2)">
                                Save and update
                            </el-button>
                            <el-button @click="resetForm2(ruleFormRef2)">Reset</el-button>
                            </el-form-item>
                        </el-form>
                </div>
            </el-collapse-item>


            <el-collapse-item class="abc" title="Work/Internship Experience" name="4">
                <div>
                    <el-form
                            ref="ruleFormRef3"
                            style="max-width: 600px"
                            :model="ruleForm3"
                            :rules="rules3"
                            label-width="auto"
                            class="demo-ruleForm3"
                            :size="formSize"
                            status-icon
                        >
                            <el-form-item label="Company Name" prop="companyName" class="item">
                            <el-input v-model="ruleForm3.companyName" />
                            </el-form-item>


                            <el-form-item label="Job Category" prop="jobCategory" class="item">
                        <el-select v-model="ruleForm3.jobCategory" placeholder="Job Category" class="item">
                            <el-option label="java engineer" value="java engineer" />
                            <el-option label="doctor" value="doctor" />
                            <el-option label="teacher" value="teacher" />
                            <el-option label="sales manager" value="sales manager" />
                            <el-option label="algorithm engineer" value="algorithm engineer" />
                        </el-select>
                        </el-form-item>


                            <el-form-item label="Start Time" required class="item">
                            <el-col :span="11">
                                <el-form-item prop="date1">
                                <el-date-picker
                                    v-model="ruleForm3.date1"
                                    type="date"
                                    label="Pick a date"
                                    placeholder="Pick a date"
                                    style="width: 100%"
                                />
                                </el-form-item>
                            </el-col>
                            <el-col class="text-center" :span="2">
                                <span class="text-gray-500">-</span>
                            </el-col>
                            <el-col :span="11">
                                <el-form-item prop="date2">
                                <el-date-picker
                                    v-model="ruleForm3.date2"
                                    label="Pick a time"
                                    placeholder="Pick a time"
                                    style="width: 100%"
                                />
                                </el-form-item>
                            </el-col>
                            </el-form-item>   
                            <el-form-item label="Salary" prop="salary" class="item">
                            <el-input v-model="ruleForm3.salary" type="textarea" />
                            </el-form-item>
                            
                            <el-form-item label="Job Desc" prop="jobDesc" class="item">
                            <el-input v-model="ruleForm3.jobDesc" type="textarea" />
                            </el-form-item>
                            <el-form-item>
                            <el-button type="primary" @click="submitForm3(ruleFormRef3)">
                                Save and update
                            </el-button>
                            <el-button @click="resetForm3(ruleFormRef3)">Reset</el-button>
                            </el-form-item>
                        </el-form>
                </div>
            </el-collapse-item>



            <el-collapse-item class="abc" title="Personal Advantage" name="5">
                <div>
                    <el-form
                            ref="ruleFormRef4"
                            style="max-width: 600px"
                            :model="ruleForm4"
                            :rules="rules4"
                            label-width="auto"
                            class="demo-ruleForm4"
                            :size="formSize"
                            status-icon
                        >                  
                            <el-form-item label="Personal Advantage" prop="personalAdvantage" class="item">
                            <el-input v-model="ruleForm4.personalAdvantage" type="textarea" />
                            </el-form-item>
                            <el-form-item>
                            <el-button type="primary" @click="submitForm4(ruleFormRef4)">
                                Save and update
                            </el-button>
                            <el-button @click="resetForm4(ruleFormRef4)">Reset</el-button>
                            </el-form-item>
                        </el-form>
                </div>
            </el-collapse-item>
            </el-collapse>
  </div>
  
          
      
  
    </div>
  </template>
  
  <script setup>
  import { UserFilled } from '@element-plus/icons-vue'
  import { reactive, ref,onMounted,onUnmounted } from 'vue'
  import request from'../utils/request'
  import { Search } from '@element-plus/icons-vue'
  import {useRouter} from 'vue-router'
  const router = useRouter()
  const select = ref('')
  
  /* 导入pinia数据 */
  import {defineUser} from "../store/userStore.js"
  let user = defineUser()
const activeName = ref('1')
//   --------------------------------
import { toRefs } from 'vue'

const state = reactive({
  circleUrl:
    '/image/17.jpg',
  sizeList: ['large'],
})
const { circleUrl, squareUrl, sizeList } = toRefs(state)
const imageUrl = ref('')
const imageUrl1 = ref('')
onMounted(async()=>{
    showPersonInfo()
    findResumeByJobSeekerId()
    showImage()
})

let age = ref('')
let gender = ref('')
let username = ref('')
async function showPersonInfo(){
  let {data} = await request.get("user/checkProfile",{params:{"jobSeekerId":user.jobSeekerId}})
  let response = await request.get("user/getUserNameById",{params:{"jobSeekerId":user.jobSeekerId}})
  age.value = data.data.profiles.age;
  gender.value = data.data.profiles.gender;
  username.value = response.data.data.jobSeeker.username;
}


//----------------------上传
import { ElMessage } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'
//------------------
// async function showImage() {
//   try {
//     let { data } = await request.get("user/showImage", { params: { "jobSeekerId": user.jobSeekerId } });
//     let blob = new Blob([data], { type: 'image/jpeg' }); 
//     let url = URL.createObjectURL(blob);
//     // 更新图片 URL
//     imageUrl1.value = url;
//   } catch (error) {
//     console.error('Failed to fetch image:', error);
//   }
// }
async function showImage() {
  let { data } = await request.get("user/showImage", { params: { "jobSeekerId": user.jobSeekerId } });
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




const ruleForm0 = reactive({
  familyAddress: '',
  telephoneNumber: '',
  email: ''
})
const ruleForm1 = reactive({
  projectName: '',
  projectDate1: '',
  projectDate2: '',
  projectDesc: '',
})
const ruleForm2 = reactive({
  preferredFunction: '',
  preferredLocation: '',
  salaryExpectation: '',
})
const ruleForm3 = reactive({
    companyName: '',
    jobCategory: '',
    date1: '',
    date2: '',
    salary:'',
    jobDesc: '',
})
const ruleForm4 = reactive({
    personalAdvantage: ''
})

const ruleForm = reactive({
  imagePath:'',
  familyAddress: ruleForm0.familyAddress,
  telephoneNumber: ruleForm0.telephoneNumber,
  email: ruleForm0.email,
  projectName: ruleForm1.projectName,
  projectDate1: ruleForm1.projectDate1,
  projectDate2: ruleForm1.projectDate2,
  projectDesc: ruleForm1.projectDesc,
  preferredFunction: ruleForm2.preferredFunction,
  preferredLocation: ruleForm2.preferredLocation,
  salaryExpectation: ruleForm2.salaryExpectation,
  companyName: ruleForm3.companyName,
  jobCategory: ruleForm3.jobCategory,
  workDate1: ruleForm3.date1,
  workDate2: ruleForm3.date2,
  salary:ruleForm3.salary,
  jobDesc: ruleForm3.jobDesc,
  personalAdvantage: ruleForm4.personalAdvantage,
  jobSeekerId:user.jobSeekerId
})
//---------------------
let imagePath3 = ref("")
let imagePath4 = ref("")
let imagePath1 = ref("")
async function findResumeByJobSeekerId(){
        let {data}  = await request.get("user/findResumeByJobSeekerId",{params:{"jobSeekerId":user.jobSeekerId}})
        if (data && data.data && data.data.resume) {  
        imagePath3.value = '123'
        if(imagePath3.value!=''){
          imagePath1.value =imagePath3.value
          ruleForm.imagePath=imagePath3.value
        }
        ruleForm0.familyAddress = data.data.resume.familyAddress|| '';
        ruleForm0.telephoneNumber = data.data.resume.telephoneNumber|| '';
        ruleForm0.email = data.data.resume.email|| '';
        ruleForm1.projectName = data.data.resume.projectName|| '';
        ruleForm1.projectDate1 = data.data.resume.projectDate1 || '';
        ruleForm1.projectDate2 = data.data.resume.projectDate2 || '';
        ruleForm1.projectDesc = data.data.resume.projectDesc || '';
        ruleForm2.preferredFunction = data.data.resume.preferredFunction || '';
        ruleForm2.preferredLocation = data.data.resume.preferredLocation || '';
        ruleForm2.salaryExpectation = data.data.resume.salaryExpectation || '';
        ruleForm3.companyName = data.data.resume.companyName || '';
        ruleForm3.jobCategory = data.data.resume.jobCategory || '';
        ruleForm3.date1 = data.data.resume.workDate1 || '';
        ruleForm3.date2 = data.data.resume.workDate2 || '';
        ruleForm3.salary = data.data.resume.salary || '';
        ruleForm3.jobDesc = data.data.resume.jobDesc || '';
        ruleForm4.personalAdvantage = data.data.resume.personalAdvantage || '';
      }
    }
//  const userInfo = ref({
//   headerImg: '',
//   jobSeekerId: user.jobSeekerId
// })
const handleAvatarSuccess = async (response, uploadFile) => {
  imageUrl.value = URL.createObjectURL(uploadFile.raw);

  console.log(response.data.realPath)
  ruleForm.realPath = response.data.realPath
  imagePath4.value = response.data.realPath
  ruleForm.imagePath = imagePath4.value 
  // let ruleForm66={
  //     realPath:response.data.realPath,
  //     jobSeekerId:user.jobSeekerId
  // }
 
  console.log(response.data.realPath)
  // let {data} = await request.get("user/findResumeByJobSeekerId",{params:{"jobSeekerId":user.jobSeekerId}})
  // if(data&&data.data&&data.data.resume){
  //   imagePath4.value = data.data.resume.imgPath
  //   imagePath1.value = imagePath4.value
  // }
  let ruleForm9 = {
      imgPath:imagePath4.value,
      jobSeekerId:user.jobSeekerId
    }
let ruleForm10 = {
  imagePath:imagePath4.value,
  familyAddress: ruleForm0.familyAddress,
  telephoneNumber: ruleForm0.telephoneNumber,
  email: ruleForm0.email,
  projectName: ruleForm1.projectName,
  projectDate1: ruleForm1.projectDate1,
  projectDate2: ruleForm1.projectDate2,
  projectDesc: ruleForm1.projectDesc,
  preferredFunction: ruleForm2.preferredFunction,
  preferredLocation: ruleForm2.preferredLocation,
  salaryExpectation: ruleForm2.salaryExpectation,
  companyName: ruleForm3.companyName,
  jobCategory: ruleForm3.jobCategory,
  workDate1: ruleForm3.date1,
  workDate2: ruleForm3.date2,
  salary:ruleForm3.salary,
  jobDesc: ruleForm3.jobDesc,
  personalAdvantage: ruleForm4.personalAdvantage,
  jobSeekerId:user.jobSeekerId
}
    console.log(imagePath1)
  if(imagePath1.value==''&&ruleForm0.familyAddress==''){
    let {data} = await request.post("user/saveFile1",ruleForm10)  
  }else if( imagePath4.value != imagePath3.value){
    let {data} = await request.post("user/updateFile1",ruleForm9)  
  }
 
};


const beforeAvatarUpload = (rawFile) => {
  if (rawFile.type !== 'image/jpeg') {
    ElMessage.error('Avatar picture must be JPG format!');
    return false;
  } else if (rawFile.size / 1024 / 1024 > 2) {
    ElMessage.error('Avatar picture size can not exceed 2MB!');
    return false;
  }
  return true;
};
//--------------------------

//----------------------
const formSize = ref('default')

//第0张表
const ruleFormRef0 = ref()

const rules0 = reactive({
  familyAddress: [
    { required: true, message: 'Please input Family Address', trigger: 'blur' },
    { min: 3, max: 30, message: 'Length should be 3 to 30', trigger: 'blur' },
  ],
  telephoneNumber: [
    { required: true, message: 'Please input Telephone Number', trigger: 'blur' },
    { min: 3, max: 20, message: 'Length should be 3 to 20', trigger: 'blur' },
  ],
  email: [
    { required: true, message: 'Please input Email', trigger: 'blur' },
    { min: 3, max: 20, message: 'Length should be 3 to 20', trigger: 'blur' },
  ]
})


const submitForm0 = async (formEl) => {
  if (!formEl) return
  await formEl.validate(async(valid, fields) => {
    if (valid) {
      if(imagePath3.value==''){
        window.alert("Please select the photo first")
        return
      }
      let ruleForm00 = {
        familyAddress: ruleForm0.familyAddress,
        telephoneNumber: ruleForm0.telephoneNumber,
        email: ruleForm0.email,
        jobSeekerId:user.jobSeekerId
      }
      let {data} = await request.post("user/saveBasicInfo",ruleForm00)
      if(data.code == 200){
        ElMessage({
        message: 'Congrats, this is a success message.',
        type: 'success',
  })
      }
      
    } else {
      console.log('error submit!', fields)
    }
  })
}

const resetForm0 = (formEl) => {
  if (!formEl) return
  formEl.resetFields()
}



//第一张表

const ruleFormRef1 = ref()


const rules1 = reactive({
    projectName: [
    { required: true, message: 'Please input Project Name', trigger: 'blur' },
    { min: 3, max: 20, message: 'Length should be 3 to 20', trigger: 'blur' },
  ],
  projectDate1: [
    {
      type: 'date',
      required: true,
      message: 'Please pick a date',
      trigger: 'change',
    },
  ],
  projectDate2: [
    {
      type: 'date',
      required: true,
      message: 'Please pick a date',
      trigger: 'change',
    },
  ],
  projectDesc: [
    { required: true, message: 'Please input Project Desc', trigger: 'blur' },
  ],
})

const submitForm1 = async (formEl) => {
  if (!formEl) return
  await formEl.validate(async(valid, fields) => {
    if (valid) {
      if(imagePath3.value==''){
        window.alert("Please select the photo first")
        return
      }
      const formattedDate1 = `${ruleForm1.projectDate1.getFullYear()}-${String(ruleForm1.projectDate1.getMonth() + 1).padStart(2, '0')}-${String(ruleForm1.projectDate1.getDate()).padStart(2, '0')}`;
      const formattedDate2 = `${ruleForm1.projectDate2.getFullYear()}-${String(ruleForm1.projectDate2.getMonth() + 1).padStart(2, '0')}-${String(ruleForm1.projectDate2.getDate()).padStart(2, '0')}`;
      let ruleForm11 = {
        projectName:ruleForm1.projectName,
        projectDate1:formattedDate1,
        projectDate2:formattedDate2,
        projectDesc:ruleForm1.projectDesc,
        jobSeekerId:user.jobSeekerId
      }
      let {data} = await request.post("user/saveProjectExperience",ruleForm11)
      if(data.code == 200){
        ElMessage({
        message: 'Congrats, this is a success message.',
        type: 'success',
  })
      }
    } else {
      console.log('error submit!', fields)
    }
  })
}

const resetForm1 = (formEl) => {
  if (!formEl) return
  formEl.resetFields()
}


//第二张表
const ruleFormRef2 = ref()


const rules2 = reactive({
    preferredFunction: [
    {
      required: true,
      message: 'Please select Preferred Function',
      trigger: 'change',
    },
  ],
  preferredLocation: [
    {
      required: true,
      message: 'Please select Preferred Location',
      trigger: 'change',
    },
  ],
  salaryExpectation: [
    { required: true, message: 'Please input Salary Expectation', trigger: 'blur' },
  ],
})

const submitForm2 = async (formEl) => {
  if (!formEl) return
  await formEl.validate(async(valid, fields) => {
    if (valid) {
      if(imagePath3.value==''){
        window.alert("Please select the photo first")
        return
      }
      let ruleForm22 = {
        preferredFunction:ruleForm2.preferredFunction,
        preferredLocation:ruleForm2.preferredLocation,
        salaryExpectation:ruleForm2.salaryExpectation,
        jobSeekerId:user.jobSeekerId
      } 
      let {data} = await request.post("user/saveCareerObjective",ruleForm22)
      if(data.code == 200){
        ElMessage({
        message: 'Congrats, this is a success message.',
        type: 'success',
  })
      }
    } else {
      console.log('error submit!', fields)
    }
  })
}

const resetForm2 = (formEl) => {
  if (!formEl) return
  formEl.resetFields()
}



//第三张表
const ruleFormRef3= ref()


const rules3 = reactive({
    companyName: [
    { required: true, message: 'Please input Company Name', trigger: 'blur' },
    { min: 3, max: 10, message: 'Length should be 3 to 5', trigger: 'blur' },
  ],
  jobCategory: [
    {
      required: true,
      message: 'Please select Job Category',
      trigger: 'change',
    },
  ],
    date1: [
    {
      type: 'date',
      required: true,
      message: 'Please pick a date',
      trigger: 'change',
    },
  ],
  date2: [
    {
      type: 'date',
      required: true,
      message: 'Please pick a time',
      trigger: 'change',
    },
  ],
  salary: [
    { required: true, message: 'Please input Salary', trigger: 'blur' },
  ],
  jobDesc: [
    { required: true, message: 'Please input Job Desc', trigger: 'blur' },
  ],
})

const submitForm3 = async (formEl) => {
  if (!formEl) return
  await formEl.validate(async(valid, fields) => {
    if (valid) {
      if(imagePath3.value==''){
        window.alert("Please select the photo first")
        return
      }
      let ruleForm33 = {
        companyName:ruleForm3.companyName,
        jobCategory:ruleForm3.jobCategory,
        workDate1:ruleForm3.date1,
        workDate2:ruleForm3.date2,
        salary:ruleForm3.salary,
        jobDesc:ruleForm3.jobDesc,
        jobSeekerId:user.jobSeekerId
      } 
      let {data} = await request.post("user/saveWorkExperience",ruleForm33)
      if(data.code == 200){
        ElMessage({
        message: 'Congrats, this is a success message.',
        type: 'success',
  })
      }
    } else {
      console.log('error submit!', fields)
    }
  })
}

const resetForm3 = (formEl) => {
  if (!formEl) return
  formEl.resetFields()
}


//第四张表
const ruleFormRef4= ref()

const rules4 = reactive({
    personalAdvantage: [
    { required: true, message: 'Please input Personal Advantage', trigger: 'blur' },
  ],
})

const submitForm4 = async (formEl) => {
  if (!formEl) return
  await formEl.validate(async(valid, fields) => {
    if (valid) {
      if(imagePath3.value==''){
        window.alert("Please select the photo first")
        return
      }
      let ruleForm44 = {
        personalAdvantage:ruleForm4.personalAdvantage,
        jobSeekerId:user.jobSeekerId
      } 
      let {data} = await request.post("user/savePersonalAdvantage",ruleForm44)
      if(data.code == 200){
        ElMessage({
        message: 'Congrats, this is a success message.',
        type: 'success',
  })
      }
    } else {
      console.log('error submit!', fields)
    }
  })
}

const resetForm4 = (formEl) => {
  if (!formEl) return
  formEl.resetFields()
}


const options = Array.from({ length: 10000 }).map((_, idx) => ({
  value: `${idx + 1}`,
  label: `${idx + 1}`,
}))




//----------------------------------Search
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
  </script>
  <style  >
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
  .Resume{
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
.demo-collapse{
    margin-left: 700px;
    margin-top: 100px;
    margin-right: 500px;
}
/* ----------------------- */
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
/* -------------------------- */
 .touxiang{
    margin-left: 10px;
    margin-top: 100px;
    border: none;



 }
 .avatar{
  margin-left: 100px;
  margin-top: 100px;

 }
 .avatar-uploader{
  margin-left: 10px;

 }

/* 上传头像 */
.avatar-uploader .avatar {
  width: 178px;
  height: 178px;
  display: block;
  margin-left: 1170px;
}

.avatar-uploader .el-upload {
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
  
}

.demo-type {
  display: flex;
}
.demo-type > div {
  flex: 1;
  text-align: center;
}

.demo-type > div:not(:last-child) {
  border-right: 1px solid var(--el-border-color);
}
.photoDown{
  margin-left: 1200px;
  font-family: Lucida Sans Unicode; /* 使用 Indie Flower 艺术字体 */
  font-size: 24px; /* 设置字体大小 */
  color: #030314; /* 设置字体颜色 */
  position: relative;
}
.photoDown1{
  margin-left: 1100px;
  position: absolute;
  font-family: Lucida Sans Unicode; /* 使用 Indie Flower 艺术字体 */
  font-size: 24px; /* 设置字体大小 */
  color: #030314; /* 设置字体颜色 */

}
.Resume {
  font-family: Lucida Sans Unicode; /* 使用 Indie Flower 艺术字体 */
  font-size: 24px; /* 设置字体大小 */
  color: #030314; /* 设置字体颜色 */
}

.el-input__inner {
  font-size: 16px; /* 设置输入框内文本的字体大小 */
}
/* .el-button {
  font-size: 26px; 
} */
.item .el-form-item__label{
    color:#e07279;
     font-weight:bold;
     font-size:20px
  }
  .abc .el-collapse-item__header {
    font-family: Lucida Sans Unicode; /* 使用 Indie Flower 艺术字体 */
  font-size: 24px; /* 设置字体大小 */
  color: #030314; /* 设置字体颜色 */
}
.Resume1{
  background-image: url('/img/14.jpg');
  background-size: cover;
  background-position: center;
}


  </style>
