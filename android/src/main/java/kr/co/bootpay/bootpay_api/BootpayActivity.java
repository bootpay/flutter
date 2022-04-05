package kr.co.bootpay.bootpay_api;

import io.flutter.embedding.android.FlutterActivity;
import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import kr.co.bootpay.Bootpay;
import kr.co.bootpay.enums.UX;
import kr.co.bootpay.listener.CancelListener;
import kr.co.bootpay.listener.CloseListener;
import kr.co.bootpay.listener.ConfirmListener;
import kr.co.bootpay.listener.DoneListener;
import kr.co.bootpay.listener.ErrorListener;
import kr.co.bootpay.listener.ReadyListener;
import kr.co.bootpay.model.BootExtra;
import kr.co.bootpay.model.BootUser;
import kr.co.bootpay.model.BootpayOneStore;
import kr.co.bootpay.model.Item;
import kr.co.bootpay.model.Payload;
import java.util.HashMap;

import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class BootpayActivity extends FlutterActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Bundle extras = this.getIntent().getExtras();
        if(extras == null) return;
        String payloadString = "";


        if(extras.containsKey("payload")) { payloadString = this.getIntent().getStringExtra("payload"); }

        String params = "";
        if(extras.containsKey("params")) { params = this.getIntent().getStringExtra("params"); }

        String userString = "";
        if(extras.containsKey("user")) { userString = this.getIntent().getStringExtra("user"); }

        String itemsString = "";
        if(extras.containsKey("items")) { itemsString = this.getIntent().getStringExtra("items"); }

        String extraString = "";
        if(extras.containsKey("extra")) { extraString = this.getIntent().getStringExtra("extra"); }

        BootUser bootUser = new BootUser();
        List<Item> items = new ArrayList<>();
        BootExtra bootExtra = new BootExtra();

        Payload payload = getPayload(payloadString, params);
        if(userString != null && !userString.isEmpty()) bootUser =  getBootUser(userString);
        if(itemsString != null && !itemsString.isEmpty()) items =  getItemList(itemsString);
        if(extraString != null && !extraString.isEmpty()) bootExtra =  getBootExtra(extraString);

        goBootpayRequest(payload, bootUser, bootExtra, items);
    }

    //원인을 알 수 없는 gson 버그로 인해 json parser로 수정
    Payload getPayload(String json, String params) {


        Payload payload = new Payload();
        try {
            JSONObject object = new JSONObject(json);

            String application_id = "";
            String pg = "";
            String method = "";
            JSONArray methods = new JSONArray();
            String name = "";
            Double price = 0.0;
            Double tax_free = 0.0;
            String order_id = "";
            Boolean use_order_id = false;

            String account_expire_at = "";
            Boolean show_agree_window = false;
            String easy_pay_user_token = "";

            if(!object.isNull("application_id")) { application_id = object.getString("application_id"); }
            if(!object.isNull("pg")) { pg = object.getString("pg"); }
            if(!object.isNull("method")) { method = object.getString("method"); }
            if(!object.isNull("methods")) { methods = object.getJSONArray("methods"); }
            if(!object.isNull("name")) { name = object.getString("name"); }

            if(!object.isNull("price")) { price = object.getDouble("price"); }
            if(!object.isNull("tax_free")) { tax_free = object.getDouble("tax_free"); }
            if(!object.isNull("order_id")) { order_id = object.getString("order_id"); }
            if(!object.isNull("use_order_id")) { use_order_id = object.getBoolean("use_order_id"); }

            if(!object.isNull("account_expire_at")) { account_expire_at = object.getString("account_expire_at"); }
            if(!object.isNull("show_agree_window")) { show_agree_window = object.getBoolean("show_agree_window"); }
            if(!object.isNull("user_token")) { easy_pay_user_token = object.getString("user_token"); }

            payload.setApplication_id(application_id);
            payload.setPg(pg);
            payload.setMethod(method);
            if(methods != null && methods.length() > 0) {
                List<String> methodList = new ArrayList<>();
                for(int i = 0; i < methods.length(); i++) {
                    methodList.add(methods.getString(i));
                }
                payload.setMethods(methodList);
            }
            payload.setName(name);
            payload.setPrice(price);
            payload.setTax_free(tax_free);
            payload.setOrder_id(order_id);
            payload.setUse_order_id(use_order_id == true ? 1 : 0);
            payload.setParams(params);
            payload.setAccount_expire_at(account_expire_at);
            payload.setShow_agree_window(show_agree_window);
//            if(isExist(boot_key)) payload.setBoot_key(boot_key);
//            if(isExist(ux)) payload.setUx(ux);
//            payload.setSms_use(sms_use);
            payload.setEasyPayUserToken(easy_pay_user_token);

        } catch (Exception e) {
            e.printStackTrace();

            payload = new Gson().fromJson(json, Payload.class);
            payload.setParams(params);
        }
        return payload;
    }

    //원인을 알 수 없는 gson 버그로 인해 json parser로 수정
    BootUser getBootUser(String json) {
        BootUser bootUser = new BootUser();
        try {
            JSONObject object = new JSONObject(json);

            String id = "";
            String username = "";
            String birth = "";
            String email = "";
            int gender = -1;
            String area = "";
            String phone = "";
            String addr = "";

            if(!object.isNull("id")) { id = object.getString("id"); }
            if(!object.isNull("username")) { username = object.getString("username"); }
            if(!object.isNull("birth")) { birth = object.getString("birth"); }
            if(!object.isNull("email")) { email = object.getString("email"); }
            if(!object.isNull("gender")) { gender = object.getInt("gender"); }
            if(!object.isNull("area")) { area = object.getString("area"); }
            if(!object.isNull("phone")) { phone = object.getString("phone"); }
            if(!object.isNull("addr")) { addr = object.getString("addr"); }

            bootUser.setID(id);
            bootUser.setUsername(username);
            bootUser.setBirth(birth);
            bootUser.setEmail(email);
            bootUser.setGender(gender);
            bootUser.setArea(area);
            bootUser.setPhone(phone);
            bootUser.setAddr(addr);
        } catch (JSONException e) {
            e.printStackTrace();
            bootUser = new Gson().fromJson(json, BootUser.class);
        }
        return bootUser;
    }

    List<Item> getItemList(String json) {
        List<Item> itemList = new ArrayList<>();

        try {
            JSONArray array = new JSONArray(json);
            for(int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);

                String item_name = "";
                int qty = 0;
                String unique = "";
                Double price = 0.0;
                String cat1 = "";
                String cat2 = "";
                String cat3 = "";

                if(!object.isNull("item_name")) { item_name = object.getString("item_name"); }
                if(!object.isNull("qty")) { qty = object.getInt("qty"); }
                if(!object.isNull("unique")) { unique = object.getString("unique"); }
                if(!object.isNull("price")) { price = object.getDouble("price"); }
                if(!object.isNull("cat1")) { cat1 = object.getString("cat1"); }
                if(!object.isNull("cat2")) { cat2 = object.getString("cat2"); }
                if(!object.isNull("cat3")) { cat3 = object.getString("cat3"); }

                Item item = new Item(item_name, qty, unique, price, cat1, cat2, cat3);
                itemList.add(item);
            }
        }   catch (JSONException e) {
            e.printStackTrace();
            itemList = new Gson().fromJson(json, new TypeToken<List<Item>>(){}.getType());
        }

        return itemList;
    }

    //원인을 알 수 없는 gson 버그로 인해 json parser로 수정
    BootExtra getBootExtra(String json) {
        BootExtra bootExtra = new BootExtra();
        BootpayOneStore oneStore = new BootpayOneStore();
        try {
            JSONObject object = new JSONObject(json);

            String start_at = "";
            String end_at = "";
            Integer expire_month = 0;
            boolean vbank_result = false;
            JSONArray quotas = new JSONArray();

            String app_scheme = "";
            String app_scheme_host = "";
            String disp_cash_result = "";
            int escrow = 0;

            if(!object.isNull("start_at")) { start_at = object.getString("start_at"); }
            if(!object.isNull("end_at")) { end_at = object.getString("end_at"); }
            if(!object.isNull("expire_month")) { expire_month = object.getInt("expire_month"); }
            if(!object.isNull("vbank_result")) { vbank_result = object.getBoolean("vbank_result"); }
            if(!object.isNull("quotas")) { quotas = object.getJSONArray("quotas"); }

            if(!object.isNull("app_scheme")) { app_scheme = object.getString("app_scheme"); }
            if(!object.isNull("app_scheme_host")) { app_scheme_host = object.getString("app_scheme_host"); }
            if(!object.isNull("disp_cash_result")) { disp_cash_result = object.getString("disp_cash_result"); }
            if(!object.isNull("escrow")) { escrow = object.getInt("escrow"); }

            if(!object.isNull("onestore")) {
                JSONObject jsonOneStore = object.getJSONObject("onestore");
                if(jsonOneStore != null) {
                    String ad_id = "";
                    String sim_operator = "";
                    String installer_package_name = "";

                    if(!object.isNull("ad_id")) { ad_id = object.getString("ad_id"); }
                    if(!object.isNull("sim_operator")) { sim_operator = object.getString("sim_operator"); }
                    if(!object.isNull("installer_package_name")) { installer_package_name = object.getString("installer_package_name"); }

                    oneStore.ad_id = ad_id;
                    oneStore.sim_operator = sim_operator;
                    oneStore.installer_package_name = installer_package_name;
                    bootExtra.setOnestore(oneStore);
                }
            }


            bootExtra.setStartAt(start_at);
            bootExtra.setEndAt(end_at);
            bootExtra.setExpireMonth(expire_month);
            bootExtra.setVbankResult(vbank_result);
            if(quotas != null && quotas.length() > 0) {
                List<Integer> quotaList = new ArrayList<>();
                for(int i = 0; i < quotas.length(); i++) {
                    quotaList.add(quotas.getInt(i));
                }
                bootExtra.setQuotas(toArray(quotaList));
            }
            bootExtra.setApp_scheme(app_scheme);
            bootExtra.setApp_scheme_host(app_scheme_host);
//            if(isExist(ux)) bootExtra.setUx(ux);
            bootExtra.setDisp_cash_result(disp_cash_result);
            bootExtra.setEscrow(escrow);
        } catch (JSONException e) {
            e.printStackTrace();
            bootExtra = new Gson().fromJson(json, BootExtra.class);
        }
        return bootExtra;
    }

    Boolean isExist(String value) {
        return !(value == null || value.isEmpty());
    }

    int[] toArray(List<Integer> list) {
        int[] ret = new int[ list.size() ];
        int i = 0;
        for(Iterator<Integer> it = list.iterator();
            it.hasNext();
            ret[i++] = it.next() );
        return ret;
    }

    void goBootpayRequest(Payload payload, BootUser user, BootExtra extra, List<Item> items) {

//        Log.d("payload", payload.getParams());

        Bootpay.init(this)
            .setContext(this)
            .setApplicationId(payload.getApplication_id()) // 해당 프로젝트(안드로이드)의 application id 값
            .setBootExtra(extra)
            .setPG(payload.getPg())
            .setMethod(payload.getMethod())
            .setMethods(payload.getMethods())
            .setBootUser(user)
            .setUX(UX.PG_DIALOG)
            .setParams(payload.getParams())
            //.isShowAgree(true)
            .setName(payload.getName()) // 결제할 상품명
            .setOrderId(payload.getOrder_id()) // 결제 고유번호
            .setIsShowAgree(payload.getShow_agree_window())
            .setTaxFree(payload.getTax_free())
            .setPrice(payload.getPrice()) // 결제할 금액
            .setAccountExpireAt(payload.getAccount_expire_at())
            .setEasyPayUserToken(payload.getEasyPayUserToken())
            .setItems(items)
            .onConfirm(new ConfirmListener() { // 결제가 진행되기 바로 직전 호출되는 함수로, 주로 재고처리 등의 로직이 수행
                @Override
                public void onConfirm(String message) {
                    Bootpay.confirm(message); // 재고가 있을 경우.
                }
            })
            .onDone(new DoneListener() { // 결제완료시 호출, 아이템 지급 등 데이터 동기화 로직을 수행합니다
                @Override
                public void onDone(String message) {
                    setFinishData("onDone", message);
                }
            })
            .onReady(new ReadyListener() { // 가상계좌 입금 계좌번호가 발급되면 호출되는 함수입니다.
                @Override
                public void onReady(String message) {
                    setFinishData("onDone", message);
                }
            })
            .onCancel(new CancelListener() { // 결제 취소시 호출
                @Override
                public void onCancel(String message) {
                    setFinishData("onCancel", message);
                }
            })
            .onError(new ErrorListener() { // 에러가 났을때 호출되는 부분
                @Override
                public void onError(String message) {
                    setFinishData("onError", message);
                }
            })
            .onClose(new CloseListener() { //결제창이 닫힐때 실행되는 부분
                @Override
                public void onClose(String message) {
                    finish();
//                    setFinishData("onClose", "");
                }
            })
            .request();
    }

    void setFinishData(String method, String message) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("method", method);
        resultIntent.putExtra("message", message);
        setResult(9876, resultIntent);

    }
}
