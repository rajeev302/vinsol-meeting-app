package com.example.vinsol_meeting_app.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.example.vinsol_meeting_app.model.ScheduledMeetingsResponseModel
import com.example.vinsol_meeting_app.repository.Respository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.coroutines.CoroutineContext

class ScheduledMeetingViewModel(application: Application): AndroidViewModel(application),
    CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    private val repository = Respository()
    val scheduledMeetingsList: ArrayList<ScheduledMeetingsResponseModel> = arrayListOf()

    val currentDate: Date = Date()
    val sdf = SimpleDateFormat("dd/M/yyyy")
    var currentDateApiString: String = sdf.format(currentDate)

    fun getScheduledMeetings(context: Context, success: () -> Unit, failure: (message: String) -> Unit) = launch {
        repository.getScheduledMeetings(context, currentDateApiString)?.let {
            scheduledMeetingsList.clear()
            scheduledMeetingsList.addAll(it)
            success.invoke()
        }?:run {
            failure.invoke("failed to get message")
        }
    }
}