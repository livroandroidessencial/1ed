package br.com.livroandroid.helloalarme;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * Classe utilitária para disparar notifications
 */
public class NotificationUtil {

    private static final String TAG = "livroandroid";

    static final String CHANNEL_ID = "1";

    public static void createChannel(Context context) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            String appName = context.getString(R.string.app_name);
            NotificationChannel c = new NotificationChannel(CHANNEL_ID, appName, NotificationManager.IMPORTANCE_DEFAULT);
            c.setLightColor(Color.BLUE);
            c.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            manager.createNotificationChannel(c);
        }
    }

    public static void create(Context context, int id, Intent intent, String title, String msg) {
        NotificationManager nm =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Intent para disparar o broadcast
        PendingIntent p = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Cria a notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentIntent(p)
                .setContentTitle(title)
                .setContentText(msg)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true);

        // Dispara a notification
        Notification n = builder.build();
        nm.notify(id, n);

        Log.d(TAG, "Notification criada com sucesso");
    }
}
