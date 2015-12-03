# # 接口规范

需要实现基于 HTTP/JSON 的 RESTful API，至少包含以下接口:

基地址 http://121.42.209.19/RestfulApi/index.php/

1. <a href="#login">登录</a>
2. <a href="#reg">注册</a>
3. <a href="#update">修改用户信息</a>
4. <a href="#sign">用户签到</a>
5. <a href="#signout">用户签退</a>
6. <a href="#mes_all">获取所有用户信息</a>
7. <a href="#mes">获取已签到用户信息</a>
8. <a href="#please">增加邀请码信息</a>
9. <a href="#please_out">获取所有邀请码信息</a>




如果需要传参的接口，传过来的请求体 json 格式有误或验证结果错误。则返回错误状态

```
{
    "status": "-1"
}
```
<a name="login" />
## 登录

`GET /user`

##### 请求体

参数名 | 类型 | 描述
---|---|---
name | string | 用户名
phone | string | 密码

#####请求示例

```
GET /api/user?name=xxx&phone=xxx
```

##### 响应示例

```
200 OK
{
  "id": "10",
  "name": "wer123",
  "sex": "1",
  "group_name": "2",
  "phone": "121",
  "imgurl": "www.123456.com",
  "position": "3",
  "sign_date": "2015-11-20 15:52:31",
  "status": "0"
}
```

##### 异常示例

用户名不存在或者密码错误：

```
403 Forbidden
{
  "status": -1,
  "message": "用户名密码错误"
}
```

<a name="reg" />
## 注册

`POST /api/users`

#### 请求体

参数名 | 类型 | 描述
---|---|---
name | string | 用户名
sex | int | 性别
group_name | int | 小组类别
imgurl | string | 头像
phone | string | 电话号码
position | int | 职位
code | string | 邀请码



#### 请求示例

```
POST /api/users	

{"name" : "wer12312", "sex" : 1, "group_name" : 2, "imgurl" : "www.123456.com", "phone" : "121", "position" : 3, "code" : "123"}


```

#### 响应示例

```
200 OK
{
  "status": 1
}
```

#### 异常示例
邀请码不存在或已被注册

```
403 Forbidden
{
  "status": -1,
  "message": "邀请码不存在"
}
```

用户名重复

```
{
  "status": -1,
  "message": "用户名重复"
}
```


<a name="update" />
## 修改用户信息


#### 请求体

参数名 | 类型 | 描述
---|---|---
name | string | 用户名
sex | int | 性别
group_name | int | 小组类别
imgurl | string | 头像
phone | string | 电话号码
position | int | 职位



#### 请求示例

```
PUT api/users
{"name" : "wer123", "sex" : 1, "group_name" : 2, "imgurl" : "www.123456123.com", "phone" : "121", "position" : 3}
```


#### 响应示例

```
200 OK
{
  "id": "10",
  "name": "wer123",
  "sex": "1",
  "group_name": 2,
  "phone": "121",
  "imgurl": "www.123456123.com",
  "position": 3,
  "sign_date": "2015-11-20 15:52:31",
  "status": "0"
}
```

#### 异常示例
```
403 Forbidden
{
  "status": -1,
  "message": "用户名信息错误"
}
```


####说明
```
name与sex无法更改
```

<a name="sign" />
## 用户签到

`POST /api/sign`

##### 请求体

参数名 | 类型 | 描述
---|---|---
name | string | 用户名

#### 请求示例

```
POST /api/sign
{
	"name" : "wer123"
}
```

#### 响应示例

签到成功

```
200 OK
{
	"status":"1"
}
```


#### 异常示例

```
403 
{
  "status": -1,
  "message": "用户还没有下线"
}
```



<a name="signout" />
## 用户签退

`DELETE /api/sign`

##### 请求体

参数名 | 类型 | 描述
---|---|---
name | string | 类型名称

#### 请求示例

```
POST /api/sign
{
	"name" : "wer123"
}
```

#### 响应示例

```
200 OK
{
  "status": 1
}
```

#### 异常示例

用户名没有签到

```
403
{
  "status": -1,
  "message": "用户还没有签到"
}
```



<a name="mes_all" />
## 获取所有用户信息
`GET /api/message_all`



#### 请求示例

```
GET /api/message_all
```

#### 响应示例

```
200 OK
{
  "status": 1,
  "data": [
    {
      "id": "13",
      "name": "wer12312",
      "sex": "1",
      "group_name": "2",
      "phone": "121",
      "imgurl": "www.123456.com",
      "position": "3",
      "sign_date": "2015-11-20 18:45:20",
      "status": "0"
    },
    {
      "id": "10",
      "name": "wer123",
      "sex": "1",
      "group_name": "2",
      "phone": "121",
      "imgurl": "www.123456123.com",
      "position": "3",
      "sign_date": "2015-11-20 15:52:31",
      "status": "0"
    },
    {
      "id": "12",
      "name": "wer1231231",
      "sex": "1",
      "group_name": "1",
      "phone": "123",
      "imgurl": "www.123.com",
      "position": "1",
      "sign_date": "2015-11-20 15:55:01",
      "status": "0"
    }
  ]
}
```


<a name="mes" />
## 获取已签到用户信息
`GET /api/message`



#### 响应示例

```
200 OK
{
  "status": 1,
  "data": [
    {
      "id": "4",
      "name": "wer",
      "sex": "1",
      "group_name": "1",
      "phone": "123",
      "imgurl": "www.123.com",
      "position": "1",
      "sign_date": "2015-11-20 15:07:47",
      "status": "1"
    }
  ]
}
```


<a name="please" />
## 增加邀请码信息

`GET /api/addplease/(num)`



#### 请求示例

```
GET /api/addplease/(num)

```

#### 响应示例

```
200 OK
{
  "status": 1
}
```



<a name="please_out" />
## 获取所有邀请码

`GET /api/getplease`



#### 请求示例

```
GET /api/getplease

```

#### 响应示例

```
200 OK
{
  "status": 1,
  "data": [
    {
      "id": "1",
      "num": "123",
      "status": "1"
    },
    {
      "id": "2",
      "num": "974c5743-3f6a-6dd5-f24f-f3e05bd3031d",
      "status": "1"
    },
    {
      "id": "3",
      "num": "296d19ad-d6b1-fa6e-b8d3-0a6abb303257",
      "status": "1"
    },
    {
      "id": "4",
      "num": "8a43cfa6-30ee-5c2c-fb6a-1dd14dc5650b",
      "status": "1"
    }
  ]
}
```







