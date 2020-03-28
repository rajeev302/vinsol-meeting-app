package com.example.vinsol_meeting_app.service

import com.example.vinsol_meeting_app.model.ScheduledMeetingsResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Service {
    @GET("schedule")
    fun getScheduledMeetings(@Query("date")date: String): Call<List<ScheduledMeetingsResponseModel>>
}