package vn.loitp.activity.api.coroutine.viewmodel

import androidx.lifecycle.MutableLiveData
import com.loitp.core.base.BaseViewModel
import com.loitp.service.liveData.ActionData
import com.loitp.service.liveData.ActionLiveData
import com.loitp.service.model.UserTest
import kotlinx.coroutines.launch
import vn.loitp.activity.api.coroutine.repository.TestRepository
import vn.loitp.activity.api.coroutine.service.TestApiClient

class TestViewModel : BaseViewModel() {
    private val repository: TestRepository = TestRepository(TestApiClient.apiService)
    val userTestListLiveData: MutableLiveData<ArrayList<UserTest>?> = MutableLiveData()
    val userActionLiveData: ActionLiveData<ActionData<ArrayList<UserTest>>> = ActionLiveData()

    fun getUserTestListByPage(page: Int, isRefresh: Boolean) {
        userActionLiveData.set(ActionData(isDoing = true))

        ioScope.launch {
            val response = repository.getUserTest(page = page)
            if (response.data != null) {
                userActionLiveData.post(
                    ActionData(
                        isDoing = false,
                        isSuccess = true,
                        isSwipeToRefresh = isRefresh,
                        data = response.data
                    )
                )
            } else {
                userActionLiveData.postAction(getErrorRequest(response))
            }
        }
    }

    fun addUserList(
        userTestList: ArrayList<UserTest>,
        isRefresh: Boolean?
    ) {
        var currentUserTestList = userTestListLiveData.value
        if (isRefresh == true) {
            currentUserTestList?.clear()
        }
        if (currentUserTestList == null) {
            currentUserTestList = ArrayList()
        }
        currentUserTestList.addAll(userTestList)
        userTestListLiveData.post(currentUserTestList)
    }
}