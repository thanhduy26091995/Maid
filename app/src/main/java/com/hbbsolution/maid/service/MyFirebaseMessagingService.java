package com.hbbsolution.maid.service;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.hbbsolution.maid.workmanager.listworkmanager.view.WorkManagerActivity;

import java.util.List;
import java.util.Map;

/**
 * Created by buivu on 28/10/2016.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";
    private NotificationUtils notificationUtils;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
//        // [START_EXCLUDE]
//        // There are two types of messages data messages and notification messages. Data messages are handled
//        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
//        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
//        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
//        // When the user taps on the notification they are returned to the app. Messages containing both notification
//        // and data payloads are treated as notification messages. The Firebase console always sends notification
//        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
//        // [END_EXCLUDE]
//
//        // TODO(developer): Handle FCM messages here.
//        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
//        Log.d(TAG, "From: " + remoteMessage.getFrom());
//
//        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            handleNotification(remoteMessage);
        }
//
//        // Check if message contains a notification payload.
//        if (remoteMessage.getNotification() != null) {
//            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
//        }
//
//        sendNotification(remoteMessage.getNotification().getBody());
//        // Also if you intend on generating your own notifications as a result of a received FCM
//        // message, here is where that should be initiated. See sendNotification method below.

//        Log.e(TAG, "From: " + remoteMessage.getFrom());
//
//        if (remoteMessage == null)
//            return;
//
//        // Check if message contains a notification payload.
//        if (remoteMessage.getNotification() != null) {
//            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
//            handleNotification(remoteMessage.getNotification());
//            //sendNotification(remoteMessage.getNotification().getBody());
//        }
    }


    /*
    status  = 6: gửi yêu cầu trực tiếp
     */
    private void handleNotification(RemoteMessage remoteMessage) {
        String status = "";
        status = remoteMessage.getData().get("status");
        if (NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            pushNotification(remoteMessage);
        } else {
            if (!isAtActivity("WorkManagerActivity")) {
                if (status.equals("6")) {
                    pushNotification(remoteMessage);
                }

            }
//            if (!isAtActivity("CommentActivity")) {
//                if (type.equals("comment")) {
//                    pushNotification(remoteMessage);
//                }
//
//            }
        }
    }

    private void pushNotification(RemoteMessage remoteMessage) {
        Map<String, String> data = remoteMessage.getData();
        PendingIntent pendingIntent = null;
        if (data.get("status").equals("6")) {
            Intent intent = new Intent(this, WorkManagerActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        }
//        if (data.get("type").equals("chat")) {
//            Intent intent = new Intent(this, ChatActivity.class);
//            User user = new User(data.get("avatar"), data.get("title"), data.get("uid"), data.get("deviceToken"));
//            intent.putExtra(Constants.USERS, user);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
//        }
//
//        if (data.get("type").equals("comment")) {
//            Intent intent = new Intent(this, CommentActivity.class);
//            Homestay homestay = new Homestay(data.get("homestayId"), Integer.parseInt(data.get("districtId")), Integer.parseInt(data.get("provinceId")));
//            intent.putExtra(Constants.HOMESTAY, homestay);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
//        }
//
//        if (data.get("type").equals("other")) {
//            Intent intent = new Intent(this, MainActivity.class);
//            pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
//        }


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setContentTitle(data.get("title"))
                .setContentText(data.get("body"))
                .setAutoCancel(true)
                .setLights(0xff00ff00, 300, 100)
                .setContentIntent(pendingIntent);

        builder.setSmallIcon(getNotificationIcon(builder));

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
        // play notification sound
        NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
        notificationUtils.playNotificationSound();
    }

    private int getNotificationIcon(NotificationCompat.Builder notificationBuilder) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int color = 0x008000;
            notificationBuilder.setColor(color);
            return com.hbbsolution.maid.R.mipmap.ic_launcher;

        } else {
            return com.hbbsolution.maid.R.mipmap.ic_launcher;
        }
    }

    private boolean isAtActivity(String activity) {
        boolean isAtActivity = false;
        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
        String current = taskInfo.get(0).topActivity.getShortClassName();
        boolean result = current.contains(activity);
        if (result) {
            isAtActivity = true;
        }
        return isAtActivity;
    }

}