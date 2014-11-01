package ph.devcon.android.news.db;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import ph.devcon.android.base.db.BaseDevCon;
import ph.devcon.android.category.db.Category;
import ph.devcon.android.news.api.NewsAPI;

/**
 * Created by lope on 10/6/14.
 */
@DatabaseTable(daoClass = NewsDaoImpl.class)
public class News extends BaseDevCon {

    @DatabaseField(index = true)
    String title;

    @DatabaseField(index = true)
    String htmlContent;

    @DatabaseField
    String photoUrl;

    @DatabaseField(dataType = DataType.BYTE_ARRAY)
    byte[] image;

    @DatabaseField(dataType = DataType.BYTE_ARRAY)
    byte[] imagePreview;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, foreignAutoCreate = true)
    Category category;

    public static News toNews(NewsAPI newsAPI) {
        News news = new News();
        news.setTitle(newsAPI.getTitle());
        news.setHtmlContent(newsAPI.getHtmlContent());
        news.setPhotoUrl(newsAPI.getPhotoUrl());
        news.setCategory(Category.toCategory(newsAPI.getCategory()));
        return news;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
