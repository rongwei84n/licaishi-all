//
//  OrderDetailViewController.swift
//  SFA
//
//  Created by 徐润肯 on 23/03/2018.
//  Copyright © 2018 runkenxu. All rights reserved.
//

import UIKit
import Kingfisher

class OrderDetailViewController: UITableViewController, InstanceFromStoryBoard, UIImagePickerControllerDelegate, UINavigationControllerDelegate {

    static var storyBoardName: String {
        return "Order"
    }
    
    static func instanceFromStoryBoard(orderId: String?) -> OrderDetailViewController {
        
        let vc = instanceFromStoryBoard()
        vc.orderId = orderId
        
        return vc
    }
    
    @IBOutlet weak var orderNoLabel: UILabel!
    @IBOutlet weak var customerNameLabel: UILabel!
    @IBOutlet weak var orderStatusLabel: UILabel!
    @IBOutlet weak var amountLabel: UILabel!
    @IBOutlet weak var productNameLabel: UILabel!
    @IBOutlet weak var comRatioLabel: UILabel!
    @IBOutlet weak var commissionLabel: UILabel!
    @IBOutlet weak var proRatioLabel: UILabel!
    @IBOutlet weak var profitLabel: UILabel!
    @IBOutlet weak var contractStatusLabel: UILabel!
    @IBOutlet weak var voucherStatusLabel: UILabel!
    @IBOutlet weak var voucherImageView: UIImageView!
    
    let viewModel = OrderViewModel()
    
    var orderId: String?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        getOrderDetail()
        
    }
    
    @IBAction func uploadButtonTapped(_ sender: Any) {
        
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
        
    }
    
    private func getOrderDetail() {
        
        ZWHud.shared.show()
        
        _ = viewModel.getOrderDetail(orderId: orderId ?? "").subscribe(onNext: { [weak self] (orderDetail) in
            
            ZWHud.shared.dismiss()
            
            self?.loadDetailData(orderDetail)
            
        }, onError: { (error) in
            
            ZWHud.shared.dismiss()
            HUDHelper.shared.showWithMsg(error.localizedDescription)
            
        })
        
    }
    
    private func loadDetailData(_ orderDetail: OrderDetail) {
        
        orderNoLabel.text = orderDetail.orderNo
        customerNameLabel.text = orderDetail.customerName
        orderStatusLabel.text = orderDetail.status?.toString()
        amountLabel.text = String(format: "%.2f", orderDetail.amount ?? 0)
        productNameLabel.text = orderDetail.productShortName
        comRatioLabel.text = orderDetail.comRatio
        commissionLabel.text = String(format: "%.2f", orderDetail.commission ?? 0)
        proRatioLabel.text = orderDetail.proRatio
        profitLabel.text = String(format: "%.2f", orderDetail.profit ?? 0)
        contractStatusLabel.text = orderDetail.contractStatus?.toString()
        voucherStatusLabel.text = orderDetail.voucherStatus?.toString()
        
        if let urlString = orderDetail.voucherPath {
            voucherImageView.kf.setImage(with: URL(string: urlString))
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
        
        let pickedImage = info[UIImagePickerControllerEditedImage] as? UIImage
        if let pickedImage = pickedImage {
            
            ZWHud.shared.show()
            _ = viewModel.uploadImage(orderId: orderId ?? "", image: pickedImage).subscribe(onNext: { [weak self] (isSuccessful) in
                
                ZWHud.shared.dismiss()
                
                if isSuccessful {
                    self?.voucherImageView.image = pickedImage
                }
                
                }, onError: { (error) in
                    
                    ZWHud.shared.dismiss()
                    HUDHelper.shared.showWithMsg(error.localizedDescription)
                    
            })
        }
    }
    
}
