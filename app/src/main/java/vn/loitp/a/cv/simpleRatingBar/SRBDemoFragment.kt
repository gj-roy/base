package vn.loitp.a.cv.simpleRatingBar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LLog
import com.willy.ratingbar.BaseRatingBar
import kotlinx.android.synthetic.main.f_srb_demo.*
import vn.loitp.R

class SRBDemoFragment : Fragment() {

    companion object {
        const val TAG = "SimpleRatingBar"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.f_srb_demo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        baseRatingBarMain.isClearRatingEnabled = false
        baseRatingBarMain.setOnRatingChangeListener { _: BaseRatingBar?, rating: Float, _: Boolean ->
            LLog.d(TAG, "BaseRatingBar onRatingChange: $rating")
        }
        scaleRatingBar.setOnRatingChangeListener { _: BaseRatingBar?, rating: Float, _: Boolean ->
            LLog.d(TAG, "ScaleRatingBar onRatingChange: $rating")
        }
        rotationRatingBarMain.setOnRatingChangeListener { _: BaseRatingBar?, rating: Float, _: Boolean ->
            LLog.d(TAG, "RotationRatingBar onRatingChange: $rating")
        }
        buttonMainAddRating.setSafeOnClickListener {
            var currentRating = baseRatingBarMain.rating
            baseRatingBarMain.rating = currentRating + 0.25f
            currentRating = scaleRatingBar.rating
            scaleRatingBar.rating = currentRating + 0.25f
            currentRating = rotationRatingBarMain.rating
            rotationRatingBarMain.rating = currentRating + 0.25f
        }
    }

}