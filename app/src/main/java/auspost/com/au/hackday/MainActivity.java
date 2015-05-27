package auspost.com.au.hackday;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import auspost.com.au.hackday.model.User;
import auspost.com.au.hackday.persistence.DatabaseManager;
import auspost.com.au.hackday.utils.UserUtils;

import java.io.InputStream;


public class MainActivity extends ActionBarActivity {

    private ServicesListAdapter formsListAdapter;

    private ListView formListView;
    private ImageButton profilePageButton;
    private TextView profilePer;
    private DatabaseManager databaseManager;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = getIntent().getParcelableExtra("user");
        setContentView(R.layout.activity_main);
        formsListAdapter = new ServicesListAdapter(this.getApplicationContext(), user);
        formListView = (ListView) findViewById(R.id.list_forms);
        formListView.setAdapter(formsListAdapter);
        profilePageButton();

        databaseManager = new DatabaseManager(this);

        if (user.changeAddress.equalsIgnoreCase("true")) {
            formsListAdapter.update("COAN", "Green", true);
        } else {
            formsListAdapter.update("COAN", "Amber", false);
        }
        formsListAdapter.update("Mypost", "Amber", false);
        formsListAdapter.update("MRSO", "Red", false);
        formsListAdapter.update("Passport", "Black", false);
        formsListAdapter.update("Postal Vote", "Green", true);
        formsListAdapter.update("Land Title", "Red", false);
        formsListAdapter.update("Mypost Card", "Amber", false);
        formsListAdapter.update("Concession Card", "Red", false);
        formsListAdapter.update("MPDM", "Green", true);
    }

    private void verificationPercentage() {
        profilePer = (TextView) findViewById(R.id.verifiedPer);
        profilePer.setText(user.verificationPercentage + "%");
    }

    @Override
    public void onResume() {
        super.onResume();
        user = getIntent().getParcelableExtra("user");
        if (databaseManager.getVerificationPercentage(user.email) != null) {
            user.verificationPercentage = databaseManager.getVerificationPercentage(user.email);
        }
        if (databaseManager.getChangeAddress(user.email) != null) {
            user.changeAddress = databaseManager.getChangeAddress(user.email);
        }
        verificationPercentage();
        if (user.changeAddress.equalsIgnoreCase("true")) {
            formsListAdapter.update("COAN", "Green", true);
        } else {
            formsListAdapter.update("COAN", "Amber", false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    private void profilePageButton() {
        profilePageButton = (ImageButton) findViewById(R.id.profile_image);
        new DownloadImageTask(profilePageButton)
                .execute("http://wp.patheos.com.s3.amazonaws.com/blogs/tinseltalk/files/2012/05/280px-Tony_Stark_Avengers-150x150.png");
        profilePageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
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
