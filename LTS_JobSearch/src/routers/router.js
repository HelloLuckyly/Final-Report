import {createRouter,createWebHashHistory} from 'vue-router'


import Login from '../components/Login.vue'
import Regist from '../components/Regist.vue'
import HomePage from '../components/HomePage.vue';
import Profile from '../components/Profile.vue'
import BasePage from '../components/basePage.vue'
import EUserHomepage from '../components/EUserHomePage.vue'
import Eregist from '../components/Eregist.vue'
import Elogin from '../components/Elogin.vue'
import JobDetail from '../components/JobDetail.vue'
import CheckProfile from '../components/checkProfile.vue'
import Resume from '../components/Resume.vue'
import CampusRecruitment from '../components/CampusRecruitment.vue'
import RecruitmentDetail from '../components/RecruitmentDetail.vue';
import CompanyDetail from '../components/companyDetail.vue'
import SearchPage from '../components/searchPage.vue'
import JobAlerts from '../components/JobAlerts.vue'
import RecruitmentDetailForJobAlerts from '../components/RecruitmentDetailForJobAlerts.vue'
import ApplicationManagement from '../components/ApplicationManagement.vue'
import CompanyDetailForUser from '../components/companyDetailForUser.vue';
import ApplicationTracking from '../components/ApplicantTracking.vue'
import SearchByEmployer from '../components/SearchByEmployer.vue' 
import CheckResume from '../components/checkResume.vue'





import pinia from '../pinia.js'
import {defineUser} from '../store/userStore.js'
import {defineEUser} from '../store/eUserStore.js'
import { defineProfile } from '../store/profileStore';
import { defineCampusRecruitment } from '../store/campusRecruitmentStore';
import { defineWorkId } from '../store/workIdStore';
import { defineCompany } from '../store/companyStore';
import { defineSearchByIndustry } from '../store/searchByIndustry';
import { defineJobAlerts } from '../store/jobAlertsStore';
import { defineApplicationManagement } from '../store/applicationManagement';
import { defineCompanyReview } from '../store/companyReviewStore';
import { defineCompanyName } from '../store/companyNameStore';
import { defineEmployerIdListStore } from '../store/employerIdListStore';
import { defineEmployerNameList } from '../store/employerNameStore';
import  {defineJobSeekerIdByApply} from '../store/jobSeekerIdByApply'
// import { defineJobAlertById } from '../store/jobAlertByIdStore';


let user = defineUser(pinia)
let eUser = defineEUser(pinia)
let profile = defineProfile(pinia)
let campusRecruitment = defineCampusRecruitment(pinia)
let workId = defineWorkId(pinia)
let company = defineCompany(pinia)
let searchByIndustry= defineSearchByIndustry(pinia)
let jobAlerts = defineJobAlerts(pinia)
let applicationManagement = defineApplicationManagement(pinia)
let companyReview = defineCompanyReview(pinia)
let companyName = defineCompanyName(pinia)
let employerIdList =defineEmployerIdListStore(pinia)
let employerNameList = defineEmployerNameList(pinia)
let jobSeekerIdByApply = defineJobSeekerIdByApply(pinia)



const router= createRouter({
    history:createWebHashHistory(),
    routes:[
        {
            path:"/",
            redirect:"/HomePage"
        },
        {
            path:"/login",
            component:Login
        },
        {
            path:"/regist",
            component:Regist
        },
        {
            path:"/homePage",
            component:HomePage
        },
        {
            path:"/profile",
            component:Profile,
            meta: { requiresAuth: true } 
        },
        {
            path:"/basePage",
            component:BasePage
        },
        {
            path:"/eUserHomePage",
            component:EUserHomepage,
            meta: { requiresAuth: true } 
        },
        {
            path:"/eRegist",
            component:Eregist
        },
        {
            path:'/eLogin',
            component:Elogin
        },
        {
            path:'/jobDetail',
            component:JobDetail,
            meta: { requiresAuth: true } 
        },
        {
            path:'/checkProfile',
            component:CheckProfile,
            meta: { requiresAuth: true }
        },
        {
            path:'/resume',
            component:Resume,
            meta: { requiresAuth: true }
        },
        {
            path:'/campusRecruitment',
            component:CampusRecruitment,
            meta: { requiresAuth: true }
        },
        {
            path:'/recruitmentDetail',
            component:RecruitmentDetail,
            meta: { requiresAuth: true }
            
        },
        {
            path:'/companyDetail',
            component:CompanyDetail,
            meta: { requiresAuth: true }
        },
        {
            path:'/searchPage',
            component:SearchPage,
            // meta: { requiresAuth: true }
        },
        {
            path:'/jobAlerts',
            component:JobAlerts,
            meta: { requiresAuth: true }
        },
        {
            path:'/recruitmentDetailForJobAlerts',
            component:RecruitmentDetailForJobAlerts,
            meta: { requiresAuth: true }
        },
        {
            path:'/applicationManagement',
            component:ApplicationManagement,
            meta: { requiresAuth: true }
        },
        {
            path:'/companyDetailForUser',
            component:CompanyDetailForUser,
            meta: { requiresAuth: true }
        },
        {
            path:'/applicantTracking',
            component:ApplicationTracking,
            meta: { requiresAuth: true }
        },
        {
            path:'/searchByEmployer',
            component:SearchByEmployer,
            meta: { requiresAuth: true }
        },
        {
            path:'/checkResume',
            component:CheckResume,
            meta: { requiresAuth: true }
        }


    ]
})

//路由 的全局前置守卫 判断是否可以访问主页
router.beforeEach((to,from,next)=>{
    if(to.meta.requiresAuth){
        // 登陆过放行
        // 没登录 回到登录页
        if(user.username == '' && eUser.username == ''){
            next("/login")
        }else{
            next()
        }
    }else{
        next()
    }

})

export default router 
