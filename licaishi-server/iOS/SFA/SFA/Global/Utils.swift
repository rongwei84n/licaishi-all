//
//  Utils.swift
//  SFA
//
//  Created by 徐润肯 on 23/02/2018.
//  Copyright © 2018 runkenxu. All rights reserved.
//

let PHONE_REGEX = ""
let PASSWORD_REGEX = ""

class Utils {
    
    static func phoneValidation(_ phoneNumber: String?) -> Bool {
        
        // 判断正则表达式
        
        return true
    }
    
    static func passwordValidation(_ password: String?) -> Bool {
        
        // 判断正则表达式
        
        return true
    }
    
}

func dispatch_main_async_safe(callback: @escaping ()-> Void) {
    
    if Thread.isMainThread {
        callback()
    }
    else {
        DispatchQueue.main.async( execute: {
            callback()
        })
    }
}
