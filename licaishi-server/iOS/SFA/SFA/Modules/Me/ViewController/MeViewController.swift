//
//  MeViewController.swift
//  SFA
//
//  Created by 徐润肯 on 05/03/2018.
//  Copyright © 2018 runkenxu. All rights reserved.
//

import Foundation
import UIKit
import Kingfisher

class MeViewController: UITableViewController, InstanceFromStoryBoard, UIImagePickerControllerDelegate, UINavigationControllerDelegate {
    
    static var storyBoardName: String {
        return "Me"
    }
    
    @IBOutlet weak var avatar: UIImageView!
    @IBOutlet weak var nickNameLabel: UILabel!
    @IBOutlet weak var loginNameLabel: UILabel!
    @IBOutlet weak var phoneNumberLabel: UILabel!
    @IBOutlet weak var studioLabel: UILabel!
    
    let viewModel = MeViewModel()
    
    // 选择的照片
    var pickedImage : UIImage?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        ZWHud.shared.show()
        _ = viewModel.getAccouontDetail().subscribe(onNext: { [weak self] (isSuccessful) in
            
            ZWHud.shared.dismiss()
            
            if isSuccessful {
                self?.setupUI()
            }
            
        }, onError: { (error) in
            
            ZWHud.shared.dismiss()
            HUDHelper.shared.showWithMsg(error.localizedDescription)
            
        })
        
    }
    
    private func setupUI() {
        
        avatar.kf.setImage(with: URL(string: User.current.img ?? ""), placeholder: UIImage(named: "icon_avatar_default"), options: nil, progressBlock: nil, completionHandler: nil)
        nickNameLabel.text = User.current.nickname
        loginNameLabel.text = User.current.loginName
        studioLabel.text = User.current.studioName
        phoneNumberLabel.text = User.current.phoneNumber
        
    }
    
    @IBAction func logoutButtonTapped(_ sender: Any) {
        
        User.current.isLoggedIn = false
        User.current.clearUserInfo()
        
        navigationController?.popViewController(animated: true)
        
    }
    
    override func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        
        switch indexPath.row {
            
        // 头像
        case 0:
            return 63
            
        // 退出按钮
        case 7:
            return 80
            
        default:
            return 54
        }
        
    }
    
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        
        switch indexPath.row {
            
        case 0:  // 头像
            
            UIActionSheet.show(in: tableView, withTitle: nil, cancelButtonTitle: "取消", destructiveButtonTitle: nil, otherButtonTitles: ["拍摄","从手机相册选择"], tap: { [weak self] (actionSheet, buttonIndex) in
                if buttonIndex == 0 {
                    // 拍摄
                    self?.openCamera()
                }
                if buttonIndex == 1 {
                    // 相册
                    self?.openAlbum()
                }
            })
            
        case 1:  // 姓名
            
            let text = nickNameLabel.text == "未设置" ? "" : nickNameLabel.text
            let vc = EditViewController.instanceFromStoryBoard(editType: .nick, text)
            vc.callback = { [weak self] newNickName in
                self?.nickNameLabel.text = newNickName
            }
            
            navigationController?.pushViewController(vc, animated: true)
            
        case 4:  // 工作室名称
            
            let text = studioLabel.text == "未设置" ? "" : studioLabel.text
            let vc = EditViewController.instanceFromStoryBoard(editType: .studio, text)
            vc.callback = { [weak self] newNickName in
                self?.studioLabel.text = newNickName
            }
            
            navigationController?.pushViewController(vc, animated: true)
            
        case 5:  // 修改登录密码
            
            let vc = SetNewPasswordViewController.instanceFromStoryBoard()
            navigationController?.pushViewController(vc, animated: true)
            
        case 6:  // 关于理财师社区
            print("did select row 6")
            
        default:
            print("did select row \(indexPath.row)")
        }
        
        
    }
    
    // MARK: - 拍摄
    func openCamera() {
        
        if AVCaptureDevice.authorizationStatus(for: AVMediaType.video) == AVAuthorizationStatus.denied || AVCaptureDevice.authorizationStatus(for: AVMediaType.video) == AVAuthorizationStatus.restricted {
            UIAlertController.show("请在iPhone的\"设置-隐私-相机\"选项中, 允许PhiHome访问你的相机", self, "确定")
        }
        
        if !UIImagePickerController.isSourceTypeAvailable(UIImagePickerControllerSourceType.camera) {
//            HUDHelper.shared.showWithMsg("相机不可用")
            print("相机不可用")
            return
        }
        
        let picker = UIImagePickerController()
        picker.sourceType = UIImagePickerControllerSourceType.camera
        picker.delegate = self
        picker.allowsEditing = true
        self.present(picker, animated: true, completion: nil)
    }
    
    // MARK: - 相册
    func openAlbum() {
        let picker = UIImagePickerController()
        picker.sourceType = UIImagePickerControllerSourceType.photoLibrary
        picker.delegate = self
        picker.allowsEditing = true
        self.present(picker, animated: true, completion: nil)
    }
    
    // MARK: - UIImagePickerControllerDelegate
    func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [String : Any]) {
        
        picker.dismiss(animated: true, completion: nil)
        
        pickedImage = info[UIImagePickerControllerEditedImage] as? UIImage
        if let pickedImage = pickedImage {
            
            ZWHud.shared.show()
            _ = viewModel.uploadImage(image: pickedImage, type: "1").subscribe(onNext: { [weak self] (isSuccessful) in
                
                ZWHud.shared.dismiss()
                
                if isSuccessful {
                    self?.avatar.image = pickedImage
                }
                
                }, onError: { (error) in
                    
                    ZWHud.shared.dismiss()
                    HUDHelper.shared.showWithMsg(error.localizedDescription)
                    
                    
            })
        }
    }
    
}
