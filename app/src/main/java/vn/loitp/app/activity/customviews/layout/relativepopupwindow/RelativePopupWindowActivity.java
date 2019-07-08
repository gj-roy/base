package vn.loitp.app.activity.customviews.layout.relativepopupwindow;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.views.layout.relativepopupwindow.RelativePopupWindow;

public class RelativePopupWindowActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Spinner spinnerVertical = findViewById(R.id.spinner_vertical);
        final ArrayAdapter<String> adapterVertical = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        adapterVertical.addAll(getResources().getStringArray(R.array.vertical_positions));
        adapterVertical.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVertical.setAdapter(adapterVertical);

        final Spinner spinnerHorizontal = findViewById(R.id.spinner_horizontal);
        final ArrayAdapter<String> adapterHorizontal = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        adapterHorizontal.addAll(getResources().getStringArray(R.array.horizontal_positions));
        adapterHorizontal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHorizontal.setAdapter(adapterHorizontal);

        final Spinner spinnerWidth = findViewById(R.id.spinner_width);
        final ArrayAdapter<String> adapterWidth = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        adapterWidth.addAll(getResources().getStringArray(R.array.width));
        adapterWidth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWidth.setAdapter(adapterWidth);

        final Spinner spinnerHeight = findViewById(R.id.spinner_height);
        final ArrayAdapter<String> adapterHeight = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        adapterHeight.addAll(getResources().getStringArray(R.array.height));
        adapterHeight.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHeight.setAdapter(adapterHeight);

        final CheckBox checkboxFitInScreen = findViewById(R.id.checkbox_fit_in_screen);

        findViewById(R.id.button1).setOnClickListener(view -> {
            final ExampleCardPopup popup = new ExampleCardPopup(view.getContext());
            switch (spinnerWidth.getSelectedItemPosition()) {
                case 0:
                    popup.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
                    break;
                case 1:
                    popup.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                    break;
                case 2:
                    popup.setWidth((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80, getResources().getDisplayMetrics()));
                    break;
                case 3:
                    popup.setWidth((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 240, getResources().getDisplayMetrics()));
                    break;
                case 4:
                    popup.setWidth((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 480, getResources().getDisplayMetrics()));
                    break;
                default:
                    throw new IllegalStateException();
            }
            switch (spinnerWidth.getSelectedItemPosition()) {
                case 0:
                    popup.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                    break;
                case 1:
                    popup.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
                    break;
                case 2:
                    popup.setHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80, getResources().getDisplayMetrics()));
                    break;
                case 3:
                    popup.setHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 240, getResources().getDisplayMetrics()));
                    break;
                case 4:
                    popup.setHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 480, getResources().getDisplayMetrics()));
                    break;
                default:
                    throw new IllegalStateException();
            }
            final int vertPos;
            switch (spinnerVertical.getSelectedItemPosition()) {
                case 0:
                    vertPos = RelativePopupWindow.VerticalPosition.ABOVE;
                    break;
                case 1:
                    vertPos = RelativePopupWindow.VerticalPosition.ALIGN_BOTTOM;
                    break;
                case 2:
                    vertPos = RelativePopupWindow.VerticalPosition.CENTER;
                    break;
                case 3:
                    vertPos = RelativePopupWindow.VerticalPosition.ALIGN_TOP;
                    break;
                case 4:
                    vertPos = RelativePopupWindow.VerticalPosition.BELOW;
                    break;
                default:
                    throw new IllegalStateException();
            }
            final int horizPos;
            switch (spinnerHorizontal.getSelectedItemPosition()) {
                case 0:
                    horizPos = RelativePopupWindow.HorizontalPosition.LEFT;
                    break;
                case 1:
                    horizPos = RelativePopupWindow.HorizontalPosition.ALIGN_RIGHT;
                    break;
                case 2:
                    horizPos = RelativePopupWindow.HorizontalPosition.CENTER;
                    break;
                case 3:
                    horizPos = RelativePopupWindow.HorizontalPosition.ALIGN_LEFT;
                    break;
                case 4:
                    horizPos = RelativePopupWindow.HorizontalPosition.RIGHT;
                    break;
                default:
                    throw new IllegalStateException();
            }
            final boolean fitInScreen = checkboxFitInScreen.isChecked();
            popup.showOnAnchor(view, vertPos, horizPos, fitInScreen);
        });
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
        return R.layout.activity_relative_popup_window_layout;
    }
}
