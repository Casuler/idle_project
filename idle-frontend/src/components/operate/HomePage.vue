<template>
    <div class="main-content">
        <el-scrollbar wrap-style="overflow-x:hidden;" style="width: 100%;">
              <div style="margin: 0 50px 20px;min-width: 800px; height: 300px">
                  <div class="banner">
                      <el-image :src="top" style="height: 270px; width: 100%;opacity: 0.8"></el-image>
                  </div>
                  <div style="text-shadow: rgb(98,98,98) 1px 1px 10px;
                   color:white; translate:30px -240px; ">
                      <div style="font-size: 30px;font-weight: bold;">
                          闲置交易
                      </div>
                      <div style="font-size: 17px; width: 550px; margin-top: 10px">
                          大家可以在这里交易闲置物品
                      </div>
                  </div>
              </div>
            <div class="card-title">
                <el-button type="danger" plain @click="router.push('/release')">
                    <el-icon><Goods/></el-icon>
                    发布闲置
                </el-button>
            </div>
              <div class="card">
                  <div class="split-line"></div>
                  <Category></Category>
              </div>
          </el-scrollbar>
    </div>
</template>

<script setup>
import {Goods} from '@element-plus/icons-vue'
import Category from "@/components/Category.vue";
import {useStore} from "@/stores/store";
import router from "@/router";

const top = 'https://itbaima.net/assets/resource-banner-27d717a1.jpg'
const store = useStore()



</script>

<style lang="less" scoped>
.main-content{
    display: flex;
    width: 100%;
    height: calc(100vh - 61px);
    min-width: 1000px;
    background-color: #f2f4f8;
}

.banner{
    height: 270px;
}

.card-title{
    margin-left: 50px;
    align-items: center;
    padding: 5px;
    display: flex;
    flex-direction: row;
}
.card{
    margin: 0 50px;
}

.split-line{
    margin-top: 5px;
    height: 1px;
    width: 100%;
    background-color: #dadada;
}
</style>