import { reactive} from 'vue'
import { defineStore } from 'pinia'

export const useStore = defineStore('store', () => {
  const auth = reactive({
    user: null
  })
  const goods = reactive({
    good: null
  })
  return  { auth, goods }
})
