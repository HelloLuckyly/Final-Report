<template>
    <div class="common-layout">
      <el-container>
        <el-header >
            <div class="back">
                <el-radio-group v-model="radio1" size="large">
                <div class="left">
                  <router-link to="/campusRecruitment">
                    <el-radio-button class="btu1" label="Post Campus Recruitment" value="Post Campus Recruitment" />
                  </router-link>
                    
                    <el-radio-button label="Post Recruitment for Government and Enterprises" value="Post Recruitment for Government and Enterprises" />
                    <el-radio-button label="Post Overseas Recruitment" value="Post Overseas Recruitment" />
                </div>
                <div class="right">
                    <router-link to="/EUserHomePage">
                      <el-radio-button label=" home page" value=" home page" />
                    </router-link>      
                    
                    <el-radio-button label=" Company Profile" value=" Company Profile" />
  
                    <el-radio-button label="Applicant Tracking" value="Applicant Tracking" />
                    <el-radio-button label="Communication Tools" value="Communication Tools" />
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
                <el-option label="Positions" value="1" />
                <el-option label="Company" value="2" />
                </el-select>
            </template>
            <template #append>
                <el-button :icon="Search" />
            </template>
            </el-input>
        </div>
        </el-header>
        </el-container>
        <div>
            
        </div>
    </div>
</template>

<script setup>

import { Search,ref } from '@element-plus/icons-vue'
const select = ref('')
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
    margin-top: 15px;

}
.right{
    margin-left: 300px;
    margin-top: 15px;
}
.mt-4{
    margin-left: 600px;
    margin-top: 20px;

}
</style>