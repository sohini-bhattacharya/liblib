
<h1 align="center">Seventh Sense 📱</h1>

<h3 align="center"><span style="color:#FFC0CB">A library built to make smartphone-sensing frameworks simple and easy.</span></h3>

<h4 align="center">
Using this kotlin library, you can access the important and widely-used sensors available in your mobile devices with just a line of code. You can save and monitor the data for sensors like Proximity, Magnetometer, Accelerometer, etc. to more complex GPS sensors, Bluetooth Low Energy sensors, WiFi sensors, Android UUID and many more. Support classes like CSV data recorder and permission requirements are already taken into consideration by this library.
Along with that, there are case specific classes and functions, ie. to record and calculate the height of your current level of elevation or to predict your state of being in an indoor or outdoor environment setting. Anyone who is interested to record and analyze sensor data for ubiquitous computing, but isn't familiar with kotlin or wants to head straight into data collection, then this library is for you!
</h4>

<h4 align="center">____________________________________________________________________________</h4>

<dl>
  <dt><span style="color:#FFC0CB">How to import the library?</span></dt>
    <dd>In your build.gradle project file, (please replace 'latest_version' with the latest release on github)
</dd>
</dl>

``` kotlin
dependencies {
...
implementation 'com.github.sohini-bhattacharya:seventhsense:{latest_version}'
...
}
```


<dl>
  <dt><span style="color:#FFC0CB">An example use case:</span>
    <dd>This will use the default values for the class object, as mentioned in the original code.
</dd>
</dl>

```kotlin
import com.example.seventhsense.Wifi
import com.example.seventhsense.Permissions

class MainActivity : AppCompatActivity() {
    ...
    lateinit var permissionClass: Permissions
    lateinit var wifiClass: Wifi
    ...
    override fun onCreate(savedInstanceState: Bundle?) {
    
    ...
    
    permissionClass = Permissions(applicationContext,this)
    wifiClass = Wifi(this)
    
    # get the permissions needed
    permissionClass.getPermissions()
    
    # inside a button object
    # on-click
        wifiClass.getWifi()
    # on-stop
        wifiClass.stop()    
    
    # this will record and save the wifi-related data on the user's mobile device, 
    # and can also be monitored using the logcat tag "WIFI"
    
    # similar use-cases for other sensor classes,
    # please go through the code for function call details for each class
    # all details will be documented soon in an organised manner

    ...

    }
    
    }
```

<dl>
  <dt><span style="color:#FFC0CB">or,</span>
</dl>

```kotlin
import com.example.seventhsense.Location
import com.example.seventhsense.Permissions

class MainActivity : AppCompatActivity() {
    ...
    lateinit var permissionClass: Permissions
    lateinit var locationClass: Location
    ...
    override fun onCreate(savedInstanceState: Bundle?) {

    ...

    permissionClass = Permissions(applicationContext,this)
    locationClass = Location(applicationContext,this)
    
    # get the permissions needed
    permissionClass.getPermissions()
    
    # inside a button object
    # on-click
        locationClass.getCurrentLocation() 
    
    # this will continuously record and save the current location data of the user's mobile device, 
    # and can also be monitored using the logcat tag "LOCATION"
    
    #latitude, longitude, altitude, bearing, accuracy, speed, speedAccuracy

    ...

    }
    
    }
```

<dl>
  <dt><span style="color:#FFC0CB">or,</span>
</dl>

```kotlin
import com.example.seventhsense.Location
import com.example.seventhsense.Permissions

class MainActivity : AppCompatActivity() {
    ...
    lateinit var permissionClass: Permissions
    lateinit var predictClass: PredictIO
    ...
    override fun onCreate(savedInstanceState: Bundle?) {

    ...
    
    permissionClass = Permissions(applicationContext,this)
    predictClass = Predict(,this)
    
    # get the permissions needed
    permissionClass.getPermissions()
    
    # inside a button object
    # on-click
        predictClass.getPrediction() 
    
    # this will continuously record and save the current predicted indoor/outdoor class of the user's mobile device, 
    # this pretrained tflite model uses current system time, proximity, and light sensor data.
    # and can also be monitored using the logcat tag "PREDICT"
    
    # this pretrained model was trained on a limited data, 
    # the library will be updated soon with a better performing model.

    ...

    }
    
    }
```


<h4 align="center">____________________________________________________________________________</h4>


This library is being actively updated and new features are being added frequently. Any bugs detected will be fixed asap.

Feel free to share your feedback! I would really appreciate it! ❤️️

