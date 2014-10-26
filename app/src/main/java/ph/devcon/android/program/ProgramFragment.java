package ph.devcon.android.program;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.applidium.headerlistview.HeaderListView;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ph.devcon.android.R;
import ph.devcon.android.program.adapter.ProgramSectionAdapter;
import ph.devcon.android.program.api.Program;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;
import retrofit.http.POST;
import retrofit.http.Path;

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
        generatePrograms();
        return rootView;
    }

    public void generatePrograms() {
        int SIZE_OF_CACHE = 1024;
        OkHttpClient ok = new OkHttpClient();
        try {
            Cache responseCache = new Cache(getActivity().getCacheDir(), SIZE_OF_CACHE);
            ok.setCache(responseCache);
        } catch (Exception e) {
            Log.d("OkHttp", "Unable to set http cache", e);
        }
        ok.setReadTimeout(30, TimeUnit.SECONDS);
        ok.setConnectTimeout(30, TimeUnit.SECONDS);
        Executor executor = Executors.newCachedThreadPool();
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setExecutors(executor, executor)
                .setClient(new OkClient(ok))
                .setEndpoint("http://api.devcon.ph/api/v1/")
                .build();
        ProgramService programService = restAdapter.create(ProgramService.class);
        programService.listPrograms("test", new Callback<List<Program>>() {
            @Override
            public void success(List<Program> programs, Response response) {
                lvwPrograms.setAdapter(new ProgramSectionAdapter(getActivity(), programs));
            }

            @Override
            public void failure(RetrofitError retrofitError) {
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        ((MainActivity) activity).onSectionAttached(
//                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    public static interface ProgramService {
        @POST("/programs")
        void listPrograms(@Path("token") String token, Callback<List<Program>> callback);
    }
}
