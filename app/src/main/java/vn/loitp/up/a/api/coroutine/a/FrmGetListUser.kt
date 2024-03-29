package vn.loitp.up.a.api.coroutine.a

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.base.BaseApplication
import com.loitp.core.base.BaseFragment
import com.loitp.core.ext.setColorForSwipeRefreshLayout
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setScrollChange
import kotlinx.android.synthetic.main.f_coroutine_get_list_user.*
import vn.loitp.R
import vn.loitp.up.a.api.coroutine.vm.TestViewModel

@LogTag("FrmGetListUser")
class FrmGetListUser : BaseFragment() {
    private var testViewModel: TestViewModel? = null
    private var userListAdapter: UserListAdapter? = null
    private var page = 1

    override fun setLayoutResourceId(): Int {
        return R.layout.f_coroutine_get_list_user
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if (frmRootView == null) {
            super.onCreateView(inflater, container, savedInstanceState)
        } else {
            frmRootView
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupViews()
        setupViewModels()

        testViewModel?.let { tvm ->
            if (tvm.userTestListLiveData.value == null) {
                logD("tvm.userAction.value == null")
                testViewModel?.getUserTestListByPage(page = page, isRefresh = false)
            } else {
                logD("tvm.userAction.value != null")
            }
        }
    }

    private fun setupViews() {
        context?.let {
            userListAdapter = UserListAdapter(callback = { _, userTest ->
                val bundle = Bundle()
                bundle.putSerializable(FrmUser.KEY_USER, userTest)
                findNavController().navigate(R.id.action_frmGetListUser_to_frmUser, bundle)
            })
        }
        rvUserTest.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvUserTest.adapter = userListAdapter
        rvUserTest.setScrollChange(
            onBottom = {
                logD("onBottom")
                page += 1
                testViewModel?.getUserTestListByPage(page = page, isRefresh = false)
            }
        )

        btBack.setSafeOnClickListener {
            logD("popBackStack")
            (activity as? BaseActivityFont)?.apply {
                onBaseBackPressed()
            }
        }

        swipeRefreshLayout.setColorForSwipeRefreshLayout()
        swipeRefreshLayout.setOnRefreshListener {
            logD("setOnRefreshListener")
            page = 1
            testViewModel?.getUserTestListByPage(page = page, isRefresh = true)
        }
    }

    private fun setupViewModels() {
        testViewModel = getViewModel(TestViewModel::class.java)
        testViewModel?.let { tvm ->
            tvm.userActionLiveData.observe(
                owner = viewLifecycleOwner,
                observer = { action ->
                    logD("userAction.observe action.isDoing ${action.isDoing}")
                    action.isDoing?.let { isDoing ->
                        swipeRefreshLayout.isRefreshing = isDoing
                    }

                    action.data?.let { userTestList ->
                        val isRefresh = action.isSwipeToRefresh
                        tvm.addUserList(userTestList = userTestList, isRefresh = isRefresh)
                    }

                    action.errorResponse?.let { error ->
                        logE("observe error " + BaseApplication.gson.toJson(error))
                        error.message?.let {
                            showDialogError(
                                errMsg = it,
                                runnable = {
                                    // do nothing
                                }
                            )
                        }
                    }
                }
            )
            tvm.userTestListLiveData.observe(
                viewLifecycleOwner
            ) {
                logD("userTestList.observe size: ${it?.size}")
                userListAdapter?.setList(it)
            }
        }
    }
}
