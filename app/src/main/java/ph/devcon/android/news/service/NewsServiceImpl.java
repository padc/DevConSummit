package ph.devcon.android.news.service;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;

import com.google.common.base.Optional;
import com.path.android.jobqueue.JobManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import ph.devcon.android.base.db.OrmliteListLoader;
import ph.devcon.android.news.api.NewsAPI;
import ph.devcon.android.news.api.NewsAPIContainer;
import ph.devcon.android.news.api.NewsBaseResponse;
import ph.devcon.android.news.db.News;
import ph.devcon.android.news.db.NewsDao;
import ph.devcon.android.news.event.FetchedNewsListEvent;
import ph.devcon.android.news.event.FetchedNewsListFailedEvent;
import ph.devcon.android.news.job.FetchNewsJob;

/**
 * Created by lope on 10/6/14.
 */
public class NewsServiceImpl implements NewsService {

    Context context;

    JobManager jobManager;

    EventBus eventBus;

    NewsDao newsDao;

    public NewsServiceImpl(Context context, JobManager jobManager, EventBus eventBus, NewsDao newsDao) {
        this.context = context;
        this.jobManager = jobManager;
        this.eventBus = eventBus;
        this.newsDao = newsDao;
    }

    @Override
    public List<News> createNewsList(NewsBaseResponse baseResponse) {
        try {
            newsDao.clear();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Optional<NewsBaseResponse> baseResponseOptional = Optional.of(baseResponse);
        List<News> newsDBList = new ArrayList<News>();
        if (baseResponseOptional.isPresent())
            for (NewsAPIContainer container : baseResponseOptional.get().getNews()) {
                try {
                    NewsAPI newsAPI = container.getNews();
                    News newsDb = News.toNews(newsAPI);
                    newsDao.create(newsDb);
                    newsDBList.add(newsDb);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        return newsDBList;
    }

    @Override
    public void populateFromCache(LoaderManager loaderManager, Bundle savedInstanceState) {
        loaderManager.initLoader(0, savedInstanceState,
                new LoaderManager.LoaderCallbacks<List<News>>() {
                    @Override
                    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
                        try {
                            return new OrmliteListLoader(context, newsDao, newsDao.queryBuilder().prepare());
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {
                        if (data != null) {
                            eventBus.post(new FetchedNewsListEvent(data));
                        } else {
                            eventBus.post(new FetchedNewsListFailedEvent());
                        }
                    }

                    @Override
                    public void onLoaderReset(Loader<List<News>> loader) {
                    }
                }
        );
    }

    @Override
    public void populateFromAPI() {
        jobManager.addJob(new FetchNewsJob());
    }

    @Override
    public boolean isCacheValid() {
        try {
            return newsDao.isCacheValid();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
