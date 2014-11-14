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

package ph.devcon.android;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import dagger.ObjectGraph;
import ph.devcon.android.attendee.module.AttendeeModule;
import ph.devcon.android.auth.module.AuthModule;
import ph.devcon.android.base.module.APIModule;
import ph.devcon.android.category.module.CategoryModule;
import ph.devcon.android.news.module.NewsModule;
import ph.devcon.android.profile.module.ProfileModule;
import ph.devcon.android.program.module.ProgramModule;
import ph.devcon.android.settings.SettingsModule;
import ph.devcon.android.speaker.module.SpeakerModule;
import ph.devcon.android.sponsor.module.SponsorModule;
import ph.devcon.android.technology.module.TechnologyModule;
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

    public static void injectMembers(Object object) {
        getInstance().graph.inject(object);
    }

    @Override
    public void onCreate() {
        initFonts();
        graph = buildObjectGraph();
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
        objectList.add(new SettingsModule(this));
        objectList.add(new AuthModule(this));
        objectList.add(new APIModule(this));
        objectList.add(new ProgramModule(this));
        objectList.add(new SpeakerModule(this));
        objectList.add(new NewsModule(this));
        objectList.add(new AttendeeModule(this));
        objectList.add(new SponsorModule(this));
        objectList.add(new ProfileModule(this));
        objectList.add(new CategoryModule(this));
        objectList.add(new TechnologyModule(this));
        return objectList;
    }
}