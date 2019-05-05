package com.savedroid.wish.database


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.savedroid.wish.database.daos.WishDao
import com.savedroid.wish.database.entities.Wish

@Database(entities = [Wish::class], version = 1, exportSchema = false)
abstract class WishRoomDatabase : RoomDatabase() {

    abstract fun wishDao(): WishDao

    companion object {

        private var INSTANCE: WishRoomDatabase? = null

        internal fun getDatabase(context: Context): WishRoomDatabase {
            if (INSTANCE == null) {
                synchronized(WishRoomDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            WishRoomDatabase::class.java, "word_database"
                        )
                            // Wipes and rebuilds instead of migrating if no Migration object.
                            // Migration is not part of this codelab.
                            .fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}
