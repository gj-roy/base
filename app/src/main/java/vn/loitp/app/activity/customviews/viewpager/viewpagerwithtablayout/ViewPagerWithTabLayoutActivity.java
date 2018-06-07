package vn.loitp.app.activity.customviews.viewpager.viewpagerwithtablayout;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import loitp.basemaster.R;
import uk.co.chrisjenx.calligraphy.CalligraphyUtils;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.app.common.Constants;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LStoreUtil;

public class ViewPagerWithTabLayoutActivity extends BaseFontActivity {

    private List<Integer> resList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        //LUIUtil.setPullLikeIOSVertical(viewPager);
        for (int i = 0; i < 20; i++) {
            resList.add(LStoreUtil.getRandomColor());
        }
        viewPager.setAdapter(new SlidePagerAdapter());

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        changeTabsFont(tabLayout, Constants.FONT_PATH);
    }

    @Override
    protected boolean setFullScreen() {
        return false;
    }

    @Override
    protected String setTag() {
        return getClass().getSimpleName();
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_viewpager_with_tablayout;
    }

    private class SlidePagerAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup collection, int position) {
            Integer res = resList.get(position);
            LLog.d(TAG, "res " + res);
            LayoutInflater inflater = LayoutInflater.from(activity);
            ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.item_photo_slide_iv, collection, false);

            ImageView imageView = (ImageView) layout.findViewById(R.id.imageView);
            //imageView.setBackgroundColor(res);
            if (position % 2 == 0) {
                imageView.setImageResource(R.drawable.iv);
            } else {
                imageView.setImageResource(R.drawable.logo);
            }

            TextView tv = (TextView) layout.findViewById(R.id.tv);
            tv.setText(position + "/" + resList.size());

            collection.addView(layout);
            return layout;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }

        @Override
        public int getCount() {
            return resList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Page Title " + position;
        }
    }

    private void changeTabsFont(TabLayout tabLayout, String fontName) {

        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    CalligraphyUtils.applyFontToTextView(tabLayout.getContext(), (TextView) tabViewChild, fontName);
                }
            }
        }
    }
}
