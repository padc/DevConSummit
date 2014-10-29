package ph.devcon.android.program.api;

import com.google.gson.annotations.Expose;

/**
 * Created by lope on 10/29/14.
 */
public class ProgramAPIContainer {
    @Expose
    private ProgramAPI program;

    public ProgramAPI getProgram() {
        return program;
    }

    public void setProgram(ProgramAPI program) {
        this.program = program;
    }
}