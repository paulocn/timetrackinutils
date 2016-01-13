package ciandt.timetrackinutils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import org.json.JSONObject;

import ciandt.timetrackinutils.storage.MemoryStorageSingleton;
import ciandt.timetrackinutils.timetracking.TTAsyncRequest;
import ciandt.timetrackinutils.timetracking.TTCallbacks;
import ciandt.timetrackinutils.timetracking.TTRequester;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ApontaAutoActivity extends ActionBarActivity implements TTCallbacks {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;

    private View mContentView;
    private View mControlsView;
    private boolean mVisible;
    private ImageView mImageRelogio;
    private ImageView mImagePonteiros;
    private ImageView mImagePonteiros2;

    private Animation mAnimationRotate;
    private Animation mAnimationRotateFast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_aponta_auto);

        mImageRelogio =  (ImageView) findViewById(R.id.imageView2);
        mImagePonteiros =  (ImageView) findViewById(R.id.imageView3);
        mImagePonteiros2 =  (ImageView) findViewById(R.id.imageView4);


        mAnimationRotate = AnimationUtils.loadAnimation(this, R.anim.rotate_center);
        mAnimationRotateFast = AnimationUtils.loadAnimation(this, R.anim.rotate_center_fast);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionApontar(null);
        iniciaAnimacaoApontamento();
    }

    public void actionApontar(View view) {

        TTAsyncRequest req = new TTAsyncRequest(this);
        req.execute(MemoryStorageSingleton.getInstance().getUsername(this),
                MemoryStorageSingleton.getInstance().getPassword(this));

    }

    private void iniciaAnimacaoApontamento() {
        mImagePonteiros.startAnimation(mAnimationRotateFast);
        mImagePonteiros2.startAnimation(mAnimationRotate);
    }

    @Override
    public void requestFinished(JSONObject responseJSON) {
            mImagePonteiros.clearAnimation();
            mImagePonteiros2.clearAnimation();
            //Trocar strings hardcoded
            if (responseJSON != null) {

                String str = TTRequester.parseMessageFromTTJSON(responseJSON);
                String strs[] = str.split("\n");

                String msg = strs[0];
                if (strs.length > 1){
                    msg = strs[1];
                }
                notifyOfAppointment(msg);

            }else{
                notifyOfAppointment(getString(R.string.falhaApontamento));

            }
        finish();

        }

    public void notifyOfAppointment(String message){
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.relogio_tt)
                        .setContentTitle(getString(R.string.timetracking))
                        .setContentText(message);


        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);

        mBuilder.setContentIntent(contentIntent);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify((int)System.currentTimeMillis(), mBuilder.build());

    }


}
