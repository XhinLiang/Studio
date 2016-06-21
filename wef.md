# Tag-System 

## 接口文档

基地址 [tag-system.webdev.com](tag-system.webdev.com)

1. [新建 Tag](#tag/create)
2. [修改 Tag](#tag/alter)
3. <a href="#tag/id">根据 id 查看 Tag </a>
4. <a href="#tag/page">分页查看 Tags </a>
5. <a href="#tag/name">根据名字查看 Tag </a>
6. <a href="#tag/standard">根据标准 id 查看 Tags </a>
7. <a href="#tag/delete">根据 id 删除 Tag </a>
8. <a href="#tag/classify">给一个 Tag 做分类</a>
9. <a href="#tag/cite">增加一次引用次数</a>
10. <a href="#category/create">根据引用次数查看 Tags </a>
11. <a href="#category/create">新建 Category </a>
12. <a href="#category/alter">修改 Category </a>
13. <a href="#category/id">根据id 查看 Category </a>
14. <a href="#category/page">分页查看 Categories </a>
15. <a href="#category/name">根据名字查看 Category </a>

### 响应示例
```
{
    response: {
        code: 0,
        message: "OK",
        cost: 7,
        timestamp: 1466435833000
    },
    data: [
        {
            Fid: "50",
            Fname: "威锋网",
            Fcreated_by: null,
            Fcreated_at: "2016-06-18 22:39:45",
            Fupdated_by: null,
            Fupdated_at: "2016-06-18 22:39:45"
        }
    ]
}
```
如上所示，整个响应即为一个 `JSON` ，其中包含两个 `JSON` :
- `response` 包含 `code` 响应码 `message` `cost` `timestamp`
  - `code` 响应码
  - `message`
  - `cost`
  - `timestamp`
- `data` 请求返回的主体，根据不同的接口，有不同的实现

<a name="tag/create" />
### 新建 Tag

`POST /tag/create`

#### 请求体

参数名 | 类型 | 描述 | 是否必须
---|---|---|---
Fname | string | 标签名字 | 是
Fstatus | 0（未审核），1（普通），2（精品）| 标签的状态 | 否
Ftype | int (-99 ~ 99) | 保留 | 否
Fstore_type | 0（默认）1（爬虫）2（编辑）... | 保存的类型 | 否
Fstandard_id | int | 该标签对应的标准（同义）标签 | 否
Fcited_time | int | 引用次数 | 否
Fimage | url | 图片链接 | 否
Fdescription | string | 标签简介 | 否
Fsource | string | 标签来源 | 否
Fcreated_by | string | 标签创建者 | 否
Fupdated_by | string | 标签修改者 | 否


#### 请求示例

```
POST /tag/create
```

#### 响应示例

```
200 OK
{
    "response": {
        "code": 0
		"message": "OK"
		"cost": 12
		"timestamp": 1465983198000
	}
	"data": null
}
```

<a name="tag/alter" />
### 修改 Tag

`POST /tag/alter`

#### 请求体

参数名 | 类型 | 描述 | 是否必须
---|---|---|---
Fid | int | 标签id | 是
Fname | string | 标签名字 | 否
Fstatus | 0（未审核），1（普通），2（精品）| 标签的状态 | 否
Ftype | int (-99 ~ 99) | 保留 | 否
Fstore_type | 0（默认）1（爬虫）2（编辑）... | 保存的类型 | 否
Fstandard_id | int | 该标签对应的标准（同义）标签 | 否
Fcited_time | int | 引用次数 | 否
Fimage | url | 图片链接 | 否
Fdescription | string | 标签简介 | 否
Fsource | string | 标签来源 | 否
Fcreated_by | string | 标签创建者 | 否
Fupdated_by | string | 标签修改者 | 否


#### 请求示例

```
POST /tag/alter

Fid=51&Fname=wefwefwefcxvxcvxcvfd
```

#### 响应示例

```
200 OK
{
    "response": {
        "code": 0
		"message": "OK"
		"cost": 12
		"timestamp": 1465983198000
	}
	"data": null
}
```

<a name="tag/id" />
### 根据 id 查看 Tag

`GET /tag/id/{Fid}`

#### 请求体

参数名 | 类型 | 描述 | 是否必须
---|---|---|---
Fid | int | 标签id | 是

#### 请求示例

```
GET /tag/id/51
```

#### 响应示例

```
{
    response: {
        code: 0,
        message: "OK",
        cost: 8,
        timestamp: 1466434625000
    },
    data: {
        Fid: "50",
        Fname: "赫尔额",
        Fstatus: "0",
        Ftype: "0",
        Fstore_type: "0",
        Fstandard_id: "0",
        Fcited_time: "0",
        Fimage: "http://www.baidu.com/index.php",
        Fdescription: null,
        Fsource: "http://www.baidu.com/index.php",
        Fcreated_by: null,
        Fcreated_at: "2016-06-18 22:39:34",
        Fupdated_by: null,
        Fupdated_at: "2016-06-18 23:41:56",
        categories: [
            {
                Fid: "50",
                Fname: "威锋网",
                Fcreated_by: null,
                Fcreated_at: "2016-06-18 22:39:45",
                Fupdated_by: null,
                Fupdated_at: "2016-06-18 22:39:45"
            }
        ]
    }
}
```

<a name="tag/page" />
### 分页查看 Tags

`GET /tag/page/{limit}/{page}`

#### 请求体

参数名 | 类型 | 描述 | 是否必须
---|---|---|---
page | int | 页码数 | 否（默认1）
limit | int | 每页tags数 | 否（默认20）

#### 请求示例

```
GET /tag/page/1
```

#### 响应示例

``` 
{
    response: {
        code: 0,
        message: "OK",
        cost: 5,
        timestamp: 1466434845000
    },
    data: [
        {
            Fid: "50",
            Fname: "赫尔额",
            Fstatus: "0",
            Ftype: "0",
            Fstore_type: "0",
            Fstandard_id: "0",
            Fcited_time: "0",
            Fimage: "http://www.baidu.com/index.php",
            Fdescription: null,
            Fsource: "http://www.baidu.com/index.php",
            Fcreated_by: null,
            Fcreated_at: "2016-06-18 22:39:34",
            Fupdated_by: null,
            Fupdated_at: "2016-06-18 23:41:56",
            categories: [
                {
                    Fid: "50",
                    Fname: "威锋网",
                    Fcreated_by: null,
                    Fcreated_at: "2016-06-18 22:39:45",
                    Fupdated_by: null,
                    Fupdated_at: "2016-06-18 22:39:45"
                }
            ]
        }
    ]
}
```

<a name="tag/rank" />
### 根据引用次数分页查看 Tags

`GET /tag/rank/{limit}/{page}`

#### 请求体

参数名 | 类型 | 描述 | 是否必须
---|---|---|---
page | int | 页码数 | 否（默认1）
limit | int | 每页tags数 | 否（默认20）

#### 请求示例

```
GET /tag/rank/1
```

#### 响应示例

``` 
{
    response: {
        code: 0,
        message: "OK",
        cost: 5,
        timestamp: 1466434845000
    },
    data: [
        {
            Fid: "50",
            Fname: "赫尔额",
            Fstatus: "0",
            Ftype: "0",
            Fstore_type: "0",
            Fstandard_id: "0",
            Fcited_time: "0",
            Fimage: "http://www.baidu.com/index.php",
            Fdescription: null,
            Fsource: "http://www.baidu.com/index.php",
            Fcreated_by: null,
            Fcreated_at: "2016-06-18 22:39:34",
            Fupdated_by: null,
            Fupdated_at: "2016-06-18 23:41:56",
            categories: [
                {
                    Fid: "50",
                    Fname: "威锋网",
                    Fcreated_by: null,
                    Fcreated_at: "2016-06-18 22:39:45",
                    Fupdated_by: null,
                    Fupdated_at: "2016-06-18 22:39:45"
                }
            ]
        }
    ]
}
```

<a name="tag/standard" />
### 根据标准 id 查看 Tags

`GET /tag/standard/{Fstandard_id}`

#### 请求体

参数名 | 类型 | 描述 | 是否必须
---|---|---|---
Fstandard_id | int | 标准id | 是

#### 请求示例

```
GET /tag/standard/50
```

#### 响应示例

```
{
    response: {
        code: 0,
        message: "OK",
        cost: 5,
        timestamp: 1466434845000
    },
    data: [
        {
            Fid: "50",
            Fname: "赫尔额",
            Fstatus: "0",
            Ftype: "0",
            Fstore_type: "0",
            Fstandard_id: "0",
            Fcited_time: "0",
            Fimage: "http://www.baidu.com/index.php",
            Fdescription: null,
            Fsource: "http://www.baidu.com/index.php",
            Fcreated_by: null,
            Fcreated_at: "2016-06-18 22:39:34",
            Fupdated_by: null,
            Fupdated_at: "2016-06-18 23:41:56",
            categories: [
                {
                    Fid: "50",
                    Fname: "威锋网",
                    Fcreated_by: null,
                    Fcreated_at: "2016-06-18 22:39:45",
                    Fupdated_by: null,
                    Fupdated_at: "2016-06-18 22:39:45"
                }
            ]
        }
    ]
}
```

<a name="tag/delete" />
### 删除 Tag

`GET /tag/delete/{Fid}`

#### 请求体

参数名 | 类型 | 描述 | 是否必须
---|---|---|---
Fid | int | 标签id | 是

#### 请求示例

```
GET /tag/delete/51
```

#### 响应示例

```
200 OK
{
    "response": {
        "code": 0
		"message": "OK"
		"cost": 12
		"timestamp": 1465983198000
	}
	"data": null 
}
```

<a name="tag/name" />
### 根据名字查看 Tag

`GET /tag/name/{Fname}`

#### 请求体

参数名 | 类型 | 描述 | 是否必须
---|---|---|---
Fname | text | 标签名字 | 是

#### 请求示例 

```
GET /tag/name/赫尔额 
```

#### 响应示例

``` 
{
    response: {
        code: 0,
        message: "OK",
        cost: 8,
        timestamp: 1466434625000
    },
    data: {
        Fid: "50",
        Fname: "赫尔额",
        Fstatus: "0",
        Ftype: "0",
        Fstore_type: "0",
        Fstandard_id: "0",
        Fcited_time: "0",
        Fimage: "http://www.baidu.com/index.php",
        Fdescription: null,
        Fsource: "http://www.baidu.com/index.php",
        Fcreated_by: null,
        Fcreated_at: "2016-06-18 22:39:34",
        Fupdated_by: null,
        Fupdated_at: "2016-06-18 23:41:56",
        categories: [
            {
                Fid: "50",
                Fname: "威锋网",
                Fcreated_by: null,
                Fcreated_at: "2016-06-18 22:39:45",
                Fupdated_by: null,
                Fupdated_at: "2016-06-18 22:39:45"
            }
        ]
    }
}
```

<a name="tag/category" />
### 根据分类查看 Tags

`GET /tag/category/{Fname}`

#### 请求体

参数名 | 类型 | 描述 | 是否必须
---|---|---|---
Fname | text | 分类名 | 是

#### 请求示例 

```
GET /tag/category/威锋网
```

#### 响应示例 

``` 
{
    response: {
        code: 0,
        message: "OK",
        cost: 5,
        timestamp: 1466434845000
    },
    data: [
        {
            Fid: "50",
            Fname: "赫尔额",
            Fstatus: "0",
            Ftype: "0",
            Fstore_type: "0",
            Fstandard_id: "0",
            Fcited_time: "0",
            Fimage: "http://www.baidu.com/index.php",
            Fdescription: null,
            Fsource: "http://www.baidu.com/index.php",
            Fcreated_by: null,
            Fcreated_at: "2016-06-18 22:39:34",
            Fupdated_by: null,
            Fupdated_at: "2016-06-18 23:41:56",
            categories: [
                {
                    Fid: "50",
                    Fname: "威锋网",
                    Fcreated_by: null,
                    Fcreated_at: "2016-06-18 22:39:45",
                    Fupdated_by: null,
                    Fupdated_at: "2016-06-18 22:39:45"
                }
            ]
        }
    ]
}
```

<a name="tag/ccite" />
### 给 Tag 增加一次引用计数

`GET /tag/cite/{Fid}`

#### 请求体

参数名 | 类型 | 描述 | 是否必须
---|---|---|---
Fid | int | 标签id | 是



#### 请求示例

```
GET /tag/cite/51
```

#### 响应示例

```
200 OK
{
    "response": {
        "code": 0
		"message": "OK"
		"cost": 12
		"timestamp": 1465983198000
	}
	"data": null 
}
```


<a name="category/create" />
### 新建 Category

`POST /category/create`

#### 请求体

参数名 | 类型 | 描述 | 是否必须
---|---|---|---
Fname | string | 分类名字 | 是
Fcreated_by | string | 创建者 | 否
Fupdated_by | string | 修改者 | 否


#### 请求示例

```
POST /category/create
```

#### 响应示例

```
200 OK
{
    "response": {
        "code": 0
		"message": "OK"
		"cost": 12
		"timestamp": 1465983198000
	}
	"data": null
}
```

<a name="category/alter" />
### 修改 Tag

`POST /category/alter`

#### 请求体

参数名 | 类型 | 描述 | 是否必须
---|---|---|---
Fid | int | 标签id | 是
Fname | string | 标签名字 | 否
Fcreated_by | string | 标签创建者 | 否
Fupdated_by | string | 标签修改者 | 否


#### 请求示例

```
POST /category/alter

Fid=51&Fname=wefwefwefcxvxcvxcvfd
```

#### 响应示例

```
200 OK
{
    "response": {
        "code": 0
		"message": "OK"
		"cost": 12
		"timestamp": 1465983198000
	}
	"data": null
}
```

<a name="category/id" />
### 根据 id 查看 Category

`GET /category/id/{Fid}`

#### 请求体

参数名 | 类型 | 描述 | 是否必须
---|---|---|---
Fid | int | 标签id | 是

#### 请求示例

```
GET /category/id/51
```

#### 响应示例

```
{
    response: {
        code: 0,
        message: "OK",
        cost: 9,
        timestamp: 1466435802000
    },
    data: {
        Fid: "50",
        Fname: "威锋网",
        Fcreated_by: null,
        Fcreated_at: "2016-06-18 22:39:45",
        Fupdated_by: null,
        Fupdated_at: "2016-06-18 22:39:45"
    }
}
```

<a name="category/page" />
### 分页查看 Categories

`GET /category/page/{limit}/{page}`

#### 请求体

参数名 | 类型 | 描述 | 是否必须
---|---|---|---
page | int | 页码数 | 否（默认1）
limit | int | 每页tags数 | 否（默认20）

#### 请求示例

```
GET /category/page/1
```

#### 响应示例

``` 
{
    response: {
        code: 0,
        message: "OK",
        cost: 7,
        timestamp: 1466435833000
    },
    data: [
        {
            Fid: "50",
            Fname: "威锋网",
            Fcreated_by: null,
            Fcreated_at: "2016-06-18 22:39:45",
            Fupdated_by: null,
            Fupdated_at: "2016-06-18 22:39:45"
        }
    ]
}
```

<a name="category/delete" />
### 删除 Tag

`GET /category/delete/{Fid}`

#### 请求体

参数名 | 类型 | 描述 | 是否必须
---|---|---|---
Fid | int | 标签id | 是

#### 请求示例

```
GET /category/delete/51
```

#### 响应示例

```
200 OK
{
    "response": {
        "code": 0
		"message": "OK"
		"cost": 12
		"timestamp": 1465983198000
	}
	"data": null 
}
```

<a name="category/name" />
### 根据名字查看 Category

`GET /category/name/{Fname}`

#### 请求体

参数名 | 类型 | 描述 | 是否必须
---|---|---|---
Fname | text | 分类名字 | 是

#### 请求示例 

```
GET /category/name/威锋网
```

#### 响应示例

``` 
{
    response: {
        code: 0,
        message: "OK",
        cost: 9,
        timestamp: 1466435802000
    },
    data: {
        Fid: "50",
        Fname: "威锋网",
        Fcreated_by: null,
        Fcreated_at: "2016-06-18 22:39:45",
        Fupdated_by: null,
        Fupdated_at: "2016-06-18 22:39:45"
    }
}
```
