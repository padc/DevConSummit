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

package ph.devcon.android.program.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import ph.devcon.android.category.api.CategoryAPI;
import ph.devcon.android.speaker.api.SpeakerAPI;

/**
 * Created by lope on 10/26/2014.
 */

public class ProgramAPI {
    @SerializedName("start_at")
    @Expose
    private String startAt;
    @Expose
    private String title;
    @Expose
    private String description;
    @Expose
    private CategoryAPI category;
    @Expose
    private List<SpeakerAPI> speakers = new ArrayList<SpeakerAPI>();

    public String getStartAt() {
        return startAt;
    }

    public void setStartAt(String startAt) {
        this.startAt = startAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CategoryAPI getCategory() {
        return category;
    }

    public void setCategory(CategoryAPI category) {
        this.category = category;
    }

    public List<SpeakerAPI> getSpeakers() {
        return speakers;
    }

    public void setSpeakers(List<SpeakerAPI> speakers) {
        this.speakers = speakers;
    }

    public SpeakerAPI getMainSpeaker() {
        for (SpeakerAPI speaker : getSpeakers()) {
            return speaker;
        }
        return null;
    }

}
