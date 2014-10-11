package ph.devcon.android.attendee.db;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by lope on 10/12/14.
 */
public class SocialLink {
    public static final int TWITTER = 0;
    public static final int FACEBOOK = 0;

    @DatabaseField(index = true)
    int type;
    @DatabaseField(index = true)
    String title;
    @DatabaseField(index = true)
    String uri;

    @DatabaseField(foreign = true)
    Attendee attendee;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
