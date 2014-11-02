package ph.devcon.android.category.db;

import com.google.common.base.Optional;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import ph.devcon.android.base.db.BaseDevCon;
import ph.devcon.android.category.api.CategoryAPI;
import ph.devcon.android.speaker.db.Speaker;
import ph.devcon.android.util.Util;

/**
 * Created by lope on 9/16/14.
 */
@DatabaseTable(daoClass = CategoryDaoImpl.class)
public class Category extends BaseDevCon {

    @DatabaseField
    String name;

    @DatabaseField(foreign = true)
    Speaker speaker;

    public static Category toCategory(CategoryAPI categoryAPI) {
        Category category = null;
        Optional<CategoryAPI> categoryAPIOptional = Optional.fromNullable(categoryAPI);
        if (categoryAPIOptional.isPresent()) {
            String categoryName = categoryAPIOptional.get().getName();
            if (!Util.isNullOrEmpty(categoryName)) {
                category = new Category();
                category.setName(categoryAPIOptional.get().getName());
            }
        }
        return category;
    }

    public static Category toCategory(String categoryName) {
        Category category = null;
        if (!Util.isNullOrEmpty(categoryName)) {
            category = new Category();
            category.setName(categoryName);
        }
        return category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Speaker getSpeaker() {
        return speaker;
    }

    public void setSpeaker(Speaker speaker) {
        this.speaker = speaker;
    }
}
