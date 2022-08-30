package com.elliottsoftware.calftracker2.util

import android.widget.EditText

class CalfUtil {

    companion object {
        /**
         * private utility function to determine if the tag number is empty or not
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
    }
}