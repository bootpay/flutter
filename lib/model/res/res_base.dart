class ResBase {
  int? status;
  int? code;
  String? message;

  fromJson(Map<String, dynamic> json) {
    status = json["status"];
    code = json["code"];
    message = json["message"];
  }
}
