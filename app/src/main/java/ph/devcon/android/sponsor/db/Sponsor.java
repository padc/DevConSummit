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

    @DatabaseField(foreignAutoRefresh = true, foreignAutoCreate = true)
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
