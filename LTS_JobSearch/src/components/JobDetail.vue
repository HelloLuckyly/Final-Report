<script setup>
import { Search, } from '@element-plus/icons-vue'
import{reactive,ref} from 'vue'
const select = ref('')
import request from'../utils/request'
import {defineEUser} from "../store/eUserStore.js"
let eUser = defineEUser()
import {useRouter} from 'vue-router'
let router = useRouter()
import {defineCampusRecruitment} from "../store/campusRecruitmentStore.js"
import {defineWorkId} from "../store/workIdStore"
let workId = defineWorkId()

onMounted(async ()=>{
    
    let {data} = await request.post("eUser/checkProfile",JobSeeker)
    if(data.code == 200 ){
            profile.profileId =data.data.profiles.profileId        
            profile.workExperience =data.data.profiles.workExperience
            profile.education =data.data.profiles.education
            profile.skill =data.data.profiles.skills    
            profile.age =data.data.profiles.age
            profile.jobSeekerId =data.data.profiles.jobSeekerId  
            profile.gender= data.data.profiles.gender
            profile.hobby = data.data.profiles.hobby
          }else if(data.code == 505){
            router.push("/profile")         
          }
    })
</script>

<template>
<div class="main">
        <div class="head">
        <el-radio-group v-model="radio1" size="large">
            <div class="left">
                <el-radio-button class="btu1" label="Campus Recruitment" value="Campus Recruitment" />
                <el-radio-button label="Recruitment for Government and Enterprises" value="Recruitment for Government and Enterprises" />
                <el-radio-button label="Overseas Recruitment" value="Overseas Recruitment" />
            </div>
            <div class="right">
                <router-link to="/homePage">
                    <el-radio-button label=" home page" value=" home page" />
                </router-link>                  
                <el-radio-button label=" Profile Creation" value=" Profile Creation" />
                <el-radio-button label="Creating Resumes" value="Creating Resumes" />
                <el-radio-button label="Job Alerts" value="Job Alerts" />
            </div>
            </el-radio-group>
        </div>

      
        <div class="head1">
            <h2>Java developer</h2>
            <text>Today's Update</text>
            <h2 class="red-text">6000-8000RMB</h2>
            <text>location:Chengdu</text>
            <text> | </text>
            <text>Experience Requirements:no</text>
            <text> | </text>
            <text>Academic Qualifications:undergraduate</text>
            <text> | </text>
            <text>6 persons required</text>
        </div>
        <div class="middle">
            <h3>Job Description</h3>
            <text>
                1.According to the development specification and process to independently complete the module design, coding, testing and related documents;<br>
                2.Demonstrated ability in object-oriented analysis and design;<br>
                3.Good code writing, commenting and unit testing habits;<br>
                4.Hard thinking, proactive, strong execution, with good communication and learning skills.
            </text>
            <h3>Position Benefits</h3>
            <text>
                Holiday benefits, double weekends.
            </text>
            <h3>
                Interview Process
            </h3>
            <text>
                Positions are taken offline interviews, no telephone interviews and video interviews, do not privately add job seekers QQ, WeChat.
            </text>
        </div>
        <div>
            {{ workId.campusRecruitmentId }}
        </div>
</div>




</template>

<style scoped>
.head{
    background-color: rgb(171, 238, 238);
}
.head1{
    margin-left: 400px;
    margin-top:90px;
}
.left{
    margin-left: 250px;
    margin-top: 15px;

}
.right{
    margin-left: 300px;
    margin-top: 15px;
}
.back{
    background-color: rgb(159, 201, 230);
}
.red-text {
  color: red;
}
.middle{
    margin-left: 400px;
    margin-top:60px
}
</style>
