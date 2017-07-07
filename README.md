# Capstone-Project
Coolest space explorer project for hobbyists, researchers and visionaries. The android application helps users to search for Collision Approach data, Near Earth Object, Earth Plychromatic Imaging Camera, NASA database, Astronomical Picture of the day, Earth Imagery and pictures taken by Mars Rover on Mars.

## Features

* Load CAD and NEO when app is loaded if network is available
* Share description of the content and the Android App
* Get a list of Mars Rover, Near Earth Object, Collision Approach Data, Search from NASA DB and EPIC
* Get Details of the item selected for general listing
* Landscape view and tablet view for general listing and detail
* Loading videos using Exoplayer for search and resuming not taking into consideration the orientation changes
* Widget for the APOD for the Android Application
* Notification Implementation using Firebase Cloud Messaging
* Analytics Implementation using Firebase Analytics
* Implementation of material design
* Espresso test
* JUnit Testing

## How to use the source code

1. Download or clone the repository onto your local machine.
2. Open the project using Android Studio
3. Register in NASA Api and Generate API Key
4. Include API_KEY for NASA Api in gradle.properties
5. Register the application in Google Console
6. Enable Google Maps for android and generate API key
7. Import project in Firebase based on the Google project created using Google Console
8. Enable Firebase Cloud Messaging and Firebase Analytics for the application.
9. Place the generated and downloaded .json file into the app directory
10. Rebuild the application
11. Run the application on Emulator or physical device using Android Studio

Note* Ensure your Google play services are at 11.0.2 version.

## Libraries

* [Glide](https://github.com/bumptech/glide)
* [Butterknife](http://jakewharton.github.io/butterknife/)
* [Rxjava 2](https://github.com/ReactiveX/RxJava/wiki/What's-different-in-2.0)
* [Retrofit 2](http://square.github.io/retrofit/)
* [Gson](http://square.github.io/retrofit/)
* [Retrofit2 Rxjava2 adapter](https://github.com/JakeWharton/retrofit2-rxjava2-adapter)
* [Logging interceptor](https://github.com/square/okhttp/tree/master/okhttp-logging-interceptor)
* [ExoPlayer](https://developer.android.com/guide/topics/media/exoplayer.html)
* [Schematic](https://github.com/SimonVT/schematic)

## Screenshots

### Mobile devices
![Splash Screen](https://github.com/dilipkumar4813/Capstone-Project/blob/master/screenshots/device-2017-07-05-123807.png)

![Initial Listing](https://github.com/dilipkumar4813/Capstone-Project/blob/master/screenshots/Screenshot_2017-07-06-23-45-07.png)

![Epic data](https://github.com/dilipkumar4813/Capstone-Project/blob/master/screenshots/device-2017-07-05-231911.png)

![General Listing](https://github.com/dilipkumar4813/Capstone-Project/blob/master/screenshots/Screenshot_2017-07-06-23-28-31.png)

![Listing Screen](https://github.com/dilipkumar4813/Capstone-Project/blob/master/screenshots/Screenshot_2017-07-06-23-28-15.png)

![Search Image](https://github.com/dilipkumar4813/Capstone-Project/blob/master/screenshots/Screenshot_2017-07-06-23-28-07.png)

![Search Video Streaming](https://github.com/dilipkumar4813/Capstone-Project/blob/master/screenshots/Screenshot_2017-07-06-23-28-15.png)

![APOD](https://github.com/dilipkumar4813/Capstone-Project/blob/master/screenshots/Screenshot_2017-07-07-11-57-38.png)

![About Us](https://github.com/dilipkumar4813/Capstone-Project/blob/master/screenshots/Screenshot_2017-07-06-23-45-22.png)

![Share](https://github.com/dilipkumar4813/Capstone-Project/blob/master/screenshots/Screenshot_2017-07-06-23-45-33.png)

![Full Screen](https://github.com/dilipkumar4813/Capstone-Project/blob/master/screenshots/Screenshot_2017-07-06-23-53-18.png)

![Maps](https://github.com/dilipkumar4813/Capstone-Project/blob/master/screenshots/Screenshot_2017-07-06-23-45-14.png)

![Widget](https://github.com/dilipkumar4813/Capstone-Project/blob/master/screenshots/Screenshot_2017-07-07-00-00-10.png)

### 7 Inch Tablets

![Screen one](https://github.com/dilipkumar4813/Capstone-Project/blob/master/screenshots/tablets/7inch/Screenshot_2017-07-07-11-47-45.png)

![Screen two](https://github.com/dilipkumar4813/Capstone-Project/blob/master/screenshots/tablets/7inch/Screenshot_2017-07-07-11-48-26.png)

![Screen three](https://github.com/dilipkumar4813/Capstone-Project/blob/master/screenshots/tablets/7inch/Screenshot_2017-07-07-11-48-40.png)

![Screen four](https://github.com/dilipkumar4813/Capstone-Project/blob/master/screenshots/tablets/7inch/Screenshot_2017-07-07-11-48-55.png)

![Screen five](https://github.com/dilipkumar4813/Capstone-Project/blob/master/screenshots/tablets/7inch/Screenshot_2017-07-07-11-49-10.png)

![Screen six](https://github.com/dilipkumar4813/Capstone-Project/blob/master/screenshots/tablets/7inch/Screenshot_2017-07-07-11-49-34.png)

### 10 Inch Tablets

![Screen one](https://github.com/dilipkumar4813/Capstone-Project/blob/master/screenshots/tablets/10inch/Screenshot_2017-07-07-11-47-59.png)

![Screen two](https://github.com/dilipkumar4813/Capstone-Project/blob/master/screenshots/tablets/10inch/Screenshot_2017-07-07-11-48-18.png)

![Screen three](https://github.com/dilipkumar4813/Capstone-Project/blob/master/screenshots/tablets/10inch/Screenshot_2017-07-07-11-49-21.png)

![Screen four](https://github.com/dilipkumar4813/Capstone-Project/blob/master/screenshots/tablets/10inch/Screenshot_2017-07-07-11-51-23.png)

![Screen five](https://github.com/dilipkumar4813/Capstone-Project/blob/master/screenshots/tablets/10inch/Screenshot_2017-07-07-11-51-54.png)

![Screen six](https://github.com/dilipkumar4813/Capstone-Project/blob/master/screenshots/tablets/10inch/Screenshot_2017-07-07-11-52-28.png)

## License

MIT License

Copyright (c) 2017 Dilip Kumar

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

