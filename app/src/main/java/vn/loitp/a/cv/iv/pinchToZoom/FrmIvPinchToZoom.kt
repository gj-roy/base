package vn.loitp.a.cv.iv.pinchToZoom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.loitp.core.ext.loadGlide
import kotlinx.android.synthetic.main.f_iv_pinch_to_zoom.*
import vn.loitp.R

class FrmIvPinchToZoom : Fragment() {

    companion object {
        const val KEY_URL = "KEY_URL"
    }

    private var urlIv: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.f_iv_pinch_to_zoom, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        urlIv = arguments?.getString(KEY_URL)
        urlIv?.let { u ->
            imageView.loadGlide(
                any = u,
            )
        }
    }
}
