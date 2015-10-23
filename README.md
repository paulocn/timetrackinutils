# timetrackinutils
Simple Android application to register time on CI&amp;T timetracking webapp

Dowload [APK](https://github.com/paulocn/timetrackinutils/blob/master/app/deploy/app-debug.apk?raw=true)

####Making an appointment:
```java
    //Example
    public void actionApontar(View view) {

        TTAsyncRequest req = new TTAsyncRequest(mActivity);
        req.execute("USERNAME",
                    "PASSWORD");

    }
```

And register the callback:
```java
    @Override
    public void requestFinished(JSONObject responseJSON) {

        if (responseJSON != null) {
            //this gets the message from the JSON
            String str = TTRequester.parseMessageFromTTJSON(responseJSON);
            //DO ACTION
    
        }else{
            //Something bad happened with the request, do something
        }

    }
```

####Adding a new fragment:
Add a new fragment on the layout and make sure its called here (the last two are currently placeholders):

*MainActivity.java*
```java
public void onNavigationDrawerItemSelected(int position)
```
And add as an option on: 
```java
public void onSectionAttached(int number)
```
