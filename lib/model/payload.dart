import 'dart:io';

class Payload {
  String? androidApplicationId = '';
  String? iosApplicationId = '';

  String? applicationId = '';

//  String rest_application_id; //해당 기능은 이해를 돕기위해 제공할 뿐, flutter에서 절대로 사용해서는 안됩니다

  String? pg = '';
  String? method = '';
  List<String>? methods;
  String? name = '';

  double? price = 0;
  double? taxFree = 0;

  String? orderId = '';
  bool? useOrderId = false;

  Map<String, dynamic>? params;

  String? accountExpireAt = '';
  bool? showAgreeWindow = false;

  String? bootKey = '';
  String? ux = 'PG_DIALOG';
  int? smsUse = 0;
  String? userToken = '';

  Payload() {
    this.methods = [];
    this.params = new Map();
  }

  Payload.fromJson(Map<String, dynamic> json) {
    androidApplicationId = json["android_application_id"];
    iosApplicationId = json["ios_application_id"];

    pg = json["pg"];
    method = json["method"];
    methods = json["methods"];
    name = json["name"];

    price = json["price"];
    taxFree = json["tax_free"];

    orderId = json["order_id"];
    useOrderId = json["use_order_id"];

    params = json["params"];

    accountExpireAt = json["account_expire_at"];
    showAgreeWindow = json["show_agree_window"];

    bootKey = json["boot_key"];
    ux = json["ux"];
    smsUse = json["sms_use"];
    userToken = json["user_token"];
  }

  getApplicationId() {
    if(this.applicationId != null && this.applicationId!.isNotEmpty) return this.applicationId;
    if(Platform.isIOS) return this.iosApplicationId;
    else return this.androidApplicationId;
  }

  Map<String, dynamic> toJson() => {
        "application_id": getApplicationId(),
        "pg": this.pg,
        "method": this.method,
        "methods": this.methods,
        "name": this.name,
        "price": this.price,
        "tax_free": this.taxFree,
        "order_id": this.orderId,
        "use_order_id": this.useOrderId,
        "params": Platform.isAndroid == true ? "" : this.params,
        "account_expire_at": this.accountExpireAt,
        "show_agree_window": this.showAgreeWindow,
        "ux": this.ux,
        "user_token": this.userToken,
      };

//  params: ${getParamsString()}, account_expire_at: '${account_expire_at}', show_agree_window: $show_agree_window, ux: '${ux}', sms_use: ${sms_use}, user_token: '${user_token}'";

  String toString() {
    return "{application_id: '${getApplicationId()}', pg: '$pg', method: '$method', name: '$name', price: $price, tax_free: $taxFree, order_id: '$orderId', use_order_id: $useOrderId, params: ${getParamsString()}, account_expire_at: '$accountExpireAt', show_agree_window: $showAgreeWindow, ux: '$ux', sms_use: $smsUse, user_token: '$userToken'";
  }

  String getParamsString() {
    if (params == null) return "{}";
    return reVal(params.toString());
  }

  String reVal(dynamic value) {
    if (value is String) {
      if (value.isEmpty) {
        return '';
      }
      return value.replaceAll("\"", "'").replaceAll("'", "\\'");
    } else {
      return value;
    }
  }

  String getMethods() {
    if (methods == null || methods!.isEmpty) return '';
    String result = '';
    for (String method in methods!) {
      if (result.length > 0) result += ',';
      result += method;
    }
    return result;
  }
}
