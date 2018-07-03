package vn.loitp.app.activity.animation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import loitp.basemaster.R;
import vn.loitp.app.activity.animation.activitytransition.Animation1Activity;
import vn.loitp.app.activity.animation.animationview.AnimationViewActivity;
import vn.loitp.app.activity.animation.confetti.ConfettiMenuActivity;
import vn.loitp.app.activity.animation.expectanim.ExpectAnimActivity;
import vn.loitp.app.activity.animation.flyschool.FlySchoolActivity;
import vn.loitp.app.activity.animation.overscroll.OverScrollActivity;
import vn.loitp.app.activity.animation.shadowviewhelper.ShadowViewHelperActivity;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LActivityUtil;

public class MenuAnimationActivity extends BaseFontActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.bt_animation_view).setOnClickListener(this);
        findViewById(R.id.bt_over_scroll).setOnClickListener(this);
        findViewById(R.id.bt_fly_school).setOnClickListener(this);
        findViewById(R.id.bt_activity_transition).setOnClickListener(this);
        findViewById(R.id.bt_shadowview_helper).setOnClickListener(this);
        findViewById(R.id.bt_expect_anim).setOnClickListener(this);
        findViewById(R.id.bt_animation_confetti).setOnClickListener(this);
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
            case R.id.bt_activity_transition:
                intent = new Intent(activity, Animation1Activity.class);
                break;
            case R.id.bt_shadowview_helper:
                intent = new Intent(activity, ShadowViewHelperActivity.class);
                break;
            case R.id.bt_expect_anim:
                intent = new Intent(activity, ExpectAnimActivity.class);
                break;
            case R.id.bt_animation_confetti:
                intent = new Intent(activity, ConfettiMenuActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
            LActivityUtil.tranIn(activity);
        }
    }
}
