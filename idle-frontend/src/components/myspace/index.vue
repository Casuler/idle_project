<script setup>
function goTarget(href) {
    window.open(href, "_blank");
}
</script>

<template>
    <div class="app-container home">
        <el-row :gutter="20">
            <el-col :sm="24" :lg="24">
                <blockquote class="text-warning" style="font-size: 14px">
                    欢迎进入idle校园闲鱼网站个人中心
                </blockquote>
                <hr />
            </el-col>
        </el-row>
        <el-row :gutter="20">
            <el-col :sm="24" :lg="24" style="padding-left: 20px">
                <h2>我的空间</h2>
                <p>
                    这里是用户的个人空间，用户可以在这里查看个人信息、修改个人信息、管理用户订单、管理用户发布的商品。
                </p>
            </el-col>
        </el-row>
    </div>
</template>

<style scoped lang="less">
.home {
  blockquote {
    padding: 10px 20px;
    margin: 0 0 20px;
    font-size: 17.5px;
    border-left: 5px solid #eee;
  }
  hr {
    margin-top: 20px;
    margin-bottom: 20px;
    border: 0;
    border-top: 1px solid #eee;
  }

  font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 16px;
  color: #676a6c;
  overflow-x: hidden;

  ul {
    list-style-type: none;
  }

  h2 {
    margin-top: 10px;
    font-size: 26px;
    font-weight: bold;
  }
}
</style>