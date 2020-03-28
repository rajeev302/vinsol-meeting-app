package com.example.vinsol_meeting_app.repository

import android.content.Context
import com.example.vinsol_meeting_app.apiservice.ApiService
import com.example.vinsol_meeting_app.extensions.isInternetAvailable
import com.example.vinsol_meeting_app.model.ScheduledMeetingsResponseModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlin.coroutines.CoroutineContext

class Respository: CoroutineScope {
    private val job: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    suspend fun getScheduledMeetings(context: Context, date: String): List<ScheduledMeetingsResponseModel>? {
        if (context.isInternetAvailable()){
            return CoroutineScope(coroutineContext).async {
                return@async ApiService().getScheduledMeetings(coroutineContext, date)
            }.await()
        } else {
            return null
        }
    }
}