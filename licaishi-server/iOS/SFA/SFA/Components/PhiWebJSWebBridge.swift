//
//  PhiWebJSWebBridge.swift
//  PhiHome
//
//  Created by Alex on 26/9/17.
//  Copyright © 2017年 Phicomm. All rights reserved.
//

import Foundation
import SwiftyJSON

typealias PhiJBResponse = String
//typealias PhiJBResponse = [String: String]
typealias PhiJBResponseCallback = (PhiWebJSBridgeCode)  -> Void
typealias PhiJBHandler = (JSON?,  PhiJBResponseCallback?) -> Void


enum PhiWebJSBridgeCode:  Error, CustomStringConvertible {
    case succeed(PhiJBResponse? )
    case invalidJSON
    case missingParam(String)
    case invalidValue(String)
    case networkError(Int,String?)
    case busy
    
    var description: String {
        switch self {
        case .succeed:
            return "成功"
        case .invalidJSON:
            return "非法的JSON字符串"
        case .missingParam(let paramName):
            return "参数缺少Key:\(paramName)"
        case .invalidValue(let value):
            return "不支持的值:\(value)"
        case .networkError(let code, let errorMsg):
            return "网络错误:\(code) \(errorMsg ?? "")" // Unused
        case .busy:
            return "点击太频繁,请稍侯"
        }
    }
    
    var code: Int {
        switch self {
        case .succeed: return 0
        case .invalidJSON: return 10001
        case .missingParam: return 10002
        case .invalidValue: return 10003
        case .networkError: return 10004
        case .busy: return 10005
        }
    }
    
    var jsonObject: JSON {
        switch self {
        case .networkError(let code, let errorMsg): // 如果是网络错误,将errorCode置为网络错误Code
            return JSON(["errorCode": code, "errorMsg": errorMsg])
        default:
            return JSON(["errorCode":code, "errorMsg": description])
        }
    }
    
    var jsonString: String? {
        switch self {
        case .succeed(let succeed): // 如果是成功,将字典加入到返回中
//            var d: [String: Any] = ["errorCode":code, "errorMsg": description]
//            if let succeed = succeed {
//                d += succeed
//            }
//            return JSON(d).rawString()
            return JSON(succeed).rawString()
        default:
            return jsonObject.rawString()
        }
        
    }
}


extension WebViewJavascriptBridge {
    func registerHandler(_ handlerName: String!, handler: PhiJBHandler!) {
        
        // convert parameters to JSON object
        registerHandler(handlerName, handler: { [weak self] (data: Any?, origResponseCallback: WVJBResponseCallback?) in
            
            let phiCallback: PhiJBResponseCallback = { [weak self] res in
                origResponseCallback?(res.jsonString)
            }
            
            if let dataString = data as? String, !dataString.isEmpty {
                let json = JSON(parseJSON: dataString)
                guard let dict = json.dictionary, dict.count > 0 else {
                    phiCallback(.invalidJSON)
                    return
                }
                
                handler?(json, phiCallback)
                
            }else {
                handler?(nil, phiCallback)
            }
        })
    }
}
