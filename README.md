# timetrackinutils
Simple Android application to register time on CI&amp;T timetracking webapp

####Making an appointment:
```java
    //Example
    public void actionApontar(View view) {

        TTAsyncRequest req = new TTAsyncRequest(mActivity);
        req.execute("USERNAME",
                    "PASSWORD");

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
