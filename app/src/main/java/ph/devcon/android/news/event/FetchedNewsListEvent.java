package ph.devcon.android.news.event;

import java.util.List;

import ph.devcon.android.news.db.News;

/**
 * Created by lope on 10/29/14.
 */
public class FetchedNewsListEvent {
    public List<News> newsList;

    public FetchedNewsListEvent(List<News> newsList) {
        this.newsList = newsList;
    }
}
