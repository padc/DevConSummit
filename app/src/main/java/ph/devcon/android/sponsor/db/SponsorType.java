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

package ph.devcon.android.sponsor.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import ph.devcon.android.base.db.BaseDevCon;
import ph.devcon.android.sponsor.api.SponsorTypeAPI;
import ph.devcon.android.util.Util;

/**
 * Created by lope on 9/16/14.
 */
@DatabaseTable(daoClass = SponsorTypeDaoImpl.class)
public class SponsorType extends BaseDevCon {

    @DatabaseField
    String name;

    public static SponsorType toSponsorType(SponsorTypeAPI sponsorTypeAPI) {
        SponsorType sponsorType = null;
        if (!Util.isNullOrEmpty(sponsorTypeAPI.getName())) {
            sponsorType = new SponsorType();
            sponsorType.setName(sponsorTypeAPI.getName());
        }
        return sponsorType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
