package ph.devcon.android.news.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import ph.devcon.android.base.db.BaseDevCon;

/**
 * Created by lope on 10/6/14.
 */
@DatabaseTable(daoClass = NewsDaoImpl.class)
public class News extends BaseDevCon {

    @DatabaseField(index = true)
    String title;

    @DatabaseField(index = true)
    String body;

    @DatabaseField
    byte[] image;

    @DatabaseField
    byte[] imagePreview;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    Tag tag;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public byte[] getImagePreview() {
        return imagePreview;
    }

    public void setImagePreview(byte[] imagePreview) {
        this.imagePreview = imagePreview;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }
}
