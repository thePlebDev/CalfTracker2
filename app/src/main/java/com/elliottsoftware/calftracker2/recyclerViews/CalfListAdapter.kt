package com.elliottsoftware.calftracker2.recyclerViews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elliottsoftware.calftracker2.R
import com.elliottsoftware.calftracker2.models.Calf
import java.text.SimpleDateFormat

class CalfListAdapter :ListAdapter<Calf,CalfListAdapter.CalfViewHolder>(CalfComparator()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalfViewHolder {
        return CalfViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: CalfViewHolder, position: Int) {
        val current = getItem(position)
        // we will pass on the values to bind
        holder.bind(current)
    }

//--------------------------------VIEW HOLDER CLASS----------------------------
    class CalfViewHolder(calfView: View):RecyclerView.ViewHolder(calfView){
        //get references to the indiv items here
        private val tagNumber: TextView = calfView.findViewById(R.id.text_view_title)
        private val dateView:TextView = calfView.findViewById(R.id.text_view_date);
         private val details:TextView = calfView.findViewById(R.id.text_view_description)
        private val sex:TextView = calfView.findViewById(R.id.text_view_sex)



        fun bind(calf: Calf){
            //set the references to values here
            val pattern = "MM-dd-yyyy"
            val simpleDateFormat = SimpleDateFormat(pattern)
            tagNumber.text = calf.tagNumber
            dateView.text = simpleDateFormat.format(calf.date)
            details.text = calf.details
            sex.text = calf.sex;
        }

    companion object{
        fun create(parent: ViewGroup):CalfViewHolder{
            val view:View = LayoutInflater.from(parent.context)
                .inflate(R.layout.indiv_calf_view,parent,false)
            return CalfViewHolder(view)
        }
    }

    }

    //--------------------------COMPARATOR CLASS-----------------------------
    class CalfComparator : DiffUtil.ItemCallback<Calf>(){
        override fun areItemsTheSame(oldItem: Calf, newItem: Calf): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Calf, newItem: Calf): Boolean {
            return oldItem.tagNumber == newItem.tagNumber
        }

    }


}