package ph.devcon.android.base.service;

import android.app.LoaderManager;
import android.os.Bundle;

import java.util.List;

import ph.devcon.android.base.db.BaseDevCon;

/**
 * Created by lope on 10/29/14.
 */
public interface BaseAPICacheService<CacheObject extends BaseDevCon, BaseResponse> {
    public List<CacheObject> createCacheObjects(BaseResponse baseResponse);

    public void populateFromCache(LoaderManager loaderManager, Bundle savedInstanceState);

    public void populateFromAPI();

    public boolean isCacheValid();
}
