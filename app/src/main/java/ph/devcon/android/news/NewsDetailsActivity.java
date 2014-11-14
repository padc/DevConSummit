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

package ph.devcon.android.news;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.common.base.Optional;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.greenrobot.event.EventBus;
import ph.devcon.android.DevConApplication;
import ph.devcon.android.R;
import ph.devcon.android.news.adapter.NewsDetailsPagerAdapter;
import ph.devcon.android.news.db.News;
import ph.devcon.android.news.event.FetchedNewsListEvent;

/**
 * Created by lope on 10/6/14.
 */
public class NewsDetailsActivity extends ActionBarActivity {
    public static final String POSITION = "position";

    NewsDetailsPagerAdapter mNewsDetailsPagerAdapter;

    @InjectView(R.id.container)
    ViewPager mViewPager;

    @Inject
    EventBus eventBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        DevConApplication.injectMembers(this);
        ButterKnife.inject(this);
        setHomeAsUp();
        //We use a handler so that the activity starts very fast
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                delayedInit();
            }
        }, 100);
    }

    protected void delayedInit() {
        // ViewPager and its adapters use support library
        // fragments, so use getSupportFragmentManager.
        mNewsDetailsPagerAdapter =
                new NewsDetailsPagerAdapter(
                        getSupportFragmentManager(), new ArrayList<News>());
        mViewPager.setAdapter(mNewsDetailsPagerAdapter);
        if (!eventBus.isRegistered(this)) {
            eventBus.registerSticky(this);
        }
    }

    protected void setHomeAsUp() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    public void setNewsList(List<News> newsList) {
        if (newsList != null && !newsList.isEmpty()) {
            mNewsDetailsPagerAdapter.setItems(newsList);
            mNewsDetailsPagerAdapter.notifyDataSetChanged();
            mViewPager.setCurrentItem(getIntent().getIntExtra(POSITION, 0));
        }
    }

    public void onEventMainThread(FetchedNewsListEvent event) {
        if (Optional.of(event).isPresent()) {
            eventBus.removeStickyEvent(event);
            setNewsList(event.newsList);
        }
    }

    @Override
    public void onBackPressed() {
        int currentItem = mViewPager.getCurrentItem();
        if (currentItem != 0) {
            mViewPager.setCurrentItem(currentItem - 1);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    // This activity is NOT part of this app's task, so create a new task
                    // when navigating up, with a synthesized back stack.
                    TaskStackBuilder.create(this)
                            // Add all of this activity's parents to the back stack
                            .addNextIntentWithParentStack(upIntent)
                                    // Navigate up to the closest parent
                            .startActivities();
                } else {
                    // This activity is part of this app's task, so simply
                    // navigate up to the logical parent activity.
                    NavUtils.navigateUpTo(this, upIntent);
                }
//                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        eventBus.unregister(this);
    }
}
