//
//  ZWHUD.swift
//  PhiHome
//
//  Created by 左为 on 2017/7/28.
//  Copyright © 2017年 Phicomm. All rights reserved.
//

import Foundation
import UIKit
import SnapKit
import SwiftyTimer

class ZWHud {
    static let shared = ZWHud()
    
    private init() {}
    
    var view: HudView!
    
    var flag: Bool?
    
    var isShowing:  Bool = false
    
    typealias dismissCallback = ()->Void
    
    var task: DispatchWorkItem?
    
    func show(withText: Bool = false, duration: Double = App.HTTPConfig.networkTimeout, timeout: dismissCallback? = nil) {
        
        if isShowing { return }
        
        self.isShowing = true
        
        if flag == nil || flag == false {
            dispatch_main_async_safe { [weak self] in
                
                self?.view = HudView()
                self?.view.frame = (UIApplication.shared.delegate?.window??.bounds)!
                self?.view.backgroundColor = UIColor(white: 0, alpha: 0)
                if let view = self?.view {
                    UIApplication.shared.delegate?.window??.addSubview(view)
                }
                
                let contentView = UIView()
                contentView.backgroundColor = UIColor(white: 0.2, alpha: 0.8)
                contentView.layer.cornerRadius = 10
                self?.view.addSubview(contentView)
                
                contentView.snp.makeConstraints { (make) in
                    make.width.height.equalTo(80)
                    make.center.equalToSuperview()
                    
                    if !withText {
                        make.width.height.equalTo(144/2)
                    }
                }
                
                let imageView1 = UIImageView()
                imageView1.image = UIImage(named: "icon_loading_fragment_1")
                contentView.addSubview(imageView1)
                
                imageView1.snp.makeConstraints { (make) in
                    make.width.height.equalTo(35)
                    make.centerX.equalToSuperview()
                    
                    
                    if !withText {
                        make.centerY.equalToSuperview()
                    }else{
                        make.centerY.equalToSuperview().offset(-10)
                    }
                }
                
                let imageView2 = UIImageView()
                imageView2.image = UIImage(named: "icon_loading_fragment_2")
                
                //李满
                UIImageView.rotate360Degree(with: imageView2)
                
                
                contentView.addSubview(imageView2)
                
                imageView2.snp.makeConstraints { (make) in
                    make.width.height.equalTo(35)
                    make.centerX.equalToSuperview()
                    
                    if !withText {
                        make.centerY.equalToSuperview()
                    }else{
                        make.centerY.equalToSuperview().offset(-10)
                    }
                }
                
                if withText {
                    let label = UILabel()
                    label.text = "加载中..."
                    label.textAlignment = NSTextAlignment.center
                    label.font = UIFont.systemFont(ofSize: 12)
                    label.textColor = .white
                    contentView.addSubview(label)
                    
                    label.snp.makeConstraints { (make) in
                        make.top.equalTo(imageView1.snp.bottom).offset(8)
                        make.centerX.equalToSuperview()
                    }
                }
                
                /***********************************************************/
                if self?.task == nil {
                    self?.task = DispatchWorkItem {
                        self?.dismiss()
                        timeout?()
                    }
                }
                guard let task = self?.task else {return}
                DispatchQueue.main.asyncAfter(deadline: DispatchTime.now() + duration, execute: task)
            }
            
            flag = true
            DispatchQueue.main.asyncAfter(deadline: DispatchTime.now() + 0.1) { [weak self] in
                self?.flag = false
            }
        }
    }
    
    func dismiss() {
        task?.cancel()
        task = nil
        
        dispatch_main_async_safe { [weak self] in
            
            for view in (UIApplication.shared.delegate?.window??.subviews)! {
                if view.isKind(of: HudView.self) {
                    view.removeFromSuperview()
                }
            }
            
            self?.isShowing = false
        }
    }
}

class HudView: UIView {
    
}
