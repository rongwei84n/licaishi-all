# 简介

嘿牛理财师--后台管理系统

## 当前版本：v1.0.0

## Install

```bush

// install dependencies
npm install
```

## Run

### Development

```bush
npm run dev || yarn start
```

### Production(Build)

```bush
npm run build
```

## 文件结构

```bash

├── build  项目构建配置
└── src
    ├── images  图片文件
    └── libs  工具方法
        └── ajax  封装的全局ajax
    ├── router  路由配置
    ├── store  状态管理
    ├── styles  样式文件
    ├── template  模板文件
    ├── vendors  公共库文件
    └── views
        └── components  主要业务组件
            ├── table  基础例子可编辑talbe组件,(需要定制化可以以此为模板来改)
            └── rtf    富文本编辑组件
        ├── container   主要功能页面
        │   ├── home  首页
        │   └── lcsmanage  理财师管理
        │       └── table  理财师table组件
        │   ├── khmanage  客户管理
        │   ├── order     订单管理
        │   ├── product   产品管理
        │   ├── udit      富文本编辑
        │   └── error-page  错误页面
        ├── main-components  主组件
```
