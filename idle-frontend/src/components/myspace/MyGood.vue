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
import {reactive, onMounted, ref} from 'vue'
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

const multipleSelection = ref()

const handleSelectionChange = (val) => {
    multipleSelection.value = val
}

onMounted(async () => {
    await getPublisherGood()
})
</script>

<style scoped>

</style>