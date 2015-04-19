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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import ph.devcon.android.news.NewsDetailsFragment;
import ph.devcon.android.news.db.News;

/**
 * Created by lope on 11/4/14.
 */
public class NewsDetailsPagerAdapter extends FragmentStatePagerAdapter {
    List<News> newsList;

    public NewsDetailsPagerAdapter(FragmentManager fm, List<News> newsList) {
        super(fm);
        this.newsList = newsList;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new NewsDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(NewsDetailsFragment.ID, newsList.get(position).getId());
        fragment.setArguments(bundle);
        return fragment;
    }

    public News getItemObject(int position) {
        return newsList.get(position);
    }

    @Override
    public int getCount() {
        return newsList.size();
    }

    public void setItems(List<News> newsList) {
        this.newsList = newsList;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return getItemObject(position).getTitle();
    }
}