package vn.loitp.app.activity.animation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import loitp.basemaster.R;
import vn.loitp.app.activity.animation.animationview.AnimationViewActivity;
import vn.loitp.app.activity.animation.flyschool.FlySchoolActivity;
import vn.loitp.app.activity.animation.overscroll.OverScrollActivity;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LUIUtil;

public class MenuAnimationActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.bt_animation_view).setOnClickListener(this);
        findViewById(R.id.bt_over_scroll).setOnClickListener(this);
        findViewById(R.id.bt_fly_school).setOnClickListener(this);
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
    protected Activity setActivity() {
        return this;
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_menu_animation;
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.bt_animation_view:
                intent = new Intent(activity, AnimationViewActivity.class);
                break;
            case R.id.bt_over_scroll:
                intent = new Intent(activity, OverScrollActivity.class);
                break;
            case R.id.bt_fly_school:
                intent = new Intent(activity, FlySchoolActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
            LUIUtil.transActivityFadeIn(activity);
        }
    }
}
