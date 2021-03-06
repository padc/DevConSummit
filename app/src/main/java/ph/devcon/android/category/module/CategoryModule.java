/*
 * Copyright (C) 2014 Philippine Android Developers Community
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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