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



    <el-form-item label="Real Name" prop="realName">
    <el-input v-model="ruleForm.realName" type="realName" autocomplete="off" />
    </el-form-item>



    <el-form-item label="Gender" prop="gender">
      <el-radio-group v-model="ruleForm.gender">
        <el-radio value="male">male</el-radio>
        <el-radio value="women">women</el-radio>
      </el-radio-group>
    </el-form-item>

    <el-form-item label="Age" prop="count">
      <el-select-v2
        v-model="ruleForm.count"
        placeholder="Age"
        :options="options"
      />
    </el-form-item>


    <el-form-item label="Hobbies" prop="hobby">
      <el-input v-model="ruleForm.hobby" type="textarea" />
    </el-form-item>

    <el-form-item label="Working experience" prop="desc1">
      <el-input v-model="ruleForm.desc1" type="textarea" />
    </el-form-item>
    <el-form-item label="Education" prop="desc2">
      <el-input v-model="ruleForm.desc2" type="textarea" />
    </el-form-item>
    <el-form-item label="Skills" prop="desc3">
      <el-input v-model="ruleForm.desc3" type="textarea" />
    </el-form-item>
    <el-form-item label="Expected salary" prop="desc4">
      <el-input v-model="ruleForm.desc4" type="textarea" />
    </el-form-item>
    <el-form-item label="Expected position" prop="desc5">
      <el-input v-model="ruleForm.desc5" type="textarea" />
    </el-form-item>


    <div class="fBtu">
      <el-form-item>
      <el-button type="primary" @click="submitForm(ruleFormRef)">
        Finish 
      </el-button>
      <el-button @click="resetForm(ruleFormRef)">Reset</el-button>
    </el-form-item>
    </div>
    


    

  </el-form>
        </div>
    

  </div>
  
</template>

<script setup>
import { reactive, ref,onMounted } from 'vue'
import request from'../utils/request'
import { Search } from '@element-plus/icons-vue'
import {useRouter} from 'vue-router'
const router = useRouter()
const select = ref('')

onMounted(async ()=>{
    showDefaultValue();
})

async  function showDefaultValue(){
  let {data} = await request.get("user/checkProfile",{params:{"jobSeekerId":user.jobSeekerId}})
        if(data.data.profiles !=null){
            ruleForm.realName = data.data.profiles.realName
            ruleForm.count = data.data.profiles.age
            ruleForm.gender = data.data.profiles.gender
            ruleForm.hobby = data.data.profiles.hobby
            ruleForm.desc1 = data.data.profiles.workExperience
            ruleForm.desc2 = data.data.profiles.education
            ruleForm.desc3 = data.data.profiles.skills
            ruleForm.desc4 = data.data.profiles.expectedSalary
            ruleForm.desc5 = data.data.profiles.expectedPosition
        }else{
          
        }      
    }

/* 导入pinia数据 */
import {defineUser} from "../store/userStore.js"
let user = defineUser()




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







const formSize = ref('default')
const ruleFormRef = ref()
const ruleForm = reactive({
  realName: '',
  count: 0,
  gender:'',
  hobby:'',
  desc1: '',
  desc2:'',
  desc3:'',
  desc4:'',
  desc5:''
})

const rules = reactive({
  realName: [
    { required: true, message: 'Please input Real Name', trigger: 'blur' },
  ],
  count: [
    {
      required: true,
      message: 'Please select Age',
      trigger: 'change',
    },
  ],
  gender: [
    { required: true, message: 'Please input Gender', trigger: 'blur' },
  ],
  hobby: [
    { required: true, message: 'Please input Hobby', trigger: 'blur' },
  ],
  desc1: [
    { required: true, message: 'Please input Work experience', trigger: 'blur' },
  ],
  desc2: [
    { required: true, message: 'Please input education', trigger: 'blur' },
  ],
  desc3: [
    { required: true, message: 'Please input skills', trigger: 'blur' },
  ],
  desc4: [
    { required: true, message: 'Please input Expected salary', trigger: 'blur' },
  ],
  desc5: [
    { required: true, message: 'Please input Expected position', trigger: 'blur' },
  ]
})

const submitForm = (formEl) => {
  if (!formEl) return
  formEl.validate(async(valid) => {
    if (valid) {
      console.log('submit!')
      
     


      // let oldUsername = user.username;
      // let newUsername = ruleForm.username;

      //构造新的ruleForm
      let ruleForm2 = {
        realName:ruleForm.realName,
        age: ruleForm.count,
        workExperience: ruleForm.desc1,
        education: ruleForm.desc2,        
        skills: ruleForm.desc3,
        expectedSalary: ruleForm.desc4,
        expectedPosition:ruleForm.desc5,
        jobSeekerId : user.jobSeekerId,
        gender:ruleForm.gender,
        hobby:ruleForm.hobby
      }
      let response;
      // if(ruleForm.username == user.username){
      //   response= await request.post("user/updateProfile",ruleForm3)
      // }
      //   response= await request.post("user/updateProfile",ruleForm2)
      let {data} = await request.get("user/checkProfile",{params:{"jobSeekerId":user.jobSeekerId}})
      if(data.data&&data.data.profiles&&ruleForm.count == data.data.profiles.age&&ruleForm.gender == data.data.profiles.gender&&
      ruleForm.hobby == data.data.profiles.hobby&&ruleForm.desc1 == data.data.profiles.workExperience
      &&ruleForm.desc2 == data.data.profiles.education&&ruleForm.desc3 == data.data.profiles.skills&&
      ruleForm.desc4 == data.data.profiles.expectedSalary&&ruleForm.desc5 == data.data.profiles.expectedPosition){
        alert("You haven't modified any information yet")
        return
      }
      if(data.data&&data.data.profiles){
        let {data} = await request.post("user/updateProfile",ruleForm2)
        if(data.code == 200){
          alert("Information saved successfully")
          router.push("/homePage")
        } 
      }else{
        let {data} = await request.post("user/addProfile",ruleForm2)
        if(data.code == 200){
          alert("Information added successfully")
          router.push("/homePage")
        }
      } 
      // await request.post(`user/updateProfile`, { oldUsername, newUsername });
      // let  {data}= await request.post("user/updateProfile",{params:{"jobSeekerId":user.jobSeekerId,"newUsername":ruleForm.username,
      // "age":ruleForm.count,"workExperience":ruleForm.desc1,"education":ruleForm.desc2,"skills":ruleForm.desc3}})
      
    } else {
      return alert("Failed to modifications")
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


//文件上传
// import { genFileId } from 'element-plus'

// const upload = ref()

// const handleExceed = (files) => {
//   upload.value.clearFiles();
//   const file = files[0];
//   file.uid = genFileId();
//   upload.value.handleStart(file);
// };
// const submitUpload = () => {
//   upload.value.submit();
// };
</script>
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
  margin-left: 600px;
}

.fBtu{
  margin-left: 280px;
}
</style>