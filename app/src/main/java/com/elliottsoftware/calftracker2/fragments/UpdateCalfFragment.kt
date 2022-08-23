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
import androidx.navigation.fragment.navArgs
import com.elliottsoftware.calftracker2.R
import com.elliottsoftware.calftracker2.databinding.FragmentNewCalfBinding
import com.elliottsoftware.calftracker2.databinding.FragmentUpdateCalfBinding
import com.elliottsoftware.calftracker2.models.Calf
import com.elliottsoftware.calftracker2.util.CalfApplication
import com.elliottsoftware.calftracker2.viewModels.CalfViewModel
import com.elliottsoftware.calftracker2.viewModels.CalfViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [UpdateCalfFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UpdateCalfFragment : Fragment() {

    private var _binding:FragmentUpdateCalfBinding? = null
    private val binding get() = _binding!!
    private val args: UpdateCalfFragmentArgs by navArgs()
    private val calfViewModel: CalfViewModel by viewModels {
        CalfViewModelFactory((activity?.application as CalfApplication).repository)
    }
    private lateinit var cancelButton: FloatingActionButton
    private lateinit var updateButton: FloatingActionButton
    private lateinit var  updateTagNumber: EditText
    private lateinit var updateDetails: EditText
    private lateinit var updateCCIANumber: EditText
    private lateinit var updateSexBULL: RadioButton
    private lateinit var updateSexHEIFER: RadioButton
    private lateinit var calfDate: Date


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateCalfBinding.inflate(inflater,container,false)
        val view = binding.root
        cancelButton = binding.newCalfFabLeft;
        updateButton = binding.newCalfFabRight;
        updateTagNumber = binding.updateTag
        updateDetails = binding.updateDescription
        updateCCIANumber = binding.updateCciaNumber
        updateSexBULL = binding.radioBull
        updateSexHEIFER = binding.radioHeifer
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        CoroutineScope(Dispatchers.Main).launch {

            setCalfOnMainThread(args.calfId)

        }
        cancelButton.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_updateCalfFragment_to_mainFragment)
        }
        updateButton.setOnClickListener{
            updateCalf(updateTagNumber.text.toString(),updateDetails.text.toString(),
                updateCCIANumber.text.toString(), updateSexBULL.isChecked)

            Navigation.findNavController(view).navigate(R.id.action_updateCalfFragment_to_mainFragment)
        }
    }

    private suspend fun setCalfOnMainThread(calfId:Long){
        val foundCalf = calfViewModel.findCalf(calfId)
        updateTagNumber.setText(foundCalf.tagNumber)
        updateDetails.setText(foundCalf.details)
        updateCCIANumber.setText(foundCalf.CCIANumber)
        calfDate = foundCalf.date
        updateSex(foundCalf.sex)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateSex(sex:String){
        if(sex == "Bull"){
            updateSexBULL.isChecked = true
        }else{
            updateSexHEIFER.isChecked = true
        }
    }
    private fun updateCalf(tagNumber: String,details:String,cciaNumber: String,isBull:Boolean){
        if(!tagNumberIsEmpty(tagNumber)){
            val sex = checkSex(isBull)
            calfViewModel.updateCalf(Calf(tagNumber,cciaNumber,sex,details,calfDate,args.calfId))
            

        }


    }

    private fun tagNumberIsEmpty(tagNumber:String):Boolean{
        //if statements are expressions in Kotlin
        return if(tagNumber.isEmpty()){
            updateTagNumber.error = "Field can not be empty"
            true;
        }else{
            false;
        }

    }

    private fun checkSex(isBull: Boolean):String{
        return if(isBull){
            "Bull"
        }else{
            "Heifer"
        }
    }

}