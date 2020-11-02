package vn.loitp.app.activity.customviews.compas

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.views.compass.CompassListener
import kotlinx.android.synthetic.main.activity_compas.*
import vn.loitp.app.R

//https://github.com/arbelkilani/Compass-View?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=6973

@LogTag("CompasActivity")
@IsFullScreen(false)
class CompasActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_compas
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        compassView.setListener(object : CompassListener {
            override fun onSensorChanged(event: SensorEvent) {
                logD("onSensorChanged : $event")
            }

            override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
                logD("onAccuracyChanged : sensor : $sensor")
                logD("onAccuracyChanged : accuracy : $accuracy")
            }
        })
    }

}
