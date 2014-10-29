package ph.devcon.android.program.service;

import java.util.List;

import ph.devcon.android.program.api.ProgramBaseResponse;
import ph.devcon.android.program.db.Program;

/**
 * Created by lope on 10/29/14.
 */
public interface ProgramService {
    public List<Program> createPrograms(ProgramBaseResponse baseResponse);
}
