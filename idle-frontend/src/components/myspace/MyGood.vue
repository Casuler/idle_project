<template>
    <el-card>
        <el-table :data="form.goodList" border stripe fit>
            <el-table-column label="序号" type="index"></el-table-column>
            <el-table-column label="商品编号" prop="id"></el-table-column>
            <el-table-column label="商品名称"  prop="product_name"></el-table-column>
            <el-table-column label="价格"  prop="price"></el-table-column>
            <el-table-column label="分类" prop="category"></el-table-column>
            <el-table-column label="商品介绍" prop="introduce"></el-table-column>
            <el-table-column label="商品图片" prop="picture">
                <template #default="scope">
                    <img style="width: 180px;height: 150px" :src="scope.row.picture" alt=""/>
                </template>
            </el-table-column>
            <el-table-column label="上架状态" prop="status">
                <div v-if="form.goodList.status='1'">
                    <el-tag effect="dark" type="success">
                        上架中
                    </el-tag>
                </div>
                <div v-else>
                    <el-tag effect="dark" type="danger">
                        已下架
                    </el-tag>
                </div>
            </el-table-column>
        </el-table>
    </el-card>
</template>

<script setup>
import {reactive, onMounted} from 'vue'
import {post} from "@/request/request";
import {useRoute} from 'vue-router'

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

onMounted(async () => {
    await getPublisherGood()
})
</script>

<style scoped>

</style>