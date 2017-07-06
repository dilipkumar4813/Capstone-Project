package iamdilipkumar.com.spacedig.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import iamdilipkumar.com.spacedig.R;
import iamdilipkumar.com.spacedig.data.ApplicationPersistence;
import iamdilipkumar.com.spacedig.ui.activities.ApodDetailActivity;
import iamdilipkumar.com.spacedig.ui.activities.GeneralListActivity;
import iamdilipkumar.com.spacedig.ui.activities.SpaceListActivity;
import iamdilipkumar.com.spacedig.utils.CommonUtils;

/**
 * Created on 06/07/17.
 *
 * @author dilipkumar4813
 * @version 1.0
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String module = null, message = null;

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            JSONObject object = new JSONObject(remoteMessage.getData());
            try {
                module = object.getString("module");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            message = remoteMessage.getNotification().getBody();
        }

        if (message != null) {
            buildNotification(module, message, this);
        }
    }

    private void buildNotification(String module, String message, Context context) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.space_dig_main);
        mBuilder.setContentTitle(getString(R.string.app_name));
        mBuilder.setContentText(message);

        Intent resultIntent = new Intent(this, SpaceListActivity.class);

        if (module != null && CommonUtils.checkNetworkConnectivity(context)) {
            if (module.equalsIgnoreCase(getString(R.string.analytics_apod))) {
                resultIntent = new Intent(this, ApodDetailActivity.class);
            } else if (module.equalsIgnoreCase(getString(R.string.analytics_mars))) {
                resultIntent = new Intent(this, GeneralListActivity.class);
                resultIntent.putExtra(GeneralListActivity.LOAD_API, 0);
                ApplicationPersistence.storeSelectedItem(this, 0);
            }
        }

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(SpaceListActivity.class);

        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);

        // notificationID allows you to update the notification later on.
        mNotificationManager.notify(0, mBuilder.build());
    }
}
