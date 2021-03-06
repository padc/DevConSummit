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

package ph.devcon.android.program;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nhaarman.listviewanimations.appearance.StickyListHeadersAdapterDecorator;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.greenrobot.event.EventBus;
import ph.devcon.android.DevConApplication;
import ph.devcon.android.R;
import ph.devcon.android.navigation.BaseDevConActivity;
import ph.devcon.android.navigation.MainActivity;
import ph.devcon.android.program.adapter.ProgramSectionAdapter;
import ph.devcon.android.program.db.Program;
import ph.devcon.android.program.event.FetchedProgramListEvent;
import ph.devcon.android.program.service.ProgramService;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * Created by lope on 9/16/14.
 */
public class ProgramFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    @InjectView(R.id.swp_programs)
    SwipeRefreshLayout swipeLayout;

    @InjectView(R.id.lvw_programs)
    StickyListHeadersListView lvwPrograms;

    ProgramSectionAdapter programSectionAdapter;

    @Inject
    EventBus eventBus;

    @Inject
    ProgramService programService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_program, container, false);
        DevConApplication.injectMembers(this);
        ButterKnife.inject(this, rootView);
        initAnimation();
        initSwipeLayout();
        if (!eventBus.isRegistered(this)) {
            eventBus.register(this);
        }
        if (programService.isCacheValid()) {
            programService.populateFromCache(getLoaderManager(), savedInstanceState);
        } else {
            programService.populateFromAPI();
        }
        return rootView;
    }

    protected void initAnimation() {
        List<Program> programList = new ArrayList<Program>();
        programSectionAdapter = new ProgramSectionAdapter(getActivity(), programList);
        SwingBottomInAnimationAdapter animationAdapter = new SwingBottomInAnimationAdapter(programSectionAdapter);
        StickyListHeadersAdapterDecorator stickyListHeadersAdapterDecorator = new StickyListHeadersAdapterDecorator(animationAdapter);
        stickyListHeadersAdapterDecorator.setStickyListHeadersListView(lvwPrograms);
        lvwPrograms.setAdapter(stickyListHeadersAdapterDecorator);
    }

    protected void initSwipeLayout() {
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeResources(R.color.yellow,
                R.color.orange,
                R.color.purple,
                R.color.blue);
        swipeLayout.setRefreshing(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        eventBus.unregister(this);
    }

    public void setProgramList(List<Program> programList) {
        if (programList != null) {
            programSectionAdapter.setItems(programList);
            programSectionAdapter.notifyDataSetChanged();
        }
        swipeLayout.setRefreshing(false);
    }

    public void onEventMainThread(FetchedProgramListEvent event) {
        setProgramList(event.programs);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(BaseDevConActivity.PlaceholderFragment.ARG_SECTION_NUMBER));
    }

    @Override
    public void onRefresh() {
        programService.populateFromAPI();
    }
}
