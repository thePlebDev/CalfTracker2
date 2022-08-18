package com.elliottsoftware.calftracker2.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elliottsoftware.calftracker2.models.Calf
import kotlinx.coroutines.flow.Flow

@Dao
interface CalfDao {
    @Query("SELECT * FROM calf ORDER BY  date ASC")
    fun getAllCalves():Flow<List<Calf>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(calf:Calf)

    @Query("DELETE FROM calf")
    suspend fun deleteAll()


}