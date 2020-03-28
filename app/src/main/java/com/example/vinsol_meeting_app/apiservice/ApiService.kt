package com.example.vinsol_meeting_app.apiservice

import com.example.vinsol_meeting_app.constants.Constants
import com.example.vinsol_meeting_app.manager.NetworkManager
import com.example.vinsol_meeting_app.model.ScheduledMeetingsResponseModel
import com.example.vinsol_meeting_app.service.Service
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlin.coroutines.CoroutineContext

class ApiService{
    suspend fun getScheduledMeetings(coroutineContext: CoroutineContext, date: String): List<ScheduledMeetingsResponseModel>? = CoroutineScope(coroutineContext).async {
        val apiService = NetworkManager.makeRetrofitObject(Service::class.java, Constants.BASE_URL)
        val callResult = apiService.getScheduledMeetings(date)
        callResult.execute().body()
    }.await()
}