package vn.loitp.app.a.interviewVN

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.loitp.annotation.LogTag
import com.loitp.core.utilities.LStoreUtil
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.frm_interview_vn_iq_list_package.*
import vn.loitp.R
import vn.loitp.app.a.demo.fragmentFlow.BaseFragmentFlow
import vn.loitp.app.a.interviewVN.adt.QAAdapter

//https://drive.google.com/drive/u/0/folders/1STvbrMp_WSvPrpdm8DYzgekdlwXKsCS9
@LogTag("FrmListPackage")
class FrmListPackage : BaseFragmentFlow() {

    private var concatAdapter = ConcatAdapter()
    private var qaAdapter = QAAdapter(
        listQA = ArrayList(),
        isShowADefault = false,
        isShowNextLink = false
    )

    override fun setLayoutResourceId(): Int {
        return R.layout.frm_interview_vn_iq_list_package
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupData()
    }

    override fun onBackPressed(): Boolean {
        popThisFragment()
        return true
    }

    override fun onFragmentResume() {
        super.onFragmentResume()
        print("onFragmentResume")
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(view = this.ivIconLeft, runnable = {
                if (activity is InterviewVNIQActivity) {
                    (activity as InterviewVNIQActivity).onBaseBackPressed()
                }
            })
            ivIconRight?.isVisible = false
            this.tvTitle?.text = FrmListPackage::class.java.simpleName
        }
        recyclerView.layoutManager = LinearLayoutManager(context)
        qaAdapter.onClickRootListener = { qa, _ ->
            if (activity is InterviewVNIQActivity) {
                (activity as InterviewVNIQActivity).addFragment(FrmListQA(qa.nextLink))
            }
        }
        concatAdapter.addAdapter(qaAdapter)
        recyclerView.adapter = concatAdapter
    }

    private fun setupData() {
        showDialogProgress()
        LStoreUtil.getPkgFromGGDrive(
            linkGGDriveSetting = "https://drive.google.com/uc?export=download&id=1bF_xmaIGsre7c-aGeDhgSnWH7IYoqq8K",
            onGGFailure = { _, e ->
                hideDialogProgress()
                showShortError(e.toString())
            },
            onGGResponse = { pkg ->
                activity?.runOnUiThread {
                    hideDialogProgress()
                    val list = pkg?.data
                    if (list.isNullOrEmpty()) {
                        showShortError(getString(R.string.no_data_eng))
                    } else {
                        qaAdapter.setData(list)
                    }
                }
            }
        )
    }
}