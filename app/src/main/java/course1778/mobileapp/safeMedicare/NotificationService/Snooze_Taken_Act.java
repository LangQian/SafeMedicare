package course1778.mobileapp.safeMedicare.NotificationService;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import course1778.mobileapp.safeMedicare.Main.PatientActivity;

/**
 * Created by lang on 14/03/16.
 */
public class Snooze_Taken_Act extends Activity {
    Context context = PatientActivity.getContext();
    //Context context = getActivity().getApplicationContext();
    //Intent intent = getIntent();
    private final String REMINDER_BUNDLE = "MyReminderBundle";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //int id = intent.getBundleExtra(REMINDER_BUNDLE).getInt("");

        cancelNotification(context, getIntent().getBundleExtra(REMINDER_BUNDLE).getInt("id"));
        Snooze.player.stop();

        AlarmManager alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, Alarm_Msg.class);
        PendingIntent pendingIntent =
                PendingIntent.getBroadcast(context, getIntent().getBundleExtra(REMINDER_BUNDLE).getInt("id") + 1, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
        alarmMgr.cancel(pendingIntent);

    }

    public static void cancelNotification(Context ctx, int notifyId) {
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager nMgr = (NotificationManager) ctx.getSystemService(ns);
        nMgr.cancel(notifyId);
    }


}
