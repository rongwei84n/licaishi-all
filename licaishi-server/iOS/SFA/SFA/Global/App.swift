//
//  Network.swift
//  SFA
//
//  Created by 徐润肯 on 11/02/2018.
//  Copyright © 2018 runkenxu. All rights reserved.
//

import Foundation

struct App {
    
    
}

extension App {
    
    enum Domain {
        
        case dev
        
        case test
        
        case product
        
        var name: String {
            get {
                switch self {
                case .dev:
                    return "http://47.97.100.240"
                case .test:
                    return "http://sithome.phicomm.com"
                case .product:
                    return "https://home.phicomm.com"
                }
            }
        }
        
    }
    
}

extension App {
    
    struct HTTPConfig {
        
        #if DEBUG
        private(set) static var domain: Domain = Domain.dev
        
        #elseif TEST
        private(set) static var domain: Domain = Domain.test
        
        #elseif RELEASE
        private(set) static var domain: Domain = Domain.production
        
        #endif
        
        public static let networkTimeout = 10.0
        
    }
    
    
}
