/*
 * Copyright (C) 2014 Philippine Android Developers Community
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ph.devcon.android.news;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.UiLifecycleHelper;
import com.google.common.base.Optional;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ph.devcon.android.DevConApplication;
import ph.devcon.android.R;
import ph.devcon.android.news.db.News;
import ph.devcon.android.news.service.NewsService;
import ph.devcon.android.news.view.ObservableScrollView;
import ph.devcon.android.util.Util;

/**
 * Created by lope on 10/9/14.
 */
public class NewsDetailsFragment extends Fragment implements ObservableScrollView.Callbacks {
    public static final String ID = "news_id";

    @InjectView(R.id.txt_title)
    TextView txtTitle;
    @InjectView(R.id.txt_content)
    TextView txtContent;
    @InjectView(R.id.txt_tag)
    TextView txtTag;
    @InjectView(R.id.img_news_big)
    ImageView imgNewsBig;
    @Inject
    NewsService newsService;

    private View mPlaceholderView;
    private ObservableScrollView mObservableScrollView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_news_details, container, false);
        DevConApplication.injectMembers(this);
        ButterKnife.inject(this, rootView);

        mObservableScrollView = (ObservableScrollView) rootView.findViewById(R.id.scroll_view);
        mObservableScrollView.setCallbacks(this);

        txtTitle = (TextView) rootView.findViewById(R.id.txt_title);
        mPlaceholderView = rootView.findViewById(R.id.placeholder);

        mObservableScrollView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        onScrollChanged(mObservableScrollView.getScrollY());
                    }
                });
        News news = getNewsObject(getArguments().getInt(ID));
        init(news);
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    protected News getNewsObject(int id) {
        Optional<News> newsOptional = Optional.of(newsService.get(id));
        return newsOptional.get();
    }

    protected void init(News news) {
        Typeface myTypeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Merriweather-Regular.otf");
        txtTitle.setText(news.getTitle());
        txtContent.setTypeface(myTypeface);
        txtContent.setText(Html.fromHtml(news.getHtmlContent()));
        String tag = news.getTag();
        if (!Util.isNullOrEmpty(tag))
            txtTag.setText(tag);
        if (!Util.isNullOrEmpty(news.getPhotoUrl()))
            Picasso.with(getActivity()).load(news.getPhotoUrl()).into(imgNewsBig);
    }

    protected View buildFooterView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.footer_standard, null);
    }

    @Override
    public void onScrollChanged(int scrollY) {
        txtTitle.setTranslationY(Math.max(mPlaceholderView.getTop(), scrollY));
    }

    @Override
    public void onDownMotionEvent() {
    }

    @Override
    public void onUpOrCancelMotionEvent() {
    }
}