package ph.devcon.android;

import android.app.Application;
import android.util.Log;

import com.path.android.jobqueue.JobManager;
import com.path.android.jobqueue.config.Configuration;
import com.path.android.jobqueue.log.CustomLogger;

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

    private static JobManager jobManager;

    public DevConApplication() {
        instance = this;
    }

    public static DevConApplication getInstance() {
        return instance;
    }

    public static JobManager getJobManager() {
        return jobManager;
    }

    @Override
    public void onCreate() {
        initFonts();
        graph = buildObjectGraph();
        configureJobManager();
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

    private void configureJobManager() {
        Configuration configuration = new Configuration.Builder(this)
                .customLogger(new CustomLogger() {
                    private static final String TAG = "JOBS";

                    @Override
                    public boolean isDebugEnabled() {
                        return true;
                    }

                    @Override
                    public void d(String text, Object... args) {
                        Log.d(TAG, String.format(text, args));
                    }

                    @Override
                    public void e(Throwable t, String text, Object... args) {
                        Log.e(TAG, String.format(text, args), t);
                    }

                    @Override
                    public void e(String text, Object... args) {
                        Log.e(TAG, String.format(text, args));
                    }
                })
                .minConsumerCount(1)//always keep at least one consumer alive
                .maxConsumerCount(3)//up to 3 consumers at a time
                .loadFactor(3)//3 jobs per consumer
                .consumerKeepAlive(120)//wait 2 minute
                .build();
        jobManager = new JobManager(this, configuration);
    }
}