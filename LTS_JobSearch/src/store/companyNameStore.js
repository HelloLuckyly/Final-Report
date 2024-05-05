/* 
专门用于存储公司信息的pinia

*/
import {defineStore} from 'pinia'

export const defineCompanyName= defineStore(
    "CompanyName1",
    {
        state:()=>{
            return {
                companyName:''
            }
        },
        getters:{

        },
        actions:{

        }
    }
)