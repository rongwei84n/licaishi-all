//
//  RegisterViewController.swift
//  SFA
//
//  Created by 徐润肯 on 11/02/2018.
//  Copyright © 2018 runkenxu. All rights reserved.
//

import UIKit
import RxSwift

class RegisterViewController: UITableViewController, InstanceFromStoryBoard, UITextFieldDelegate {
    
    static var storyBoardName: String {
        return "Account"
    }
    
    @IBOutlet weak var phoneNumberTextField: UITextField!
    @IBOutlet weak var codeTextField: UITextField!
    @IBOutlet weak var passwordTextField: UITextField!
    @IBOutlet weak var psdRepeatTextField: UITextField!
    
    @IBOutlet weak var gainCodeButton: UIButton!
    
    private let viewModel = LoginViewModel()
    
    private let phoneTextFieldDelegate = PhoneTextFieldDelegate()
    private let passwordTextFieldDelegate = PasswordTextFieldDelegate()
    
    private var phoneNumber: String?
    
    private var timer: Timer?
    private var second = 45
    
    static func instanceFromStoryBoard(phoneNumber: String?) -> RegisterViewController {
        
        let vc = instanceFromStoryBoard()
        vc.phoneNumber = phoneNumber
        
        return vc
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()

        gainCodeButton.isEnabled = false
        if let phoneNumber = phoneNumber {
            gainCodeButton.isEnabled = phoneNumber.countForChineseString() >= 11
        }
        
        phoneNumberTextField.text = phoneNumber
        phoneTextFieldDelegate.shouldChangeCallback = { [weak self] (textField, range, string) in
            let count = (textField.text?.count ?? 0) + string.count
            self?.gainCodeButton.isEnabled = (count >= 11 && !string.isEmpty)
        }
        phoneNumberTextField.delegate = phoneTextFieldDelegate
        
        passwordTextField.delegate = passwordTextFieldDelegate
        
    }
    
    @objc func countdown() {
        
        second -= 1
        if second <= 0 {
            gainCodeButton.setTitle("获取验证码", for: .normal)
            gainCodeButton.isEnabled = true
            second = 45
            
            invalidateTimer()
        }
        else {
            gainCodeButton.setTitle("重新获取(\(second))", for: .disabled)
        }
        
    }
    
    private func invalidateTimer() {
        
        if timer != nil {
            timer?.invalidate()
            timer = nil
        }
    }

    @IBAction func gainCodeButtonTapped(_ sender: Any) {
        
        gainCodeButton.isEnabled = false
        
        if timer == nil {
            timer = Timer.scheduledTimer(timeInterval: 1.0, target: self, selector: #selector(countdown), userInfo: nil, repeats: true)
            gainCodeButton.setTitle("重新获取(\(second))", for: .disabled)
        }
        
        // 请求验证码
        _ = viewModel.gainVerificationCode(phoneNumber: phoneNumberTextField.text ?? "").subscribe(onNext: { (isSuccess) in
            
        }, onError: { (error) in
            HUDHelper.shared.showWithMsg(error.localizedDescription)
        })
        
    }
    
    @IBAction func userAgreementButtonTapped(_ sender: Any) {
        
    }
    
    @IBAction func registerButtonTapped(_ sender: Any) {
        
        let phone = phoneNumberTextField.text ?? ""
        let password = passwordTextField.text ?? ""
        let repeatPassword = psdRepeatTextField.text ?? ""
        let verificationCode = codeTextField.text ?? ""
        
        if phone.isEmpty {
            print("手机号码不能为空")
            return
        }
        
        if !Utils.phoneValidation(phone) {
            print("不是有效的手机号码")
            return
        }
        
        if verificationCode.isEmpty {
            print("请填写验证码")
            return
        }
        
        if password.isEmpty {
            print("密码不能为空")
            return
        }
        
        if !Utils.passwordValidation(password) {
            print("密码无效")
            return
        }
        
        if repeatPassword.isEmpty {
            print("请再次确认密码")
            return
        }
        
        if password != repeatPassword {
            print("两次密码输入不一致")
            return
        }
        
        ZWHud.shared.show()
        _ = viewModel.register(phoneNumber: phone, password: password.md5(), verificationCode: verificationCode).subscribe(onNext: { [weak self] (isSuccess) in
            
            ZWHud.shared.dismiss()
            
            let vc = RegisterSuccessfullyViewController.instanceFromStoryBoard(phoneNumber: phone, password: password)
            self?.navigationController?.pushViewController(vc, animated: true)
            
            }, onError: { (error) in
                
                ZWHud.shared.dismiss()
                HUDHelper.shared.showWithMsg(error.localizedDescription)
                
        })
        
    }
    
    deinit {
        invalidateTimer()
    }
    
}
