package ph.devcon.android.attendee.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ph.devcon.android.R;
import ph.devcon.android.attendee.db.Attendee;
import ph.devcon.android.util.Util;

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
        Attendee attendee = getItem(position);
        holder.txtName.setText(attendee.getUser().getFullName());
        holder.txtJob.setText(attendee.getUser().getPosition());
        holder.txtLanguages.setText(attendee.getUser().getPrettyTechnologyList());
        if (!Util.isNullOrEmpty(attendee.getUser().getPhotoUrl())) {
            Picasso.with(getContext()).load(attendee.getUser().getPhotoUrl()).placeholder(R.drawable.ic_action_person).into(holder.imgAttendee);
        }
        Util.emptyToGone(holder.txtName, holder.txtJob, holder.txtLanguages);
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
