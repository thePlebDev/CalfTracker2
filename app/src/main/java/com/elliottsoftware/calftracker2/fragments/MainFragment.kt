package com.elliottsoftware.calftracker2.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.elliottsoftware.calftracker2.R
import com.elliottsoftware.calftracker2.databinding.FragmentMainBinding
import com.elliottsoftware.calftracker2.databinding.IndivCalfViewBinding
import com.elliottsoftware.calftracker2.recyclerViews.CalfListAdapter
import com.elliottsoftware.calftracker2.util.CalfApplication
import com.elliottsoftware.calftracker2.viewModels.CalfViewModel
import com.elliottsoftware.calftracker2.viewModels.CalfViewModelFactory


/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {
    private  var _binding:FragmentMainBinding? = null
    //this property is only valid between onCreateView on onDestroy
    private val binding get() = _binding!!
    private val calfViewModel: CalfViewModel by viewModels {
        CalfViewModelFactory((activity?.application as CalfApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = binding.recyclerview
        val adapter = CalfListAdapter()
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



    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}