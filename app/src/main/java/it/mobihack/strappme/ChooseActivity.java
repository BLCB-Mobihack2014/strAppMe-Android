package it.mobihack.strappme;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baasbox.android.BaasUser;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ChooseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose);
        ImageView offro = (ImageView)findViewById(R.id.offro);
        offro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseActivity.this, OffroActivity.class);
                startActivity(intent);
            }
        });
        ImageView cerco = (ImageView)findViewById(R.id.cerco);
        cerco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseActivity.this, CercoActivity.class);
                startActivity(intent);
            }
        });


      /*  TextView txNome = (TextView)findViewById(R.id.txNome);
        txNome.setText( BaasUser.current().getScope(BaasUser.Scope.PRIVATE).getString("name"));
        */

		/*Bitmap image = getIntent().getParcelableExtra("image");
		ImageView profileImage = (ImageView)findViewById(R.id.profile_image);
		profileImage.setImageBitmap(image);*/

        //facebook image
   /*     URL url;
        URL secondURL = null;
        try {
            String fbID = BaasUser.current().getScope(BaasUser.Scope.PRIVATE).getObject("_social").getObject("facebook").getString("id");
            url = new URL("http://graph.facebook.com/" + fbID + "/picture?type=square");
            HttpURLConnection ucon = null;
            try {
                ucon = (HttpURLConnection) url.openConnection();
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            ucon.setInstanceFollowRedirects(false);
            secondURL = new URL(ucon.getHeaderField("Location"));


            ImageView imgProfile = (ImageView)findViewById(R.id.imgProfile);
            imgProfile.setImageBitmap(BitmapFactory.decodeStream(secondURL.openConnection().getInputStream()));

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          //  return null;
        }

        */
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.choose, menu);
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
