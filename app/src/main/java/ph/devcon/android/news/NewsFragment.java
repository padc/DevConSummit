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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;

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
public class NewsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    @InjectView(R.id.lvw_news)
    ListView lvwNews;

    @Inject
    EventBus eventBus;

    NewsAdapter newsAdapter;

    SwingBottomInAnimationAdapter animationAdapter;

    @Inject
    NewsService newsService;

    @InjectView(R.id.cont_news)
    SwipeRefreshLayout swipeLayout;

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
        List<News> newsList = new ArrayList<News>();
        newsAdapter = new NewsAdapter(getActivity(), newsList);
        lvwNews.setAdapter(newsAdapter);
        if (newsService.isCacheValid()) {
            newsService.populateFromCache(getLoaderManager(), savedInstanceState);
        } else {
            newsService.populateFromAPI();
        }
        animationAdapter = new SwingBottomInAnimationAdapter(newsAdapter);
        animationAdapter.setAbsListView(lvwNews);
        lvwNews.setAdapter(animationAdapter);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeResources(R.color.yellow,
                R.color.orange,
                R.color.purple,
                R.color.blue);
        return rootView;
    }

    public void setNewsList(List<News> newsList) {
        swipeLayout.setRefreshing(false);
        if (newsList != null) {
            newsAdapter.setItems(newsList);
            newsAdapter.notifyDataSetChanged();
        }
        if (lvwNews.getFooterViewsCount() == 0)
            lvwNews.addFooterView(buildFooterView(getLayoutInflater(getArguments())));
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

    @Override
    public void onRefresh() {
        newsService.populateFromAPI();
    }
}