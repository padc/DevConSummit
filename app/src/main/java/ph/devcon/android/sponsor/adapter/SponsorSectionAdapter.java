package ph.devcon.android.sponsor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.applidium.headerlistview.SectionAdapter;
import com.google.common.collect.ArrayListMultimap;
import com.squareup.picasso.Picasso;

import org.apmem.tools.layouts.FlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ph.devcon.android.R;
import ph.devcon.android.sponsor.db.Sponsor;

/**
 * Created by lope on 9/21/14.
 */
public class SponsorSectionAdapter extends SectionAdapter {

    Context mContext;
    ArrayListMultimap<String, Sponsor> mSponsorMultimap = ArrayListMultimap.create();
    List<String> mSponsorTypeKeyList = new ArrayList<String>();

    public SponsorSectionAdapter(Context context, ArrayListMultimap<String, Sponsor> sponsorMultimap) {
        mContext = context;
        mSponsorMultimap = sponsorMultimap;
        for (String key : mSponsorMultimap.keySet()) {
            mSponsorTypeKeyList.add(key);
        }
    }

    @Override
    public int numberOfSections() {
        return mSponsorMultimap.keySet().size();
    }

    @Override
    public int numberOfRows(int section) {
        return 1;
    }

    @Override
    public Object getRowItem(int section, int row) {
        return mSponsorMultimap.get(mSponsorTypeKeyList.get(section));
    }

    @Override
    public boolean hasSectionHeaderView(int section) {
        return true;
    }

    @Override
    public View getRowView(int section, int row, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder holder = null;
        List<Sponsor> sponsors = (List<Sponsor>) getRowItem(section, row);
        if (convertView != null) {
            // related to a bug lol
//            holder = (ViewHolder) convertView.getTag();
        } else if (convertView == null) {
            convertView = inflater.inflate(mContext.getResources().getLayout(R.layout.item_sponsor), null);
//            holder = new ViewHolder(convertView);
//            convertView.setTag(holder);
        }
        FlowLayout contSponsorImages = (FlowLayout) convertView.findViewById(R.id.cont_sponsor_images);
        for (Sponsor sponsor : sponsors) {
            ImageView imageView = new ImageView(mContext);
            Picasso.with(mContext).load(sponsor.getPhotoUrl()).into(imageView);
            contSponsorImages.addView(imageView);
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
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(mContext.getResources().getLayout(R.layout.item_header_sponsor), parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder.txtSponsorType.setText(mSponsorTypeKeyList.get(section));
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