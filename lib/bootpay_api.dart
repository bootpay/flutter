import 'dart:async';
import 'dart:convert';
import 'dart:io';

import 'package:bootpay_api/model/bio_payload.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

import 'model/extra.dart';
import 'model/item.dart';
import 'model/payload.dart';
import 'model/user.dart';

typedef void VoidCallback();
typedef void StringCallback(String? value);

class BootpayApi {
  static const MethodChannel _channel = const MethodChannel('bootpay_api');

  static Future<String?> get platformVersion async {
    final String? version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static Future<void> request(BuildContext context, Payload payload,
      {User? user,
      List<Item>? items,
      Extra? extra,
      StringCallback? onDone,
      StringCallback? onReady,
      StringCallback? onCancel,
      StringCallback? onError}) async {

    payload.applicationId = Platform.isIOS
        ? payload.iosApplicationId
        : payload.androidApplicationId;

    if (user == null) user = User();
    if (items == null) items = [];
    if (extra == null) extra = Extra();

    Map<String, dynamic> params = {
      "payload": payload.toJson(),
      "params": payload.params ?? {},
      "user": user.toJson(),
      "items": items.map((v) => v.toJson()).toList(),
      "extra": extra.toJson()
    };

    try {
      Map<dynamic, dynamic> result = await _channel.invokeMethod(
        "bootpayRequest",
        params,
      );
      // Map<dynamic, dynamic> result = await (_channel.invokeMethod(
      //   "bootpayRequest",
      //   params,
      // ) as FutureOr<Map<dynamic, dynamic>>);


      String? method = result["method"];
      if (method == null) method = result["action"];

      String? message = result["message"];
      if (message == null) message = result["msg"];



      //confirm 생략
      if (method == 'onDone' || method == 'BootpayDone') {
        if (onDone != null) onDone(message);
      } else if (method == 'onReady' || method == 'BootpayReady') {
        if (onReady != null) onReady(message);
      } else if (method == 'onCancel' || method == 'BootpayCancel') {
        if (onCancel != null) onCancel(message);
      } else if (method == 'onError' || method == 'BootpayError') {
        if (onError != null) onError(message);
      } else if (result['receipt_id'] != null && result['receipt_id'].isNotEmpty) {
        if (onDone != null) onDone(jsonEncode(result));
      } else if (method == 'onConfirm' || method == 'BootpayConfirm') {
        if (onReady != null) onReady(message);
      }
    } on PlatformException catch (e) {
      print("PlatformException occured!! code: ${e.code}, msg: ${e.message}");
    }
  }

  static Future<void> removePaymentWindow() async {
    await (_channel.invokeMethod("removePaymentWindow"));
  }

  static Future<void> requestBio(BuildContext context, BioPayload payload,
      {User? user,
        List<Item>? items,
        Extra? extra,
        StringCallback? onConfirm,
        StringCallback? onDone,
        StringCallback? onReady,
        StringCallback? onCancel,
        StringCallback? onError}) async {

    payload.applicationId = Platform.isIOS
        ? payload.iosApplicationId
        : payload.androidApplicationId;

    if (user == null) user = User();
    if (items == null) items = [];
    if (extra == null) extra = Extra();

    Map<String, dynamic> params = {
      "payload": payload.toJson(),
      "params": payload.params ?? {},
      "user": user.toJson(),
      "items": items.map((v) => v.toJson()).toList(),
      "extra": extra.toJson()
    };

    Map<dynamic, dynamic> result = await (_channel.invokeMethod(
      "bootpayRequestBio",
      params,
    ));

    // Map<dynamic, dynamic> result = await (_channel.invokeMethod(
    //   "bootpayRequestBio",
    //   params,
    // ) as FutureOr<Map<dynamic, dynamic>>);

    String? method = result["method"];
    if (method == null) method = result["action"];

    String? message = result["message"];
    if (message == null) message = result["msg"];

    //confirm 생략
    if (method == 'onDone' || method == 'BootpayDone') {
      if (onDone != null) onDone(jsonEncode(result["data"]));
    } else if (method == 'onReady' || method == 'BootpayReady') {
      if (onReady != null) onReady(message);
    } else if (method == 'onCancel' || method == 'BootpayCancel') {
      if (onCancel != null) onCancel(message);
    } else if (method == 'onError' || method == 'BootpayError') {
      if (onError != null) onError(message);
    } else if (result['receipt_id'] != null && result['receipt_id'].isNotEmpty) {
      if (onDone != null) onDone(jsonEncode(result));
    } else if (method == 'onConfirm' || method == 'BootpayConfirm') {
      if (onConfirm != null) onConfirm(message);
    }
  }
}