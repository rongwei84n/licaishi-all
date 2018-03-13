//
//  HUDHelper.swift
//  PhiHome
//
//  Created by liman on 2017/7/26.
//  Copyright © 2017年 Phicomm. All rights reserved.
//

import Foundation
import UIKit


class HUDHelper {

    //callback
    var callback:(() -> Void)?
    
    // MARK: - 单例
    static let shared = HUDHelper()
    private init() {}

    private var lastMsg: String?

    // MARK: - 显示信息
    func showWithMsg(_ msg : String, duration: Double = 1.5, ignoreSameSuccessorMsg: Bool = false) {
        
        if ignoreSameSuccessorMsg && msg == lastMsg {
            return
        }
        lastMsg = msg
        
        dispatch_main_async_safe { [weak self] in
            
            SVProgressHUD.setDefaultStyle(SVProgressHUDStyle.dark)
            SVProgressHUD.setFont(UIFont.systemFont(ofSize: 15))
            SVProgressHUD.setCornerRadius(10)
            SVProgressHUD.showCustomStatus(msg, maskType: SVProgressHUDMaskType.none)
            SVProgressHUD.dismiss(withDelay: duration) { [weak self] in
                self?.callback?()
                
                if msg.contains("登录过期") {
                    //退出登录
                    self?.logout()
                }
            }
            
        }
    }
    
    //MARK: - 退出登录
    func logout() {
        
        //清空User对象信息
        User.current.clearUserInfo()
        
        DispatchQueue.main.asyncAfter(deadline: DispatchTime.now() + 0.5) {
            let vc = LoginViewController()
            UIApplication.shared.delegate?.window??.rootViewController? = UINavigationController.init(rootViewController:vc)
        }
    }
    
}
