package ph.devcon.android.news;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import butterknife.ButterKnife;
import ph.devcon.android.R;
import ph.devcon.android.news.view.ObservableScrollView;

/**
 * Created by lope on 10/9/14.
 */
public class NewsDetailFragment extends Fragment implements ObservableScrollView.Callbacks {

//    @InjectView(R.id.lvw_news)
//    ListView lvwNews;

    private TextView mStickyView;
    private View mPlaceholderView;
    private ObservableScrollView mObservableScrollView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_news_details, container, false);
        ButterKnife.inject(this, container);
//        lvwNews.setAdapter(new NewsAdapter(this, new ArrayList<News>()));
//        lvwNews.addFooterView(buildFooterView(inflater));
        mObservableScrollView = (ObservableScrollView) rootView.findViewById(R.id.scroll_view);
        mObservableScrollView.setCallbacks(this);

        mStickyView = (TextView) rootView.findViewById(R.id.sticky);
        mPlaceholderView = rootView.findViewById(R.id.placeholder);

        mObservableScrollView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        onScrollChanged(mObservableScrollView.getScrollY());
                    }
                });

        return rootView;
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