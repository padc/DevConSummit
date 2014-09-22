package ph.devcon.android.program;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.applidium.headerlistview.HeaderListView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ph.devcon.android.R;
import ph.devcon.android.program.adapter.ProgramSectionAdapter;

/**
 * Created by lope on 9/16/14.
 */
public class ProgramFragment extends Fragment {

    @InjectView(R.id.lvw_programs)
    HeaderListView lvwPrograms;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_program, container, false);
        ButterKnife.inject(this, rootView);
        lvwPrograms.setAdapter(new ProgramSectionAdapter(getActivity()));
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        ((MainActivity) activity).onSectionAttached(
//                getArguments().getInt(ARG_SECTION_NUMBER));
    }
}
