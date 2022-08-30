package com.elliottsoftware.calftracker2.util

import android.widget.EditText
import android.widget.RadioButton

class CalfUtil {

    companion object {
        /**
         *  utility function to determine if the tag number is empty or not
         * @param[tagNumber] the tag number entered by teh user
         * @return boolean detecting if the tag number is empty
         */
        fun validateTagNumber(tagNumber: String, editTag: EditText): Boolean {
            //if statements are expressions in Kotlin
            return if (tagNumber.isEmpty()) {
                editTag.error = "Field can not be empty"
                true;
            } else {
                false;
            }
        }

        /**
         *  utility function to determine the sex of the calf
         * @param[radioButton] the bull radio button to determine if it was
         * clicked of not
         * @return the sex of the calf to be saved
         */
         fun buttonIsChecked(radioButton: RadioButton):String{
            return if(radioButton.isChecked){
                "Bull"
            }else{
                "Heifer"
            }
        }
    }
}