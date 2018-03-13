//
//  UIAlertController+Extension.swift
//  PhiSpeaker
//
//  Created by liman on 29/09/2017.
//  Copyright © 2017 Phicomm. All rights reserved.
//

import Foundation
import UIKit

extension UIAlertController {
    
    static func show(_ msg: String?, _ vc: UIViewController?, _ button: String?) {
        weak var weakVC = vc
        
        //step 1.
        let attributedString = NSAttributedString(string: msg ?? "", attributes: [
            .font : UIFont.systemFont(ofSize: 17),
            .foregroundColor : UIColor(hexString: "#313131") ?? ""
            ])
        
        //step 2.
        let alert = UIAlertController(title:nil , message: nil, preferredStyle: .alert)
        alert.setValue(attributedString, forKey: "attributedTitle")
        
        //step 3.
        let action = UIAlertAction(title: button ?? "", style: .default, handler: { (action) in
        })
        action.setValue(UIColor(hexString: "#313131"), forKey: "titleTextColor")
        
        //step 4.
//        let action2 = UIAlertAction(title: "取消", style: .default, handler: { (action) in
//        })
//        action2.setValue(UIColor.init(hexString: "#ff9b1b"), forKey: "titleTextColor")
        
        //step 5.
        alert.addAction(action)
//        alert.addAction(action2)
        
        DispatchQueue.main.async {
            weakVC?.present(alert, animated: true, completion: nil)
        }
    }
    
    
    
    static func show(_ msg: String?, _ vc: UIViewController?, _ button1: String?, _ button2: String?, _ color1: String?, _ color2: String?, callback1: @escaping () -> Void, callback2: @escaping () -> Void) {
        weak var weakVC = vc
        
        //step 1.
        let attributedString = NSAttributedString(string: msg ?? "", attributes: [
            .font : UIFont.systemFont(ofSize: 17),
            .foregroundColor : UIColor(hexString: "#313131") ?? ""
            ])
        
        //step 2.
        let alert = UIAlertController(title:nil , message: nil, preferredStyle: .alert)
        alert.setValue(attributedString, forKey: "attributedTitle")
        
        //step 3.
        let action1 = UIAlertAction(title: button1 ?? "", style: .default, handler: { (action) in
            callback1()
        })
        action1.setValue(UIColor(hexString: color1 ?? ""), forKey: "titleTextColor")
        
        //step 4.
        let action2 = UIAlertAction(title: button2 ?? "", style: .default, handler: { (action) in
            callback2()
        })
        action2.setValue(UIColor(hexString: color2 ?? ""), forKey: "titleTextColor")
        
        //step 5.
        if button1 != nil {
            alert.addAction(action1)
        }
        if button2 != nil {
            alert.addAction(action2)
        }
        
        DispatchQueue.main.async {
            weakVC?.present(alert, animated: true, completion: nil)
        }
    }
    
}
