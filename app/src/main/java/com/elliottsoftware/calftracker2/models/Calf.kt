package com.elliottsoftware.calftracker2.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Calf(

    var tagNumber: String,
    val CCIANumber: String,
    val sex:String,
    val details:String,
    val date: Date,
    @PrimaryKey(autoGenerate = true)
    val id: Long =0
) {

}
