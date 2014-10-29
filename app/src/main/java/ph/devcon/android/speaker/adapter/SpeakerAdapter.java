package ph.devcon.android.speaker.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ph.devcon.android.R;
import ph.devcon.android.speaker.db.Speaker;

/**
 * Created by lope on 9/17/14.
 */
public class SpeakerAdapter extends ArrayAdapter<Speaker> {
    public SpeakerAdapter(Context context, List<Speaker> logs) {
        super(context, R.layout.item_program, logs);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Speaker speaker = getItem(position);
        LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_speaker, parent, false);
            TextView txtSpeakerName = (TextView) convertView.findViewById(R.id.txt_speaker_name);
            txtSpeakerName.setText(speaker.getFullName());
        }
        return convertView;
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
