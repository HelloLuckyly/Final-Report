/* 
专门用于存储校园招聘信息的pinia

*/
import {defineStore} from 'pinia'

export const defineSearchByIndustry= defineStore(
    "searchByIndustry",
    {
        state:()=>{
            return {
                searchByIndustryList:[]
            }
        },
        getters:{

        },
        actions:{

        }
    }
)