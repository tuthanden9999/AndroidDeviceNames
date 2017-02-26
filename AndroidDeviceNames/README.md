# Android Device Names

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.jaredrummler/android-device-names/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.jaredrummler/android-device-names) [![License](http://img.shields.io/:license-apache-blue.svg)](LICENSE.txt) [![API](https://img.shields.io/badge/API-7%2B-blue.svg?style=flat)](https://android-arsenal.com/api?level=7) <a href="http://www.methodscount.com/?lib=com.jaredrummler%3Aandroid-device-names%3A1.1.2" target="_blank"><img src="https://img.shields.io/badge/method count-86-e91e63.svg"></img></a> <a href="http://www.methodscount.com/?lib=com.jaredrummler%3Aandroid-device-names%3A1.1.2" target="_blank"><img src="https://img.shields.io/badge/size-34 KB-e91e63.svg"></img></a> [![Twitter Follow](https://img.shields.io/twitter/follow/jrummy16.svg?style=social)](https://twitter.com/jrummy16)

A small Android library to get the market name of an Android device.

On many popular devices the market name of the device is not available. For example, on the Samsung Galaxy S7 the value of [`Build.MODEL`](http://developer.android.com/reference/android/os/Build.html#MODEL) could be `"SAMSUNG-SM-G930A"`, `"SM-G930F"`, `"SM-G930K"`, `"SM-G930L"`, etc.

This small library gets the market (consumer friendly) name of a device.

Usage
-----

**Get the name of the current device:**

```java
String deviceName = DeviceName.getDeviceName();
```

The above code will get the correct device name for the top 600 Android devices. If the device is unrecognized, then [`Build.MODEL`](http://developer.android.com/reference/android/os/Build.html#MODEL) is returned. This can be executed from the UI thread.

**Get the name of a device using the device's codename:**

```java
// Retruns "Moto X Style"
DeviceName.getDeviceName("clark", "Unknown device");
```

**Get information about the device:**

```java
DeviceName.with(context).request(new DeviceName.Callback() {

  @Override public void onFinished(DeviceName.DeviceInfo info, Exception error) {
    String manufacturer = info.manufacturer;  // "Samsung"
    String name = info.marketName;            // "Galaxy S7 Edge"
    String model = info.model;                // "SAMSUNG-SM-G935A"
    String codename = info.codename;          // "hero2lte"
    String deviceName = info.getName();       // "Galaxy S7 Edge"
    // FYI: We are on the UI thread.
  }
});
 ```

The above code loads [JSON from a generated list](https://github.com/jaredrummler/AndroidDeviceNames/tree/master/json) of device names based on [Google's maintained list](https://support.google.com/googleplay/answer/1727131?hl=en). It will be up-to-date with Google's supported device list so that you will get the correct name for new or unknown devices. This supports *over 10,000* devices.

This will only make a network call once. The value is saved to SharedPreferences for future calls.

Download
--------

Download [the latest AAR](https://repo1.maven.org/maven2/com/jaredrummler/android-device-names/1.1.2/android-device-names-1.1.2.aar) or grab via Gradle:

```groovy
compile 'com.jaredrummler:android-device-names:1.1.2'
```

Or simply copy the [DeviceName](https://raw.githubusercontent.com/jaredrummler/AndroidDeviceNames/master/library/src/main/java/com/jaredrummler/android/device/DeviceName.java) class intro your project, update the package declaration, and you are good to go.

Apps using this library
-----------------------

[**Root Check**](http://rootcheck.jrummyapps.com/) <small>1,000,000 - 5,000,000 downloads</small>

<a href='https://play.google.com/store/apps/details?id=com.jrummyapps.rootchecker'><img alt='Get it on Google Play' src='https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png' width="186" height="72"/></a>

and [more](https://github.com/search?q=com.jaredrummler%3Aandroid-device-names+in%3Afile+extension%3Agradle&ref=searchresults&type=Code&utf8=%E2%9C%93)



License
--------

    Copyright (C) 2015. Jared Rummler

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
