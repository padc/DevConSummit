package ph.devcon.android.news.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import ph.devcon.android.news.NewsDetailsFragment;
import ph.devcon.android.news.db.News;

/**
 * Created by lope on 11/4/14.
 */
public class NewsDetailsPagerAdapter extends FragmentStatePagerAdapter {
    List<News> newsList;

    public NewsDetailsPagerAdapter(FragmentManager fm, List<News> newsList) {
        super(fm);
        this.newsList = newsList;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new NewsDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(NewsDetailsFragment.ID, newsList.get(position).getId());
        fragment.setArguments(bundle);
        return fragment;
    }

    public News getItemObject(int position) {
        return newsList.get(position);
    }

    @Override
    public int getCount() {
        return newsList.size();
    }

    public void setItems(List<News> newsList) {
        this.newsList = newsList;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return getItemObject(position).getTitle();
    }
}