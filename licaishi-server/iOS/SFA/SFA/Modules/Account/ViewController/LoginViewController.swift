//
//  LoginViewController.swift
//  SFA
//
//  Created by 徐润肯 on 08/02/2018.
//  Copyright © 2018 runkenxu. All rights reserved.
//

import UIKit
import CryptoSwift

class LoginViewController: UITableViewController, InstanceFromStoryBoard, UITextFieldDelegate {
    
    static var storyBoardName: String {
        return "Account"
    }
    
    @IBOutlet weak var phoneNumberTextField: UITextField!

    @IBOutlet weak var passwordTextField: UITextField!
    
    private let viewModel = LoginViewModel()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        
        
    }
    
    @IBAction func loginButtonTapped(_ sender: Any) {
        
        let phone = phoneNumberTextField.text ?? ""
        let password = passwordTextField.text ?? ""
        
        if phone.isEmpty {
            print("手机号码不能为空")
            return
        }
        
        if password.isEmpty {
            print("密码不能为空")
            return
        }
        
        // 登录
        ZWHud.shared.show()
        _ = viewModel.login(phoneNumber: phone, password: password.md5()).subscribe(onNext: { [weak self] (isSuccessful) in
            
            ZWHud.shared.dismiss()
            
            if isSuccessful {
                self?.navigationController?.popViewController(animated: true)
            }
            
        }, onError: { (error) in
            
            ZWHud.shared.dismiss()
            HUDHelper.shared.showWithMsg(error.localizedDescription)
            
        })
        
    }
    
    @IBAction func registerButtonTapped(_ sender: Any) {
        let registerController = RegisterViewController.instanceFromStoryBoard(phoneNumber: phoneNumberTextField.text)
        navigationController?.pushViewController(registerController, animated: true)
    }
    
    @IBAction func resetPasswordButtonTapped(_ sender: Any) {
        
        let vc = ResetPasswordViewController.instanceFromStoryBoard(phoneNumber: phoneNumberTextField.text)
        navigationController?.pushViewController(vc, animated: true)
        
    }
    
}
