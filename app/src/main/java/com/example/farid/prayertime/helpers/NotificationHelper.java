package com.example.farid.prayertime.helpers;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.provider.Settings;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.example.farid.prayertime.R;

public class NotificationHelper extends ContextWrapper {
    private NotificationManager mNotificationManager;
    private Notification notification;
    private final String MY_CHANNEL = "my_channel";
    private final long[] vibrationScheme = new long[]{200, 400};

    /**
     * Registers notification channels, which can be used later by individual notifications.
     *
     * @param context The application context
     */
    public NotificationHelper(Context context) {
        super(context);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            // Create the channel object with the unique ID MY_CHANNEL
            NotificationChannel myChannel =
                    new NotificationChannel(
                            MY_CHANNEL,
                            "StAM",
                            NotificationManager.IMPORTANCE_DEFAULT);

            // Configure the channel's initial settings
            myChannel.setLightColor(Color.GREEN);
            myChannel.setVibrationPattern(vibrationScheme);

            // Submit the notification channel object to the notification manager
            getNotificationManager().createNotificationChannel(myChannel);

        }
    }

    /**
     * Build you notification with desired configurations
     *
     */
    public Notification getNotificationBuilder(String title, String body, PendingIntent intent) {

        Bitmap notificationLargeIconBitmap = BitmapFactory.decodeResource(
                getApplicationContext().getResources(),
                R.mipmap.ic_launcher);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), MY_CHANNEL)
                .setSmallIcon(R.drawable.stam_logo)
                .setLargeIcon(notificationLargeIconBitmap)
                .setContentTitle(title)
                .setContentText(body)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setVibrate(vibrationScheme)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                .setColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary))
                .setContentIntent(intent)
                .setOngoing(true)
                .setAutoCancel(true);


        return notificationBuilder.build();
    }

    public NotificationManager getNotificationManager() {
        if (mNotificationManager == null) {
            mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mNotificationManager;
    }

}
