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

import com.google.common.base.Optional;
import com.j256.ormlite.stmt.PreparedDelete;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import ph.devcon.android.base.db.BaseDevConDaoImpl;
import ph.devcon.android.user.db.User;

/**
 * Created by lope on 10/12/14.
 */
public class TechnologyDaoImpl extends BaseDevConDaoImpl<Technology, Integer> implements TechnologyDao {
    public TechnologyDaoImpl(ConnectionSource connectionSource, Class<Technology> clazz) throws SQLException {
        super(connectionSource, clazz);
    }

    @Override
    public void clear() throws SQLException {
        TableUtils.clearTable(getConnectionSource(), Technology.class);
    }


    @Override
    public boolean isCacheValid() throws SQLException {
        return queryForFirst(queryBuilder().prepare()) != null;
    }

    /**
     * remove existing technologies and reattach new ones
     * @param user
     * @throws SQLException
     */
    @Override
    public void updateOrCreateUserTechnologies(User user) throws SQLException {
        // clear technologies attached to user
        Where<Technology, Integer> where = deleteBuilder().where();
        where = where.eq("user_id", user.getId());
        PreparedDelete<Technology> preparedDelete =
                (PreparedDelete<Technology>) where.prepare();
        delete(preparedDelete);

        // create technologies
        for (Technology technology : user.getOtherTechnologies()) {
            create(technology);
        }
        create(user.getPrimaryTechnology());
    }
}
