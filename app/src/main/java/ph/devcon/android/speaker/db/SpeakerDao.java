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

package ph.devcon.android.speaker.db;

import com.j256.ormlite.stmt.PreparedQuery;

import java.sql.SQLException;

import ph.devcon.android.base.db.BaseDevConDao;

/**
 * Created by lope on 9/16/14.
 */
public interface SpeakerDao extends BaseDevConDao<Speaker, Integer> {
    public PreparedQuery<Speaker> getAll() throws SQLException;

    public PreparedQuery<Speaker> getSpeakers() throws SQLException;

    public PreparedQuery<Speaker> getPanels() throws SQLException;
}
