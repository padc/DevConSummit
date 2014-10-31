package ph.devcon.android.news.controller;

import ph.devcon.android.news.api.NewsBaseResponse;
import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by lope on 10/29/14.
 * <p/>
 */
public interface NewsController {
    @GET("/news")
    void fetchNews(Callback<NewsBaseResponse> callback);
}
