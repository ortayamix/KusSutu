package com.example.tefeul;

import static android.content.Context.ALARM_SERVICE;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.TaskStackBuilder;

import java.util.Random;

public class NotificationB extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        SharedPreferences prefs = context.getSharedPreferences(Activity.class.getSimpleName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();


        int notificationNumber = prefs.getInt("notificationNumber", 0);

        /*int id = intent.getIntExtra("bildirim", 0);
        int id_a = intent.getIntExtra("bildirim_1", 0);
        int id_b = intent.getIntExtra("bildirim_2", 1);*/

        String[] kus_sutleri_notify = context.getResources().getStringArray(R.array.kus_sutu);
        Random randomNumber = new Random();
        int selector = randomNumber.nextInt(kus_sutleri_notify.length);
        String mesaj = kus_sutleri_notify[selector];


        Intent intent2 = new Intent(context, Kus_Sutu_display.class);
        intent2.putExtra("bildirim_bool", true);
        intent2.putExtra("bildirim", selector);
        //intent2.putExtra("bildirim_"+notificationNumber, selector);
        intent2.setAction("kuş_sütleri_"+selector);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = TaskStackBuilder.create(context)
                .addNextIntentWithParentStack(intent2)
                //.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                .getPendingIntent(0, 0);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notifyLemubit")
                .setSmallIcon(R.drawable.logo)
                //.setContentTitle("Kuş Sütü")
                .setStyle(new NotificationCompat.BigTextStyle().bigText(mesaj + "\n"+(selector+1)))
                .setContentText(mesaj + "\n"+(selector+1))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT) //PRIORITY_DEFAULT, PRIORITY_HIGH
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);


        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);

        //notificationManagerCompat.notify(notificationNumber, builder.build());
        notificationManagerCompat.notify(selector, builder.build());

        //notificationNumber++;
        //editor.putInt("notificationNumber", notificationNumber).apply();


/*        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {

        }*/
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Kus_Sutu_display ks = new Kus_Sutu_display();
        //ks.alarm_prepare_set(alarmManager);

    }
}
