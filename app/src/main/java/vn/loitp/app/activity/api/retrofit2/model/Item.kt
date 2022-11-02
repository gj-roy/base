package vn.loitp.app.activity.api.retrofit2.model

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.loitpcore.core.base.BaseModel

@Keep
class Item : BaseModel() {
    @SerializedName("owner")
    @Expose
    var owner: Owner? = null

    @SerializedName("is_accepted")
    @Expose
    @Suppress("unused")
    var isAccepted: Boolean? = null

    @SerializedName("score")
    @Expose
    var score: Int? = null

    @SerializedName("last_activity_date")
    @Expose
    @Suppress("unused")
    var lastActivityDate: Int? = null

    @SerializedName("creation_date")
    @Expose
    @Suppress("unused")
    var creationDate: Int? = null

    @SerializedName("answer_id")
    @Expose
    var answerId: Int? = null

    @SerializedName("question_id")
    @Expose
    @Suppress("unused")
    var questionId: Int? = null

    @SerializedName("last_edit_date")
    @Expose
    @Suppress("unused")
    var lastEditDate: Int? = null
}
