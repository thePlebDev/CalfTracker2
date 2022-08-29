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
 * todo: break all these interfaces up. SINGLE PURPOSE
 * A simple [Fragment] subclass used to instantiate and reuses the [R.layout.fragment_main]
 * and to handle basic logic
 * Implements [CalfListAdapter.OnCalfListener] to handle clicks on the indivual RecyclerView items
 * Implements [MenuProvider] to allow search functionality
 * Implements [SearchView.OnQueryTextListener] to implement search functionality
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




    /**
     * Will inflate the XML file via View Binding, also gets references to basic
     * view items
     */
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

    /**
     * handles all the business logic needed for the [R.layout.fragment_main]
     * file
     */
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


    /**
     * sets _binding = null to avoid memory leaks with View Binding
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    /**
     * method from [CalfListAdapter.OnCalfListener] used to navigate to [UpdateCalfFragment]
     * @param[calfId] the unique identifier of the calf
     *
     * @return
     */
    override fun onCalfClick(calfId: Long) {
        //allCalves.value?.get(position) //index of the current calf
        val action = MainFragmentDirections.actionMainFragmentToUpdateCalfFragment(calfId)

        Navigation.findNavController(binding.root).navigate(action)
    }



    /**
     * method from [SearchView.OnQueryTextListener] used to handle search queries
     * @param[query] the query entered by the user
     *
     * @return boolean to deterime if the query was handled properly
     */
    //QUERY RELATED METHODS
    override fun onQueryTextSubmit(query: String?): Boolean {
        if(query != null){
            searchDatabase(query)
        }
        return true
    }

    /**
     * method from [SearchView.OnQueryTextListener] used to handle search queries
     * @param[query] the query entered by the user
     *
     * @return boolean to determine if the query was handled properly
     */
    override fun onQueryTextChange(query: String?): Boolean {
        if(query != null){
            searchDatabase(query)
        }
        return true
    }
    /**
     * private utility method to search the database
     * @param[query] the query entered by the user
     *
     * @return
     */
    private fun searchDatabase(query: String){
        val searchQuery = "%$query%"

        calfViewModel.searchDatabase(searchQuery).observe(this) { list ->
            list.let {
                adapter.submitList(it)
            }
        }
    }

    /**
     * method from [MenuProvider], called to inflate the menu
     * @param[menu] the menu to inflate the new menu items into
     * @param[menuInflater] the inflater to be used to inflate the updated menu
     * @return
     */

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