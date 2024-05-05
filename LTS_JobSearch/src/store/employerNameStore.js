/* 
专门用于存储信息的pinia

*/
import {defineStore} from 'pinia'

export const defineEmployerNameList= defineStore(
    "employerNameList",
    {
        state:()=>{
            return {
                employerNameList:[]
            }
        },
        getters:{

        },
        actions:{

        }
    }
)