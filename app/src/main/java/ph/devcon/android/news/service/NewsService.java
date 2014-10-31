package ph.devcon.android.news.service;

import android.app.LoaderManager;
import android.os.Bundle;

import java.util.List;

import ph.devcon.android.news.api.NewsBaseResponse;
import ph.devcon.android.news.db.News;

/**
 * Created by lope on 10/6/14.
 */
public interface NewsService {

    public List<News> createNewsList(NewsBaseResponse baseResponse);

    public void populateFromCache(LoaderManager loaderManager, Bundle savedInstanceState);

    public void populateFromAPI();

    public boolean isCacheValid();
}
