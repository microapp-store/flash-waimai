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
