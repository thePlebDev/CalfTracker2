package com.elliottsoftware.calftracker2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elliottsoftware.calftracker2.R
import com.elliottsoftware.calftracker2.databinding.FragmentMainBinding
import com.elliottsoftware.calftracker2.models.Calf
import com.elliottsoftware.calftracker2.recyclerViews.CalfListAdapter
import com.elliottsoftware.calftracker2.util.CalfApplication
import com.elliottsoftware.calftracker2.util.SwipeToDelete
import com.elliottsoftware.calftracker2.viewModels.CalfViewModel
import com.elliottsoftware.calftracker2.viewModels.CalfViewModelFactory


/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment(), CalfListAdapter.OnCalfListener{
    private  var _binding:FragmentMainBinding? = null
    //this property is only valid between onCreateView on onDestroy
    private val binding get() = _binding!!

    private val calfViewModel: CalfViewModel by viewModels {
        CalfViewModelFactory((activity?.application as CalfApplication).repository)
    }

    private lateinit var allCalves:LiveData<List<Calf>>;
    private lateinit var recyclerView:RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(inflater,container,false)
        recyclerView = binding.recyclerview

        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = CalfListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
        //add an observer on the LiveData returned by allCalves();
        //the onChanged method fires when the observed data changes and the activity is in the foreground
        calfViewModel.allCalves.observe(viewLifecycleOwner, Observer { calves ->
            calves?.let{adapter.submitList(it)}
        })
        allCalves = calfViewModel.allCalves
        binding.fab.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_mainFragment_to_newCalfFragment)
        }

        ItemTouchHelper(SwipeToDelete(calfViewModel,adapter)).attachToRecyclerView(recyclerView)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onCalfClick(calfId: Long) {
        //allCalves.value?.get(position) //index of the current calf
        val action = MainFragmentDirections.actionMainFragmentToUpdateCalfFragment(calfId)

        Navigation.findNavController(binding.root).navigate(action)
    }

    /**
     * Used to add the swipe to delete functionality
     */
    private fun touchHelper(adapter: CalfListAdapter) {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                //THIS IS FOR DRAG AND DROP FUNCTIONALITY WHICH WE WILL NOT BE USING
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                calfViewModel.delete(adapter.getCalfAt(viewHolder.adapterPosition)!!)
                //snackBarCreation.createSnackbarCalfDeleted(Globalview)
            }
        }).attachToRecyclerView(recyclerView)
    }



}