/* 
专门用于存储招聘者状态信息的pinia

*/
import {defineStore} from 'pinia'

export const defineEUser= defineStore(
    "loginUserE",
    {
        state:()=>{
            return {
               employerId:0,
               defaultList:6,
               username:'' 
            }
        },
        getters:{

        },
        actions:{

        }
    }
)
