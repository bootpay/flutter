import Flutter
import UIKit
import SwiftyBootpay

public class SwiftBootpayApiPlugin: NSObject, FlutterPlugin, UIAdaptivePresentationControllerDelegate {
    
 public static func register(with registrar: FlutterPluginRegistrar) {
    let channel = FlutterMethodChannel(name: "bootpay_api", binaryMessenger: registrar.messenger())
   let instance = SwiftBootpayApiPlugin()
   registrar.addMethodCallDelegate(instance, channel: channel) 
 }

 public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
   if(call.method == "bootpayRequest") {
       goBootpayController(call.arguments as! Dictionary<String, Any>, result: result)
   } else if(call.method == "bootpayRequestBio") {
       goBootpayBioController(call.arguments as! Dictionary<String, Any>, result: result)
   } else if(call.method == "removePaymentWindow") {
//       goBootpayBioController(call.arguments as! Dictionary<String, Any>, result: result)
    
       removePaymentWindow()
   } else if(call.method == "getPlatformVersion") {
       result("i dont know")
   } else {
       result(FlutterMethodNotImplemented)
   } 
 }

 func goBootpayController(_ params: Dictionary<String, Any>, result: @escaping FlutterResult) {
 
   let rootViewController = UIApplication.shared.keyWindow?.rootViewController
//   let navigationController = UINavigationController()
    
   let vc = BootpayViewController()
   vc.params = params;
   vc.flutterResult = result
   vc.isModalButNoFullScreen = true
   
   vc.presentationController?.delegate = vc
   rootViewController?.present(vc, animated: true, completion: nil)
 }
    
 func removePaymentWindow() {
    let rootViewController = UIApplication.shared.keyWindow?.rootViewController
    rootViewController?.dismiss(animated: true, completion: nil)
 }
    
    
 func goBootpayBioController(_ params: Dictionary<String, Any>, result: @escaping FlutterResult) {
    let rootViewController = UIApplication.shared.keyWindow?.rootViewController
    rootViewController?.modalPresentationStyle = .overCurrentContext
    
    let _payload: [String: Any] = params["payload"] as! [String : Any]
    let payload = BootpayBioPayload(JSON: _payload) ?? BootpayBioPayload()
     
    var user = BootpayUser()
    var extra = BootpayExtra()
    var items = [BootpayItem()]
     
    if let _user = params["user"] { user = BootpayUser(JSON: _user as! [String : Any])! }
    if let _extra = params["extra"] { extra = BootpayExtra(JSON: _extra as! [String : Any])! }
     
    if let _items = params["items"] as! [[String : Any]]? {
        for obj in _items {
             items.append( BootpayItem(JSON: obj )!)
        }
    }
    
    let vc = BootpayBioController()
    vc.params = params;
    vc.flutterResult = result
    vc.presentationController?.delegate = vc
    
    Bootpay.requestBio3rd(rootViewController!, authController: vc, sendable: vc, payload: payload, user: user, items: items, extra: extra)
 }
    
 @objc func popViewController(animated: Bool) {
    print(animated)
 }



 public func applicationWillResignActive(_ application: UIApplication) {
     Bootpay.sharedInstance.sessionActive(active: false)
 }

 public func applicationDidBecomeActive(_ application: UIApplication) {
     Bootpay.sharedInstance.sessionActive(active: true)
 }



}

extension BootpayViewController: UIAdaptivePresentationControllerDelegate {
    func presentationControllerDidDismiss(_ presentationController: UIPresentationController) {
        let result = [
            "method": "onCancel",
            "message": "사용자에 의해 취소되었습니다."
        ]
        self.flutterResult?(result)
    }
}

extension BootpayBioController: UIAdaptivePresentationControllerDelegate {
    func presentationControllerDidDismiss(_ presentationController: UIPresentationController) {
        let result = [
            "method": "onCancel",
            "message": "사용자에 의해 취소되었습니다."
        ]
        self.flutterResult?(result)
    }
}

 
