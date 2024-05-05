/* 
专门用于存储申请信息的pinia

*/
import {defineStore} from 'pinia'

export const defineApplicationManagement= defineStore(
    "applicationManagement",
    {
        state:()=>{
            return {
                applicationManagementList:[]
            }
        },
        getters:{

        },
        actions:{

        }
    }
)