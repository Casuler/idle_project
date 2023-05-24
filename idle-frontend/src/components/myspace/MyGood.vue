<template>
    <el-card>
        <el-table :data="form.goodList">
            <el-table-column label="商品编号" width="110" align="center" sortable prop="id"></el-table-column>
            <el-table-column label="商品名称" width="200" align="center" prop="product_name"></el-table-column>
            <el-table-column label="价格" align="center" sortable prop="price"></el-table-column>
            <el-table-column label="分类" align="center" sortable prop="category"></el-table-column>
            <el-table-column label="商品介绍" width="200" align="center" prop="introduce"></el-table-column>
            <el-table-column label="商品图片" align="center" width="210" prop="picture">
                <template #default="scope">
                    <img style="width: 180px;height: 150px" :src="scope.row.picture" alt=""/>
                </template>
            </el-table-column>
            <el-table-column label="上架状态" width="110" align="center" sortable prop="status">
                <template #default="scope">
                    <div v-if="scope.row.status !== 0">
                        <el-tag effect="dark" type="success">
                            上架中
                        </el-tag>
                    </div>
                    <div v-else>
                        <el-tag effect="dark" type="danger">
                            已下架
                        </el-tag>
                    </div>
                </template>
            </el-table-column>
            <el-table-column label="操作" align="center" width="70">
                <template #default="scope">
                    <el-tooltip content="查看商品" placement="top">
                        <el-icon @click="handleSelect(scope.row.id,scope.row.status)" style="cursor:pointer;margin-right: 10px">
                            <Document/>
                        </el-icon>
                    </el-tooltip>
                    <el-tooltip content="下架商品" placement="top">
                        <el-icon @click="handleOffset(scope.row.id, scope.row.status)" style="cursor: pointer">
                            <Close/>
                        </el-icon>
                    </el-tooltip>
                </template>
            </el-table-column>

        </el-table>
    </el-card>
</template>

<script setup>
import {reactive, onMounted} from 'vue'
import {post} from "@/request/request";
import {useRoute} from 'vue-router'
import {Document, Close} from "@element-plus/icons-vue";
import router from "@/router";
import {ElMessage} from "element-plus";

const route = useRoute()
const id = route.query.publisher_id

const form = reactive({
    goodList:[],
    imageBox:[],
    search: ''
})

const getPublisherGood = () => {
    post("/api/goods/get-publisher-goods",{
        publisher_id: id
    },message => {
        form.goodList=message
        form.imageBox=form.goodList.picture
    })
}

const handleSelect = (goodId,status) => {
    router.push({
        path: `/product/${goodId}`,
        query: {
            id: goodId,
            publisher_id: id,
            uid: id,
            status: status
        }
    })
}

const handleOffset = (goodId, status) => {
    if(status === 0){
        ElMessage.warning('商品已下架，请不要重复操作！')
    } else {
        post('/api/goods/update-status',{
            status: 0,
            id: goodId
        }, message => {
            ElMessage.success(message)
        })
    }

}

onMounted(async () => {
    await getPublisherGood()
})
</script>

<style scoped>

</style>