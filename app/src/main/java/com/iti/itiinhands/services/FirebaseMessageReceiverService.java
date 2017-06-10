package com.iti.itiinhands.services;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.iti.itiinhands.R;
import com.iti.itiinhands.activities.ChatRoomActivity;
import com.iti.itiinhands.activities.CompanySideMenu;
import com.iti.itiinhands.activities.GraduateSideMenu;
import com.iti.itiinhands.activities.GuestSideMenu;
import com.iti.itiinhands.activities.SideMenuActivity;
import com.iti.itiinhands.activities.StaffSideMenuActivity;
import com.iti.itiinhands.beans.Announcement;
import com.iti.itiinhands.database.DataBase;
import com.iti.itiinhands.dto.UserData;
import com.iti.itiinhands.utilities.Constants;
import com.iti.itiinhands.utilities.UserDataSerializer;

import java.util.Date;

import static com.iti.itiinhands.fragments.chat.ChatFragment.SP_NAME;


public class FirebaseMessageReceiverService extends FirebaseMessagingService {

    public static final String MESSAGE_RECEIVED_ACTION = "MESSAGE_RECEIVED";
    private SharedPreferences sharedPreferences;

    public FirebaseMessageReceiverService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getNotification() == null) {
            int notificationType = Integer.parseInt(remoteMessage.getData().get("notificationType"));
            String notificationTitle = remoteMessage.getData().get("notificationTitle");
            String notificationBody = remoteMessage.getData().get("notificationBody");


            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.calendar)
                            .setContentTitle(notificationTitle)
                            .setContentText(notificationBody);

            SharedPreferences data = getSharedPreferences(Constants.USER_SHARED_PREFERENCES, 0);
            int userType = data.getInt(Constants.USER_TYPE, 0);

            Intent sideMenu = null;

            switch (userType) {
                case 1:
                    //type 1 -> goes to Student side menu
                    sideMenu = new Intent(this, SideMenuActivity.class);
                    break;
                case 2:
                    //type 2 -> goes to Staff side menu
                    sideMenu = new Intent(this, StaffSideMenuActivity.class);
                    break;
                case 3:
                    //type 3 -> goes to Company side menu
                    sideMenu = new Intent(this, CompanySideMenu.class);
                    break;
                case 4:
                    //type 4 -> goes to Graduate side menu
                    sideMenu = new Intent(this, GraduateSideMenu.class);
                    break;
                default:
                    sideMenu = new Intent(this, GuestSideMenu.class);
                    break;
            }


            sideMenu.putExtra("notificationTitle", notificationTitle);
            sideMenu.putExtra("notificationBody", notificationBody);
            sideMenu.putExtra("notificationType", notificationType);
            sideMenu.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            sideMenu.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            //getting user name
            SharedPreferences setting = getApplicationContext().getSharedPreferences(Constants.USER_SHARED_PREFERENCES, 0);
            UserData userData = UserDataSerializer.deSerialize(setting.getString(Constants.USER_OBJECT,""));
            int type = setting.getInt(Constants.USER_TYPE,0 );
            String userName = "";

            switch (type){
                case 0:
                    //guest
                    userName = "guest";
                    break;
                case 1:
                    //Student
                    userName = userData.getName();
                    break;
                case 2:
                    //Staff
                    userName = userData.getEmployeeName();
                    break;
                case 3:
                    //Company
                    userName = userData.getCompanyUserName();
                    break;
                case 4:
                    //Graduate
                    userName = "graduate";
                    break;

            }

            //inserting announcement into sqlite
            Announcement announcement = new Announcement();
            announcement.setDate(new Date().getTime());
            announcement.setBody(notificationBody);
            announcement.setType(notificationType);
            announcement.setTitle(notificationTitle);
            announcement.setUserName(userName);
            DataBase DB = DataBase.getInstance(getApplicationContext());
            DB.insertAnnouncement(announcement);

            PendingIntent resultPendingIntent =
                    PendingIntent.getActivity(
                            this,
                            0,
                            sideMenu,
                            PendingIntent.FLAG_CANCEL_CURRENT
                    );

            mBuilder.setContentIntent(resultPendingIntent);
            mBuilder.setAutoCancel(true);

// Sets an ID for the notification
            int mNotificationId = 001;
// Gets an instance of the NotificationManager service
            NotificationManager mNotifyMgr =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
// Builds the notification and issues it.
            mNotifyMgr.notify(mNotificationId, mBuilder.build());




        } else {


            sharedPreferences = getApplicationContext().getSharedPreferences(SP_NAME, MODE_PRIVATE);
            String receiverId = remoteMessage.getData().get("receiverId");

            String friendListActivityActive = sharedPreferences.getString("friendListActivityActive", null);
            if (friendListActivityActive != null) {
                if (friendListActivityActive.equals("true")) {
                    Intent intent = new Intent(MESSAGE_RECEIVED_ACTION);
                    LocalBroadcastManager broadcaster = LocalBroadcastManager.getInstance(this);
                    if (receiverId != null) {
                        intent.putExtra("receiverId", receiverId);
                        broadcaster.sendBroadcast(intent);
                    }
                } else {

                    String message = remoteMessage.getData().get("body");
                    String receiverName = remoteMessage.getData().get("receiverName");
                    String senderId = remoteMessage.getData().get("senderId");
                    String roomKey = remoteMessage.getData().get("roomKey");

                    String chatRoomActive = sharedPreferences.getString("chatRoomActive", null);

                    if (chatRoomActive == null) {
                        makeNotification(roomKey, senderId, receiverId, receiverName, message);
                    } else if (!chatRoomActive.equals(receiverId)) {
                        makeNotification(roomKey, senderId, receiverId, receiverName, message);
                    }

                }
            }
        }

    }

    private void makeNotification(String roomKey, String senderId, String receiverId, String receiverName, String message) {

        //make notification
        Intent intent = new Intent(this, ChatRoomActivity.class);
        intent.putExtra("roomKey", roomKey);
        intent.putExtra("senderId", senderId);
        intent.putExtra("receiverId", receiverId);
        intent.putExtra("receiverName", receiverName);

        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle(receiverName);
        builder.setContentText(message);
        builder.setAutoCancel(true);


        //get integer equivalent
        int id = 7;
        for (int i = 0; i < receiverId.length(); i++) {
            id = id * 31 + receiverId.charAt(i);
        }

        builder.setSmallIcon(android.R.mipmap.sym_def_app_icon);
        PendingIntent pendingIntent = PendingIntent.
                getActivity(this, id, intent, PendingIntent.FLAG_ONE_SHOT);
        builder.setContentIntent(pendingIntent);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        builder.setDefaults(Notification.DEFAULT_SOUND);
        manager.notify(id, builder.build());
    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }
}
