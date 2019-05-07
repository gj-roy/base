package vn.loitp.app.activity.animation.basictransition;

import android.os.Bundle;
import androidx.core.view.ViewCompat;
import android.widget.ImageView;
import android.widget.TextView;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.common.Constants;
import vn.loitp.core.utilities.LImageUtil;

public class BasicTransition1Activity extends BaseFontActivity {
    public static final String IV = "iv";
    public static final String TV = "tv";
    private ImageView iv;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iv = (ImageView) findViewById(R.id.imageview_item);
        tv = (TextView) findViewById(R.id.tv);
        LImageUtil.load(activity, Constants.URL_IMG_2, iv);
        ViewCompat.setTransitionName(iv, IV);
        ViewCompat.setTransitionName(tv, TV);
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
        return R.layout.activity_basic_transition_1;
    }
}
