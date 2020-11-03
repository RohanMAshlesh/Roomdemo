package com.example.roomdemo

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomdemo.db.Subscriber
import com.example.roomdemo.db.SubscriberRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SubscriberViewModel(private val repository: SubscriberRepository): ViewModel(),Observable {

    val subscribers = repository.subscribers
    @Bindable
    val inputName = MutableLiveData<String>()
    @Bindable
    val inputEmail = MutableLiveData<String>()
    @Bindable
    val saveorupdateButtonText = MutableLiveData<String>()
    @Bindable
    val clearallordeleteButtonText = MutableLiveData<String>()

    init {
        saveorupdateButtonText.value = "Save"
        clearallordeleteButtonText.value = "Clear"
    }

    fun saveorUpdate(){
        val name = inputName.value!!
        val email = inputEmail.value!!
        insert(Subscriber(0,name,email))
        inputName.value = null
        inputEmail.value = null
    }

    fun clearordelete(){
        deleteAll()
    }

    fun insert(subscriber: Subscriber): Job = viewModelScope.launch {
            repository.insert(subscriber)
    }
    fun update(subscriber: Subscriber): Job = viewModelScope.launch {
        repository.update(subscriber)
    }
    fun delete(subscriber: Subscriber): Job = viewModelScope.launch {
        repository.delete(subscriber)
    }
    fun deleteAll(): Job = viewModelScope.launch {
        repository.deleteAll()
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        TODO("Not yet implemented")
    }

}