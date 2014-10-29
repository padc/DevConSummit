package ph.devcon.android.program.service;

import com.google.common.base.Optional;
import com.j256.ormlite.dao.ForeignCollection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ph.devcon.android.program.api.ProgramAPI;
import ph.devcon.android.program.api.ProgramAPIContainer;
import ph.devcon.android.program.api.ProgramBaseResponse;
import ph.devcon.android.program.db.Program;
import ph.devcon.android.program.db.ProgramDao;
import ph.devcon.android.speaker.api.SpeakerAPI;
import ph.devcon.android.speaker.db.Speaker;
import ph.devcon.android.speaker.db.SpeakerDao;

/**
 * Created by lope on 10/29/14.
 */
public class ProgramServiceImpl implements ProgramService {

    @Inject
    ProgramDao programDao;

    @Inject
    SpeakerDao speakerDao;

    public ProgramServiceImpl(ProgramDao programDao, SpeakerDao speakerDao) {
        this.programDao = programDao;
        this.speakerDao = speakerDao;
    }

    public List<Program> createPrograms(ProgramBaseResponse baseResponse) {
        try {
            programDao.clear();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Optional<ProgramBaseResponse> baseResponseOptional = Optional.of(baseResponse);
        ProgramBaseResponse programBaseResponse = baseResponseOptional.get();
        List<Program> programsDBList = new ArrayList<Program>();
        for (ProgramAPIContainer container : programBaseResponse.getPrograms()) {
            try {
                ProgramAPI programAPI = container.getProgram();
                Program programDb = Program.toProgram(programAPI);
                ForeignCollection<Speaker> speakers = programDao.getEmptyForeignCollection("speakers");
                for (SpeakerAPI speakerAPI : programAPI.getSpeakers())
                    speakers.add(Speaker.toSpeaker(speakerAPI));
                programDb.setSpeakers(speakers);
                programDao.create(programDb);
                programsDBList.add(programDb);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return programsDBList;
    }

}