package vn.loitp.up.a.cv.layout.transformation.rv

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class Poster(
    val name: String,
    val release: String,
    val playtime: String,
    val description: String,
    val poster: String
) : Parcelable
