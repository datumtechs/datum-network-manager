# RosettaNet-Admin

RosettaNet管理台

###项目模块介绍
+ 1.rosettanet-admin --父模块
+ 2.common --公共模块，主要包含工具类和一些公共类
+ 3.controller --controller模块，主要包含对外接口相关内容
+ 4.service --service模块，负责业务的主要逻辑代码
+ 5.dao --dao模块，主要负责数据库操作
+ 6.grpc-client --主要负责grpc接口访问
+ 7.grpc-lib --主要包含grpc接口定义的proto文件，负责通过插件生成grpc接口的java代码

### 打包
mvn clean install

### 启动
java -jar rosettanet-admin-*.jar --spring.profiles.active=dev

### 接口文档
启动后在浏览器上输入：http://localhost:9090/rosettanet-admin/doc.html

### 数据库脚本
路径：[rosettanet-admin/controller/scripts/rosettanet_admin.sql](./controller/scripts/rosettanet_admin.sql)

### grpc相关文档
[grpc文档](./grpc-lib/README.MD)

### 部署文档
[部署文档](./部署文档.MD)