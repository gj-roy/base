package com.loitpcore.restapi.flickr.model.photosetgetlist

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.loitpcore.core.base.BaseModel

@Keep
class Description : BaseModel() {
    @SerializedName("_content")
    @Expose
    var content: String? = null
}