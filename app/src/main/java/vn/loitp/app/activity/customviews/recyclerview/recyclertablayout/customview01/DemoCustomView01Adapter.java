package vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.customview01;

import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.views.recyclerview.recyclertablayout.RecyclerTabLayout;

import org.jetbrains.annotations.NotNull;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.ColorItem;
import vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.DemoColorPagerAdapter;

/**
 * Created by Shinichi Nishimura on 2015/07/22.
 */
public class DemoCustomView01Adapter extends RecyclerTabLayout.Adapter<DemoCustomView01Adapter.ViewHolder> {

    private DemoColorPagerAdapter mAdapater;

    DemoCustomView01Adapter(ViewPager viewPager) {
        super(viewPager);
        mAdapater = (DemoColorPagerAdapter) mViewPager.getAdapter();
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_custom_view01_tab, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ColorItem colorItem = mAdapater.getColorItem(position);
        holder.title.setText(colorItem.getName());
        holder.color.setBackgroundColor(colorItem.getColor());

        SpannableString name = new SpannableString(colorItem.getName());
        if (position == getCurrentIndicatorPosition()) {
            name.setSpan(new StyleSpan(Typeface.BOLD), 0, name.length(), 0);
        }
        holder.title.setText(name);
    }

    @Override
    public int getItemCount() {
        return mAdapater.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public View color;
        public TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            color = itemView.findViewById(R.id.color);

            itemView.setOnClickListener(v -> getViewPager().setCurrentItem(getAdapterPosition()));
        }
    }
}
