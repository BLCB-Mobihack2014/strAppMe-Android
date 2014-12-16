package it.mobihack.strappme;

import android.app.Application;
import android.content.Context;
import android.provider.Settings;
import android.util.Log;

import com.baasbox.android.BaasBox;

public class MyApplication extends Application {

    private static MyApplication sSelf;

    public BaasBox box;
    public void onCreate(){
        super.onCreate();
        //init baasbox
        //172.16.37.69
        String host = "172.16.37.69";
        //String host = "10.0.2.0";
        box =BaasBox.builder(this).setAuthentication(BaasBox.Config.AuthType.SESSION_TOKEN)
                .setApiDomain(host)
                .setPort(9000)
                .setPushSenderId("453055635932")
                .setAppCode("1234567890")
                .init();
        sSelf  = this;
    }

    public static MyApplication get(){
        return sSelf;
    }
}