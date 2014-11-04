package ph.devcon.android.base.service;

import android.app.LoaderManager;
import android.os.Bundle;

/**
 * Created by lope on 10/29/14.
 */
public interface BaseAPICacheService<CacheObject, BaseResponse> {
    public CacheObject createCacheObject(BaseResponse baseResponse);

    public void populateFromCache(LoaderManager loaderManager, Bundle savedInstanceState);

    public void populateFromAPI();

    public boolean isCacheValid();
}