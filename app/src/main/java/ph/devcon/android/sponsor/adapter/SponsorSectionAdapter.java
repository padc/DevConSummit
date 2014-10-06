package ph.devcon.android.sponsor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.applidium.headerlistview.SectionAdapter;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ph.devcon.android.R;
import ph.devcon.android.util.Util;

/**
 * Created by lope on 9/21/14.
 */
public class SponsorSectionAdapter extends SectionAdapter {

    Context mContext;

    public SponsorSectionAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int numberOfSections() {
        return 4;
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
//        Program program = (Program) getRowItem(section, row);
        if (convertView != null) {
            // related to a bug lol
//            holder = (ViewHolder) convertView.getTag();
        } else if (convertView == null) {
            convertView = inflater.inflate(mContext.getResources().getLayout(R.layout.item_sponsor), null);
//            holder = new ViewHolder(convertView);
        }
//        holder.txtSpeakerName.setText(program.getSpeaker().getName());
//        holder.imgSpeaker.setImageBitmap(program.getSpeaker().getSpeakerIconBitmap());
//        holder.txtProgramTitle.setText(program.getTitle());
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
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(mContext.getResources().getLayout(R.layout.item_header_sponsor), parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder.txtSponsorType.setText(Util.toSponsorType(section));
        return convertView;
    }

    @Override
    public void onRowItemClick(AdapterView<?> parent, View view, int section, int row, long id) {
        super.onRowItemClick(parent, view, section, row, id);
    }

    static class ViewHolder {
        @InjectView(R.id.txt_sponsor_type)
        TextView txtSponsorType;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}