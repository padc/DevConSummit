package ph.devcon.android.speaker;

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
import ph.devcon.android.speaker.adapter.SpeakerDetailsPagerAdapter;
import ph.devcon.android.speaker.db.Speaker;
import ph.devcon.android.speaker.event.FetchedSpeakerListEvent;
import ph.devcon.android.speaker.service.SpeakerService;

/**
 * Created by lope on 10/6/14.
 */
public class SpeakerDetailsActivity extends BaseDevConActivity {
    public static final String POSITION = "position";
    SpeakerDetailsPagerAdapter mSpeakerDetailsPagerAdapter;

    @InjectView(R.id.container)
    ViewPager mViewPager;

    @Inject
    SpeakerService speakerService;

    @Inject
    EventBus eventBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DevConApplication.injectMembers(this);
        ButterKnife.inject(this);
        if (!eventBus.isRegistered(this)) {
            eventBus.registerSticky(this);
        }
        setHomeAsUp();
        speakerService.populateFromCache(getLoaderManager(), savedInstanceState);
        // ViewPager and its adapters use support library
        // fragments, so use getSupportFragmentManager.
        mSpeakerDetailsPagerAdapter =
                new SpeakerDetailsPagerAdapter(
                        getSupportFragmentManager(), new ArrayList<Speaker>());
        mViewPager.setAdapter(mSpeakerDetailsPagerAdapter);
    }

    @Override
    public void onSectionAttached(int number) {
        mTitle = getString(R.string.title_speakers);
    }

    public void setSpeakerList(List<Speaker> speakerList) {
        if (speakerList != null && !speakerList.isEmpty()) {
            mSpeakerDetailsPagerAdapter.setItems(speakerList);
            mSpeakerDetailsPagerAdapter.notifyDataSetChanged();
            mViewPager.setCurrentItem(getIntent().getIntExtra(POSITION, 0));
        }
    }

    public void onEventMainThread(FetchedSpeakerListEvent event) {
        setSpeakerList(event.speakers);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_speaker_details;
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
