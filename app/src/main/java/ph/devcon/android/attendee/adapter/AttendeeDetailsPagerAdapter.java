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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import ph.devcon.android.attendee.AttendeeDetailsFragment;
import ph.devcon.android.attendee.db.Attendee;

/**
 * Created by lope on 11/4/14.
 */
public class AttendeeDetailsPagerAdapter extends FragmentStatePagerAdapter {
    List<Attendee> attendeeList;

    public AttendeeDetailsPagerAdapter(FragmentManager fm, List<Attendee> attendeeList) {
        super(fm);
        this.attendeeList = attendeeList;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new AttendeeDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(AttendeeDetailsFragment.ID, attendeeList.get(position).getId());
        fragment.setArguments(bundle);
        return fragment;
    }

    public Attendee getItemObject(int position) {
        return attendeeList.get(position);
    }

    @Override
    public int getCount() {
        return attendeeList.size();
    }

    public void setItems(List<Attendee> newsList) {
        this.attendeeList = newsList;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return getItemObject(position).getUser().getFullName();
    }
}