package com.savedroid.wish


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.savedroid.wish.database.WishRepository
import com.savedroid.wish.database.entities.Wish
import com.savedroid.wish.network.model.ApiResponse
import com.savedroid.wish.network.model.Wrapper
import com.savedroid.wish.network.request.WishRequest
import com.savedroid.wish.network.requesthandler.Kobserver

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val wishRepository by lazy { WishRepository(application) }
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    var wishes = MutableLiveData<List<Wish>>()
    var exception = MutableLiveData<java.lang.Exception>()

    fun loadWishes() {
        wishRepository.getAllWishes(object : DataCallBack {
            override fun onSuccess(wishes: List<Wish>) {
                when {
                    wishes.isNullOrEmpty() -> remoteLoadWishes()
                    else -> {
                        this@MainViewModel.wishes.value = wishes
                        remoteLoadWishes()
                    }
                }
            }
        })

    }

    private fun remoteLoadWishes() {
        wishApi().observeForever(object : Kobserver<ApiResponse>(),
            Observer<Wrapper<ApiResponse>> {
            override fun onSuccess(data: ApiResponse) {
                insert(data.wishes!!)
                wishes.value = data.wishes
            }

            override fun onException(exception: Exception) {
                super.onException(exception)
                this@MainViewModel.exception.value = exception
            }
        })
    }

    fun insert(wishes: List<Wish>) {
        wishRepository.insert(wishes)
    }

    fun wishApi(): LiveData<Wrapper<ApiResponse>> {
        return WishRequest().doRequest()
    }

}