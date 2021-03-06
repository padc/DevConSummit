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

package ph.devcon.android.news.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.common.base.Optional;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ph.devcon.android.R;
import ph.devcon.android.category.db.Category;
import ph.devcon.android.news.db.News;
import ph.devcon.android.util.Util;

/**
 * Created by lope on 10/6/14.
 */
public class NewsAdapter extends BaseAdapter {
    Context mContext;
    List<News> mNewsList;

    public NewsAdapter(Context context, List<News> newsList) {
        mContext = context;
        mNewsList = newsList;
    }

    public List<News> getItems() {
        return mNewsList;
    }

    public void setItems(List<News> newsList) {
        mNewsList = newsList;
    }

    @Override
    public int getCount() {
        return mNewsList.size();
    }

    @Override
    public Object getItem(int position) {
        return mNewsList.get(position);
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
            convertView = inflater.inflate(R.layout.item_news, parent, false);
            holder = new ViewHolder(convertView, mContext);
            convertView.setTag(holder);
        }
        News newsItem = (News) getItem(position);
        holder.txtTitle.setText(newsItem.getTitle());
        holder.txtPreview.setText(Util.stripHtml(newsItem.getHtmlContent()));
        Optional<Category> categoryOptional = Optional.fromNullable(newsItem.getCategory());
        if (categoryOptional.isPresent()) {
            holder.txtTag.setText(categoryOptional.get().getName());
            setColor(categoryOptional.get().getName(), holder.txtTag);
        }
        if (!Util.isNullOrEmpty(newsItem.getPhotoUrl())) {
            Picasso.with(mContext).setIndicatorsEnabled(true);
            Picasso.with(mContext).load(newsItem.getPhotoUrl()).into(holder.imgIcon);
        }
        return convertView;
    }

    protected void setColor(String tagName, TextView txtTag) {
        if (tagName.equals(Category.SPEAKERS)) {
            txtTag.setBackgroundResource(R.color.blue);
        } else if (tagName.equals(Category.PROMOS)) {
            txtTag.setBackgroundResource(R.color.yellow);
        } else if (tagName.equals(Category.PROGRAM)) {
            txtTag.setBackgroundResource(R.color.purple);
        }
    }

    static class ViewHolder {
        @InjectView(R.id.img_news)
        ImageView imgIcon;
        @InjectView(R.id.txt_tag)
        TextView txtTag;
        @InjectView(R.id.txt_title)
        TextView txtTitle;
        @InjectView(R.id.txt_preview)
        TextView txtPreview;

        public ViewHolder(View view, Context context) {
            ButterKnife.inject(this, view);
        }
    }
}
