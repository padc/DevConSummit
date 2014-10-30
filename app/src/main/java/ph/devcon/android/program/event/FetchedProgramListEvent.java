package ph.devcon.android.program.event;

import java.util.List;

import ph.devcon.android.program.db.Program;

/**
 * Created by lope on 10/29/14.
 */
public class FetchedProgramListEvent {
    public List<Program> programs;

    public FetchedProgramListEvent(List<ph.devcon.android.program.db.Program> programs) {
        this.programs = programs;
    }
}
