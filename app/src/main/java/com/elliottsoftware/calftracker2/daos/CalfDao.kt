package com.elliottsoftware.calftracker2.daos

import androidx.room.*
import com.elliottsoftware.calftracker2.models.Calf
import kotlinx.coroutines.flow.Flow

@Dao
interface CalfDao {
    @Query("SELECT * FROM calf ORDER BY  date ASC")
    fun getAllCalves():Flow<MutableList<Calf>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(calf:Calf)

    @Query("DELETE FROM calf")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteCalf( calf: Calf):Int

    @Update
    suspend fun updateCalf(calf:Calf)

    @Query("SELECT * FROM calf WHERE calf.id==:calfId")
    suspend fun findCalf(calfId:Long):Calf

    @Query("SELECT * FROM calf WHERE tagNumber LIKE :searchQuery")
    fun searchDatabase(searchQuery:String): Flow<List<Calf>>


}