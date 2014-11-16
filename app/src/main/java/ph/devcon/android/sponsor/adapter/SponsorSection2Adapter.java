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

package ph.devcon.android.sponsor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.common.collect.ArrayListMultimap;
import com.nhaarman.listviewanimations.ArrayAdapter;
import com.squareup.picasso.Picasso;

import org.apmem.tools.layouts.FlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ph.devcon.android.R;
import ph.devcon.android.sponsor.db.Sponsor;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by lope on 9/21/14.
 * emilsjolander implementation of sticky header listview
 * <p/>
 * I like it because it still inherits from listview
 */
public class SponsorSection2Adapter extends ArrayAdapter<List<Sponsor>> implements StickyListHeadersAdapter {

    Context mContext;
    ArrayListMultimap<String, Sponsor> mSponsorMultimap = ArrayListMultimap.create();
    List<String> mSponsorTypeKeyList = new ArrayList<String>();

    public SponsorSection2Adapter(Context context, ArrayListMultimap<String, Sponsor> sponsorMultimap) {
        mContext = context;
        mSponsorMultimap = sponsorMultimap;
        for (String key : mSponsorMultimap.keySet()) {
            mSponsorTypeKeyList.add(key);
        }
    }

    public List<Sponsor> getItem(int position) {
        return mSponsorMultimap.get(mSponsorTypeKeyList.get(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder holder = null;
        List<Sponsor> sponsors = getItem(position);
        if (convertView != null) {
            // related to a bug lol
//            holder = (ViewHolder) convertView.getTag();
        } else if (convertView == null) {
            convertView = inflater.inflate(mContext.getResources().getLayout(R.layout.item_sponsor), null);
//            holder = new ViewHolder(convertView);
//            convertView.setTag(holder);
        }
        FlowLayout contSponsorImages = (FlowLayout) convertView.findViewById(R.id.cont_sponsor_images);
        for (Sponsor sponsor : sponsors) {
            ImageView imageView = new ImageView(mContext);
            Picasso.with(mContext).load(sponsor.getPhotoUrl()).into(imageView);
            contSponsorImages.addView(imageView);
        }
        return convertView;
    }

    @Override
    public View getHeaderView(int section, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(mContext.getResources().getLayout(R.layout.item_header_sponsor), parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder.txtSponsorType.setText(mSponsorTypeKeyList.get(section));
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        return position;
    }

    static class ViewHolder {
        @InjectView(R.id.txt_sponsor_type)
        TextView txtSponsorType;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}