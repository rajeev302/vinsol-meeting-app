package com.example.vinsol_meeting_app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.vinsol_meeting_app.model.ScheduledMeetingsResponseModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.CoroutineContext

class ScheduleMeetingViewModel(application: Application): AndroidViewModel(application),
    CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main
    val scheduledMeetingsList: MutableList<ScheduledMeetingsResponseModel> = mutableListOf()
    val calendar = Calendar.getInstance()
    val mHour = calendar.get(Calendar.HOUR_OF_DAY)
    val mMinute = calendar.get(Calendar.MINUTE)
    var startTime: String = ""
    var endTime: String = ""
    var currentDateApiString: String? = null
    var startTimeDateObject: Date = Date()
    var endTimeDateObject: Date = Date()
    val sdf = SimpleDateFormat("hh:mm")
    var isStartTimeAvailable: Boolean = true
    var isEndTimeAvailable: Boolean = true
}