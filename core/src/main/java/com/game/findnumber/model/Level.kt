package com.game.findnumber.model

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by ©Loitp93 on 12/1/2020.
 * VinHMS
 * www.muathu@gmail.com
 */
@Keep
@Entity(tableName = "Level")
data class Level(
        @SerializedName("id")
        @Expose
        @PrimaryKey
        var id: String = "",

        @SerializedName("name")
        @Expose
        var name: String? = null,

        @SerializedName("status")
        @Expose
        var status: Int = STATUS_LEVEL_OPEN,

        @SerializedName("timeInMls")
        @Expose
        var timeInMls: Long = 0

) : Serializable {
    companion object {
        const val STATUS_LEVEL_OPEN = 0
        const val STATUS_LEVEL_WIN = 1
    }
}