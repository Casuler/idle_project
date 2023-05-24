<template>
    <div class="pay-success">
        <div class="success">
            <h3>
                <img src="@/assets/images/icon/right.png" width="32" height="32" alt=""/>
                恭喜您，订单创建成功！请与卖家联系完成商品交易！
            </h3>
            <div class="paydetail">
                <el-button class="iconfont icon-rollback" @click="goHome">继续购物</el-button>
            </div>
        </div>
    </div>
</template>

<script setup>

import router from "@/router";

const goHome = () => {
    router.push('/index')
}
</script>

<style lang="less" scoped>
.pay-success {
    margin: 20px auto;
    padding: 25px;
    background-color: #fff;
    border: 1px solid rgb(211, 211, 211);
    width: 1140px;
}
.success {
    width: 600px;
    margin: 0 auto;
}

.success h3 {
    margin-top: 20px;
    text-align: center;
    font-weight: 700;
    font-size: 20px;
    line-height: 30px;
}

.success h3 img {
    max-width: 100%;
    vertical-align: middle;
    border: 0;
    margin-right: 14px;
}

.paydetail {
    font-size: 15px;
    text-align: center;
    .el-button {
        margin-top: 20px;
        display: inline-block;
        box-sizing: border-box;
        cursor: pointer;
        border-radius: 6px;
        user-select: none;
        font-size: 18px;
        text-decoration: none;
    }
}

</style>