/* 
专门用于存储Id信息的pinia
*/
import {defineStore} from 'pinia'

export const defineJobSeekerIdByApply= defineStore(
    "jobSeekerIdByApply",
    {
        state:()=>{
            return {
                jobSeekerId:0,             
            }
        },
        getters:{

        },
        actions:{

        }
    }
)
