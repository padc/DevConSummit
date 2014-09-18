package ph.devcon.android.sponsor.db;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

import ph.devcon.android.base.db.BaseDevCon;

/**
 * Created by lope on 9/16/14.
 */
public class Sponsor extends BaseDevCon {
    public static final int TYPE_GOLD = 0;
    public static final int TYPE_SILVER = 1;
    public static final int TYPE_CO_PRESENTOR = 2;

    @DatabaseField
    Integer sponsorType;
    @DatabaseField
    String sponsorName;
    @DatabaseField(dataType = DataType.BYTE_ARRAY)
    byte[] sponsorIcon;
    @DatabaseField
    String sponsorLink;

    public Integer getSponsorType() {
        return sponsorType;
    }

    public void setSponsorType(Integer sponsorType) {
        this.sponsorType = sponsorType;
    }

    public String getSponsorName() {
        return sponsorName;
    }

    public void setSponsorName(String sponsorName) {
        this.sponsorName = sponsorName;
    }

    public byte[] getSponsorIcon() {
        return sponsorIcon;
    }

    public void setSponsorIcon(byte[] sponsorIcon) {
        this.sponsorIcon = sponsorIcon;
    }

    public String getSponsorLink() {
        return sponsorLink;
    }

    public void setSponsorLink(String sponsorLink) {
        this.sponsorLink = sponsorLink;
    }
}
