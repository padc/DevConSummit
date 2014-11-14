DevCon Summit 2014 Android App
============

DevCon Summit is the biggest annual developer conference in the Philippines featuring a collaboration of major developer groups in the country. For [DevCon Summit 2014](http://summit.devcon.ph/#agenda), the day long conference holds quiz bees for student participants, a keynote from one of our industry leaders, and lightning talks for mobile, web, and developer tools.

As one of DevCon Summit's partners, the Philippine Android Developers Community ([PADC](https://www.facebook.com/groups/339892436056451/)) has built an open source android app to not only showcase talks, speakers and program schedules but also to help attendees socialize with one another. The project also aims to be a community driven end-point for android development tools and best practices being used today.

Requirements
--------
Android Studio 0.9.2

Java 1.6+

Gradle 1.11+

Android Gradle Build Tool Plugin 0.14.1

Android Build Tools 21.0.2

Android SDK 4.4.2 (API 19)

Tools used for the project
--------
[Ormlite](http://ormlite.com/) by j256
    <p>Ormlite is an annotation based Java ORM for SQLite. It helps in table manipulation as well as doing basic CRUD functionality.</p>
[Retrofit](http://square.github.io/retrofit/) by Square
    <p>Retrofit turns the DevCon Summit REST API to Java interfaces. It helps in the seamless integratio of the app and its various api endpoints</p>
[Dagger](http://square.github.io/dagger/) by Square
    <p>Dagger is a fast and lightweight Dependency Injection for Java and optimized for android. It helps in making the app decoupled, clean, and easier to test</p>
[Android Priority Queue](https://github.com/path/android-priority-jobqueue) by Path
    <p>Android Priority Queue is an implementation of a Job Queue for scheduling tasks. Among other useful features, it helps in persisting network dependent tasks- only executing them once Internet connectivity is available (i.e. Edit Profile)</p>
[EventBus](https://github.com/greenrobot/EventBus) by Greenrobot
    <p>EventBus is an Android optimized event bus that simplifies communication between Activities, Fragments, background threads, Services, etc.. It helps in integrating with the Android Priority Queue library, making sure that commands are executed in the UI thread and comes with an in-memory cache that helps in making a faster app.</p>
[ButterKnife](http://jakewharton.github.io/butterknife/) by Jake Wharton
    <p>Butterknife is a View "injection" library for Android. It helps in mapping xml views to java objects nice and clean as well as making our ViewHolders and OnClickListeners more readable</p>
[Picasso](http://square.github.io/picasso/) by Square
    <p>Picasso is an image downloading and caching library for Android. It helps a lot in managing and setting bitmaps for our ImageViews and provides support for RenderScript commands that helps in making the app look aesthetically pleasant</p>

<h2>Copyright</h2>

    Copyright (C) 2014 Philippine Android Developers Community

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
