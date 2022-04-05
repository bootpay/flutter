import 'model/pay_result_data.dart';
import 'res_base.dart';

class ResPayResult extends ResBase {
  PayResultData? data;

  ResPayResult.fromJson(Map<String, dynamic> parsedJson) {
    data = PayResultData.fromJson(parsedJson['data']);
    super.fromJson(parsedJson);
  }
}
