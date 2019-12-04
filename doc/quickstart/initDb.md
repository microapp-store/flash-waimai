# 初始化数据

本系统使用mysql数据库和mongodb数据库，其中mysql存放基础配置数据，mongodb存放业务数据。（后期可能会全面切换到mysql数据库，此是后话）

- mysql(安装5.5以上的版本)
    - 创建数据库
        ```sql
        CREATE DATABASE IF NOT EXISTS waimai DEFAULT CHARSET utf8 COLLATE utf8_general_ci; 
        CREATE USER 'waimai'@'%' IDENTIFIED BY 'waimai123';
        GRANT ALL privileges ON waimai.* TO 'waimai'@'%';
        flush privileges;
        ```
    - 数据初始化，无需手动初始化数据，flash-waimai-api启动的时候会根据配置自动导入初始化数据(import.sql)，第二次启动的时候如果不需要重新初始化语句，则可以注释掉配置 
        ```properties
        spring.jpa.hibernate.ddl-auto=create
        ```
- mongodb(安装4.0的版本)
    - 创建mongodb库
        ```sql
        use flash-waimai
        ```
    - 初始化数据，由于测试数据量较大，将测试数据打包放在了百度云盘：链接：[https://pan.baidu.com/s/1mfO7yckFL7lMb_O0BPsviw](https://pan.baidu.com/s/1mfO7yckFL7lMb_O0BPsviw)  提取码：apgd 下载后将文件解压到d:\\elm，使用如下命令导入数据：
        ```
        mongorestore.exe -d flash-waimai d:\\elm
        ```
- 测试图片数据，系统中测试数据中包含了大量的商铺，食品图片。
    - 由于数据量较大， 放在了百度云盘，链接：[https://pan.baidu.com/s/1rvZDspoapWa6rEq2D_5kzw](https://pan.baidu.com/s/1rvZDspoapWa6rEq2D_5kzw) 提取码：urzw ，
    - 将图片存放到t_sys_cfg表中system.file.upload.path配置的目录下
  
  
## 清空测试数据

如果想清空所有测试数据，自己通过后台管理功能区录入测试数据，可以通过使用下面配置清空系统测试数据
```properties
# 开启该配置自动创建数据库
spring.jpa.hibernate.ddl-auto=create
# 是否重新清空mongodb测试数据
flash.waimai.mongodb.init=true
使用客户端连接mongodb数据库为shop表创建索引:
db.shops.createIndex({location: "2dsphere"})
```  
