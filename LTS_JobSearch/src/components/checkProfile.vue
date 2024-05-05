<script setup>
import { ref, reactive,onMounted,onUnmounted } from 'vue'
const centerDialogVisible = ref(false)
import { Search } from '@element-plus/icons-vue'

import request from'../utils/request'
import {useRouter} from 'vue-router'
const router = useRouter()
/* 导入pinia数据 */
import {defineUser} from "../store/userStore.js"
import{defineProfile} from "../store/profileStore.js"
let user = defineUser()
let profile = defineProfile()
const JobSeeker = reactive({
    jobSeekerId:user.jobSeekerId
})


//----------------------

 const input3 = ref('')
 const select = ref('')
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
// --------------------
import { computed} from 'vue'
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
onMounted(async ()=>{
    let {data} = await request.get("user/checkProfile",{params:{"jobSeekerId":user.jobSeekerId}})
    if(data.code == 200 ){
            profile.profileId =data.data.profiles.profileId        
            profile.workExperience =data.data.profiles.workExperience
            profile.education =data.data.profiles.education
            profile.skill =data.data.profiles.skills    
            profile.age =data.data.profiles.age
            profile.jobSeekerId =data.data.profiles.jobSeekerId  
            profile.gender= data.data.profiles.gender
            profile.hobby = data.data.profiles.hobby
            profile.realName = data.data.profiles.realName
            profile.expectedSalary = data.data.profiles.expectedSalary
            profile.expectedPosition = data.data.profiles.expectedPosition
          }else if(data.code == 505){
            router.push("/profile")         
          }
    })

onUnmounted(() => {
  // 在这里清除 Pinia 数据
  profile.$reset()
})

import {
  Check,
  Delete,
  Edit,
  Message,
  Star,
} from '@element-plus/icons-vue'

</script>

<template>
        <div>
            <div  class="common-layout">
                <el-header >
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
            </div>
        
            <div class="content" >
                <el-radio-group v-model="size" class="big">
                        <el-radio value="large">make it bigger</el-radio>
                        <el-radio value="default">Default</el-radio>
                        <el-radio value="small">make it smaller</el-radio>
                    </el-radio-group>
                         
                    <el-descriptions
                        title="My personal details"
                        direction="vertical"
                        :column="4"
                        :size="size"
                        border
                    >
                        <el-descriptions-item label="Real Name">{{ profile.realName }}</el-descriptions-item>
                        <el-descriptions-item label="Age">{{ profile.age }}</el-descriptions-item>
                        <el-descriptions-item label="Gender" :span="2">{{profile.gender}}</el-descriptions-item>
                        
                        <el-descriptions-item label="Hobbies"
                        >{{ profile.hobby }}
                        </el-descriptions-item>
                            
                        <el-descriptions-item label="Expected salary">
                              {{ profile.expectedSalary }}
                        </el-descriptions-item>

                        <el-descriptions-item label="Skills"
                        >{{ profile.skill }}
                        </el-descriptions-item>

                        <el-descriptions-item label=""
                        >
                        </el-descriptions-item>

                        <el-descriptions-item label="Education"
                        >{{ profile.education }}
                        </el-descriptions-item>

                        <el-descriptions-item label="Expected position"
                        >{{ profile.expectedPosition }}
                        </el-descriptions-item>

                        <el-descriptions-item label="Work Experience"
                        >{{ profile.workExperience }}
                        </el-descriptions-item>
                    </el-descriptions>
            </div>
            <div class="wenzi">
                If you would like to change your information click here.That's it.
            </div>
            
            <div class="button">
            <router-link to="/profile">
                    <el-button type="success" round>modifications</el-button>
            </router-link>
                
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
.el-descriptions {
  margin-top: 20px;
}
.big{
    margin-left: 500px;
}
.content{
    margin-top: 150px;
    margin-left: 200px;
}

.content img{
    width: 100px;
    height: 100px;
    object-fit: cover;
}
.wenzi{
  font-family: Lucida Sans Unicode; /* 使用 Indie Flower 艺术字体 */
  font-size: 20px; /* 设置字体大小 */
  color: #030314; /* 设置字体颜色 */
  margin-left: 400px;
  margin-top: 100px;

}
.button{
    margin-left: 920px;
    margin-top: 10px;

    
}
</style>
