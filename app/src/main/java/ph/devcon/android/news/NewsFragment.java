package ph.devcon.android.news;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;
import de.greenrobot.event.EventBus;
import ph.devcon.android.DevConApplication;
import ph.devcon.android.R;
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

    @Inject
    EventBus eventBus;

    NewsAdapter newsAdapter;

    @Inject
    NewsService newsService;

    @OnItemClick(R.id.lvw_news)
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), NewsDetailsActivity.class);
        intent.putExtra(NewsDetailsActivity.POSITION, position);
        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);
        DevConApplication.injectMembers(this);
        ButterKnife.inject(this, rootView);
        if (!eventBus.isRegistered(this)) {
            eventBus.registerSticky(this);
        }
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
            newsAdapter = new NewsAdapter(getActivity(), newsList);
            lvwNews.setAdapter(newsAdapter);
        }
    }

    public void onEventMainThread(FetchedNewsListEvent event) {
        setNewsList(event.newsList);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    protected View buildFooterView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.footer_standard, null);
    }
}