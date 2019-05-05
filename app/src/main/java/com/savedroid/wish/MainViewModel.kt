package com.savedroid.wish


import android.app.Application
import androidx.annotation.UiThread
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.savedroid.wish.database.Wish
import com.savedroid.wish.network.model.ApiResponse
import com.savedroid.wish.network.model.Wrapper
import com.savedroid.wish.network.request.WishRequest
import com.savedroid.wish.network.requesthandler.Kobserver

class MainViewModel(application: Application) : AndroidViewModel(application) {
    // var wishes = MutableLiveData<List<Wish>>()
    private val _exception = MutableLiveData<java.lang.Exception>()
    val exception: LiveData<java.lang.Exception> get() = _exception

    private val _wishes = MutableLiveData<List<Wish>>()
    val wishes: LiveData<List<Wish>> get() = _wishes
    @UiThread
    fun loadWishes(): LiveData<List<Wish>> {
        Coroutines.ioThenMain({
            DataManager.getWishes()
        }) {
            when {
                it!!.isNullOrEmpty() -> remoteLoadWishes()
                else -> {
                    _wishes.value = it
                    remoteLoadWishes()
                }
            }
        }
        return wishes
    }

    private fun remoteLoadWishes() {
        wishApi().observeForever(object : Kobserver<ApiResponse>(),
            Observer<Wrapper<ApiResponse>> {
            override fun onSuccess(data: ApiResponse) {
                Coroutines.ioThenMain({
                    DataManager.saveWishes(data.wishes!!)
                }) {
                    _wishes.value = data.wishes
                }
            }

            override fun onException(exception: Exception) {
                super.onException(exception)
                this@MainViewModel._exception.value = exception
            }
        })
    }

    fun wishApi(): LiveData<Wrapper<ApiResponse>> {
        return WishRequest().doRequest()
    }
}