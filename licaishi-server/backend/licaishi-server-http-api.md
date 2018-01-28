title:	理财师服务端 HTTP接口
---------------------

- 接口统一采用RESTful风格的接口

### app手机端使用时


HTTP头里面带有以下信息:

参数 | 数据类型 | 必填|参数类型|描述
---|--- |--- | ---| ---
Authorization | string | 必填| Header| 构造形式必须 Bearer xxxxxxxxxxxxxxxxxxxxxx


# 产品类

- 首页查询部分热销产品，一般两条

```
get /v1/server/partial_hot_products
```
请求参数:

参数 | 数据类型 | 必填|参数类型|描述
---|--- |--- | ---| ---
platid| string | 必填| body|1代表音响的Android版本，2代表音响的IOS版本
vercode | int | 必填| body|当前app的版本code
channel | string | 必填| body|渠道id,sandroid的有多个渠道，IOS的指定为0ASE


响应参数:

参数 | 数据类型 | 描述
---|--- |---
status| int | 请求状态码
message| string | 状态码为非200时，错误信息
result | object | 业务结果,统一都有带这个字段返回

请求例子

```
get /v1/server/partial_hot_products
{
   "platid":"2015100011",
	"vercode":1,  
	"channel":"1CWR"
}

response:
{
    "status": 200,
    "message": "成功",
    "result": [
    	{
    		"name":"国通信托",
    		"type":"综合资管",
    		"income_p":"0.09"//预计年化
    		"time_limit":"24个月",
    		"pic":"http:///xxx.img"，
    		"filed":"基础设施" //投资领域,
    		"rebate_p":"0.34"//返佣比例,
    		"progress":"0.8"//进度
    	},
    	{
    	
    	}
    ]
}

```

- 所有热销产品

```
get /v1/server/all_hot_products
```
请求参数:

参数 | 数据类型 | 必填|参数类型|描述
---|--- |--- | ---| ---
platid| string | 必填| body|1代表音响的Android版本，2代表音响的IOS版本
vercode | int | 必填| body|当前app的版本code
channel | string | 必填| body|渠道id,sandroid的有多个渠道，IOS的指定为0ASE


响应参数:

参数 | 数据类型 | 描述
---|--- |---
status| int | 请求状态码
message| string | 状态码为非200时，错误信息
result | object | 业务结果,统一都有带这个字段返回

请求例子

```
get /v1/server/all_hot_products
{
   "platid":"2015100011",
	"vercode":1,  
	"channel":"1CWR"
}

response:
{
    "status": 200,
    "message": "成功",
    "result": [
    	{
    		"name":"国通信托",
    		"type":"综合资管",
    		"income_p":"0.09"//预计年化
    		"time_limit":"24个月",
    		"pic":"http:///xxx.img"，
    		"filed":"基础设施" //投资领域,
    		"rebate_p":"0.34"//返佣比例,
    		"progress":"0.8"//进度
    	},
    	{
    	
    	}
    ]
}

```


- 首页查询部分最新推荐

```
get /v1/server/partial_recommend_products
```
请求参数:

参数 | 数据类型 | 必填|参数类型|描述
---|--- |--- | ---| ---
platid| string | 必填| body|1代表音响的Android版本，2代表音响的IOS版本
vercode | int | 必填| body|当前app的版本code
channel | string | 必填| body|渠道id,sandroid的有多个渠道，IOS的指定为0ASE


响应参数:

参数 | 数据类型 | 描述
---|--- |---
status| int | 请求状态码
message| string | 状态码为非200时，错误信息
result | object | 业务结果,统一都有带这个字段返回

请求例子

```
get /v1/server/partial_recommend_products
{
   "platid":"2015100011",
	"vercode":1,  
	"channel":"1CWR"
}

response:
{
    "status": 200,
    "message": "成功",
    "result": [
    	{
    		"name":"国通信托",
    		"type":"综合资管",
    		"income_p":"0.09"//预计年化
    		"time_limit":"24个月",
    		"pic":"http:///xxx.img"，
    		"filed":"基础设施" //投资领域,
    		"rebate_p":"0.34"//返佣比例,
    		"progress":"0.8"//进度
    	},
    	{
    	
    	}
    ]
}

```

- 所有推荐产品，首页推荐产品更多

```
get /v1/server/all_recommend_products
```
请求参数:

参数 | 数据类型 | 必填|参数类型|描述
---|--- |--- | ---| ---
platid| string | 必填| body|1代表音响的Android版本，2代表音响的IOS版本
vercode | int | 必填| body|当前app的版本code
channel | string | 必填| body|渠道id,sandroid的有多个渠道，IOS的指定为0ASE


响应参数:

参数 | 数据类型 | 描述
---|--- |---
status| int | 请求状态码
message| string | 状态码为非200时，错误信息
result | object | 业务结果,统一都有带这个字段返回

请求例子

```
get /v1/server/all_recommend_products
{
   "platid":"2015100011",
	"vercode":1,  
	"channel":"1CWR"
}

response:
{
    "status": 200,
    "message": "成功",
    "result": [
    	{
    		"name":"国通信托",
    		"type":"综合资管",
    		"income_p":"0.09"//预计年化
    		"time_limit":"24个月",
    		"pic":"http:///xxx.img"，
    		"filed":"基础设施" //投资领域,
    		"rebate_p":"0.34"//返佣比例,
    		"progress":"0.8"//进度
    	},
    	{
    	
    	}
    ]
}

```
