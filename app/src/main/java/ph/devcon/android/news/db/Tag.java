package ph.devcon.android.news.db;

import com.j256.ormlite.field.DatabaseField;

import ph.devcon.android.base.db.BaseDevCon;

/**
 * Created by lope on 10/6/14.
 */
public class Tag extends BaseDevCon {
    @DatabaseField(index = true)
    String tagTitle;

    public String getTagTitle() {
        return tagTitle;
    }

    public void setTagTitle(String tagTitle) {
        this.tagTitle = tagTitle;
    }
}
