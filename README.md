# AnimatedDotsView
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.channguyen/adv/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.channguyen/adv)  
Simple dot animation to show progress

# Demo
![Main screen](/screenshots/sc.gif)

# Usage
Add a dependency to your `build.gradle`:
```
dependencies {
    compile 'com.github.channguyen.adv:1.0.0'
}
```
Add the `com.github.channguyen.adv.AnimatedDotsView` to your layout XML file.
```XML
<LinearLayout
  xmlns:android="http://schemas.android.com/apk/res/android"
  xmlns:custom="http://schemas.android.com/apk/res-auto"
  android:layout_width="match_parent"
  android:layout_height="match_parent"
  android:orientation="vertical"
  >

  <com.github.channguyen.adv.AnimatedDotsView
    android:id="@+id/adv_1"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    custom:adv___dotCount="3"
    custom:adv___dotBlinkingColor="#FF0000"
    android:layout_marginTop="40dp"
    android:layout_gravity="center_horizontal"
    />

  <com.github.channguyen.adv.AnimatedDotsView
    android:id="@+id/adv_2"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    custom:adv___dotCount="5"
    custom:adv___dotBlinkingColor="#FFFF00"
    android:layout_marginTop="40dp"
    android:layout_gravity="center_horizontal"
    />
</LinearLayout>
```

For more usage examples check the **sample** project.


# License
```
Copyright 2015 Chan Nguyen

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
