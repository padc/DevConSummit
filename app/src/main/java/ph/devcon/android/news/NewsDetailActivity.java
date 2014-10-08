package ph.devcon.android.news;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import butterknife.ButterKnife;
import ph.devcon.android.R;
import ph.devcon.android.news.view.ObservableScrollView;

/**
 * Created by lope on 10/6/14.
 */
public class NewsDetailActivity extends Activity implements ObservableScrollView.Callbacks {

//    @InjectView(R.id.lvw_news)
//    ListView lvwNews;

    private TextView mStickyView;
    private View mPlaceholderView;
    private ObservableScrollView mObservableScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_news_details);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ButterKnife.inject(this);
//        lvwNews.setAdapter(new NewsAdapter(this, new ArrayList<News>()));
//        lvwNews.addFooterView(buildFooterView(inflater));
        mObservableScrollView = (ObservableScrollView) findViewById(R.id.scroll_view);
        mObservableScrollView.setCallbacks(this);

        mStickyView = (TextView) findViewById(R.id.sticky);
        mPlaceholderView = findViewById(R.id.placeholder);

        mObservableScrollView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        onScrollChanged(mObservableScrollView.getScrollY());
                    }
                });
    }

    protected View buildFooterView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.footer_standard, null);
    }

    @Override
    public void onScrollChanged(int scrollY) {
        mStickyView.setTranslationY(Math.max(mPlaceholderView.getTop(), scrollY));
    }

    @Override
    public void onDownMotionEvent() {
    }

    @Override
    public void onUpOrCancelMotionEvent() {
    }
}
