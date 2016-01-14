package ciandt.timetrackinutils;

import android.app.Application;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * Created by paulocn on 14/01/16.
 */
public class TTApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);
    }
}