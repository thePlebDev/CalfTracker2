package com.elliottsoftware.calftracker2.recyclerViews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elliottsoftware.calftracker2.R
import com.elliottsoftware.calftracker2.models.Calf

class CalfListAdapter :ListAdapter<Calf,CalfListAdapter.CalfViewHolder>(CalfComparator()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalfViewHolder {
        return CalfViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: CalfViewHolder, position: Int) {
        val current = getItem(position)
        // we will pass on the values to bind
        holder.bind()
    }

//--------------------------------VIEW HOLDER CLASS----------------------------
    class CalfViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        //get references to the indiv items here

        fun bind(){
            //set the references to values here
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