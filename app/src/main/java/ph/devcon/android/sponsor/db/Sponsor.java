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

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import ph.devcon.android.base.db.BaseDevCon;
import ph.devcon.android.sponsor.api.SponsorAPI;

/**
 * Created by lope on 9/16/14.
 */
@DatabaseTable(daoClass = SponsorDaoImpl.class)
public class Sponsor extends BaseDevCon {

    @DatabaseField(dataType = DataType.BYTE_ARRAY)
    byte[] sponsorIcon;

    @DatabaseField
    String name;

    @DatabaseField
    String photoUrl;

    @DatabaseField
    String website;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, foreignAutoCreate = true)
    SponsorType sponsorType;

    public static Sponsor toSponsor(SponsorAPI sponsorAPI) {
        Sponsor sponsor = new Sponsor();
        sponsor.setName(sponsorAPI.getName());
        sponsor.setWebsite(sponsorAPI.getWebsite());
        sponsor.setPhotoUrl(sponsorAPI.getPhotoUrl());
        return sponsor;
    }

    public byte[] getSponsorIcon() {
        return sponsorIcon;
    }

    public void setSponsorIcon(byte[] sponsorIcon) {
        this.sponsorIcon = sponsorIcon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public SponsorType getSponsorType() {
        return sponsorType;
    }

    public void setSponsorType(SponsorType sponsorType) {
        this.sponsorType = sponsorType;
    }
}
