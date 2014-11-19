/*
 * Copyright (C) 2014 Philippine Android Developers Community
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ph.devcon.android.attendee.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
public class AttendeeAdapter extends BaseAdapter {
    Context mContext;
    List<Attendee> mAttendeeList;

    public AttendeeAdapter(Context context, List<Attendee> attendeeList) {
        mContext = context;
        mAttendeeList = attendeeList;
    }

    public void setItems(List<Attendee> attendeeList) {
        mAttendeeList = attendeeList;
    }

    @Override
    public int getCount() {
        return mAttendeeList.size();
    }

    @Override
    public Object getItem(int position) {
        return mAttendeeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.item_attendee, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        Attendee attendee = (Attendee) getItem(position);
        holder.txtName.setText(attendee.getUser().getFullName());
        holder.txtJob.setText(attendee.getUser().getPosition());
        holder.txtLanguages.setText(attendee.getUser().getPrettyTechnologyList());
        if (!Util.isNullOrEmpty(attendee.getUser().getPhotoUrl())) {
            Picasso.with(mContext).load(attendee.getUser().getPhotoUrl()).placeholder(R.drawable.ic_action_person).into(holder.imgAttendee);
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
