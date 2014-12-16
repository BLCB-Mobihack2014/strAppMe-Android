package it.mobihack.strappme;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.provider.Settings.Secure;

import com.baasbox.android.BaasBox;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class LoginActivity extends FragmentActivity {

    private MainFragment mainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "it.mobihack.strappme",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }


        try {
            String android_id = Settings.Secure.getString( MyApplication.get().getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            Log.d("deviceId:" , android_id);

        } catch (Exception e) {

            Log.d("errror deviceId:", e.getMessage() + "" );
        }


        mainFragment = new MainFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(android.R.id.content, mainFragment)
                .commit();



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}

//public static void showHashKey(Context context) {
//try {
//  PackageInfo info = context.getPackageManager().getPackageInfo(
//          "it.mobihack.strappme", PackageManager.GET_SIGNATURES); //Your            package name here
//  for (Signature signature : info.signatures) {
//      MessageDigest md = MessageDigest.getInstance("SHA");
//      md.update(signature.toByteArray());
//      Log.i("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//      }
//} catch (NameNotFoundException e) {
//} catch (NoSuchAlgorithmException e) {
//}
//}

//Session fbSession = Session.getActiveSession();
//if(fbSession.isOpened()) {
//System.out.println("ok");
//}
