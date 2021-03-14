package com.example.androiddevchallenge.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androiddevchallenge.common.Resource
import com.example.androiddevchallenge.model.MainData
import com.example.androiddevchallenge.repository.MainRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

/**
 * MainViewModel is the bridge to communicate between data layer and ui layer.
 *
 * @author PeytonWu
 * @since 2021/3/13
 */
class MainViewModel : ViewModel(){
    private val repository by lazy {
        MainRepository()
    }
    private val _mainLiveData = MutableLiveData<Resource<MainData>>()
    val mainLiveData :MutableLiveData<Resource<MainData>>
        get() = _mainLiveData

    /**
     * The handler to handle coroutine exceptions and notify to the observer.
     */
    private val handler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
        _mainLiveData.value = Resource.error(throwable.message ?: "Uncaught exception happens")
    }
    /**
     * Start to get data.
     */
    fun getData() = viewModelScope.launch(handler) {
        _mainLiveData.value = Resource.loading()
        _mainLiveData.value = Resource.success(repository.getMainData())
    }
}