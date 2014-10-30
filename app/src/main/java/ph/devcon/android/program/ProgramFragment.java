package ph.devcon.android.program;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.applidium.headerlistview.HeaderListView;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.greenrobot.event.EventBus;
import ph.devcon.android.DevConApplication;
import ph.devcon.android.R;
import ph.devcon.android.program.adapter.ProgramSectionAdapter;
import ph.devcon.android.program.db.Program;
import ph.devcon.android.program.event.FetchedProgramListEvent;
import ph.devcon.android.program.service.ProgramService;

/**
 * Created by lope on 9/16/14.
 */
public class ProgramFragment extends Fragment {

    @InjectView(R.id.lvw_programs)
    HeaderListView lvwPrograms;

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
        eventBus.register(this);
        if (programService.isCacheValid()) {
            programService.populateFromCache(getLoaderManager(), savedInstanceState);
        } else {
            programService.populateFromAPI();
        }
        return rootView;
    }

    public void setProgramList(List<Program> programList) {
        if (programList != null && !programList.isEmpty()) {
            programSectionAdapter = new ProgramSectionAdapter(getActivity(), programList);
            lvwPrograms.setAdapter(programSectionAdapter);
        }
    }

    public void onEventMainThread(FetchedProgramListEvent event) {
        setProgramList(event.programs);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        ((MainActivity) activity).onSectionAttached(
//                getArguments().getInt(ARG_SECTION_NUMBER));
    }
}
