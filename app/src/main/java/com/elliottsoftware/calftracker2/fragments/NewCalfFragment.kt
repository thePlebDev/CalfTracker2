package com.elliottsoftware.calftracker2.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.elliottsoftware.calftracker2.R
import com.elliottsoftware.calftracker2.databinding.FragmentNewCalfBinding
import com.elliottsoftware.calftracker2.models.Calf
import com.elliottsoftware.calftracker2.util.CalfApplication
import com.elliottsoftware.calftracker2.viewModels.CalfViewModel
import com.elliottsoftware.calftracker2.viewModels.CalfViewModelFactory
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [NewCalfFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewCalfFragment : Fragment() {

    private var _binding: FragmentNewCalfBinding? = null
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
        _binding = FragmentNewCalfBinding.inflate(inflater, container, false)
        val view = binding.root
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.newCalfFabLeft.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_newCalfFragment_to_mainFragment)
        }
        binding.newCalfFabRight.setOnClickListener{
            val tagNumber:String = binding.editTextTitle.text.toString()
            val details:String = binding.editTextDescription.text.toString()
            val cciaNumber:String = binding.editTextCciaNumber.text.toString()
            val sex:String = buttonIsChecked(binding.radioBull)
            saveCalf(tagNumber,details,cciaNumber,sex,it)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun buttonIsChecked(radioButton: RadioButton):String{
        return if(radioButton.isChecked){
            "Bull"
        }else{
            "Heifer"
        }
    }
    private fun saveCalf(tagNumber: String,details:String,cciaNumber:String,sex:String,view:View){
        if(!validateTagNumber(tagNumber)){
            // this should run if the tagNumber is not empty
            calfViewModel.insert(Calf(tagNumber,cciaNumber,sex,details, Date()))
            Navigation.findNavController(view).navigate(R.id.action_newCalfFragment_to_mainFragment)
        }
    }

    private fun validateTagNumber(tagNumber:String):Boolean{
        //if statements are expressions in Kotlin
        return if(tagNumber.isEmpty()){
            binding.editTextTitle.error = "Field can not be empty"
            true;
        }else{
            false;
        }

    }

}