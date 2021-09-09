# onego-mobile
본 Repository는 **자필 원고지 인식 및 맞춤법 교정 시스템**인 **One Go!**의 모바일 앱을 위한 것입니다. 사용자는 간단한 구글 로그인 이후,  카메라와 갤러리를 통해 파일을 **원고지 사진을 추가**하고 **교정된 결과**를 얻을 수 있습니다.

<br>

## 주요 기능

- 사용자별 원고지 관리를 위한 구글 로그인
- 자필 원고지를 카메라 및 갤러리를 통하여 추가
- 교정된 원고지 내용 텍스트 파일로 다운로드
- 최근 교정한 파일 불러오는 최근 문서 기능

<br>

## 설치

Git 프로젝트 디렉토리 생성 및 원격 저장소 연결 혹은 git clone

```
$ git init
$ git remote add origin https://github.com/Onego2021/onego-mobile.git
$ git pull origin master

$ git clone https://github.com/Onego2021/onego-mobile.git
```

<br>

## 개발 환경

- Android Studio @4.1.2

<br>

## 어플리케이션 버전

- minsdkversion : 21 (API 21: Android 5.0(Lollipop))
- targetsdkversion : 30(API 30: Android 11.0(R))

<br>

## 스크린샷
![KakaoTalk_20210909_224450020_02](https://user-images.githubusercontent.com/28584299/132697829-f87a4de1-e47e-4fa5-9fab-e5a4d9c4654f.jpg)
![KakaoTalk_20210909_224708749](https://user-images.githubusercontent.com/28584299/132697840-f49d2583-a203-4bba-ab8e-3fed477a8e9b.jpg)
![KakaoTalk_20210909_224450020_04](https://user-images.githubusercontent.com/28584299/132697837-f3432b02-b3c2-492e-81c2-197f8b47cee2.jpg)
![KakaoTalk_20210909_224450020_03](https://user-images.githubusercontent.com/28584299/132697833-f88784a0-5fc0-46e2-8d02-0db39fe37351.jpg)


<br>

## 의존성

1. **build.gradle(:project)**

```
dependencies {
        classpath 'com.google.gms:google-services:4.3.10'
        classpath "com.android.tools.build:gradle:4.1.2"
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
    }
```

<br>

2. **build.gradle(:app)**

```
dependencies{
    implementation ("com.squareup.okhttp3:logging-interceptor:4.8.1")
    implementation 'com.firebaseui:firebase-ui-auth:7.2.0'
    implementation platform('com.google.firebase:firebase-bom:28.4.0')
    implementation 'com.google.firebase:firebase-analytics-ktx'
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.1'
    implementation 'com.squareup.retrofit2:retrofit:2.6.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.6.0'
    implementation 'com.squareup.retrofit2:retrofit-converters:2.6.0'
    implementation 'com.google.code.gson:gson:2.8.8'
    implementation 'com.android.support:recyclerview-v7:26.1.0'
    implementation 'com.theartofdev.edmodo:android-image-cropper:2.8.0'
    implementation 'io.github.ParkSangGwon:tedpermission:2.3.0'
    implementation 'com.squareup.picasso:picasso:2.71828'
    implementation 'com.synnapps:carouselview:0.1.4'
    implementation "org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version"
    implementation 'androidx.core:core-ktx:1.6.0'
    implementation 'androidx.appcompat:appcompat:1.3.1'
    implementation 'com.google.android.material:material:1.4.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.0.4'
    testImplementation 'junit:junit:4.+'
    androidTestImplementation 'androidx.test.ext:junit:1.1.3'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'
}
```

## 개발자

- **민대인(bamin0422)** : One Go! 모바일 앱 개발
[Contributors List](https://github.com/Onego2021/onego-mobile/graphs/contributors)

<br>

## 라이센스

- Apache 2.0 - [LICENSE](https://github.com/Onego2021/onego-mobile/blob/master/LICENSE)

