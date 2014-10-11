package ph.devcon.android.attendee.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import ph.devcon.android.base.db.BaseDevCon;

/**
 * Created by lope on 10/12/14.
 */
@DatabaseTable(daoClass = TechnologyDaoImpl.class)
public class Technology extends BaseDevCon {

    @DatabaseField
    String code;

    @DatabaseField
    String title;

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
}
