package ciandt.timetrackinutils.utils;

import android.app.Activity;

import net.danlew.android.joda.DateUtils;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Duration;

/**
 * Created by paulocn on 14/01/16.
 */
public class dateutils {

    public static DateTime getUTCStringDate(DateTime date){
        DateTime utc = date.withZone(DateTimeZone.UTC);
        return utc;
    }

    public static DateTime getDateFromUTCString(String dateString){
        DateTime dateTimeUtc = new DateTime( dateString, DateTimeZone.UTC );
        return dateTimeUtc;
    }

    public static String dateStringInUTC(DateTime date){
        return getUTCStringDate(date).toString();
    }

    public static DateTime dateInDevicesTimezone(DateTime date){
        DateTime dt = new DateTime(date, DateTimeZone.getDefault());
        return dt;
    }

    public static String dateStringInDevicesTimezone(DateTime date){
        return dateInDevicesTimezone(date).toString();
    }

    public static String dateToContextString(DateTime date, Activity act){
        return DateUtils.getRelativeTimeSpanString(act, date).toString();
    }

    public static String hoursDifference(DateTime date1, DateTime date2){
        Duration duration = new Duration(date1, date2);

        String hour = String.format("%02d", duration.getStandardHours()*-1);
        String minute = String.format("%02d",(duration.getStandardMinutes()*-1)%60);
        String second = String.format("%02d",(duration.getStandardSeconds()*-1)%60);

        return hour + ":" + minute+ ":" + second;
    }

}
