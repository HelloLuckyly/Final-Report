/* 
专门用于存储工作Id信息的pinia
*/
import {defineStore} from 'pinia'

export const defineWorkId= defineStore(
    "WorkId",
    {
        state:()=>{
            return {
                campusRecruitmentId:0,
                employerId:0             
            }
        },
        getters:{

        },
        actions:{

        }
    }
)
