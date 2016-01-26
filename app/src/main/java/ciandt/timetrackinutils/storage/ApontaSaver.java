package ciandt.timetrackinutils.storage;

import android.app.Activity;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ciandt.timetrackinutils.R;
import ciandt.timetrackinutils.utils.dateutils;

/**
 * Created by paulocn on 14/01/16.
 */
public class ApontaSaver {

    static ArrayList<DateTime> mArrDates;

    private static String getArrayAsJSONString(){
        JSONArray jsonArray = new JSONArray();

        for (DateTime d : mArrDates){
                jsonArray.put(
                        dateutils.dateStringInUTC(d)
                );

        }
        JSONObject jobj = new JSONObject();
        try {
            jobj.put(Constants.kJsonApontamentos, jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;         //EARLY RETURN*******************************************************
        }

        return jobj.toString();
    }

    private static ArrayList<DateTime> JSONToArray(String str){

        ArrayList<DateTime> tempArr = new ArrayList<DateTime>();
        try {
            JSONObject jobj = new JSONObject(str);
            JSONArray jArray = jobj.getJSONArray(Constants.kJsonApontamentos);

            for (int i=0; i < jArray.length(); i++) {
                String date = jArray.getString(i);
                tempArr.add(
                        dateutils.dateInDevicesTimezone(
                                dateutils.getDateFromUTCString(date)
                        )
                );
            }

            return tempArr;         //EARLY RETURN*******************************************************

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void addAndSaveDate(DateTime date, Activity act){
        if (mArrDates == null){
            loadDates(act);
        }
        addNewDate(date);
        saveDates(act);
    }

    protected static void addNewDate(DateTime date){
        mArrDates.add(date);
        if (mArrDates.size() > 20){
            mArrDates.remove(0);
        }
    }

    protected static void saveDates(Activity act){
        String str = getArrayAsJSONString();
        GeneralSaver.save(act, Constants.kSAVEAPONTAMENTOS, str);
    }

    public static void loadDates(Activity act){
        String str = GeneralSaver.get(act, Constants.kSAVEAPONTAMENTOS);
        if (str.length() == 0){
            mArrDates = new ArrayList<>();
        }else{
            mArrDates = JSONToArray(str);
        }
    }

    public static ArrayList<String> getLastNApontamentosContextualized(int n, Activity act){
        ArrayList<String> arr = null;
        if (mArrDates == null){
            loadDates(act);
        }
        if (mArrDates != null && mArrDates.size() > 0){
            arr = new ArrayList<String>();
            int max = Math.min(n, mArrDates.size());
            for (int i=0; i < max; i++) {
                arr.add(
                        dateutils.dateToContextString(
                                mArrDates.get(i), act)
                );
            }
        }else{
            arr = new ArrayList<String>();
            arr.add(act.getString(R.string.nuncaapontou));
        }

        return arr;
    }

    public static ArrayList<DateTime> getLastApontamentos(Activity act){
        if (mArrDates == null){
            loadDates(act);
        }
        return mArrDates;
    }


}
