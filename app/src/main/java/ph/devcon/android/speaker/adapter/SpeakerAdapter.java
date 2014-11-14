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

package ph.devcon.android.speaker.adapter;

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
import ph.devcon.android.speaker.db.Speaker;
import ph.devcon.android.util.Util;

/**
 * Created by lope on 9/17/14.
 */
public class SpeakerAdapter extends ArrayAdapter<Speaker> {
    boolean isShouldDisplayTalkTitle = true;

    List<Speaker> speakers;

    public SpeakerAdapter(Context context, List<Speaker> speakers, boolean isShouldDisplayTalkTitle) {
        super(context, R.layout.item_program, speakers);
        this.isShouldDisplayTalkTitle = isShouldDisplayTalkTitle;
        this.speakers = speakers;
    }

    public List<Speaker> getItems() {
        return this.speakers;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Speaker speaker = getItem(position);
        LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
        ViewHolder holder = null;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.item_speaker, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        if (isShouldDisplayTalkTitle)
            holder.txtProgramTitle.setText(speaker.getTalkTitle());
        else
            holder.txtProgramTitle.setText(speaker.getPanelTitle());
        holder.txtSpeakerName.setText(speaker.getFullName());
        holder.txtPosition.setText(speaker.getPosition());
        if (!Util.isNullOrEmpty(speaker.getPhotoUrl())) {
            Picasso.with(getContext()).load(speaker.getPhotoUrl()).into(holder.imgSpeaker);
        }
        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.img_speaker)
        ImageView imgSpeaker;
        @InjectView(R.id.txt_speaker_name)
        TextView txtSpeakerName;
        @InjectView(R.id.txt_position)
        TextView txtPosition;
        @InjectView(R.id.txt_program_title)
        TextView txtProgramTitle;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
