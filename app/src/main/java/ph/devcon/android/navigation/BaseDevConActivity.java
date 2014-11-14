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

package ph.devcon.android.navigation;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import ph.devcon.android.R;
import ph.devcon.android.attendee.AttendeesFragment;
import ph.devcon.android.news.NewsFragment;
import ph.devcon.android.program.ProgramFragment;
import ph.devcon.android.speaker.SpeakerTabFragment;
import ph.devcon.android.sponsor.SponsorFragment;

public abstract class BaseDevConActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, SectionAttachedListener {
    public static final int NEWS = 1;
    public static final int PROGRAMS = 2;
    public static final int SPEAKERS = 3;
    public static final int ATTENDEES = 4;
    public static final int SPONSORS = 5;
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    protected NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    protected CharSequence mTitle;

    protected DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer, mDrawerLayout);

        // Set the drawer toggle as the DrawerListener

//        new ActionBarDrawerToggle()
    }

    protected abstract int getLayout();

    protected void setHomeAsUp() {
        mNavigationDrawerFragment.getActionBarDrawerToggle().setDrawerIndicatorEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    @Override
    public void onSectionAttached(int number) {
        switch (number) {
            case NEWS:
                mTitle = getString(R.string.title_news);
                break;
            case PROGRAMS:
                mTitle = getString(R.string.title_programs);
                break;
            case ATTENDEES:
                mTitle = getString(R.string.title_attendees);
                break;
            case SPEAKERS:
                mTitle = getString(R.string.title_speakers);
                break;
            case SPONSORS:
                mTitle = getString(R.string.title_sponsors);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
//            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        public static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static android.support.v4.app.Fragment newInstance(int sectionNumber) {
            android.support.v4.app.Fragment fragment = null;
            switch (sectionNumber) {
                case NEWS:
                    fragment = new NewsFragment();
                    break;
                case PROGRAMS:
                    fragment = new ProgramFragment();
                    break;
                case SPEAKERS:
                    fragment = new SpeakerTabFragment();
                    break;
                case ATTENDEES:
                    fragment = new AttendeesFragment();
                    break;
                case SPONSORS:
                    fragment = new SponsorFragment();
                    break;
                default:
                    fragment = new NewsFragment();
                    break;
            }
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

    }

}
