/* 
专门用于存储招聘者状态信息的pinia

*/
import {defineStore} from 'pinia'

export const defineProfile= defineStore(
    "proflie",
    {
        state:()=>{
            return {
               profileId:0,
               workExperience:'',
               education:'',
               skill:'',
               age:0,
               jobSeekerId:0,
               gender:'',
               hobby:'',
               realName:'',
               expectedSalary:'',
               expectedPosition:''
            }
        },
        getters:{

        },
        actions:{

        }
    }
)
