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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import ph.devcon.android.speaker.SpeakerDetailsFragment;
import ph.devcon.android.speaker.db.Speaker;

/**
 * Created by lope on 11/4/14.
 */
public class SpeakerDetailsPagerAdapter extends FragmentStatePagerAdapter {
    List<Speaker> speakerList;

    public SpeakerDetailsPagerAdapter(FragmentManager fm, List<Speaker> speakerList) {
        super(fm);
        this.speakerList = speakerList;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new SpeakerDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(SpeakerDetailsFragment.ID, speakerList.get(position).getId());
        fragment.setArguments(bundle);
        return fragment;
    }

    public Speaker getItemObject(int position) {
        return speakerList.get(position);
    }

    @Override
    public int getCount() {
        return speakerList.size();
    }

    public void setItems(List<Speaker> newsList) {
        this.speakerList = newsList;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return getItemObject(position).getFullName();
    }
}