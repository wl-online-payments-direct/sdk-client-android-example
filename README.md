Ingenico Direct - Android Example App
=======================

The Ingenico Direct Android SDK provides a convenient way to support a large number of payment methods inside your Android app.
It supports Jelly Bean (Android version 4.1.x) and up out-of-the box.

This Example app illustrates the use of the SDK and the services provided by Ingenico ePayments on the Ingenico ePayments platform.

See the [Ingenico Direct Developer Hub](https:///support.direct.ingenico.com/documentation/sdk/mobile/android/) for more information on how to use the SDK.

Installation via Gradle
------------

Add a requirement to the SDK to your `build.gradle` file, where `x.y.z` is the version number:

    dependencies {
        // other dependencies
        implementation 'com.ingenico.direct:direct-sdk-client-android:x.y.z'
    }

Manual installation
------------

To install the example app, first download the code from GitHub.

```
$ git clone https://github.com/Ingenico/direct-sdk-client-android-example.git
```

Afterwards, you can open the project you just downloaded in Android Studio to execute the example app.

To use the Android SDK in your own app, you need to add the `ingenicodirect-sdk` dependency your project as follows:
*If you do not yet have the SDK .aar file, please refer to https://github.com/Ingenico/direct-sdk-client-android for instruction on how to build.*

1. Open your app in Android Studio.
2. Copy the .aar SDK file in the app/libs folder.
3. Go to app/build.gradle and find the SDK dependency in the `dependencies` node.
4. Make sure that the name of the SDK .aar in the `libs` folder matches the name of the dependency in the `build.gradle` file.
5. Add the `libs` folder as a `flatDir` repository.
6. Once the dependency is included, you can `Run` the `app` module on an emulator or physical device in order to build and install the app.