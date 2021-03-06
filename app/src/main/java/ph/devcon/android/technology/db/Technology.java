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

package ph.devcon.android.technology.db;

import com.github.slugify.Slugify;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import ph.devcon.android.base.db.BaseDevCon;
import ph.devcon.android.user.db.User;

/**
 * Created by lope on 10/12/14.
 */
@DatabaseTable(daoClass = TechnologyDaoImpl.class)
public class Technology extends BaseDevCon {

    @DatabaseField
    String code;

    @DatabaseField
    String title;

    @DatabaseField(foreign = true)
    User user;

    public static Technology toTechnology(User user, String title) {
        Technology technology = new Technology();
        Slugify slugger = new Slugify();
        technology.setCode(slugger.slugify(title));
        technology.setTitle(title);
        technology.setUser(user);
        return technology;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
