package auspost.com.au.hackday;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import auspost.com.au.hackday.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by reidj2 on 26/05/2015.
 */
public class ServicesListAdapter extends BaseAdapter {

    private TextView serviceName;
    private TextView serviceStatus;
    private ImageView serviceActive;
    private User user;

    Context context;

    private List<Service> services = new ArrayList<>();

    public ServicesListAdapter(Context context, User user) {
        this.context = context;
        this.user = user;
    }

    public void update(String serviceName, String serviceStatus, boolean active) {
        for (int i = 0; i < services.size(); i++) {
            if (services.get(i).name.compareTo(serviceName) == 0) {
                this.services.remove(i);
            }
        }
        Service service = new Service(serviceName, serviceStatus, active);
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
        serviceActive = (ImageView) rowView.findViewById(R.id.textActive);
        String status = services.get(position).status;
        boolean active = services.get(position).active;
        if (status.equalsIgnoreCase("green")) {
            serviceStatus.setText(R.string.status_green);
        } else if (status.equalsIgnoreCase("amber")){
            serviceStatus.setText(R.string.status_amber);
        } else if (status.equalsIgnoreCase("red")){
            serviceStatus.setText(R.string.status_orange);
        } else if (status.equalsIgnoreCase("black")){
            serviceStatus.setText(R.string.status_black);
        }

        if (active) {
            serviceActive.setImageDrawable(context.getResources().getDrawable(R.mipmap.complete));
        } else {
            serviceActive.setImageDrawable(context.getResources().getDrawable(R.mipmap.incomplete));
        }
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewAddressActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("user", user);
                context.startActivity(intent);
            }
        });
        return rowView;
    }
}
