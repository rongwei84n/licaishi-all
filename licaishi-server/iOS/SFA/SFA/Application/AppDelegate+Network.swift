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
                
            case "8":
                return "帐号或密码错误，请重新输入"
                
            default:
                return ""
                
            }
        }
        
        return ""
    }
    
}

