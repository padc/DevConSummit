package ph.devcon.android.news.job;

import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;
import ph.devcon.android.news.api.NewsBaseResponse;
import ph.devcon.android.news.controller.NewsController;
import ph.devcon.android.news.db.News;
import ph.devcon.android.news.event.FetchedNewsListEvent;
import ph.devcon.android.news.event.FetchedNewsListFailedEvent;
import ph.devcon.android.news.service.NewsService;
import ph.devcon.android.program.job.Priority;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by lope on 10/31/14.
 */
public class FetchNewsJob extends Job {
    private static final AtomicInteger jobCounter = new AtomicInteger(0);

    private final int id;

    @Inject
    RestAdapter restAdapter;

    @Inject
    NewsService newsService;

    @Inject
    EventBus eventBus;

    public FetchNewsJob() {
        super(new Params(Priority.MID).requireNetwork());
        id = jobCounter.incrementAndGet();
    }

    @Override
    public void onAdded() {
    }

    @Override
    public void onRun() throws Throwable {
        if (id != jobCounter.get()) {
            //looks like other fetch jobs has been added after me. no reason to keep fetching
            //many times, cancel me, let the other one fetch tweets.
            return;
        }
        NewsController newsController = restAdapter.create(NewsController.class);
        newsController.fetchNews(new Callback<NewsBaseResponse>() {
            @Override
            public void success(NewsBaseResponse baseResponse, Response response) {
                List<News> newsDbList = newsService.createNewsList(baseResponse);
                eventBus.post(new FetchedNewsListEvent(newsDbList));
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                eventBus.post(new FetchedNewsListFailedEvent());
            }
        });
    }

    @Override
    protected void onCancel() {
        //TODO show error notification
    }

    @Override
    protected boolean shouldReRunOnThrowable(Throwable throwable) {
        if (throwable instanceof SQLException) {
            // if database error then stop
            return false;
        }
        return true;
    }
}
