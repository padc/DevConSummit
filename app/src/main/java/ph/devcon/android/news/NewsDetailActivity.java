package ph.devcon.android.news;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import butterknife.ButterKnife;
import ph.devcon.android.R;
import ph.devcon.android.navigation.BaseDevConActivity;
import ph.devcon.android.news.view.ObservableScrollView;

/**
 * Created by lope on 10/6/14.
 */
public class NewsDetailActivity extends BaseDevConActivity {

//    @InjectView(R.id.lvw_news)
//    ListView lvwNews;

    private TextView mStickyView;
    private View mPlaceholderView;
    private ObservableScrollView mObservableScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        FragmentManager fragmentManager = getFragmentManager();
//        fragmentManager.beginTransaction()
//                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
//                .commit();
//
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ButterKnife.inject(this);
//        lvwNews.setAdapter(new NewsAdapter(this, new ArrayList<News>()));
//        lvwNews.addFooterView(buildFooterView(inflater));
        setHomeAsUp();

        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, new NewsDetailFragment())
                .commit();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_news_details;
    }

    protected View buildFooterView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.footer_standard, null);
    }

}
