public class Libs {
    static String kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib:$kotlin_ver";
    //presentation
    static String corektx = "androidx.core:core-ktx:" + Versions.core_version;
    static String appcompat = "androidx.appcompat:appcompat:1.3.1";
    static String legacySupport = "androidx.legacy:legacy-support-v4:1.0.0";
    static String material = "com.google.android.material:material:1.4.0";
    static String constraint = "androidx.constraintlayout:constraintlayout:2.1.1";
    static String recyclerView = "androidx.recyclerview:recyclerview:" + Versions.recyclerView_version;
    // For control over item selection of both touch and mouse driven selection
    static String recyclerViewSelection = "androidx.recyclerview:recyclerview-selection:" + Versions.recyclerViewSelection_version;

    static String viewPager2 = "androidx.viewpager2:viewpager2:" + Versions.viewPager2_version;

    //navigation
    static String navFragment = "androidx.navigation:navigation-fragment-ktx:" + Versions.navigation_version;
    static String navUi = "androidx.navigation:navigation-ui-ktx:" + Versions.navigation_version;
    //coroutines
    static String coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:" + Versions.coroutines_version;
    //ktx
    static String fragmentktx = "androidx.fragment:fragment-ktx:1.3.6";
    static String activityktx = "androidx.activity:activity-ktx:1.4.0";

    // androidx.lifecycle
    // ViewModel
    static String lifecycleViewModelktx = "androidx.lifecycle:lifecycle-viewmodel-ktx:" + Versions.ktx_version;
    // ViewModel utilities for Compose
    static String lifecycleViewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:" + Versions.ktx_version;
    // LiveData
    static String lifecycleLiveDataktx = "androidx.lifecycle:lifecycle-livedata-ktx:" + Versions.ktx_version;
    // Lifecycles only (without ViewModel or LiveData)
    static String lifecycleRuntimeOnlyktx = "androidx.lifecycle:lifecycle-runtime-ktx:" + Versions.ktx_version;
    // Saved state module for ViewModel
    static String lifecycleViewModelSavedState = "androidx.lifecycle:lifecycle-viewmodel-savedstate:" + Versions.ktx_version;
    // alternately - if using Java8, use the following instead of lifecycle-compiler
    static String lifecycleCommonJava8 = "androidx.lifecycle:lifecycle-common-java8:" + Versions.ktx_version;
    // optional - helpers for implementing LifecycleOwner in a Service
    static String lifecycleService = "androidx.lifecycle:lifecycle-service:" + Versions.ktx_version;
    // optional - ProcessLifecycleOwner provides a lifecycle for the whole application process
    static String lifecycleAppProcess = "androidx.lifecycle:lifecycle-process:" + Versions.ktx_version;
    // optional - ReactiveStreams support for LiveData
    static String lifecycleReactiveStreams = "androidx.lifecycle:lifecycle-reactivestreams-ktx:" + Versions.ktx_version;
    // optional - Test helpers for LiveData
    static String lifecycleTestHelpers = "androidx.arch.core:core-testing:$2.1.0";

    //test
    static String junit = "junit:junit:4.13.2";
    //reflect
    static String reflect = "org.jetbrains.kotlin:kotlin-reflect:1.5.21";
    //github.com/square/logcat
    static String logcat = "com.squareup.logcat:logcat:0.1";
    //splash
    static String splashScreen = "androidx.core:core-splashscreen:1.0.0-alpha02";
    //dagger
    static String dagger = "com.google.dagger:dagger:" + Versions.dagger_version;
    //hilt
    static String hilt = "com.google.dagger:hilt-android:" + Versions.dagger_version;
    static String hiltCompiler = "com.google.dagger:hilt-compiler:" + Versions.dagger_version;

    // androidx.room + androidx.room.migration + androidx.room.testing
    static String room = "androidx.room:room-runtime:" + Versions.room_version;
    static String roomKtx = "androidx.room:room-ktx:" + Versions.room_version;
    // optional - Test helpers
    // testImplementation "androidx.room:room-testing:$room_version"
    // optional - Paging 3 Integration
    static String roomPaging =  "androidx.room:room-paging:2.4.1";

    //kotlin serialization
    static String kotlinSerial = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.0";
    static String kotlinSerialConverter = "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0";
    //network
    static String okHttp = "com.squareup.okhttp3:okhttp:" + Versions.okhttp_version;
    static String retrofit = "com.squareup.retrofit2:retrofit:" + Versions.retrofit_version;
    static String logInterceptor = "com.squareup.okhttp3:logging-interceptor:" + Versions.okhttp_version;
    //coil
    static String coil = "io.coil-kt:coil:1.4.0";
    //WorkManager
    static String workManager = "androidx.work:work-runtime-ktx:2.7.0";
    static String hiltWork = "androidx.hilt:hilt-work:1.0.0";
    static String androidxHiltCompiler = "androidx.hilt:hilt-compiler:1.0.0";
    //Paging
    static String paging = "androidx.paging:paging-runtime-ktx:3.0.1";

    // Annotation processor

    // androidx.lifecycle
    static String lifecycleCompiler = "androidx.lifecycle:lifecycle-compiler:" + Versions.ktx_version;
    // androidx.room
    static String roomCompiler = "androidx.room:room-compiler:" + Versions.room_version;
    //dagger
    static String daggerCompiler = "com.google.dagger:dagger-compiler:" + Versions.dagger_version;
}