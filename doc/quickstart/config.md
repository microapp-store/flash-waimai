# 系统配置

你已经下载项目，并且初始化好了数据，那么接下来只需要更改相应的配置就可以运行该项目了

## application-{env}.properties配置

### 数据库配置 
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/web-flash?useUnicode=true&characterEncoding=UTF8
spring.datasource.username=root
spring.datasource.password=root
```

### mongodb 配置
```properties
spring.data.mongodb.uri=mongodb://localhost:27017/flash-waimai
```
### 腾讯地图服务配置
```properties
api.qq.map.url=https://apis.map.qq.com/ws/
cfg.tencentkey=RLHBZ-WMPRP-Q3JDS-V2IQA-JNRFH-EJBHL
cfg.tencentkey2=RRXBZ-WC6KF-ZQSJT-N2QU7-T5QIT-6KF5X
cfg.tencentkey3=OHTBZ-7IFRG-JG2QF-IHFUK-XTTK6-VXFBN
```

- 本项目使用腾讯位置服务做地图相关的操作，比如根据用户ip获取经纬度，根据名称模糊搜索位置列表等
- key值的申请请通过以下链接申请：[https://lbs.qq.com/dev/console/key/add](https://lbs.qq.com/dev/console/key/add)
- 建议至少申请三个，因为免费的key又使用数量限制，如果测试过于频繁会用光额度导致查询失败
- 不建议使用项目中自带的key值，由于演示环境使用了这些key值，很容易出现额度用光的情况，会导致跟位置服务相关功能失效。


## 全局参数配置
这里的配置在后台管理系统启动成功后，登录后台管理系统进行配置
- 登录系统后进入“系统管理”-》“参数管理”（http://localhost:9528/#/system/cfg）
- 确认system.file.upload.path的配置正确，该配置为项目中商铺和食品的图片存放的位置,该路径配置为绝对路径
- 如果需要短信，项目集成了腾讯云短信功能，可以去[https://cloud.tencent.com/product/sms](https://cloud.tencent.com/product/sms)申请短信功能并配置短信相关参数

