package ciandt.timetrackinutils.utils;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import ciandt.timetrackinutils.MainActivity;
import ciandt.timetrackinutils.R;

/**
 * Created by paulocn on 02/12/15.
 */
public class notificacoes {

    public static void notifyOfAppointment(String message, Activity act){
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(act)
                        .setSmallIcon(R.drawable.ic_stat_tt)
                        .setContentTitle(act.getString(R.string.timetracking))
                        .setContentText(message);


        PendingIntent contentIntent = PendingIntent.getActivity(act, 0,
                new Intent(act, MainActivity.class), 0);

        mBuilder.setContentIntent(contentIntent);

        NotificationManager mNotificationManager =
                (NotificationManager) act.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify((int)System.currentTimeMillis(), mBuilder.build());

    }
}
