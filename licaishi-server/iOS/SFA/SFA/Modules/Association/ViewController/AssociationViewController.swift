//
//  AssociationViewController.swift
//  SFA
//
//  Created by 徐润肯 on 24/02/2018.
//  Copyright © 2018 runkenxu. All rights reserved.
//

import UIKit
import SnapKit
import SwiftyJSON
import Alamofire
import SwiftyTimer

class AssociationViewController: UIViewController {
    
    var urlString = "http://47.97.100.240:9095"
    
    var webview: WKWebView!
    
    var jsBridge: WebViewJavascriptBridge!
    
    var passthroughCbs = [String: PhiJBResponseCallback]()
    
    var shadows: [JSON]!
    
    var openPageCallback: PhiJBResponseCallback?
    var openPageCallbackParam: PhiJBResponse? = nil
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        view.backgroundColor = UIColor.white // 解决'卡顿'
        extendedLayoutIncludesOpaqueBars = true
        
        setupViews()
//        setupJSBridge()
        
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        navigationController?.navigationBar.isHidden = true
        openPageCallback?(.succeed(openPageCallbackParam))
        openPageCallback = nil
        
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        
        navigationController?.interactivePopGestureRecognizer?.isEnabled  = false
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        
        navigationController?.navigationBar.isHidden = false
        navigationController?.interactivePopGestureRecognizer?.isEnabled  = true
        
        super.viewWillDisappear(animated)
    }
    
    deinit {
        jsBridge.removeHandler("toast")
        jsBridge.removeHandler("toastLong")
        jsBridge.removeHandler("closePage")
        jsBridge.removeHandler("openPage")
        jsBridge.removeHandler("showLoading")
        jsBridge.removeHandler("hideLoading")
        
        jsBridge.removeHandler("netGet")
        jsBridge.removeHandler("netPost")
        jsBridge.removeHandler("netPut")
        jsBridge.removeHandler("netDelete")
        jsBridge.removeHandler("netRequest")
        
        jsBridge.removeHandler("initConfig")
        jsBridge.removeHandler("passthrough")
        jsBridge.removeHandler("publish")
        jsBridge.removeHandler("subscribe")
        
    }
    
    private func setupViews() {
        
        self.title = "智能排插管理页面"
        webview = WKWebView(frame: view.bounds.divided(atDistance: 20, from: .minYEdge).remainder)
//        webview.keyboardDisplayRequiresUserAction = false
        view.addSubview(webview)
        
        let url = URL(string: urlString)
        let urlRequest = URLRequest(url: url!, timeoutInterval: App.HTTPConfig.networkTimeout)
        webview.load(urlRequest)
        
        // THIS IS IMPORTANT!
//        automaticallyAdjustsScrollViewInsets = false
        
    }
    
    
    // MARK: -  Handle calls from JS
    
//    private func handleToast(json: JSON?, responseCallback: PhiJBResponseCallback?, duration: Double) {
//        guard let json = json,  let message = json["message"].string else {
//            responseCallback?(.missingParam("message"))
//            return
//        }
//
////        HUDHelper.shared.showWithMsg(message, duration: duration)
//        responseCallback?(.succeed(nil))
//    }
//
//
//    private func handleNetworkRequest(method: String, json: JSON?, responseCallback: PhiJBResponseCallback?) {
//
//        guard let json = json,  let url = json["url"].string else {
//            responseCallback?(.missingParam("url"))
//            return
//        }
//
//        var headers =  json["headers"].object as? HTTPHeaders ?? HTTPHeaders()
////        headers["Authorization"] = "Bearer " + ( User.current.accessToken ?? "" )
//
//
//        let sessionManager =  DefaultAlamofireManager.sharedManager
//        var request: DataRequest!
//        switch method {
//        case "get":
//            request = sessionManager.request(url,
//                                             method: .get,
//                                             headers: headers)
//        case "post":
//            headers["Content-Type"] = "application/json"
//            request = sessionManager.request(url,
//                                             method: .post,
//                                             parameters: json["requestBody"].object as? Parameters,
//                                             encoding: JSONEncoding.default,
//                                             headers: headers)
//        case "delete":
//            request = sessionManager.request(url,
//                                             method: .delete,
//                                             parameters: json["requestBody"].object as? Parameters,
//                                             encoding: JSONEncoding.default,
//                                             headers: headers)
//        default:
//            responseCallback?(.invalidValue(method))
//            return
//
//        }
//
//        request.responseString() { response in
//            let json = JSON(response.data)
//            guard Utils.checkMultiClientLogin(response: json) == false else { return }
//
//            if let error = response.error {
//                responseCallback?(.networkError(error.code, error.localizedDescription))
//            }else {
//                let ret = ["netResponse": response.result.value ?? "没有响应内容"]
//                responseCallback?(.succeed(ret))
//            }
//        }
//    }
//
//    private func handleOpenPage(_ page: String, _ extraData: String?, _ responseCallback: PhiJBResponseCallback?) {
//        switch page {
//        case "phihome.room.manage":
//            navigationController?.pushViewController(RoomListViewController.instanceFromStoryBoard(with: home), animated: true)
//            openPageCallback = responseCallback
//            openPageCallbackParam = ["hasChange":"1"]
//
//        case "phihome.device.picture":
//            let deviceIconVC = DeviceIconListViewController.instanceFromStoryBoard()
//            deviceIconVC.deviceIconViewModel = DeviceIconViewModel()
//
//            guard let iconGroupId = extraData else {
//                responseCallback?(.missingParam("pageExtra"))
//                return
//            }
//
//            var icon = DeviceIcon()
//            icon.groupId = iconGroupId
//
//            deviceIconVC.deviceIconViewModel?.deviceIcon.value = icon
//            deviceIconVC.deviceID = deviceID
//
//
//            navigationController?.pushViewController(deviceIconVC, animated: true)
//            openPageCallback = responseCallback
//            openPageCallbackParam = ["hasChange":"1"]
//
//        default:
//            break
//        }
//
//    }
//
//    // MARK: -  register handlers
//
//    private func setupJSBridge() {
//        //        if DEBUG {
//
//        if jsBridge != nil { return }
//
//        WebViewJavascriptBridge.enableLogging()
//        //        }
//
//        jsBridge = WebViewJavascriptBridge(forWebView: webview)
//        jsBridge.setWebViewDelegate(self)
//
//        registerUtils(jsBridge)
//        registerNetwork(jsBridge)
//        registerPassthrough(jsBridge)
//        registerUmengAnalysis(jsBridge)
//
//        jsBridge.callHandler("onPageFinish")
//
//    }
//
//    private func registerUtils(_ jsbridge: WebViewJavascriptBridge) {
//
//        jsBridge.registerHandler("toast") { [weak self] json, responseCallback in
//            self?.handleToast(json: json, responseCallback: responseCallback, duration: 2.0)
//        }
//
//        jsBridge.registerHandler("toastLong") { [weak self] json, responseCallback in
//            self?.handleToast(json: json, responseCallback: responseCallback, duration: 3.0)
//        }
//
//        jsBridge.registerHandler("openPage") { [weak self] (json: JSON?, responseCallback: PhiJBResponseCallback?) in
//            guard let json = json,  let pageName = json["pageName"].string else {
//                responseCallback?(.missingParam("pageName"))
//                return
//            }
//
//            self?.handleOpenPage(pageName, json["pageExtra"].string, responseCallback)
//        }
//
//
//        jsBridge.registerHandler("closePage") {[weak self]  (json: JSON?, responseCallback: PhiJBResponseCallback?) in
//            mqtt.clearWatches()
//
//            guard let me = self else { return }
//            //            me.shadows?.forEach {
//            //                mqtt.unwatch(me.shadowTopicPrefix + "/" + $0.stringValue + "/update/+")
//            //                mqtt.unwatch(me.shadowTopicPrefix + "/" + $0.stringValue + "/get/+")
//            //            }
//
//            me.navigationController?.popViewController(animated: true)
//        }
//
//
//        jsBridge.registerHandler("showLoading") {  (json: JSON?, responseCallback: PhiJBResponseCallback?) in
//
//
//
//            let message = (json?["message"].stringValue == "") ? false : true
//            var showTime = (json?["showTime"].double ?? 10000.0) / 1000
//
//            ZWHud.shared.show(duration: showTime)
//        }
//
//        jsBridge.registerHandler("hideLoading") {  (json: JSON?, responseCallback: PhiJBResponseCallback?) in
//            ZWHud.shared.dissmiss()
//        }
//
//        jsBridge.registerHandler("getNetType") { (json, responseCallback) in
//
//            responseCallback?(.succeed(
//                ["netType": Utils.getNetworkType().description]))
//
//        }
//
//        jsBridge.registerHandler("initConfig") { [weak self] (json: JSON?, responseCallback: PhiJBResponseCallback?) in
//
//            guard let json = json,  let shadows = json["shadows"].array else {
//                responseCallback?(.missingParam("shadows"))
//                return
//            }
//
//            guard let me = self else { return }
//
//            me.shadows = shadows
//
//            var ret = PhiJBResponse()
//            shadows.forEach { it in
//                print(it.stringValue)
//                mqtt.watch(me.shadowTopicPrefix + "/" + it.stringValue + "/get/accepted") { messsage in
//
//                    ret["pushData"] = messsage.string
//                    ret["topic"] = messsage.topic
//                    //                    me.getShadowCbs[it.stringValue]?(.succeed(ret) )
//                    //                    me.getShadowCbs.removeValue(forKey: it.stringValue)
//
//                    me.jsBridge.callHandler("pushData", data: PhiWebJSBridgeCode.succeed(ret).jsonString )
//                    ZWHud.shared.dissmiss()
//                }
//
//                mqtt.watch(me.shadowTopicPrefix + "/" + it.stringValue + "/update/accepted") { messsage in
//                    ret["pushData"] = messsage.string
//                    ret["topic"] = messsage.topic
//                    //                    me.updateShadowCbs[it.stringValue]?(.succeed(ret) )
//                    //                    me.updateShadowCbs.removeValue(forKey: it.stringValue)
//                    me.jsBridge.callHandler("pushData", data: PhiWebJSBridgeCode.succeed(ret).jsonString )
//
//                    ZWHud.shared.dissmiss()
//                }
//            }
//
//            responseCallback?(.succeed(nil))
//
//        }
//
//    }
//
//
//    private func registerPassthrough(_ jsbridge: WebViewJavascriptBridge) {
//
//
//        jsBridge.registerHandler("publish") {   (json: JSON?, responseCallback: PhiJBResponseCallback?) in
//
//            guard let json = json,
//                let topic = json["publishTopic"].string else {
//                    responseCallback?(.missingParam("publishTopic"))
//                    return
//            }
//            let path = ["publishData", "state", "desired","switch"]
//            if let publishData = json[path].dictionary {
//                for (key, value) in publishData {
//                    break;
//                }
//            }
//
//            if  GLobalRealReachability.currentReachabilityStatus() == .RealStatusNotReachable {
//                HUDHelper.shared.showWithMsg(PhiError.networkUnavailable.localizedDescription)
//                responseCallback?(.networkError(112, PhiError.networkUnavailable.localizedDescription))
//                return
//            }
//
//            ZWHud.shared.show {
//                HUDHelper.shared.showWithMsg(PhiError.deviceOffline.localizedDescription)
//            }
//
//            if  [.disconnected, .connecting].contains(mqtt.connState)  {
//                mqtt.connectCompleted["publish"] =  { [weak mqtt] in
//                    mqtt?.publish(topic, withString: json["publishData"].rawString([.castNilToNSNull: true]) ?? "" )
//                }
//                if mqtt.connState == .disconnected { mqtt.connect() }
//                responseCallback?(.succeed(nil))
//                return
//            }
//
//            mqtt.publish(topic, withString: json["publishData"].rawString([.castNilToNSNull: true]) ?? "" )
//
//            responseCallback?(.succeed(nil))
//        }
//
//        jsBridge.registerHandler("subscribe") {  [weak self] (json: JSON?, responseCallback: PhiJBResponseCallback?) in
//
//            guard let json = json,
//                let topic = json["subscribeTopic"].string else {
//                    responseCallback?(.missingParam("subscribeTopic"))
//                    return
//            }
//
//            if GLobalRealReachability.currentReachabilityStatus() == .RealStatusNotReachable {
//                HUDHelper.shared.showWithMsg(PhiError.networkUnavailable.localizedDescription)
//                responseCallback?(.networkError(112, PhiError.networkUnavailable.localizedDescription))
//                return
//            }
//
//            if  [.disconnected, .connecting].contains(mqtt.connState)  {
//                mqtt.connectCompleted["subscribe"] =  { [weak mqtt, weak self] in
//                    mqtt?.watch(topic) { [weak self] message in
//                        var ret = PhiJBResponse()
//                        ret["pushData"] = message.string
//                        ret["topic"] = message.topic
//                        self?.jsBridge.callHandler("pushData", data: PhiWebJSBridgeCode.succeed(ret).jsonString )
//                    }
//                }
//                if mqtt.connState == .disconnected { mqtt.connect() }
//                responseCallback?(.succeed(nil))
//                return
//            }
//
//            mqtt.watch(topic) { [weak self] message in
//                var ret = PhiJBResponse()
//                ret["pushData"] = message.string
//                ret["topic"] = message.topic
//                self?.jsBridge.callHandler("pushData", data: PhiWebJSBridgeCode.succeed(ret).jsonString )
//            }
//
//            responseCallback?(.succeed(nil))
//        }
//
//        jsBridge.registerHandler("passthrough") { [weak self] (json: JSON?, responseCallback: PhiJBResponseCallback?) in
//
//            guard let json = json,
//                let requestTopic = json["requestTopic"].string,
//                let responseTopic = json["responseTopic"].string else {
//                    responseCallback?(.missingParam("requestTopic或responseTopic"))
//                    return
//            }
//
//            let reach = Reachability.forInternetConnection()
//            if reach?.currentReachabilityStatus() == .NotReachable {
//                HUDHelper.shared.showWithMsg(PhiError.networkUnavailable.localizedDescription, ignoreSameSuccessorMsg: true)
//                responseCallback?(.networkError(400, PhiError.networkUnavailable.localizedDescription))
//                return
//            }
//
//            guard mqtt.connState == .connected else {
//                HUDHelper.shared.showWithMsg(PhiError.networkUnreachable.localizedDescription, ignoreSameSuccessorMsg: true)
//                responseCallback?(.networkError(400, PhiError.networkUnreachable.localizedDescription))
//                return
//            }
//
//            self?.passthroughCbs[responseTopic] = responseCallback
//
//            mqtt.watch(responseTopic) { message in
//                var ret = PhiJBResponse()
//                if let retString = message.string { // JSON字符串
//                    ret["dataType"] = "1"
//                    ret["mqttData"] = retString
//
//                }else { // 二进制, base64编码后传后前端
//                    ret["mqttData"] = message.payload.toBase64()
//                    ret["dataType"] = "2"
//                }
//
//
//                self?.passthroughCbs[message.topic]?(.succeed(ret) )
//                self?.passthroughCbs.removeValue(forKey: message.topic)
//                //                mqtt.unwatch(responseTopic)
//            }
//
//            mqtt.publish(requestTopic, withString: json["requestData"].rawString([.castNilToNSNull: true]) ?? "" )
//
//        }
//    }
//
//    private func registerNetwork(_ jsbridge: WebViewJavascriptBridge) {
//
//        jsBridge.registerHandler("netGet") { [weak self] json, responseCallback in
//            self?.handleNetworkRequest(method: "get", json: json, responseCallback: responseCallback)
//        }
//
//        jsBridge.registerHandler("netPost") { [weak self] json, responseCallback in
//            self?.handleNetworkRequest(method: "post", json: json, responseCallback: responseCallback)
//        }
//
//        jsBridge.registerHandler("netPut") { [weak self] json, responseCallback in
//            self?.handleNetworkRequest(method: "put", json: json, responseCallback: responseCallback)
//        }
//
//        jsBridge.registerHandler("netDelete") { [weak self] json, responseCallback in
//            self?.handleNetworkRequest(method: "delete", json: json, responseCallback: responseCallback)
//        }
//
//        jsBridge.registerHandler("netRequest") { [weak self] (json: JSON?, responseCallback: PhiJBResponseCallback?) in
//
//            guard let json2 = json,  let action = json2["netAction"].string else {
//                responseCallback?(.missingParam("netAction"))
//                return
//            }
//            self?.handleNetworkRequest(method: action, json: json, responseCallback: responseCallback)
//        }
//    }
//    private func registerUmengAnalysis(_ jsbridge: WebViewJavascriptBridge) {
//        jsBridge.registerHandler("onStatisEvent") { (json: JSON?, responseCallback: PhiJBResponseCallback?) in
//            guard let json = json, let event = json["eventType"].string else{
//                responseCallback?(.missingParam("eventType"))
//                return
//            }
//
//            switch (event.toInt()!){
//            case 1:
//                if let eventID = json["eventId"].string {
//                    UAPP.analysisEvent(eventID)
//                }else{
//                    responseCallback?(.missingParam("eventId"))
//                }
//            case 2:
//                if let pageTitle = json["pageTitle"].string {
//                    UAPP.beginTrackingPage(pageTitle)
//                }else{
//                    responseCallback?(.missingParam("pageTitle"))
//                }
//            case 3:
//                if let pageTitle = json["pageTitle"].string {
//                    UAPP.endTrackingPage(pageTitle)
//                }else{
//                    responseCallback?(.missingParam("pageTitle"))
//                }
//            default:
//                responseCallback?(.invalidValue(event))
//                return
//            }
//            responseCallback?(.succeed(nil))
//        }
//    }
    
}




// 字典拓展

func += <K, V> (left: inout [K:V], right: [K:V]) {
    for (k, v) in right {
        left[k] = v
    }
}

func + <K, V> (left:  [K:V], right: [K:V]) -> [K: V]  {
    var ret = left
    for (k, v) in right {
        ret[k] = v
    }
    return ret
}
