//
//  UIViewController+Extension.swift
//  PhiHome
//
//  Created by ZhiYun Huang on 28/07/2017.
//  Copyright © 2017 Phicomm. All rights reserved.
//

import Foundation
import JRSwizzle

extension UIViewController {
    
    static func swizzle() {
        do {
            try self.jr_swizzleMethod(#selector(viewDidLoad), withMethod: #selector(swizzle_viewDidLoad))
        } catch _ {
            
        }
    }
    
    @objc func swizzle_viewDidLoad() {
        
        navigationItem.backBarButtonItem = UIBarButtonItem(title: " ", style: .plain, target: nil, action: nil)
        
        swizzle_viewDidLoad()
        
    }
    
}
