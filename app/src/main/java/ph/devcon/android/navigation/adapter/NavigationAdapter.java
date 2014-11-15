package ph.devcon.android.navigation.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ph.devcon.android.R;

/**
 * Created by lope on 11/15/14.
 */
public class NavigationAdapter extends ArrayAdapter<String> {
    static String[] items = new String[]{
            "News",
            "Programs",
            "Speakers",
            "Attendees",
            "Sponsors",
    };

    public NavigationAdapter(Context context) {
        super(context, R.layout.item_navigation_drawer, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.item_navigation_drawer, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder.txtTitle.setText(getItem(position));
        setColor(position, holder.txtTitle);
        return null;

    }

    public void setColor(int position, TextView txtTitle) {
        switch (position) {
            case 0:
                txtTitle.setBackgroundColor(getContext().getResources().getColor(R.color.green));
                break;
            case 1:
                txtTitle.setBackgroundColor(getContext().getResources().getColor(R.color.orange));
                break;
            case 2:
                txtTitle.setBackgroundColor(getContext().getResources().getColor(R.color.purple));
                break;
            case 3:
                txtTitle.setBackgroundColor(getContext().getResources().getColor(R.color.yellow));
                break;
            case 4:
                txtTitle.setBackgroundColor(getContext().getResources().getColor(R.color.blue));
                break;
            default:
                txtTitle.setBackgroundColor(getContext().getResources().getColor(R.color.green));
                break;
        }
    }

    static class ViewHolder {
        @InjectView(android.R.id.text1)
        TextView txtTitle;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
