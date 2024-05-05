/* 
专门用于存储工作Id信息的pinia
*/
import {defineStore} from 'pinia'

export const defineJobAlertById= defineStore(
    "JobAlertById",
    {
        state:()=>{
            return {
                campusRecruitmentId:0          
            }
        },
        getters:{

        },
        actions:{

        }
    }
)
