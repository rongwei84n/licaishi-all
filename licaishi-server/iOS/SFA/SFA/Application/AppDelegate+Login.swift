//
//  AppDelegate+Login.swift
//  SFA
//
//  Created by 徐润肯 on 06/03/2018.
//  Copyright © 2018 runkenxu. All rights reserved.
//

import Foundation

extension AppDelegate {
    
    func autoLogin() {
        
        if User.current.isLoggedIn {
            
            // 登录过,自动登录
            let vc = UINavigationController(rootViewController: MeViewController.instanceFromStoryBoard())
            window?.rootViewController? = vc
            
        } else {
            let vc = UINavigationController(rootViewController: LoginViewController.instanceFromStoryBoard())
            window?.rootViewController? = vc
        }
        
    }
    
}
