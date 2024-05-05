/* 
专门用于存储公司信息的pinia

*/
import {defineStore} from 'pinia'

export const defineCompanyReview= defineStore(
    "CompanyReview",
    {
        state:()=>{
            return {
                companyReviewId:0
            }
        },
        getters:{

        },
        actions:{

        }
    }
)