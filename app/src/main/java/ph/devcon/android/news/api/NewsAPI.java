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

package ph.devcon.android.news.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ph.devcon.android.category.api.CategoryAPI;

/**
 * Created by lope on 10/31/14.
 */
public class NewsAPI {
    @Expose
    private String title;

    @SerializedName("photo_url")
    @Expose
    private String photoUrl;

    @SerializedName("html_content")
    @Expose
    private String htmlContent;

    @Expose
    private CategoryAPI category;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public CategoryAPI getCategory() {
        return category;
    }

    public void setCategory(CategoryAPI category) {
        this.category = category;
    }

}
