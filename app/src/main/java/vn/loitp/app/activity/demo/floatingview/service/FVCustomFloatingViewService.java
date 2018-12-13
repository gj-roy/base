package vn.loitp.app.activity.demo.floatingview.service;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import loitp.basemaster.R;
import vn.loitp.views.LToast;
import vn.loitp.views.floatingview.FloatingViewListener;
import vn.loitp.views.floatingview.FloatingViewManager;

public class FVCustomFloatingViewService extends Service implements FloatingViewListener {
    public static final String EXTRA_CUTOUT_SAFE_AREA = "cutout_safe_area";

    //https://stackoverflow.com/questions/8725909/startforeground-does-not-show-my-notification
    //private static final int NOTIFICATION_ID = 908114;
    private static final int NOTIFICATION_ID = 0;

    private static final String PREF_KEY_LAST_POSITION_X = "last_position_x";
    private static final String PREF_KEY_LAST_POSITION_Y = "last_position_y";
    private FloatingViewManager mFloatingViewManager;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mFloatingViewManager != null) {
            return START_STICKY;
        }

        final DisplayMetrics metrics = new DisplayMetrics();
        final WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);
        final LayoutInflater inflater = LayoutInflater.from(this);
        final RelativeLayout relativeLayout = (RelativeLayout) inflater.inflate(R.layout.fv_widget_custom, null, false);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LToast.show(getBaseContext(), getString(R.string.chathead_click_message));
            }
        });
        mFloatingViewManager = new FloatingViewManager(this, this);
        mFloatingViewManager.setFixedTrashIconImage(R.drawable.ic_trash_fixed);
        mFloatingViewManager.setActionTrashIconImage(R.drawable.ic_trash_action);
        mFloatingViewManager.setSafeInsetRect((Rect) intent.getParcelableExtra(EXTRA_CUTOUT_SAFE_AREA));
        // Setting Options(you can change options at any time)
        loadDynamicOptions();
        // Initial Setting Options (you can't change options after created.)
        final FloatingViewManager.Options options = loadOptions(metrics);
        mFloatingViewManager.addViewToWindow(relativeLayout, options);
        startForeground(NOTIFICATION_ID, createNotification(this));
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        destroy();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onFinishFloatingView() {
        stopSelf();
    }

    @Override
    public void onTouchFinished(boolean isFinishing, int x, int y) {
        if (!isFinishing) {
            // Save the last position
            final SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
            editor.putInt(PREF_KEY_LAST_POSITION_X, x);
            editor.putInt(PREF_KEY_LAST_POSITION_Y, y);
            editor.apply();
        }
    }

    private void destroy() {
        if (mFloatingViewManager != null) {
            mFloatingViewManager.removeAllViewToWindow();
            mFloatingViewManager = null;
        }
    }

    private static Notification createNotification(Context context) {
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(context, context.getString(R.string.default_floatingview_channel_id));
        builder.setWhen(System.currentTimeMillis());
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle(context.getString(R.string.mail_content_title));
        builder.setContentText(context.getString(R.string.content_text));
        builder.setOngoing(true);
        builder.setPriority(NotificationCompat.PRIORITY_MIN);
        builder.setCategory(NotificationCompat.CATEGORY_SERVICE);
        return builder.build();
    }

    private void loadDynamicOptions() {
        mFloatingViewManager.setDisplayMode(FloatingViewManager.DISPLAY_MODE_SHOW_ALWAYS);
        //mFloatingViewManager.setDisplayMode(FloatingViewManager.DISPLAY_MODE_HIDE_FULLSCREEN);
    }

    private FloatingViewManager.Options loadOptions(DisplayMetrics metrics) {
        final FloatingViewManager.Options options = new FloatingViewManager.Options();
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        // Shape
        options.shape = FloatingViewManager.SHAPE_CIRCLE;
        //options.shape = FloatingViewManager.SHAPE_RECTANGLE;

        // Margin
        options.overMargin = 0;

        // MoveDirection
        options.moveDirection = FloatingViewManager.MOVE_DIRECTION_DEFAULT;
        //options.moveDirection = FloatingViewManager.MOVE_DIRECTION_LEFT;
        //options.moveDirection = FloatingViewManager.MOVE_DIRECTION_RIGHT;
        //options.moveDirection = FloatingViewManager.MOVE_DIRECTION_NEAREST;
        //options.moveDirection = FloatingViewManager.MOVE_DIRECTION_NONE;
        //options.moveDirection = FloatingViewManager.MOVE_DIRECTION_THROWN;

        options.usePhysics = true;

        // Last position
        final boolean isUseLastPosition = true;
        if (isUseLastPosition) {
            final int defaultX = options.floatingViewX;
            final int defaultY = options.floatingViewY;
            options.floatingViewX = sharedPref.getInt(PREF_KEY_LAST_POSITION_X, defaultX);
            options.floatingViewY = sharedPref.getInt(PREF_KEY_LAST_POSITION_Y, defaultY);
        } else {
            // Init X/Y
            final int offset = (int) (48 + 8 * metrics.density);
            options.floatingViewX = (int) (metrics.widthPixels * 50 - offset);
            options.floatingViewY = (int) (metrics.heightPixels * 100 - offset);
        }
        // Initial Animation
        options.animateInitialMove = true;
        return options;
    }
}
