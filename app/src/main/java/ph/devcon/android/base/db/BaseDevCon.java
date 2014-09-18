package ph.devcon.android.base.db;

import com.j256.ormlite.field.DatabaseField;

import java.util.Date;

/**
 * Created by lope on 9/17/14.
 */
public abstract class BaseDevCon {

    @DatabaseField(generatedId = true)
    Integer id;

    @DatabaseField
    Date dateCreated;

    @DatabaseField
    Date dateUpdated;

    public BaseDevCon() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFormattedLogDate() {
        // TODO
        return "" + dateCreated;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }
}