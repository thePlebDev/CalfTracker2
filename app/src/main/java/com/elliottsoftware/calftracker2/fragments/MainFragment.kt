package com.elliottsoftware.calftracker2.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elliottsoftware.calftracker2.R
import com.elliottsoftware.calftracker2.databinding.FragmentMainBinding
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
class MainFragment : Fragment(), CalfListAdapter.OnCalfListener,MenuProvider, SearchView.OnQueryTextListener{
    private  var _binding:FragmentMainBinding? = null
    //this property is only valid between onCreateView on onDestroy
    private val binding get() = _binding!!

    private val calfViewModel: CalfViewModel by viewModels {
        CalfViewModelFactory((activity?.application as CalfApplication).repository)
    }


    private lateinit var recyclerView:RecyclerView
    private val adapter = CalfListAdapter(this)




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
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
        //add an observer on the LiveData returned by allCalves();
        //the onChanged method fires when the observed data changes and the activity is in the foreground
        calfViewModel.allCalves.observe(viewLifecycleOwner, Observer { calves ->
            calves?.let{adapter.submitList(it)}
        })


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




    override fun onQueryTextSubmit(query: String?): Boolean {
        if(query != null){
            searchDatabase(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if(query != null){
            searchDatabase(query)
        }
        return true
    }
    private fun searchDatabase(query: String){
        val searchQuery = "%$query%"

        calfViewModel.searchDatabase(searchQuery).observe(this) { list ->
            list.let {
                adapter.submitList(it)
            }
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.main_menu,menu)
        val search = menu?.findItem(R.id.menu_search)
        val searchView = search?.actionView as? SearchView
        searchView?.queryHint = "Search Tag Number"
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
       return true
    }


}