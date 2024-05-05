/* 
专门用于存储申请信息的pinia

*/
import {defineStore} from 'pinia'

export const defineEmployerIdListStore= defineStore(
    "employerIdList",
    {
        state:()=>{
            return {
                employerIdList:[]
            }
        },
        getters:{

        },
        actions:{

        }
    }
)