package com.savedroid.wish

import android.util.Log
import androidx.annotation.WorkerThread
import com.savedroid.wish.database.Wish
import com.savedroid.wish.database.WishDatabase

object DataManager {

    @WorkerThread
    fun saveWishes(wishes: List<Wish>) {
        val db = WishDatabase.get()
        deleteAllWishes()
        db.wishDao().insert(wishes)
        Log.d("Size in databse", "" + getWishes().size)
    }

    @WorkerThread
    fun getWishes(): List<Wish> {
        return WishDatabase.get().wishDao().getAll()
    }

    @WorkerThread
    private fun deleteAllWishes() {
        return WishDatabase.get().wishDao().deleteAll()
    }

}