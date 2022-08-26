package com.elliottsoftware.calftracker2.util

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.elliottsoftware.calftracker2.models.Calf
import com.elliottsoftware.calftracker2.recyclerViews.CalfListAdapter
import com.elliottsoftware.calftracker2.viewModels.CalfViewModel

class SwipeToDelete(private val calfViewModel: CalfViewModel, private val calfListAdapter: CalfListAdapter):
    ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        //NOT BEING IMPLEMENTED
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {


//        calfViewModel.allCalves.value?.removeAt(viewHolder.adapterPosition)
//        calfListAdapter.notifyItemRemoved(viewHolder.adapterPosition)
        //calfViewModel.delete(calfListAdapter.getCalfAt(viewHolder.adapterPosition)!!)
        val calf  = calfListAdapter.currentList[viewHolder.adapterPosition]
        calfViewModel.delete(calf)


        //snackBarCreation.createSnackbarCalfDeleted(Globalview)
    }
}