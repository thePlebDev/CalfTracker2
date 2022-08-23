package com.elliottsoftware.calftracker2.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RadioButton
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.elliottsoftware.calftracker2.R
import com.elliottsoftware.calftracker2.daos.CalfDao
import com.elliottsoftware.calftracker2.databinding.FragmentNewCalfBinding
import com.elliottsoftware.calftracker2.models.Calf
import com.elliottsoftware.calftracker2.repositories.CalfRepository
import com.elliottsoftware.calftracker2.util.CalfApplication
import com.elliottsoftware.calftracker2.viewModels.CalfViewModel
import com.elliottsoftware.calftracker2.viewModels.CalfViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
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
    private lateinit var cancelButton: FloatingActionButton
    private lateinit var createButton: FloatingActionButton
    private lateinit var  tagNumber: EditText
    private lateinit var details: EditText
    private lateinit var cCIANumber: EditText
    private lateinit var bull: RadioButton
    private lateinit var heifer: RadioButton
    private lateinit var calfDate: Date


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
        cancelButton = binding.newCalfFabLeft
        createButton = binding.newCalfFabRight
        tagNumber = binding.editTag
        details = binding.editDescription
        cCIANumber = binding.editCciaNumber
        bull = binding.radioBull
        heifer = binding.radioHeifer



        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.newCalfFabLeft.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_newCalfFragment_to_mainFragment)
        }
        binding.newCalfFabRight.setOnClickListener{
            val tagNumber:String = tagNumber.text.toString()
            val details:String = details.text.toString()
            val cciaNumber:String = cCIANumber.text.toString()
            val sex:String = buttonIsChecked(bull)
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
            binding.editTag.error = "Field can not be empty"
            true;
        }else{
            false;
        }

    }

}