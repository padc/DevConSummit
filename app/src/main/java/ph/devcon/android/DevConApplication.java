package ph.devcon.android;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import dagger.ObjectGraph;
import ph.devcon.android.auth.module.AuthModule;
import ph.devcon.android.util.TypeFaceUtil;

/**
 * Created by lope on 9/13/14.
 */
public class DevConApplication extends Application {
    public static final String SOURCESANSPRO_SEMIBOLD = "SANS_SERIF";
    public static final String SOURCESANSPRO_REGULAR = "SERIF";
    public static final String PTSERIF_ITALIC = "MONOSPACE";
    public static final String API_ENDPOINT = "http://api.devcon.ph/api/v1/";
    static DevConApplication instance;
    ObjectGraph graph;

    public DevConApplication() {
        instance = this;
    }

    public static DevConApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        initFonts();
        graph = buildObjectGraph();
    }

    public static void injectMembers(Object object) {
        getInstance().graph.inject(object);
    }

    private void initFonts() {
        TypeFaceUtil.overrideFont(getApplicationContext(), SOURCESANSPRO_SEMIBOLD, "fonts/SourceSansPro-Semibold.otf");
        TypeFaceUtil.overrideFont(getApplicationContext(), SOURCESANSPRO_REGULAR, "fonts/SourceSansPro-Regular.otf");
        TypeFaceUtil.overrideFont(getApplicationContext(), PTSERIF_ITALIC, "fonts/pt-serif.italic.ttf");
    }

    protected ObjectGraph buildObjectGraph() {
        List<Object> objectList = new ArrayList<Object>();
        objectList.addAll(getModules());
        return ObjectGraph.create(objectList.toArray());
    }

    protected List<Object> getModules() {
        List<Object> objectList = new ArrayList<Object>();
        objectList.add(new AuthModule(this));
        return objectList;
    }
}