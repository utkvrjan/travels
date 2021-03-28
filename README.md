# 旅游信息管理系统
## 项目简介

后端技术栈：springboot + mybatis

前后端分离：axios、json

前端技术栈、技术架构：Vue、node.js


### 需求分析：

用户模块：登录 + 注册

省份模块：一个省份可能存在多个景点

景点模块：一个景点对应多个省份

## 部分页面展示

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210328132426345.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NDE5NzEyMA==,size_16,color_FFFFFF,t_70)
![在这里插入图片描述](https://img-blog.csdnimg.cn/2021032813244446.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NDE5NzEyMA==,size_16,color_FFFFFF,t_70)



### 库表设计

用户表 `t_user` —— 独立的表

id、username、password、email

```sql
CREATE TABLE t_user(
	id INT(6) PRIMARY KEY AUTO_INCREMENT,
	username VARCHAR(60),
	password VARCHAR(60),
	email VARCHAR(60)
);
```


省份表 `t_province` [省份表 : 景点表] —— [1 : N]

id、name、tags、placecounts

```sql
CREATE TABLE t_province(
	id INT(6) PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(60),
	tags VARCHAR(80),
	placecounts INT(4)
);
```


景点表 `t_place`

id、name、picpath、hottime、hotticket、dimticket、placedes、provinceid(外键)

```sql
CREATE TABLE t_place(
	id INT(6) PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(60),
	picpath MEDIUMTEXT,
	hottime	TIMESTAMP,
	hotticket	DOUBLE(7,2),
	dimticket	DOUBLE(7,2),
	placedes	VARCHAR(300),
	provinceid	INT(6) REFERENCES t_province(id)
);
```



## 其他

验证功能写得有些繁琐【需要对生成的验证码图片进行 Base64 编码后传到前端页面，前端再解析展示图片。】，但是该有的功能都实现了。
