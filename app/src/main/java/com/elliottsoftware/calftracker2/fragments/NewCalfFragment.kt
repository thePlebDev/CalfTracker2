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
import com.elliottsoftware.calftracker2.databinding.FragmentNewCalfBinding
import com.elliottsoftware.calftracker2.models.Calf
import com.elliottsoftware.calftracker2.util.CalfApplication
import com.elliottsoftware.calftracker2.util.SnackBarActions
import com.elliottsoftware.calftracker2.viewModels.CalfViewModel
import com.elliottsoftware.calftracker2.viewModels.CalfViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import java.util.*


/**
 * A simple [Fragment] subclass used to instantiate the [R.layout.fragment_new_calf]
 * and to handle basic logic
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

    /**
     * Will inflate the XML file via View Binding, also gets references to basic
     * view items
     */
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
    /**
     * handles all the business logic needed for the [R.layout.fragment_new_calf]
     * file
     */
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

    /**
     * sets _binding = null to avoid memory leaks with View Binding
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * private utility function to determine the sex of the calf
     * @param[radioButton] the bull radio button to determine if it was
     * clicked of not
     * @return the sex of the calf to be saved
     */
    private fun buttonIsChecked(radioButton: RadioButton):String{
        return if(radioButton.isChecked){
            "Bull"
        }else{
            "Heifer"
        }
    }
    /**
     * private utility function to save the calf to the Room database and navigate
     * back to home fragment.
     * @param[tagNumber] tag number entered by the user
     * @param[details] details entered by the user
     * @param[cciaNumber] ccia number entered by the user
     * @param[sex] sex of the calf entered by the user
     * @param[view] the view being clicked. Used to navigate back to home
     *
     * @return no value
     */

    private fun saveCalf(
        tagNumber: String,
        details: String,
        cciaNumber: String,
        sex: String,
        view: View,
    ){
        if(!validateTagNumber(tagNumber)){
            // this should run if the tagNumber is not empty
            calfViewModel.insert(Calf(tagNumber,cciaNumber,sex,details, Date()))
            val snackBar = Snackbar.make(view,"Calf $tagNumber created",Snackbar.LENGTH_LONG)
            snackBar.setAction("DISMISS",SnackBarActions(snackBar))
            snackBar.show()
            Navigation.findNavController(view).navigate(R.id.action_newCalfFragment_to_mainFragment)
        }
    }

    /**
     * private utility function to determine if the tag number is empty or not
     * @param[tagNumber] the tag number entered by teh user
     * @return boolean detecting if the tag number is empty
     */
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