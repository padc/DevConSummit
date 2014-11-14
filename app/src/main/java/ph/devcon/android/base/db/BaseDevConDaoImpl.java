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

package ph.devcon.android.base.db;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.Date;

/**
 * Created by lope on 9/17/14.
 */
public abstract class BaseDevConDaoImpl<T extends BaseDevCon, ID> extends BaseDaoImpl<T, ID> implements BaseDevConDao<T, ID> {

    public BaseDevConDaoImpl(ConnectionSource connectionSource, Class<T> clazz)
            throws SQLException {
        super(connectionSource, clazz);
    }

    @Override
    public int create(T baseDevCon) throws SQLException {
        baseDevCon.setDateCreated(new Date());
        int count = super.create(baseDevCon);
        return count;
    }

    @Override
    public int update(T baseDevCon) throws SQLException {
        baseDevCon.setDateUpdated(new Date());
        int count = super.update(baseDevCon);
        return count;
    }

}