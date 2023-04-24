<template>
    <div class="order-detail-box">
        <header :name="'订单详情'" @callback="close"></header>
        <div class="order-status">
            <div class="status-item">
                <label>订单状态：</label>
                <span>{{ state.detail.orderStatusString }}</span>
            </div>
            <div class="status-item">
                <label>订单编号：</label>
                <span>{{ state.detail.orderNo }}</span>
            </div>
            <div class="status-item">
                <label>下单时间：</label>
                <span>{{ state.detail.createTime }}</span>
            </div>
            <el-button v-if="state.detail.orderStatus == 3" style="margin-bottom: 10px" color="#1baeae" block @click="handleConfirmOrder(state.detail.orderNo)">确认收货</el-button>
            <el-button v-if="state.detail.orderStatus == 0" style="margin-bottom: 10px" color="#1baeae" block @click="showPayFn">去支付</el-button>
            <el-button v-if="!(state.detail.orderStatus < 0 || state.detail.orderStatus == 4)" block @click="handleCancelOrder(state.detail.orderNo)">取消订单</el-button>
        </div>
        <div class="order-price">
            <div class="price-item">
                <label>商品金额：</label>
                <span>¥ {{ state.detail.totalPrice }}</span>
            </div>
            <div class="price-item">
                <label>配送方式：</label>
                <span>普通快递</span>
                <span>线下交易</span>
            </div>
        </div>
        <el-card
                v-for="item in state.detail.newBeeMallOrderItemVOS"
                :key="item.goodsId"
                style="background: #fff"
                :num="item.goodsCount"
                :price="item.sellingPrice"
                desc="全场包邮"
                :title="item.goodsName"
                :thumb="$filters.prefix(item.goodsCoverImg)"
        />
        <el-popover
                v-model:show="state.showPay"
                position="bottom"
                :style="{ height: '24%' }"
        >
            <div :style="{ width: '90%', margin: '0 auto', padding: '20px 0' }">
                <el-button :style="{ marginBottom: '10px' }" color="#1989fa" block @click="handlePayOrder(state.detail.orderNo, 1)">支付宝支付</el-button>
                <el-button color="#4fc08d" block @click="handlePayOrder(state.detail.orderNo, 2)">微信支付</el-button>
                <el-button color="#4fc08d" block @click="handlePayOrder(state.detail.orderNo, 2)">线下支付</el-button>
            </div>
        </el-popover>
    </div>
</template>

<script setup>
import { reactive, toRefs, onMounted } from 'vue'

const state = reactive({
    detail: {},
    showPay: false
})
</script>