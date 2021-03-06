package course1778.mobileapp.safeMedicare.NotificationService;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.List;

import course1778.mobileapp.safeMedicare.Main.PatientActivity;

/**
 * Created by lang on 14/03/16.
 */
public class Snooze_Snooze_Act extends Activity {
    Context context = PatientActivity.getContext();
    //Context context = getActivity().getApplicationContext();
    private final String REMINDER_BUNDLE = "MyReminderBundle";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();


        cancelNotification(context, intent.getBundleExtra(REMINDER_BUNDLE).getInt("id"));
        Snooze.player.stop();

        Bundle bundle = intent.getBundleExtra(REMINDER_BUNDLE);

        Snooze snooze = new Snooze(context, bundle);

    }

    public static void cancelNotification(Context ctx, int notifyId) {
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager nMgr = (NotificationManager) ctx.getSystemService(ns);
        nMgr.cancel(notifyId);
    }


}