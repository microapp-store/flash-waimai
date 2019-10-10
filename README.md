# flash-waimai
- 一个完整的外卖系统，包括手机端，后台管理，api
- 基于spring boot和vue的前后端分离的外卖系统
- 包含完整的手机端，后台管理功能

## 技术选型
- 核心框架：Spring Boot
- 数据库层：Spring data jpa/Spring data mongodb
- 数据库连接池：Druid
- 缓存：Ehcache
- 前端：Vue.js
- 数据库：mysql5.5以上,Mongodb4.0(不要使用最新版4.2)

## 快速开始
- 数据存储采用了mysql和mongodb，其中基础管理配置功能使用mysql，业务数据使用mongodb存储。
- 创建mysql数据库
```sql
    CREATE DATABASE IF NOT EXISTS waimai DEFAULT CHARSET utf8 COLLATE utf8_general_ci; 
    CREATE USER 'waimai'@'%' IDENTIFIED BY 'waimai123';
    GRANT ALL privileges ON waimai.* TO 'waimai'@'%';
    flush privileges;
```
- mysql数据库创建好了之后，启动flash-waimai-api服务，会自动初始化数据，无需开发人员自己手动初始化数据
- 安装mongodb并创建数据库:flash-waimai
使用mongorestore命令  导入mongodb数据,由于测试数据量较大，打包放在了百度云盘：链接：https://pan.baidu.com/s/1mfO7yckFL7lMb_O0BPsviw   提取码：apgd 下载后将文件姐要到d:\\elm
                                              
```
mongorestore.exe -d flash-waimai d:\\elm
```
- 启动管理平台，进入flash-waimai-manage目录：
    - 运行 npm install --registry=https://registry.npm.taobao.org
    - 运行npm run dev
    - 启动成功后访问 http://localhost:9528 ,登录，用户名密码:admin/admin
- 启动手机端，进入flash-waimai-mobile目录：    
    - 运行 npm install --registry=https://registry.npm.taobao.org
    - 运行npm run local
    - 启动成功后访问 http://localhost:8000

## 模块

- flash-waimai-mobile 手机端站点
- flash-waimai-manage后台管理系统
- flash-waimai-api java接口服务
- flash-waimai-core 底层核心模块
- flash-waimai-generate 代码生成模块
    
## 目前还在开发中
- flash-waimai-manage [初步完成]
- flash-waimai-mobile[完善中]

## 鸣谢
- 感谢[bailicangdu](https://github.com/bailicangdu),[enilu](https://github.com/enilu),本项目参考参考借鉴了[vue2-elm](https://github.com/bailicangdu/vue2-elm)，[web-flash](https://github.com/enilu/web-flash)，[vue2-manage](https://github.com/bailicangdu/vue2-manage)

## 交流
- QQ:936439613
# License

[GPL](https://github.com/microapp-store/flash-waimai/blob/master/COPYING)
