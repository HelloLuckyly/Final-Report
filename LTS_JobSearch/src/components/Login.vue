<script setup>
import { reactive, ref } from 'vue'
import request from'../utils/request'
import {useRouter} from 'vue-router'
import{ defineUser}  from '../store/userStore.js'

let user = defineUser()


const router = useRouter()
const ruleFormRef = ref()
const ruleForm = reactive({
  username: '',
  password: ''
  
})
const checkUsername = async(rule, value, callback) => {
  if (!value) {  
    return  callback(new Error('Please input the Username'))
  }
  let usernameReg= /^[a-zA-Z0-9]{5,10}$/
        if(!usernameReg.test(value)){
            // usernameMsg.value="格式有误"           
            return callback(new Error('Please enter the correct format'))
        }
        // 发送异步请求   继续校验用户名是否存在
        let {data} = await request.post(`user/checkUsernameUsed?username=${value}`)
        if(data.code != 505){           
            return callback("You're not registered")
        }
        return  callback()
    }


const validatePass = (rule, value, callback) => {
  if (value === '') {  
     callback(new Error('Please input the password'))
  } 
  let userPwdReg = /^[0-9]{6}$/
    if(!userPwdReg.test(value)){      
        callback(new Error('Please enter the correct format'))
       
    }
    callback()
  }


const rules = reactive({
  password: [{ validator: validatePass, trigger: 'blur' }],
  username: [{ validator: checkUsername, trigger: 'blur' }],
})

const submitForm = (formEl) => {
  if (!formEl) return
  formEl.validate(async(valid) => {
    if (valid) {
      console.log('submit!')
      let ruleForm1 ={
        username: ruleForm.username,
        password: ruleForm.password,
        defaultList:user.defaultList
            }
      let  {data}= await request.post("user/login",ruleForm1)
      if(data.code == 200){
            
            alert("Login Successful")
            // 获得登录的用户信息,更新到pinia中
            user.jobSeekerId =data.data.loginUser.jobSeekerId
            user.username = data.data.loginUser.username
            user.defaultList = data.data.loginUser.defaultList
            router.push("/HomePage")
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


</script>

<template>
    <div class="Regist">
      <div class="icon">
        <img src="/img/14.jpg" alt="Custom Image"><br>      
      </div>
      <div class="Star">
        LOVE STAR
      </div>
        <div class="top" >
          LOOK THE STAR JOB SEARCH WEBSITE
        </div>


        <div class="btu3">
          <router-link to="/login" style="margin-right: 10px;">
            <el-button type="primary" round>Job Seeker</el-button>
          </router-link>
            <router-link to="/Elogin" style="margin-left: 10px;">
              <el-button type="success" round>Employer</el-button>
            </router-link>
            
        </div>
        <div class="box">
          <div class="container">
              <div class="box-wrapper">  
            <el-form
                ref="ruleFormRef"
                style="max-width: 700px"
                :model="ruleForm"
                status-icon
                :rules="rules"
                label-width="70px"
                class="demo-ruleForm"   
                size=large  
                >
                <el-form-item label="Username" prop="username">
                <el-input v-model="ruleForm.username" type="username" autocomplete="off" />
                </el-form-item>
                <el-form-item label="Password" prop="password">
                <el-input v-model="ruleForm.password" type="password" autocomplete="off" />
                </el-form-item>
               
                <el-form-item>
                
                <el-button class="btu" @click="resetForm(ruleFormRef)">Reset</el-button>
                <el-button class="btu" type="primary" @click="submitForm(ruleFormRef)"
                    >Login</el-button>
                <router-link to="/Regist">
                    <el-button class="btu" >Sign in</el-button>
                </router-link>   
                </el-form-item>
            </el-form>
          </div>
            </div>
          <div class="right-image">
          <img src="/img/6.jpg" alt="Custom Image">
           </div>
           <div class="left">
            Minimalist style, efficient job search<br>
            Job seekers welcome!     
           </div>
           <div class="bottom">
            Look to the stars job website for your service, if you have all the questions, please consult the mailbox yangxiaoyix.163.com.
           </div>
        </div>
        
    </div>

   
</template>
<style scoped>
      .box{
        display: flex;
        margin-left: 870px;
        margin-top: 300px;  

      }
        .top{
          margin-left: 650px;
          margin-top: 50px;
          border: 1px solid #ccc;
          border-radius:10px; /* 设置圆角边框 */
          padding: 10px;
          box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); /* 添加阴影效果 */
          width: 450px;
          height: 30px;
          font-family: Lucida Sans Unicode; /* 使用 Indie Flower 艺术字体 */
         font-size: 24px; /* 设置字体大小 */
         color: #030314; /* 设置字体颜色 */
         position: relative;
        }
        .icon{
            left:420px; /* 调整图标距离左边的位置 */
            top: 50px; /* 调整图标距离顶部的位置 */
            position: absolute;
            border: none !important;
           
        }
        .Star{
          left:380px; /* 调整图标距离左边的位置 */
            top: 100px; /* 调整图标距离顶部的位置 */
            position: absolute;
            font-family: Lucida Sans Unicode; /* 使用 Indie Flower 艺术字体 */
        font-size: 24px; /* 设置字体大小 */
        color: #030314; /* 设置字体颜色 */
        
        }

        .icon img {
        width: 50px; /* 设置图片宽度 */
        height: 50px; /* 根据宽度等比例缩放高度 */
        border-radius: 40px 10px ; /* 将边框角变为圆形 */
      }
      .Regist{
        background: url("");
      }
      .btu{
        margin-left: 50px;
      }

      .right-image {
        position: fixed; /* 使用固定定位，让图片始终保持在浏览器窗口右侧 */
        top: 226px; /* 设置图片容器顶部距离浏览器窗口顶部的距离 */
        right: 750px; /* 设置图片容器右侧距离浏览器窗口右侧的距离 */
         /* 使用 translateY(-50%) 居中图片容器 */
      }

      .right-image img {
        width: 400px; /* 设置图片宽度 */
        height: 500px; /* 根据宽度等比例缩放高度 */
      }

      .left {
        position: fixed; /* 使用固定定位，让图片始终保持在浏览器窗口右侧 */
        top: 430px; /* 设置图片容器顶部距离浏览器窗口顶部的距离 */
        left: 160px; /* 设置图片容器右侧距离浏览器窗口右侧的距离 */
        transform: translateY(-50%); /* 使用 translateY(-50%) 居中图片容器 */
        font-family: Lucida Sans Unicode; /* 使用 Indie Flower 艺术字体 */
        font-size: 24px; /* 设置字体大小 */
        color: #030314; /* 设置字体颜色 */
      }


.container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 280px;
}

.box-wrapper {
  border: 1px solid #ccc;
  border-radius: 10px; /* 设置圆角边框 */
  padding: 20px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); /* 添加阴影效果 */
}

.demo-ruleForm .el-form-item {
  margin-bottom: 20px;
}

.btu {
  margin-left: 25px;
}
.btu3{
  position: absolute; /* 绝对定位 */
  top: 300px;
  left: 960px;
  margin-top: 50px;
  border: 2px solid #8092e4;
  border-radius:30px; /* 设置圆角边框 */
  padding: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); /* 添加阴影效果 */
  width: 220px;
  height: 30px;
}
.bottom{
        position: fixed; /* 使用固定定位，让图片始终保持在浏览器窗口右侧 */
        top: 930px; /* 设置图片容器顶部距离浏览器窗口顶部的距离 */
        left: 200px; /* 设置图片容器右侧距离浏览器窗口右侧的距离 */
        transform: translateY(-50%); /* 使用 translateY(-50%) 居中图片容器 */
        font-family: Lucida Sans Unicode; /* 使用 Indie Flower 艺术字体 */
        font-size: 21px; /* 设置字体大小 */
        color: #030314; /* 设置字体颜色 */

}
.Regist{
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 120px;
  background-image: url('/img/5.jpg'); /* 替换为你的图片路径 */
  background-size: cover;
  background-position: center;
  z-index: -1; /* 使图片在内容之后 */
}

</style>

