package ph.devcon.android.news.api;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lope on 10/31/14.
 */
public class NewsBaseResponse {
    @Expose
    private List<NewsAPIContainer> news = new ArrayList<NewsAPIContainer>();

    public List<NewsAPIContainer> getNews() {
        return news;
    }

    public void setNews(List<NewsAPIContainer> news) {
        this.news = news;
    }
}
