/* 
专门用于存储校园招聘信息的pinia

*/
import {defineStore} from 'pinia'

export const defineJobAlerts= defineStore(
    "jobAlerts",
    {
        state:()=>{
            return {
                jobAlertsList:[]
            }
        },
        getters:{

        },
        actions:{

        }
    }
)