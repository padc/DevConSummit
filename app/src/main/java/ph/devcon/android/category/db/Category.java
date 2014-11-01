package ph.devcon.android.category.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import ph.devcon.android.base.db.BaseDevCon;
import ph.devcon.android.category.api.CategoryAPI;

/**
 * Created by lope on 9/16/14.
 */
@DatabaseTable(daoClass = CategoryDaoImpl.class)
public class Category extends BaseDevCon {

    @DatabaseField
    String name;

    public static Category toCategory(CategoryAPI categoryAPI) {
        Category category = new Category();
        category.setName(categoryAPI.getName());
        return category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
