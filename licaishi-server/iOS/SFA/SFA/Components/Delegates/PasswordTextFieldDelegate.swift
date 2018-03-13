//
//  PasswordTextFieldDelegate.swift
//  SFA
//
//  Created by 徐润肯 on 23/02/2018.
//  Copyright © 2018 runkenxu. All rights reserved.
//

import UIKit

class PasswordTextFieldDelegate: NSObject, UITextFieldDelegate {
    
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        
        if string.containSpecialCharaters() || string.isIncludeChineseIn() {
            return false
        }
        
        let count = (textField.text?.count ?? 0) + string.count
        
        return count <= 20
        
    }
    
}
