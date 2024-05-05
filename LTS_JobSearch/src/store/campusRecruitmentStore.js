/* 
专门用于存储校园招聘信息的pinia

*/
import {defineStore} from 'pinia'

export const defineCampusRecruitment= defineStore(
    "campusRecruitment",
    {
        state:()=>{
            return {
                campusRecruitmentList:[]
            }
        },
        getters:{

        },
        actions:{

        }
    }
)