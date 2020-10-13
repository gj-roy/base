package vn.loitp.app.activity.customviews.layout.dragueur;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.annotation.IsFullScreen;
import com.annotation.LayoutId;
import com.annotation.LogTag;
import com.core.base.BaseFontActivity;
import com.views.layout.dragueur.Direction;
import com.views.layout.dragueur.DraggableView;

import vn.loitp.app.R;

//https://github.com/Meetic/Dragueur?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=3534

@LayoutId((R.layout.activity_dragueur))
@LogTag("DragueurActivity")
@IsFullScreen(false)
public class DragueurActivity extends BaseFontActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupViews();
    }

    private void setupViews() {
        DraggableView dragueur = findViewById(R.id.dragueur);
        textView = findViewById(R.id.textView);
        dragueur.setRotationEnabled(true);
        //draggableView.setAnimating(true);
        dragueur.setRotationValue(10f);
        //draggableView.setDraggable(true);
        dragueur.setDragListener(new DraggableView.DraggableViewListener() {
            @Override
            public void onDrag(DraggableView draggableView, float percentX, float percentY) {
                setText("draggableView: " + percentX + " - " + percentY);
                logD("onDrag " + percentX + " x " + percentY);
            }

            @Override
            public void onDraggedStarted(DraggableView draggableView, Direction direction) {
                setText("onDraggedStarted");
                logD("onDraggedStarted " + direction.name());
            }

            @Override
            public void onDraggedEnded(DraggableView draggableView, Direction direction) {
                setText("onDraggedEnded");
                logD("onDraggedEnded " + direction.name());
            }

            @Override
            public void onDragCancelled(DraggableView draggableView) {
                setText("onDragCancelled");
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void setText(String s) {
        textView.setText("Dragueur can move any view with one finger ;)\n" + s);
    }
}
