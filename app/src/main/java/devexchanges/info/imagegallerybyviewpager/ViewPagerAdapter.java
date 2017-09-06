package devexchanges.info.imagegallerybyviewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<String> images;

    public ViewPagerAdapter(FragmentManager fm, ArrayList<String> imagesList) {
        super(fm);
        this.images = imagesList;
    }

    @Override
    public Fragment getItem(int position) {
        return PageFragment.getInstance(images);
    }

    @Override
    public int getCount() {
        return images.size();
    }
}
