package ph.devcon.android.news.api;

import com.google.gson.annotations.Expose;

/**
 * Created by lope on 10/31/14.
 */
public class NewsAPIContainer {
    @Expose
    private NewsAPI news;

    public NewsAPI getNews() {
        return news;
    }

    public void setNews(NewsAPI news) {
        this.news = news;
    }

}
