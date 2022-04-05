//
//  BootpayBioController.swift
//  Alamofire
//
//  Created by Taesup Yoon on 04/11/2020.
//


import Foundation
import UIKit
import SwiftyBootpay


class BootpayBioController: BootpayAuthController {

    var params: Dictionary<String, Any> = [:]
    var addView = false
    var flutterResult: FlutterResult?
 

//    func goBootpayRequest() {
//        let _payload: [String: Any] = params["payload"] as! [String : Any]
//        let payload = BootpayBioPayload(JSON: _payload) ?? BootpayBioPayload()
//        
//        var user = BootpayUser()
//        var extra = BootpayExtra()
//        var items = [BootpayItem()]
//        
//        if let _user = params["user"] { user = BootpayUser(JSON: _user as! [String : Any])! }
//        if let _extra = params["extra"] { extra = BootpayExtra(JSON: _extra as! [String : Any])! }
//        
//        if let _items = params["items"] as! [[String : Any]]? {
//            for obj in _items {
////                    items.append( BootpayItem(JSON: obj as! [String : Any])!)
//                items.append( BootpayItem(JSON: obj )!)
//            }
//        }
//        
//        Bootpay.requestBio(self, sendable: self, payload: payload, user: user, items: items, extra: extra, addView: addView)
//    }
}


//MARK: Bootpay Callback Protocol
extension BootpayBioController: BootpayRequestProtocol {
    // 에러가 났을때 호출되는 부분
    func onError(data: [String: Any]) {
        var dump = data
        dump["method"] = "onDone"
        self.flutterResult?(dump)
        Bootpay.dismiss()
    }

    // 가상계좌 입금 계좌번호가 발급되면 호출되는 함수입니다.
    func onReady(data: [String: Any]) {
        var dump = data
        dump["method"] = "onDone"
        self.flutterResult?(dump)
    }

    // 결제가 진행되기 바로 직전 호출되는 함수로, 주로 재고처리 등의 로직이 수행
    func onConfirm(data: [String: Any]) {
        var dump = data
        dump["method"] = "onConfirm"
//        self.flutterResult?(dump)
        Bootpay.transactionConfirm(data: data)
    }

    // 결제 취소시 호출
    func onCancel(data: [String: Any]) {
        var dump = data
        dump["method"] = "onCancel"
        self.flutterResult?(dump)
        Bootpay.dismiss()
    }

    // 결제완료시 호출
    // 아이템 지급 등 데이터 동기화 로직을 수행합니다
    func onDone(data: [String: Any]) {
        var dump = data
        dump["method"] = "onDone"
        self.flutterResult?(dump) 
        Bootpay.dismiss()
    }

    //결제창이 닫힐때 실행되는 부분
    func onClose() {
        var data = [String: Any]()
        data["method"] = "onClose"
        self.flutterResult?(data)
        self.presentingViewController?.dismiss(animated: true)
    }
}


