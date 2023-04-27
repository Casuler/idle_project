<template>
  <el-card>
      <el-row :gutter="20">
          <el-col :span="8">
              <el-input placeholder="请输入订单编号" v-model="form.search">
                  <template #suffix>
                      <el-icon style="cursor: pointer" @click="searchOrder"><Search /></el-icon>
                  </template>
              </el-input>
          </el-col>
      </el-row>

      <el-table :data="form.orderList" border stripe>
          <el-table-column label="序号" type="index"></el-table-column>
          <el-table-column label="订单编号" prop="id"></el-table-column>
          <el-table-column label="订单价格(元)" prop="price"></el-table-column>
          <el-table-column label="卖家"  prop="seller"></el-table-column>
          <el-table-column label="买家"  prop="buyer"></el-table-column>
          <el-table-column label="支付方式" prop="payment"></el-table-column>
          <el-table-column label="订单状态" prop="status">
              <div v-if="form.orderList.status='1'">
                  <el-tag effect="dark" type="success">
                      已完成
                  </el-tag>
              </div>
              <div v-else>
                  <el-tag effect="dark" type="danger">
                      未完成
                  </el-tag>
              </div>
          </el-table-column>
          <el-table-column label="下单时间" prop="create_time"></el-table-column>
      </el-table>
  </el-card>
</template>

<script setup>
import {reactive, onMounted} from 'vue'
import {post} from "@/request/request";
import {useRoute} from 'vue-router'
import {Search} from '@element-plus/icons-vue'
import {ElMessage} from 'element-plus'

const route = useRoute()
const name = route.query.nickname
const form = reactive({
    orderList:[],
    search: ''
})

const getOwnOrder = () => {
    post('/api/orders/get-own-order',{
        nickname: name
    },message => {
        form.orderList=message
    })
}

onMounted(async () => {
    await getOwnOrder()
})

const searchOrder = () => {
    post('/api/orders/get-id-order',{
        id: form.search,
        nickname: name
    },message =>{
        ElMessage.success("搜索成功")
        form.orderList = message
    })
}

</script>



<style scoped>

</style>