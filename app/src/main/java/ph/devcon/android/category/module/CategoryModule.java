package ph.devcon.android.category.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ph.devcon.android.base.DatabaseHelper;
import ph.devcon.android.category.db.CategoryDao;

/**
 * Created by lope on 10/29/14.
 */
@Module(library = true)
public class CategoryModule {
    Context mContext;

    public CategoryModule(Context context) {
        mContext = context;
    }

    @Provides
    @Singleton
    public CategoryDao provideCategoryDao() {
        return DatabaseHelper.getInstance(mContext).getCategoryDao();
    }
}