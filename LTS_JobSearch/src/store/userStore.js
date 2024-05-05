/* 
专门用于存储用户状态信息的pinia

*/
import {defineStore} from 'pinia'

export const defineUser= defineStore(
    "loginUser",
    {
        state:()=>{
            return {
               jobSeekerId:0,
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
