package vn.loitp.a.cv.vp.auto

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import vn.loitp.R

class FrmIv : Fragment() {

    companion object {
        fun newInstance(): FrmIv {
            return FrmIv()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.f_iv, container, false)
    }
}