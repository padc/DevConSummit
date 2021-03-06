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

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ph.devcon.android.R;
import ph.devcon.android.attendee.db.FTSAttendee;
import ph.devcon.android.util.Util;

/**
 * Created by lope on 10/9/14.
 */
public class AttendeeCursorAdapter extends SimpleCursorAdapter {
    final static String[] columns = new String[]{
            FTSAttendee.COL_FULL_NAME,
            FTSAttendee.COL_POSITION,
            FTSAttendee.COL_COMPANY,
            FTSAttendee.COL_PHOTO_URL,
            FTSAttendee.COL_TECH_PRIMARY,
    };
    // the XML defined views which the data will be bound to
    final static int[] to = new int[]{
            R.id.txt_full_name,
            R.id.txt_job,
            R.id.img_attendee,
            R.id.txt_main_technology,
    };
    protected Context mContext;
    protected Cursor cursor;

    public AttendeeCursorAdapter(Context context, Cursor cursor, int flags) {
        super(context, R.layout.item_attendee, cursor, columns, to, flags);
        mContext = context;
        this.cursor = cursor;
    }

    public AttendeeCursorAdapter(Context context, int layout, Cursor cursor, String[] from, int[] to, int flags) {
        super(context, layout, cursor, from, to, flags);
        mContext = context;
        this.cursor = cursor;
    }

    @Override
    public void changeCursor(Cursor cursor) {
        this.cursor = cursor;
        super.changeCursor(cursor);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_attendee, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        this.cursor.moveToPosition(position);
        String fullName = this.cursor.getString(this.cursor.getColumnIndex(FTSAttendee.COL_FULL_NAME));
        String companyPosition = this.cursor.getString(this.cursor.getColumnIndex(FTSAttendee.COL_POSITION));
        String company = this.cursor.getString(this.cursor.getColumnIndex(FTSAttendee.COL_COMPANY));
        String technologyMain = this.cursor.getString(this.cursor.getColumnIndex(FTSAttendee.COL_TECH_PRIMARY));
        String imgUrl = this.cursor.getString(this.cursor.getColumnIndex(FTSAttendee.COL_PHOTO_URL));
        holder.txtName.setText(fullName);
        holder.txtJob.setText(getJobLabel(company, companyPosition));
        holder.txtLanguages.setText(technologyMain);
        if (!Util.isNullOrEmpty(imgUrl)) {
            Picasso.with(mContext).load(imgUrl).placeholder(R.drawable.ic_action_person).into(holder.imgAttendee);
        }
        Util.emptyToGone(holder.txtName, holder.txtJob, holder.txtLanguages);
        return convertView;
    }

    private String getJobLabel(String company, String companyPosition) {
        String label = "";
        if (Util.isNullOrEmpty(company) || Util.isNullOrEmpty(companyPosition)) {
            if (!Util.isNullOrEmpty(company)) {
                label = company;
            } else if (!Util.isNullOrEmpty(companyPosition)) {
                label = companyPosition;
            }
        } else {
            label = companyPosition + " at " + company;
        }
        return label;
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
