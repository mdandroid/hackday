package auspost.com.au.hackday;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import org.w3c.dom.Text;

import java.io.InputStream;


public class ProfileActivity extends ActionBarActivity {
    private EditText dob, email, mobile;
    private TextView firstName;
    private TextView lastName;
    private TextView profilePer;
    private ImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        dob = (EditText) findViewById(R.id.dob_edit);
        email = (EditText) findViewById(R.id.email_edit);
        mobile = (EditText) findViewById(R.id.phone_edit);
        profilePer = (TextView) findViewById(R.id.verifiedPer);
        firstName = (TextView) findViewById(R.id.first_name_lbl);
        lastName = (TextView) findViewById(R.id.last_name_lbl);
        profileImage = (ImageView) findViewById(R.id.profile_image);
        loadProfile();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadProfile(){
        new DownloadImageTask(profileImage)
                .execute("http://wp.patheos.com.s3.amazonaws.com/blogs/tinseltalk/files/2012/05/280px-Tony_Stark_Avengers-150x150.png");
        dob.setText("01/01/1980");
        mobile.setText("0401 234 567");
        email.setText("tony.stark@starkindustry.com");
        firstName.setText("Tony");
        lastName.setText("Stark");
        profilePer.setText("20%");
    }

    public void editProfile(View view){
        dob.setEnabled(true);
        mobile.setEnabled(true);
        email.setEnabled(true);
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
