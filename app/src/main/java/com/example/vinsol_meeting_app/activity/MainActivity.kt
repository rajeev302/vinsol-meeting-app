package com.example.vinsol_meeting_app.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vinsol_meeting_app.R
import com.example.vinsol_meeting_app.adapter.ScheduledMeetingsAdapter
import com.example.vinsol_meeting_app.viewmodel.ScheduledMeetingViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var scheduledRecyclerView: RecyclerView
    private lateinit var adapter: ScheduledMeetingsAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var viewmodel: ScheduledMeetingViewModel
    private lateinit var prevButton: Button
    private lateinit var nextButton: Button
    private lateinit var noDataAvailable: TextView
    private lateinit var dateTextView: TextView
    private lateinit var scheduleCompanyMeetingButton: TextView

    companion object {
        const val SCHEDULED_MEETINGS_RESPONSE_MODEL = "scheduledMeetingsResponseModel"
        const val CURRENT_STRING_DATE_VALUE = "currentDateValue"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewmodel = ViewModelProvider(this).get(ScheduledMeetingViewModel::class.java)
        setupRecyclerView()
        setupUi()
        setupClickListener()
        fireApiCall()
    }

    private fun setupUi(){
        prevButton = prev_button
        nextButton = next_button
        progressBar = main_activity_progress_bar
        noDataAvailable = no_data_available_text_view
        dateTextView = date_text_view
        scheduleCompanyMeetingButton = schedule_company_meeting_button
        dateTextView.text = viewmodel.currentDateApiString
    }

    private fun setupClickListener(){
        prevButton.setOnClickListener {
            viewmodel.currentDate.date = viewmodel.currentDate.date - 1
            viewmodel.currentDateApiString = viewmodel.sdf.format(viewmodel.currentDate)
            dateTextView.text = viewmodel.currentDateApiString
            fireApiCall()
        }
        nextButton.setOnClickListener {
            viewmodel.currentDate.date = viewmodel.currentDate.date + 1
            viewmodel.currentDateApiString = viewmodel.sdf.format(viewmodel.currentDate)
            dateTextView.text = viewmodel.currentDateApiString
            fireApiCall()
        }
        scheduleCompanyMeetingButton.setOnClickListener {
            val intent = Intent(this, ScheduleMeetingActivity::class.java)
            intent.putParcelableArrayListExtra(SCHEDULED_MEETINGS_RESPONSE_MODEL, viewmodel.scheduledMeetingsList)
            intent.putExtra(CURRENT_STRING_DATE_VALUE, viewmodel.currentDateApiString)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView(){
        scheduledRecyclerView = scheduled_meeting_recycler_view
        progressBar = main_activity_progress_bar
        adapter = ScheduledMeetingsAdapter(this, viewmodel.scheduledMeetingsList)
        val layoutManager = LinearLayoutManager(this)
        scheduledRecyclerView.layoutManager = layoutManager
        scheduledRecyclerView.adapter = adapter
    }

    private fun fireApiCall(){
        progressBar.visibility = View.VISIBLE
        noDataAvailable.visibility = View.GONE
        viewmodel.getScheduledMeetings(this, {
            progressBar.visibility = View.GONE
            if (viewmodel.scheduledMeetingsList.size > 0){
                adapter.notifyDataSetChanged()
            } else {
                noDataAvailable.visibility = View.VISIBLE
            }
        }, {
            noDataAvailable.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
        })
    }
}
