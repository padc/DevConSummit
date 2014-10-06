package ph.devcon.android.program.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.applidium.headerlistview.SectionAdapter;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ph.devcon.android.R;
import ph.devcon.android.util.Util;

/**
 * Created by lope on 9/21/14.
 */
public class ProgramSectionAdapter extends SectionAdapter {

    Context mContext;

    public ProgramSectionAdapter(Context context) {
        mContext = context;
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
        if (section != 8) {
//        Program program = (Program) getRowItem(section, row);
            if (convertView != null) {
                holder = (ViewHolder) convertView.getTag();
            } else if (convertView == null) {
                convertView = inflater.inflate(mContext.getResources().getLayout(R.layout.item_program), null);
                holder = new ViewHolder(convertView);
            }
//        holder.txtSpeakerName.setText(program.getSpeaker().getName());
//        holder.imgSpeaker.setImageBitmap(program.getSpeaker().getSpeakerIconBitmap());
//        holder.txtProgramTitle.setText(program.getTitle());
        } else {
            convertView = inflater.inflate(R.layout.footer_standard, null);
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
        txtTime.setText(Util.toTime(section));
        if (section == 8) {
            convertView = inflater.inflate(mContext.getResources().getLayout(R.layout.item_header_null), null);
        }
//        ((TextView) convertView).setText("Section " + section + " Row " + row);
//        if (convertView == null) {
//            if (getSectionHeaderItemViewType(section) == 0) {
//                convertView = inflater.inflate(mContext.getResources().getLayout(android.R.layout.simple_list_item_1), null);
//            } else {
//                convertView = inflater.inflate(mContext.getResources().getLayout(android.R.layout.simple_list_item_2), null);
//            }
//        }
//
//        if (getSectionHeaderItemViewType(section) == 0) {
//            ((TextView) convertView).setText("Header for section " + section);
//        } else {
//            ((TextView) convertView.findViewById(android.R.id.text1)).setText("Header for section " + section);
//            ((TextView) convertView.findViewById(android.R.id.text2)).setText("Has a detail text field");
//        }

//        convertView.setBackgroundColor(mContext.getResources().getColor(android.R.color.holo_orange_light));
//        switch (section) {
//            case 0:
//                convertView.setBackgroundColor(mContext.getResources().getColor(android.R.color.holo_red_light));
//                break;
//            case 1:
//                break;
//            case 2:
//                convertView.setBackgroundColor(mContext.getResources().getColor(android.R.color.holo_green_light));
//                break;
//            case 3:
//                convertView.setBackgroundColor(mContext.getResources().getColor(android.R.color.holo_blue_light));
//                break;
//        }
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