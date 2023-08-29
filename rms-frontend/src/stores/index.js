import {reactive} from 'vue'
import {defineStore} from 'pinia'

export const useStore = defineStore('store', () => {
    const auth = reactive({
        userid: null,
        username: null,
        role: null
    })
    return {auth}
})