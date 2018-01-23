/* eslint-disable */
;(function () {

    if (window.phihome) {
        return
    }

    var JSBridge;

    function init(config) {
        JSBridge = config.bridge;
        // JSBridge.init(function (msg, callback) {
        //
        // });
        window.initConfigFlag = true;
        initRegister();
    }

    function initRegister() {
        JSBridge.registerHandler("pushData", function (data) {
            var event = new Event('pushDataReceived');
            event.data = data;
            document.dispatchEvent(event);
        });
        JSBridge.registerHandler("nativeBack", function (data) {
          var event = new Event('nativeBack');
          event.data = data;
          document.dispatchEvent(event);
        });
    }

    function notifyiOSLoadJSB() {
        var WVJBIframe = document.createElement('iframe');
        WVJBIframe.style.display = 'none';
        WVJBIframe.src = 'https://__bridge_loaded__';
        document.documentElement.appendChild(WVJBIframe);
        setTimeout(function () {
            document.documentElement.removeChild(WVJBIframe)
        }, 0)
    }

    notifyiOSLoadJSB();

    var web = {
        registerHandler: function (methodName, method) {
            JSBridge.registerHandler(methodName, method);
        }
    };
    //设备相关api
    var iot = {
        controlDevice: function (message, successCallback) {
            JSBridge.callHandler('controlDevice', message, function (response) {
                successCallback(response);
            });
        },
        initConfig: function (message, successCallback) {//先初始化
            JSBridge.callHandler('initConfig', message, function (response) {
                successCallback(response);
            });
        },

        //透传方法

        passthrough: function (requestTopic, responseTopic, requestData, dataType, successCallback) {
            var data = JSON.stringify({
                requestTopic: requestTopic,
                responseTopic: responseTopic,
                requestData: requestData,
                dataType: dataType
            });
            JSBridge.callHandler('passthrough', data, function (response) {
                successCallback(response);
            });
        },

        publish: function (publishTopic, publishData, successCallback) {
            var data = JSON.stringify({publishTopic: publishTopic, publishData: publishData});
            if (!navigator.onLine) {
              window.phihome.app.toast('当前网络不可用，请检查网络设置', function (response) {
              });
            }
            JSBridge.callHandler('publish', data, function (response) {
                successCallback(response)
            });
        },
        subscribe: function (subscribeTopic, successCallback) {
            var data = JSON.stringify({subscribeTopic: subscribeTopic});
            JSBridge.callHandler('subscribe', data, function (response) {
                successCallback(response)
            });
        },

    };


    //native App相关api
    var app = {
        toast: function (message, successCallback) {
            var data = JSON.stringify({message: message});
            JSBridge.callHandler('toast', data, function (response) {
                successCallback(response);
            });
        },
        toastLong: function (message, successCallback) {
            var data = JSON.stringify({message: message});
            JSBridge.callHandler('toastLong', data, function (response) {
                successCallback(response);
            });
        },
        showLoading: function (message, showTime, successCallback) {
            var data = JSON.stringify({message: message, showTime: showTime});
            JSBridge.callHandler('showLoading', data, function (response) {
                successCallback(response);
            });
        },
        hideLoading: function (message, successCallback) {
            JSBridge.callHandler('hideLoading', message, function (response) {
                successCallback(response);
            });
        },
        closePage: function (message, successCallback) {
            JSBridge.callHandler('closePage', message, function (response) {
                successCallback(response);
            });
        },
        getNetType: function (message, successCallback) {
            JSBridge.callHandler('getNetType', message, function (response) {
                successCallback(response);
            });
        },
        openPage: function (pageName, pageExtra, successCallback) {
            var data = JSON.stringify({pageName: pageName, pageExtra: pageExtra});
            JSBridge.callHandler('openPage', data, function (response) {
                successCallback(response);
            });
        }
    };

    //工具类相关api
    var util = {
        netRequest: function (netAction, url, requestHeader, requestBody, successCallback) {
            var data = JSON.stringify({
                netAction: netAction,
                url: url,
                requestHeader: requestHeader,
                requestBody: requestBody
            });
            JSBridge.callHandler('netRequest', data, function (response) {
                var responseJson = JSON.parse(response);
                if (responseJson.errorCode !== 0) { // 错误状态包括：设备处于离线状态
                    window.phihome.app.toast(responseJson.errorMsg, function (response) {
                    });
                }
                successCallback(response);
            });
        }
    };

    window.phihome = {
        init: init,
        iot: iot,
        app: app,
        util: util
    };

    document.addEventListener('WebViewJavascriptBridgeReady', function onReady(ev) {
        phihome.init({'bridge': ev.bridge});
    });
})();
/* eslint-disable */
