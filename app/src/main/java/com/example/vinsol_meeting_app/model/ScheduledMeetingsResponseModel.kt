package com.example.vinsol_meeting_app.model


import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ScheduledMeetingsResponseModel(
    @Json(name = "description")
    var description: String?,
    @Json(name = "end_time")
    var endTime: String?,
    @Json(name = "participants")
    var participants: List<String?>?,
    @Json(name = "start_time")
    var startTime: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.createStringArrayList(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(description)
        parcel.writeString(endTime)
        parcel.writeStringList(participants)
        parcel.writeString(startTime)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ScheduledMeetingsResponseModel> {
        override fun createFromParcel(parcel: Parcel): ScheduledMeetingsResponseModel {
            return ScheduledMeetingsResponseModel(parcel)
        }

        override fun newArray(size: Int): Array<ScheduledMeetingsResponseModel?> {
            return arrayOfNulls(size)
        }
    }
}