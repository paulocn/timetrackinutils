package ciandt.timetrackinutils.widgets;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import ciandt.timetrackinutils.R;

/**
 * Created by paulocn on 20/10/15.
 */
public class Widget extends AppWidgetProvider {

    public void onReceive(Context context, Intent intent)
    {
        String act=intent.getAction();
        if(AppWidgetManager.ACTION_APPWIDGET_UPDATE.equals(act))
        {
            RemoteViews myView =new RemoteViews(context.getPackageName(), R.layout.widget);

            AppWidgetManager.getInstance(context).updateAppWidget(intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS), myView);
        }
    }

}