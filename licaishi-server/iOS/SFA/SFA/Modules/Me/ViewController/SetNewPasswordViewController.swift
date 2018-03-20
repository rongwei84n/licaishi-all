//
//  SetNewPasswordViewController.swift
//  SFA
//
//  Created by 徐润肯 on 11/02/2018.
//  Copyright © 2018 runkenxu. All rights reserved.
//

import UIKit

class SetNewPasswordViewController: UITableViewController, InstanceFromStoryBoard {

    static var storyBoardName: String {
        return "Me"
    }
    
    @IBOutlet weak var oldPasswordTextField: UITextField!
    @IBOutlet weak var newPasswordTextField: UITextField!
    @IBOutlet weak var psdRepeatTextField: UITextField!
    
    private let viewModel = MeViewModel()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        
    }

    @IBAction func confirmButtonTapped(_ sender: Any) {
        
        let oldPassword = oldPasswordTextField.text ?? ""
        let newPassword = newPasswordTextField.text ?? ""
        let repeatPassword = psdRepeatTextField.text ?? ""
        
        if oldPassword.isEmpty {
            print("旧密码不能为空")
            return
        }
        
        if newPassword.isEmpty {
            print("新密码不能为空")
            return
        }
        
        if !Utils.passwordValidation(newPassword) {
            print("新密码无效")
            return
        }
        
        if repeatPassword.isEmpty {
            print("请再次确认新密码")
            return
        }
        
        if newPassword != repeatPassword {
            print("两次密码输入不一致")
            return
        }
        
        ZWHud.shared.show()
        _ = viewModel.changePassword(oldPassword: oldPassword, newPassword: newPassword.md5()).subscribe(onNext: { [weak self] (isSuccessful) in
            
            ZWHud.shared.dismiss()
            
            if isSuccessful {
                self?.navigationController?.popViewController(animated: true)
            }
            
            
        }, onError: { (error) in
            
            ZWHud.shared.dismiss()
            HUDHelper.shared.showWithMsg(error.localizedDescription)
            
        })
        
    }
    
}
