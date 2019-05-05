package com.savedroid.wish.network.model

import androidx.lifecycle.LiveData
import com.savedroid.wish.database.entities.Wish

data class ApiResponse(val wishes: List<Wish>?)