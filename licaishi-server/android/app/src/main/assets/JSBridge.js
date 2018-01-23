/**
 * Created on 16/12/05.
 *
 协定协议:phihome://class/method?params;
 params是一串json字符串
 */
 
(function () {
        var doc = document;
        var win = window;
        var JS_BRIDGE_PROTOCOL_SCHEMA = "phihome";
        var ua = win.navigator.userAgent;
        var myCallback;
        var jsBridge = win.jsBridge || (win.jsBridge = {});

        var ExposeMethod = {
            //调用原生方法
            callMethod: function (clazz, method, param, callback) {
                PrivateMethod.callNativeMethod(clazz, method, param);
                myCallback = callback;
            },

            //给原生回调
            onComplete: function (result) {
                PrivateMethod.onNativeComplete(result);
            },

        };

        var PrivateMethod = {

            onNativeComplete: function (result) {
               myCallback(result)
            },

            //调用原生方法
            callNativeMethod: function (clazz, method, param) {
                var jsonStr = "";
                if (param !== null) {
                    jsonStr = PrivateMethod.json2Str(param);
                }
                if (PrivateMethod.isAndroid()) {
                    var uri = JS_BRIDGE_PROTOCOL_SCHEMA + "://" + clazz + "/" + method + "?" + jsonStr;
                    win.prompt(uri, "");
                }
                if (PrivateMethod.isIos()) {
                    var url = JS_BRIDGE_PROTOCOL_SCHEMA + "://" + method + "?" + jsonStr;
                    PrivateMethod.loadURL(url);
                }
            },

            // iOS使用，发起URL请求
    loadURL: function (url) {
        var iFrame;
        iFrame = doc.createElement("iframe");
        iFrame.setAttribute("src", url);
        iFrame.setAttribute("style", "display:none;");
        iFrame.setAttribute("height", "0px");
        iFrame.setAttribute("width", "0px");
        iFrame.setAttribute("frameborder", "0");
        doc.body.appendChild(iFrame);
        // 发起请求后这个iFrame就没用了，所以把它从dom上移除掉
        iFrame.parentNode.removeChild(iFrame);
        iFrame = null;
    },

            //判断是安卓APP
            isAndroid: function () {
                var tmp = ua.toLowerCase();
                var android = tmp.indexOf("android") > -1;
                return !!android;
            },

            //判断是IOS APP
            isIos: function () {
                var tmp = ua.toLowerCase();
                var ios = tmp.indexOf("iphone") > -1;
                return !!ios;
            },

            json2Str: function (param) {
                if (param && typeof param === 'object') {
                    return JSON.stringify(param);
                   } else {
                      return param || '';
                   }
            },

        };

        for (var index in ExposeMethod) {
            if (ExposeMethod.hasOwnProperty(index)) {
                if (!Object.prototype.hasOwnProperty.call(jsBridge, index)) {
                    jsBridge[index] = ExposeMethod[index];
                }
            }
        }

    })();
