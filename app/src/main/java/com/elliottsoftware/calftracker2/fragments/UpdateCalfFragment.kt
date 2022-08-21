package com.elliottsoftware.calftracker2.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.elliottsoftware.calftracker2.R
import com.elliottsoftware.calftracker2.databinding.FragmentNewCalfBinding
import com.elliottsoftware.calftracker2.databinding.FragmentUpdateCalfBinding


/**
 * A simple [Fragment] subclass.
 * Use the [UpdateCalfFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UpdateCalfFragment : Fragment() {

    private var _binding:FragmentNewCalfBinding? = null
    private val binding get() = _binding!!
    private val args: UpdateCalfFragmentArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNewCalfBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // binding.button.text = args.calfId.toString()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}