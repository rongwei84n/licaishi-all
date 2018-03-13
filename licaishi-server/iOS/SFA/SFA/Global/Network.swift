//
//  Network.swift
//  SFA
//
//  Created by 徐润肯 on 23/02/2018.
//  Copyright © 2018 runkenxu. All rights reserved.
//

import Foundation
import Alamofire

class DefaultAlamofireManager: Alamofire.SessionManager { // 设置默认超时时间为10秒
    
    static let sharedManager: DefaultAlamofireManager = {
        
        let configuration = URLSessionConfiguration.default
        configuration.httpAdditionalHeaders = Alamofire.SessionManager.defaultHTTPHeaders
        configuration.timeoutIntervalForRequest = App.HTTPConfig.networkTimeout // as seconds, you can set your request timeout
        configuration.timeoutIntervalForResource = App.HTTPConfig.networkTimeout // as seconds, you can set your resource timeout
//        configuration.requestCachePolicy = .useProtocolCachePolicy
        
        return DefaultAlamofireManager(configuration: configuration)
        
    }()
    
}
