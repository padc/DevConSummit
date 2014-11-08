package ph.devcon.android.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.greenrobot.event.EventBus;
import ph.devcon.android.DevConApplication;
import ph.devcon.android.R;
import ph.devcon.android.navigation.BaseDevConActivity;
import ph.devcon.android.news.adapter.NewsDetailsPagerAdapter;
import ph.devcon.android.news.db.News;
import ph.devcon.android.news.event.FetchedNewsListEvent;
import ph.devcon.android.news.service.NewsService;

/**
 * Created by lope on 10/6/14.
 */
public class NewsDetailsActivity extends BaseDevConActivity {
    public static final String POSITION = "position";

    NewsDetailsPagerAdapter mNewsDetailsPagerAdapter;

    @InjectView(R.id.container)
    ViewPager mViewPager;

    @Inject
    EventBus eventBus;

    @Inject
    NewsService newsService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DevConApplication.injectMembers(this);
        ButterKnife.inject(this);
        if (!eventBus.isRegistered(this)) {
            eventBus.registerSticky(this);
        }
        setHomeAsUp();
        newsService.populateFromCache(getLoaderManager(), savedInstanceState);
        // ViewPager and its adapters use support library
        // fragments, so use getSupportFragmentManager.
        mNewsDetailsPagerAdapter =
                new NewsDetailsPagerAdapter(
                        getSupportFragmentManager(), new ArrayList<News>());
        mViewPager.setAdapter(mNewsDetailsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    public void setNewsList(List<News> newsList) {
        if (newsList != null && !newsList.isEmpty()) {
            mNewsDetailsPagerAdapter.setItems(newsList);
            mNewsDetailsPagerAdapter.notifyDataSetChanged();
            mViewPager.setCurrentItem(getIntent().getIntExtra(POSITION, 0));
        }
    }

    public void onEventMainThread(FetchedNewsListEvent event) {
        setNewsList(event.newsList);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_news_details;
    }

    protected View buildFooterView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.footer_standard, null);
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
