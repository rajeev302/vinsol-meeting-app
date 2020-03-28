package com.example.vinsol_meeting_app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vinsol_meeting_app.R
import com.example.vinsol_meeting_app.model.ScheduledMeetingsResponseModel

class ScheduledMeetingsAdapter(
    val context: Context,
    val dataSet: MutableList<ScheduledMeetingsResponseModel>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int {
        return dataSet.count()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val scheduledMeetingsViewHOlder = holder as ScheduledMeetingsListViewHolder
        scheduledMeetingsViewHOlder.startEndTimeTextView.text = "${dataSet[position].startTime} - ${dataSet[position].endTime}"
        scheduledMeetingsViewHOlder.descriptionTextView.text = dataSet[position].description
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.scheduled_meetings, parent, false)
        return ScheduledMeetingsListViewHolder(itemView)
    }

    inner class ScheduledMeetingsListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val startEndTimeTextView: TextView = itemView.findViewById(R.id.start_end_time_text_view)
        val descriptionTextView: TextView = itemView.findViewById(R.id.description_text_view)
    }
}