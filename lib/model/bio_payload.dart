import 'dart:io';

import 'package:bootpay_api/model/payload.dart';
import 'package:bootpay_api/model/bio_price.dart';

class BioPayload extends Payload {
  List<String>? names;
  late List<BioPrice> prices;

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
    "names": this.names,
    "prices": this.prices.map((e) => e.toJson()).toList(),
  };
}
