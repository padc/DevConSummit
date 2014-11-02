package ph.devcon.android.speaker.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ph.devcon.android.R;
import ph.devcon.android.speaker.db.Speaker;
import ph.devcon.android.util.Util;

/**
 * Created by lope on 9/17/14.
 */
public class SpeakerAdapter extends ArrayAdapter<Speaker> {
    boolean isShouldDisplayTalkTitle = true;

    public SpeakerAdapter(Context context, List<Speaker> logs, boolean isShouldDisplayTalkTitle) {
        super(context, R.layout.item_program, logs);
        this.isShouldDisplayTalkTitle = isShouldDisplayTalkTitle;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Speaker speaker = getItem(position);
        LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
        ViewHolder holder = null;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.item_speaker, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        if (isShouldDisplayTalkTitle)
            holder.txtProgramTitle.setText(speaker.getTalkTitle());
        else
            holder.txtProgramTitle.setText(speaker.getPanelTitle());
        holder.txtSpeakerName.setText(speaker.getFullName());
        holder.txtPosition.setText(speaker.getPosition());
        if (!Util.isNullOrEmpty(speaker.getPhotoUrl())) {
            Picasso.with(getContext()).load(speaker.getPhotoUrl()).into(holder.imgSpeaker);
        }
        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.img_speaker)
        ImageView imgSpeaker;
        @InjectView(R.id.txt_speaker_name)
        TextView txtSpeakerName;
        @InjectView(R.id.txt_position)
        TextView txtPosition;
        @InjectView(R.id.txt_program_title)
        TextView txtProgramTitle;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
