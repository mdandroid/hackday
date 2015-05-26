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
        update("Postal Vote", "Green");
        update("Land Title", "Red");
        update("Mypost Card", "Amber");
        update("Concession Card", "Red");
        update("MPDM", "Green");
    }

    public void update(String serviceName, String serviceStatus) {
        for (int i = 0; i < services.size(); i++) {
            if (services.get(i).name.compareTo(serviceName) == 0) {
                this.services.remove(i);
            }
        }
        Service service = new Service(serviceName, serviceStatus);
        this.services.add(service);
        notifyDataSetChanged();
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
        String status = services.get(position).status;
        if (status.equalsIgnoreCase("green")) {
            serviceStatus.setTextColor(context.getResources().getColor(R.color.green));
        } else if (status.equalsIgnoreCase("amber")){
            serviceStatus.setTextColor(context.getResources().getColor(R.color.orange));
        } else if (status.equalsIgnoreCase("red")){
            serviceStatus.setTextColor(context.getResources().getColor(R.color.red));
        } else if (status.equalsIgnoreCase("black")){
            serviceStatus.setTextColor(context.getResources().getColor(R.color.black));
        }
        serviceStatus.setText(services.get(position).status);
        return rowView;
    }
}
