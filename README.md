# flash-waimai
- 一个完整的外卖系统，包括手机端，后台管理，api
- 基于spring boot和vue的前后端分离的外卖系统

## 快速开始
- 创建数据库
```sql
    CREATE DATABASE IF NOT EXISTSwaimai DEFAULT CHARSET utf8 COLLATE utf8_general_ci; 
    CREATE USER 'waimai'@'%' IDENTIFIED BY 'waimai123';
    GRANT ALL privileges ON waimai.* TO 'waimai'@'%';
    flush privileges;

```
- 安装mongodb并创建数据库
使用mongorestore  导入mongodb数据
```
mongorestore.exe -d flash-waimai e:\\elm
```
## 模块
- flash-waimai-admin原版后台管理系统
- flash-waimai-manage改版的后台管理系统：在flash-waimai-admin的基础上完善后台管理功能，并且把后台接口从nodejs服务更改为java接口，java接口服务未flash-waimai-api
- flash-waimai-api java接口服务
- flash-waimai-core 底层核心模块
- flash-waimai-generate 代码生成模块
- flash-waimai-mobile 手机端站点
## todo
- 改造 flash-waimai-manage 
- 改造 flash-waimai-mobile