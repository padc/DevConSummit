package ph.devcon.android.attendee.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ph.devcon.android.R;
import ph.devcon.android.attendee.db.Attendee;

/**
 * Created by lope on 10/9/14.
 */
public class AttendeeAdapter extends ArrayAdapter<Attendee> {
    public AttendeeAdapter(Context context, List<Attendee> attendeeList) {
        super(context, R.layout.item_attendee, attendeeList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.item_attendee, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.img_attendee)
        ImageView imgAttendee;
        @InjectView(R.id.txt_name)
        TextView txtName;
        @InjectView(R.id.txt_job)
        TextView txtJob;
        @InjectView(R.id.txt_languages)
        TextView txtLanguages;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
