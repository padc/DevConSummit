package ph.devcon.android.program.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lope on 10/29/14.
 */
public class ProgramBaseResponse {
    @Expose
    private List<ProgramAPIContainer> programs = new ArrayList<ProgramAPIContainer>();
    @SerializedName("status_code")
    @Expose
    private Integer statusCode;

    public List<ProgramAPIContainer> getPrograms() {
        return programs;
    }

    public void setPrograms(List<ProgramAPIContainer> programs) {
        this.programs = programs;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

}