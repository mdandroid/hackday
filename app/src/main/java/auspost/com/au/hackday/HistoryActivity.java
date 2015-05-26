package auspost.com.au.hackday;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class HistoryActivity extends ActionBarActivity {
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.listView);

        // Defined Array values to show in ListView
        Notifi[] cards = new Notifi[] {
                new Notifi("ANZ","Successfully acknowledaged your address changes"),
                new Notifi("CommBank", "Your address has been successfully changed"),
                new Notifi("City West Water", "Processing your address change request"),
        };

        NotiItemAdapter adapter = new NotiItemAdapter(this, R.id.listView, cards);

        // Assign adapter to ListView
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_history, menu);
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

    private class Notifi {
        public String notiTitle;
        public String notiDesc;

        private Notifi(String notiTitle, String notiDesc){
            this.notiTitle = notiTitle;
            this.notiDesc = notiDesc;
        }
    }

    private class NotiItemAdapter extends ArrayAdapter<Notifi> {
        private Activity myContext;
        private Notifi[] cards;

        public NotiItemAdapter(Context context, int textViewResourceId, Notifi[] objects) {
            super(context, textViewResourceId, objects);
            myContext = (Activity) context;
            cards = objects;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = myContext.getLayoutInflater();
            View rowView = inflater.inflate(R.layout.list_item, null);
            TextView postTitleView = (TextView) rowView.findViewById(R.id.heading);
            postTitleView.setText(cards[position].notiTitle);
            TextView postDateView = (TextView) rowView.findViewById(R.id.short_desc);
            postDateView.setText(cards[position].notiDesc);

            return rowView;
        }
    }

}
