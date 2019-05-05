package com.savedroid.wish.database


import android.app.Application
import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.savedroid.wish.DataCallBack
import com.savedroid.wish.database.daos.WishDao
import com.savedroid.wish.database.entities.Wish

class WishRepository
internal constructor(application: Application) {

    private val wishDao by lazy { wishRoomDatabase.wishDao() }
    private val wishRoomDatabase by lazy {
        WishRoomDatabase.getDatabase(application)
    }

    var wishes = MutableLiveData<List<Wish>>()


    fun getAllWishes(dataCallback: DataCallBack) {
        GetWishes(wishDao, object : DataCallBack {
            override fun onSuccess(wishes: List<Wish>) {
                dataCallback.onSuccess(wishes)
                this@WishRepository.wishes.value = wishes
            }
        }).execute()
    }

    // You must call this on a non-UI thread or your app will crash.
    // Like this, Room ensures that you're not doing any long running operations on the main
    // thread, blocking the UI.
    fun insert(wishes: List<Wish>) {
        Log.d("Inserting Now", wishes.size.toString())
        SaveWishes(wishDao).execute(wishes)
    }

    private class SaveWishes internal constructor(private val mAsyncTaskDao: WishDao) :
        AsyncTask<List<Wish>, Void, Void>() {

        override fun doInBackground(vararg params: List<Wish>): Void? {
            try {
                mAsyncTaskDao.insertAll(params[0])
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
            return null
        }
    }

    private class GetWishes internal constructor(private val mAsyncTaskDao: WishDao, val dataCallback: DataCallBack) :
        AsyncTask<Void, Void, List<Wish>>() {

        override fun doInBackground(vararg params: Void): List<Wish>? {
            return mAsyncTaskDao.getAllWishes()
        }

        override fun onPostExecute(result: List<Wish>?) {
            super.onPostExecute(result!!)
            dataCallback.onSuccess(result)
        }
    }
}
