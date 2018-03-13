//
//  PhoneTextFieldDelegate.swift
//  SFA
//
//  Created by 徐润肯 on 23/02/2018.
//  Copyright © 2018 runkenxu. All rights reserved.
//

import Foundation
import UIKit

class PhoneTextFieldDelegate: NSObject, UITextFieldDelegate {
    
    var shouldChangeCallback: ((UITextField, NSRange, String)->())?
    
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        
        if !string.isIncludeNumberOnly() {
            return false
        }
        
        let count = (textField.text?.count ?? 0) + string.count
        
        if count <= 11 {
            shouldChangeCallback?(textField, range, string)
            return true
        }
        
        return false
        
    }
    
}
