package ph.devcon.android.news;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;
import de.greenrobot.event.EventBus;
import ph.devcon.android.DevConApplication;
import ph.devcon.android.R;
import ph.devcon.android.navigation.BaseDevConActivity;
import ph.devcon.android.navigation.SectionAttachedListener;
import ph.devcon.android.news.adapter.NewsAdapter;
import ph.devcon.android.news.db.News;
import ph.devcon.android.news.event.FetchedNewsListEvent;
import ph.devcon.android.news.service.NewsService;

/**
 * Created by lope on 10/6/14.
 */
public class NewsFragment extends Fragment {
    @InjectView(R.id.lvw_news)
    ListView lvwNews;

    @InjectView(R.id.pbr_loading)
    ProgressBar pbrLoading;

    @Inject
    EventBus eventBus;

    NewsAdapter newsAdapter;

    @Inject
    NewsService newsService;

    @OnItemClick(R.id.lvw_news)
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), NewsDetailsActivity.class);
        intent.putExtra(NewsDetailsActivity.POSITION, position);
        eventBus.postSticky(new FetchedNewsListEvent(newsAdapter.getItems()));
        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);
        DevConApplication.injectMembers(this);
        ButterKnife.inject(this, rootView);
        if (!eventBus.isRegistered(this)) {
            eventBus.register(this);
        }
        newsAdapter = new NewsAdapter(getActivity(), new ArrayList<News>());
        lvwNews.setAdapter(newsAdapter);
        lvwNews.setEmptyView(pbrLoading);
        lvwNews.addFooterView(buildFooterView(inflater));
        if (newsService.isCacheValid()) {
            newsService.populateFromCache(getLoaderManager(), savedInstanceState);
        } else {
            newsService.populateFromAPI();
        }
        return rootView;
    }

    public void setNewsList(List<News> newsList) {
        if (newsList != null && !newsList.isEmpty()) {
            newsAdapter.notifyDataSetChanged();
        }
    }

    public void onEventMainThread(FetchedNewsListEvent event) {
        setNewsList(event.newsList);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        eventBus.unregister(this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((SectionAttachedListener) activity).onSectionAttached(
                getArguments().getInt(BaseDevConActivity.PlaceholderFragment.ARG_SECTION_NUMBER));
    }

    protected View buildFooterView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.footer_standard, null);
    }

}