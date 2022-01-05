package com.game.findnumber.model

import androidx.annotation.Keep
import com.core.base.BaseModel

@Keep
class FindNumberItem : BaseModel() {

    companion object {
        const val STATUS_OPEN = 0
        const val STATUS_CLOSE = 1
    }

    var name: String? = null
    var rotate: Float = 0f
    var status = STATUS_OPEN
}
