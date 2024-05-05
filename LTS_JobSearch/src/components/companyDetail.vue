<script setup>


const colors = ref(['#99A9BF', '#F7BA2A', '#FF9900']) // same as { 2: '#99A9BF', 4: { value: '#F7BA2A', excluded: true }, 5: '#FF9900' }

import { Search } from '@element-plus/icons-vue'
import{ref,reactive,computed,onMounted,onUnmounted} from'vue'
import { defineEUser } from '../store/eUserStore'; 
import request from'../utils/request'
import {useRouter} from 'vue-router'
import {defineCompanyName} from '../store/companyNameStore'
    let companyName = defineCompanyName()

    import {defineCompany} from '../store/companyStore'
    let company2 = defineCompany()
    import {defineCompanyReviewList} from '../store/companyReviewListStore'
let companyReviewList = defineCompanyReviewList()

import { defineEmployerIdListStore } from '../store/employerIdListStore';
let employerIdList = defineEmployerIdListStore()

let router = useRouter()
let eUser = defineEUser()
const select = ref('')

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
const CompanyDetail = reactive({
  companyName: '',
  companyAddress: '',
  specificAddress: '',
  companyDescription: '',
  companyCulture: '',
  jobOpportunities: ''
})

    //挂载完毕后,立刻查询信息,赋值给pinia
    onMounted(async ()=>{
      showCompanyDetail()
      findCompanyReviewByEmployerId() 
      showALLCompanyReview()
      
      console.log(companyName.companyName)
      console.log(companyReviewList.companyReviewList)   
      console.log(employerIdList) 
    })
    onUnmounted(() => {
  // 在这里清除 Pinia 数据
  companyReviewList.$reset()
  employerNameList.$reset()
})
    
    // async function showCompanyDetail(){
    //     let {data} = await request.get("eUser/findCompanyByEmployerId",{params:{"employerId":eUser.employerId}})
    //     if(!data.data.company.companyId){
    //         router.push("/eUserHomePage")
    //     }
    // }


    async function showCompanyDetail(){
        // 发送异步请求,获得当前用户的所有记录
        let {data} = await request.get("eUser/findCompanyByEmployerId",{params:{"employerId":eUser.employerId}})
         // 检查返回的数据对象是否存在以及 company 对象是否存在
    if (data && data.data && data.data.company) {
        // 从返回的数据中获取 company 对象
        let companyData = data.data.company;
        
        // 判断 company 对象的各个属性是否存在，如果存在则赋值给 CompanyDetail 对应的属性
        companyName.companyName = companyData.companyName || '';
        CompanyDetail.companyName = companyData.companyName || '';
        CompanyDetail.companyDescription = companyData.companyDescription || '';
        CompanyDetail.companyCulture = companyData.companyCulture || '';
        CompanyDetail.companyAddress = companyData.companyAddress || '';
        CompanyDetail.specificAddress = companyData.specificAddress || '';
        CompanyDetail.jobOpportunities = companyData.jobOpportunities || '';
    } else {
        router.push("/eUserHomePage");
    }
    }

    const value2 = ref(null)
    async function findCompanyReviewByEmployerId() {
    let { data } = await request.get("eUser/findCompanyReviewByEmployerId", { params: { "employerId": eUser.employerId } });
    if (data && data.data && data.data.review) {
        review.companyReviewId = data.data.review.companyReviewId || '';
        form.companyReview = data.data.review.companyReview;
    } else {
        // 如果 data.data.review 为 null 或未定义，将 form.companyReview 设置为默认值或者其他适当的值
        form.companyReview = ''; // 这里可以设置默认值
    }
}
import { defineEmployerNameList } from '../store/employerNameStore';
let employerNameList = defineEmployerNameList();


async function showALLCompanyReview() {
  let { data } = await request.get("eUser/findAllCompanyReview", { params: { "companyName": companyName.companyName } });
    if (data && data.data && data.data.employerIdList) {
       employerIdList.employerIdList = data.data.employerIdList;
        for (const employerId of employerIdList.employerIdList) {
            const response1 = await request.get("eUser/findCompanyReviewByEmployerId", { params: { "employerId": employerId } });
            if (response1 && response1.data && response1.data.data && response1.data.data.review) {
                const companyReview = response1.data.data.review;
                companyReviewList.companyReviewList.push(companyReview);
            }          
          const response2 = await request.get("eUser/findNameByEmployerId", { params: { "employerId": employerId } });
          if(response2 && response2.data && response2.data.data && response2.data.data.employer){
            const employerName = response2.data.data.employer.username;
            employerNameList.employerNameList.push(employerName);
            }

        }
        return companyReviewList.companyReviewList ;
    } else {
        return []; // 或者根据需要返回其他默认值
    }

}


//------------------comment-------------------------
import {defineCompanyReview} from '../store/companyReviewStore'
let review = defineCompanyReview()
const dialogFormVisible = ref(false)
const formLabelWidth = '140px'

const form = reactive({
  companyReview: '',
  rating:0
})

const formSize = ref('default')
const ruleFormRef = ref()
const rules = reactive({
    companyReview: [
    { required: true, message: 'Please input Company Review', trigger: 'blur' },
    { min: 1, max: 50, message: 'Length should be 1 to 50', trigger: 'blur' },
  ]
})
const submitForm = async (formEl) => {
  if (!formEl) return
  await formEl.validate(async(valid) => {
    if (valid) {
      console.log('submit!')
      // 获取当前本机时间
      const currentTime = new Date()

      // 将时间格式化为您所需的格式，例如：YYYY-MM-DD HH:mm:ss
      const formattedTime = `${currentTime.getFullYear()}-${String(currentTime.getMonth() + 1).padStart(2, '0')}-${String(currentTime.getDate()).padStart(2, '0')} ${String(currentTime.getHours()).padStart(2, '0')}:${String(currentTime.getMinutes()).padStart(2, '0')}:${String(currentTime.getSeconds()).padStart(2, '0')}`
      let form1 ={
        companyReview:form.companyReview,
        employerId:eUser.employerId,
        companyReviewTime:formattedTime,
        companyId :company2.companyId,
        rating:value2.value
        }
        let response;
        let {data} = await request.get("eUser/findCompanyReviewByEmployerId",{params:{"employerId":eUser.employerId}})  

        if (data && data.data && data.data.review) {
    if (data.data.review.companyReview === form.companyReview && data.data.review.rating === value2) {
        alert("You have not modified your company review content yet");
        return;
            }
               
        if(review.companyReviewId == 0){        
                response== await request.post("eUser/saveCompanyReview",form1)                      
                    alert("Submit Successful")
                    form.companyReview=data.data.review.companyReview
                }else{            
                    response== await request.post("eUser/updateCompanyReview",form1)
                    alert("Update Successful")          
                    router.push("/companyDetail")
                }

              }else{
                response== await request.post("eUser/saveCompanyReview",form1)                      
                    alert("Submit Successful")
                    form.companyReview=data.data.review.companyReview
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

<template>
    <div class ="common-layout">
        <el-header >
            <div class="back">
                <el-radio-group v-model="radio1" size="large">
                <div class="left">
                  <router-link to="/campusRecruitment">
                    <el-radio-button class="btu1" label="Post a position" value="Post Campus Recruitment" />
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

        <div class="companyDetails">
            <el-descriptions
                title="Company Details"
                direction="vertical"
                :column="4"
                :size="size"
                border
            >
                <el-descriptions-item label="Company Name">{{CompanyDetail.companyName}}</el-descriptions-item>
                <el-descriptions-item label="Company Address">{{CompanyDetail.companyAddress}}</el-descriptions-item>
                <el-descriptions-item label="Specific Address" :span="2">{{CompanyDetail.specificAddress}}</el-descriptions-item>
                <el-descriptions-item label="Company Description" :span="2">{{CompanyDetail.companyDescription}}</el-descriptions-item>
                <el-descriptions-item label="Company Culture" :span="2">{{CompanyDetail.companyCulture}}</el-descriptions-item>
                <el-descriptions-item label="Job Opportunities" :span="2">{{CompanyDetail.jobOpportunities}}</el-descriptions-item>
            </el-descriptions>
                    </div>


                    

                    <div class="post">    
                        <el-button plain @click="dialogFormVisible = true" type="primary" v-if="eUser.username != ''">
                            Post a company review
                        </el-button>
                    </div>
                    <el-dialog v-model="dialogFormVisible" title="Post a company review" width="500"  ref="ruleFormRef" :rules="rules">
                      <el-form ref="ruleFormRef"
                        style="max-width: 600px"
                        :model="form"
                        :rules="rules"
                        label-width="auto"
                        class="demo-ruleForm"
                        :size="formSize"
                        status-icon>

                        <el-form-item label="Company Review" prop="companyReview" :label-width="formLabelWidth" >
                          <el-input v-model="form.companyReview" autocomplete="off" />
                        </el-form-item>
                        <el-form-item label="Please mark:" prop="rating" :label-width="formLabelWidth" >
                          <span class="demonstration"></span>
                          <el-rate v-model="value2" :colors="colors" autocomplete="off" />                    
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
                        
                    <div class="comment">
                      <el-descriptions title="Company Review" :column="4" border v-for="companyReview,index in companyReviewList.companyReviewList" :key="index">
                          <el-descriptions-item
                            label="Username"
                            label-align="right"
                            align="center"  
                            label-class-name="my-label"
                            class-name="my-content"
                            width="200px"                          
                            >{{employerNameList.employerNameList[index]}}</el-descriptions-item
                          >
                          <el-descriptions-item label="Company Review" label-align="right" align="center"
                            >{{ companyReview.companyReview }}</el-descriptions-item
                          >
                          <el-descriptions-item label="Modification time" label-align="right" align="center"
                            >{{companyReview.companyReviewTime}}</el-descriptions-item
                          >
                          <el-descriptions-item label="Rating(0-5)" label-align="right" align="center"
                            >{{companyReview.rating}}</el-descriptions-item
                          >
                        </el-descriptions>
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
.companyDetails{
    margin-left: 100px;
    margin-top: 120px;
}
:deep(.my-label) {
  background: var(--el-color-success-light-9) !important;
}
:deep(.my-content) {
  background: var(--el-color-danger-light-9);
}
.comment{
  margin-top: 40px;
  margin-left: 100px;
  position: relative;
}
.post{
position: absolute;
left: 2200px;
top: 490px;
}
.demo-rate-block {
  padding: 10px 10px;
  text-align: center;
  border-right: solid 1px var(--el-border-color);
  display: inline-block;
  width: 29%;
  box-sizing: border-box;
}
.demo-rate-block:last-child {
  border-right: none;
}
.demo-rate-block .demonstration {
  display: block;
  color: var(--el-text-color-secondary);
  font-size: 5px;
  margin-bottom: 5px;
}
</style>