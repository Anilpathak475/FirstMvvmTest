package com.savedroid.wish.database


import androidx.annotation.WorkerThread
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.savedroid.wish.WishApplication

@Database(entities = [Wish::class], version = 1, exportSchema = false)
abstract class WishDatabase : RoomDatabase() {

    abstract fun wishDao(): Wishes

    companion object {

        private var INSTANCE: WishDatabase? = null

        @WorkerThread
        fun get(): WishDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(WishApplication.applicationContext(), WishDatabase::class.java, "wish")
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE!!
        }

    }
}
