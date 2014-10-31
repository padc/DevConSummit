package ph.devcon.android.news.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ph.devcon.android.program.api.Category;

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
    private Category category;

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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}
