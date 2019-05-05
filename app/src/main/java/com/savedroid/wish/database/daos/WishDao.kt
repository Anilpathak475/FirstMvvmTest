package com.savedroid.wish.database.daos


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.savedroid.wish.database.entities.Wish

/**
 * Created by axier on 7/2/18.
 */

@Dao
interface WishDao {

    @Query("SELECT * FROM wish")
    fun getAllWishes(): List<Wish>

    @get:Query("SELECT Count(*) FROM wish")
    val count: Long

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(bills: List<Wish>)

    @Query("DELETE FROM wish")
    fun deleteAll()
}
