# flash-waimai
- 一个完整的外卖系统，包括手机端，后台管理，api
- 基于spring boot和vue的前后端分离的外卖系统
- 包含完整的手机端，后台管理功能

## 快速开始
- 数据存储采用了mysql和mongodb，其中基础管理配置功能使用mysql，业务数据使用mongodb存储。
- 创建mysql数据库
```sql
    CREATE DATABASE IF NOT EXISTSwaimai DEFAULT CHARSET utf8 COLLATE utf8_general_ci; 
    CREATE USER 'waimai'@'%' IDENTIFIED BY 'waimai123';
    GRANT ALL privileges ON waimai.* TO 'waimai'@'%';
    flush privileges;
```
- mysql创建好了之后，启动flash-waimai-api服务，会自动初始化数据，无需开发人员自己手动初始化数据
- 安装mongodb并创建数据库:flash-waimai
使用mongorestore  导入mongodb数据,由于测试数据量较大，打包放在了百度云盘：链接：https://pan.baidu.com/s/1mfO7yckFL7lMb_O0BPsviw   提取码：apgd 
                                              
```
mongorestore.exe -d flash-waimai e:\\elm
```
## 模块

- flash-waimai-mobile 手机端站点
- flash-waimai-manage后台管理系统
- flash-waimai-api java接口服务
- flash-waimai-core 底层核心模块
- flash-waimai-generate 代码生成模块

## 鸣谢
- 感谢[bailicangdu](https://github.com/bailicangdu)
    - 手机端 flash-waimai-mobile基于bailicangdu的[vue2-elm](https://github.com/bailicangdu/vue2-elm)改造，主要将后台接口服务从nodejs服务更改为java服务(spring boot)
    - 后台管理 flash-waimai-manage基于enilu的[web-flash](https://github.com/enilu/web-flash)的后台管理模块，在次基础上增加了业务相关功能,业务功能参考了bailicangdu的[vue2-manage](https://github.com/bailicangdu/vue2-manage)
    - api服务 flash-waimai-api 基于enlu的[web-flash](https://github.com/enilu/web-flash)的flash-api模块
    
## 目前还在开发中
- flash-waimai-manage [初步完成]
- flash-waimai-mobile[开发中]


# License

[GPL](https://github.com/microapp-store/flash-waimai/blob/master/COPYING)
