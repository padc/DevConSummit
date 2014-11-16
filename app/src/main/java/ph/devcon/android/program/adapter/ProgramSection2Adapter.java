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

package ph.devcon.android.program.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.common.base.Optional;
import com.google.common.collect.Maps;
import com.nhaarman.listviewanimations.ArrayAdapter;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ph.devcon.android.R;
import ph.devcon.android.program.db.Program;
import ph.devcon.android.speaker.db.Speaker;
import ph.devcon.android.util.Util;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by lope on 9/21/14.
 */
public class ProgramSection2Adapter extends ArrayAdapter<Program> implements StickyListHeadersAdapter {

    Context mContext;
    List<Program> programList;
    HashMap<Integer, Program> programHashMap;

    public ProgramSection2Adapter(Context context, List<Program> programList) {
        mContext = context;
        this.programList = programList;
        buildProgramMap();
    }

    public void buildProgramMap() {
        programHashMap = Maps.newHashMap();
        int counter = 0;
        for (Program program : programList) {
            programHashMap.put(counter, program);
            counter++;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder holder = null;
        if (position != programHashMap.size()) {
            Program program = programHashMap.get(position);
            if (convertView != null) {
                // related to a bug lol
                View sponsorView = convertView.findViewById(R.id.cont_sponsors);
                if (sponsorView == null)
                    holder = (ViewHolder) convertView.getTag();
                else {
                    convertView = inflater.inflate(mContext.getResources().getLayout(R.layout.item_program), null);
                    holder = new ViewHolder(convertView);
                    convertView.setTag(holder);
                }
            } else {
                convertView = inflater.inflate(mContext.getResources().getLayout(R.layout.item_program), null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }
            holder.txtProgramTitle.setText(program.getTitle());
            Optional<Speaker> speakerOptional = Optional.fromNullable(program.getMainSpeaker());
            if (speakerOptional.isPresent()) {
                Speaker speaker = speakerOptional.get();
                if (!Util.isNullOrEmpty(speaker.getPhotoUrl())) {
                    Picasso.with(mContext).load(speaker.getPhotoUrl()).into(holder.imgSpeaker);
                }
                holder.txtSpeakerName.setText(speaker.getFullName());
            }
            holder.txtProgramDescription.setText(program.getDescription());
        } else {
            // now at the end of the list
            return inflater.inflate(R.layout.footer_standard, parent, true);
        }
        return convertView;
    }

    @Override
    public View getHeaderView(int section, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(mContext.getResources().getLayout(R.layout.item_header_program), null);
        if (convertView == null) {
            convertView = inflater.inflate(mContext.getResources().getLayout(R.layout.item_header_program), null);
        }
        TextView txtTime = (TextView) convertView.findViewById(R.id.txt_time);
        Program program = programHashMap.get(section);
        txtTime.setText(program.getStartAt());
        if (section == programHashMap.size()) {
            convertView = inflater.inflate(mContext.getResources().getLayout(R.layout.item_header_null), null);
        }
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        return position;
    }

    static class ViewHolder {
        @InjectView(R.id.img_speaker)
        ImageView imgSpeaker;
        @InjectView(R.id.txt_speaker_name)
        TextView txtSpeakerName;
        @InjectView(R.id.txt_program_title)
        TextView txtProgramTitle;
        @InjectView(R.id.txt_program_description)
        TextView txtProgramDescription;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}