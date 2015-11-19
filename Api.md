
基地址：http://hostname/api/1.0/

```
状态码约定：
   - 1 成功
   - 0 未知错误
   - 负数 已知错误
```
####1.注册
请求路径：http://hostname/api/1.0/information/getMarks

- 请求方式：post
- 请求参数name：名字 string
- 请求参数phone：电话 string
- 请求参数group：组别 int
- 请求参数code：邀请码 string
- 请求参数position：职位 int
- 请求参数sex：性别 int

- 返回格式 : json
- 成功:
{
        "code": 1,
    	"msg": "请求成功",
    	"objList": 
    	
}
- 失败
{       
        "code":"202",
        "msg":"网络错误"
}


####2.登录
请求路径：http://hostname/api/1.0/information/getMarks

- 请求方式：post
- 请求参数name：名字 string
- 请求参数phone：电话 string

- 返回格式 : json
- 成功:
{
        "code": 1,
    	"msg": "请求成功",
    	"data": "头像url"
}
如果成功需要在Header里添加token
- 失败
{       
        "code":"0",
        "msg":"网络错误"
}


####3.获取在工作室的用户
请求路径：http://hostname/api/1.0/information/getMarks

- 请求方式：get

- 返回格式 : json
- 成功:
{
        "code": 1,
    	"msg": "请求成功",
    	"data": 
    	[
            	      {
            	       "group":"",
            	       "fellow":
                    	    [
                    	    {
                        	    "name": "", //String
                            	"phone": "",//String
                            	"avatar": "url",//String
                            	"position": "1" //int
                            	"description": "" //String
                        	}，
                            {....},
                            {....}
                            ]
                       },
                       {....},
                       {....}
                ]
}
- 失败
{       
        "code":"0",
        "msg":"网络错误"
}


####4.获取所有用户
请求路径：http://hostname/api/1.0/information/getMarks

- 请求方式：get

- 返回格式 : json
- 成功:
{
        "code": 1,
    	"msg": "请求成功",
    	"data": 
    	[
    	      {
    	       "group":"",
    	       "fellow":
            	    [
            	    {
                	    "name": "", //String
                    	"phone": "",//String
                    	"avatar": "url",//String
                    	"position": "1" //int
                    	"description": "" //String
                	}，
                    {....},
                    {....}
                    ]
               },
               {....},
               {....}
        ]
}
- 失败
{       
        "code":"0",
        "msg":"网络错误"
}

####5.签到与签退
请求路径：http://hostname/api/1.0/information/getMarks

- 请求方式：post
- 请求参数order: int 0签到 1签退

- 返回格式 : json
- 成功:
{
        "code": 1,
    	"msg": "请求成功",
}
- 失败
{       
        "code":"0",
        "msg":"网络错误"
}


####6.修改用户信息
请求路径：http://hostname/api/1.0/information/getMarks

- 请求方式：post
- 请求参数phone: string
- 请求参数description: string
- 请求参数avatar: string

- 返回格式 : json
- 成功:
{
        "code": 1,
    	"msg": "请求成功",
}
- 失败
{       
        "code":"0",
        "msg":"网络错误"
}


