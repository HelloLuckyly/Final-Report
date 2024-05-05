/* 
专门用于存储公司信息的pinia

*/
import {defineStore} from 'pinia'

export const defineCompany= defineStore(
    "Company",
    {
        state:()=>{
            return {
                companyId:0
            }
        },
        getters:{

        },
        actions:{

        }
    }
)