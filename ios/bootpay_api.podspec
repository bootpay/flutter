#
# To learn more about a Podspec see http://guides.cocoapods.org/syntax/podspec.html.
# Run `pod lib lint bootpay_api.podspec' to validate before publishing.
#
Pod::Spec.new do |s|
  s.name             = 'bootpay_api'
  s.version          = '3.0.1'
  s.summary          = 'Bootpay Flutter plugin.'
  s.description      = <<-DESC
Bootpay Flutter plugin.
                       DESC
  s.homepage         = 'http://example.com'
  s.license          = { :file => '../LICENSE' }
  s.author           = { 'Bootpay' => 'ehowlsla@bootpay.co.kr' }
  s.source           = { :path => '.' }
  s.source_files = 'Classes/**/*'
  s.dependency 'Flutter'
  s.dependency 'SwiftyBootpay', '3.4.3'

  s.static_framework = true
  # s.platform = :ios, '11.0'
  s.ios.deployment_target = '13.0'

  # Flutter.framework does not contain a i386 slice. Only x86_64 simulators are supported.
  s.pod_target_xcconfig = { 'DEFINES_MODULE' => 'YES', 'VALID_ARCHS[sdk=iphonesimulator*]' => 'x86_64' }
  s.swift_version = '5.0'
end
