<script setup>
import { reactive, ref } from 'vue'
import request from'../utils/request'
import {useRouter} from 'vue-router'
import {defineEUser} from "../store/eUserStore.js"
let eUser = defineEUser()
const router = useRouter()
const ruleFormRef = ref()

const checkUsername = async(rule, value, callback) => {
  if (!value) {  
    return  callback(new Error('Please input the Username'))
  }
  let usernameReg= /^[a-zA-Z0-9]{5,10}$/
        if(!usernameReg.test(value)){
            // usernameMsg.value="格式有误"           
            return callback(new Error('Please enter the correct format'))
        }
        // 发送异步请求   继续校验用户名是否被占用
        let {data} = await request.post(`eUser/checkUsernameUsed?username=${value}`)
        if(data.code != 200){           
            return callback(new Error('Sorry, the username is already taken'))
        }
        return   callback()
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

const validatePass2 = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('Please input the password again'))
    
  } 
  let userPwdReg = /^[0-9]{6}$/
        if(!userPwdReg.test(value)){
            callback(new Error('Please enter the correct format'))
        }
        if(value !== ruleForm.password){
           callback(new Error('Please enter the same password'))
        }
       callback()
}

const ruleForm = reactive({
  username: '',
  password: ''
})

const rules = reactive({
  password: [{ validator: validatePass, trigger: 'blur' }],
  checkPass: [{ validator: validatePass2, trigger: 'blur' }],
  username: [{ validator: checkUsername, trigger: 'blur' }],
})

const submitForm = (formEl) => {
  if (!formEl) return
  formEl.validate(async(valid) => {
    if (valid) {
      console.log('submit!')
      
      // 构造新的ruleForm
      let ruleForm2 = {
        username: ruleForm.username,
        password: ruleForm.password,
        defaultList:eUser.defaultList
      }

      let  {data}= await request.post("eUser/regist",ruleForm2)
      if(data.code == 200){
            // 注册成功跳转 登录页
            alert("Registration is successful, go ahead and log in!")
            router.push("/Elogin")
          }else{
            alert("Failed to register")
          }
    } else {
      return alert("Failed to register")
    }
  })
}

const resetForm = (formEl) => {
  if (!formEl) return
  formEl.resetFields()
}   




//边框
const radiusGroup = ref([
  {
    name: 'Round Radius',
    type: 'round',
  },
])

const getValue = (type) => {
  const getCssVarValue = (prefix, type) =>
    getComputedStyle(document.documentElement).getPropertyValue(
      `--el-${prefix}-${type}`
    )
  return getCssVarValue('border-radius', type)
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
          <router-link to="/regist"  style="margin-right: 10px;">
            <el-button type="primary" round>Job Seeker</el-button>
          </router-link>
          <router-link to="/eRegist" style="margin-left: 10px;">
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
                label-width="90px"
                class="demo-ruleForm" 
                size=large  
                >
                <el-form-item label="Username" prop="username">
                <el-input v-model="ruleForm.username" type="username" autocomplete="off" />
                </el-form-item>
                <el-form-item label="Password" prop="password">
                <el-input v-model="ruleForm.password" type="password" autocomplete="off" />
                </el-form-item>
                <el-form-item label="Confirm" prop="checkPass">
                <el-input
                    v-model="ruleForm.checkPass"
                    type="password"
                    autocomplete="off"
                />
                </el-form-item>
               
                <el-form-item>
               
                <el-button  class="btu" @click="resetForm(ruleFormRef)">Reset</el-button>
                <el-button class="btu" type="primary" @click="submitForm(ruleFormRef) " 
                    >Sign in</el-button>
                <router-link to="/login">
                    <el-button class="btu">Login</el-button>
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
            Welcome Employer!           
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
        margin-left: 830px;
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
      .btu{
        margin-left: 50px;
      }
      .right-image {
        position: fixed; /* 使用固定定位，让图片始终保持在浏览器窗口右侧 */
        top: 430px; /* 设置图片容器顶部距离浏览器窗口顶部的距离 */
        right: 660px; /* 设置图片容器右侧距离浏览器窗口右侧的距离 */
        transform: translateY(-50%); /* 使用 translateY(-50%) 居中图片容器 */
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
  margin-left: 20px;
}
.btu3{
  position: absolute; /* 绝对定位 */
  top: 250px;
  left: 910px;
  margin-top: 50px;
  border: 2px solid #8fb3e7;
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