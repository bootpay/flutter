package kr.co.bootpay.bootpay_api;


import androidx.annotation.NonNull;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import io.flutter.Log;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** BootpayApiPlugin */
public class BootpayApiPlugin implements MethodCallHandler, PluginRegistry.ActivityResultListener, FlutterPlugin, ActivityAware{
  private Activity activity;
  private Context context;
  private MethodChannel.Result methodChannelResult;

  final int BOOTPAY_REQUEST_CODE = 9876;


  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding binding) {
    this.context = binding.getApplicationContext();
    final MethodChannel channel = new MethodChannel(binding.getBinaryMessenger(), "bootpay_api");
    channel.setMethodCallHandler(this);
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {

  }

  @Override
  public void onAttachedToActivity(@NonNull ActivityPluginBinding binding) {
    this.activity = binding.getActivity();
    if(this.context == null) {
      this.context = this.activity.getApplicationContext();
    }
    binding.addActivityResultListener(this);

  }

  @Override
  public void onDetachedFromActivityForConfigChanges() {

  }

  @Override
  public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding binding) {

  }

  @Override
  public void onDetachedFromActivity() {

  }

//  public BootpayApiPlugin(Activity activity, Context context) {
//    this.activity = activity;
//    this.context = context;
//  }

  public static void registerWith(Registrar registrar) {

//    registrar
//            .platformViewRegistry()
//            .registerViewFactory(
//                    "kr.co.bootpay/webview",
//                    new WebViewFactory(registrar.messenger(), registrar.view()));
//    new FlutterCookieManager(registrar.messenger());

    final MethodChannel channel = new MethodChannel(registrar.messenger(), "bootpay_api");
    final BootpayApiPlugin instance = new BootpayApiPlugin();
    registrar.addActivityResultListener(instance);
    channel.setMethodCallHandler(instance);

  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    this.methodChannelResult = result;
    if (call.method.equals("getPlatformVersion")) {
      result.success("Android " + android.os.Build.VERSION.RELEASE);
    } else if(call.method.equals("bootpayRequest")) {
      goBootpayActivity(call.<Map<String, Object>>arguments());
    } else if(call.method.equals("bootpayRequestBio")) {
      goBootpayBioActivity(call.<Map<String, Object>>arguments());
    } else if(call.method.equals("removePaymentWindow")) {
      removePaymentWindow();
    } else {
      result.notImplemented();
    }
  }

  private void goBootpayActivity(Map<String, Object> params) {
    final Intent intent = new Intent(this.activity, BootpayActivity.class);
    if(params.get("payload") != null) intent.putExtra("payload", new Gson().toJson(params.get("payload")));
    if(params.get("params") != null) intent.putExtra("params", new Gson().toJson(params.get("params")));
    if(params.get("user") != null) intent.putExtra("user", new Gson().toJson(params.get("user")));
    if(params.get("items") != null) intent.putExtra("items", new Gson().toJson(params.get("items")));
    if(params.get("extra") != null) intent.putExtra("extra", new Gson().toJson(params.get("extra")));
    this.activity.startActivityForResult(intent, BOOTPAY_REQUEST_CODE);
  }

  private void goBootpayBioActivity(Map<String, Object> params) {
    final Intent intent = new Intent(this.activity, BootpayActivityBio.class);
    if(params.get("payload") != null) intent.putExtra("payload", new Gson().toJson(params.get("payload")));
    if(params.get("params") != null) intent.putExtra("params", new Gson().toJson(params.get("params")));
    if(params.get("user") != null) intent.putExtra("user", new Gson().toJson(params.get("user")));
    if(params.get("items") != null) intent.putExtra("items", new Gson().toJson(params.get("items")));
    if(params.get("extra") != null) intent.putExtra("extra", new Gson().toJson(params.get("extra")));
    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
    this.activity.startActivityForResult(intent, BOOTPAY_REQUEST_CODE);
  }

  private void removePaymentWindow() {
    this.activity.finishActivity(BOOTPAY_REQUEST_CODE);
  }

  @Override
  public boolean onActivityResult(int requestCode, int resultCode, Intent intent) {
    if (requestCode != BOOTPAY_REQUEST_CODE) {
      return false;
    }

    if(this.methodChannelResult == null) {
      return false;
    }

    if(intent == null) {
      this.methodChannelResult.error("error", "", null);
      return true;
    }

    try {
      HashMap<Object, Object> params = new HashMap<>();
      String method = "";
      String message = "";

      if(intent.hasExtra("method")) {
        method = intent.getStringExtra("method");
        if(method != null && method.length() > 0) params.put("method", method);
      }

      if(intent.hasExtra("message")) {
        message = intent.getStringExtra("message");
        if(message != null && message.length() > 0) params.put("message", message);
      }


      this.methodChannelResult.success(params);
    } catch (Exception e) {
      Log.d("bootpay error", e.getLocalizedMessage());
      this.methodChannelResult.error("error", e.getLocalizedMessage(), e);
    }
    return true;
  }
}
