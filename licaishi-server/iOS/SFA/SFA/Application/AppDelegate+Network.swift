//
//  AppDelegate+Network.swift
//  SFA
//
//  Created by 徐润肯 on 12/03/2018.
//  Copyright © 2018 runkenxu. All rights reserved.
//

import Foundation

let GLobalRealReachability: RealReachability = RealReachability.sharedInstance()

enum ServerError : LocalizedError {
    
    case internalError(code : String?)
    case businessError(code : String?)
    
    var errorDescription: String? {
        switch self {
            
        case .internalError(let code), .businessError(let code):
            
            return codeTranslation(code)
            
        }
    }
    
    func codeTranslation(_ code: String?) -> String {
        
        if let code = code {
            switch code {
                
            case "1":
                return "验证码错误"
                
            case "2":
                return "验证码已过期"
                
            case "8":
                return "帐号或密码错误，请重新输入"
                
            case "14":
                return "该账户已存在"
                
            case "23":
                return "验证码已使用"
                
            case "50":
                return "服务器异常"
                
            default:
                return ""
                
            }
        }
        
        return ""
    }
    
}

