package licttrainer.ttinnovations.batchschedule;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Abhishek.Sehgal on 17-03-2017.
 */

class AlarmReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        createNotification(context,"Time UP","5 Second has paused","Alert");

    }

    private void createNotification(Context context, String s, String s1, String alert) {
        PendingIntent pendingIntent= PendingIntent.getActivity(context,0,new Intent(context,MainActivity.class),0);
        NotificationCompat.Builder notificationCompat=new NotificationCompat.Builder(context);
        notificationCompat.setTicker(alert);
        notificationCompat.setContentText(s1);
        notificationCompat.setContentTitle("I am Broadcast Receiver");
        notificationCompat.setSmallIcon(R.drawable.alarm);

        notificationCompat.setContentIntent(pendingIntent);
        notificationCompat.setDefaults(NotificationCompat.DEFAULT_SOUND);
        notificationCompat.setAutoCancel(true);

        NotificationManager notificationManager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1,notificationCompat.build());

    }
}
