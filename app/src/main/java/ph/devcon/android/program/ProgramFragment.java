package ph.devcon.android.program;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.applidium.headerlistview.HeaderListView;
import com.path.android.jobqueue.JobManager;

import java.sql.SQLException;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ph.devcon.android.DevConApplication;
import ph.devcon.android.R;
import ph.devcon.android.base.DatabaseHelper;
import ph.devcon.android.base.db.OrmliteListLoader;
import ph.devcon.android.program.adapter.ProgramSectionAdapter;
import ph.devcon.android.program.db.Program;
import ph.devcon.android.program.db.ProgramDao;
import ph.devcon.android.program.job.FetchProgramListJob;

/**
 * Created by lope on 9/16/14.
 */
public class ProgramFragment extends Fragment {

    @InjectView(R.id.lvw_programs)
    HeaderListView lvwPrograms;

    ProgramSectionAdapter programSectionAdapter;

    JobManager jobManager;
    ProgramDao programDao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_program, container, false);
        ButterKnife.inject(this, rootView);
        generatePrograms();
        programDao = DatabaseHelper.getInstance(DevConApplication.getInstance()).getProgramDao();
        if (programDao.isCacheValid()) {
            populateFromCache(savedInstanceState);
        } else {
            populateFromAPI();
        }
        return rootView;
    }

    protected void populateFromCache(Bundle savedInstanceState) {
        getLoaderManager().initLoader(0, savedInstanceState,
                new LoaderManager.LoaderCallbacks<List<Program>>() {
                    @Override
                    public Loader<List<Program>> onCreateLoader(int id, Bundle args) {
                        try {
                            return new OrmliteListLoader(getActivity(), programDao, programDao.queryBuilder().prepare());
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    public void onLoadFinished(Loader<List<Program>> loader, List<Program> data) {
                        setProgramList(data);
                    }

                    @Override
                    public void onLoaderReset(Loader<List<Program>> loader) {
                    }
                }
        );
    }

    protected void populateFromAPI() {
        jobManager = DevConApplication.getJobManager();
        jobManager.addJobInBackground(new FetchProgramListJob());
    }

    public void generatePrograms() {

    }

    public void setProgramList(List<Program> programList) {
        programSectionAdapter = new ProgramSectionAdapter(getActivity(), programList);
        lvwPrograms.setAdapter(programSectionAdapter);
    }

    public void onEventMainThread(FetchProgramListJob.FetchedProgramListEvent event) {
        setProgramList(event.programs);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        ((MainActivity) activity).onSectionAttached(
//                getArguments().getInt(ARG_SECTION_NUMBER));
    }
}
