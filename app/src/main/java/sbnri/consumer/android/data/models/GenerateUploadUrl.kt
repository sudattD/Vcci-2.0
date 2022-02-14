package sbnri.consumer.android.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GenerateUploadUrl(
        @SerializedName ("url")
val url:String?) : Parcelable