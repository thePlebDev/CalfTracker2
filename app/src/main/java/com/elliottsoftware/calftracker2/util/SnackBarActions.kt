package com.elliottsoftware.calftracker2.util

import android.view.View
import com.google.android.material.snackbar.Snackbar


//TODO REFACTOR
//should be able to create a SnackBar with different lengths and texts
//look into extension functions
class SnackBarActions(private val snackBar:Snackbar): View. OnClickListener{

    override fun onClick(v:View){
        snackBar.dismiss()
    }
}