package it.mobihack.strappme;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

public class DriverAcceptingReceiver extends BroadcastReceiver {
    public DriverAcceptingReceiver() {
    }

    private static final String TAG = "DriverAcceptingReceiverReceiver";

    @Override
    public final void onReceive(Context context, Intent intent) {
        Log.v(TAG, "onReceive: " + intent.getAction());
        String className = getGCMIntentServiceClassName(context);
        Log.v(TAG, "GCM IntentService class: " + className);
        // Delegates to the application-specific intent service.
        parseIntent(context, intent, className);
        setResult(Activity.RESULT_OK, null /* data */, null /* extra */);
    }

    /**
     * Gets the class name of the intent service that will handle GCM messages.
     */
    protected String getGCMIntentServiceClassName(Context context) {
        String className = context.getPackageName() +
                DEFAULT_INTENT_SERVICE_CLASS_NAME;
        return className;
    }

    protected void parseIntent(Context context, Intent intent, String className) {
        Log.v("LOG", "PUSHHHHHHH: ");

        //myNotification(context, intent, className);

    }


    protected void myNotification(Context context, Intent intent, String className) {
        int notificationId = 001;
        // Build intent for notification content
        Intent viewIntent = new Intent(context, IncomingDriverActivity.class);
        viewIntent.putExtra("pippo", "pluto");
        PendingIntent viewPendingIntent =
                PendingIntent.getActivity(context, 0, viewIntent, 0);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("abbiamo un driver!")
                        .setContentText("C'è un nuovo driver per te!")
                        .setContentIntent(viewPendingIntent)
                        .setDefaults(Notification.DEFAULT_SOUND);

        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(context);

        // Build the notification and issues it with notification manager.
        notificationManager.notify(notificationId, notificationBuilder.build());

    }


    /**
     * Intent sent to GCM to register the application.
     */
    public static final String INTENT_TO_GCM_REGISTRATION =
            "com.google.android.c2dm.intent.REGISTER";

    /**
     * Intent sent to GCM to unregister the application.
     */
    public static final String INTENT_TO_GCM_UNREGISTRATION =
            "com.google.android.c2dm.intent.UNREGISTER";

    /**
     * Intent sent by GCM indicating with the result of a registration request.
     */
    public static final String INTENT_FROM_GCM_REGISTRATION_CALLBACK =
            "com.google.android.c2dm.intent.REGISTRATION";

    /**
     * Intent used by the GCM library to indicate that the registration call
     * should be retried.
     */
    public static final String INTENT_FROM_GCM_LIBRARY_RETRY =
            "com.google.android.gcm.intent.RETRY";

    /**
     * Intent sent by GCM containing a message.
     */
    public static final String INTENT_FROM_GCM_MESSAGE =
            "com.google.android.c2dm.intent.RECEIVE";

    /**
     * Extra used on {@link #INTENT_TO_GCM_REGISTRATION} to indicate the sender
     * account (a Google email) that owns the application.
     */
    public static final String EXTRA_SENDER = "sender";

    /**
     * Extra used on {@link #INTENT_TO_GCM_REGISTRATION} to get the application
     * id.
     */
    public static final String EXTRA_APPLICATION_PENDING_INTENT = "app";

    /**
     * Extra used on {@link #INTENT_FROM_GCM_REGISTRATION_CALLBACK} to indicate
     * that the application has been unregistered.
     */
    public static final String EXTRA_UNREGISTERED = "unregistered";

    /**
     * Extra used on {@link #INTENT_FROM_GCM_REGISTRATION_CALLBACK} to indicate
     * an error when the registration fails. See constants starting with ERROR_
     * for possible values.
     */
    public static final String EXTRA_ERROR = "error";

    /**
     * Extra used on {@link #INTENT_FROM_GCM_REGISTRATION_CALLBACK} to indicate
     * the registration id when the registration succeeds.
     */
    public static final String EXTRA_REGISTRATION_ID = "registration_id";

    /**
     * Type of message present in the {@link #INTENT_FROM_GCM_MESSAGE} intent.
     * This extra is only set for special messages sent from GCM, not for
     * messages originated from the application.
     */
    public static final String EXTRA_SPECIAL_MESSAGE = "message_type";

    /**
     * Special message indicating the server deleted the pending messages.
     */
    public static final String VALUE_DELETED_MESSAGES = "deleted_messages";

    /**
     * Number of messages deleted by the server because the device was idle.
     * Present only on messages of special type
     * {@link #VALUE_DELETED_MESSAGES}
     */
    public static final String EXTRA_TOTAL_DELETED = "total_deleted";

    /**
     * Permission necessary to receive GCM intents.
     */
    public static final String PERMISSION_GCM_INTENTS =
            "com.google.android.c2dm.permission.SEND";

    /**
     * @see
     */
    public static final String DEFAULT_INTENT_SERVICE_CLASS_NAME =
            ".GCMIntentService";

    /**
     * The device can't read the response, or there was a 500/503 from the
     * server that can be retried later. The application should use exponential
     * back off and retry.
     */
    public static final String ERROR_SERVICE_NOT_AVAILABLE =
            "SERVICE_NOT_AVAILABLE";

    /**
     * There is no Google account on the phone. The application should ask the
     * user to open the account manager and add a Google account.
     */
    public static final String ERROR_ACCOUNT_MISSING =
            "ACCOUNT_MISSING";

    /**
     * Bad password. The application should ask the user to enter his/her
     * password, and let user retry manually later. Fix on the device side.
     */
    public static final String ERROR_AUTHENTICATION_FAILED =
            "AUTHENTICATION_FAILED";

    /**
     * The request sent by the phone does not contain the expected parameters.
     * This phone doesn't currently support GCM.
     */
    public static final String ERROR_INVALID_PARAMETERS =
            "INVALID_PARAMETERS";
    /**
     * The sender account is not recognized. Fix on the device side.
     */
    public static final String ERROR_INVALID_SENDER =
            "INVALID_SENDER";

    /**
     * Incorrect phone registration with Google. This phone doesn't currently
     * support GCM.
     */
    public static final String ERROR_PHONE_REGISTRATION_ERROR =
            "PHONE_REGISTRATION_ERROR";

}
