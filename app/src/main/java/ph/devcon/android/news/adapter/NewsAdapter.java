package ph.devcon.android.news.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.common.base.Optional;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ph.devcon.android.R;
import ph.devcon.android.category.db.Category;
import ph.devcon.android.news.db.News;
import ph.devcon.android.util.Util;

/**
 * Created by lope on 10/6/14.
 */
public class NewsAdapter extends ArrayAdapter<News> {
    Context mContext;
    List<News> mNewsList;

    public NewsAdapter(Context context, List<News> newsList) {
        super(context, R.layout.item_news, newsList);
        mContext = context;
        mNewsList = newsList;
    }

    public List<News> getItems() {
        return mNewsList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.item_news, parent, false);
            holder = new ViewHolder(convertView, mContext);
            convertView.setTag(holder);
        }
        News newsItem = getItem(position);
        holder.txtTitle.setText(newsItem.getTitle());
        holder.txtPreview.setText(Util.stripHtml(newsItem.getHtmlContent()));
        Optional<Category> categoryOptional = Optional.fromNullable(newsItem.getCategory());
        if (categoryOptional.isPresent())
            holder.txtTag.setText(categoryOptional.get().getName());
        if (!Util.isNullOrEmpty(newsItem.getPhotoUrl())) {
            Picasso.with(mContext).setIndicatorsEnabled(true);
            Picasso.with(mContext).load(newsItem.getPhotoUrl()).into(holder.imgIcon);
        }
        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.img_news)
        ImageView imgIcon;
        @InjectView(R.id.txt_tag)
        TextView txtTag;
        @InjectView(R.id.txt_title)
        TextView txtTitle;
        @InjectView(R.id.txt_preview)
        TextView txtPreview;

        public ViewHolder(View view, Context context) {
            ButterKnife.inject(this, view);
        }
    }
}
