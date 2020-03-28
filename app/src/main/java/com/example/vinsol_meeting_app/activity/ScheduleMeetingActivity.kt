package com.example.vinsol_meeting_app.activity

import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.vinsol_meeting_app.R
import com.example.vinsol_meeting_app.model.ScheduledMeetingsResponseModel
import com.example.vinsol_meeting_app.viewmodel.ScheduleMeetingViewModel
import com.example.vinsol_meeting_app.viewmodel.ScheduledMeetingViewModel
import kotlinx.android.synthetic.main.activity_schedule_meeting.*
import java.text.SimpleDateFormat
import java.util.*

class ScheduleMeetingActivity : AppCompatActivity() {

    private lateinit var viewModel: ScheduleMeetingViewModel
    private lateinit var meetingDateValueTextView: TextView
    private lateinit var startTimeValueTextView: TextView
    private lateinit var endTimeValueTextView: TextView
    private lateinit var submitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_meeting)
        viewModel = ViewModelProvider(this).get(ScheduleMeetingViewModel::class.java)
        intent.extras?.let {
            it.getParcelableArrayList<ScheduledMeetingsResponseModel>(MainActivity.SCHEDULED_MEETINGS_RESPONSE_MODEL)?.let {
                viewModel.scheduledMeetingsList.addAll(it)
            }
            viewModel.currentDateApiString = it.getString(MainActivity.CURRENT_STRING_DATE_VALUE)
        }
        setupUi()
        setupClickListener()
    }

    private fun setupUi(){
        meetingDateValueTextView = meeting_date_value
        startTimeValueTextView = start_time_value
        endTimeValueTextView = end_time_value
        submitButton = submit_button
        meetingDateValueTextView.text = viewModel.currentDateApiString
    }

    private fun setupClickListener(){
        meetingDateValueTextView.setOnClickListener {  }
        startTimeValueTextView.setOnClickListener {
            val timePickerDialog = TimePickerDialog(this,
                TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                    viewModel.startTime = "$hourOfDay : ${String.format("%02d", minute)}"
                    startTimeValueTextView.text = viewModel.startTime
                }, viewModel.mHour, viewModel.mMinute, false)
            timePickerDialog.show()
        }
        endTimeValueTextView.setOnClickListener {
            val timePickerDialog = TimePickerDialog(this,
                TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                    viewModel.endTime = "$hourOfDay : ${String.format("%02d", minute)}"
                    endTimeValueTextView.text = viewModel.endTime
                }, viewModel.mHour, viewModel.mMinute, false)
            timePickerDialog.show()
        }
        submitButton.setOnClickListener {
            viewModel.startTimeDateObject = viewModel.sdf.parse(viewModel.startTime.replace(" ", ""))
            viewModel.endTimeDateObject = viewModel.sdf.parse(viewModel.endTime.replace(" ", ""))
            viewModel.scheduledMeetingsList.forEach {
                val jsonStartTimeObject = viewModel.sdf.parse(it.startTime ?: "")
                val jsonEndTimeObject = viewModel.sdf.parse(it.endTime ?: "")
                jsonStartTimeObject?.let { guardedJsonStartTimeObject ->
                    jsonEndTimeObject?.let { guardedJsonEndTimeObject ->
                        if (viewModel.startTimeDateObject in guardedJsonStartTimeObject..guardedJsonEndTimeObject)
                            viewModel.isStartTimeAvailable = viewModel.isStartTimeAvailable && false
                        if (viewModel.endTimeDateObject in guardedJsonStartTimeObject..guardedJsonEndTimeObject)
                            viewModel.isEndTimeAvailable = viewModel.isEndTimeAvailable && false
                    }
                }
            }
            if (viewModel.isStartTimeAvailable && viewModel.isEndTimeAvailable){
                Toast.makeText(this, "slots available", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "slots not available", Toast.LENGTH_LONG).show()
            }
        }
    }
}
