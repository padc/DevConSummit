package ph.devcon.android.program.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.applidium.headerlistview.SectionAdapter;
import com.google.common.base.Optional;
import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ph.devcon.android.R;
import ph.devcon.android.program.db.Program;
import ph.devcon.android.speaker.db.Speaker;

/**
 * Created by lope on 9/21/14.
 */
public class ProgramSectionAdapter extends SectionAdapter {

    Context mContext;
    List<Program> programList;
    HashMap<Integer, Program> programHashMap;

    public ProgramSectionAdapter(Context context, List<Program> programList) {
        mContext = context;
        this.programList = programList;
        buildProgramMap();
    }

    public void buildProgramMap() {
        programHashMap = Maps.newHashMap();
        int counter = 0;
        for (Program program : programList) {
            programHashMap.put(counter, program);
        }
    }

    @Override
    public int numberOfSections() {
        return 9;
    }

    @Override
    public int numberOfRows(int section) {
        return 1;
    }

    @Override
    public Object getRowItem(int section, int row) {
        return null;
    }

    @Override
    public boolean hasSectionHeaderView(int section) {
        return true;
    }

    @Override
    public View getRowView(int section, int row, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder holder = null;
        if (section != programHashMap.size()) {
            Program program = programHashMap.get(section);
            if (convertView != null) {
                // related to a bug lol
                View sponsorView = convertView.findViewById(R.id.cont_sponsors);
                if (sponsorView == null)
                    holder = (ViewHolder) convertView.getTag();
                else {
                    convertView = inflater.inflate(mContext.getResources().getLayout(R.layout.item_program), null);
                    holder = new ViewHolder(convertView);
                }
            } else if (convertView == null) {
                convertView = inflater.inflate(mContext.getResources().getLayout(R.layout.item_program), null);
                holder = new ViewHolder(convertView);
            }
            holder.txtProgramTitle.setText(program.getPosition());
            Optional<Speaker> speakerOptional = Optional.fromNullable(program.getMainSpeaker());
            if (speakerOptional.isPresent()) {
                Speaker speaker = speakerOptional.get();
                // get speaker image
                // Picasso.with(mContext).load(speaker.get)
                holder.txtSpeakerName.setText(speaker.getName());
            }
        } else {
            return inflater.inflate(R.layout.footer_standard, null);
        }
        return convertView;
    }

    @Override
    public int getSectionHeaderViewTypeCount() {
        return 2;
    }

    @Override
    public int getSectionHeaderItemViewType(int section) {
        return section % 2;
    }

    @Override
    public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(mContext.getResources().getLayout(R.layout.item_header_program), null);
        if (convertView == null) {
            convertView = inflater.inflate(mContext.getResources().getLayout(R.layout.item_header_program), null);
        }
        TextView txtTime = (TextView) convertView.findViewById(R.id.txt_time);
        Program program = programHashMap.get(section);
        txtTime.setText(program.getStartAt());
        if (section == programHashMap.size()) {
            convertView = inflater.inflate(mContext.getResources().getLayout(R.layout.item_header_null), null);
        }
        return convertView;
    }

    @Override
    public void onRowItemClick(AdapterView<?> parent, View view, int section, int row, long id) {
        super.onRowItemClick(parent, view, section, row, id);
    }

    static class ViewHolder {
        @InjectView(R.id.img_speaker)
        ImageView imgSpeaker;
        @InjectView(R.id.txt_speaker_name)
        TextView txtSpeakerName;
        @InjectView(R.id.txt_program_title)
        TextView txtProgramTitle;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}