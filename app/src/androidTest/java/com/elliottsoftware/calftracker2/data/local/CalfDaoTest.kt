package com.elliottsoftware.calftracker2.data.local

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.elliottsoftware.calftracker2.daos.CalfDao
import com.elliottsoftware.calftracker2.db.CalfRoomDatabase
import com.elliottsoftware.calftracker2.models.Calf
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.*
import kotlin.jvm.Throws

@RunWith(AndroidJUnit4::class)
class CalfDaoTest {
    private lateinit var calfDao: CalfDao
    private lateinit var db:CalfRoomDatabase


    @Before
    fun createDb(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,CalfRoomDatabase::class.java).build()
        calfDao = db.calfDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb(){
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertCalfTest() = runBlocking{
        //GIVEN
        val expectedTagNumber = "TESTING"
        val calf = Calf(expectedTagNumber,"more","again","fds", Date())

        //WHEN
        calfDao.insert(calf)
        val foundCalf:Calf = calfDao.findCalf(1L)



        //THEN
        assertThat(foundCalf.tagNumber,equalTo(expectedTagNumber))

    }

    @Test
    fun updateCalfTest() = runBlocking {
        //GIVEN
        val expectedTagNumber = "TESTING"
        val updatedTagNumber = "CHANGED"
        val calf = Calf(expectedTagNumber,"more","again","fds", Date())

        //WHEN
        calfDao.insert(calf)
        val foundCalf = calfDao.findCalf(1L)
        foundCalf.tagNumber = updatedTagNumber
        calfDao.updateCalf(foundCalf)
        val updatedCalf = calfDao.findCalf(1L)

        //THEN
        assertThat(updatedCalf.tagNumber, equalTo(updatedTagNumber))
    }

    @Test
    fun deleteCalfTest() = runBlocking {
        //GIVEN
        val calf = Calf("TESTING","more","again","fds", Date())

        //WHEN
        calfDao.insert(calf)
        val foundCalf = calfDao.findCalf(1L)
        val deletedCalf:Int = calfDao.deleteCalf(foundCalf)

        //THEN
        assertThat(deletedCalf, equalTo(1))
    }
}