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

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import ph.devcon.android.base.db.BaseDevCon;
import ph.devcon.android.util.Util;

/**
 * Created by lope on 11/1/2014.
 */
@DatabaseTable(daoClass = TalkDaoImpl.class)
public class Talk extends BaseDevCon {
    @DatabaseField
    String name;

    @DatabaseField(foreign = true)
    Speaker speaker;

    public static Talk toTalk(String talkName) {
        Talk talk = null;
        if (!Util.isNullOrEmpty(talkName)) {
            talk = new Talk();
            talk.setName(talkName);
        }
        return talk;
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
