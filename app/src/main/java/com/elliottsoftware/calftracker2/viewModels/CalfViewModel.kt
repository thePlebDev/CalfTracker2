package com.elliottsoftware.calftracker2.viewModels

import androidx.lifecycle.*
import com.elliottsoftware.calftracker2.models.Calf
import com.elliottsoftware.calftracker2.repositories.CalfRepository
import kotlinx.coroutines.launch

class CalfViewModel(private val repository: CalfRepository): ViewModel() {

    val allCalves: LiveData<List<Calf>> = repository.allCalves.asLiveData()

    //launching a new coroutine to insert the data in a non-blocking way
    fun insert(calf:Calf) = viewModelScope.launch{
        repository.insert(calf)
    }
}

class CalfViewModelFactory(private val repository: CalfRepository): ViewModelProvider.Factory{
    override fun <T:ViewModel> create(modelClass: Class<T>):T{
        if(modelClass.isAssignableFrom(CalfViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return CalfViewModel(repository) as T
        }
        throw IllegalArgumentException("UNKNOWN VIEW MODEL CLASS")
    }
}