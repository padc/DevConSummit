package ph.devcon.android.profile.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import ph.devcon.android.base.db.BaseDevCon;
import ph.devcon.android.user.db.User;

/**
 * Created by lope on 11/1/2014.
 */
@DatabaseTable(daoClass = ProfileDaoImpl.class)
public class Profile extends BaseDevCon {
    @DatabaseField(foreign = true, foreignAutoRefresh = true, foreignAutoCreate = true)
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
