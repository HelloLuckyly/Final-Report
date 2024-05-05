/* 
专门用于存储申请信息的pinia

*/
import {defineStore} from 'pinia'

export const defineCompanyReviewList= defineStore(
    "companyReviewList",
    {
        state:()=>{
            return {
                companyReviewList:[]
            }
        },
        getters:{

        },
        actions:{

        }
    }
)