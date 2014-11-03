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
