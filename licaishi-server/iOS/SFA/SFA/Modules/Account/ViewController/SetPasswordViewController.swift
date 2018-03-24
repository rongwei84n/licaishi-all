//
//  ModifyPasswordViewController.swift
//  SFA
//
//  Created by 徐润肯 on 11/02/2018.
//  Copyright © 2018 runkenxu. All rights reserved.
//

import UIKit

class SetPasswordViewController: UITableViewController, InstanceFromStoryBoard {

    static var storyBoardName: String {
        return "Account"
    }
    
    @IBOutlet weak var passwordTextField: UITextField!
    @IBOutlet weak var psdRepeatTextField: UITextField!
    
    private let viewModel = LoginViewModel()
    
    private var phoneNumber = ""
    private var verificationCode = ""
    
    static func instanceFromStoryBoard(phoneNumber: String, verificationCode: String) -> SetPasswordViewController {
        
        let vc = instanceFromStoryBoard()
        vc.phoneNumber = phoneNumber
        vc.verificationCode = verificationCode
        
        return vc
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
    }

    @IBAction func confirmButtonTapped(_ sender: Any) {
        
        let password = passwordTextField.text ?? ""
        let repeatPassword = psdRepeatTextField.text ?? ""
        
        if password.isEmpty {
            HUDHelper.shared.showWithMsg("密码不能为空")
            return
        }
        
        if !Utils.passwordValidation(password) {
            HUDHelper.shared.showWithMsg("密码无效")
            return
        }
        
        if repeatPassword.isEmpty {
            HUDHelper.shared.showWithMsg("请再次确认密码")
            return
        }
        
        if password != repeatPassword {
            HUDHelper.shared.showWithMsg("两次密码输入不一致")
            return
        }
        
        ZWHud.shared.show()
        _ = viewModel.forgetPassword(phoneNumber: phoneNumber, password: password.md5(), verificationCode: verificationCode)
            .subscribe(onNext: { [weak self] (isSuccessful) in
                
                ZWHud.shared.dismiss()
                
                if isSuccessful {
                    if let viewControllers = self?.navigationController?.viewControllers {
                        
                        for viewController in viewControllers {
                            if viewController.isKind(of: LoginViewController.self) {
                                self?.navigationController?.popToViewController(viewController, animated: true)
                            }
                        }
                        
                    }
                }
                
            }, onError: { (error) in
                
                ZWHud.shared.dismiss()
                HUDHelper.shared.showWithMsg(error.localizedDescription)
                
            })
        
    }
    
}
