## 3.1.2
* request로 비밀번호 간편결제 호출시 토큰 관련 버그 수정, android, ios version update 

## 3.1.1
* 동적 타입과 관련된 버그 수정 

## 3.1.0
* embedding v2 support by dion.kim

## 3.0.2
* invokeMethod로 결제창을 닫을 수 있도록 removePaymentWindow 메소드 추가       

## 3.0.1
* 타입캐스팅 버그 수정, 사이드이펙트로 onDone, close등 함수가 호출되지 않았음      

## 3.0.0
* null safety support, android & ios version update      

## 2.0.8
* ios 생체인증 결제시 onDone으로 데이터 리턴 안되는 현상 수정     

## 2.0.7
* android 특정 기기에서 invokedMethod 에러가 발생함. flutter버전 - 안드로이드에서 네이티브 콜이 지원이 안될거라고 예상중. try catch 를 추가함    

## 2.0.6
* ios 모달로 결제창 띄우게 수정하고, 사용자가 swipe로 모달 종료시 이벤트 수신할 수 있도록 처리함   

## 2.0.5
* 토스페이먼츠 추가  

## 2.0.4
* 가상계좌 결제완료시 결과창 안나오는 버그 수정 

## 2.0.3
* SwiftyBootpay 3.3.81로 버전 업데이트  

## 2.0.2
* 다날 본인인증시 안드로이드 버그 수정  

## 2.0.1
* npay 로그인 안드로이드 버그 개선   

## 2.0.0
* 생체인식 결제 추가  

## 1.2.21
* version update 

## 1.2.2
* application_id is not confired runtime error 해결됨 

## 1.2.11
* runtime exception 처리 

## 1.2.1
* bootpay 메인 sdk가 3.3.1버전으로 업데이트 되었습니다.
* runtime exception 처리 

## 1.2.0
* android params가 null 나오는 버그가 해결되었으며, 데이터 언마샬링시 gson으로 파싱하도록 처리되었습니다. 

## 1.1.14
* android, ios bootpay native library version update to 3.4.7  

## 1.1.13
* book_key jsonParsing에서 제외  


## 1.1.12
* application_id가 비어있는 버그는 gson 버그로 추측중, 해당 로직을 변경  

## 1.1.11
* rename을 롤백함, 클래스명 혼동에 의한 버그는 아닌것으로 추측함. platform.isandroid임을 인식못하는 현상이 있지 않을까 추측중   

## 1.1.1
* 출시 후 application_id가 비어있는 버그가 있다는 이슈처리를 위해, model class를 rename 함  

## 1.1.0
* application_id가 비어있을 경우에 대한 예외처리가 추가되었습니다. 

## 1.0.9
* User모델에 addr 필드를 추가하였습니다.

## 1.0.8
* onDone이 호출되지 않는 버그를 수정하였습니다.

## 1.0.7
* 아이폰용 닫기 버튼을 옵션으로 추가함. extra.iosCloseButton = false/true;

## 1.0.6
* update License 

## 1.0.5
* update pubspec.yaml 

## 1.0.4 
* reformat to flutter style

## 1.0.3
* readme 파일 수정 

## 1.0.2
* readme 파일 수정 

## 1.0.1
* readme 파일 수정 

## 1.0.0
* first commit.
