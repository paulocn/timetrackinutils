package ciandt.timetrackinutils.storage;

import android.app.Activity;

/**
 * Created by paulocn on 05/10/15.
 */
public class MemoryStorageSingleton {
    private static MemoryStorageSingleton ourInstance = new MemoryStorageSingleton();

    public static MemoryStorageSingleton getInstance() {
        return ourInstance;
    }

    private String mPassword = "";

    private MemoryStorageSingleton() {

    }

    public String getUsername(Activity act){
        return EncriptedSaver.getDecripted(act, Constants.kUSERNAME);
    }

    public void setPassword(String pass){
        mPassword = pass;
    }

    public String getPassword(Activity act){
        if (getSaved(act)){
            return EncriptedSaver.getDecripted(act, Constants.kPASSWORD);
        }else{
            //here we should have a action trigger to ask for the user password
            return null;
        }
    }

    private boolean getSaved(Activity act){
        return EncriptedSaver.getDecripted(act, Constants.kPASSWORD) != "";
    }

}
