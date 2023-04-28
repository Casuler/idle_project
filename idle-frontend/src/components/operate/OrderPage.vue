<template>
    <div class="content">
        <div class="title">
            <span>订单</span>
        </div>
        <div class="order-container">
            <el-table :data="form.goodList" border stripe fit>
                <el-table-column label="商品名称"  prop="product_name"></el-table-column>
                <el-table-column label="价格"  prop="price"></el-table-column>
                <el-table-column label="分类" prop="category"></el-table-column>
                <el-table-column label="商品介绍" prop="introduce"></el-table-column>
                <el-table-column label="商品图片" prop="picture">
                    <template #default="scope">
                        <img style="width: 180px;height: 150px" :src="scope.row.picture" alt=""/>
                    </template>
                </el-table-column>
            </el-table>
            <div class="payment">
                <h3>支付方式</h3>
                <el-radio-group  v-model="form.payment" @click="createVisible=true">
                    <el-radio-button label="支付宝">
                        <i class="iconfont icon-zhifubaozhifu"/>
                        支付宝
                    </el-radio-button>
                    <el-radio-button label="微信">
                        <i class="iconfont icon-weixinzhifu"/>
                        微信
                    </el-radio-button>
                    <el-radio-button label="线下交易">
                        <i class="iconfont icon-zhifu"/>
                        线下交易
                    </el-radio-button>
                </el-radio-group>
            </div>
            <div class="shipping-address">
                <h3>收货地址</h3>
                <div class="address">
                    地址：{{form.user.address}}
                </div>
                <div class="addressee">
                    收件人：{{form.user.nickname}}
                </div>
                <el-button type="warning" plain @click="dialogVisible = true">修改收货地址</el-button>
                <el-dialog v-model="dialogVisible"
                           title="修改收货地址"
                           width="30%">
                    <el-input type="text" v-model="form.address" placeholder="新的收货地址" :prefix-icon="House" />
                    <template #footer>
                        <span class="dialog-footer">
                        <el-button @click="dialogVisible = false">取消</el-button>
                        <el-button type="primary" @click="upgradeAddress();dialogVisible=false">
                            确认
                        </el-button>
                        </span>
                    </template>
                </el-dialog>
            </div>
            <div class="createOrder">
               <el-button type="danger" :disabled="!createVisible" @click="setOrder()">创建订单</el-button>
            </div>
        </div>
    </div>
</template>

<script setup>
import { reactive, onMounted, ref } from 'vue'
import {House} from '@element-plus/icons-vue'
import {ElMessage} from 'element-plus'
import {get, post} from "@/request/request";
import {useRoute} from 'vue-router'
import '@/assets/icon/iconfont.css'
import router from "@/router";

const route = useRoute()

const goodId = route.query.id
const goodSeller = route.query.seller

const form = reactive({
    orderList: [],
    goodList: [],
    user:[],
    payment: '',
    price: '',
    address: ''
})

const dialogVisible = ref(false)
const createVisible = ref(false)

const upgradeAddress = () => {
post('/api/auth/upgrade-address',{
    address: form.address,
    id: form.user.id
    },message => {
    ElMessage.success("地址修改成功")
    form.user.address=message
})
}
const setOrder = () => {
    updateStatus()
    post('/api/orders/set-order',{
        id: goodId,
        seller: goodSeller,
        buyer: form.user.nickname,
        payment: form.payment,
        price: form.price,
        shipping_address: form.user.address
    }, message => {
        ElMessage.success(message)
        router.push('/success')
    })
}

const updateStatus = () =>{
    post('/api/goods/update-status',{
        id: goodId,
    },message =>{
        console.log(message)
    })
}

const getGoodById = () => {
    post('/api/goods/find-goods',{
        id: goodId
    },message => {
        form.goodList = message
    })
}

const getMe = () => {
    get('/api/user/me',(message)=> {
        form.user = message
    })
}

const getPrice = () => {
    post('/api/goods/get-price',{
        id: goodId
    },message => {
        form.price = message
    })
}

onMounted(async () => {
    await getGoodById()
    await getMe()
    await getPrice()
})
</script>

<style lang="less" scoped>
.content{
  width: 1140px;
  background-color: #fff;
  margin: 20px auto;
  display: flex;
  flex-direction: column;
  border-radius: 12px;
}

.title{
  width: 100%;
  line-height: 60px;
  background-color: #85d0fc;
  border-radius: 12px;
  span{
    margin-left: 20px;
    font-size: 24px;
    font-weight: bold;
    font-family: "Source Han Sans SC",serif;
  }
}

.order-container{
    width: 100%;
    border-radius: 12px;
    .payment{
        width: 100%;
        h3{
            margin-left: 20px;
        }
        .el-button{
            margin-left: 20px;
            width: 100px;
        }
    }
    .shipping-address{
        width: 100%;
        margin-left: 20px;
        .address{
            font-size: 18px;
            font-family: "Source Han Sans SC", serif;
        }
        .addressee{
            font-size: 18px;
            font-family: "Source Han Sans SC", serif;
        }
        .dialog-footer button:first-child {
            margin-right: 10px;
        }
        .el-button{
            margin-top: 10px;
            width: 100px;
        }
    }
    .createOrder{
        margin-top: 20px;
        margin-bottom: 50px;
        width: 100%;
        text-align: center;
        .el-button{
            width: 200px;
        }
    }

}
</style>