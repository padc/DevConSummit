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
