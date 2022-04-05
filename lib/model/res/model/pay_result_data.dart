class PayResultData {
  String? receiptId;
  double? price;
  String? cardNo;
  String? cardName;
  String? cardQuota;
  Map<String, dynamic>? params;
  String? itemName;
  String? orderId;
  String? url;
  String? paymentName;
  String? pgName;
  String? pg;
  String? method;
  String? methodName;
  String? requestedAt;
  String? purchasedAt;
  int? status;

  PayResultData.fromJson(Map<String, dynamic> json) {
    receiptId = json["receipt_id"];
    price = json["price"];
    cardNo = json["card_no"];
    cardName = json["card_name"];
    cardQuota = json["card_quota"];
    params = json["params"];

    itemName = json["item_name"];
    orderId = json["order_id"];
    url = json["url"];

    paymentName = json["payment_name"];
    pgName = json["pg_name"];
    pg = json["pg"];
    method = json["method"];

    methodName = json["method_name"];
    requestedAt = json["requested_at"];
    purchasedAt = json["purchased_at"];
    status = json["status"];
  }
}
