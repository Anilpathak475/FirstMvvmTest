package com.savedroid.wish.database


import androidx.annotation.Nullable
import androidx.room.*
import com.google.gson.annotations.SerializedName

@Entity
data class Wish(
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0,

    @ColumnInfo(name = "isCrypto")
    @Nullable
    @SerializedName("is_crypto")
    val isCrypto: Boolean?,

    @ColumnInfo(name = "wishId")
    @SerializedName("wish_id")
    val wishId: String?,

    @ColumnInfo(name = "targetDate")
    @SerializedName("target_date")
    val targetDate: String?,

    @ColumnInfo(name = "isDefault")
    @SerializedName("is_default")
    val isDefault: Boolean?,

    @ColumnInfo(name = "isCompleted")
    @SerializedName("is_completed")
    val isCompleted: Boolean?,

    @ColumnInfo(name = "isDeleted")
    @SerializedName("is_deleted")
    val isDeleted: Boolean?,

    @ColumnInfo(name = "isGroupWish")
    @Nullable
    @SerializedName("is_groupwish")
    val isGroupWish: Boolean?,

    @ColumnInfo(name = "userId")
    @Nullable
    @SerializedName("user_id")
    val userId: String?,

    @ColumnInfo(name = "cryptoConfig")
    @Nullable
    @SerializedName("crypto_config")
    val cryptoConfig: String?,

    @ColumnInfo(name = "targetBalance")
    @Nullable
    @SerializedName("target_balance")
    val targetBalance: Int = 0,

    @ColumnInfo(name = "name")
    @Nullable
    @SerializedName("name")
    val name: String ?,

    @ColumnInfo(name = "currency")
    @Nullable
    @SerializedName("currency")
    val currency: String ?,

    @ColumnInfo(name = "id")
    @Nullable
    @SerializedName("id")
    val id: String?
)

@Dao
interface Wishes : BaseDao<Wish> {

    @Query("SELECT * FROM wish")
    fun getAll(): List<Wish>

    @Query("DELETE FROM wish")
    fun deleteAll()

}


