package ph.devcon.android.program.service;

import android.app.LoaderManager;
import android.os.Bundle;

import java.util.List;

import ph.devcon.android.program.api.ProgramBaseResponse;
import ph.devcon.android.program.db.Program;

/**
 * Created by lope on 10/29/14.
 */
public interface ProgramService {
    public List<Program> createPrograms(ProgramBaseResponse baseResponse);

    public void populateFromCache(LoaderManager loaderManager, Bundle savedInstanceState);

    public void populateFromAPI();

    public boolean isCacheValid();
}
