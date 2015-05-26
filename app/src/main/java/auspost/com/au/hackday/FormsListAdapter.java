package auspost.com.au.hackday;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by reidj2 on 26/05/2015.
 */
public class FormsListAdapter extends BaseAdapter {

    private TextView serviceName;
    private TextView serviceStatus;

    Context context;

    private List<Service> services = new ArrayList<>();

    public FormsListAdapter(Context context) {
        this.context = context;
        update("COAN", "Green");
        update("Mypost", "Amber");
        update("MRSO", "Red");
        update("Passport", "Black");
    }

    public void update(String serviceName, String serviceStatus) {
        boolean found = false;
        for (int i = 0; i < services.size(); i++) {
            if (services.get(i).name.compareTo(serviceName) == 0) {
                found = true;
            }
        }
        if (!found) {
            Service service = new Service(serviceName, serviceStatus);
            this.services.add(service);
            notifyDataSetChanged();
        }
    }


    @Override
    public int getCount() {
        return services.size();
    }

    @Override
    public Object getItem(int position) {
        return services.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.service_item, parent, false);
        serviceName = (TextView) rowView.findViewById(R.id.textService);
        serviceName.setText(services.get(position).name);
        serviceStatus = (TextView) rowView.findViewById(R.id.textStatus);
        serviceStatus.setText(services.get(position).status);
        return rowView;
    }
}
