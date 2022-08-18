package com.elliottsoftware.calftracker2.repositories

import androidx.annotation.WorkerThread
import com.elliottsoftware.calftracker2.daos.CalfDao
import com.elliottsoftware.calftracker2.models.Calf
import kotlinx.coroutines.flow.Flow

//we declare the DAO as a private property in the constructor.Pass in the DAO
//instead of the whole database, because we only need to access the DAO
class CalfRepository(private val calfDao: CalfDao) {

    //Room executes all the queries on a separate thread
    // Observed Flow will notify the observer when the data has changed
    val allCalves: Flow<List<Calf>> = calfDao.getAllCalves();


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(calf: Calf) {
        //suspend tells the compiler the this needs to be called from a coroutine or another suspending function
        calfDao.insert(calf)
    }
}