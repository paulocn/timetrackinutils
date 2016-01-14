package ciandt.timetrackinutils.storage;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by paulocn on 14/01/16.
 */
public class GeneralSaver {
    //TODO encrypt
    public static void save(Activity act, String key, String value){
        SharedPreferences sharedPref = act.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String get(Activity act, String key){
        SharedPreferences sharedPref = act.getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getString(key, "");
    }


    public static void delete(Activity act, String key) {
        SharedPreferences sharedPref = act.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove(key);
        editor.commit();

    }
}
