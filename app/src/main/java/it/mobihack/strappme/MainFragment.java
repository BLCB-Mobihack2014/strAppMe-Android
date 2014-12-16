package it.mobihack.strappme;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.baasbox.android.BaasBox;
import com.baasbox.android.BaasCloudMessagingService;
import com.baasbox.android.BaasHandler;
import com.baasbox.android.BaasResult;
import com.baasbox.android.BaasUser;
import com.baasbox.android.RequestToken;
import com.baasbox.android.json.JsonObject;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.LoginButton;


public class MainFragment extends Fragment {

    private static final String TAG = "MainFragment";
    private static final String siteUrl = "http://sbraam.tk/r/Api/";
    private UiLifecycleHelper uiHelper;
    private AsyncTask<Void, Void, Void> getFbResponse;
    private String userName;
    private Bitmap image;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uiHelper = new UiLifecycleHelper(getActivity(), callback);
        uiHelper.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);
        LoginButton authButton = (LoginButton) view.findViewById(R.id.myLoginButton);
        authButton.setFragment(this);
        final Button button = (Button) view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        myNotification();
                    }

                }, 10000); // 5000ms delay


            }
        });
        return view;
    }

    protected void myNotification() {
        int notificationId = 001;
        // Build intent for notification content
        Intent viewIntent = new Intent(getActivity(), ViewEventActivity.class);
        viewIntent.putExtra("pippo", "pluto");
        PendingIntent viewPendingIntent =
                PendingIntent.getActivity(getActivity(), 0, viewIntent, 0);


        NotificationCompat.Action action1 = new NotificationCompat.Action(R.drawable.ic_launcher, "azione1", viewPendingIntent);
        NotificationCompat.Action action2 = new NotificationCompat.Action(R.drawable.ic_launcher, "azione2", viewPendingIntent);


        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(getActivity())
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("my notification")
                        .setContentText("sbucciami")
                        .setContentIntent(viewPendingIntent)
                        .addAction(action1)
                        .addAction(action2)
                        .setDefaults(Notification.DEFAULT_SOUND);

        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(getActivity());

        // Build the notification and issues it with notification manager.
        notificationManager.notify(notificationId, notificationBuilder.build());

    }

    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
        //  BaasUser.current().getScope(BaasUser.Scope.PRIVATE);
        if (state.isOpened()) {
            String token = session.getAccessToken();// a valid token from the provider
            String secret = "f93fb81a36bb729e532696223b1b0562";
            BaasUser.signupWithProvider(BaasUser.Social.FACEBOOK, token, secret, new BaasHandler<BaasUser>() {
                @Override
                public void handle(BaasResult<BaasUser> res) {

                    Log.d("LOG", "handle");
                    if (res.isSuccess()) {
                        BaasUser current = res.value();
                        BaasCloudMessagingService msg = BaasBox.messagingService();
                        msg.enable(new BaasHandler<Void>() {
                            @Override
                            public void handle(BaasResult<Void> voidBaasResult) {

                            }
                        });
                       msg.newMessage().extra(new JsonObject().put("cio", 2))
                                .to("a4e399f2-f5d4-4ba8-8283-69eb0ad89553")
                                .text("my msg")
                                .send(new BaasHandler<Void>() {
                                    @Override
                                    public void handle(BaasResult<Void> voidBaasResult) {

                                    }
                                });


                        Log.d("LOG", "utente " + current.getName());

                        Intent intent = new Intent(getActivity(), ChooseActivity.class);
                        intent.putExtra("id", current.getName());
                        startActivity(intent);

                    } else {

                        Log.d("LOG", "utente non presente! " + res.error().getMessage());
                    }
                }

            });


        } else if (state.isClosed()) {
            Log.i(TAG, "Logged out...");
        }
    }

    /* private void onSessionStateChange(Session session, SessionState state, Exception exception) {
        if (state.isOpened()) {
            getFbResponse = new AsyncTask<Void, Void, Void>() {

                @Override
                protected Void doInBackground(Void... params) {
                    Log.d("LOG","backgroubd: ");

                    final Session mySession = Session.getActiveSession();


                    if (mySession != null && mySession.isOpened()) {

                        Log.d("LOG","ifff!!!: ");
                        // If the session is open, make an API call to get user data
                        // and define a new callback to handle the response
                        Request request = Request.newMeRequest(mySession, new Request.GraphUserCallback() {
                            @Override
                            public void onCompleted(final GraphUser user, Response response) {

                                Log.d("LOG","complete!!!: ");
                                // If the response is successful
                                if (mySession == Session.getActiveSession()) {
                                    if (user != null) {
                                        userName = user.getName();

                                        URL image_value = null;
                                        try {
                                            image_value = new URL("https://graph.facebook.com/"+user.getId()+"/picture" );
                                            image = BitmapFactory.decodeStream(image_value.openConnection().getInputStream());

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                        BaasUser.fetch("a",new BaasHandler<BaasUser>() {
                                            @Override
                                            public void handle(BaasResult<BaasUser> res) {
                                                boolean isNew;
                                                if(res.isSuccess()){
                                                    BaasUser u = res.value();
                                                    Log.d("LOG","The user: "+u);
                                                    isNew = false;
                                                } else {
                                                    Log.e("LOG","Error",res.error());
                                                    isNew = true;
                                                }
                                                signupWithBaasBox(isNew, user.getId(), "1234");
                                            }
                                        });


                                    }
                                }
                            }
                        });
                        Request.executeBatchAsync(request);
                    } else {

                        Log.d("LOG","else!!!: ");
                    }

                    return null;
                }

                @Override
                protected void onPostExecute(Void result) {
                    Intent intent = new Intent(getActivity(), ChooseActivity.class);
                    intent.putExtra("name", userName);
                    intent.putExtra("image", image);
                    startActivity(intent);
                    super.onPostExecute(result);
                }
            };
            getFbResponse.execute();
        } else if (state.isClosed()) {
            Log.i(TAG, "Logged out...");
        }
    }  */

    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };


    @Override
    public void onResume() {
        super.onResume();
        uiHelper.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }


    //baasbox
    //https://gist.githubusercontent.com/eliantor/9527155/raw/signup.java


    private RequestToken mSignupOrLogin;


    private void signupWithBaasBox(boolean newUser, String mUserName, String mPassword) {
        BaasUser user = BaasUser.withUserName(mUserName);
        user.setPassword(mPassword);
        if (newUser) {
            Log.d("LOG", "signupWithBaasBox: new");
            mSignupOrLogin = user.signup(onComplete);
        } else {
            Log.d("LOG", "signupWithBaasBox: not new");
            mSignupOrLogin = user.login(onComplete);
        }
    }


    private final BaasHandler<BaasUser> onComplete =
            new BaasHandler<BaasUser>() {
                @Override
                public void handle(BaasResult<BaasUser> result) {
                    mSignupOrLogin = null;
                    if (result.isFailed()) {
                        Log.d("ERROR", "ERROR", result.error());
                    }
                    // completeLogin(result.isSuccess());
                }
            };


}
