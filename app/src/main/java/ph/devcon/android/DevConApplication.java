package ph.devcon.android;

import android.app.Application;

import ph.devcon.android.util.TypeFaceUtil;

/**
 * Created by lope on 9/13/14.
 */
public class DevConApplication extends Application {
    public static final String SOURCESANSPRO_SEMIBOLD = "SANS_SERIF";
    public static final String SOURCESANSPRO_REGULAR = "SERIF";
    public static final String PTSERIF_ITALIC = "MONOSPACE";

    @Override
    public void onCreate() {
        TypeFaceUtil.overrideFont(getApplicationContext(), SOURCESANSPRO_SEMIBOLD, "fonts/SourceSansPro-Semibold.otf");
        TypeFaceUtil.overrideFont(getApplicationContext(), SOURCESANSPRO_REGULAR, "fonts/SourceSansPro-Regular.otf");
        TypeFaceUtil.overrideFont(getApplicationContext(), PTSERIF_ITALIC, "fonts/pt-serif.italic.ttf");
    }
}