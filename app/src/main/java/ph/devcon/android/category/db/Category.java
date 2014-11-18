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

package ph.devcon.android.category.db;

import com.google.common.base.Optional;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import ph.devcon.android.base.db.BaseDevCon;
import ph.devcon.android.category.api.CategoryAPI;
import ph.devcon.android.speaker.db.Speaker;
import ph.devcon.android.util.Util;

/**
 * Created by lope on 9/16/14.
 */
@DatabaseTable(daoClass = CategoryDaoImpl.class)
public class Category extends BaseDevCon {
    public static final String SPEAKERS = "Speakers";
    public static final String PROMOS = "Promos";
    public static final String PROGRAM = "Program";

    @DatabaseField
    String name;

    @DatabaseField(foreign = true)
    Speaker speaker;

    public static Category toCategory(CategoryAPI categoryAPI) {
        Category category = null;
        Optional<CategoryAPI> categoryAPIOptional = Optional.fromNullable(categoryAPI);
        if (categoryAPIOptional.isPresent()) {
            String categoryName = categoryAPIOptional.get().getName();
            if (!Util.isNullOrEmpty(categoryName)) {
                category = new Category();
                category.setName(categoryAPIOptional.get().getName());
            }
        }
        return category;
    }

    public static Category toCategory(String categoryName) {
        Category category = null;
        if (!Util.isNullOrEmpty(categoryName)) {
            category = new Category();
            category.setName(categoryName);
        }
        return category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Speaker getSpeaker() {
        return speaker;
    }

    public void setSpeaker(Speaker speaker) {
        this.speaker = speaker;
    }
}
