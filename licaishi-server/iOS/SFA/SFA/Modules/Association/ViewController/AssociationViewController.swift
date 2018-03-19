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

class AssociationViewController: UIViewController, InstanceFromStoryBoard {
    
    static var storyBoardName: String {
        return "Me"
    }
    
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
        setupJSBridge()
        
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
        jsBridge.removeHandler("openPage")
        
        jsBridge.removeHandler("netGet")
        jsBridge.removeHandler("netPost")
        jsBridge.removeHandler("netPut")
        jsBridge.removeHandler("netDelete")
        jsBridge.removeHandler("netRequest")
    }
    
    private func setupViews() {
        
        webview = WKWebView(frame: view.bounds.divided(atDistance: 20, from: .minYEdge).remainder)
        view.addSubview(webview)
        
        let url = URL(string: urlString)
        let urlRequest = URLRequest(url: url!, timeoutInterval: App.HTTPConfig.networkTimeout)
        webview.load(urlRequest)
        
        // THIS IS IMPORTANT!
//        automaticallyAdjustsScrollViewInsets = false
        
    }
    
    private func handleNetworkRequest(method: String, json: JSON?, responseCallback: PhiJBResponseCallback?) {

        guard let json = json,  let url = json["url"].string else {
            responseCallback?(.missingParam("url"))
            return
        }

        var headers =  json["headers"].object as? HTTPHeaders ?? HTTPHeaders()
        headers["Authorization"] = "Bearer " + ( User.current.accessToken ?? "" )


        let sessionManager =  DefaultAlamofireManager.sharedManager
        var request: DataRequest!
        switch method {
        case "get":
            request = sessionManager.request(url,
                                             method: .get,
                                             headers: headers)
        case "post":
            headers["Content-Type"] = "application/json"
            request = sessionManager.request(url,
                                             method: .post,
                                             parameters: json["requestBody"].object as? Parameters,
                                             encoding: JSONEncoding.default,
                                             headers: headers)
        case "delete":
            request = sessionManager.request(url,
                                             method: .delete,
                                             parameters: json["requestBody"].object as? Parameters,
                                             encoding: JSONEncoding.default,
                                             headers: headers)
        default:
            responseCallback?(.invalidValue(method))
            return

        }

        ZWHud.shared.show()
        request.responseString() { response in
            
            ZWHud.shared.dismiss()
            
            let json = JSON(response.data)
//            if json["error"].stringValue == "0" {
//                let ret = ["netResponse": response.result.value ?? "没有响应内容"]
                let ret = response.result.value ?? "没有响应内容"
                responseCallback?(.succeed(ret))
//            } else {
//                HUDHelper.shared.showWithMsg(json["error"].stringValue)
//            }
        }
    }

    private func handleOpenPage(_ page: String, _ extraData: String?, _ responseCallback: PhiJBResponseCallback?) {
        switch page {
            
        case "lcs.account.personinfo", "lcs.account.login":
            
            if User.current.isLoggedIn {
                navigationController?.pushViewController(MeViewController.instanceFromStoryBoard(), animated: true)
                openPageCallback = responseCallback
//                openPageCallbackParam = ["hasChange":"1"]
            }
            else {
                navigationController?.pushViewController(LoginViewController.instanceFromStoryBoard(), animated: true)
            }

        default:
            break
        }

    }

    // MARK: -  register handlers
    private func setupJSBridge() {
        
        if jsBridge != nil { return }

        WebViewJavascriptBridge.enableLogging()

        jsBridge = WebViewJavascriptBridge(forWebView: webview)
        jsBridge.setWebViewDelegate(self)

        registerUtils(jsBridge)
        registerNetwork(jsBridge)

        jsBridge.callHandler("onPageFinish")

    }

    private func registerUtils(_ jsbridge: WebViewJavascriptBridge) {

        jsBridge.registerHandler("openPage") { [weak self] (json: JSON?, responseCallback: PhiJBResponseCallback?) in
            guard let json = json,  let pageName = json["pageName"].string else {
                responseCallback?(.missingParam("pageName"))
                return
            }

            self?.handleOpenPage(pageName, json["pageExtra"].string, responseCallback)
        }
        
    }

    private func registerNetwork(_ jsbridge: WebViewJavascriptBridge) {

        jsBridge.registerHandler("netGet") { [weak self] json, responseCallback in
            self?.handleNetworkRequest(method: "get", json: json, responseCallback: responseCallback)
        }

        jsBridge.registerHandler("netPost") { [weak self] json, responseCallback in
            self?.handleNetworkRequest(method: "post", json: json, responseCallback: responseCallback)
        }

        jsBridge.registerHandler("netPut") { [weak self] json, responseCallback in
            self?.handleNetworkRequest(method: "put", json: json, responseCallback: responseCallback)
        }

        jsBridge.registerHandler("netDelete") { [weak self] json, responseCallback in
            self?.handleNetworkRequest(method: "delete", json: json, responseCallback: responseCallback)
        }

        jsBridge.registerHandler("netRequest") { [weak self] (json: JSON?, responseCallback: PhiJBResponseCallback?) in

            guard let json2 = json,  let action = json2["netAction"].string else {
                responseCallback?(.missingParam("netAction"))
                return
            }
            self?.handleNetworkRequest(method: action, json: json, responseCallback: responseCallback)
        }
    }
    
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