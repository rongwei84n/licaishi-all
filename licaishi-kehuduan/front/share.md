# 与 APP 交互的参数说明

## 首页复制功能的参数说明

* 对象名 Copy

|   属性名    |           作用           | 说明 |
| :---------: | :----------------------: | :--: |
| partnerCode | 需要复制的文本，推广编码 |

## App接受H5分享功能的数据说明

### ios 接收 h5 分享参数说明

```bash

//H5发送代码
window.webkit.messageHandlers.Share.postMessage(params);

// 例子
Share:{
  title:'测试分享',
  url:'https://www.baidu.com',
  share_logo_url:'https://pic3.zhimg.com/v2-080267af84aa0e97c66d5f12e311c3d6_xl.jpg',
  content:'文本描述',
  sharetype:2,
}
```

### 安卓接收 h5 分享参数说明

```bash
//H5发送代码
window.jsInterface.invokeMethod(JSON.stringify(params));

// 例子
"{
  "title":"目标电话号码",
  "url":"https://heavenzhanghr.github.io/",
  "share_logo_url":"https://pic3.zhimg.com/v2-080267af84aa0e97c66d5f12e311c3d6_xl.jpg",
  "content":"文本描述",
  "sharetype":2,
}"
```

|     属性名     |      作用      |                                 说明                                 |
| :------------: | :------------: | :------------------------------------------------------------------: |
|     title      |      标题      |
|      url       |      链接      |
| share_logo_url | 分享图标的 url |                                                                      |
|    content     |    文本内容    |
|   sharetype    |   分享的类型   | 1、新浪微博 ,2、朋友圈 ,3、微信好友 ,4、qq 好友 ,5、QQ 空间 ,6、短信 |

> PS: 当 sharetype=6 时，也就是发送短信时 content 内存放发送短信的内容

```bash
// 例子   发送参数例子
"{
  "title":"可不取",
  "url":"可不取",
  "share_logo_url":"可不取",
  "content":"文本描述",
  "sharetype":6,
}"
```
