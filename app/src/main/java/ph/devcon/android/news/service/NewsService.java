package ph.devcon.android.news.service;

import java.util.List;

import ph.devcon.android.base.service.BaseAPICacheService;
import ph.devcon.android.news.api.NewsBaseResponse;
import ph.devcon.android.news.db.News;

/**
 * Created by lope on 10/6/14.
 */
public interface NewsService extends BaseAPICacheService<List<News>, NewsBaseResponse> {
}
